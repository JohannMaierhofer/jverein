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
package de.jost_net.JVerein.gui.control;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.kapott.hbci.sepa.SepaVersion;

import de.jost_net.JVerein.Einstellungen;
import de.jost_net.JVerein.gui.input.BICInput;
import de.jost_net.JVerein.gui.input.EmailInput;
import de.jost_net.JVerein.gui.input.IBANInput;
import de.jost_net.JVerein.gui.input.KontoauswahlInput;
import de.jost_net.JVerein.gui.input.SEPALandInput;
import de.jost_net.JVerein.gui.input.SEPALandObject;
import de.jost_net.JVerein.keys.AbstractInputAuswahl;
import de.jost_net.JVerein.keys.AfaOrt;
import de.jost_net.JVerein.keys.Altermodel;
import de.jost_net.JVerein.keys.ArbeitsstundenModel;
import de.jost_net.JVerein.keys.Beitragsmodel;
import de.jost_net.JVerein.keys.BuchungsartSort;
import de.jost_net.JVerein.keys.SepaMandatIdSource;
import de.jost_net.JVerein.keys.Staat;
import de.jost_net.JVerein.keys.Zahlungsrhythmus;
import de.jost_net.JVerein.keys.Zahlungsweg;
import de.jost_net.JVerein.rmi.Einstellung;
import de.jost_net.JVerein.rmi.Konto;
import de.jost_net.JVerein.server.EinstellungImpl;
import de.jost_net.JVerein.util.MitgliedSpaltenauswahl;
import de.jost_net.OBanToo.SEPA.Land.SEPALaender;
import de.jost_net.OBanToo.SEPA.Land.SEPALand;
import de.willuhn.jameica.gui.AbstractControl;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.input.CheckboxInput;
import de.willuhn.jameica.gui.input.DateInput;
import de.willuhn.jameica.gui.input.DecimalInput;
import de.willuhn.jameica.gui.input.DialogInput;
import de.willuhn.jameica.gui.input.DirectoryInput;
import de.willuhn.jameica.gui.input.ImageInput;
import de.willuhn.jameica.gui.input.Input;
import de.willuhn.jameica.gui.input.IntegerInput;
import de.willuhn.jameica.gui.input.PasswordInput;
import de.willuhn.jameica.gui.input.ScaleInput;
import de.willuhn.jameica.gui.input.SelectInput;
import de.willuhn.jameica.gui.input.TextAreaInput;
import de.willuhn.jameica.gui.input.TextInput;
import de.willuhn.jameica.gui.parts.TablePart;
import de.willuhn.jameica.hbci.HBCIProperties;
import de.willuhn.jameica.security.Wallet;
import de.willuhn.jameica.system.Settings;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

public class EinstellungControl extends AbstractControl
{

  private Input name;

  private Input strasse;

  private Input plz;

  private Input ort;

  private TextInput finanzamt;

  private TextInput steuernummer;

  private DateInput bescheiddatum;

  private CheckboxInput vorlaeufig;

  private DateInput vorlaeufigab;

  private DateInput veranlagungvon;

  private DateInput veranlagungbis;

  private TextInput beguenstigterzweck;

  private CheckboxInput mitgliedsbetraege;

  private TextInput bic;

  private IBANInput iban;

  private TextInput glaeubigerid;

  private CheckboxInput geburtsdatumpflicht;

  private CheckboxInput eintrittsdatumpflicht;

  private CheckboxInput sterbedatum;

  private CheckboxInput kommunikationsdaten;

  private CheckboxInput sekundaerebeitragsgruppen;

  private CheckboxInput zusatzbetrag;

  private CheckboxInput vermerke;

  private CheckboxInput wiedervorlage;

  private CheckboxInput kursteilnehmer;

  private CheckboxInput lehrgaenge;

  private CheckboxInput juristischepersonen;

  private CheckboxInput mitgliedfoto;

  private CheckboxInput uselesefelder;

  private CheckboxInput zusatzadressen;

  private CheckboxInput auslandsadressen;

  private CheckboxInput arbeitseinsatz;

  private CheckboxInput dokumentenspeicherung;

  private CheckboxInput individuellebeitraege;

  private CheckboxInput kursteilnehmergebgespflicht;

  private TextInput rechnungtextabbuchung;

  private TextInput rechnungtextueberweisung;

  private TextInput rechnungtextbar;

  private IntegerInput zaehlerlaenge;

  private CheckboxInput externemitgliedsnummer;

  private SelectInput arbeitsstundenmodel;

  private SelectInput beitragsmodel;

  private SelectInput sepamandatidsourcemodel;

  private TextInput dateinamenmuster;

  private TextInput dateinamenmusterspende;

  private DirectoryInput vorlagenCsvVerzeichnis;

  private DecimalInput spendenbescheinigungminbetrag;

  private DirectoryInput spendenbescheinigungverzeichnis;

  private CheckboxInput spendenbescheinigungprintbuchungsart;

  private TextInput beginngeschaeftsjahr;

  private CheckboxInput autobuchunguebernahme;

  private CheckboxInput unterdrueckungohnebuchung;

  private CheckboxInput kontonummer_in_buchungsliste;
  
  private IntegerInput unterdrueckunglaenge;
  
  private IntegerInput unterdrueckungkonten;

  private CheckboxInput automatische_buchungskorrektur_hibiscus;

  private TextInput smtp_server;

  private IntegerInput smtp_port;

  private TextInput smtp_auth_user;

  private PasswordInput smtp_auth_pwd;

  private EmailInput smtp_from_address;

  private TextInput smtp_from_anzeigename;

  private CheckboxInput smtp_ssl;

  private CheckboxInput smtp_starttls;

  private ScaleInput mailverzoegerung;

  private TextInput alwaysBccTo;

  private TextInput alwaysCcTo;

  private CheckboxInput copyToImapFolder;

  private TextInput imapAuthUser;

  private PasswordInput imapAuthPwd;

  private TextInput imapHost;

  private IntegerInput imapPort;

  private CheckboxInput imap_ssl;

  private CheckboxInput imap_starttls;

  private TextInput imapSentFolder;

  private TextAreaInput mailsignatur;

  private SelectInput zahlungsweg;

  private SelectInput zahlungsrhytmus;

  private SelectInput sepaland;
  
  private SelectInput sepaversion;
  
  private SelectInput ct1sepaversion;

  private Input altersgruppen;

  private Input jubilaeen;

  private Input altersjubilaeen;

  private IntegerInput jubilarStartAlter;

  private Settings settings;

  private MitgliedSpaltenauswahl spalten;

  private IntegerInput AnzahlSpaltenStammdatenInput;

  private IntegerInput AnzahlSpaltenZusatzfelderInput;

  private IntegerInput AnzahlSpaltenLesefelderInput;

  private IntegerInput AnzahlSpaltenMitgliedschaftInput;

  private IntegerInput AnzahlSpaltenZahlungInput;

  private CheckboxInput ZeigeStammdatenInTabInput;

  private CheckboxInput ZeigeMitgliedschaftInTabInput;

  private CheckboxInput ZeigeZahlungInTabInput;

  private CheckboxInput ZeigeZusatzbeitraegeInTabInput;

  private CheckboxInput ZeigeMitgliedskontoInTabInput;

  private CheckboxInput ZeigeVermerkeInTabInput;

  private CheckboxInput ZeigeWiedervorlageInTabInput;

  private CheckboxInput ZeigeMailsInTabInput;

  private CheckboxInput ZeigeEigenschaftenInTabInput;

  private CheckboxInput ZeigeZusatzfelderInTabInput;

  private CheckboxInput ZeigeLehrgaengeInTabInput;

  private CheckboxInput ZeigeFotoInTabInput;

  private CheckboxInput ZeigeLesefelderInTabInput;

  private CheckboxInput ZeigeArbeitseinsatzInTabInput;

  private CheckboxInput zusatzbetragAusgetretene;

  private SelectInput altersmodel;

  private ScaleInput sepadatumoffset;

  private SelectInput buchungBuchungsartAuswahl;
  
  private SelectInput mitgliedAuswahl;

  private SelectInput buchungsartsort;

  private CheckboxInput abrlabschliessen;

  private CheckboxInput optiert;

  private CheckboxInput unterschriftdrucken;
  
  private ImageInput unterschrift;
  
  private CheckboxInput anhangspeichern;
  
  private CheckboxInput freiebuchungsklasse;

  private CheckboxInput summenAnlagenkonto;

  private IntegerInput qrcodesize;

  private CheckboxInput qrcodeptext;

  private CheckboxInput qrcodepdate;

  private CheckboxInput qrcodeprenum;

  private CheckboxInput qrcodepmnum;

  private TextInput qrcodetext;

  private CheckboxInput qrcodesngl;

  private TextInput qrcodeinfom;

  private TextInput qrcodeintro;

  private CheckboxInput qrcodekuerzen;
  
  private DecimalInput afarestwert;
  
  private SelectInput afaort;

  private TextInput beitragaltersstufen;
  
  private CheckboxInput mittelverwendung;

  /**
   * Verschlüsselte Datei für besonders sensible Daten (Passwörter)
   */
  private Wallet wallet = null;

  private TextInput ustid;

  private SelectInput staat;

  private DialogInput verrechnungskonto;

  private CheckboxInput splitpositionzweck;

  public EinstellungControl(AbstractView view)
  {
    super(view);
    settings = new Settings(this.getClass());
    settings.setStoreWhenRead(true);
    try
    {
      wallet = new Wallet(EinstellungImpl.class);
    }
    catch (Exception e)
    {
      Logger.error("Erstellen des Wallet-Objekts fehlgeschlagen");
    }

  }

  public Einstellung getEinstellung()
  {
    return Einstellungen.getEinstellung();
  }

  public Input getName(boolean withFocus) throws RemoteException
  {
    if (name != null)
    {
      return name;
    }
    name = new TextInput(Einstellungen.getEinstellung().getName(), 70);
    name.setMandatory(true);
    if (withFocus)
    {
      name.focus();
    }
    return name;
  }

  public Input getStrasse() throws RemoteException
  {
    if (strasse != null)
    {
      return strasse;
    }
    strasse = new TextInput(Einstellungen.getEinstellung().getStrasse(), 50);
    return strasse;
  }

  public Input getPlz() throws RemoteException
  {
    if (plz != null)
    {
      return plz;
    }
    plz = new TextInput(Einstellungen.getEinstellung().getPlz(), 5);
    return plz;
  }

  public Input getOrt() throws RemoteException
  {
    if (ort != null)
    {
      return ort;
    }
    ort = new TextInput(Einstellungen.getEinstellung().getOrt(), 50);
    return ort;
  }

  public SelectInput getStaat() throws RemoteException
  {
    if (staat != null)
    {
      return staat;
    }
    staat = new SelectInput(Staat.values(),
        Staat.getByKey(Einstellungen.getEinstellung().getStaat()));
    return staat;
  }

