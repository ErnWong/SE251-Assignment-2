package tbs.server;

import java.util.List;
import java.util.ArrayList;

public class Dump {

  private List<DumpLine> _lines;
  private DumpLine _currentParent;

  public Dump() {
    _currentParent = new DumpLine(null, "TBS DUMP");
    _lines = new ArrayList<>();
    _lines.add(_currentParent);
  }

  public void add(String content) {
    DumpLine line = new DumpLine(_currentParent, content);
    _lines.add(line);
    _currentParent.noticeChild(line);
  }

  public void rewritePrevious(String content) {
    _currentParent.rewriteLastChild(content);
  }

  public void groupStart() {
    _currentParent = _lines.get(_lines.size() - 1);
  }

  public void groupEnd() {
    _currentParent.tidyChildren();

    DumpLine newParent = _currentParent.getParent();
    if (newParent == null) {
      throw new RuntimeException("Unbalanced dump groups");
    }

    _currentParent = newParent;
  }

  public List<String> render() {
    tidyLooseEnds();
    List<String> content = new ArrayList<>();
    for (DumpLine line : _lines) {
      content.addAll(line.render());
    }
    return content;
  }

  private void tidyLooseEnds() {
    _currentParent.tidyChildren();
    while (_currentParent != null) {
      _currentParent.setLastInGroup();
      _currentParent = _currentParent.getParent();
    }
  }

  private class DumpLine {
    private String _content;
    private boolean _isLastInGroup;
    private DumpLine _parent;
    private DumpLine _lastChild;

    public DumpLine(DumpLine parent, String content) {
      _parent = parent;
      _lastChild = null;
      _content = content;
    }

    public void noticeChild(DumpLine child) {
      _lastChild = child;
    }

    public void tidyChildren() {
      if (_lastChild != null) {
        _lastChild.setLastInGroup();
      }
    }

    public void setLastInGroup() {
      _isLastInGroup = true;
    }

    public void rewriteLastChild(String content) {
      if (_lastChild != null) {
        _lastChild._content = content;
      }
    }

    public DumpLine getParent() {
      return _parent;
    }

    public List<String> render() {
      List<String> strLines = new ArrayList<>();

      String strLine = renderAncestors();

      strLine += "   ";
      strLine += _isLastInGroup ? "└─" : "├─";
      strLine += " ";
      strLine += _content;

      strLines.add(strLine);

      if (_isLastInGroup && _lastChild == null) {
        strLines.add(renderAncestors());
      }

      return strLines;
    }

    public String renderAncestors() {
      String strLine = "";
      DumpLine trail = this._parent;
      while (trail != null) {
        strLine = (trail._isLastInGroup ? "    " : "   │") + strLine;
        trail = trail._parent;
      }
      return strLine;
    }
  }

}
