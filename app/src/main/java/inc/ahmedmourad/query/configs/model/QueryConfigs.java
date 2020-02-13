package inc.ahmedmourad.query.configs.model;

import android.support.annotation.ColorRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import inc.ahmedmourad.query.model.TypeResolver;
import inc.ahmedmourad.query.model.ViewCreator;
import inc.ahmedmourad.query.model.ViewUpdater;

public interface QueryConfigs {

	@IntRange(from = 0, to = Integer.MAX_VALUE)
	int getParameterType();

	@IntRange(from = 0, to = Integer.MAX_VALUE)
	int getRelationType();

	@IntRange(from = 0, to = Integer.MAX_VALUE)
	int getGroupType();

	@ColorRes
	int getParameterColor();

	@ColorRes
	int getRelationColor();

	@ColorRes
	int getGroupColor();

	@Nullable
	ViewCreator getParameterViewCreator();

	@Nullable
	ViewCreator getRelationViewCreator();

	@Nullable
	ViewCreator getGroupViewCreator();

	@Nullable
	ViewUpdater getParameterViewUpdater();

	@Nullable
	ViewUpdater getRelationViewUpdater();

	@NonNull
	String getRelationAnd();

	@NonNull
	String getRelationOr();

	@NonNull
	String getGroupLead();

	@NonNull
	String getGroupTrail();

	@Nullable
	TypeResolver getTypeResolver();
}