  public Input getUstID() throws RemoteException
  {
    if (ustid != null)
    {
      return ustid;
    }
    ustid = new TextInput(Einstellungen.getEinstellung().getUStID(), 50);
    return ustid;
  }

  public TextInput getFinanzamt() throws RemoteException
  {
    if (finanzamt != null)
    {
      return finanzamt;
    }
    finanzamt = new TextInput(Einstellungen.getEinstellung().getFinanzamt(), 30);
    return finanzamt;
  }

  public TextInput getSteuernummer() throws RemoteException
  {
    if (steuernummer != null)
    {
      return steuernummer;
    }
    steuernummer = new TextInput(Einstellungen.getEinstellung()
        .getSteuernummer(), 30);
    return steuernummer;
  }

  public DateInput getBescheiddatum() throws RemoteException
  {
    if (bescheiddatum != null)
    {
      return bescheiddatum;
    }
    bescheiddatum = new DateInput(Einstellungen.getEinstellung()
        .getBescheiddatum());
    return bescheiddatum;
  }

  public CheckboxInput getVorlaeufig() throws RemoteException
  {
    if (vorlaeufig != null)
    {
      return vorlaeufig;
    }
    vorlaeufig = new CheckboxInput(Einstellungen.getEinstellung()
        .getVorlaeufig());
    return vorlaeufig;
  }

  public DateInput getVorlaeufigab() throws RemoteException
  {
    if (vorlaeufigab != null)
    {
      return vorlaeufigab;
    }
    vorlaeufigab = new DateInput(Einstellungen.getEinstellung()
        .getVorlaeufigab());
    return vorlaeufigab;
  }

  public DateInput getVeranlagungVon() throws RemoteException
  {
    if (veranlagungvon != null)
    {
      return veranlagungvon;
    }
    veranlagungvon = new DateInput(Einstellungen.getEinstellung()
        .getVeranlagungVon());
    return veranlagungvon;
  }

  public DateInput getVeranlagungBis() throws RemoteException
  {
    if (veranlagungbis != null)
    {
      return veranlagungbis;
    }
    veranlagungbis = new DateInput(Einstellungen.getEinstellung()
        .getVeranlagungBis());
    return veranlagungbis;
  }

  public TextInput getBeguenstigterzweck() throws RemoteException
  {
    if (beguenstigterzweck != null)
    {
      return beguenstigterzweck;
    }
    beguenstigterzweck = new TextInput(Einstellungen.getEinstellung()
        .getBeguenstigterzweck(), 100);
    return beguenstigterzweck;
  }

  public CheckboxInput getMitgliedsbetraege() throws RemoteException
  {
    if (mitgliedsbetraege != null)
    {
      return mitgliedsbetraege;
    }
    mitgliedsbetraege = new CheckboxInput(Einstellungen.getEinstellung()
        .getMitgliedsbetraege());
    return mitgliedsbetraege;
  }

  public TextInput getBic() throws RemoteException
  {
    if (bic != null && !bic.getControl().isDisposed())
    {
      return bic;
    }
    bic = new BICInput(Einstellungen.getEinstellung().getBic());
    return bic;
  }

  public IBANInput getIban() throws RemoteException
  {
    if (iban != null)
    {
      return iban;
    }
    iban = new IBANInput(HBCIProperties.formatIban(Einstellungen.getEinstellung().getIban()), bic);
    return iban;
  }

  public TextInput getGlaeubigerID() throws RemoteException
  {
    if (glaeubigerid != null)
    {
      return glaeubigerid;
    }
    glaeubigerid = new TextInput(Einstellungen.getEinstellung()
        .getGlaeubigerID(), 35);
    return glaeubigerid;
  }

  public ScaleInput getSEPADatumOffset() throws RemoteException
  {
    if (sepadatumoffset != null)
    {
      return sepadatumoffset;
    }
    sepadatumoffset = new ScaleInput(Einstellungen.getEinstellung()
        .getSEPADatumOffset(), SWT.HORIZONTAL);
    sepadatumoffset.setScaling(0, 14, 1, 1);
    sepadatumoffset.setName("Zusätzliche SEPA-Vorlaufzeit");
    SEPADatumOffsetListener listener = new SEPADatumOffsetListener();
    sepadatumoffset.addListener(listener);
    listener.handleEvent(null); // einmal initial ausloesen
    return sepadatumoffset;
  }

  public CheckboxInput getGeburtsdatumPflicht() throws RemoteException
  {
    if (geburtsdatumpflicht != null)
    {
      return geburtsdatumpflicht;
    }
    geburtsdatumpflicht = new CheckboxInput(Einstellungen.getEinstellung()
        .getGeburtsdatumPflicht());
    return geburtsdatumpflicht;
  }

  public CheckboxInput getEintrittsdatumPflicht() throws RemoteException
  {
    if (eintrittsdatumpflicht != null)
    {
      return eintrittsdatumpflicht;
    }
    eintrittsdatumpflicht = new CheckboxInput(Einstellungen.getEinstellung()
        .getEintrittsdatumPflicht());
    return eintrittsdatumpflicht;
  }

  public CheckboxInput getSterbedatum() throws RemoteException
  {
    if (sterbedatum != null)
    {
      return sterbedatum;
    }
    sterbedatum = new CheckboxInput(Einstellungen.getEinstellung()
        .getSterbedatum());
    return sterbedatum;
  }

  public CheckboxInput getKommunikationsdaten() throws RemoteException
  {
    if (kommunikationsdaten != null)
    {
      return kommunikationsdaten;
    }
    kommunikationsdaten = new CheckboxInput(Einstellungen.getEinstellung()
        .getKommunikationsdaten());
    return kommunikationsdaten;
  }

  public CheckboxInput getSekundaereBeitragsgruppen() throws RemoteException
  {
    if (sekundaerebeitragsgruppen != null)
    {
      return sekundaerebeitragsgruppen;
    }
    sekundaerebeitragsgruppen = new CheckboxInput(Einstellungen
        .getEinstellung().getSekundaereBeitragsgruppen());
    return sekundaerebeitragsgruppen;
  }

  public CheckboxInput getZusatzbetrag() throws RemoteException
  {
    if (zusatzbetrag != null)
    {
      return zusatzbetrag;
    }
    zusatzbetrag = new CheckboxInput(Einstellungen.getEinstellung()
        .getZusatzbetrag());
    return zusatzbetrag;
  }

  public CheckboxInput getVermerke() throws RemoteException
  {
    if (vermerke != null)
    {
      return vermerke;
    }
    vermerke = new CheckboxInput(Einstellungen.getEinstellung().getVermerke());
    return vermerke;
  }

  public CheckboxInput getWiedervorlage() throws RemoteException
  {
    if (wiedervorlage != null)
    {
      return wiedervorlage;
    }
    wiedervorlage = new CheckboxInput(Einstellungen.getEinstellung()
        .getWiedervorlage());
    return wiedervorlage;
  }

  public CheckboxInput getKursteilnehmer() throws RemoteException
  {
    if (kursteilnehmer != null)
    {
      return kursteilnehmer;
    }
    kursteilnehmer = new CheckboxInput(Einstellungen.getEinstellung()
        .getKursteilnehmer());
    return kursteilnehmer;
  }

  public CheckboxInput getLehrgaenge() throws RemoteException
  {
    if (lehrgaenge != null)
    {
      return lehrgaenge;
    }
    lehrgaenge = new CheckboxInput(Einstellungen.getEinstellung()
        .getLehrgaenge());
    return lehrgaenge;
  }

  public CheckboxInput getJuristischePersonen() throws RemoteException
  {
    if (juristischepersonen != null)
    {
      return juristischepersonen;
    }
    juristischepersonen = new CheckboxInput(Einstellungen.getEinstellung()
        .getJuristischePersonen());
    return juristischepersonen;
  }

  public CheckboxInput getMitgliedfoto() throws RemoteException
  {
    if (mitgliedfoto != null)
    {
      return mitgliedfoto;
    }
    mitgliedfoto = new CheckboxInput(Einstellungen.getEinstellung()
        .getMitgliedfoto());
    return mitgliedfoto;
  }

  public CheckboxInput getKursteilnehmerGebGesPflicht() throws RemoteException
  {
    if (kursteilnehmergebgespflicht != null)
    {
      return kursteilnehmergebgespflicht;
    }
    kursteilnehmergebgespflicht = new CheckboxInput(Einstellungen
        .getEinstellung().getKursteilnehmerGebGesPflicht());
    return kursteilnehmergebgespflicht;
  }

  public CheckboxInput getUseLesefelder() throws RemoteException
  {
    if (uselesefelder == null)
    {
      uselesefelder = new CheckboxInput(Einstellungen.getEinstellung()
          .getUseLesefelder());
    }
    return uselesefelder;
  }

  public CheckboxInput getZusatzadressen() throws RemoteException
  {
    if (zusatzadressen != null)
    {
      return zusatzadressen;
    }
    zusatzadressen = new CheckboxInput(Einstellungen.getEinstellung()
        .getZusatzadressen());
    return zusatzadressen;
  }

  public CheckboxInput getAuslandsadressen() throws RemoteException
  {
    if (auslandsadressen != null)
    {
      return auslandsadressen;
    }
    auslandsadressen = new CheckboxInput(Einstellungen.getEinstellung()
        .getAuslandsadressen());
    return auslandsadressen;
  }

  public CheckboxInput getArbeitseinsatz() throws RemoteException
  {
    if (arbeitseinsatz != null)
    {
      return arbeitseinsatz;
    }
    arbeitseinsatz = new CheckboxInput(Einstellungen.getEinstellung()
        .getArbeitseinsatz());
    return arbeitseinsatz;
  }

  public CheckboxInput getDokumentenspeicherung() throws RemoteException
  {
    if (dokumentenspeicherung != null)
    {
      return dokumentenspeicherung;
    }
    dokumentenspeicherung = new CheckboxInput(Einstellungen.getEinstellung()
        .getDokumentenspeicherung());
    return dokumentenspeicherung;
  }

  public CheckboxInput getIndividuelleBeitraege() throws RemoteException
  {
    if (individuellebeitraege != null)
    {
      return individuellebeitraege;
    }
    individuellebeitraege = new CheckboxInput(Einstellungen.getEinstellung()
        .getIndividuelleBeitraege());
    return individuellebeitraege;
  }

  public TextInput getRechnungTextAbbuchung() throws RemoteException
  {
    if (rechnungtextabbuchung != null)
    {
      return rechnungtextabbuchung;
    }
    rechnungtextabbuchung = new TextInput(Einstellungen.getEinstellung()
        .getRechnungTextAbbuchung(), 100);
    return rechnungtextabbuchung;
  }

