package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.style.LevelStyle;
import com.nhlstenden.jabberpoint.style.Style;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

/** <p>A slide. This class has drawing functionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2023/01/14 Rick Vinke
 */
public class Slide {
	public final static int WIDTH = 1200;
	public final static int HEIGHT = 800;
	protected String title; //The title is kept separately
	protected List<SlideItem> items; //The SlideItems are kept in a ArrayList.
	protected Style style;

	public Slide(Style style) {
		items = new ArrayList<>();
		this.style = style;
	}

	/**
	 * Adds a SlideItem.
	 * @param anItem The new SlideItem.
	 */
	public void append(SlideItem anItem) {
		items.add(anItem);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String newTitle) {
		title = newTitle;
	}

	/**
	 * Create a TextItem out of a String and adds it to this Slide.
	 *
	 * @param level The level of the TextItem.
	 * @param message The text of the new TextItem.
	 */
	public void append(int level, String message) {
		append(new TextItem(level, message));
	}

	public SlideItem getSlideItem(int number) {
		return items.get(number);
	}

	/**
	 * Return all the SlideItems in a ArrayList.
	 */
	public List<SlideItem> getSlideItems() {
		return items;
	}

	public int getSize() {
		return items.size();
	}

	/**
	 * Draws the slide
	 *
	 * @param g The graphics object for the Side.
	 * @param area The rendering area for the Slide.
	 * @param view The observer for the Slide.
	 */
	public void draw(Graphics g, Rectangle area, ImageObserver view) {
		float scale = getScale(area);
	    int y = area.y;

		//The title is treated separately
	    SlideItem slideItem = new TextItem(0, getTitle());
	    LevelStyle levelStyle = style.getStyle(slideItem.getLevel());
	    slideItem.draw(area.x, y, scale, g, levelStyle, view);
	    y += slideItem.getBoundingBox(g, view, scale, levelStyle).height;

	    for (int number = 0; number < getSize(); number++) {
	      slideItem = getSlideItems().get(number);
		  levelStyle = style.getStyle(slideItem.getLevel());
	      slideItem.draw(area.x, y, scale, g, levelStyle, view);
	      y += slideItem.getBoundingBox(g, view, scale, levelStyle).height;
	    }
	  }

	/**
	 * Returns the scale to draw a slide
	 *
	 * @param area The area for the Slide.
	 *
	 * @return The scale.
	 */
	private float getScale(Rectangle area) {
		return Math.min(((float)area.width) / ((float)WIDTH), ((float)area.height) / ((float)HEIGHT));
	}
}
