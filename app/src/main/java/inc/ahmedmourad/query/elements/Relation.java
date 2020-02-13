package inc.ahmedmourad.query.elements;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import inc.ahmedmourad.query.Query;
import inc.ahmedmourad.query.elements.model.DisplayableElement;
import inc.ahmedmourad.query.elements.model.PersistableElement;
import inc.ahmedmourad.query.elements.model.QueryElement;
import inc.ahmedmourad.query.utils.QueryUtils;

public class Relation implements QueryElement, PersistableElement, DisplayableElement {

	public static final transient int TYPE_AND = 0;
	public static final transient int TYPE_OR = 1;

	private int type;

	private transient View view = null;

	@NonNull
	public static Relation of(@RelationType final int type) {
		final Relation relation = new Relation();
		relation.setType(type);
		return relation;
	}

	private Relation() {

	}

	@RelationType
	public int getType() {
		return type;
	}

	public void setType(@RelationType final int type) {
		this.type = type;
	}

	@Override
	public boolean isRelation() {
		return true;
	}

	@Override
	public boolean isValid() {
		return type == TYPE_AND || type == TYPE_OR;
	}

	@Override
	public void display(@NonNull final ViewGroup container) {
		container.addView(getView(container.getContext()));
	}

	@Override
	@IntRange(from = 0, to = Integer.MAX_VALUE)
	public int getElementType() {
		return Query.getConfigs().getRelationType();
	}

	@NonNull
	public View getView(@NonNull Context context) {

		if (view == null) {

			if (Query.getConfigs().getRelationViewCreator() != null)
				view = Query.getConfigs().getRelationViewCreator().createView(getAsString());
			else
				view = QueryUtils.createFlexboxView(context, Query.getConfigs().getRelationColor(), getAsString().toLowerCase());

		} else {

			if (Query.getConfigs().getRelationViewUpdater() != null)
				Query.getConfigs().getRelationViewUpdater().updateView(view, getAsString());
			else
				QueryUtils.updateView(view, getAsString().toLowerCase());
		}

		return view;
	}

	@Override
	@NonNull
	public String getAsString() {

		switch (getType()) {

			case TYPE_AND:
				return Query.getConfigs().getRelationAnd();

			case TYPE_OR:
				return Query.getConfigs().getRelationOr();

			default:
				throw new IllegalArgumentException("Unsupported Relation type: " + getType());
		}
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

		final Relation relation = (Relation) o;

		return getType() == relation.getType();
	}

	@Override
	public int hashCode() {
		return getType();
	}

	@IntDef({TYPE_AND, TYPE_OR})
	@Retention(RetentionPolicy.SOURCE)
	@Target({ElementType.PARAMETER, ElementType.METHOD})
	@interface RelationType {
	}
}