  public TextInput getRechnungTextUeberweisung() throws RemoteException
  {
    if (rechnungtextueberweisung != null)
    {
      return rechnungtextueberweisung;
    }
    rechnungtextueberweisung = new TextInput(Einstellungen.getEinstellung()
        .getRechnungTextUeberweisung(), 100);
    return rechnungtextueberweisung;
  }

  public TextInput getRechnungTextBar() throws RemoteException
  {
    if (rechnungtextbar != null)
    {
      return rechnungtextbar;
    }
    rechnungtextbar = new TextInput(Einstellungen.getEinstellung()
        .getRechnungTextBar(), 100);
    return rechnungtextbar;
  }

  public IntegerInput getZaehlerLaenge() throws RemoteException
  {
    if (null == zaehlerlaenge)
    {
      zaehlerlaenge = new IntegerInput(
          Einstellungen.getEinstellung().getZaehlerLaenge());
    }
    return zaehlerlaenge;
  }
  
  public CheckboxInput getOptiert() throws RemoteException 
  {
    if (optiert != null) 
    {
      return optiert;
    }
    optiert = new CheckboxInput(Einstellungen.getEinstellung().getOptiert());
    optiert.setName("Umsatzsteueroption");
    return optiert;
  }
  
  public CheckboxInput getSplitPositionZweck() throws RemoteException
  {
    if (splitpositionzweck != null)
    {
      return splitpositionzweck;
    }
    splitpositionzweck = new CheckboxInput(
        Einstellungen.getEinstellung().getSplitPositionZweck());
    splitpositionzweck.setName("Bei automatischem Splitten den "
        + "Verwendungszweck aus den Sollbuchungspositionen übernehmen");
    return splitpositionzweck;
  }

  public CheckboxInput getFreieBuchungsklasse() throws RemoteException 
  {
    if (freiebuchungsklasse != null) 
    {
      return freiebuchungsklasse;
    }
    freiebuchungsklasse = new CheckboxInput(Einstellungen.getEinstellung().getBuchungsklasseInBuchung());
    freiebuchungsklasse.setName("Keine feste Zuordnung von Buchungsklasse zu Buchungsart z.B. SKR 42");
    return freiebuchungsklasse;
  }

  public CheckboxInput getExterneMitgliedsnummer() throws RemoteException
  {
    if (externemitgliedsnummer != null)
    {
      return externemitgliedsnummer;
    }
    externemitgliedsnummer = new CheckboxInput(Einstellungen.getEinstellung()
        .getExterneMitgliedsnummer());
    return externemitgliedsnummer;
  }

  public SelectInput getBeitragsmodel() throws RemoteException
  {
    if (beitragsmodel != null)
    {
      return beitragsmodel;
    }
    beitragsmodel = new SelectInput(Beitragsmodel.values(), Einstellungen
        .getEinstellung().getBeitragsmodel());
    return beitragsmodel;
  }

  public SelectInput getArbeitsstundenmodel() throws RemoteException
  {
    if (arbeitsstundenmodel != null)
    {
      return arbeitsstundenmodel;
    }
    arbeitsstundenmodel = new SelectInput(ArbeitsstundenModel.getArray(),
        new ArbeitsstundenModel(Einstellungen.getEinstellung()
            .getArbeitsstundenmodel()));
    return arbeitsstundenmodel;
  }
  
  public Input getBeitragAltersgruppen() throws RemoteException
  {
    if (beitragaltersstufen != null)
    {
      return beitragaltersstufen;
    }
    beitragaltersstufen = new TextInput(Einstellungen.getEinstellung()
        .getBeitragAltersstufen(), 200);
    return beitragaltersstufen;
  }

  public SelectInput getSepamandatidsourcemodel() throws RemoteException
  {
    if (sepamandatidsourcemodel != null)
    {
      return sepamandatidsourcemodel;
    }
    sepamandatidsourcemodel = new SelectInput(SepaMandatIdSource.getArray(),
        new SepaMandatIdSource(Einstellungen.getEinstellung()
            .getSepaMandatIdSource()));
    return sepamandatidsourcemodel;
  }

  public SelectInput getAltersModel() throws RemoteException
  {
    if (null != altersmodel)
    {
      return altersmodel;
    }

    altersmodel = new SelectInput(Altermodel.getArray(), new Altermodel(
        Einstellungen.getEinstellung().getAltersModel()));

    return altersmodel;
  }

  public TextInput getDateinamenmuster() throws RemoteException
  {
    if (dateinamenmuster != null)
    {
      return dateinamenmuster;
    }
    dateinamenmuster = new TextInput(Einstellungen.getEinstellung()
        .getDateinamenmuster(), 30);
    dateinamenmuster
        .setComment("a$ = Aufgabe, d$ = Datum, s$ = Sortierung, z$ = Zeit");
    return dateinamenmuster;
  }

  public TextInput getDateinamenmusterSpende() throws RemoteException
  {
    if (dateinamenmusterspende != null)
    {
      return dateinamenmusterspende;
    }
    dateinamenmusterspende = new TextInput(Einstellungen.getEinstellung()
        .getDateinamenmusterSpende(), 30);
    dateinamenmusterspende
        .setComment("n$ = Name, v$ = Vorname, d$ = Datum, z$ = Zeit");
    return dateinamenmusterspende;
  }

  public DirectoryInput getVorlagenCsvVerzeichnis() throws RemoteException
  {
    if (vorlagenCsvVerzeichnis != null)
    {
      return vorlagenCsvVerzeichnis;
    }
    String lastValue = Einstellungen.getEinstellung()
        .getVorlagenCsvVerzeichnis();
    vorlagenCsvVerzeichnis = new DirectoryInput(lastValue);
    return vorlagenCsvVerzeichnis;
  }

  public DecimalInput getSpendenbescheinigungminbetrag() throws RemoteException
  {
    if (spendenbescheinigungminbetrag != null)
    {
      return spendenbescheinigungminbetrag;
    }
    spendenbescheinigungminbetrag = new DecimalInput(Einstellungen
        .getEinstellung().getSpendenbescheinigungminbetrag(),
        new DecimalFormat("###0.00"));
    return spendenbescheinigungminbetrag;
  }

  public DirectoryInput getSpendenbescheinigungverzeichnis()
      throws RemoteException
  {
    if (spendenbescheinigungverzeichnis != null)
    {
      return spendenbescheinigungverzeichnis;
    }
    spendenbescheinigungverzeichnis = new DirectoryInput(Einstellungen
        .getEinstellung().getSpendenbescheinigungverzeichnis());
    return spendenbescheinigungverzeichnis;
  }

  public CheckboxInput getSpendenbescheinigungPrintBuchungsart()
      throws RemoteException
  {
    if (spendenbescheinigungprintbuchungsart != null)
    {
      return spendenbescheinigungprintbuchungsart;
    }
    spendenbescheinigungprintbuchungsart = new CheckboxInput(Einstellungen
        .getEinstellung().getSpendenbescheinigungPrintBuchungsart());
    return spendenbescheinigungprintbuchungsart;
  }

  public TextInput getBeginnGeschaeftsjahr() throws RemoteException
  {
    if (beginngeschaeftsjahr != null)
    {
      return beginngeschaeftsjahr;
    }
    beginngeschaeftsjahr = new TextInput(Einstellungen.getEinstellung()
        .getBeginnGeschaeftsjahr(), 6);
    return beginngeschaeftsjahr;
  }

  public CheckboxInput getAutoBuchunguebernahme() throws RemoteException
  {
    if (autobuchunguebernahme != null)
    {
      return autobuchunguebernahme;
    }
    autobuchunguebernahme = new CheckboxInput(Einstellungen.getEinstellung()
        .getAutoBuchunguebernahme());
    autobuchunguebernahme
        .setName("Automatische Buchungsübernahme aus Hibiscus");
    return autobuchunguebernahme;
  }

  public CheckboxInput getUnterdrueckungOhneBuchung() throws RemoteException
  {
    if (unterdrueckungohnebuchung != null)
    {
      return unterdrueckungohnebuchung;
    }
    unterdrueckungohnebuchung = new CheckboxInput(Einstellungen
        .getEinstellung().getUnterdrueckungOhneBuchung());
    unterdrueckungohnebuchung
        .setName("Listen: Buchungsarten ohne Buchung unterdrücken");
    return unterdrueckungohnebuchung;
  }
  
  public IntegerInput getUnterdrueckungLaenge() throws RemoteException
  {
    if (null == unterdrueckunglaenge)
    {
      unterdrueckunglaenge = new IntegerInput(
          Einstellungen.getEinstellung().getUnterdrueckungLaenge());
    }
    return unterdrueckunglaenge;
  }
  
  public IntegerInput getUnterdrueckungKonten() throws RemoteException
  {
    if (null == unterdrueckungkonten)
    {
      unterdrueckungkonten = new IntegerInput(
          Einstellungen.getEinstellung().getUnterdrueckungKonten());
    }
    return unterdrueckungkonten;
  }  
  
  public CheckboxInput getKontonummerInBuchungsliste() throws RemoteException 
  {
    if (kontonummer_in_buchungsliste != null) 
    {
      return kontonummer_in_buchungsliste;
    }
    kontonummer_in_buchungsliste = new CheckboxInput(Einstellungen.getEinstellung().getKontonummerInBuchungsliste());
    kontonummer_in_buchungsliste.setName("Zeige Kontonummer in Buchungsliste");
    return kontonummer_in_buchungsliste;
  }

  public CheckboxInput getAutomatischeBuchungskorrekturHibiscus() throws RemoteException 
  {
    if (automatische_buchungskorrektur_hibiscus != null) 
    {
      return automatische_buchungskorrektur_hibiscus;
    }
    automatische_buchungskorrektur_hibiscus = new CheckboxInput(Einstellungen.getEinstellung().getAutomatischeBuchungskorrekturHibiscus());
    automatische_buchungskorrektur_hibiscus.setName("Automatische Korrektur der Verwendungszwecke aus Hibiscus Buchungen");
    return automatische_buchungskorrektur_hibiscus;
  }

  public TextInput getSmtpServer() throws RemoteException
  {
    if (smtp_server != null)
    {
      return smtp_server;
    }
    smtp_server = new TextInput(Einstellungen.getEinstellung().getSmtpServer(),
        50);
    return smtp_server;
  }

  public IntegerInput getSmtpPort() throws RemoteException
  {
    if (smtp_port != null)
    {
      return smtp_port;
    }
    String port = Einstellungen.getEinstellung().getSmtpPort();
    if (port != null && port.length() > 0)
    {
      smtp_port = new IntegerInput(Integer.valueOf(port));
    }
    else
    {
      smtp_port = new IntegerInput();
    }
    return smtp_port;
  }

