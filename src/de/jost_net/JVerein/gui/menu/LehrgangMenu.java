/**********************************************************************
 * Copyright (c) by Heiner Jostkleigrewe
 * This program is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without 
 *  even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See 
 *  the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, 
 * see <http://www.gnu.org/licenses/>.
 * 
 * heiner@jverein.de
 * www.jverein.de
 **********************************************************************/
package de.jost_net.JVerein.gui.menu;

import de.jost_net.JVerein.gui.action.LehrgangAction;
import de.jost_net.JVerein.gui.action.LehrgangDeleteAction;
import de.jost_net.JVerein.gui.action.MitgliedDetailAction;
import de.willuhn.jameica.gui.parts.CheckedContextMenuItem;
import de.willuhn.jameica.gui.parts.CheckedSingleContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;
import de.willuhn.jameica.gui.parts.ContextMenuItem;

/**
 * Kontext-Menu zu den Lehrg�ngen
 */
public class LehrgangMenu extends ContextMenu
{

  /**
   * Erzeugt ein Kontext-Menu fuer die Liste der Lehrg�nge.
   */
  public LehrgangMenu()
  {
    addItem(new CheckedSingleContextMenuItem("Bearbeiten", new LehrgangAction(null),
        "text-x-generic.png"));
    addItem(new CheckedContextMenuItem("L�schen", new LehrgangDeleteAction(),
        "user-trash-full.png"));
    addItem(ContextMenuItem.SEPARATOR);
    addItem(new CheckedSingleContextMenuItem("Mitglied anzeigen",
        new MitgliedDetailAction(), "user-friends.png"));
  }
}
