package com.nhlstenden.jabberpoint.style;

import java.awt.*;

/** <p>Contains data for the Style of a item-level.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2023/01/14 Rick Vinke
 */
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