  public TextInput getSmtpAuthUser() throws RemoteException
  {
    if (smtp_auth_user != null)
    {
      return smtp_auth_user;
    }
    smtp_auth_user = new TextInput(Einstellungen.getEinstellung()
        .getSmtpAuthUser(), 140);
    return smtp_auth_user;
  }

  public PasswordInput getSmtpAuthPwd() throws RemoteException
  {
    if (smtp_auth_pwd != null)
    {
      return smtp_auth_pwd;
    }
    smtp_auth_pwd = new PasswordInput(Einstellungen.getEinstellung()
        .getSmtpAuthPwd());
    return smtp_auth_pwd;
  }

  public EmailInput getSmtpFromAddress() throws RemoteException
  {
    if (smtp_from_address != null)
    {
      return smtp_from_address;
    }
    smtp_from_address = new EmailInput(Einstellungen.getEinstellung()
        .getSmtpFromAddress());
    return smtp_from_address;
  }

  public TextInput getSmtpFromAnzeigename() throws RemoteException
  {
    if (smtp_from_anzeigename != null)
    {
      return smtp_from_anzeigename;
    }
    smtp_from_anzeigename = new TextInput(Einstellungen.getEinstellung()
        .getSmtpFromAnzeigename(), 50);
    return smtp_from_anzeigename;
  }

  public CheckboxInput getSmtpSsl() throws RemoteException
  {
    if (smtp_ssl != null)
    {
      return smtp_ssl;
    }
    smtp_ssl = new CheckboxInput(Einstellungen.getEinstellung().getSmtpSsl());
    return smtp_ssl;
  }

  public CheckboxInput getSmtpStarttls() throws RemoteException
  {
    if (smtp_starttls != null)
    {
      return smtp_starttls;
    }
    smtp_starttls = new CheckboxInput(Einstellungen.getEinstellung()
        .getSmtpStarttls());
    return smtp_starttls;
  }

  public ScaleInput getMailVerzoegerung() throws RemoteException
  {
    if (mailverzoegerung != null)
    {
      return mailverzoegerung;
    }
    mailverzoegerung = new ScaleInput(Einstellungen.getEinstellung()
        .getMailVerzoegerung());
    mailverzoegerung.setScaling(0, 10000, 100, 100);
    mailverzoegerung.setComment("");
    MailVerzoegerungListener listener = new MailVerzoegerungListener();
    mailverzoegerung.addListener(listener);
    listener.handleEvent(null); // einmal initial ausloesen

    return mailverzoegerung;
  }

  public TextInput getAlwaysBccTo() throws RemoteException
  {
    if (alwaysBccTo != null)
    {
      return alwaysBccTo;
    }
    alwaysBccTo = new TextInput(Einstellungen.getEinstellung()
        .getMailAlwaysBcc());
    return alwaysBccTo;
  }

  public TextInput getAlwaysCcTo() throws RemoteException
  {
    if (alwaysCcTo != null)
    {
      return alwaysCcTo;
    }
    alwaysCcTo = new TextInput(Einstellungen.getEinstellung().getMailAlwaysCc());
    return alwaysCcTo;
  }

  public CheckboxInput getCopyToImapFolder() throws RemoteException
  {
    if (copyToImapFolder != null)
    {
      return copyToImapFolder;
    }
    copyToImapFolder = new CheckboxInput(Einstellungen.getEinstellung()
        .getCopyToImapFolder());
    return copyToImapFolder;
  }

  public TextInput getImapAuthUser() throws RemoteException
  {
    if (imapAuthUser != null)
    {
      return imapAuthUser;
    }
    imapAuthUser = new TextInput(Einstellungen.getEinstellung()
        .getImapAuthUser());
    return imapAuthUser;
  }

  public TextInput getImapAuthPwd() throws RemoteException
  {
    if (imapAuthPwd != null)
    {
      return imapAuthPwd;
    }
    imapAuthPwd = new PasswordInput(Einstellungen.getEinstellung()
        .getImapAuthPwd());
    return imapAuthPwd;
  }

  public TextInput getImapHost() throws RemoteException
  {
    if (imapHost != null)
    {
      return imapHost;
    }
    imapHost = new TextInput(Einstellungen.getEinstellung().getImapHost());
    return imapHost;
  }

  public IntegerInput getImapPort() throws RemoteException
  {
    if (imapPort != null)
    {
      return imapPort;
    }
    String port = Einstellungen.getEinstellung().getImapPort();
    if (port != null && port.length() > 0)
    {
      imapPort = new IntegerInput(Integer.valueOf(port));
    }
    else
    {
      imapPort = new IntegerInput();
    }
    return imapPort;
  }

  public CheckboxInput getImap_ssl() throws RemoteException
  {
    if (imap_ssl != null)
    {
      return imap_ssl;
    }
    imap_ssl = new CheckboxInput(Einstellungen.getEinstellung().getImapSsl());
    return imap_ssl;
  }

  public CheckboxInput getImap_starttls() throws RemoteException
  {
    if (imap_starttls != null)
    {
      return imap_starttls;
    }
    imap_starttls = new CheckboxInput(Einstellungen.getEinstellung()
        .getImapStartTls());
    return imap_starttls;
  }

  public TextInput getImapSentFolder() throws RemoteException
  {
    if (imapSentFolder != null)
    {
      return imapSentFolder;
    }
    imapSentFolder = new TextInput(Einstellungen.getEinstellung()
        .getImapSentFolder());
    return imapSentFolder;
  }

  public Input getMailSignatur() throws RemoteException
  {
    if (mailsignatur != null)
    {
      return mailsignatur;
    }
    mailsignatur = new TextAreaInput(Einstellungen.getEinstellung()
        .getMailSignatur(false), 1000);
    mailsignatur.setHeight(50);
    return mailsignatur;
  }

  public SelectInput getZahlungsweg() throws RemoteException
  {
    if (zahlungsweg != null)
    {
      return zahlungsweg;
    }
    zahlungsweg = new SelectInput(Zahlungsweg.getArray(false), new Zahlungsweg(
        Einstellungen.getEinstellung().getZahlungsweg()));
    zahlungsweg.setName("Standard-Zahlungsweg f. neue Mitglieder");
    return zahlungsweg;
  }

  public SelectInput getZahlungsrhytmus() throws RemoteException
  {
    if (zahlungsrhytmus != null)
    {
      return zahlungsrhytmus;
    }
    zahlungsrhytmus = new SelectInput(Zahlungsrhythmus.getArray(),
        new Zahlungsrhythmus(Einstellungen.getEinstellung()
            .getZahlungsrhytmus()));
    zahlungsrhytmus.setName("Standard-Zahlungsrhytmus f. neue Mitglieder");
    return zahlungsrhytmus;
  }

  public SelectInput getDefaultSEPALand() throws RemoteException
  {
    if (sepaland != null)
    {
      return sepaland;
    }
    SEPALand sl = SEPALaender.getLand(Einstellungen.getEinstellung()
        .getDefaultLand());
    sepaland = new SEPALandInput(sl);
    return sepaland;
  }
  
  public SelectInput getSepaVersion() throws RemoteException
  {
    if (sepaversion != null)
    {
      return sepaversion;
    }
    List<SepaVersion> list = SepaVersion.getKnownVersions(
        org.kapott.hbci.sepa.SepaVersion.Type.PAIN_008);
    sepaversion = new SelectInput(list, 
        Einstellungen.getEinstellung().getSepaVersion());
    sepaversion.setAttribute("file");
    return sepaversion;
  }
  
  public SelectInput getCt1SepaVersion() throws RemoteException
  {
    if (ct1sepaversion != null)
    {
      return ct1sepaversion;
    }
    List<SepaVersion> list = SepaVersion.getKnownVersions(
        org.kapott.hbci.sepa.SepaVersion.Type.PAIN_001);
    ct1sepaversion = new SelectInput(list, 
        Einstellungen.getEinstellung().getCt1SepaVersion());
    ct1sepaversion.setAttribute("file");
    return ct1sepaversion;
  }

  public DialogInput getVerrechnungskonto() throws RemoteException
  {
    if (verrechnungskonto != null)
    {
      return verrechnungskonto;
    }
    verrechnungskonto = new KontoauswahlInput(null).getKontoAuswahl(false,
        Einstellungen.getEinstellung().getVerrechnungskontoId() == null ? null
            : Einstellungen.getEinstellung().getVerrechnungskontoId()
                .toString(),
        false, false, null);
    return verrechnungskonto;
  }

  public Input getAltersgruppen() throws RemoteException
  {
    if (altersgruppen != null)
    {
      return altersgruppen;
    }
    altersgruppen = new TextInput(Einstellungen.getEinstellung()
        .getAltersgruppen(), 200);
    return altersgruppen;
  }

  public Input getJubilaeen() throws RemoteException
  {
    if (jubilaeen != null)
    {
      return jubilaeen;
    }
    jubilaeen = new TextInput(Einstellungen.getEinstellung().getJubilaeen(), 50);
    return jubilaeen;
  }

  public Input getAltersjubilaeen() throws RemoteException
  {
    if (altersjubilaeen != null)
    {
      return altersjubilaeen;
    }
    altersjubilaeen = new TextInput(Einstellungen.getEinstellung()
        .getAltersjubilaeen(), 200);
    return altersjubilaeen;
  }

  public IntegerInput getJubilarStartAlter() throws RemoteException
  {
    if (null == jubilarStartAlter)
    {
      jubilarStartAlter = new IntegerInput(Einstellungen.getEinstellung()
          .getJubilarStartAlter());
    }
    return jubilarStartAlter;
  }

  public TablePart getSpaltendefinitionTable() throws RemoteException
  {
    if (spalten == null)
    {
      spalten = new MitgliedSpaltenauswahl();
    }
    return spalten.paintSpaltenpaintSpaltendefinitionTable();
  }

  public void setCheckSpalten()
  {
    spalten.setCheckSpalten();
  }

  public IntegerInput getAnzahlSpaltenStammdatenInput() throws RemoteException
  {
    {
      if (AnzahlSpaltenStammdatenInput != null)
      {
        return AnzahlSpaltenStammdatenInput;
      }
      AnzahlSpaltenStammdatenInput = new IntegerInput(Einstellungen
          .getEinstellung().getAnzahlSpaltenStammdaten());
      return AnzahlSpaltenStammdatenInput;
    }
  }

  public IntegerInput getAnzahlSpaltenLesefelderInput() throws RemoteException
  {
    {
      if (AnzahlSpaltenLesefelderInput != null)
      {
        return AnzahlSpaltenLesefelderInput;
      }
      AnzahlSpaltenLesefelderInput = new IntegerInput(Einstellungen
          .getEinstellung().getAnzahlSpaltenLesefelder());
      return AnzahlSpaltenLesefelderInput;
    }
  }

