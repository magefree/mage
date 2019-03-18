
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author LevelX2
 */
public final class ClashPack extends ExpansionSet {

    private static final ClashPack instance = new ClashPack();

    public static ClashPack getInstance() {
        return instance;
    }

    private ClashPack() {
        super("Clash Pack", "CLASH", ExpansionSet.buildDate(2014, 7, 18), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Courser of Kruphix", 12, Rarity.SPECIAL, mage.cards.c.CourserOfKruphix.class));
        cards.add(new SetCardInfo("Fated Intervention", 2, Rarity.SPECIAL, mage.cards.f.FatedIntervention.class));
        cards.add(new SetCardInfo("Font of Fertility", 3, Rarity.SPECIAL, mage.cards.f.FontOfFertility.class));
        cards.add(new SetCardInfo("Hero's Downfall", 8, Rarity.SPECIAL, mage.cards.h.HerosDownfall.class));
        cards.add(new SetCardInfo("Hydra Broodmaster", 4, Rarity.SPECIAL, mage.cards.h.HydraBroodmaster.class));
        cards.add(new SetCardInfo("Necropolis Fiend", 7, Rarity.SPECIAL, mage.cards.n.NecropolisFiend.class));
        cards.add(new SetCardInfo("Prognostic Sphinx", 1, Rarity.SPECIAL, mage.cards.p.PrognosticSphinx.class));
        cards.add(new SetCardInfo("Prophet of Kruphix", 5, Rarity.SPECIAL, mage.cards.p.ProphetOfKruphix.class));
        cards.add(new SetCardInfo("Reaper of the Wilds", 10, Rarity.SPECIAL, mage.cards.r.ReaperOfTheWilds.class));
        cards.add(new SetCardInfo("Sultai Ascendancy", 9, Rarity.SPECIAL, mage.cards.s.SultaiAscendancy.class));
        cards.add(new SetCardInfo("Temple of Mystery", 6, Rarity.SPECIAL, mage.cards.t.TempleOfMystery.class));
        cards.add(new SetCardInfo("Whip of Erebos", 11, Rarity.SPECIAL, mage.cards.w.WhipOfErebos.class));
    }
}
