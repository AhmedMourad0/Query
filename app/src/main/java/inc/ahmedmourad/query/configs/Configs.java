package inc.ahmedmourad.query.configs;

import android.support.annotation.ColorRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import inc.ahmedmourad.query.R;
import inc.ahmedmourad.query.configs.model.QueryConfigs;
import inc.ahmedmourad.query.model.TypeResolver;
import inc.ahmedmourad.query.model.ViewCreator;
import inc.ahmedmourad.query.model.ViewUpdater;

public final class Configs implements QueryConfigs {

	private static final Configs INSTANCE = new Configs();

	private int parameterType = 0;
	private int relationType = 1;
	private int groupType = 2;

	private int parameterColor = R.color.colorParameter;
	private int relationColor = R.color.colorRelation;
	private int groupColor = R.color.colorGroup;

	private ViewCreator parameterViewCreator = null;
	private ViewCreator relationViewCreator = null;
	private ViewCreator groupViewCreator = null;

	private ViewUpdater parameterViewUpdater = null;
	private ViewUpdater relationViewUpdater = null;

	private String relationAnd = "AND";
	private String relationOr = "OR";

	private String groupLead = "(";
	private String groupTrail = ")";

	private TypeResolver typeResolver = null;

	@NonNull
	public static Configs getInstance() {
		return INSTANCE;
	}

	private Configs() {

	}

	@Override
	@IntRange(from = 0, to = Integer.MAX_VALUE)
	public int getParameterType() {
		return parameterType;
	}

	@NonNull
	public Configs setParameterType(@IntRange(from = 0, to = Integer.MAX_VALUE) final int parameterType) {
		this.parameterType = parameterType;
		return this;
	}

	@Override
	@IntRange(from = 0, to = Integer.MAX_VALUE)
	public int getRelationType() {
		return relationType;
	}

	@NonNull
	public Configs setRelationType(@IntRange(from = 0, to = Integer.MAX_VALUE) final int relationType) {
		this.relationType = relationType;
		return this;
	}

	@Override
	@IntRange(from = 0, to = Integer.MAX_VALUE)
	public int getGroupType() {
		return groupType;
	}

	@NonNull
	public Configs setGroupType(@IntRange(from = 0, to = Integer.MAX_VALUE) final int groupType) {
		this.groupType = groupType;
		return this;
	}

	@Override
	@ColorRes
	public int getParameterColor() {
		return parameterColor;
	}

	@NonNull
	public Configs setParameterColor(@ColorRes final int parameterColor) {
		this.parameterColor = parameterColor;
		return this;
	}

	@Override
	@ColorRes
	public int getRelationColor() {
		return relationColor;
	}

	@NonNull
	public Configs setRelationColor(@ColorRes final int relationColor) {
		this.relationColor = relationColor;
		return this;
	}

	@Override
	@ColorRes
	public int getGroupColor() {
		return groupColor;
	}

	@NonNull
	public Configs setGroupColor(@ColorRes final int groupColor) {
		this.groupColor = groupColor;
		return this;
	}

	@Nullable
	@Override
	public ViewCreator getParameterViewCreator() {
		return parameterViewCreator;
	}

	@NonNull
	public Configs setParameterViewCreator(@Nullable final ViewCreator parameterViewCreator) {
		this.parameterViewCreator = parameterViewCreator;
		return this;
	}

	@Nullable
	@Override
	public ViewCreator getRelationViewCreator() {
		return relationViewCreator;
	}

	@NonNull
	public Configs setRelationViewCreator(@Nullable final ViewCreator relationViewCreator) {
		this.relationViewCreator = relationViewCreator;
		return this;
	}

	@Nullable
	@Override
	public ViewCreator getGroupViewCreator() {
		return groupViewCreator;
	}

	@NonNull
	public Configs setGroupViewCreator(@Nullable final ViewCreator groupViewCreator) {
		this.groupViewCreator = groupViewCreator;
		return this;
	}

	@Nullable
	@Override
	public ViewUpdater getParameterViewUpdater() {
		return parameterViewUpdater;
	}

	@NonNull
	public Configs setParameterViewUpdater(@Nullable final ViewUpdater parameterViewUpdater) {
		this.parameterViewUpdater = parameterViewUpdater;
		return this;
	}

	@Nullable
	@Override
	public ViewUpdater getRelationViewUpdater() {
		return relationViewUpdater;
	}

	@NonNull
	public Configs setRelationViewUpdater(@Nullable final ViewUpdater relationViewUpdater) {
		this.relationViewUpdater = relationViewUpdater;
		return this;
	}

	@Override
	@NonNull
	public String getRelationAnd() {
		return relationAnd;
	}

	@NonNull
	public Configs setRelationAnd(@NonNull final String relationAnd) {
		this.relationAnd = relationAnd;
		return this;
	}

	@Override
	@NonNull
	public String getRelationOr() {
		return relationOr;
	}

	@NonNull
	public Configs setRelationOr(@NonNull final String relationOr) {
		this.relationOr = relationOr;
		return this;
	}

	@NonNull
	@Override
	public String getGroupLead() {
		return groupLead;
	}

	@NonNull
	public Configs setGroupLead(@NonNull final String groupLead) {
		this.groupLead = groupLead;
		return this;
	}

	@NonNull
	@Override
	public String getGroupTrail() {
		return groupTrail;
	}

	@NonNull
	public Configs setGroupTrail(@NonNull final String groupTrail) {
		this.groupTrail = groupTrail;
		return this;
	}

	@Override
	@Nullable
	public TypeResolver getTypeResolver() {
		return typeResolver;
	}

	@NonNull
	public Configs setTypeResolver(@Nullable final TypeResolver typeResolver) {
		this.typeResolver = typeResolver;
		return this;
	}
}
