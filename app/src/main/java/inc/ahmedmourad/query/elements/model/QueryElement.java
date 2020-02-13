package inc.ahmedmourad.query.elements.model;

import android.support.annotation.NonNull;

public interface QueryElement {

	boolean isRelation();

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	boolean isValid();

	@NonNull
	String getAsString();
}
