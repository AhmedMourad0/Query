package inc.ahmedmourad.query.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import inc.ahmedmourad.query.Query;
import inc.ahmedmourad.query.R;
import inc.ahmedmourad.query.elements.Group;
import inc.ahmedmourad.query.elements.Parameter;
import inc.ahmedmourad.query.elements.Relation;
import inc.ahmedmourad.query.elements.model.QueryElement;
import inc.ahmedmourad.query.model.TypeResolver;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public final class QueryUtils {

	public static List<QueryElement> trim(@NonNull final List<? extends QueryElement> elements) {

		final List<QueryElement> clone = new ArrayList<>(elements);

		while (clone.size() > 0 && (clone.get(0).isRelation() || !clone.get(0).isValid()))
			clone.remove(0);

		while (clone.size() > 0 && (clone.get(clone.size() - 1).isRelation() || !clone.get(clone.size() - 1).isValid()))
			clone.remove(clone.size() - 1);

		return clone;
	}

	@NonNull
	public static CardView createView(@NonNull final Context context, @ColorRes final int backgroundColor, @NonNull final String value) {

		final CardView cardView = new CardView(context);

		cardView.setCardBackgroundColor(ContextCompat.getColor(context, backgroundColor));

		cardView.setRadius(context.getResources().getDimension(R.dimen.queryElementCardCornerRadius));

		cardView.setCardElevation(context.getResources().getDimension(R.dimen.queryElementCardElevation));

		final TextView textView = new TextView(context);

		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

		textView.setPadding(context.getResources().getDimensionPixelSize(R.dimen.queryElementPaddingStart),
				context.getResources().getDimensionPixelSize(R.dimen.queryElementPaddingTop),
				context.getResources().getDimensionPixelSize(R.dimen.queryElementPaddingEnd),
				context.getResources().getDimensionPixelSize(R.dimen.queryElementPaddingBottom)
		);

		textView.setText(value);

		cardView.addView(textView, 0);

		return cardView;
	}

	@NonNull
	public static CardView createFlexboxView(@NonNull final Context context, @ColorRes final int backgroundColor, @NonNull final String value) {

		final CardView cardView = createView(context, backgroundColor, value);

		final FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);

		params.setMargins(context.getResources().getDimensionPixelSize(R.dimen.queryCardMargin),
				context.getResources().getDimensionPixelSize(R.dimen.queryCardMargin),
				context.getResources().getDimensionPixelSize(R.dimen.queryCardMargin),
				context.getResources().getDimensionPixelSize(R.dimen.queryCardMargin)
		);

		cardView.setLayoutParams(params);

		return cardView;
	}

	public static void updateView(@NonNull final View view, @NonNull final String newValue) {
		extractTextView(view).setText(newValue);
	}

	@NonNull
	private static TextView extractTextView(@NonNull final View view) {
		return ((TextView) ((ViewGroup) view).getChildAt(0));
	}

	@NonNull
	public static Class<? extends QueryElement> resolveElementType(final int type) {
		return resolveElementType(type, null);
	}

	@NonNull
	public static Class<? extends QueryElement> resolveElementType(final int type, @Nullable final TypeResolver resolver) {

		if (resolver != null) {

			final Class<? extends QueryElement> cls = resolver.resolve(type);

			if (cls != null)
				return cls;
		}

		if (type == Query.getConfigs().getParameterType())
			return Parameter.class;
		else if (type == Query.getConfigs().getRelationType())
			return Relation.class;
		else if (type == Query.getConfigs().getGroupType())
			return Group.class;
		else
			throw new IllegalArgumentException("Unsupported QueryElement of type: " + type + ", try providing a TypeResolver");
	}

	private QueryUtils() {

	}
}
