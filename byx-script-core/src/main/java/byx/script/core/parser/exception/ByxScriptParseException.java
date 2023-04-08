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
            return String.format("syntax error: \nrow: %d, col: %d: %s", cursor.row(), cursor.col(), msg);
        } else {
            return String.format("syntax: \n%s", msg);
        }
    }
}
