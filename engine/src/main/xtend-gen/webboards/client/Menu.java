package webboards.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.vectomatic.dom.svg.impl.SVGSVGElement;
import webboards.client.data.GameCtx;

@SuppressWarnings("all")
public class Menu {
  private SVGSVGElement svg;
  
  private GameCtx ctx;
  
  private Button button = new Function0<Button>() {
    public Button apply() {
      Button _button = new Button("OK");
      return _button;
    }
  }.apply();
  
  public Menu(final SVGSVGElement svg, final GameCtx ctx) {
    this.svg = svg;
    this.ctx = ctx;
  }
  
  public void ok() {
    Window.alert("xtend");
  }
}