  public IntegerInput getAnzahlSpaltenMitgliedschaftInput()
      throws RemoteException
  {
    {
      if (AnzahlSpaltenMitgliedschaftInput != null)
      {
        return AnzahlSpaltenMitgliedschaftInput;
      }
      AnzahlSpaltenMitgliedschaftInput = new IntegerInput(Einstellungen
          .getEinstellung().getAnzahlSpaltenMitgliedschaft());
      return AnzahlSpaltenMitgliedschaftInput;
    }
  }

  public IntegerInput getAnzahlSpaltenZahlungInput() throws RemoteException
  {
    {
      if (AnzahlSpaltenZahlungInput != null)
      {
        return AnzahlSpaltenZahlungInput;
      }
      AnzahlSpaltenZahlungInput = new IntegerInput(Einstellungen
          .getEinstellung().getAnzahlSpaltenZahlung());
      return AnzahlSpaltenZahlungInput;
    }
  }

  public IntegerInput getAnzahlSpaltenZusatzfelderInput()
      throws RemoteException
  {
    {
      if (AnzahlSpaltenZusatzfelderInput != null)
      {
        return AnzahlSpaltenZusatzfelderInput;
      }
      AnzahlSpaltenZusatzfelderInput = new IntegerInput(Einstellungen
          .getEinstellung().getAnzahlSpaltenZusatzfelder());
      return AnzahlSpaltenZusatzfelderInput;
    }
  }

  public CheckboxInput getZeigeStammdatenInTabCheckbox() throws RemoteException
  {
    if (ZeigeStammdatenInTabInput != null)
    {
      return ZeigeStammdatenInTabInput;
    }
    ZeigeStammdatenInTabInput = new CheckboxInput(Einstellungen
        .getEinstellung().getZeigeStammdatenInTab());
    return ZeigeStammdatenInTabInput;
  }

  public CheckboxInput getZeigeMitgliedschaftInTabCheckbox()
      throws RemoteException
  {
    if (ZeigeMitgliedschaftInTabInput != null)
    {
      return ZeigeMitgliedschaftInTabInput;
    }
    ZeigeMitgliedschaftInTabInput = new CheckboxInput(Einstellungen
        .getEinstellung().getZeigeMitgliedschaftInTab());
    return ZeigeMitgliedschaftInTabInput;
  }

  public CheckboxInput getZeigeZahlungInTabCheckbox() throws RemoteException
  {
    if (ZeigeZahlungInTabInput != null)
    {
      return ZeigeZahlungInTabInput;
    }
    ZeigeZahlungInTabInput = new CheckboxInput(Einstellungen.getEinstellung()
        .getZeigeZahlungInTab());
    return ZeigeZahlungInTabInput;
  }

  public CheckboxInput getZeigeZusatzbetrageInTabCheckbox()
      throws RemoteException
  {
    if (ZeigeZusatzbeitraegeInTabInput != null)
    {
      return ZeigeZusatzbeitraegeInTabInput;
    }
    ZeigeZusatzbeitraegeInTabInput = new CheckboxInput(Einstellungen
        .getEinstellung().getZeigeZusatzbetraegeInTab());
    return ZeigeZusatzbeitraegeInTabInput;
  }

  public CheckboxInput getZeigeMitgliedskontoInTabCheckbox()
      throws RemoteException
  {
    if (ZeigeMitgliedskontoInTabInput != null)
    {
      return ZeigeMitgliedskontoInTabInput;
    }
    ZeigeMitgliedskontoInTabInput = new CheckboxInput(Einstellungen
        .getEinstellung().getZeigeMitgliedskontoInTab());
    return ZeigeMitgliedskontoInTabInput;
  }

  public CheckboxInput getZeigeVermerkeInTabCheckbox() throws RemoteException
  {
    if (ZeigeVermerkeInTabInput != null)
    {
      return ZeigeVermerkeInTabInput;
    }
    ZeigeVermerkeInTabInput = new CheckboxInput(Einstellungen.getEinstellung()
        .getZeigeVermerkeInTab());
    return ZeigeVermerkeInTabInput;
  }

  public CheckboxInput getZeigeWiedervorlageInTabCheckbox()
      throws RemoteException
  {
    if (ZeigeWiedervorlageInTabInput != null)
    {
      return ZeigeWiedervorlageInTabInput;
    }
    ZeigeWiedervorlageInTabInput = new CheckboxInput(Einstellungen
        .getEinstellung().getZeigeWiedervorlageInTab());
    return ZeigeWiedervorlageInTabInput;
  }

  public CheckboxInput getZeigeMailsInTabCheckbox() throws RemoteException
  {
    if (ZeigeMailsInTabInput != null)
    {
      return ZeigeMailsInTabInput;
    }
    ZeigeMailsInTabInput = new CheckboxInput(Einstellungen.getEinstellung()
        .getZeigeMailsInTab());
    return ZeigeMailsInTabInput;
  }

  public CheckboxInput getZeigeEigenschaftenInTabCheckbox()
      throws RemoteException
  {
    if (ZeigeEigenschaftenInTabInput != null)
    {
      return ZeigeEigenschaftenInTabInput;
    }
    ZeigeEigenschaftenInTabInput = new CheckboxInput(Einstellungen
        .getEinstellung().getZeigeEigenschaftenInTab());
    return ZeigeEigenschaftenInTabInput;
  }

  public CheckboxInput getZeigeZusatzfelderInTabCheckbox()
      throws RemoteException
  {
    if (ZeigeZusatzfelderInTabInput != null)
    {
      return ZeigeZusatzfelderInTabInput;
    }
    ZeigeZusatzfelderInTabInput = new CheckboxInput(Einstellungen
        .getEinstellung().getZeigeZusatzfelderInTab());
    return ZeigeZusatzfelderInTabInput;
  }

  public CheckboxInput getZeigeLehrgaengeInTabCheckbox() throws RemoteException
  {
    if (ZeigeLehrgaengeInTabInput != null)
    {
      return ZeigeLehrgaengeInTabInput;
    }
    ZeigeLehrgaengeInTabInput = new CheckboxInput(Einstellungen
        .getEinstellung().getZeigeLehrgaengeInTab());
    return ZeigeLehrgaengeInTabInput;
  }

  public CheckboxInput getZeigeFotoInTabCheckbox() throws RemoteException
  {
    if (ZeigeFotoInTabInput != null)
    {
      return ZeigeFotoInTabInput;
    }
    ZeigeFotoInTabInput = new CheckboxInput(Einstellungen.getEinstellung()
        .getZeigeFotoInTab());
    return ZeigeFotoInTabInput;
  }

  public CheckboxInput getZeigeLesefelderInTabCheckbox() throws RemoteException
  {
    if (ZeigeLesefelderInTabInput != null)
    {
      return ZeigeLesefelderInTabInput;
    }
    ZeigeLesefelderInTabInput = new CheckboxInput(Einstellungen
        .getEinstellung().getZeigeLesefelderInTab());
    return ZeigeLesefelderInTabInput;
  }

  public CheckboxInput getZeigeArbeitseinsatzInTabCheckbox()
      throws RemoteException
  {
    if (ZeigeArbeitseinsatzInTabInput != null)
    {
      return ZeigeArbeitseinsatzInTabInput;
    }
    ZeigeArbeitseinsatzInTabInput = new CheckboxInput(Einstellungen
        .getEinstellung().getZeigeArbeitseinsatzInTab());
    return ZeigeArbeitseinsatzInTabInput;
  }

  public SelectInput getBuchungBuchungsartAuswahl() throws RemoteException
  {
    if (null != buchungBuchungsartAuswahl)
    {
      return buchungBuchungsartAuswahl;
    }

    buchungBuchungsartAuswahl = new SelectInput(
        AbstractInputAuswahl.getArray(), new AbstractInputAuswahl(
            Einstellungen.getEinstellung().getBuchungBuchungsartAuswahl()));

    return buchungBuchungsartAuswahl;
  }
  
  public SelectInput getMitgliedAuswahl() throws RemoteException
  {
    if (null != mitgliedAuswahl)
    {
      return mitgliedAuswahl;
    }

    mitgliedAuswahl = new SelectInput(
        AbstractInputAuswahl.getArray(), new AbstractInputAuswahl(
            Einstellungen.getEinstellung().getMitgliedAuswahl()));

    return mitgliedAuswahl;
  }

  public SelectInput getBuchungsartSort() throws RemoteException
  {
    if (buchungsartsort != null)
    {
      return buchungsartsort;
    }
    buchungsartsort = new SelectInput(
        BuchungsartSort.getArray(),
        new BuchungsartSort(Einstellungen.getEinstellung().getBuchungsartSort()));
    return buchungsartsort;
  }

  public IntegerInput getQRCodeSizeInMm() throws RemoteException
  {
    if (null == qrcodesize)
    {
      qrcodesize = new IntegerInput(
          Einstellungen.getEinstellung().getQRCodeSizeInMm());
    }
    return qrcodesize;
  }

  public TextInput getQRCodeVerwendungszweck() throws RemoteException
  {
    if (null == qrcodetext)
    {
      qrcodetext = new TextInput(
          Einstellungen.getEinstellung().getQRCodeText());
    }
    return qrcodetext;
  }

  public CheckboxInput getQRCodePrintVerwendungszweck() throws RemoteException
  {
    if (null == qrcodeptext)
    {
      qrcodeptext = new CheckboxInput(
          Einstellungen.getEinstellung().getQRCodeFesterText());
    }
    return qrcodeptext;
  }

  public CheckboxInput getQRCodeSingle() throws RemoteException
  {
    if (null == qrcodesngl)
    {
      qrcodesngl = new CheckboxInput(
          Einstellungen.getEinstellung().getQRCodeSnglLine());
    }
    return qrcodesngl;
  }

  public CheckboxInput getQRCodeReDa() throws RemoteException
  {
    if (null == qrcodepdate)
    {
      qrcodepdate = new CheckboxInput(
          Einstellungen.getEinstellung().getQRCodeDatum());
    }
    return qrcodepdate;
  }

  public CheckboxInput getQRCodeReNr() throws RemoteException
  {
    if (null == qrcodeprenum)
    {
      qrcodeprenum = new CheckboxInput(
          Einstellungen.getEinstellung().getQRCodeReNu());
    }
    return qrcodeprenum;
  }

  public CheckboxInput getQRCodeMemberNr() throws RemoteException
  {
    if (null == qrcodepmnum)
    {
      qrcodepmnum = new CheckboxInput(
          Einstellungen.getEinstellung().getQRCodeMember());
    }
    return qrcodepmnum;
  }

  public TextInput getQRCodeInfoToMember() throws RemoteException
  {
    if (null == qrcodeinfom)
    {
      qrcodeinfom = new TextInput(
          Einstellungen.getEinstellung().getQRCodeInfoM());
    }
    return qrcodeinfom;
  }

