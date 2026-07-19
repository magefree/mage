package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class TheHobbitCommander extends ExpansionSet {

    private static final TheHobbitCommander instance = new TheHobbitCommander();

    public static TheHobbitCommander getInstance() {
        return instance;
    }

    private TheHobbitCommander() {
        super("The Hobbit Commander", "HOC", ExpansionSet.buildDate(2026, 8, 14), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arcane Signet", 95, Rarity.MYTHIC, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Bag End Banquet", 5, Rarity.RARE, mage.cards.b.BagEndBanquet.class));
        cards.add(new SetCardInfo("Bilbo, Fellow Conspirator", 4, Rarity.RARE, mage.cards.b.BilboFellowConspirator.class));
        cards.add(new SetCardInfo("Call Forth the Tempest", 22, Rarity.MYTHIC, mage.cards.c.CallForthTheTempest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Call Forth the Tempest", 62, Rarity.MYTHIC, mage.cards.c.CallForthTheTempest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dwarven Warriors", 93, Rarity.MYTHIC, mage.cards.d.DwarvenWarriors.class));
        cards.add(new SetCardInfo("Fili and Kili, Joyous", 1, Rarity.RARE, mage.cards.f.FiliAndKiliJoyous.class));
        cards.add(new SetCardInfo("Flame of Anor", 31, Rarity.MYTHIC, mage.cards.f.FlameOfAnor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flame of Anor", 71, Rarity.MYTHIC, mage.cards.f.FlameOfAnor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Galadriel's Dismissal", 16, Rarity.MYTHIC, mage.cards.g.GaladrielsDismissal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Galadriel's Dismissal", 56, Rarity.MYTHIC, mage.cards.g.GaladrielsDismissal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Amber", 96, Rarity.MYTHIC, mage.cards.m.MoxAmber.class));
        cards.add(new SetCardInfo("Orcish Bowmasters", 19, Rarity.MYTHIC, mage.cards.o.OrcishBowmasters.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orcish Bowmasters", 59, Rarity.MYTHIC, mage.cards.o.OrcishBowmasters.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reprieve", 17, Rarity.MYTHIC, mage.cards.r.Reprieve.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reprieve", 57, Rarity.MYTHIC, mage.cards.r.Reprieve.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rivendell", 51, Rarity.MYTHIC, mage.cards.r.Rivendell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rivendell", 91, Rarity.MYTHIC, mage.cards.r.Rivendell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sauron, the Dark Lord", 36, Rarity.MYTHIC, mage.cards.s.SauronTheDarkLord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sauron, the Dark Lord", 76, Rarity.MYTHIC, mage.cards.s.SauronTheDarkLord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The One Ring", 44, Rarity.MYTHIC, mage.cards.t.TheOneRing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The One Ring", 84, Rarity.MYTHIC, mage.cards.t.TheOneRing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Reaver Cleaver", 94, Rarity.MYTHIC, mage.cards.t.TheReaverCleaver.class));
        cards.add(new SetCardInfo("The Shire", 52, Rarity.MYTHIC, mage.cards.t.TheShire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Shire", 92, Rarity.MYTHIC, mage.cards.t.TheShire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tom Bombadil", 38, Rarity.MYTHIC, mage.cards.t.TomBombadil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tom Bombadil", 78, Rarity.MYTHIC, mage.cards.t.TomBombadil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Treasure Vault", 97, Rarity.MYTHIC, mage.cards.t.TreasureVault.class));
    }
}
