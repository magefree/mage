package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author JayDi85
 */
public final class ArenaNewPlayerExperience extends ExpansionSet {

    private static final ArenaNewPlayerExperience instance = new ArenaNewPlayerExperience();

    public static ArenaNewPlayerExperience getInstance() {
        return instance;
    }

    private ArenaNewPlayerExperience() {
        super("Arena New Player Experience", "ANA", ExpansionSet.buildDate(2018, 7, 29), SetType.MAGIC_ONLINE);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Altar's Reap", 24, Rarity.COMMON, mage.cards.a.AltarsReap.class));
        cards.add(new SetCardInfo("Ambition's Cost", 25, Rarity.UNCOMMON, mage.cards.a.AmbitionsCost.class));
        cards.add(new SetCardInfo("Angelic Reward", 1, Rarity.UNCOMMON, mage.cards.a.AngelicReward.class));
        cards.add(new SetCardInfo("Blinding Radiance", 2, Rarity.UNCOMMON, mage.cards.b.BlindingRadiance.class));
        cards.add(new SetCardInfo("Chaos Maw", 36, Rarity.RARE, mage.cards.c.ChaosMaw.class));
        cards.add(new SetCardInfo("Confront the Assault", 3, Rarity.UNCOMMON, mage.cards.c.ConfrontTheAssault.class));
        cards.add(new SetCardInfo("Cruel Cut", 26, Rarity.COMMON, mage.cards.c.CruelCut.class));
        cards.add(new SetCardInfo("Divination", 14, Rarity.COMMON, mage.cards.d.Divination.class));
        cards.add(new SetCardInfo("Doublecast", 37, Rarity.UNCOMMON, mage.cards.d.Doublecast.class));
        cards.add(new SetCardInfo("Feral Roar", 46, Rarity.COMMON, mage.cards.f.FeralRoar.class));
        cards.add(new SetCardInfo("Goblin Bruiser", 39, Rarity.UNCOMMON, mage.cards.g.GoblinBruiser.class));
        cards.add(new SetCardInfo("Goblin Gang Leader", 40, Rarity.UNCOMMON, mage.cards.g.GoblinGangLeader.class));
        cards.add(new SetCardInfo("Goblin Grenade", 41, Rarity.UNCOMMON, mage.cards.g.GoblinGrenade.class));
        cards.add(new SetCardInfo("Inspiring Commander", 5, Rarity.RARE, mage.cards.i.InspiringCommander.class));
        cards.add(new SetCardInfo("Knight's Pledge", 6, Rarity.COMMON, mage.cards.k.KnightsPledge.class));
        cards.add(new SetCardInfo("Loxodon Line Breaker", 7, Rarity.COMMON, mage.cards.l.LoxodonLineBreaker.class));
        cards.add(new SetCardInfo("Miasmic Mummy", 29, Rarity.COMMON, mage.cards.m.MiasmicMummy.class));
        cards.add(new SetCardInfo("Nimble Pilferer", 31, Rarity.COMMON, mage.cards.n.NimblePilferer.class));
        cards.add(new SetCardInfo("Ogre Painbringer", 42, Rarity.RARE, mage.cards.o.OgrePainbringer.class));
        cards.add(new SetCardInfo("Overflowing Insight", 16, Rarity.MYTHIC, mage.cards.o.OverflowingInsight.class));
        cards.add(new SetCardInfo("Raging Goblin", 43, Rarity.COMMON, mage.cards.r.RagingGoblin.class));
        cards.add(new SetCardInfo("Renegade Demon", 33, Rarity.COMMON, mage.cards.r.RenegadeDemon.class));
        cards.add(new SetCardInfo("Rise from the Grave", 34, Rarity.UNCOMMON, mage.cards.r.RiseFromTheGrave.class));
        cards.add(new SetCardInfo("River's Favor", 17, Rarity.COMMON, mage.cards.r.RiversFavor.class));
        cards.add(new SetCardInfo("Rumbling Baloth", 47, Rarity.COMMON, mage.cards.r.RumblingBaloth.class));
        cards.add(new SetCardInfo("Sanctuary Cat", 8, Rarity.COMMON, mage.cards.s.SanctuaryCat.class));
        cards.add(new SetCardInfo("Seismic Rupture", 44, Rarity.UNCOMMON, mage.cards.s.SeismicRupture.class));
        cards.add(new SetCardInfo("Serra Angel", 9, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shorecomber Crab", 18, Rarity.COMMON, mage.cards.s.ShorecomberCrab.class));
        cards.add(new SetCardInfo("Shrine Keeper", 10, Rarity.COMMON, mage.cards.s.ShrineKeeper.class));
        cards.add(new SetCardInfo("Soulhunter Rakshasa", 35, Rarity.RARE, mage.cards.s.SoulhunterRakshasa.class));
        cards.add(new SetCardInfo("Spiritual Guardian", 11, Rarity.COMMON, mage.cards.s.SpiritualGuardian.class));
        cards.add(new SetCardInfo("Tactical Advantage", 12, Rarity.COMMON, mage.cards.t.TacticalAdvantage.class));
        cards.add(new SetCardInfo("Take Vengeance", 13, Rarity.COMMON, mage.cards.t.TakeVengeance.class));
        cards.add(new SetCardInfo("Titanic Pelagosaur", 19, Rarity.UNCOMMON, mage.cards.t.TitanicPelagosaur.class));
        cards.add(new SetCardInfo("Treetop Warden", 48, Rarity.COMMON, mage.cards.t.TreetopWarden.class));
        cards.add(new SetCardInfo("Volcanic Dragon", 45, Rarity.UNCOMMON, mage.cards.v.VolcanicDragon.class));
        cards.add(new SetCardInfo("Waterknot", 22, Rarity.COMMON, mage.cards.w.Waterknot.class));
        cards.add(new SetCardInfo("Zephyr Gull", 23, Rarity.COMMON, mage.cards.z.ZephyrGull.class));
    }
}