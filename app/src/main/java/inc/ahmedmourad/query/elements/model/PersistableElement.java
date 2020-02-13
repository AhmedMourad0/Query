package inc.ahmedmourad.query.elements.model;

import android.support.annotation.IntRange;

public interface PersistableElement {
	@IntRange(from = 0, to = Integer.MAX_VALUE)
	int getElementType();
}
