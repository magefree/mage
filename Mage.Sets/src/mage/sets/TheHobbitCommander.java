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

        cards.add(new SetCardInfo("Anduril, Flame of the West", 39, Rarity.MYTHIC, mage.cards.a.AndurilFlameOfTheWest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anduril, Flame of the West", 79, Rarity.MYTHIC, mage.cards.a.AndurilFlameOfTheWest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anduril, Narsil Reforged", 40, Rarity.MYTHIC, mage.cards.a.AndurilNarsilReforged.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anduril, Narsil Reforged", 80, Rarity.MYTHIC, mage.cards.a.AndurilNarsilReforged.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aragorn and Arwen, Wed", 28, Rarity.MYTHIC, mage.cards.a.AragornAndArwenWed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aragorn and Arwen, Wed", 68, Rarity.MYTHIC, mage.cards.a.AragornAndArwenWed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aragorn, the Uniter", 29, Rarity.MYTHIC, mage.cards.a.AragornTheUniter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aragorn, the Uniter", 69, Rarity.MYTHIC, mage.cards.a.AragornTheUniter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcane Signet", 95, Rarity.MYTHIC, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Arwen, Mortal Queen", 30, Rarity.MYTHIC, mage.cards.a.ArwenMortalQueen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arwen, Mortal Queen", 70, Rarity.MYTHIC, mage.cards.a.ArwenMortalQueen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arwen, Weaver of Hope", 24, Rarity.MYTHIC, mage.cards.a.ArwenWeaverOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arwen, Weaver of Hope", 64, Rarity.MYTHIC, mage.cards.a.ArwenWeaverOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bag End Banquet", 5, Rarity.RARE, mage.cards.b.BagEndBanquet.class));
        cards.add(new SetCardInfo("Bilbo's Ring", 41, Rarity.MYTHIC, mage.cards.b.BilbosRing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bilbo's Ring", 81, Rarity.MYTHIC, mage.cards.b.BilbosRing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bilbo, Fellow Conspirator", 4, Rarity.RARE, mage.cards.b.BilboFellowConspirator.class));
        cards.add(new SetCardInfo("Call Forth the Tempest", 22, Rarity.MYTHIC, mage.cards.c.CallForthTheTempest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Call Forth the Tempest", 62, Rarity.MYTHIC, mage.cards.c.CallForthTheTempest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cavern-Hoard Dragon", 23, Rarity.MYTHIC, mage.cards.c.CavernHoardDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cavern-Hoard Dragon", 63, Rarity.MYTHIC, mage.cards.c.CavernHoardDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Delighted Halfling", 25, Rarity.MYTHIC, mage.cards.d.DelightedHalfling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Delighted Halfling", 65, Rarity.MYTHIC, mage.cards.d.DelightedHalfling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dwarven Warriors", 93, Rarity.MYTHIC, mage.cards.d.DwarvenWarriors.class));
        cards.add(new SetCardInfo("Elven Chorus", 26, Rarity.MYTHIC, mage.cards.e.ElvenChorus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elven Chorus", 66, Rarity.MYTHIC, mage.cards.e.ElvenChorus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fili and Kili, Joyous", 1, Rarity.RARE, mage.cards.f.FiliAndKiliJoyous.class));
        cards.add(new SetCardInfo("Flame of Anor", 31, Rarity.MYTHIC, mage.cards.f.FlameOfAnor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flame of Anor", 71, Rarity.MYTHIC, mage.cards.f.FlameOfAnor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flowering of the White Tree", 14, Rarity.MYTHIC, mage.cards.f.FloweringOfTheWhiteTree.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flowering of the White Tree", 54, Rarity.MYTHIC, mage.cards.f.FloweringOfTheWhiteTree.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Galadriel's Dismissal", 16, Rarity.MYTHIC, mage.cards.g.GaladrielsDismissal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Galadriel's Dismissal", 56, Rarity.MYTHIC, mage.cards.g.GaladrielsDismissal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Last March of the Ents", 27, Rarity.MYTHIC, mage.cards.l.LastMarchOfTheEnts.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Last March of the Ents", 67, Rarity.MYTHIC, mage.cards.l.LastMarchOfTheEnts.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Minas Morgul, Dark Fortress", 48, Rarity.MYTHIC, mage.cards.m.MinasMorgulDarkFortress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Minas Morgul, Dark Fortress", 88, Rarity.MYTHIC, mage.cards.m.MinasMorgulDarkFortress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Minas Tirith", 49, Rarity.MYTHIC, mage.cards.m.MinasTirith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Minas Tirith", 89, Rarity.MYTHIC, mage.cards.m.MinasTirith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mithril Coat", 43, Rarity.MYTHIC, mage.cards.m.MithrilCoat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mithril Coat", 83, Rarity.MYTHIC, mage.cards.m.MithrilCoat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mount Doom", 50, Rarity.MYTHIC, mage.cards.m.MountDoom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mount Doom", 90, Rarity.MYTHIC, mage.cards.m.MountDoom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Amber", 96, Rarity.MYTHIC, mage.cards.m.MoxAmber.class));
        cards.add(new SetCardInfo("Necklace of Girion", 12, Rarity.RARE, mage.cards.n.NecklaceOfGirion.class));
        cards.add(new SetCardInfo("Orcish Bowmasters", 19, Rarity.MYTHIC, mage.cards.o.OrcishBowmasters.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orcish Bowmasters", 59, Rarity.MYTHIC, mage.cards.o.OrcishBowmasters.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Palantir of Orthanc", 45, Rarity.MYTHIC, mage.cards.p.PalantirOfOrthanc.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Palantir of Orthanc", 85, Rarity.MYTHIC, mage.cards.p.PalantirOfOrthanc.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Relic of Sauron", 46, Rarity.MYTHIC, mage.cards.r.RelicOfSauron.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Relic of Sauron", 86, Rarity.MYTHIC, mage.cards.r.RelicOfSauron.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reprieve", 17, Rarity.MYTHIC, mage.cards.r.Reprieve.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reprieve", 57, Rarity.MYTHIC, mage.cards.r.Reprieve.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rivendell", 51, Rarity.MYTHIC, mage.cards.r.Rivendell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rivendell", 91, Rarity.MYTHIC, mage.cards.r.Rivendell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Saruman of Many Colors", 35, Rarity.MYTHIC, mage.cards.s.SarumanOfManyColors.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Saruman of Many Colors", 75, Rarity.MYTHIC, mage.cards.s.SarumanOfManyColors.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sauron, the Dark Lord", 36, Rarity.MYTHIC, mage.cards.s.SauronTheDarkLord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sauron, the Dark Lord", 76, Rarity.MYTHIC, mage.cards.s.SauronTheDarkLord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sauron, the Lidless Eye", 37, Rarity.MYTHIC, mage.cards.s.SauronTheLidlessEye.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sauron, the Lidless Eye", 77, Rarity.MYTHIC, mage.cards.s.SauronTheLidlessEye.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Gaffer", 15, Rarity.MYTHIC, mage.cards.t.TheGaffer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Gaffer", 55, Rarity.MYTHIC, mage.cards.t.TheGaffer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The One Ring", 44, Rarity.MYTHIC, mage.cards.t.TheOneRing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The One Ring", 84, Rarity.MYTHIC, mage.cards.t.TheOneRing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Reaver Cleaver", 94, Rarity.MYTHIC, mage.cards.t.TheReaverCleaver.class));
        cards.add(new SetCardInfo("The Shire", 52, Rarity.MYTHIC, mage.cards.t.TheShire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Shire", 92, Rarity.MYTHIC, mage.cards.t.TheShire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tom Bombadil", 38, Rarity.MYTHIC, mage.cards.t.TomBombadil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tom Bombadil", 78, Rarity.MYTHIC, mage.cards.t.TomBombadil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Treasure Vault", 97, Rarity.MYTHIC, mage.cards.t.TreasureVault.class));
        cards.add(new SetCardInfo("Witch-king, Bringer of Ruin", 21, Rarity.MYTHIC, mage.cards.w.WitchKingBringerOfRuin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Witch-king, Bringer of Ruin", 61, Rarity.MYTHIC, mage.cards.w.WitchKingBringerOfRuin.class, NON_FULL_USE_VARIOUS));
    }
}
