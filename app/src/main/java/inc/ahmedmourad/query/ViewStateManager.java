package inc.ahmedmourad.query;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.flexbox.FlexboxLayout;

import inc.ahmedmourad.query.elements.Group;

public class ViewStateManager {

	private Query query = null;

	@Nullable
	private FlexboxLayout displayFlexbox = null;

	@Nullable
	private View beginGroupView = null;

	@Nullable
	private View endGroupView = null;

	@Nullable
	private View andView = null;

	@Nullable
	private View orView = null;

	@Nullable
	private View enterView = null;

	@Nullable
	private View clearView = null;

	@Nullable
	private EditText parameterEditText = null;

	private boolean canAcceptParameter = true;

	private ViewStateManager() {

	}

	private static ViewStateManager of(@NonNull final Query query) {
		final ViewStateManager manager = new ViewStateManager();
		manager.setQuery(query);
		return manager;
	}

	private void bind() {

		if (beginGroupView != null)
			beginGroupView.setOnClickListener(v -> query.beginGroup());

		if (endGroupView != null)
			endGroupView.setOnClickListener(v -> query.endGroup());

		if (andView != null)
			andView.setOnClickListener(v -> query.and());

		if (orView != null)
			orView.setOnClickListener(v -> query.or());

		if (clearView != null)
			clearView.setOnClickListener(v -> query.clear());

		if (enterView != null)
			enterView.setOnClickListener(v -> {
				if (parameterEditText != null) {
					query.param(parameterEditText.getText().toString());
					parameterEditText.setText("");
				}
			});

		if (parameterEditText != null)
			parameterEditText.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					if (enterView != null)
						enterView.setEnabled(canAcceptParameter && s.toString().trim().length() > 0);
				}
			});

		query.setOnElementsChangedListener((elements, openGroups) -> {

			if (displayFlexbox != null)
				query.display(displayFlexbox);

			if (endGroupView != null)
				endGroupView.setEnabled(openGroups.size() > 0);

			if (elements.size() == 0 && openGroups.size() == 0) {

				setRelationViewsEnabled(false);
				setInputViewsEnabled(true);

			} else if (openGroups.size() == 0) {

				setInputViewsEnabled(elements.get(elements.size() - 1).isRelation());
				setRelationViewsEnabled(true);

			} else {

				final Group group = openGroups.get(openGroups.size() - 1);
				setInputViewsEnabled(group.size() == 0 || group.get(group.size() - 1).isRelation());

				setRelationViewsEnabled(group.size() > 0);
			}
		});
	}

	private void setRelationViewsEnabled(final boolean enabled) {

		if (orView != null)
			orView.setEnabled(enabled);

		if (andView != null)
			andView.setEnabled(enabled);
	}

	private void setInputViewsEnabled(final boolean enabled) {

		if (beginGroupView != null)
			beginGroupView.setEnabled(enabled);

		canAcceptParameter = enabled;

		if (enterView != null)
			enterView.setEnabled(canAcceptParameter());
	}

	private boolean canAcceptParameter() {
		return parameterEditText == null ? canAcceptParameter : canAcceptParameter && parameterEditText.getText().toString().trim().length() > 0;
	}

	public void release() {

		if (displayFlexbox != null)
			displayFlexbox.removeAllViews();

		query.setOnElementsChangedListener(null);
	}

	public void updateQuery(@NonNull final Query query) {
		setQuery(query);
		bind();
	}

	private void setQuery(@NonNull final Query query) {
		this.query = query;
	}

	private void setDisplayFlexbox(@Nullable final FlexboxLayout displayFlexbox) {
		this.displayFlexbox = displayFlexbox;
	}

	private void setBeginGroupView(@Nullable final View beginGroupView) {
		this.beginGroupView = beginGroupView;
	}

	private void setEndGroupView(@Nullable final View endGroupView) {
		this.endGroupView = endGroupView;
	}

	private void setAndView(@Nullable final View andView) {
		this.andView = andView;
	}

	private void setOrView(@Nullable final View orView) {
		this.orView = orView;
	}

	private void setEnterView(@Nullable final View enterView) {
		this.enterView = enterView;
	}

	private void setClearView(@Nullable final View clearView) {
		this.clearView = clearView;
	}

	private void setParameterEditText(@Nullable EditText parameterEditText) {
		this.parameterEditText = parameterEditText;
	}

	public static class Builder {

		private ViewStateManager manager;

		public static Builder with(@NonNull final Query query) {
			return Builder.of(ViewStateManager.of(query));
		}

		private Builder() {

		}

		private static Builder of(@NonNull final ViewStateManager manager) {
			final Builder builder = new Builder();
			builder.setManager(manager);
			return builder;
		}

		private void setManager(@NonNull final ViewStateManager manager) {
			this.manager = manager;
		}

		public Builder display(@Nullable final FlexboxLayout displayFlexbox) {
			manager.setDisplayFlexbox(displayFlexbox);
			return this;
		}

		public Builder beginGroup(@Nullable final View beginGroupView) {
			manager.setBeginGroupView(beginGroupView);
			return this;
		}

		public Builder endGroup(@Nullable final View endGroupView) {
			manager.setEndGroupView(endGroupView);
			return this;
		}

		public Builder and(@Nullable final View andView) {
			manager.setAndView(andView);
			return this;
		}

		public Builder or(@Nullable final View orView) {
			manager.setOrView(orView);
			return this;
		}

		public Builder enter(@Nullable final View enterView) {
			manager.setEnterView(enterView);
			return this;
		}

		public Builder clear(@Nullable final View clearView) {
			manager.setClearView(clearView);
			return this;
		}

		public Builder parameter(@Nullable final EditText parameterEditText) {
			manager.setParameterEditText(parameterEditText);
			return this;
		}

		public ViewStateManager build() {
			manager.bind();
			return manager;
		}
	}
}
