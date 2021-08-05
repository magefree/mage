package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class HistoricAnthology5 extends ExpansionSet {

    private static final HistoricAnthology5 instance = new HistoricAnthology5();

    public static HistoricAnthology5 getInstance() {
        return instance;
    }

    private HistoricAnthology5() {
        super("Historic Anthology 5", "HA5", ExpansionSet.buildDate(2021, 5, 27), SetType.MAGIC_ARENA);
        this.blockName = "Reprint";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ancient Grudge", 12, Rarity.COMMON, mage.cards.a.AncientGrudge.class));
        cards.add(new SetCardInfo("Atarka's Command", 21, Rarity.RARE, mage.cards.a.AtarkasCommand.class));
        cards.add(new SetCardInfo("Court Homunculus", 1, Rarity.COMMON, mage.cards.c.CourtHomunculus.class));
        cards.add(new SetCardInfo("Dragonstorm", 13, Rarity.RARE, mage.cards.d.Dragonstorm.class));
        cards.add(new SetCardInfo("Dromoka's Command", 22, Rarity.RARE, mage.cards.d.DromokasCommand.class));
        cards.add(new SetCardInfo("Elesh Norn, Grand Cenobite", 2, Rarity.MYTHIC, mage.cards.e.EleshNornGrandCenobite.class));
        cards.add(new SetCardInfo("Grisly Salvage", 23, Rarity.COMMON, mage.cards.g.GrislySalvage.class));
        cards.add(new SetCardInfo("Ichor Wellspring", 24, Rarity.COMMON, mage.cards.i.IchorWellspring.class));
        cards.add(new SetCardInfo("Intangible Virtue", 3, Rarity.UNCOMMON, mage.cards.i.IntangibleVirtue.class));
        cards.add(new SetCardInfo("Into the North", 16, Rarity.COMMON, mage.cards.i.IntoTheNorth.class));
        cards.add(new SetCardInfo("Jin-Gitaxias, Core Augur", 5, Rarity.MYTHIC, mage.cards.j.JinGitaxiasCoreAugur.class));
        cards.add(new SetCardInfo("Kolaghan's Command", 20, Rarity.RARE, mage.cards.k.KolaghansCommand.class));
        cards.add(new SetCardInfo("Merfolk Looter", 6, Rarity.COMMON, mage.cards.m.MerfolkLooter.class));
        cards.add(new SetCardInfo("Ojutai's Command", 18, Rarity.RARE, mage.cards.o.OjutaisCommand.class));
        cards.add(new SetCardInfo("Ray of Revelation", 4, Rarity.COMMON, mage.cards.r.RayOfRevelation.class));
        cards.add(new SetCardInfo("Relic of Progenitus", 25, Rarity.COMMON, mage.cards.r.RelicOfProgenitus.class));
        cards.add(new SetCardInfo("Reverse Engineer", 7, Rarity.UNCOMMON, mage.cards.r.ReverseEngineer.class));
        cards.add(new SetCardInfo("Sheoldred, Whispering One", 10, Rarity.MYTHIC, mage.cards.s.SheoldredWhisperingOne.class));
        cards.add(new SetCardInfo("Silumgar's Command", 19, Rarity.RARE, mage.cards.s.SilumgarsCommand.class));
        cards.add(new SetCardInfo("Stifle", 8, Rarity.RARE, mage.cards.s.Stifle.class));
        cards.add(new SetCardInfo("Trash for Treasure", 14, Rarity.UNCOMMON, mage.cards.t.TrashForTreasure.class));
        cards.add(new SetCardInfo("Urabrask the Hidden", 15, Rarity.MYTHIC, mage.cards.u.UrabraskTheHidden.class));
        cards.add(new SetCardInfo("Vault Skirge", 11, Rarity.COMMON, mage.cards.v.VaultSkirge.class));
        cards.add(new SetCardInfo("Vorinclex, Voice of Hunger", 17, Rarity.MYTHIC, mage.cards.v.VorinclexVoiceOfHunger.class));
        cards.add(new SetCardInfo("Whirler Rogue", 9, Rarity.UNCOMMON, mage.cards.w.WhirlerRogue.class));
    }
}
