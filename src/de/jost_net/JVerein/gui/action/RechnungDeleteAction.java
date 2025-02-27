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
package de.jost_net.JVerein.gui.action;

import java.rmi.RemoteException;

import de.jost_net.JVerein.rmi.Rechnung;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.dialogs.YesNoDialog;
import de.willuhn.jameica.gui.parts.TablePart;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

public class RechnungDeleteAction implements Action
{
  @Override
  public void handleAction(Object context) throws ApplicationException
  {
    Rechnung[] res = null;
    if (context instanceof TablePart)
    {
      TablePart tp = (TablePart) context;
      context = tp.getSelection();
    }
    if (context == null)
    {
      throw new ApplicationException("Keine Rechnung ausgew�hlt");
    }
    else if (context instanceof Rechnung)
    {
      res = new Rechnung[] { (Rechnung) context};
    }
    else if (context instanceof Rechnung[])
    {
      res = (Rechnung[]) context;
    }
    else
    {
      return;
    }
    try
    {
      String mehrzahl = res.length > 1 ? "en" : "";
      YesNoDialog d = new YesNoDialog(YesNoDialog.POSITION_CENTER);
      d.setTitle("Rechnung" + mehrzahl + " l�schen");
      d.setText("Wollen Sie die Rechnung" + mehrzahl
          + " wirklich l�schen?");

      try
      {
        Boolean choice = (Boolean) d.open();
        if (!choice.booleanValue())
        {
          return;
        }
      }
      catch (Exception e)
      {
        Logger.error("Fehler beim L�schen der Rechnung" + mehrzahl,
            e);
        return;
      }
      for (Rechnung re : res)
      {
        if (re.isNewObject())
        {
          continue;
        }
        re.delete();
      }
      GUI.getStatusBar().setSuccessText(
          "Rechnung" + mehrzahl + "  gel�scht.");
    }
    catch (RemoteException e)
    {
      String fehler = "Fehler beim L�schen der Rechnung";
      GUI.getStatusBar().setErrorText(fehler);
      Logger.error(fehler, e);
    }
  }
}
