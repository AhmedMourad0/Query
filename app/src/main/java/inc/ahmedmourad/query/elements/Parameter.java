package inc.ahmedmourad.query.elements;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import inc.ahmedmourad.query.Query;
import inc.ahmedmourad.query.elements.model.DisplayableElement;
import inc.ahmedmourad.query.elements.model.PersistableElement;
import inc.ahmedmourad.query.elements.model.QueryElement;
import inc.ahmedmourad.query.utils.QueryUtils;

public class Parameter implements QueryElement, PersistableElement, DisplayableElement {

	private String value = "";

	private transient View view = null;

	@NonNull
	public static Parameter of(@NonNull final String value) {
		final Parameter parameter = new Parameter();
		parameter.setValue(value);
		return parameter;
	}

	private Parameter() {

	}

	@NonNull
	public String getValue() {
		return value.trim();
	}

	public void setValue(@NonNull final String value) {

		if (value.trim().length() == 0)
			throw new IllegalArgumentException("Parameter value cannot be an empty string");

		this.value = value;
	}

	@Override
	public boolean isRelation() {
		return false;
	}

	@Override
	public boolean isValid() {
		return getValue().length() > 0;
	}

	@Override
	public void display(@NonNull final ViewGroup container) {
		container.addView(getView(container.getContext()));
	}

	@Override
	@IntRange(from = 0, to = Integer.MAX_VALUE)
	public int getElementType() {
		return Query.getConfigs().getParameterType();
	}

	@NonNull
	public View getView(@NonNull final Context context) {

		if (view == null) {

			if (Query.getConfigs().getParameterViewCreator() != null)
				view = Query.getConfigs().getParameterViewCreator().createView(getValue());
			else
				view = QueryUtils.createFlexboxView(context, Query.getConfigs().getParameterColor(), getValue());

		} else {

			if (Query.getConfigs().getParameterViewUpdater() != null)
				Query.getConfigs().getParameterViewUpdater().updateView(view, getValue());
			else
				QueryUtils.updateView(view, getValue());
		}

		return view;
	}

	@Override
	@NonNull
	public String getAsString() {
		return "\"" + getValue() + "\"";
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

		final Parameter parameter = (Parameter) o;

		return getValue().equals(parameter.getValue());
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}
}
