package webboards.client

import org.vectomatic.dom.svg.impl.SVGSVGElement
import webboards.client.data.GameCtx

class Menu {
	private SVGSVGElement svg
	private GameCtx ctx
	
	new(SVGSVGElement svg, GameCtx ctx) {
		this.svg=svg
		this.ctx=ctx		
	}
	
}