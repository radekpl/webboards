package webboards.client;

import org.vectomatic.dom.svg.impl.SVGSVGElement;
import webboards.client.data.GameCtx;

@SuppressWarnings("all")
public class Menu {
  private SVGSVGElement svg;
  
  private GameCtx ctx;
  
  public Menu(final SVGSVGElement svg, final GameCtx ctx) {
    this.svg = svg;
    this.ctx = ctx;
  }
}
