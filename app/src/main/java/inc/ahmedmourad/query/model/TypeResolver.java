package inc.ahmedmourad.query.model;

import android.support.annotation.Nullable;

import inc.ahmedmourad.query.elements.model.QueryElement;

public interface TypeResolver {
	@Nullable
	Class<? extends QueryElement> resolve(final int type);
}
