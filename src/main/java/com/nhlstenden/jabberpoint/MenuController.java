package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.files.FileHandler;
import com.nhlstenden.jabberpoint.files.loading.FileLoader;
import com.nhlstenden.jabberpoint.files.saving.FileSaver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/** <p>The controller for the menu</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2023/01/14 Rick Vinke
 */
public class MenuController extends MenuBar {
	
	private final Frame parent; //The frame, only used as parent for the Dialogs
	private final Presentation presentation; //Commands are given to the presentation
	
	private static final long serialVersionUID = 227L;
	
	protected static final String ABOUT = "About";
	protected static final String FILE = "File";
	protected static final String EXIT = "Exit";
	protected static final String GOTO = "Go to";
	protected static final String HELP = "Help";
	protected static final String NEW = "New";
	protected static final String NEXT = "Next";
	protected static final String OPEN = "Open";
	protected static final String PAGENR = "Page number?";
	protected static final String PREV = "Prev";
	protected static final String SAVE = "Save";
	protected static final String VIEW = "View";
	protected static final String SLIDE_INPUT_ERROR_TITLE = "Invalid slide input";
	protected static final String SLIDE_INPUT_ERROR = "The input must be more than 0 and not be more than the amount of slides";
	
	protected static final String TESTFILE = "testPresentation";
	protected static final String SAVEFILE = "savedPresentation";

	/**
	 * Handles the Menu's on top of the screen.
	 *
	 * @param frame The frame of the application.
	 * @param pres The current presentation.
	 */
	public MenuController(SlideViewerFrame frame, Presentation pres) {
		parent = frame;
		presentation = pres;
		MenuItem menuItem;
		Menu fileMenu = new Menu(FILE);
		fileMenu.add(mkOpenMenuItem(fileMenu));

		fileMenu.add(menuItem = mkMenuItem(NEW));
		menuItem.addActionListener(actionEvent -> {
			presentation.clear();
			parent.repaint();
		});

		fileMenu.add(mkSaveMenuItem(fileMenu));
		fileMenu.addSeparator();

		fileMenu.add(menuItem = mkMenuItem(EXIT));
		menuItem.addActionListener(actionEvent -> frame.exit(0));
		add(fileMenu);

		Menu viewMenu = new Menu(VIEW);
		viewMenu.add(menuItem = mkMenuItem(NEXT));
		menuItem.addActionListener(actionEvent -> presentation.nextSlide());

		viewMenu.add(menuItem = mkMenuItem(PREV));
		menuItem.addActionListener(actionEvent -> presentation.prevSlide());

		viewMenu.add(menuItem = mkMenuItem(GOTO));
		menuItem.addActionListener(actionEvent -> {
			try {
				String pageNumberStr = JOptionPane.showInputDialog(PAGENR);
				int pageNumber = Integer.parseInt(pageNumberStr);
				if(pageNumber < 0 || pageNumber > presentation.getSize()){
					JOptionPane.showMessageDialog(parent,
							SLIDE_INPUT_ERROR, SLIDE_INPUT_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
					return;
				}
				presentation.setSlideNumber(pageNumber - 1);
			} catch (NumberFormatException e){
				JOptionPane.showMessageDialog(parent,
						SLIDE_INPUT_ERROR, SLIDE_INPUT_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		});

		add(viewMenu);
		Menu helpMenu = new Menu(HELP);
		helpMenu.add(menuItem = mkMenuItem(ABOUT));

		menuItem.addActionListener(actionEvent -> AboutBox.show(parent));
		setHelpMenu(helpMenu);		//Needed for portability (Motif, etc.).
	}

	/**
	 * Creates a normal MenuItem.
	 *
	 * @param name The text of the Menu Item.
	 * @return The newly created MenuItem.
	 */
	public MenuItem mkMenuItem(String name) {
		return new MenuItem(name, new MenuShortcut(name.charAt(0)));
	}

	/**
	 * Creates a MenuItem with a file open action.
	 *
	 * @param fileMenu The existing file menu dropdown.
	 * @return The newly created MenuItem.
	 */
	public MenuItem mkOpenMenuItem(Menu fileMenu) {
		Menu menu;
		FileHandler fileHandler = new FileHandler();

		//Different behavior if more than 1 loader exists.
		if(fileHandler.getFileLoaders().size() > 1){
			menu = new Menu(OPEN);
		} else {
			menu = fileMenu;
		}

		//Loop through all loaders.
		for(FileLoader loader : fileHandler.getFileLoaders()){
			//Different text if more than 1 loader exists.
			MenuItem item = new MenuItem(fileHandler.getFileSavers().size() > 1 ? " ." + loader.getExtension().toUpperCase() : OPEN,
					new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar(loader.getShortcut())));

			//Add loader action.
			item.addActionListener(e -> {
				presentation.clear();
				File file = new File(TESTFILE + "." + loader.getExtension());

				loader.loadPresentation(presentation, file);
				presentation.setSlideNumber(0);
				parent.repaint();
			});
			menu.add(item);
		}
		return menu;
	}

	/**
	 * Creates a MenuItem with a file save action.
	 *
	 * @param fileMenu The existing file menu dropdown.
	 * @return The newly created MenuItem.
	 */
	public MenuItem mkSaveMenuItem(Menu fileMenu) {
		Menu menu;
		FileHandler fileHandler = new FileHandler();

		//Different behavior if more than 1 saver exists.
		if(fileHandler.getFileSavers().size() > 1){
			menu = new Menu(SAVE);
		} else {
			menu = fileMenu;
		}

		//Loop through all savers.
		for(FileSaver saver : fileHandler.getFileSavers()){
			//Different text if more than 1 saver exists.
			MenuItem item = new MenuItem(fileHandler.getFileSavers().size() > 1 ? " ." + saver.getExtension().toUpperCase() : SAVE,
					new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar(saver.getShortcut())));

			//Add save action.
			item.addActionListener(e -> {
				try {
					File file = new File(SAVEFILE + "." + saver.getExtension());
					if (!file.exists()) {
						file.createNewFile();
					}
					saver.savePresentation(presentation, file);
				} catch (IOException exc) {
					exc.printStackTrace();
				}
			});
			menu.add(item);
		}
		return menu;
	}
}
