/**********************************************************************
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
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
package de.jost_net.JVerein.util;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bsh.EvalError;
import bsh.Interpreter;
import de.jost_net.JVerein.Einstellungen;
import de.jost_net.JVerein.rmi.LeseFeld;
import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.logging.Logger;

/**
 * Bietet Funktionalit�t rund um Lesefelder.
 * 
 * Lesefelder werden durch Skripte konfiguriert. Jedes Skript kann f�r jedes
 * Mitglied aufgerufen werden und erstellt so f�r jedes Mitglied ein Feld, das
 * nur gelesen werden kann. Skripte k�nnen auf alle Daten des jeweiligen
 * Mitgliedes zugreifen und diese weiterverarbeiten.
 * 
 * Beispiel: Skript: String mein_name = mitglied_vorname;
 * return(mein_name.substring(0, 3)); Ausgabe f�r Mitlied... Beate --> Bea
 * Christian --> Chr
 * 
 * Die Daten des jeweiligen Mitgliedes m�ssen gesetzt werden mit: setMap()
 * Au�erdem m�ssen die aktuellen Lesefelder-Skripte geladen werden. Dies
 * geschieht mit: setLesefelderDefinitions() oder
 * setLesefelderDefinitionsFromDatabase()
 * 
 * LesefeldAuswerter l = new LesefeldAuswerter();
 * l.setLesefelderDefinitionsFromDatabase(); l.setMap(map); lesefelderMap =
 * l.getLesefelderMap();
 * 
 * @author Julian
 * 
 */
public class LesefeldAuswerter
{

  // BeanShell-Interpreter.
  Interpreter bsh;

  List<LeseFeld> lesefelder;

  // Name des aktuellen Mitgliedes f�r Debug-Zwecke:
  String vornamename = "";

  /**
   * Legt eine Instanz vom Bean-Shell-Interpreter an.
   */
  public LesefeldAuswerter()
  {
    bsh = new Interpreter();
    lesefelder = new ArrayList<LeseFeld>();
  }

  /**
   * Nimmt eine map mit Mitgliedsdaten entgegen und macht es dem Interpreter
   * verf�gbar. map enth�lt Zuordnung von Variablen-Name zu Variablen-Inhalt.
   * map kann direkt von einem Mitglied-Objekt �ber die Funktion getMap()
   * erhalten werden.
   * 
   * @param map
   *          map mit Mitgliedsdaten
   */
  public void setMap(Map<String, Object> map)
  {
    vornamename = (String) map.get("mitglied_vornamename");

    // Mache alle Variablen aus map in BeanScript verf�gbar.
    // '.', '-' und ' ' werden ersetzt durch '_'.
    for (String key : map.keySet())
    {

      // TODO: gibt es noch mehr Zeichen, die ersetzt werden m�ssen?
      String keyNormalized = key.replace("-", "_").replace(".", "_")
          .replace(" ", "_");

      try
      {
        bsh.set(keyNormalized, map.get(key));
      }
      catch (EvalError e)
      {
        Logger.error(
            "Interner Fehler beim Auswerten eines Skriptes: \""
                + e.getMessage() + "\".", e);
        e.printStackTrace();
      }
    }

    // DEBUG: Zeige alle gesetzten Variablen.
    String[] vars = bsh.getNameSpace().getVariableNames();
    try
    {
      for (int i = 0; i < vars.length; i++)
      {
        if (vars[i].compareTo("bsh") == 0)
          continue;
        String s2 = "\"" + vars[i] + ":\" + " + vars[i] + ";";
        Object o = bsh.eval(s2);
        Logger.debug("Skript-Variable: " + o);
      }
    }
    catch (EvalError e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Liest alle Lesefelder aus Datenbank um sie sp�ter wiederzuverwenden (z.B.
   * von getLesefelderMap()).
   * 
   * @throws RemoteException
   */
  public void setLesefelderDefinitionsFromDatabase() throws RemoteException
  {
    DBIterator itlesefelder = Einstellungen.getDBService().createList(
        LeseFeld.class);
    while (itlesefelder.hasNext())
    {
      LeseFeld lesefeld = (LeseFeld) itlesefelder.next();
      lesefelder.add(lesefeld);
    }
  }

  public void setLesefelderDefinitions(List<LeseFeld> list)
  {
    lesefelder = list;
  }

  /**
   * Evaluiert alle Lesefelder-Definitionen, die mit
   * readLesefelderDefinitionsFromDatabase() oder setLesefelderDefinitions()
   * gesetzt wurden. Dabei werden alle Mitglieder-Variablen ber�cksichtig, die
   * vorher mit setMap() gesetzt wurden. Evaluation-Ausnahmen (EvalError) werden
   * abgefangen und ignoriert (kein Eintrag in R�ckgabe map)
   * 
   * @return Liste von Mitglieder-Lesefelder-Variablen
   * @throws RemoteException
   */
  public Map<String, Object> getLesefelderMap() throws RemoteException
  {
    Map<String, Object> map = new HashMap<String, Object>();
    for (LeseFeld lesefeld : lesefelder)
    {

      String script = lesefeld.getScript();

      Object scriptResult = null;
      try
      {
        scriptResult = bsh.eval(script);
        String val = scriptResult == null ? "" : scriptResult.toString();
        map.put("mitglied_lesefeld_" + lesefeld.getBezeichnung(), val);
      }
      catch (EvalError e)
      {
        Logger.error("Fehler beim Auswerten des Skriptes: \"" + e.getMessage()
            + "\".", e);
        e.printStackTrace();
      }

    }
    Logger.debug("Lesefeld-Variablen f�r Mitglied " + vornamename + ":");
    for (String key : map.keySet())
    {
      Logger.debug(key + "=" + map.get(key));
    }
    return map;
  }

  /**
   * Evaluiert Skript script. Dabei werden alle Mitglieder-Variablen
   * ber�cksichtig, die vorher mit setMap() gesetzt wurden.
   * 
   * @param script
   *          Auszuwertendes Skript.
   * @return Ergebnis der Skript-Ausf�hrung
   * @throws EvalError
   */
  public Object eval(String script) throws EvalError
  {
    return bsh.eval(script);
  }
}