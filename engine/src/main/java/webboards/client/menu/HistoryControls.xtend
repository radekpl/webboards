package webboards.client.menu

import com.google.gwt.user.client.ui.Button
import com.google.gwt.user.client.ui.Grid
import webboards.client.data.GameCtx

import static extension webboards.client.menu.UIExtentions.*

class HistoryControls extends Grid {
	final GameCtx ctx;
	int position = 1;

	new(GameCtx ctx) {
		super(1, 3)
		this.ctx = ctx
		setWidget(1, 0, new Button("&lt;") += [back])
		setWidget(1, 1, new Button("&gt;") += [forward])
		setWidget(1, 2, new Button("&gt;&gt;") += [fastForward])
	}

	def back() {
		move(-1)
	}

	def forward() {
		move(1)
	}

	def fastForward() {
		while (forward) {
		}
	}

	def move(int step) {
		val end = ctx.ops.size - 1
		if (0 <= position && position < end) {
			position = position + step
			var op = ctx.ops.get(position)
			op.draw(ctx)
			true
		} else {
			false
		}
	}

}
