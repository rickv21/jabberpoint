package com.nhlstenden.jabberpoint.files.loading;

import com.nhlstenden.jabberpoint.*;
import com.nhlstenden.jabberpoint.style.DefaultStyle;
import com.nhlstenden.jabberpoint.style.Style;

import java.io.File;

/** <p>The loader for the Demo presentation.<p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2023/01/14 Rick Vinke
 */
public class DemoLoader extends FileLoader {

    @Override
    public void loadPresentation(Presentation presentation, File file) {
        presentation.setTitle("Demo Presentation");
        Style style = new DefaultStyle();
        Slide slide;
        slide = new Slide(style);
        slide.setTitle("JabberPoint");
        slide.append(1, "The Java prestentation tool");
        slide.append(2, "Copyright (c) 1996-2000: Ian Darwin");
        slide.append(2, "Copyright (c) 2000-now:");
        slide.append(2, "Gert Florijn and Sylvia Stuurman");
        slide.append(4, "Calling Jabberpoint without a filename");
        slide.append(4, "will show this presentation");
        slide.append(1, "Navigate:");
        slide.append(3, "Next slide: PgDn or Enter");
        slide.append(3, "Previous slide: PgUp or up-arrow");
        slide.append(3, "Quit: q or Q");
        presentation.append(slide);

        slide = new Slide(style);
        slide.setTitle("Demonstration of levels and styles");
        slide.append(1, "Level 1");
        slide.append(2, "Level 2");
        slide.append(1, "Again level 1");
        slide.append(1, "Level 1 has style number 1");
        slide.append(2, "Level 2 has style number 2");
        slide.append(3, "This is how level 3 looks like");
        slide.append(4, "And this is level 4");
        presentation.append(slide);

        slide = new Slide(style);
        slide.setTitle("The third slide");
        slide.append(1, "To open a new presentation,");
        slide.append(2, "use File->Open from the menu.");
        slide.append(1, " ");
        slide.append(1, "This is the end of the presentation.");
        slide.append(new BitmapItem(1, "JabberPoint.jpg"));
        presentation.append(slide);
    }

    @Override
    public String getExtension() {
        return null;
    }

    @Override
    public char getShortcut() {
        return 0;
    }
}
