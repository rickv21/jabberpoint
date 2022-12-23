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
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
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
	protected static final String OPEN = "Open...";
	protected static final String PAGENR = "Page number?";
	protected static final String PREV = "Prev";
	protected static final String SAVE = "Save as...";
	protected static final String VIEW = "View";
	protected static final String SAVE_OVERWRITE_CONFIRM_TITLE= "Save confirmation";
	protected static final String SAVE_OVERWRITE_CONFIRM= "The file {0} already exists, do you want to overwrite it?";
	protected static final String FILE_NOT_EXIST = "The file {0} does not exist.";
	protected static final String SLIDE_INPUT_ERROR_TITLE = "Invalid slide input";
	protected static final String SLIDE_INPUT_ERROR = "The input must be more than 0 and not be more than the amount of slides";
	
	protected static final String TESTFILE = "testPresentation";
	protected static final String SAVEFILE = "savedPresentation";

	public MenuController(Frame frame, Presentation pres) {
		parent = frame;
		presentation = pres;
		MenuItem menuItem;
		Menu fileMenu = new Menu(FILE);
		fileMenu.add(mkOpenMenuItem());

		fileMenu.add(menuItem = mkMenuItem(NEW));
		menuItem.addActionListener(actionEvent -> {
			presentation.clear();
			parent.repaint();
		});

		fileMenu.add(mkSaveMenuItem());
		fileMenu.addSeparator();

		fileMenu.add(menuItem = mkMenuItem(EXIT));
		menuItem.addActionListener(actionEvent -> presentation.exit(0));
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

//Creating a menu-item
	public MenuItem mkMenuItem(String name) {
		return new MenuItem(name, new MenuShortcut(name.charAt(0)));
	}

	public MenuItem mkOpenMenuItem() {
		Menu menu = new Menu(OPEN);
		FileHandler fileHandler = new FileHandler();

		for(FileLoader loader : fileHandler.getFileLoaders()){
			MenuItem item = new MenuItem(" ." + loader.getExtension().toUpperCase(),
					new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar(loader.getShortcut())));
			item.addActionListener(e -> {
				presentation.clear();
				File file = new File(TESTFILE + "." + loader.getExtension());
				if(!file.exists()){
					JOptionPane.showMessageDialog(parent,
							FILE_NOT_EXIST.replace("{0}", TESTFILE + "." + loader.getExtension()), "IO Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				loader.loadPresentation(presentation, file);
				presentation.setSlideNumber(0);
				parent.repaint();
			});
			menu.add(item);
		}
		return menu;
	}

	public MenuItem mkSaveMenuItem() {
		Menu menu = new Menu(SAVE);
		FileHandler fileHandler = new FileHandler();

		for(FileSaver saver : fileHandler.getFileSavers()){
			MenuItem item = new MenuItem(" ." + saver.getExtension().toUpperCase(),
					new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar(saver.getShortcut())));

			item.addActionListener(e -> {
				try {
					File file = new File(SAVEFILE + "." + saver.getExtension());
					if (file.exists()) {
						int answer = JOptionPane.showConfirmDialog(parent, SAVE_OVERWRITE_CONFIRM.replace("{0}", SAVEFILE + "." + saver.getExtension()),
								SAVE_OVERWRITE_CONFIRM_TITLE, JOptionPane.YES_NO_OPTION);
						if (answer != JOptionPane.YES_OPTION) {
							return;
						}
					} else {
						file.createNewFile();
					}
					saver.savePresentation(presentation, file);
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(parent, "IO Error" + exc,
							"Save Error", JOptionPane.ERROR_MESSAGE);
				}
			});
			menu.add(item);
		}
		return menu;
	}
}
