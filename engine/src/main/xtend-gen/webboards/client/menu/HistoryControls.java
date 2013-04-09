package webboards.client.menu;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import webboards.client.data.GameCtx;
import webboards.client.menu.UIExtentions;
import webboards.client.ops.Operation;

@SuppressWarnings("all")
public class HistoryControls extends Grid {
  private final GameCtx ctx;
  
  private int position = 1;
  
  public HistoryControls(final GameCtx ctx) {
    super(1, 3);
    this.ctx = ctx;
    Button _button = new Button("&lt;");
    final ClickHandler _function = new ClickHandler() {
        public void onClick(final ClickEvent it) {
          HistoryControls.this.back();
        }
      };
    Button _add = UIExtentions.operator_add(_button, _function);
    this.setWidget(1, 0, _add);
    Button _button_1 = new Button("&gt;");
    final ClickHandler _function_1 = new ClickHandler() {
        public void onClick(final ClickEvent it) {
          HistoryControls.this.forward();
        }
      };
    Button _add_1 = UIExtentions.operator_add(_button_1, _function_1);
    this.setWidget(1, 1, _add_1);
    Button _button_2 = new Button("&gt;&gt;");
    final ClickHandler _function_2 = new ClickHandler() {
        public void onClick(final ClickEvent it) {
          HistoryControls.this.fastForward();
        }
      };
    Button _add_2 = UIExtentions.operator_add(_button_2, _function_2);
    this.setWidget(1, 2, _add_2);
  }
  
  public boolean back() {
    int _minus = (-1);
    boolean _move = this.move(_minus);
    return _move;
  }
  
  public boolean forward() {
    boolean _move = this.move(1);
    return _move;
  }
  
  public void fastForward() {
    boolean _forward = this.forward();
    boolean _while = _forward;
    while (_while) {
      boolean _forward_1 = this.forward();
      _while = _forward_1;
    }
  }
  
  public boolean move(final int step) {
    boolean _xblockexpression = false;
    {
      int _size = this.ctx.ops.size();
      final int end = (_size - 1);
      boolean _xifexpression = false;
      boolean _and = false;
      boolean _lessEqualsThan = (0 <= this.position);
      if (!_lessEqualsThan) {
        _and = false;
      } else {
        boolean _lessThan = (this.position < end);
        _and = (_lessEqualsThan && _lessThan);
      }
      if (_and) {
        boolean _xblockexpression_1 = false;
        {
          int _plus = (this.position + step);
          this.position = _plus;
          Operation op = this.ctx.ops.get(this.position);
          op.draw(this.ctx);
          _xblockexpression_1 = (true);
        }
        _xifexpression = _xblockexpression_1;
      } else {
        _xifexpression = false;
      }
      _xblockexpression = (_xifexpression);
    }
    return _xblockexpression;
  }
}
