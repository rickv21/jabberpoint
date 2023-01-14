package com.nhlstenden.jabberpoint.style;

import java.awt.*;

public class LevelStyle {

    private final String fontName;
    private final int indent;
    private final Color color;
    private final Font font;
    private final int fontSize;
    private final int leading;

    public LevelStyle(String fontName, int indent, Color color, int points, int leading) {
        this.fontName = fontName;
        this.indent = indent;
        this.color = color;
        this.font = new Font(fontName, Font.BOLD, fontSize=points);
        this.leading = leading;
    }

    public String getFontName() {
        return fontName;
    }

    public int getIndent() {
        return indent;
    }

    public Color getColor() {
        return color;
    }

    public Font getFont(float scale) {
        return font.deriveFont(fontSize * scale);
    }

    public int getFontSize() {
        return fontSize;
    }

    public int getLeading() {
        return leading;
    }

    public String toString() {
        return "["+ indent + "," + color + "; " + fontSize + " on " + leading +"]";
    }
}
