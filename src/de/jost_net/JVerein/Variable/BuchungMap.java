package de.jost_net.JVerein.Variable;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import de.jost_net.JVerein.Einstellungen;
import de.jost_net.JVerein.Einstellungen.Property;
import de.jost_net.JVerein.io.Adressbuch.Adressaufbereitung;
import de.jost_net.JVerein.rmi.Buchung;
import de.jost_net.JVerein.util.StringTool;

public class BuchungMap extends AbstractMap
{
  public Map<String, Object> getMap(Buchung bu, Map<String, Object> inma)
      throws RemoteException
  {
    Map<String, Object> map = null;
    if (inma == null)
    {
      map = new HashMap<>();
    }
    else
    {
      map = inma;
    }

    for (BuchungVar var : BuchungVar.values())
    {
      Object value = null;
      switch (var)
      {
        case ABRECHNUNGSLAUF:
          value = bu.getAbrechnungslauf() != null
              ? bu.getAbrechnungslauf().getDatum()
              : "";
          break;
        case ART:
          value = StringTool.toNotNullString(bu.getArt());
          break;
        case AUSZUGSNUMMER:
          value = bu.getAuszugsnummer();
          break;
        case BETRAG:
          value = bu.getBetrag();
          break;
        case BLATTNUMMER:
          value = bu.getBlattnummer();
          break;
        case BUCHUNGSARBEZEICHNUNG:
          value = bu.getBuchungsart() != null
              ? bu.getBuchungsart().getBezeichnung()
              : "";
          break;
        case BUCHUNGSARTNUMMER:
          value = bu.getBuchungsart() != null ? bu.getBuchungsart().getNummer()
              : "";
          break;
        case BUCHUNGSKLASSEBEZEICHNUNG:
          value = "";
          if ((Boolean) Einstellungen
              .getEinstellung(Property.BUCHUNGSKLASSEINBUCHUNG))
          {
            value = bu.getBuchungsklasse() != null
                ? bu.getBuchungsklasse().getBezeichnung()
                : "";
          }
          else
          {
            value = bu.getBuchungsart().getBuchungsklasse() != null
                ? bu.getBuchungsart().getBuchungsklasse().getBezeichnung()
                : "";
          }
          break;
        case BUCHUNGSKLASSENUMMER:
          value = "";
          if ((Boolean) Einstellungen
              .getEinstellung(Property.BUCHUNGSKLASSEINBUCHUNG))
          {
            value = bu.getBuchungsklasse() != null
                ? bu.getBuchungsklasse().getNummer()
                : "";
          }
          else
          {
            value = bu.getBuchungsart().getBuchungsklasse() != null
                ? bu.getBuchungsart().getBuchungsklasse().getNummer()
                : "";
          }
          break;
        case DATUM:
          value = bu.getDatum();
          break;
        case IBAN:
          value = bu.getIban();
          break;
        case ID:
          value = bu.getID();
          break;
        case JAHRESABSCHLUSS:
          value = bu.getJahresabschluss() != null
              ? bu.getJahresabschluss().getBis()
              : "";
          break;
        case KOMMENTAR:
          value = StringTool.toNotNullString(bu.getKommentar());
          break;
        case KONTONUMMER:
          value = bu.getKonto() != null ? bu.getKonto().getNummer() : "";
          break;
        case MITGLIEDSKONTO:
          value = bu.getSollbuchung() != null ? Adressaufbereitung
              .getNameVorname(bu.getSollbuchung().getMitglied()) : "";
          break;
        case NAME:
          value = bu.getName();
          break;
        case PROJEKTBEZEICHNUNG:
          value = bu.getProjekt() != null ? bu.getProjekt().getBezeichnung()
              : "";
          break;
        case PROJEKTNUMMER:
          value = bu.getProjekt() != null ? bu.getProjektID() : "";
          break;
        case SPENDENBESCHEINIGUNG:
          value = bu.getSpendenbescheinigung() != null
              ? bu.getSpendenbescheinigung().getID()
              : "";
          break;
        case STEUER:
          value = "";
          if ((Boolean) Einstellungen.getEinstellung(Property.OPTIERT))
          {
            if ((Boolean) Einstellungen
                .getEinstellung(Property.STEUERINBUCHUNG))
            {
              value = bu.getSteuer() == null ? 0d : bu.getSteuer().getSatz();
            }
            else
            {
              value = bu.getBuchungsart() == null
                  || bu.getBuchungsart().getSteuer() == null ? 0d
                      : bu.getBuchungsart().getSteuer().getSatz();
            }
          }
          break;
        case ZWECK1:
          value = StringTool.toNotNullString(bu.getZweck());
          break;
        default:
          break;
      }
      map.put(var.getName(), value);
    }
    return map;
  }
}
