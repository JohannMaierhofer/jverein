/**********************************************************************
 * JVerein - Mitgliederverwaltung und einfache Buchhaltung f�r Vereine
 * Copyright (c) by Heiner Jostkleigrewe
 * Copyright (c) 2015 by Thomas Hooge
 * Main Project: heiner@jverein.dem  http://www.jverein.de/
 * Module Author: thomas@hoogi.de, http://www.hoogi.de/
 *
 * This file is part of JVerein.
 *
 * JVerein is free software: you can redistribute it and/or modify 
 * it under the terms of the  GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JVerein is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 **********************************************************************/
package de.jost_net.JVerein.server.DDLTool.Updates;

import java.sql.Connection;

import de.jost_net.JVerein.server.DDLTool.AbstractDDLUpdate;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.ProgressMonitor;

public class Update0439 extends AbstractDDLUpdate
{
  public Update0439(String driver, ProgressMonitor monitor, Connection conn)
  {
    super(driver, monitor, conn);
  }

  @Override
  public void run() throws ApplicationException
  {
    // Diese Migration ist f�r den Fall, dass die korrigierte Migration
    // Update0430 nicht ausgef�rt wird weil schon mit der alten Version 
    // migriert wurde z.B. mit einem Nightly Build.
    // Weil wegen gel�schter Buchungsklassen die Integrit�t verletzt sein 
    // k�nnte, muss der Foreign Key mit NOCHECK erzeugt werden

    createForeignKeyIfNotExistsNocheck("fkKonto1", "konto",
        "buchungsart", "buchungsart", "id", "SET NULL", "NO ACTION");

  }
}