package webboards.client.menu

import com.google.gwt.event.dom.client.ClickHandler
import com.google.gwt.user.client.ui.Button

class UIExtentions {
	
	static def operator_add(Button a, ClickHandler b) {
		a.addClickHandler(b)	
		return a	
	}
	
}