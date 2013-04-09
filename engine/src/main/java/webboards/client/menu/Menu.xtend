package webboards.client.menu

import com.google.gwt.user.client.ui.Button
import com.google.gwt.user.client.ui.FlexTable
import java.util.ArrayList
import org.vectomatic.dom.svg.impl.SVGSVGElement
import webboards.client.ClientEngine
import webboards.client.ClientOpRunner
import webboards.client.data.GameCtx

import static extension java.lang.Math.*
import static extension webboards.client.menu.UIExtentions.*

class Menu extends FlexTable {
	SVGSVGElement svg
	GameCtx ctx
	val buttons = new ArrayList<Button>()
	var columns = 1
	final Button menuButton
	
	ClientOpRunner runner

	new(SVGSVGElement svg, GameCtx ctx) {
		this.svg = svg
		this.ctx = ctx

		runner = new ClientOpRunner(ctx);
		menuButton = add("Hide menu")
		menuButton += [ toggleMenu ]
		add("Undo")
		(1 .. 10).forEach [
			add("Log " + it)
		]
		add("Undo") += [ undo ]
		add("Toggle units")
		add("Expand menu") += [
			columns = (if(columns == 1) calculateColumns else 1)
			realign()
		]
		columns = calculateColumns
		toggleMenu()
		realign()
	}

	private def calculateColumns() {
		buttons.size.sqrt.ceil as int
	}

	def toggleMenu() {
		menuButton.text = if (menuButton.text == "Show menu") {
			"Hide menu"
		} else {
			"Show menu"
		}
		buttons.filter[it !== menuButton].forEach [
			it.visible = !it.visible;
		]
	}

	def realign() {
		removeAllRows()
		(0 ..< buttons.size).forEach [
			val row = it / columns;
			val column = columns - it % columns
			val button = buttons.get(it)
			ClientEngine::log(row + "," + column + ": " + button.text)
			setWidget(row, column, button);
		]
	}

	def add(String string) {
		val btn = new Button(string)
		buttons += btn
		btn
	}
	
	def undo() {
		val last = ctx.ops.last
		if(last == null) 
			return null
		last.undoDraw(ctx);
		last.undoUpdate(ctx.board);
		ctx.ops.remove(ctx.ops.size - 1)
//		runner.service.undo()			
	}
}