  public CheckboxInput getQRCodeKuerzen() throws RemoteException
  {
    if (null == qrcodekuerzen)
    {
      qrcodekuerzen = new CheckboxInput(
          Einstellungen.getEinstellung().getQRCodeKuerzen());
    }
    return qrcodekuerzen;
  }

  public TextInput getQRCodeIntro() throws RemoteException
  {
    if (null == qrcodeintro)
    {
      qrcodeintro = new TextInput(
          Einstellungen.getEinstellung().getQRCodeIntro());
    }
    return qrcodeintro;
  }

  // // public void handleStore()
  // {
  // try
  // {
  // Einstellung e = Einstellungen.getEinstellung();
  // e.setID();
  // e.setName((String) getName(false).getValue());
  // e.setStrasse((String) getStrasse().getValue());
  // e.setPlz((String) getPlz().getValue());
  // e.setOrt((String) getOrt().getValue());
  // e.setFinanzamt((String) getFinanzamt().getValue());
  // e.setSteuernummer((String) getSteuernummer().getValue());
  // e.setBescheiddatum((Date) getBescheiddatum().getValue());
  // e.setVorlaeufig((Boolean) getVorlaeufig().getValue());
  // e.setVorlaeufigab((Date) getVorlaeufigab().getValue());
  // e.setVeranlagungVon((Date) getVeranlagungVon().getValue());
  // e.setVeranlagungBis((Date) getVeranlagungBis().getValue());
  // e.setBeguenstigterzweck((String) getBeguenstigterzweck().getValue());
  // e.setMitgliedsbeitraege((Boolean) getMitgliedsbetraege().getValue());
  // e.setBic((String) getBic().getValue());
  // e.setIban((String) getIban().getValue());
  // e.setGlaeubigerID((String) getGlaeubigerID().getValue());
  // e.setBlz((String) getBlz().getValue());
  // e.setKonto((String) getKonto().getValue());
  // e.setGeburtsdatumPflicht((Boolean) geburtsdatumpflicht.getValue());
  // e.setEintrittsdatumPflicht((Boolean) eintrittsdatumpflicht.getValue());
  // e.setSterbedatum((Boolean) sterbedatum.getValue());
  // e.setKommunikationsdaten((Boolean) kommunikationsdaten.getValue());
  // e.setZusatzbetrag((Boolean) zusatzbetrag.getValue());
  // e.setZusatzbetragAusgetretene((Boolean) zusatzbetragAusgetretene
  // .getValue());
  // e.setVermerke((Boolean) vermerke.getValue());
  // e.setWiedervorlage((Boolean) wiedervorlage.getValue());
  // e.setKursteilnehmer((Boolean) kursteilnehmer.getValue());
  // e.setLehrgaenge((Boolean) lehrgaenge.getValue());
  // e.setJuristischePersonen((Boolean) juristischepersonen.getValue());
  // e.setMitgliedfoto((Boolean) mitgliedfoto.getValue());
  // e.setUseLesefelder((Boolean) uselesefelder.getValue());
  // e.setZusatzadressen((Boolean) zusatzadressen.getValue());
  // e.setAuslandsadressen((Boolean) auslandsadressen.getValue());
  // e.setArbeitseinsatz((Boolean) arbeitseinsatz.getValue());
  // e.setDokumentenspeicherung((Boolean) dokumentenspeicherung.getValue());
  // e.setIndividuelleBeitraege((Boolean) individuellebeitraege.getValue());
  // e.setRechnungTextAbbuchung((String) rechnungtextabbuchung.getValue());
  // e.setRechnungTextAbbuchung((String) rechnungtextabbuchung.getValue());
  // e.setRechnungTextUeberweisung((String) rechnungtextueberweisung
  // .getValue());
  // e.setRechnungTextBar((String) rechnungtextbar.getValue());
  // e.setExterneMitgliedsnummer((Boolean) externemitgliedsnummer.getValue());
  // Beitragsmodel bm = (Beitragsmodel) beitragsmodel.getValue();
  // e.setBeitragsmodel(bm.getKey());
  // ArbeitsstundenModel am = (ArbeitsstundenModel) arbeitsstundenmodel
  // .getValue();
  // e.setArbeitsstundenmodel(am.getKey());
  // SepaMandatIdSource sepaSource = (SepaMandatIdSource)
  // sepamandatidsourcemodel
  // .getValue();
  // e.setSepaMandatIdSource(sepaSource.getKey());
  // Altermodel amValue = (Altermodel) altersmodel.getValue();
  // e.setAltersModel(amValue.getKey());
  // e.setDateinamenmuster((String) dateinamenmuster.getValue());
  // e.setDateinamenmusterSpende((String) dateinamenmusterspende.getValue());
  // e.setVorlagenCsvVerzeichnis((String) vorlagenCsvVerzeichnis.getValue());
  // e.setSpendenbescheinigungminbetrag((Double) spendenbescheinigungminbetrag
  // .getValue());
  // e.setSpendenbescheinigungverzeichnis((String)
  // spendenbescheinigungverzeichnis
  // .getValue());
  // e.setSpendenbescheinigungPrintBuchungsart((Boolean)
  // spendenbescheinigungprintbuchungsart
  // .getValue());
  // e.setBeginnGeschaeftsjahr((String) beginngeschaeftsjahr.getValue());
  // e.setAutoBuchunguebernahme((Boolean) autobuchunguebernahme.getValue());
  // e.setSmtpServer((String) smtp_server.getValue());
  // Integer port = (Integer) smtp_port.getValue();
  // e.setSmtpPort(port.toString());
  // e.setSmtpAuthUser((String) smtp_auth_user.getValue());
  // e.setSmtpAuthPwd((String) smtp_auth_pwd.getValue());
  // e.setSmtpFromAddress((String) smtp_from_address.getValue());
  // e.setSmtpFromAnzeigename((String) smtp_from_anzeigename.getValue());
  // e.setSmtpSsl((Boolean) smtp_ssl.getValue());
  // e.setSmtpStarttls((Boolean) smtp_starttls.getValue());
  // e.setMailAlwaysCc((String) alwaysCcTo.getValue());
  // e.setMailAlwaysBcc((String) alwaysBccTo.getValue());
  //
  // e.setCopyToImapFolder((Boolean) copyToImapFolder.getValue());
  // e.setImapHost((String) imapHost.getValue());
  // e.setImapPort((String) imapPort.getValue());
  // e.setImapAuthUser(((String) imapAuthUser.getValue()));
  // e.setImapAuthPwd(((String) imapAuthPwd.getValue()));
  // e.setImapSsl((Boolean) imap_ssl.getValue());
  // e.setImapStartTls((Boolean) imap_starttls.getValue());
  // e.setImapSentFolder((String) imapSentFolder.getValue());
  // e.setMailSignatur((String) mailsignatur.getValue());
  //
  // Zahlungsrhytmus zr = (Zahlungsrhytmus) zahlungsrhytmus.getValue();
  // e.setZahlungsrhytmus(zr.getKey());
  // Zahlungsweg zw = (Zahlungsweg) zahlungsweg.getValue();
  // e.setZahlungsweg(zw.getKey());
  // SEPALandObject slo = (SEPALandObject) getDefaultSEPALand().getValue();
  // e.setDefaultLand(slo.getLand().getKennzeichen());
  // e.setAltersgruppen((String) getAltersgruppen().getValue());
  // e.setJubilaeen((String) getJubilaeen().getValue());
  // e.setAltersjubilaeen((String) getAltersjubilaeen().getValue());
  // Integer jubilaeumStartAlter = (Integer) jubilarStartAlter.getValue();
  // e.setJubilarStartAlter(jubilaeumStartAlter);
  // e.store();
  // spalten.save();
  // Einstellungen.setEinstellung(e);
  //
  // e.setAnzahlSpaltenStammdaten((Integer) getAnzahlSpaltenStammdatenInput()
  // .getValue());
  // e.setAnzahlSpaltenLesefelder((Integer) getAnzahlSpaltenLesefelderInput()
  // .getValue());
  // e.setAnzahlSpaltenZusatzfelder((Integer)
  // getAnzahlSpaltenZusatzfelderInput()
  // .getValue());
  // e.setAnzahlSpaltenMitgliedschaft((Integer)
  // getAnzahlSpaltenMitgliedschaftInput()
  // .getValue());
  // e.setAnzahlSpaltenZahlung((Integer) getAnzahlSpaltenZahlungInput()
  // .getValue());
  // e.setZeigeStammdatenInTab((Boolean) getZeigeStammdatenInTabCheckbox()
  // .getValue());
  // e.setZeigeMitgliedschaftInTab((Boolean)
  // getZeigeMitgliedschaftInTabCheckbox()
  // .getValue());
  // e.setZeigeZahlungInTab((Boolean) getZeigeZahlungInTabCheckbox()
  // .getValue());
  // e.setZeigeZusatzbetrageInTab((Boolean) getZeigeZusatzbetrageInTabCheckbox()
  // .getValue());
  // e.setZeigeMitgliedskontoInTab((Boolean)
  // getZeigeMitgliedskontoInTabCheckbox()
  // .getValue());
  // e.setZeigeVermerkeInTab((Boolean) getZeigeVermerkeInTabCheckbox()
  // .getValue());
  // e.setZeigeWiedervorlageInTab((Boolean) getZeigeWiedervorlageInTabCheckbox()
  // .getValue());
  // e.setZeigeMailsInTab((Boolean) getZeigeMailsInTabCheckbox().getValue());
  // e.setZeigeEigentschaftenInTab((Boolean)
  // getZeigeEigenschaftenInTabCheckbox()
  // .getValue());
  // e.setZeigeZusatzfelderInTab((Boolean) getZeigeZusatzfelderInTabCheckbox()
  // .getValue());
  // e.setZeigeLehrgaengeInTab((Boolean) getZeigeLehrgaengeInTabCheckbox()
  // .getValue());
  // e.setZeigeFotoInTab((Boolean) getZeigeFotoInTabCheckbox().getValue());
  // e.setZeigeLesefelderInTab((Boolean) getZeigeLesefelderInTabCheckbox()
  // .getValue());
  // e.setZeigeArbeitseinsatzInTab((Boolean)
  // getZeigeArbeitseinsatzInTabCheckbox()
  // .getValue());
  // GUI.getStatusBar().setSuccessText("Einstellungen gespeichert");
  // }
  // catch (RemoteException e)
  // {
  // GUI.getStatusBar().setErrorText(e.getMessage());
  // }
  // catch (ApplicationException e)
  // {
  // GUI.getStatusBar().setErrorText(e.getMessage());
  // }
  // }

  public CheckboxInput getZusatzbetragAusgetretene() throws RemoteException
  {
    if (zusatzbetragAusgetretene != null)
    {
      return zusatzbetragAusgetretene;
    }
    zusatzbetragAusgetretene = new CheckboxInput(Einstellungen.getEinstellung()
        .getZusatzbetragAusgetretene());
    return zusatzbetragAusgetretene;
  }

