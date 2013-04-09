package webboards.client.menu;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

@SuppressWarnings("all")
public class UIExtentions {
  public static Button operator_add(final Button a, final ClickHandler b) {
    a.addClickHandler(b);
    return a;
  }
}
