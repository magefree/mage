
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class UginsFate extends ExpansionSet {

    private static final UginsFate instance = new UginsFate();

    public static UginsFate getInstance() {
        return instance;
    }

    private UginsFate() {
        super("Ugin's Fate", "UGIN", ExpansionSet.buildDate(2015, 1, 16), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ainok Tracker", 96, Rarity.COMMON, mage.cards.a.AinokTracker.class));
        cards.add(new SetCardInfo("Altar of the Brood", 216, Rarity.RARE, mage.cards.a.AltarOfTheBrood.class));
        cards.add(new SetCardInfo("Arashin War Beast", 123, Rarity.UNCOMMON, mage.cards.a.ArashinWarBeast.class));
        cards.add(new SetCardInfo("Arc Lightning", 97, Rarity.UNCOMMON, mage.cards.a.ArcLightning.class));
        cards.add(new SetCardInfo("Briber's Purse", 217, Rarity.UNCOMMON, mage.cards.b.BribersPurse.class));
        cards.add(new SetCardInfo("Debilitating Injury", 68, Rarity.COMMON, mage.cards.d.DebilitatingInjury.class));
        cards.add(new SetCardInfo("Dragonscale Boon", 131, Rarity.COMMON, mage.cards.d.DragonscaleBoon.class));
        cards.add(new SetCardInfo("Fierce Invocation", 98, Rarity.COMMON, mage.cards.f.FierceInvocation.class));
        cards.add(new SetCardInfo("Formless Nurturing", 129, Rarity.COMMON, mage.cards.f.FormlessNurturing.class));
        cards.add(new SetCardInfo("Ghostfire Blade", 220, Rarity.RARE, mage.cards.g.GhostfireBlade.class));
        cards.add(new SetCardInfo("Grim Haruspex", 73, Rarity.RARE, mage.cards.g.GrimHaruspex.class));
        cards.add(new SetCardInfo("Hewed Stone Retainers", 161, Rarity.UNCOMMON, mage.cards.h.HewedStoneRetainers.class));
        cards.add(new SetCardInfo("Jeering Instigator", 113, Rarity.RARE, mage.cards.j.JeeringInstigator.class));
        cards.add(new SetCardInfo("Jeskai Infiltrator", 36, Rarity.RARE, mage.cards.j.JeskaiInfiltrator.class));
        cards.add(new SetCardInfo("Mastery of the Unseen", 19, Rarity.RARE, mage.cards.m.MasteryOfTheUnseen.class));
        cards.add(new SetCardInfo("Mystic of the Hidden Way", 48, Rarity.COMMON, mage.cards.m.MysticOfTheHiddenWay.class));
        cards.add(new SetCardInfo("Reality Shift", 46, Rarity.UNCOMMON, mage.cards.r.RealityShift.class));
        cards.add(new SetCardInfo("Ruthless Ripper", 88, Rarity.UNCOMMON, mage.cards.r.RuthlessRipper.class));
        cards.add(new SetCardInfo("Smite the Monstrous", 24, Rarity.COMMON, mage.cards.s.SmiteTheMonstrous.class));
        cards.add(new SetCardInfo("Soul Summons", 26, Rarity.COMMON, mage.cards.s.SoulSummons.class));
        cards.add(new SetCardInfo("Sultai Emissary", 85, Rarity.COMMON, mage.cards.s.SultaiEmissary.class));
        cards.add(new SetCardInfo("Ugin's Construct", 164, Rarity.UNCOMMON, mage.cards.u.UginsConstruct.class));
        cards.add(new SetCardInfo("Ugin, the Spirit Dragon", 1, Rarity.MYTHIC, mage.cards.u.UginTheSpiritDragon.class));
        cards.add(new SetCardInfo("Watcher of the Roost", 30, Rarity.UNCOMMON, mage.cards.w.WatcherOfTheRoost.class));
        cards.add(new SetCardInfo("Wildcall", 146, Rarity.RARE, mage.cards.w.Wildcall.class));
        cards.add(new SetCardInfo("Write into Being", 59, Rarity.COMMON, mage.cards.w.WriteIntoBeing.class));
    }
}
