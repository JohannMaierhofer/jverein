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
package de.jost_net.JVerein.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import de.jost_net.JVerein.server.PseudoDBObject;
import de.willuhn.util.ApplicationException;

/**
 * Interface f�r den Export der Salden
 */
public interface ISaldoExport
{
  public void export(ArrayList<PseudoDBObject> zeilen, File file, Date von,
      Date bis) throws ApplicationException;
}
