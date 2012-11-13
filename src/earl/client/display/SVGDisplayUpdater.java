package earl.client.display;

import java.util.ArrayList;
import java.util.Collection;

import org.vectomatic.dom.svg.impl.SVGElement;
import org.vectomatic.dom.svg.impl.SVGImageElement;

import com.google.gwt.dom.client.Element;

import earl.client.data.Counter;
import earl.client.data.Hex;

public class SVGDisplayUpdater implements DisplayChangeListener {
	private final SVGDisplay display;

	public SVGDisplayUpdater(SVGDisplay display) {
		this.display = display;
	}

	@Override
	public void counterMoved(Counter counter, Hex from, Hex to) {
		display.alignStack(to);
	}

	@Override
	public void counterChanged(Counter piece) {
		SVGImageElement c = (SVGImageElement) display.svg.getElementById(piece.getId());
		c.getHref().setBaseVal("units/"+piece.getState());
	}
	
	@Override
	public void alignStack(Hex position) {
		display.alignStack(position);
	}
	
	@Override
	public void showStackSelection(Hex hex) {
		SVGElement c = (SVGElement) display.svg.getElementById(hex.getId());
		Collection<Counter> stack = hex.getStack();
		Collection<SVGElement> counters = new ArrayList<SVGElement>(stack.size());
		for (Counter counter : stack) {
			SVGElement e = (SVGElement) display.svg.getElementById(counter.getId());
			counters.add(e);
		}
		display.showStackSelection(c, counters);
	}
	
	@Override
	public void counterDeselected(Counter selectedPiece) {
		Element c = display.svg.getElementById(selectedPiece.getId());
		c.setAttribute("style", "opacity: 1");
	}

	@Override
	public void counterSelected(Counter selectedPiece) {
		SVGElement c = (SVGElement)display.svg.getElementById(selectedPiece.getId());
		c.setAttribute("style", "opacity: 0.8");
		display.bringToTop(c);
	}
}
