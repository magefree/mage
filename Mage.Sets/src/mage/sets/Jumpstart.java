package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Jumpstart extends ExpansionSet {

    private static final Jumpstart instance = new Jumpstart();

    public static Jumpstart getInstance() {
        return instance;
    }

    private Jumpstart() {
        super("Jumpstart", "JMP", ExpansionSet.buildDate(2020, 7, 17), SetType.SUPPLEMENTAL);
        this.blockName = "Jumpstart";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Alloy Myr", 457, Rarity.COMMON, mage.cards.a.AlloyMyr.class));
        cards.add(new SetCardInfo("Auger Spree", 449, Rarity.COMMON, mage.cards.a.AugerSpree.class));
        cards.add(new SetCardInfo("Bone Picker", 212, Rarity.UNCOMMON, mage.cards.b.BonePicker.class));
        cards.add(new SetCardInfo("Chamber Sentry", 461, Rarity.RARE, mage.cards.c.ChamberSentry.class));
        cards.add(new SetCardInfo("Dinrova Horror", 450, Rarity.UNCOMMON, mage.cards.d.DinrovaHorror.class));
        cards.add(new SetCardInfo("Dutiful Attendant", 226, Rarity.COMMON, mage.cards.d.DutifulAttendant.class));
        cards.add(new SetCardInfo("Fusion Elemental", 451, Rarity.UNCOMMON, mage.cards.f.FusionElemental.class));
        cards.add(new SetCardInfo("Ghoulcaller's Accomplice", 237, Rarity.COMMON, mage.cards.g.GhoulcallersAccomplice.class));
        cards.add(new SetCardInfo("Innocent Blood", 244, Rarity.COMMON, mage.cards.i.InnocentBlood.class));
        cards.add(new SetCardInfo("Ironroot Warlord", 452, Rarity.UNCOMMON, mage.cards.i.IronrootWarlord.class));
        cards.add(new SetCardInfo("Lawmage's Binding", 453, Rarity.COMMON, mage.cards.l.LawmagesBinding.class));
        cards.add(new SetCardInfo("Maelstrom Archangel", 454, Rarity.MYTHIC, mage.cards.m.MaelstromArchangel.class));
        cards.add(new SetCardInfo("Mirrodin's Core", 492, Rarity.UNCOMMON, mage.cards.m.MirrodinsCore.class));
        cards.add(new SetCardInfo("Prophetic Prism", 478, Rarity.COMMON, mage.cards.p.PropheticPrism.class));
        cards.add(new SetCardInfo("Raging Regisaur", 455, Rarity.UNCOMMON, mage.cards.r.RagingRegisaur.class));
        cards.add(new SetCardInfo("Rupture Spire", 495, Rarity.COMMON, mage.cards.r.RuptureSpire.class));
        cards.add(new SetCardInfo("Shambling Goblin", 277, Rarity.COMMON, mage.cards.s.ShamblingGoblin.class));
        cards.add(new SetCardInfo("Skittering Surveyor", 486, Rarity.COMMON, mage.cards.s.SkitteringSurveyor.class));
        cards.add(new SetCardInfo("Swamp", 54, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terramorphic Expanse", 78, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
    }
}
