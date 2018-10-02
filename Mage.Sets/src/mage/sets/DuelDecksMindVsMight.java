package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class DuelDecksMindVsMight extends ExpansionSet {

    private static final DuelDecksMindVsMight instance = new DuelDecksMindVsMight();

    public static DuelDecksMindVsMight getInstance() {
        return instance;
    }

    private DuelDecksMindVsMight() {
        super("Duel Decks: Mind vs. Might", "DDS", ExpansionSet.buildDate(2017, 3, 31), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Ambassador Oak", 42, Rarity.COMMON, mage.cards.a.AmbassadorOak.class));
        cards.add(new SetCardInfo("Beacon of Destruction", 35, Rarity.RARE, mage.cards.b.BeaconOfDestruction.class));
        cards.add(new SetCardInfo("Beacon of Tomorrows", 2, Rarity.RARE, mage.cards.b.BeaconOfTomorrows.class));
        cards.add(new SetCardInfo("Beast Attack", 43, Rarity.UNCOMMON, mage.cards.b.BeastAttack.class));
        cards.add(new SetCardInfo("Boldwyr Intimidator", 36, Rarity.UNCOMMON, mage.cards.b.BoldwyrIntimidator.class));
        cards.add(new SetCardInfo("Burning-Tree Emissary", 55, Rarity.UNCOMMON, mage.cards.b.BurningTreeEmissary.class));
        cards.add(new SetCardInfo("Call of the Herd", 44, Rarity.RARE, mage.cards.c.CallOfTheHerd.class));
        cards.add(new SetCardInfo("Cloudcrown Oak", 45, Rarity.COMMON, mage.cards.c.CloudcrownOak.class));
        cards.add(new SetCardInfo("Coat of Arms", 58, Rarity.RARE, mage.cards.c.CoatOfArms.class));
        cards.add(new SetCardInfo("Deep-Sea Kraken", 3, Rarity.RARE, mage.cards.d.DeepSeaKraken.class));
        cards.add(new SetCardInfo("Desperate Ritual", 14, Rarity.UNCOMMON, mage.cards.d.DesperateRitual.class));
        cards.add(new SetCardInfo("Empty the Warrens", 15, Rarity.COMMON, mage.cards.e.EmptyTheWarrens.class));
        cards.add(new SetCardInfo("Firebolt", 37, Rarity.COMMON, mage.cards.f.Firebolt.class));
        cards.add(new SetCardInfo("Firemind's Foresight", 21, Rarity.RARE, mage.cards.f.FiremindsForesight.class));
        cards.add(new SetCardInfo("Forest", 63, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 64, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 65, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Electromancer", 22, Rarity.COMMON, mage.cards.g.GoblinElectromancer.class));
        cards.add(new SetCardInfo("Gorehorn Minotaurs", 38, Rarity.COMMON, mage.cards.g.GorehornMinotaurs.class));
        cards.add(new SetCardInfo("Grapeshot", 16, Rarity.COMMON, mage.cards.g.Grapeshot.class));
        cards.add(new SetCardInfo("Guttural Response", 56, Rarity.UNCOMMON, mage.cards.g.GutturalResponse.class));
        cards.add(new SetCardInfo("Harmonize", 46, Rarity.UNCOMMON, mage.cards.h.Harmonize.class));
        cards.add(new SetCardInfo("Increasing Savagery", 47, Rarity.RARE, mage.cards.i.IncreasingSavagery.class));
        cards.add(new SetCardInfo("Island", 28, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 29, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 30, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jhoira of the Ghitu", 1, Rarity.MYTHIC, mage.cards.j.JhoiraOfTheGhitu.class));
        cards.add(new SetCardInfo("Jori En, Ruin Diver", 23, Rarity.RARE, mage.cards.j.JoriEnRuinDiver.class));
        cards.add(new SetCardInfo("Kamahl, Pit Fighter", 39, Rarity.RARE, mage.cards.k.KamahlPitFighter.class));
        cards.add(new SetCardInfo("Kruin Striker", 40, Rarity.COMMON, mage.cards.k.KruinStriker.class));
        cards.add(new SetCardInfo("Lovisa Coldeyes", 34, Rarity.MYTHIC, mage.cards.l.LovisaColdeyes.class));
        cards.add(new SetCardInfo("Mind's Desire", 4, Rarity.RARE, mage.cards.m.MindsDesire.class));
        cards.add(new SetCardInfo("Mountain", 31, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 32, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 33, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 60, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 61, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 62, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nivix Cyclops", 24, Rarity.COMMON, mage.cards.n.NivixCyclops.class));
        cards.add(new SetCardInfo("Nucklavee", 26, Rarity.UNCOMMON, mage.cards.n.Nucklavee.class));
        cards.add(new SetCardInfo("Peer Through Depths", 5, Rarity.COMMON, mage.cards.p.PeerThroughDepths.class));
        cards.add(new SetCardInfo("Quicken", 6, Rarity.RARE, mage.cards.q.Quicken.class));
        cards.add(new SetCardInfo("Radha, Heir to Keld", 53, Rarity.RARE, mage.cards.r.RadhaHeirToKeld.class));
        cards.add(new SetCardInfo("Rampant Growth", 48, Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Reach Through Mists", 7, Rarity.COMMON, mage.cards.r.ReachThroughMists.class));
        cards.add(new SetCardInfo("Relentless Hunter", 54, Rarity.UNCOMMON, mage.cards.r.RelentlessHunter.class));
        cards.add(new SetCardInfo("Rift Bolt", 17, Rarity.COMMON, mage.cards.r.RiftBolt.class));
        cards.add(new SetCardInfo("Roar of the Wurm", 49, Rarity.UNCOMMON, mage.cards.r.RoarOfTheWurm.class));
        cards.add(new SetCardInfo("Rubblebelt Raiders", 57, Rarity.RARE, mage.cards.r.RubblebeltRaiders.class));
        cards.add(new SetCardInfo("Rugged Highlands", 59, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Sage-Eye Avengers", 8, Rarity.RARE, mage.cards.s.SageEyeAvengers.class));
        cards.add(new SetCardInfo("Shivan Meteor", 18, Rarity.UNCOMMON, mage.cards.s.ShivanMeteor.class));
        cards.add(new SetCardInfo("Sift Through Sands", 9, Rarity.COMMON, mage.cards.s.SiftThroughSands.class));
        cards.add(new SetCardInfo("Skarrgan Pit-Skulk", 50, Rarity.COMMON, mage.cards.s.SkarrganPitSkulk.class));
        cards.add(new SetCardInfo("Snap", 10, Rarity.COMMON, mage.cards.s.Snap.class));
        cards.add(new SetCardInfo("Spellheart Chimera", 25, Rarity.UNCOMMON, mage.cards.s.SpellheartChimera.class));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 27, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Sylvan Might", 51, Rarity.COMMON, mage.cards.s.SylvanMight.class));
        cards.add(new SetCardInfo("Talara's Battalion", 52, Rarity.RARE, mage.cards.t.TalarasBattalion.class));
        cards.add(new SetCardInfo("Talrand, Sky Summoner", 11, Rarity.RARE, mage.cards.t.TalrandSkySummoner.class));
        cards.add(new SetCardInfo("Temporal Fissure", 12, Rarity.COMMON, mage.cards.t.TemporalFissure.class));
        cards.add(new SetCardInfo("The Unspeakable", 13, Rarity.RARE, mage.cards.t.TheUnspeakable.class));
        cards.add(new SetCardInfo("Volcanic Vision", 19, Rarity.RARE, mage.cards.v.VolcanicVision.class));
        cards.add(new SetCardInfo("Young Pyromancer", 20, Rarity.UNCOMMON, mage.cards.y.YoungPyromancer.class));
        cards.add(new SetCardInfo("Zo-Zu the Punisher", 41, Rarity.RARE, mage.cards.z.ZoZuThePunisher.class));

    }
}
