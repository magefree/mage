package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class InnistradMidnightHunt extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Arlinn, the Pack's Hope", "Arlinn, the Moon's Fury", "Brutal Cathar", "Moonrage Brute", "Tavern Ruffian", "Tavern Smasher", "Tovolar, Dire Overlord", "Tovolar, the Midnight Scourge", "Village Watch", "Village Reavers");
    private static final InnistradMidnightHunt instance = new InnistradMidnightHunt();

    public static InnistradMidnightHunt getInstance() {
        return instance;
    }

    private InnistradMidnightHunt() {
        super("Innistrad: Midnight Hunt", "MID", ExpansionSet.buildDate(2021, 9, 24), SetType.EXPANSION);
        this.blockName = "Innistrad: Midnight Hunt";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.numBoosterDoubleFaced = 1;

        cards.add(new SetCardInfo("Arrogant Outlaw", 84, Rarity.COMMON, mage.cards.a.ArrogantOutlaw.class));
        cards.add(new SetCardInfo("Candlelit Cavalry", 175, Rarity.COMMON, mage.cards.c.CandlelitCavalry.class));
        cards.add(new SetCardInfo("Champion of the Perished", 91, Rarity.RARE, mage.cards.c.ChampionOfThePerished.class));
        cards.add(new SetCardInfo("Consider", 44, Rarity.COMMON, mage.cards.c.Consider.class));
        cards.add(new SetCardInfo("Dawnhart Rejuvenator", 180, Rarity.COMMON, mage.cards.d.DawnhartRejuvenator.class));
        cards.add(new SetCardInfo("Defenestrate", 95, Rarity.COMMON, mage.cards.d.Defenestrate.class));
        cards.add(new SetCardInfo("Deserted Beach", 260, Rarity.RARE, mage.cards.d.DesertedBeach.class));
        cards.add(new SetCardInfo("Festival Crasher", 140, Rarity.COMMON, mage.cards.f.FestivalCrasher.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Haunted Ridge", 263, Rarity.RARE, mage.cards.h.HauntedRidge.class));
        cards.add(new SetCardInfo("Infernal Grasp", 107, Rarity.UNCOMMON, mage.cards.i.InfernalGrasp.class));
        cards.add(new SetCardInfo("Island", 270, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Jadar, Ghoulcaller of Nephalia", 108, Rarity.RARE, mage.cards.j.JadarGhoulcallerOfNephalia.class));
        cards.add(new SetCardInfo("Join the Dance", 229, Rarity.UNCOMMON, mage.cards.j.JoinTheDance.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Farmland", 265, Rarity.RARE, mage.cards.o.OvergrownFarmland.class));
        cards.add(new SetCardInfo("Pestilent Wolf", 192, Rarity.COMMON, mage.cards.p.PestilentWolf.class));
        cards.add(new SetCardInfo("Plains", 268, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Play with Fire", 154, Rarity.UNCOMMON, mage.cards.p.PlayWithFire.class));
        cards.add(new SetCardInfo("Rockfall Vale", 266, Rarity.RARE, mage.cards.r.RockfallVale.class));
        cards.add(new SetCardInfo("Shipwreck Marsh", 267, Rarity.RARE, mage.cards.s.ShipwreckMarsh.class));
        cards.add(new SetCardInfo("Snarling Wolf", 199, Rarity.COMMON, mage.cards.s.SnarlingWolf.class));
        cards.add(new SetCardInfo("Stormrider Spirit", 79, Rarity.COMMON, mage.cards.s.StormriderSpirit.class));
        cards.add(new SetCardInfo("Swamp", 272, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Tavern Ruffian", 163, Rarity.COMMON, mage.cards.t.TavernRuffian.class));
        cards.add(new SetCardInfo("Tavern Smasher", 163, Rarity.COMMON, mage.cards.t.TavernSmasher.class));
        cards.add(new SetCardInfo("Triskaidekaphile", 81, Rarity.RARE, mage.cards.t.Triskaidekaphile.class));
        cards.add(new SetCardInfo("Unruly Mob", 40, Rarity.COMMON, mage.cards.u.UnrulyMob.class));
        cards.add(new SetCardInfo("Village Reavers", 165, Rarity.UNCOMMON, mage.cards.v.VillageReavers.class));
        cards.add(new SetCardInfo("Village Watch", 165, Rarity.UNCOMMON, mage.cards.v.VillageWatch.class));
        cards.add(new SetCardInfo("Wrenn and Seven", 208, Rarity.MYTHIC, mage.cards.w.WrennAndSeven.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is fully implemented
    }
}
