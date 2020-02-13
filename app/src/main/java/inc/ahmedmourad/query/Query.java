package inc.ahmedmourad.query;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import inc.ahmedmourad.query.configs.Configs;
import inc.ahmedmourad.query.configs.model.QueryConfigs;
import inc.ahmedmourad.query.elements.Group;
import inc.ahmedmourad.query.elements.Parameter;
import inc.ahmedmourad.query.elements.Relation;
import inc.ahmedmourad.query.elements.model.DisplayableElement;
import inc.ahmedmourad.query.elements.model.QueryElement;
import inc.ahmedmourad.query.elements.model.ValidateableElement;
import inc.ahmedmourad.query.gson.deserializer.Deserializer;
import inc.ahmedmourad.query.gson.serializer.Serializer;
import inc.ahmedmourad.query.utils.QueryUtils;

public class Query {

	private static QueryConfigs configs = Configs.getInstance();

	private List<QueryElement> elements = new ArrayList<>();

	private final List<Group> groups = new ArrayList<>();

	@Nullable
	private transient OnElementsChangedListener listener = null;

	private static final transient Gson gson;

	static {
		gson = new GsonBuilder().registerTypeAdapter(List.class, new Serializer())
				.registerTypeAdapter(List.class, new Deserializer())
				.create();
	}

	@NonNull
	public static Query with(@NonNull final String parameter) {
		final Query query = new Query();
		query.param(parameter);
		return query;
	}

	@NonNull
	public static Query withGroup() {
		final Query query = new Query();
		query.beginGroup();
		return query;
	}

	@NonNull
	public static Query fromJson(@NonNull final String json) {
		return gson.fromJson(json, Query.class);
	}

	@NonNull
	public static Query empty() {
		return new Query();
	}

	public static void configure(@NonNull final QueryConfigs configs) {
		Query.configs = configs;
	}

	@NonNull
	public static QueryConfigs getConfigs() {
		return configs;
	}

	private Query() {

	}

	@SuppressWarnings("UnusedReturnValue")
	@NonNull
	public Query param(@NonNull final String parameter) {
		add(Parameter.of(parameter));
		fireElementsChangedListener();
		return this;
	}

	@NonNull
	public Query group(@NonNull final Group group) {
		add(group);
		fireElementsChangedListener();
		return this;
	}

	@NonNull
	public Query and() {

		if (elements.size() == 0 && (groups.size() == 0 || groups.get(groups.size() - 1).size() == 0))
			throw new IllegalStateException("First element of a query or a group can't be a relation");

		add(Relation.of(Relation.TYPE_AND));

		fireElementsChangedListener();

		return this;
	}

	@NonNull
	public Query or() {

		if (elements.size() == 0 && (groups.size() == 0 || groups.get(groups.size() - 1).size() == 0))
			throw new IllegalStateException("First element of a query or a group can't be a relation");

		add(Relation.of(Relation.TYPE_OR));

		fireElementsChangedListener();

		return this;
	}

	public void add(@NonNull final QueryElement element) {
		if (groups.size() == 0)
			addElement(element);
		else
			groups.get(groups.size() - 1).add(element);
	}

	private void addElement(@NonNull final QueryElement element) {

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
	}

	@NonNull
	public Query beginGroup() {
		groups.add(new Group());
		fireElementsChangedListener();
		return this;
	}

	@NonNull
	public Query endGroup() {

		if (groups.size() == 0)
			throw new IllegalStateException("You must begin a group first before ending it");

		final Group group = groups.get(groups.size() - 1);

		group.validate();

		if (groups.size() > 1)
			groups.get(groups.size() - 2).group(group);
		else if (group.isValid())
			addElement(group);

		groups.remove(group);

		fireElementsChangedListener();

		return this;
	}

	public Query clear() {
		elements.clear();
		groups.clear();
		fireElementsChangedListener();
		return this;
	}

	private void validate() {

		while (groups.size() > 0)
			endGroup();

		elements = QueryUtils.trim(elements);

		for (int i = 0; i < elements.size(); ++i) {
			final QueryElement element = elements.get(i);
			if (element instanceof ValidateableElement)
				((ValidateableElement) element).validate();
		}
	}

	void display(@NonNull final ViewGroup container) {

		container.removeAllViews();

		for (int i = 0; i < elements.size(); ++i) {
			final QueryElement element = elements.get(i);
			if (element instanceof DisplayableElement)
				((DisplayableElement) element).display(container);
		}

		for (int i = 0; i < groups.size(); ++i) {

			final Group group = groups.get(i);

			container.addView(group.getLeadingView(container.getContext()));

			if (group.isValid())
				group.displayElements(container);
		}
	}

	public boolean isEmpty() {
		return QueryUtils.trim(elements).size() == 0 && QueryUtils.trim(groups).size() == 0;
	}

	void setOnElementsChangedListener(@Nullable final OnElementsChangedListener listener) {
		this.listener = listener;
		fireElementsChangedListener();
	}

	private void fireElementsChangedListener() {
		if (listener != null)
			listener.onChanged(new ArrayList<>(elements), new ArrayList<>(groups));
	}

	@NonNull
	public String toJson() {
		return toJson(false);
	}

	@NonNull
	public String toJson(final boolean validate) {
		if (validate)
			validate();
		return gson.toJson(this);
	}

	@NonNull
	public String getAsString() {

		validate();

		if (elements.size() == 0)
			return "";

		if (elements.size() == 1)
			return elements.get(0).getAsString();

		final StringBuilder builder = new StringBuilder();

		for (int i = 0; i < elements.size(); ++i)
			builder.append(elements.get(i)).append(" ");

		return builder.toString().trim().replaceAll(" +", " ");
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

		final Query query = (Query) o;

		if (elements.size() != query.elements.size() || groups.size() != query.groups.size())
			return false;

		for (int i = 0; i < elements.size(); ++i)
			if (!elements.get(i).equals(query.elements.get(i)))
				return false;

		for (int i = 0; i < groups.size(); ++i)
			if (!groups.get(i).equals(query.groups.get(i)))
				return false;

		return true;
	}

	@Override
	public int hashCode() {

		int result = 30;

		for (int i = 0; i < elements.size(); ++i)
			result *= elements.get(i).hashCode();

		for (int i = 0; i < groups.size(); ++i)
			result *= groups.get(i).hashCode();

		return result;
	}

	@FunctionalInterface
	public interface OnElementsChangedListener {
		void onChanged(@NonNull List<QueryElement> elements, @NonNull List<Group> openGroups);
	}
}