  public CheckboxInput getAbrlAbschliessen() throws RemoteException
  {
    if (abrlabschliessen != null)
    {
      return abrlabschliessen;
    }
    abrlabschliessen = new CheckboxInput(Einstellungen.getEinstellung()
        .getAbrlAbschliessen());
    abrlabschliessen.setName("Funktion einschalten");
    return abrlabschliessen;
  }
  
  public CheckboxInput getUnterschriftdrucken() throws RemoteException 
  {
    if (unterschriftdrucken != null) 
    {
      return unterschriftdrucken;
    }
    unterschriftdrucken = new CheckboxInput(Einstellungen.getEinstellung().getUnterschriftdrucken());
    unterschriftdrucken.setName(" *Die maschinelle Erstellung von Zuwendungsbestätigungen muss "
        + "vorab dem zuständigen Finanzamt angezeigt worden sein.");
    return unterschriftdrucken;
  }

  public ImageInput getUnterschrift() throws RemoteException
  {
    if (unterschrift != null)
    {
      return unterschrift;
    }
    unterschrift = new ImageInput(Einstellungen.getEinstellung().getUnterschrift(), 400, 75);
    return unterschrift;
  }
  
  public CheckboxInput getAnhangSpeichern() throws RemoteException 
  {
    if (anhangspeichern != null) 
    {
      return anhangspeichern;
    }
    anhangspeichern = new CheckboxInput(Einstellungen.getEinstellung().getAnhangSpeichern());
    anhangspeichern
        .setName("Bei Mail Versand von Formularen Anhang in DB speichern");
    return anhangspeichern;
  }
  
  public CheckboxInput getSummenAnlagenkonto() throws RemoteException 
  {
    if (summenAnlagenkonto != null) 
    {
      return summenAnlagenkonto;
    }
    summenAnlagenkonto = new CheckboxInput(Einstellungen.getEinstellung().getSummenAnlagenkonto());
    return summenAnlagenkonto;
  }
  
  public DecimalInput getAfaRestwert() throws RemoteException
  {
    if (afarestwert != null)
    {
      return afarestwert;
    }
    afarestwert = new DecimalInput(Einstellungen.getEinstellung().getAfaRestwert(),
        new DecimalFormat("###0.00"));
    return afarestwert;
  }
  
  public SelectInput getAfaOrt() throws RemoteException
  {
    if (afaort != null)
    {
      return afaort;
    }
    Boolean isinjahresabschluss = Einstellungen.getEinstellung().getAfaInJahresabschluss();
    if (isinjahresabschluss)
      afaort = new SelectInput(AfaOrt.getArray(), new AfaOrt(AfaOrt.JAHRESABSCHLUSS));
    else
      afaort = new SelectInput(AfaOrt.getArray(),  new AfaOrt(AfaOrt.ANLAGENBUCHUNGEN));
    return afaort;
  }

  public CheckboxInput getMittelverwendung() throws RemoteException
  {
    if (mittelverwendung != null)
    {
      return mittelverwendung;
    }
    mittelverwendung = new CheckboxInput(
        Einstellungen.getEinstellung().getMittelverwendung());
    return mittelverwendung;
  }

