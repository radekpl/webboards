package webboards.client.menu

import com.google.gwt.user.client.ui.Button
import com.google.gwt.user.client.ui.Grid
import webboards.client.data.GameCtx

import static extension webboards.client.menu.UIExtentions.*

class HistoryControls extends Grid {
	final GameCtx ctx;

	new(GameCtx ctx) {
		super(1, 4)
		this.ctx = ctx		
		setWidget(0, 0, new Button("&lt;") += [back])
		setWidget(0, 1, new Button("[]") += [reset])
		setWidget(0, 2, new Button("&gt;") += [forward])
		setWidget(0, 3, new Button("&gt;&gt;") += [fastForward])
	}

	def reset() {
		while (ctx.position < ctx.ops.size) {
			ctx.ops.remove(ctx.ops.size - 1)
		}
		ctx.position = ctx.position - 1
	}

	def back() {
		if (ctx.position > 0) {
			ctx.ops.get(ctx.position).undoUpdate(ctx.board)
			ctx.ops.get(ctx.position).undoDraw(ctx)
			ctx.position = ctx.position - 1
		}
	}

	def forward() {
		if (ctx.position < ctx.ops.size - 1) {
			ctx.ops.get(ctx.position).updateBoard(ctx.board);
			ctx.ops.get(ctx.position).draw(ctx);
			ctx.ops.get(ctx.position).drawDetails(ctx)
			ctx.position = ctx.position + 1
		}
	}

	def fastForward() {
		while (ctx.position < ctx.ops.size) {
			forward
		}
	}
}
