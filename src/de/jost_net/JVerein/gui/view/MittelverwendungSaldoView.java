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
package de.jost_net.JVerein.gui.view;

import de.jost_net.JVerein.gui.action.DokumentationAction;
import de.jost_net.JVerein.gui.control.MittelverwendungSaldoControl;
import de.jost_net.JVerein.gui.parts.QuickAccessPart;
import de.jost_net.JVerein.gui.parts.VonBisPart;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.parts.ButtonArea;
import de.willuhn.jameica.gui.util.LabelGroup;

public class MittelverwendungSaldoView extends AbstractView
{
  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Mittelverwendungssaldo");

    final MittelverwendungSaldoControl control = new MittelverwendungSaldoControl(
        this);
  
    VonBisPart vpart = new VonBisPart(control, true);
    vpart.paint(this.getParent());
    
    QuickAccessPart qpart = new QuickAccessPart(control, true);
    qpart.paint(this.getParent());

    LabelGroup group = new LabelGroup(getParent(), "Saldo", true);
    group.addPart(control.getSaldoList());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.MITTELVERWENDUNGSALDO, false, "question-circle.png");
    buttons.addButton(control.getStartAuswertungCSVButton());
    buttons.addButton(control.getStartAuswertungPDFButton());
    buttons.paint(this.getParent());
  }
}