  public void handleStoreAllgemein()
  {
    try
    {
      Einstellung e = Einstellungen.getEinstellung();
      e.setID();
      e.setName((String) getName(false).getValue());
      e.setStrasse((String) getStrasse().getValue());
      e.setPlz((String) getPlz().getValue());
      e.setOrt((String) getOrt().getValue());
      e.setBic((String) getBic().getValue());
      String ib = (String) getIban().getValue();
      if (ib == null || ib.isBlank())
        e.setIban(null);
      else
        e.setIban(ib.toUpperCase().replace(" ",""));
      e.setGlaeubigerID((String) getGlaeubigerID().getValue());
      e.setStaat(((Staat)getStaat().getValue()).getKey());
      e.setUStID((String) getUstID().getValue());
      e.store();
      Einstellungen.setEinstellung(e);

      GUI.getStatusBar().setSuccessText("Einstellungen Allgemein gespeichert");
    }
    catch (RemoteException e)
    {
      try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
    catch (ApplicationException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
  }

  public void handleStoreAnzeige()
  {
    try
    {
      Einstellung e = Einstellungen.getEinstellung();
      e.setID();
      e.setGeburtsdatumPflicht((Boolean) geburtsdatumpflicht.getValue());
      e.setEintrittsdatumPflicht((Boolean) eintrittsdatumpflicht.getValue());
      e.setSterbedatum((Boolean) sterbedatum.getValue());
      e.setKommunikationsdaten((Boolean) kommunikationsdaten.getValue());
      e.setSekundaereBeitragsgruppen((Boolean) sekundaerebeitragsgruppen
          .getValue());
      e.setZusatzbetrag((Boolean) zusatzbetrag.getValue());
      e.setZusatzbetragAusgetretene((Boolean) zusatzbetragAusgetretene
          .getValue());
      e.setVermerke((Boolean) vermerke.getValue());
      e.setWiedervorlage((Boolean) wiedervorlage.getValue());
      e.setKursteilnehmer((Boolean) kursteilnehmer.getValue());
      e.setKursteilnehmerGebGesPflicht((Boolean) kursteilnehmergebgespflicht
          .getValue());
      e.setLehrgaenge((Boolean) lehrgaenge.getValue());
      e.setJuristischePersonen((Boolean) juristischepersonen.getValue());
      e.setMitgliedfoto((Boolean) mitgliedfoto.getValue());
      e.setUseLesefelder((Boolean) uselesefelder.getValue());
      e.setZusatzadressen((Boolean) zusatzadressen.getValue());
      e.setAuslandsadressen((Boolean) auslandsadressen.getValue());
      e.setArbeitseinsatz((Boolean) arbeitseinsatz.getValue());
      e.setDokumentenspeicherung((Boolean) dokumentenspeicherung.getValue());
      e.setIndividuelleBeitraege((Boolean) individuellebeitraege.getValue());
      e.setExterneMitgliedsnummer((Boolean) externemitgliedsnummer.getValue());
      e.setSummenAnlagenkonto((Boolean) summenAnlagenkonto.getValue());
      Altermodel amValue = (Altermodel) altersmodel.getValue();
      e.setAltersModel(amValue.getKey());
      AbstractInputAuswahl bbaAuswahl = (AbstractInputAuswahl) buchungBuchungsartAuswahl
          .getValue();
      e.setBuchungBuchungsartAuswahl(bbaAuswahl.getKey());
      AbstractInputAuswahl mAuswahl = (AbstractInputAuswahl) mitgliedAuswahl
          .getValue();
      e.setMitgliedAuswahl(mAuswahl.getKey());
      e.setBuchungsartSort(((BuchungsartSort) buchungsartsort.getValue())
          .getKey());
      if (((AfaOrt) afaort.getValue()).getKey() == 0)
        e.setAfaInJahresabschluss(false);
      else
        e.setAfaInJahresabschluss(true);
      e.setMittelverwendung((Boolean) mittelverwendung.getValue());

      e.store();
      Einstellungen.setEinstellung(e);

      GUI.getStatusBar().setSuccessText("Einstellungen gespeichert");
    }
    catch (RemoteException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
    catch (ApplicationException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
  }

  public void handleStoreAbrechnung()
  {
    try
    {
      Einstellung e = Einstellungen.getEinstellung();
      e.setID();
      Beitragsmodel bm = (Beitragsmodel) beitragsmodel.getValue();
      e.setBeitragsmodel(bm.getKey());
      ArbeitsstundenModel am = (ArbeitsstundenModel) arbeitsstundenmodel
          .getValue();
      e.setArbeitsstundenmodel(am.getKey());
      SepaMandatIdSource sepaSource = (SepaMandatIdSource) sepamandatidsourcemodel
          .getValue();
      e.setSepaMandatIdSource(sepaSource.getKey());
      Zahlungsrhythmus zr = (Zahlungsrhythmus) zahlungsrhytmus.getValue();
      e.setZahlungsrhytmus(zr.getKey());
      Zahlungsweg zw = (Zahlungsweg) zahlungsweg.getValue();
      e.setZahlungsweg(zw.getKey());
      SEPALandObject slo = (SEPALandObject) getDefaultSEPALand().getValue();
      e.setDefaultLand(slo.getLand().getKennzeichen());
      e.setSepaVersion((SepaVersion) sepaversion.getValue());
      e.setCt1SepaVersion((SepaVersion) ct1sepaversion.getValue());
      e.setSEPADatumOffset((Integer) sepadatumoffset.getValue());
      e.setAbrlAbschliessen((Boolean) abrlabschliessen.getValue());
      e.setBeitragAltersstufen((String)beitragaltersstufen.getValue());
      if (verrechnungskonto.getValue() != null)
      {
        e.setVerrechnungskontoId((Long
          .parseLong((String) ((Konto) verrechnungskonto.getValue()).getID())));
      }
      e.store();
      Einstellungen.setEinstellung(e);

      GUI.getStatusBar().setSuccessText("Einstellungen gespeichert");
    }
    catch (RemoteException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
    catch (ApplicationException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
  }

  public void handleStoreDateinamen()
  {
    try
    {
      Einstellung e = Einstellungen.getEinstellung();
      e.setID();
      e.setDateinamenmuster((String) dateinamenmuster.getValue());
      e.setDateinamenmusterSpende((String) dateinamenmusterspende.getValue());
      e.setVorlagenCsvVerzeichnis((String) vorlagenCsvVerzeichnis.getValue());
      e.store();
      Einstellungen.setEinstellung(e);
      GUI.getStatusBar().setSuccessText("Einstellungen gespeichert");
    }
    catch (RemoteException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
    catch (ApplicationException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
  }

  public void handleStoreSpendenbescheinigungen()
  {
    try
    {
      Einstellung e = Einstellungen.getEinstellung();
      e.setID();
      e.setFinanzamt((String) getFinanzamt().getValue());
      e.setSteuernummer((String) getSteuernummer().getValue());
      e.setBescheiddatum((Date) getBescheiddatum().getValue());
      e.setVorlaeufig((Boolean) getVorlaeufig().getValue());
      e.setVorlaeufigab((Date) getVorlaeufigab().getValue());
      e.setVeranlagungVon((Date) getVeranlagungVon().getValue());
      e.setVeranlagungBis((Date) getVeranlagungBis().getValue());
      e.setBeguenstigterzweck((String) getBeguenstigterzweck().getValue());
      e.setMitgliedsbeitraege((Boolean) getMitgliedsbetraege().getValue());
      e.setSpendenbescheinigungminbetrag((Double) spendenbescheinigungminbetrag
          .getValue());
      e.setSpendenbescheinigungverzeichnis((String) spendenbescheinigungverzeichnis
          .getValue());
      e.setSpendenbescheinigungPrintBuchungsart((Boolean) spendenbescheinigungprintbuchungsart
          .getValue());
      e.setUnterschriftdrucken((Boolean) unterschriftdrucken.getValue());
      e.setUnterschrift((byte[]) unterschrift.getValue());
      e.store();
      Einstellungen.setEinstellung(e);
      GUI.getStatusBar().setSuccessText("Einstellungen gespeichert");
    }
    catch (RemoteException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
    catch (ApplicationException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
  }

  public void handleStoreBuchfuehrung()
  {
    try
    {
      Einstellung e = Einstellungen.getEinstellung();
      e.setID();
      e.setBeginnGeschaeftsjahr((String) beginngeschaeftsjahr.getValue());
      e.setAutoBuchunguebernahme((Boolean) autobuchunguebernahme.getValue());
      e.setAutomatischeBuchungskorrekturHibiscus((Boolean) getAutomatischeBuchungskorrekturHibiscus().getValue());
      e.setUnterdrueckungOhneBuchung((Boolean) unterdrueckungohnebuchung
          .getValue());
      Integer ulength = (Integer) unterdrueckunglaenge.getValue();
      e.setUnterdrueckungLaenge(ulength);
      Integer klength = (Integer) unterdrueckungkonten.getValue();
      e.setUnterdrueckungKonten(klength);
      e.setAfaRestwert((Double) afarestwert.getValue());
      e.setKontonummerInBuchungsliste((Boolean) kontonummer_in_buchungsliste.getValue());
      e.setOptiert((Boolean) getOptiert().getValue());
      e.setBuchungsklasseInBuchung((Boolean) getFreieBuchungsklasse().getValue());
      e.setSplitPositionZweck((Boolean) getSplitPositionZweck().getValue());
      e.store();
      Einstellungen.setEinstellung(e);

      GUI.getStatusBar().setSuccessText("Einstellungen gespeichert");
    }
    catch (RemoteException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
    catch (ApplicationException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
  }

  public void handleStoreRechnungen()
  {
    try
    {
      Einstellung e = Einstellungen.getEinstellung();
      e.setID();
      e.setRechnungTextAbbuchung((String) rechnungtextabbuchung.getValue());
      e.setRechnungTextUeberweisung((String) rechnungtextueberweisung
          .getValue());
      e.setRechnungTextBar((String) rechnungtextbar.getValue());
      Integer length = (Integer) zaehlerlaenge.getValue();
      e.setZaehlerLaenge(length);
      e.setQRCodeSizeInMm((Integer) qrcodesize.getValue());
      e.setQRCodeDatum((Boolean) qrcodepdate.getValue());
      e.setQRCodeFesterText((Boolean) qrcodeptext.getValue());
      e.setQRCodeInfoM((String) qrcodeinfom.getValue());
      e.setQRCodeMember((Boolean) qrcodepmnum.getValue());
      e.setQRCodeReNu((Boolean) qrcodeprenum.getValue());
      e.setQRCodeSnglLine((Boolean) qrcodesngl.getValue());
      e.setQRCodeText((String) qrcodetext.getValue());
      e.setQRCodeIntro((String) qrcodeintro.getValue());
      e.setQRCodeKuerzen((Boolean) qrcodekuerzen.getValue());

      e.store();
      Einstellungen.setEinstellung(e);

      GUI.getStatusBar().setSuccessText("Einstellungen gespeichert");
    }
    catch (RemoteException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
    catch (ApplicationException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
  }

  public void handleStoreMitgliederSpalten()
  {
    try
    {
      spalten.save();
      GUI.getStatusBar().setSuccessText("Einstellungen gespeichert");
    }
    catch (RemoteException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
  }

  public void handleStoreMail()
  {
    try
    {
      Einstellung e = Einstellungen.getEinstellung();
      e.setID();
      e.setSmtpServer((String) smtp_server.getValue());
      Integer port = (Integer) smtp_port.getValue();
      if (port != null)
      {
        e.setSmtpPort(port.toString());
      }
      else
      {
        e.setSmtpPort(null);
      }
      e.setSmtpAuthUser((String) smtp_auth_user.getValue());
      e.setSmtpAuthPwd((String) smtp_auth_pwd.getValue());
      e.setSmtpFromAddress((String) smtp_from_address.getValue());
      e.setSmtpFromAnzeigename((String) smtp_from_anzeigename.getValue());
      e.setSmtpSsl((Boolean) smtp_ssl.getValue());
      e.setSmtpStarttls((Boolean) smtp_starttls.getValue());
      e.setMailVerzoegerung((Integer) mailverzoegerung.getValue());
      e.setMailAlwaysCc((String) alwaysCcTo.getValue());
      e.setMailAlwaysBcc((String) alwaysBccTo.getValue());

      e.setCopyToImapFolder((Boolean) copyToImapFolder.getValue());
      e.setImapHost((String) imapHost.getValue());
      port = (Integer) imapPort.getValue();
      if (port != null)
      {
        e.setImapPort(port.toString());
      }
      else
      {
        e.setImapPort(null);
      }
      e.setImapAuthUser((String) imapAuthUser.getValue());
      e.setImapAuthPwd(((String) imapAuthPwd.getValue()));
      e.setImapSsl((Boolean) imap_ssl.getValue());
      e.setImapStartTls((Boolean) imap_starttls.getValue());
      e.setImapSentFolder((String) imapSentFolder.getValue());
      e.setMailSignatur((String) mailsignatur.getValue());

      wallet.set("smtp_auth_pwd", e.getSmtpAuthPwd());
      wallet.set("imap_auth_pwd", e.getImapAuthPwd());
      
      e.setAnhangSpeichern((Boolean) anhangspeichern.getValue());

      e.store();
      Einstellungen.setEinstellung(e);

      GUI.getStatusBar().setSuccessText("Einstellungen gespeichert");
    }
    catch (Exception e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
  }

  public void handleStoreStatistik()
  {
    try
    {
      Einstellung e = Einstellungen.getEinstellung();
      e.setID();
      e.setAltersgruppen((String) getAltersgruppen().getValue());
      e.setJubilaeen((String) getJubilaeen().getValue());
      e.setAltersjubilaeen((String) getAltersjubilaeen().getValue());
      Integer jubilaeumStartAlter = (Integer) jubilarStartAlter.getValue();
      e.setJubilarStartAlter(jubilaeumStartAlter);
      e.store();
      Einstellungen.setEinstellung(e);

      GUI.getStatusBar().setSuccessText("Einstellungen gespeichert");
    }
    catch (RemoteException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
    catch (ApplicationException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
  }

  public void handleStoreMitgliedAnsicht()
  {
    try
    {
      Einstellung e = Einstellungen.getEinstellung();
      e.setID();

      e.setAnzahlSpaltenStammdaten((Integer) getAnzahlSpaltenStammdatenInput()
          .getValue());
      e.setAnzahlSpaltenLesefelder((Integer) getAnzahlSpaltenLesefelderInput()
          .getValue());
      e.setAnzahlSpaltenZusatzfelder((Integer) getAnzahlSpaltenZusatzfelderInput()
          .getValue());
      e.setAnzahlSpaltenMitgliedschaft((Integer) getAnzahlSpaltenMitgliedschaftInput()
          .getValue());
      e.setAnzahlSpaltenZahlung((Integer) getAnzahlSpaltenZahlungInput()
          .getValue());
      e.setZeigeStammdatenInTab((Boolean) getZeigeStammdatenInTabCheckbox()
          .getValue());
      e.setZeigeMitgliedschaftInTab((Boolean) getZeigeMitgliedschaftInTabCheckbox()
          .getValue());
      e.setZeigeZahlungInTab((Boolean) getZeigeZahlungInTabCheckbox()
          .getValue());
      e.setZeigeZusatzbetrageInTab((Boolean) getZeigeZusatzbetrageInTabCheckbox()
          .getValue());
      e.setZeigeMitgliedskontoInTab((Boolean) getZeigeMitgliedskontoInTabCheckbox()
          .getValue());
      e.setZeigeVermerkeInTab((Boolean) getZeigeVermerkeInTabCheckbox()
          .getValue());
      e.setZeigeWiedervorlageInTab((Boolean) getZeigeWiedervorlageInTabCheckbox()
          .getValue());
      e.setZeigeMailsInTab((Boolean) getZeigeMailsInTabCheckbox().getValue());
      e.setZeigeEigentschaftenInTab((Boolean) getZeigeEigenschaftenInTabCheckbox()
          .getValue());
      e.setZeigeZusatzfelderInTab((Boolean) getZeigeZusatzfelderInTabCheckbox()
          .getValue());
      e.setZeigeLehrgaengeInTab((Boolean) getZeigeLehrgaengeInTabCheckbox()
          .getValue());
      e.setZeigeFotoInTab((Boolean) getZeigeFotoInTabCheckbox().getValue());
      e.setZeigeLesefelderInTab((Boolean) getZeigeLesefelderInTabCheckbox()
          .getValue());
      e.setZeigeArbeitseinsatzInTab((Boolean) getZeigeArbeitseinsatzInTabCheckbox()
          .getValue());
      e.store();
      Einstellungen.setEinstellung(e);
      GUI.getStatusBar().setSuccessText("Einstellungen gespeichert");
    }
    catch (RemoteException e)
    {
	  try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
    catch (ApplicationException e)
    {
      try {
		Einstellungen.reloadEinstellung();
	  } catch (RemoteException e1) {
		  Logger.error("Reload der Einstellungen felgeschlagen");
	  }
      GUI.getStatusBar().setErrorText(e.getMessage());
    }
  }

  /**
   * Hilfsklasse zum Aktualisieren des Kommentars hinter SEPADatumOffset
   */
  private class SEPADatumOffsetListener implements Listener
  {
    @Override
    public void handleEvent(Event event)
    {
      try
      {
        int start = ((Integer) getSEPADatumOffset().getValue()).intValue();
        if (start == 1)
        {
          getSEPADatumOffset().setComment("1 Tage");
        }
        else
        {
          getSEPADatumOffset().setComment(start + " Tage");
        }
      }
      catch (Exception e)
      {
        Logger.error("unable to update comment", e);
      }
    }
  }

  /**
   * Hilfsklasse zum Aktualisieren des Kommentars von MailVerzoegerung.
   */
  private class MailVerzoegerungListener implements Listener
  {
    @Override
    public void handleEvent(Event event)
    {
      try
      {
        int pause = ((Integer) getMailVerzoegerung().getValue()).intValue();
        if (pause == 0)
        {
          getMailVerzoegerung().setComment("keine Pause");
        }
        else
        {
          getMailVerzoegerung().setComment(pause + " Millisekunden");
        }
      }
      catch (Exception e)
      {
        Logger.error("unable to update comment", e);
      }
    }
  }

}
