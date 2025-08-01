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

import de.jost_net.JVerein.rmi.Buchung;
import de.jost_net.JVerein.rmi.Jahresabschluss;
import de.jost_net.JVerein.rmi.Spendenbescheinigung;
import de.jost_net.JVerein.util.JVDateFormatTTMMJJJJ;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.dialogs.YesNoDialog;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

/**
 * Loeschen einer Buchung.
 */
public class BuchungDeleteAction implements Action
{
  private boolean splitbuchung;

  public BuchungDeleteAction(boolean splitbuchung)
  {
    this.splitbuchung = splitbuchung;
  }

  @Override
  public void handleAction(Object context) throws ApplicationException
  {
    if (context == null
        || (!(context instanceof Buchung) && !(context instanceof Buchung[])))
    {
      throw new ApplicationException("Keine Buchung ausgew�hlt");
    }
    try
    {
      Buchung[] b = null;
      if (context instanceof Buchung)
      {
        b = new Buchung[1];
        b[0] = (Buchung) context;
      }
      else if (context instanceof Buchung[])
      {
        b = (Buchung[]) context;
      }
      if (b == null)
      {
        return;
      }
      if (b.length == 0)
      {
        return;
      }
      if (b[0].isNewObject())
      {
        return;
      }
      
      // Check ob einer der Buchungen
      // eine Spendenbescheinigung zugeordnet ist
      boolean spendenbescheinigung = false;
      for (Buchung bu : b)
      {
        if (bu.getSpendenbescheinigung() != null)
        {
          spendenbescheinigung = true;
          break;
        }
      }
      
      String text = "";
      if (!spendenbescheinigung)
      {
        text = "Wollen Sie diese Buchung" + (b.length > 1 ? "en" : "")
            + " wirklich l�schen?";
      }
      else
      {
        if (b.length == 1)
        {
         text = "Die Buchung geh�rt zu einer Spendenbescheinigung.\n"
              + "Sie k�nnen nur zusammen gel�scht werden.\n"
              + "Beide l�schen?";
        }
        else
        {
          text = "Mindestens eine Buchung geh�rt zu einer Spendenbescheinigung.\n"
              + "Sie k�nnen nur zusammen gel�scht werden.\n"
              + "Jeweils auch die Spendenbescheinigungen l�schen?";
        }
      }
      
      YesNoDialog d = new YesNoDialog(YesNoDialog.POSITION_CENTER);
      d.setTitle("Buchung" + (b.length > 1 ? "en" : "") + " l�schen");
      d.setText(text);
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
        Logger.error("Fehler beim L�schen der Buchung", e);
        return;
      }
      
      int count = 0;
      for (Buchung bu : b)
      {
        Jahresabschluss ja = bu.getJahresabschluss();
        if (ja != null)
        {
          throw new ApplicationException(String.format(
              "Buchung wurde bereits am %s von %s abgeschlossen.",
              new JVDateFormatTTMMJJJJ().format(ja.getDatum()), ja.getName()));
        }
        Spendenbescheinigung spb = bu.getSpendenbescheinigung();
        if(spb != null)
        {
          throw new ApplicationException(
              "Buchung kann nicht bearbeitet werden. Sie ist einer Spendenbescheinigung zugeordnet.");
        }
        if (bu.getSplitId() == null)
        {
          if (bu.getSpendenbescheinigung() != null)
            bu.getSpendenbescheinigung().delete();
          bu.delete();
          count++;
        }
        else if (splitbuchung)
        {
          bu.setDelete(true);
          count++;
        }
      }
      if (count > 0)
      {
        GUI.getStatusBar().setSuccessText(String.format(
            "%d Buchung" + (count != 1 ? "en" : "") + " gel�scht.", count));
      }
      else
      {
        GUI.getStatusBar().setErrorText("Keine Buchung gel�scht");
      }
    }
    catch (ApplicationException e)
    {
      GUI.getStatusBar().setErrorText(e.getLocalizedMessage());
    }
    catch (RemoteException e)
    {
      String fehler = "Fehler beim L�schen der Buchung.";
      GUI.getStatusBar().setErrorText(fehler);
      Logger.error(fehler, e);
    }
  }
}
