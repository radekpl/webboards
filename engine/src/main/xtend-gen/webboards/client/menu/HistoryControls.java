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
  
  public HistoryControls(final GameCtx ctx) {
    super(1, 4);
    this.ctx = ctx;
    Button _button = new Button("&lt;");
    final ClickHandler _function = new ClickHandler() {
        public void onClick(final ClickEvent it) {
          HistoryControls.this.back();
        }
      };
    Button _add = UIExtentions.operator_add(_button, _function);
    this.setWidget(0, 0, _add);
    Button _button_1 = new Button("[]");
    final ClickHandler _function_1 = new ClickHandler() {
        public void onClick(final ClickEvent it) {
          HistoryControls.this.reset();
        }
      };
    Button _add_1 = UIExtentions.operator_add(_button_1, _function_1);
    this.setWidget(0, 1, _add_1);
    Button _button_2 = new Button("&gt;");
    final ClickHandler _function_2 = new ClickHandler() {
        public void onClick(final ClickEvent it) {
          HistoryControls.this.forward();
        }
      };
    Button _add_2 = UIExtentions.operator_add(_button_2, _function_2);
    this.setWidget(0, 2, _add_2);
    Button _button_3 = new Button("&gt;&gt;");
    final ClickHandler _function_3 = new ClickHandler() {
        public void onClick(final ClickEvent it) {
          HistoryControls.this.fastForward();
        }
      };
    Button _add_3 = UIExtentions.operator_add(_button_3, _function_3);
    this.setWidget(0, 3, _add_3);
  }
  
  public int reset() {
    int _xblockexpression = (int) 0;
    {
      int _size = this.ctx.ops.size();
      boolean _lessThan = (this.ctx.position < _size);
      boolean _while = _lessThan;
      while (_while) {
        int _size_1 = this.ctx.ops.size();
        int _minus = (_size_1 - 1);
        this.ctx.ops.remove(_minus);
        int _size_2 = this.ctx.ops.size();
        boolean _lessThan_1 = (this.ctx.position < _size_2);
        _while = _lessThan_1;
      }
      int _minus = (this.ctx.position - 1);
      int _position = this.ctx.position = _minus;
      _xblockexpression = (_position);
    }
    return _xblockexpression;
  }
  
  public Integer back() {
    Integer _xifexpression = null;
    boolean _greaterThan = (this.ctx.position > 0);
    if (_greaterThan) {
      int _xblockexpression = (int) 0;
      {
        Operation _get = this.ctx.ops.get(this.ctx.position);
        _get.undoUpdate(this.ctx.board);
        Operation _get_1 = this.ctx.ops.get(this.ctx.position);
        _get_1.undoDraw(this.ctx);
        int _minus = (this.ctx.position - 1);
        int _position = this.ctx.position = _minus;
        _xblockexpression = (_position);
      }
      _xifexpression = Integer.valueOf(_xblockexpression);
    }
    return _xifexpression;
  }
  
  public Integer forward() {
    Integer _xifexpression = null;
    int _size = this.ctx.ops.size();
    int _minus = (_size - 1);
    boolean _lessThan = (this.ctx.position < _minus);
    if (_lessThan) {
      int _xblockexpression = (int) 0;
      {
        Operation _get = this.ctx.ops.get(this.ctx.position);
        _get.updateBoard(this.ctx.board);
        Operation _get_1 = this.ctx.ops.get(this.ctx.position);
        _get_1.draw(this.ctx);
        Operation _get_2 = this.ctx.ops.get(this.ctx.position);
        _get_2.drawDetails(this.ctx);
        int _plus = (this.ctx.position + 1);
        int _position = this.ctx.position = _plus;
        _xblockexpression = (_position);
      }
      _xifexpression = Integer.valueOf(_xblockexpression);
    }
    return _xifexpression;
  }
  
  public void fastForward() {
    int _size = this.ctx.ops.size();
    boolean _lessThan = (this.ctx.position < _size);
    boolean _while = _lessThan;
    while (_while) {
      this.forward();
      int _size_1 = this.ctx.ops.size();
      boolean _lessThan_1 = (this.ctx.position < _size_1);
      _while = _lessThan_1;
    }
  }
}
