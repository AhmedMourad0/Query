package inc.ahmedmourad.query.elements;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import inc.ahmedmourad.query.Query;
import inc.ahmedmourad.query.elements.model.DisplayableElement;
import inc.ahmedmourad.query.elements.model.PersistableElement;
import inc.ahmedmourad.query.elements.model.QueryElement;
import inc.ahmedmourad.query.elements.model.ValidateableElement;
import inc.ahmedmourad.query.utils.QueryUtils;

public class Group implements QueryElement, ValidateableElement, PersistableElement, DisplayableElement {

	private List<QueryElement> elements = new ArrayList<>();

	private transient View leadingView = null;
	private transient View trailingView = null;

	@NonNull
	public static Group with(@NonNull final String parameter) {
		final Group group = new Group();
		group.param(parameter);
		return group;
	}

	@NonNull
	public static Group with(@NonNull final Group group) {
		final Group g = new Group();
		g.add(group);
		return g;
	}

	@NonNull
	public Group param(@NonNull final String parameter) {
		add(Parameter.of(parameter));
		return this;
	}

	@SuppressWarnings("UnusedReturnValue")
	@NonNull
	public Group group(@NonNull final Group group) {
		if (group.isValid())
			add(group);
		return this;
	}

	@NonNull
	public Group and() {
		add(Relation.of(Relation.TYPE_AND));
		return this;
	}

	@NonNull
	public Group or() {
		add(Relation.of(Relation.TYPE_OR));
		return this;
	}

	@NonNull
	public Group add(@NonNull final QueryElement element) {

		if (elements.size() > 0) {

			if (!element.isRelation() && !elements.get(elements.size() - 1).isRelation())
				throw new IllegalStateException("You must have a Relation between " +
						elements.get(elements.size() - 1).getClass().getSimpleName() +
						" and " +
						element.getClass().getSimpleName()
				);

			if (element.isRelation() && elements.get(elements.size() - 1).isRelation())
				elements.remove(elements.size() - 1);
		}

		elements.add(element);

		return this;
	}

	@Override
	public boolean isRelation() {
		return false;
	}

	@Override
	public boolean isValid() {
		return QueryUtils.trim(elements).size() > 0;
	}

	@Override
	public void validate() {

		elements = QueryUtils.trim(elements);

		for (int i = 0; i < elements.size(); ++i) {
			final QueryElement element = elements.get(i);
			if (element instanceof ValidateableElement)
				((ValidateableElement) element).validate();
		}
	}

	@Override
	public void display(@NonNull final ViewGroup container) {
		container.addView(getLeadingView(container.getContext()));
		displayElements(container);
		container.addView(getTrailingView(container.getContext()));
	}

	@Override
	@IntRange(from = 0, to = Integer.MAX_VALUE)
	public int getElementType() {
		return Query.getConfigs().getGroupType();
	}

	public void displayElements(@NonNull ViewGroup container) {
		for (int i = 0; i < elements.size(); ++i) {
			final QueryElement element = elements.get(i);
			if (element instanceof DisplayableElement)
				((DisplayableElement) element).display(container);
		}
	}

	@NonNull
	public View getLeadingView(@NonNull Context context) {
		if (leadingView == null)
			return leadingView = createView(context, Query.getConfigs().getGroupLead());
		else
			return leadingView;
	}

	@NonNull
	public View getTrailingView(@NonNull Context context) {
		if (trailingView == null)
			return trailingView = createView(context, Query.getConfigs().getGroupTrail());
		else
			return trailingView;
	}
	
	public View createView(@NonNull Context context, @NonNull final String value) {
		if (Query.getConfigs().getGroupViewCreator() != null)
			return Query.getConfigs().getGroupViewCreator().createView(value);
		else
			return QueryUtils.createFlexboxView(context, Query.getConfigs().getGroupColor(), value);
	}

	public QueryElement get(final int index) {
		return elements.get(index);
	}

	public int size() {
		return elements.size();
	}

	@Override
	@NonNull
	public String getAsString() {

		validate();

		if (elements.size() == 0)
			return "";

		if (elements.size() == 1)
			return elements.get(0).getAsString();

		final StringBuilder builder = new StringBuilder();

		builder.append(Query.getConfigs().getGroupLead());

		for (int i = 0; i < elements.size(); ++i)
			builder.append(elements.get(i)).append(" ");

		builder.deleteCharAt(builder.length() - 1).append(Query.getConfigs().getGroupTrail());

		return builder.toString();
	}

	@NonNull
	@Override
	public String toString() {
		return getAsString();
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		final Group group = (Group) o;

		if (elements.size() != group.elements.size())
			return false;

		for (int i = 0; i < elements.size(); ++i)
			if (!elements.get(i).equals(group.elements.get(i)))
				return false;

		return true;
	}

	@Override
	public int hashCode() {

		int result = 30;

		for (int i = 0; i < elements.size(); ++i)
			result *= elements.get(i).hashCode();

		return result;
	}
}
