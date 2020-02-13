package inc.ahmedmourad.query.model;

import android.support.annotation.NonNull;
import android.view.View;

public interface ViewCreator {
	@NonNull
	View createView(@NonNull String value);
}
