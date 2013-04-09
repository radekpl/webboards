package webboards.client.menu;

import com.google.common.base.Objects;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import java.util.ArrayList;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.vectomatic.dom.svg.impl.SVGSVGElement;
import webboards.client.ClientEngine;
import webboards.client.ClientOpRunner;
import webboards.client.data.GameCtx;
import webboards.client.menu.UIExtentions;
import webboards.client.ops.Operation;

@SuppressWarnings("all")
public class Menu extends FlexTable {
  private SVGSVGElement svg;
  
  private GameCtx ctx;
  
  private final ArrayList<Button> buttons = new Function0<ArrayList<Button>>() {
    public ArrayList<Button> apply() {
      ArrayList<Button> _arrayList = new ArrayList<Button>();
      return _arrayList;
    }
  }.apply();
  
  private int columns = 1;
  
  private final Button menuButton;
  
  private ClientOpRunner runner;
  
  public Menu(final SVGSVGElement svg, final GameCtx ctx) {
    this.svg = svg;
    this.ctx = ctx;
    ClientOpRunner _clientOpRunner = new ClientOpRunner(ctx);
    this.runner = _clientOpRunner;
    Button _add = this.add("Hide menu");
    this.menuButton = _add;
    final ClickHandler _function = new ClickHandler() {
        public void onClick(final ClickEvent it) {
          Menu.this.toggleMenu();
        }
      };
    UIExtentions.operator_add(
      this.menuButton, _function);
    this.add("Undo");
    IntegerRange _upTo = new IntegerRange(1, 10);
    final Procedure1<Integer> _function_1 = new Procedure1<Integer>() {
        public void apply(final Integer it) {
          String _plus = ("Log " + it);
          Menu.this.add(_plus);
        }
      };
    IterableExtensions.<Integer>forEach(_upTo, _function_1);
    Button _add_1 = this.add("Undo");
    final ClickHandler _function_2 = new ClickHandler() {
        public void onClick(final ClickEvent it) {
          Menu.this.undo();
        }
      };
    UIExtentions.operator_add(_add_1, _function_2);
    this.add("Toggle units");
    Button _add_2 = this.add("Expand menu");
    final ClickHandler _function_3 = new ClickHandler() {
        public void onClick(final ClickEvent it) {
          int _xifexpression = (int) 0;
          boolean _equals = (Menu.this.columns == 1);
          if (_equals) {
            int _calculateColumns = Menu.this.calculateColumns();
            _xifexpression = _calculateColumns;
          } else {
            _xifexpression = 1;
          }
          Menu.this.columns = _xifexpression;
          Menu.this.realign();
        }
      };
    UIExtentions.operator_add(_add_2, _function_3);
    int _calculateColumns = this.calculateColumns();
    this.columns = _calculateColumns;
    this.toggleMenu();
    this.realign();
  }
  
  private int calculateColumns() {
    int _size = this.buttons.size();
    double _sqrt = Math.sqrt(_size);
    double _ceil = Math.ceil(_sqrt);
    return ((int) _ceil);
  }
  
  public void toggleMenu() {
    String _xifexpression = null;
    String _text = this.menuButton.getText();
    boolean _equals = Objects.equal(_text, "Show menu");
    if (_equals) {
      _xifexpression = "Hide menu";
    } else {
      _xifexpression = "Show menu";
    }
    this.menuButton.setText(_xifexpression);
    final Function1<Button,Boolean> _function = new Function1<Button,Boolean>() {
        public Boolean apply(final Button it) {
          boolean _tripleNotEquals = (it != Menu.this.menuButton);
          return Boolean.valueOf(_tripleNotEquals);
        }
      };
    Iterable<Button> _filter = IterableExtensions.<Button>filter(this.buttons, _function);
    final Procedure1<Button> _function_1 = new Procedure1<Button>() {
        public void apply(final Button it) {
          boolean _isVisible = it.isVisible();
          boolean _not = (!_isVisible);
          it.setVisible(_not);
        }
      };
    IterableExtensions.<Button>forEach(_filter, _function_1);
  }
  
  public void realign() {
    this.removeAllRows();
    int _size = this.buttons.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    final Procedure1<Integer> _function = new Procedure1<Integer>() {
        public void apply(final Integer it) {
          final int row = ((it).intValue() / Menu.this.columns);
          int _modulo = ((it).intValue() % Menu.this.columns);
          final int column = (Menu.this.columns - _modulo);
          final Button button = Menu.this.buttons.get((it).intValue());
          String _plus = (Integer.valueOf(row) + ",");
          String _plus_1 = (_plus + Integer.valueOf(column));
          String _plus_2 = (_plus_1 + ": ");
          String _text = button.getText();
          String _plus_3 = (_plus_2 + _text);
          ClientEngine.log(_plus_3);
          Menu.this.setWidget(row, column, button);
        }
      };
    IterableExtensions.<Integer>forEach(_doubleDotLessThan, _function);
  }
  
  public Button add(final String string) {
    Button _xblockexpression = null;
    {
      Button _button = new Button(string);
      final Button btn = _button;
      this.buttons.add(btn);
      _xblockexpression = (btn);
    }
    return _xblockexpression;
  }
  
  public Operation undo() {
    Operation _xblockexpression = null;
    {
      final Operation last = IterableExtensions.<Operation>last(this.ctx.ops);
      boolean _equals = Objects.equal(last, null);
      if (_equals) {
        return null;
      }
      last.undoDraw(this.ctx);
      last.undoUpdate(this.ctx.board);
      int _size = this.ctx.ops.size();
      int _minus = (_size - 1);
      Operation _remove = this.ctx.ops.remove(_minus);
      _xblockexpression = (_remove);
    }
    return _xblockexpression;
  }
}
