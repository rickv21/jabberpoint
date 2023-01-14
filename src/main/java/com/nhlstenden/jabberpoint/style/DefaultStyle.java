package com.nhlstenden.jabberpoint.style;

import java.awt.*;

/** <p>Style stands for Indent, Color, Font and Leading.</p>
 * <p>The link between a style number and a item level is hard-linked:
 * in Slide the style is grabbed for an item
 * with a style number the same as the item level.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class DefaultStyle extends Style{

	public DefaultStyle(){
		super();
		styles.put(0, new LevelStyle("Helvetica", 0, Color.red,   48, 20));	// style voor item-level 0
		styles.put(1, new LevelStyle("Helvetica",20, Color.blue,  40, 10));	// style voor item-level 1
		styles.put(2, new LevelStyle("Helvetica",50, Color.black, 36, 10));	// style voor item-level 2
		styles.put(3, new LevelStyle("Helvetica",70, Color.black, 30, 10));	// style voor item-level 3
		styles.put(4, new LevelStyle("Helvetica",90, Color.black, 24, 10));	// style voor item-level 4
	}
}
