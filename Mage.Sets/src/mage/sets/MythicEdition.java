package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author JayDi85
 */
public final class MythicEdition extends ExpansionSet {

    private static final MythicEdition instance = new MythicEdition();

    public static MythicEdition getInstance() {
        return instance;
    }

    private MythicEdition() {
        super("Mythic Edition", "MEDM", ExpansionSet.buildDate(2018, 10, 5), SetType.SUPPLEMENTAL);  // MEDM cause MED uses for master edition
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ajani, Mentor of Heroes", "RA5", Rarity.MYTHIC, mage.cards.a.AjaniMentorOfHeroes.class));
        cards.add(new SetCardInfo("Dack Fayden", "RA6", Rarity.MYTHIC, mage.cards.d.DackFayden.class));
        cards.add(new SetCardInfo("Daretti, Ingenious Iconoclast", "GR3", Rarity.MYTHIC, mage.cards.d.DarettiIngeniousIconoclast.class));
        cards.add(new SetCardInfo("Domri, Chaos Bringer", "RA7", Rarity.MYTHIC, mage.cards.d.DomriChaosBringer.class));
        cards.add(new SetCardInfo("Elspeth, Knight-Errant", "GR1", Rarity.MYTHIC, mage.cards.e.ElspethKnightErrant.class));
        cards.add(new SetCardInfo("Jaya Ballard", "RA4", Rarity.MYTHIC, mage.cards.j.JayaBallard.class));
        cards.add(new SetCardInfo("Karn, Scion of Urza", "RA1", Rarity.MYTHIC, mage.cards.k.KarnScionOfUrza.class));
        cards.add(new SetCardInfo("Kaya, Orzhov Usurper", "RA8", Rarity.MYTHIC, mage.cards.k.KayaOrzhovUsurper.class));
        cards.add(new SetCardInfo("Liliana, the Last Hope", "GR2", Rarity.MYTHIC, mage.cards.l.LilianaTheLastHope.class));
        cards.add(new SetCardInfo("Nicol Bolas, Planeswalker", "GR4", Rarity.MYTHIC, mage.cards.n.NicolBolasPlaneswalker.class));
        cards.add(new SetCardInfo("Ral, Izzet Viceroy", "GR5", Rarity.MYTHIC, mage.cards.r.RalIzzetViceroy.class));
        cards.add(new SetCardInfo("Sorin Markov", "RA3", Rarity.MYTHIC, mage.cards.s.SorinMarkov.class));
        cards.add(new SetCardInfo("Tamiyo, the Moon Sage", "RA2", Rarity.MYTHIC, mage.cards.t.TamiyoTheMoonSage.class));
        cards.add(new SetCardInfo("Teferi, Hero of Dominaria", "GR6", Rarity.MYTHIC, mage.cards.t.TeferiHeroOfDominaria.class));
        cards.add(new SetCardInfo("Tezzeret, Agent of Bolas", "GR7", Rarity.MYTHIC, mage.cards.t.TezzeretAgentOfBolas.class));
        cards.add(new SetCardInfo("Vraska, Golgari Queen", "GR8", Rarity.MYTHIC, mage.cards.v.VraskaGolgariQueen.class));
    }
}
