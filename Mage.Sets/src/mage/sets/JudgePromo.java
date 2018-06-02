
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public final class JudgePromo extends ExpansionSet {

    private static final JudgePromo instance = new JudgePromo();

    public static JudgePromo getInstance() {
        return instance;
    }

    private JudgePromo() {
        super("Judge Promo", "JR", ExpansionSet.buildDate(2011, 6, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        cards.add(new SetCardInfo("Argothian Enchantress", 12, Rarity.RARE, mage.cards.a.ArgothianEnchantress.class));
        cards.add(new SetCardInfo("Armageddon", 14, Rarity.RARE, mage.cards.a.Armageddon.class));
        cards.add(new SetCardInfo("Avacyn, Angel of Hope", 110, Rarity.SPECIAL, mage.cards.a.AvacynAngelOfHope.class));
        cards.add(new SetCardInfo("Azusa, Lost but Seeking", 102, Rarity.RARE, mage.cards.a.AzusaLostButSeeking.class));// 2016 003/008
        cards.add(new SetCardInfo("Balance", 15, Rarity.RARE, mage.cards.b.Balance.class));
        cards.add(new SetCardInfo("Ball Lightning", 7, Rarity.RARE, mage.cards.b.BallLightning.class));
        cards.add(new SetCardInfo("Bitterblossom", 59, Rarity.RARE, mage.cards.b.Bitterblossom.class));
        cards.add(new SetCardInfo("Bloodstained Mire", 43, Rarity.RARE, mage.cards.b.BloodstainedMire.class));
        cards.add(new SetCardInfo("Bribery", 73, Rarity.RARE, mage.cards.b.Bribery.class));
        cards.add(new SetCardInfo("Burning Wish", 42, Rarity.RARE, mage.cards.b.BurningWish.class));
        cards.add(new SetCardInfo("Command Beacon", 105, Rarity.RARE, mage.cards.c.CommandBeacon.class));// 2016 004/008
        cards.add(new SetCardInfo("Command Tower", 71, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Counterspell", 5, Rarity.COMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Crucible of Worlds", 75, Rarity.RARE, mage.cards.c.CrucibleOfWorlds.class));
        cards.add(new SetCardInfo("Cunning Wish", 29, Rarity.RARE, mage.cards.c.CunningWish.class));
        cards.add(new SetCardInfo("Damnation", 98, Rarity.RARE, mage.cards.d.Damnation.class));
        cards.add(new SetCardInfo("Dark Confidant", 61, Rarity.RARE, mage.cards.d.DarkConfidant.class));
        cards.add(new SetCardInfo("Dark Ritual", 38, Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Decree of Justice", 32, Rarity.RARE, mage.cards.d.DecreeOfJustice.class));
        cards.add(new SetCardInfo("Defense of the Heart", 106, Rarity.RARE, mage.cards.d.DefenseOfTheHeart.class)); // 2016 007/008
        cards.add(new SetCardInfo("Demonic Tutor", 35, Rarity.UNCOMMON, mage.cards.d.DemonicTutor.class));
        cards.add(new SetCardInfo("Deranged Hermit", 18, Rarity.RARE, mage.cards.d.DerangedHermit.class));
        cards.add(new SetCardInfo("Doubling Season", 62, Rarity.RARE, mage.cards.d.DoublingSeason.class));
        cards.add(new SetCardInfo("Dualcaster Mage", 99, Rarity.RARE, mage.cards.d.DualcasterMage.class));
        cards.add(new SetCardInfo("Elesh Norn, Grand Cenobite", 87, Rarity.MYTHIC, mage.cards.e.EleshNornGrandCenobite.class));
        cards.add(new SetCardInfo("Entomb", 56, Rarity.RARE, mage.cards.e.Entomb.class));
        cards.add(new SetCardInfo("Exalted Angel", 24, Rarity.RARE, mage.cards.e.ExaltedAngel.class));
        cards.add(new SetCardInfo("Feldon of the Third Path", 100, Rarity.MYTHIC, mage.cards.f.FeldonOfTheThirdPath.class));
        cards.add(new SetCardInfo("Flooded Strand", 44, Rarity.RARE, mage.cards.f.FloodedStrand.class));
        cards.add(new SetCardInfo("Flusterstorm", 65, Rarity.RARE, mage.cards.f.Flusterstorm.class));
        cards.add(new SetCardInfo("Force of Will", 83, Rarity.UNCOMMON, mage.cards.f.ForceOfWill.class));
        cards.add(new SetCardInfo("Forest", 93, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gaddock Teeg", 112, Rarity.SPECIAL, mage.cards.g.GaddockTeeg.class));
        cards.add(new SetCardInfo("Gaea's Cradle", 3, Rarity.RARE, mage.cards.g.GaeasCradle.class));
        cards.add(new SetCardInfo("Gemstone Mine", 20, Rarity.UNCOMMON, mage.cards.g.GemstoneMine.class));
        cards.add(new SetCardInfo("Genesis", 79, Rarity.RARE, mage.cards.g.Genesis.class));
        cards.add(new SetCardInfo("Goblin Piledriver", 36, Rarity.RARE, mage.cards.g.GoblinPiledriver.class));
        cards.add(new SetCardInfo("Goblin Welder", 63, Rarity.RARE, mage.cards.g.GoblinWelder.class));
        cards.add(new SetCardInfo("Greater Good", 81, Rarity.RARE, mage.cards.g.GreaterGood.class));
        cards.add(new SetCardInfo("Grim Lavamancer", 25, Rarity.RARE, mage.cards.g.GrimLavamancer.class));
        cards.add(new SetCardInfo("Grindstone", 104, Rarity.RARE, mage.cards.g.Grindstone.class));
        cards.add(new SetCardInfo("Hammer of Bogardan", 9, Rarity.RARE, mage.cards.h.HammerOfBogardan.class));
        cards.add(new SetCardInfo("Hanna, Ship's Navigator", 84, Rarity.RARE, mage.cards.h.HannaShipsNavigator.class));
        cards.add(new SetCardInfo("Hermit Druid", 19, Rarity.RARE, mage.cards.h.HermitDruid.class));
        cards.add(new SetCardInfo("Homeward Path", 113, Rarity.SPECIAL, mage.cards.h.HomewardPath.class));
        cards.add(new SetCardInfo("Imperial Recruiter", 74, Rarity.UNCOMMON, mage.cards.i.ImperialRecruiter.class));
        cards.add(new SetCardInfo("Imperial Seal", 109, Rarity.SPECIAL, mage.cards.i.ImperialSeal.class));// 2016 006/008
        cards.add(new SetCardInfo("Intuition", 11, Rarity.RARE, mage.cards.i.Intuition.class));
        cards.add(new SetCardInfo("Island", 90, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karador, Ghost Chieftain", 80, Rarity.MYTHIC, mage.cards.k.KaradorGhostChieftain.class));
        cards.add(new SetCardInfo("Karakas", 69, Rarity.UNCOMMON, mage.cards.k.Karakas.class));
        cards.add(new SetCardInfo("Karmic Guide", 67, Rarity.RARE, mage.cards.k.KarmicGuide.class));
        cards.add(new SetCardInfo("Land Tax", 52, Rarity.RARE, mage.cards.l.LandTax.class));
        cards.add(new SetCardInfo("Lightning Bolt", 1, Rarity.COMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Living Death", 13, Rarity.RARE, mage.cards.l.LivingDeath.class));
        cards.add(new SetCardInfo("Living Wish", 37, Rarity.RARE, mage.cards.l.LivingWish.class));
        cards.add(new SetCardInfo("Mana Crypt", 60, Rarity.RARE, mage.cards.m.ManaCrypt.class));
        cards.add(new SetCardInfo("Mana Drain", 103, Rarity.UNCOMMON, mage.cards.m.ManaDrain.class));// 2016 002/008
        cards.add(new SetCardInfo("Maze of Ith", 39, Rarity.UNCOMMON, mage.cards.m.MazeOfIth.class));
        cards.add(new SetCardInfo("Meddling Mage", 26, Rarity.RARE, mage.cards.m.MeddlingMage.class));
        cards.add(new SetCardInfo("Memory Lapse", 4, Rarity.COMMON, mage.cards.m.MemoryLapse.class));
        cards.add(new SetCardInfo("Mind's Desire", 34, Rarity.RARE, mage.cards.m.MindsDesire.class));
        cards.add(new SetCardInfo("Mishra's Factory", 23, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class));
        cards.add(new SetCardInfo("Morphling", 53, Rarity.RARE, mage.cards.m.Morphling.class));
        cards.add(new SetCardInfo("Mountain", 92, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Confluence", 108, Rarity.SPECIAL, mage.cards.m.MysticConfluence.class, NON_FULL_USE_VARIOUS)); // 2016 005/008
        cards.add(new SetCardInfo("Natural Order", 49, Rarity.RARE, mage.cards.n.NaturalOrder.class));
        cards.add(new SetCardInfo("Nekusar, the Mindrazer", 86, Rarity.MYTHIC, mage.cards.n.NekusarTheMindrazer.class));
        cards.add(new SetCardInfo("Noble Hierarch", 66, Rarity.RARE, mage.cards.n.NobleHierarch.class));
        cards.add(new SetCardInfo("Oath of Druids", 8, Rarity.RARE, mage.cards.o.OathOfDruids.class));
        cards.add(new SetCardInfo("Oloro, Ageless Ascetic", 88, Rarity.MYTHIC, mage.cards.o.OloroAgelessAscetic.class));
        cards.add(new SetCardInfo("Orim's Chant", 33, Rarity.RARE, mage.cards.o.OrimsChant.class));
        cards.add(new SetCardInfo("Overwhelming Forces", 76, Rarity.RARE, mage.cards.o.OverwhelmingForces.class));
        cards.add(new SetCardInfo("Pernicious Deed", 27, Rarity.RARE, mage.cards.p.PerniciousDeed.class));
        cards.add(new SetCardInfo("Phyrexian Dreadnought", 50, Rarity.RARE, mage.cards.p.PhyrexianDreadnought.class));
        cards.add(new SetCardInfo("Phyrexian Negator", 17, Rarity.RARE, mage.cards.p.PhyrexianNegator.class));
        cards.add(new SetCardInfo("Plains", 89, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Polluted Delta", 45, Rarity.RARE, mage.cards.p.PollutedDelta.class));
        cards.add(new SetCardInfo("Ravages of War", 97, Rarity.RARE, mage.cards.r.RavagesOfWar.class));
        cards.add(new SetCardInfo("Ravenous Baloth", 28, Rarity.RARE, mage.cards.r.RavenousBaloth.class));
        cards.add(new SetCardInfo("Regrowth", 21, Rarity.UNCOMMON, mage.cards.r.Regrowth.class));
        cards.add(new SetCardInfo("Riku of Two Reflections", 82, Rarity.MYTHIC, mage.cards.r.RikuOfTwoReflections.class));
        cards.add(new SetCardInfo("Rishadan Port", 96, Rarity.RARE, mage.cards.r.RishadanPort.class));
        cards.add(new SetCardInfo("Shardless Agent", 95, Rarity.UNCOMMON, mage.cards.s.ShardlessAgent.class));
        cards.add(new SetCardInfo("Show and Tell", 77, Rarity.RARE, mage.cards.s.ShowAndTell.class));
        cards.add(new SetCardInfo("Sinkhole", 48, Rarity.COMMON, mage.cards.s.Sinkhole.class));
        cards.add(new SetCardInfo("Sneak Attack", 68, Rarity.RARE, mage.cards.s.SneakAttack.class));
        cards.add(new SetCardInfo("Sol Ring", 22, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Stifle", 40, Rarity.RARE, mage.cards.s.Stifle.class));
        cards.add(new SetCardInfo("Stroke of Genius", 2, Rarity.RARE, mage.cards.s.StrokeOfGenius.class));
        cards.add(new SetCardInfo("Survival of the Fittest", 41, Rarity.RARE, mage.cards.s.SurvivalOfTheFittest.class));
        cards.add(new SetCardInfo("Swamp", 91, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of Feast and Famine", 85, Rarity.MYTHIC, mage.cards.s.SwordOfFeastAndFamine.class));
        cards.add(new SetCardInfo("Sword of Fire and Ice", 57, Rarity.RARE, mage.cards.s.SwordOfFireAndIce.class));
        cards.add(new SetCardInfo("Sword of Light and Shadow", 70, Rarity.RARE, mage.cards.s.SwordOfLightAndShadow.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 72, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Thawing Glaciers", 51, Rarity.RARE, mage.cards.t.ThawingGlaciers.class));
        cards.add(new SetCardInfo("Time Warp", 16, Rarity.MYTHIC, mage.cards.t.TimeWarp.class));
        cards.add(new SetCardInfo("Tradewind Rider", 10, Rarity.RARE, mage.cards.t.TradewindRider.class));
        cards.add(new SetCardInfo("Vampiric Tutor", 6, Rarity.RARE, mage.cards.v.VampiricTutor.class));
        cards.add(new SetCardInfo("Vendilion Clique", 58, Rarity.RARE, mage.cards.v.VendilionClique.class));
        cards.add(new SetCardInfo("Vindicate", 31, Rarity.RARE, mage.cards.v.Vindicate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vindicate", 78, Rarity.RARE, mage.cards.v.Vindicate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wasteland", 55, Rarity.UNCOMMON, mage.cards.w.Wasteland.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wasteland", 101, Rarity.RARE, mage.cards.w.Wasteland.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wheel of Fortune", 54, Rarity.RARE, mage.cards.w.WheelOfFortune.class));
        cards.add(new SetCardInfo("Windswept Heath", 46, Rarity.RARE, mage.cards.w.WindsweptHeath.class));
        cards.add(new SetCardInfo("Wooded Foothills", 47, Rarity.RARE, mage.cards.w.WoodedFoothills.class));
        cards.add(new SetCardInfo("Xiahou Dun, the One-Eyed", 64, Rarity.RARE, mage.cards.x.XiahouDunTheOneEyed.class));
        cards.add(new SetCardInfo("Yawgmoth's Will", 30, Rarity.RARE, mage.cards.y.YawgmothsWill.class));
        cards.add(new SetCardInfo("Zur the Enchanter", 107, Rarity.RARE, mage.cards.z.ZurTheEnchanter.class)); // 2016 008/008
    }

}
