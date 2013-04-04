package webboards.client

import com.google.gwt.user.client.ui.Button
import org.vectomatic.dom.svg.impl.SVGSVGElement
import webboards.client.data.GameCtx
import com.google.gwt.user.client.Window

class Menu {
	private SVGSVGElement svg
	private GameCtx ctx
	var button = new Button("OK")
	
	new(SVGSVGElement svg, GameCtx ctx) {
		this.svg=svg
		this.ctx=ctx
	}
	def ok() {
		Window::alert("xtend");
	}
}