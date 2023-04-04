package byx.script.core.parser.exception;

import byx.script.core.parser.parserc.Cursor;

/**
 * ByxScript解析异常
 */
public class ByxScriptParseException extends FastException {
    private final Cursor cursor;
    private final String msg;

    public ByxScriptParseException(Cursor cursor, String msg) {
        this.cursor = cursor;
        this.msg = msg;
    }

    public ByxScriptParseException(String msg) {
        this(null, msg);
    }

    @Override
    public String getMessage() {
        if (cursor != null) {
            return String.format("语法解析错误：\n第%d行，第%d列：%s", cursor.row(), cursor.col(), msg);
        } else {
            return String.format("语法解析错误：\n%s", msg);
        }
    }
}
