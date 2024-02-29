package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/anb
 */
public class ArenaBeginnerSet extends ExpansionSet {

    private static final ArenaBeginnerSet instance = new ArenaBeginnerSet();

    public static ArenaBeginnerSet getInstance() {
        return instance;
    }

//  Missing cards - Baloth Packhunter , Compound Fracture , Hallowed Priest , Mardu Outrider , Tin Street Cadet

    private ArenaBeginnerSet() {
        super("Arena Beginner Set", "ANB", ExpansionSet.buildDate(2020, 8, 13), SetType.MAGIC_ONLINE);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Affectionate Indrik", 89, Rarity.UNCOMMON, mage.cards.a.AffectionateIndrik.class));
        cards.add(new SetCardInfo("Air Elemental", 23, Rarity.UNCOMMON, mage.cards.a.AirElemental.class));
        cards.add(new SetCardInfo("Angelic Guardian", 2, Rarity.RARE, mage.cards.a.AngelicGuardian.class));
        cards.add(new SetCardInfo("Angelic Reward", 3, Rarity.UNCOMMON, mage.cards.a.AngelicReward.class));
        cards.add(new SetCardInfo("Angel of Vitality", 1, Rarity.UNCOMMON, mage.cards.a.AngelOfVitality.class));
        cards.add(new SetCardInfo("Arcane Signet", 117, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Armored Whirl Turtle", 24, Rarity.COMMON, mage.cards.a.ArmoredWhirlTurtle.class));
        cards.add(new SetCardInfo("Bad Deal", 45, Rarity.UNCOMMON, mage.cards.b.BadDeal.class));
//        cards.add(new SetCardInfo("Baloth Packhunter", 90, Rarity.COMMON, mage.cards.b.BalothPackhunter.class));
        cards.add(new SetCardInfo("Bombard", 67, Rarity.COMMON, mage.cards.b.Bombard.class));
        cards.add(new SetCardInfo("Bond of Discipline", 4, Rarity.UNCOMMON, mage.cards.b.BondOfDiscipline.class));
        cards.add(new SetCardInfo("Burn Bright", 68, Rarity.COMMON, mage.cards.b.BurnBright.class));
        cards.add(new SetCardInfo("Charging Badger", 91, Rarity.COMMON, mage.cards.c.ChargingBadger.class));
        cards.add(new SetCardInfo("Charmed Stray", 5, Rarity.COMMON, mage.cards.c.CharmedStray.class));
        cards.add(new SetCardInfo("Cloudkin Seer", 25, Rarity.COMMON, mage.cards.c.CloudkinSeer.class));
        cards.add(new SetCardInfo("Colossal Majesty", 92, Rarity.UNCOMMON, mage.cards.c.ColossalMajesty.class));
        cards.add(new SetCardInfo("Command Tower", 118, Rarity.COMMON, mage.cards.c.CommandTower.class));
//        cards.add(new SetCardInfo("Compound Fracture", 46, Rarity.COMMON, mage.cards.c.CompoundFracture.class));
        cards.add(new SetCardInfo("Confront the Assault", 6, Rarity.UNCOMMON, mage.cards.c.ConfrontTheAssault.class));
        cards.add(new SetCardInfo("Coral Merfolk", 26, Rarity.COMMON, mage.cards.c.CoralMerfolk.class));
        cards.add(new SetCardInfo("Cruel Cut", 47, Rarity.COMMON, mage.cards.c.CruelCut.class));
        cards.add(new SetCardInfo("Demon of Loathing", 48, Rarity.RARE, mage.cards.d.DemonOfLoathing.class));
        cards.add(new SetCardInfo("Epic Proportions", 93, Rarity.RARE, mage.cards.e.EpicProportions.class));
        cards.add(new SetCardInfo("Eternal Thirst", 49, Rarity.UNCOMMON, mage.cards.e.EternalThirst.class));
        cards.add(new SetCardInfo("Evolving Wilds", 111, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Fearless Halberdier", 69, Rarity.COMMON, mage.cards.f.FearlessHalberdier.class));
        cards.add(new SetCardInfo("Fencing Ace", 7, Rarity.COMMON, mage.cards.f.FencingAce.class));
        cards.add(new SetCardInfo("Feral Roar", 94, Rarity.COMMON, mage.cards.f.FeralRoar.class));
        cards.add(new SetCardInfo("Forest", 112, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Frilled Sea Serpent", 27, Rarity.COMMON, mage.cards.f.FrilledSeaSerpent.class));
        cards.add(new SetCardInfo("Generous Stray", 95, Rarity.COMMON, mage.cards.g.GenerousStray.class));
        cards.add(new SetCardInfo("Gigantosaurus", 96, Rarity.RARE, mage.cards.g.Gigantosaurus.class));
        cards.add(new SetCardInfo("Glint", 28, Rarity.COMMON, mage.cards.g.Glint.class));
        cards.add(new SetCardInfo("Goblin Gang Leader", 70, Rarity.UNCOMMON, mage.cards.g.GoblinGangLeader.class));
        cards.add(new SetCardInfo("Goblin Gathering", 71, Rarity.COMMON, mage.cards.g.GoblinGathering.class));
        cards.add(new SetCardInfo("Goblin Trashmaster", 72, Rarity.RARE, mage.cards.g.GoblinTrashmaster.class));
        cards.add(new SetCardInfo("Goblin Tunneler", 73, Rarity.COMMON, mage.cards.g.GoblinTunneler.class));
        cards.add(new SetCardInfo("Goring Ceratops", 8, Rarity.RARE, mage.cards.g.GoringCeratops.class));
        cards.add(new SetCardInfo("Greenwood Sentinel", 97, Rarity.COMMON, mage.cards.g.GreenwoodSentinel.class));
//        cards.add(new SetCardInfo("Hallowed Priest", 9, Rarity.UNCOMMON, mage.cards.h.HallowedPriest.class));
        cards.add(new SetCardInfo("Hurloon Minotaur", 74, Rarity.COMMON, mage.cards.h.HurloonMinotaur.class));
        cards.add(new SetCardInfo("Ilysian Caryatid", 98, Rarity.COMMON, mage.cards.i.IlysianCaryatid.class));
        cards.add(new SetCardInfo("Immortal Phoenix", 75, Rarity.RARE, mage.cards.i.ImmortalPhoenix.class));
        cards.add(new SetCardInfo("Impassioned Orator", 10, Rarity.COMMON, mage.cards.i.ImpassionedOrator.class));
        cards.add(new SetCardInfo("Inescapable Blaze", 76, Rarity.UNCOMMON, mage.cards.i.InescapableBlaze.class));
        cards.add(new SetCardInfo("Inspiring Commander", 11, Rarity.RARE, mage.cards.i.InspiringCommander.class));
        cards.add(new SetCardInfo("Island", 113, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Jungle Delver", 99, Rarity.COMMON, mage.cards.j.JungleDelver.class));
        cards.add(new SetCardInfo("Knight's Pledge", 12, Rarity.COMMON, mage.cards.k.KnightsPledge.class));
        cards.add(new SetCardInfo("Krovikan Scoundrel", 50, Rarity.COMMON, mage.cards.k.KrovikanScoundrel.class));
        cards.add(new SetCardInfo("Leonin Warleader", 13, Rarity.RARE, mage.cards.l.LeoninWarleader.class));
        cards.add(new SetCardInfo("Loxodon Line Breaker", 14, Rarity.COMMON, mage.cards.l.LoxodonLineBreaker.class));
        cards.add(new SetCardInfo("Malakir Cullblade", 51, Rarity.UNCOMMON, mage.cards.m.MalakirCullblade.class));
        cards.add(new SetCardInfo("Maniacal Rage", 77, Rarity.COMMON, mage.cards.m.ManiacalRage.class));
//        cards.add(new SetCardInfo("Mardu Outrider", 52, Rarity.RARE, mage.cards.m.MarduOutrider.class));
        cards.add(new SetCardInfo("Molten Ravager", 78, Rarity.COMMON, mage.cards.m.MoltenRavager.class));
        cards.add(new SetCardInfo("Moorland Inquisitor", 15, Rarity.COMMON, mage.cards.m.MoorlandInquisitor.class));
        cards.add(new SetCardInfo("Mountain", 114, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Murder", 53, Rarity.UNCOMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Nest Robber", 79, Rarity.COMMON, mage.cards.n.NestRobber.class));
        cards.add(new SetCardInfo("Nightmare", 54, Rarity.RARE, mage.cards.n.Nightmare.class));
        cards.add(new SetCardInfo("Nimble Pilferer", 55, Rarity.COMMON, mage.cards.n.NimblePilferer.class));
        cards.add(new SetCardInfo("Octoprophet", 29, Rarity.COMMON, mage.cards.o.Octoprophet.class));
        cards.add(new SetCardInfo("Ogre Battledriver", 80, Rarity.RARE, mage.cards.o.OgreBattledriver.class));
        cards.add(new SetCardInfo("Overflowing Insight", 30, Rarity.RARE, mage.cards.o.OverflowingInsight.class));
        cards.add(new SetCardInfo("Pacifism", 16, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Plains", 115, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Prized Unicorn", 100, Rarity.UNCOMMON, mage.cards.p.PrizedUnicorn.class));
        cards.add(new SetCardInfo("Rabid Bite", 101, Rarity.COMMON, mage.cards.r.RabidBite.class));
        cards.add(new SetCardInfo("Raging Goblin", 81, Rarity.COMMON, mage.cards.r.RagingGoblin.class));
        cards.add(new SetCardInfo("Raid Bombardment", 82, Rarity.UNCOMMON, mage.cards.r.RaidBombardment.class));
        cards.add(new SetCardInfo("Raise Dead", 56, Rarity.COMMON, mage.cards.r.RaiseDead.class));
        cards.add(new SetCardInfo("Rampaging Brontodon", 102, Rarity.RARE, mage.cards.r.RampagingBrontodon.class));
        cards.add(new SetCardInfo("Reduce to Ashes", 83, Rarity.COMMON, mage.cards.r.ReduceToAshes.class));
        cards.add(new SetCardInfo("Riddlemaster Sphinx", 31, Rarity.RARE, mage.cards.r.RiddlemasterSphinx.class));
        cards.add(new SetCardInfo("Rise from the Grave", "56b", Rarity.UNCOMMON, mage.cards.r.RiseFromTheGrave.class));
        cards.add(new SetCardInfo("River's Favor", 32, Rarity.COMMON, mage.cards.r.RiversFavor.class));
        cards.add(new SetCardInfo("Rumbling Baloth", 103, Rarity.COMMON, mage.cards.r.RumblingBaloth.class));
        cards.add(new SetCardInfo("Sanctuary Cat", 17, Rarity.COMMON, mage.cards.s.SanctuaryCat.class));
        cards.add(new SetCardInfo("Sanitarium Skeleton", 57, Rarity.COMMON, mage.cards.s.SanitariumSkeleton.class));
        cards.add(new SetCardInfo("Savage Gorger", 58, Rarity.COMMON, mage.cards.s.SavageGorger.class));
        cards.add(new SetCardInfo("Scathe Zombies", 59, Rarity.COMMON, mage.cards.s.ScatheZombies.class));
        cards.add(new SetCardInfo("Sengir Vampire", 60, Rarity.UNCOMMON, mage.cards.s.SengirVampire.class));
        cards.add(new SetCardInfo("Sentinel Spider", 104, Rarity.UNCOMMON, mage.cards.s.SentinelSpider.class));
        cards.add(new SetCardInfo("Serra Angel", 18, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shock", 84, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Shorecomber Crab", "32a", Rarity.COMMON, mage.cards.s.ShorecomberCrab.class));
        cards.add(new SetCardInfo("Shrine Keeper", 19, Rarity.COMMON, mage.cards.s.ShrineKeeper.class));
        cards.add(new SetCardInfo("Siege Dragon", 85, Rarity.RARE, mage.cards.s.SiegeDragon.class));
        cards.add(new SetCardInfo("Skeleton Archer", 61, Rarity.COMMON, mage.cards.s.SkeletonArcher.class));
        cards.add(new SetCardInfo("Sleep", 33, Rarity.UNCOMMON, mage.cards.s.Sleep.class));
        cards.add(new SetCardInfo("Soulblade Djinn", 34, Rarity.RARE, mage.cards.s.SoulbladeDjinn.class));
        cards.add(new SetCardInfo("Soulhunter Rakshasa", 62, Rarity.RARE, mage.cards.s.SoulhunterRakshasa.class));
        cards.add(new SetCardInfo("Soulmender", 20, Rarity.COMMON, mage.cards.s.Soulmender.class));
        cards.add(new SetCardInfo("Spiritual Guardian", 21, Rarity.COMMON, mage.cards.s.SpiritualGuardian.class));
        cards.add(new SetCardInfo("Stony Strength", 105, Rarity.COMMON, mage.cards.s.StonyStrength.class));
        cards.add(new SetCardInfo("Storm Strike", 86, Rarity.COMMON, mage.cards.s.StormStrike.class));
        cards.add(new SetCardInfo("Swamp", 116, Rarity.LAND, mage.cards.basiclands.Swamp.class));
        cards.add(new SetCardInfo("Sworn Guardian", 35, Rarity.COMMON, mage.cards.s.SwornGuardian.class));
        cards.add(new SetCardInfo("Tactical Advantage", 22, Rarity.COMMON, mage.cards.t.TacticalAdvantage.class));
//        cards.add(new SetCardInfo("Tin Street Cadet", 87, Rarity.COMMON, mage.cards.t.TinStreetCadet.class));
        cards.add(new SetCardInfo("Titanic Growth", 106, Rarity.COMMON, mage.cards.t.TitanicGrowth.class));
        cards.add(new SetCardInfo("Treetop Warden", 107, Rarity.COMMON, mage.cards.t.TreetopWarden.class));
        cards.add(new SetCardInfo("Typhoid Rats", 63, Rarity.COMMON, mage.cards.t.TyphoidRats.class));
        cards.add(new SetCardInfo("Unlikely Aid", 64, Rarity.COMMON, mage.cards.u.UnlikelyAid.class));
        cards.add(new SetCardInfo("Unsummon", 36, Rarity.COMMON, mage.cards.u.Unsummon.class));
        cards.add(new SetCardInfo("Vampire Opportunist", 65, Rarity.COMMON, mage.cards.v.VampireOpportunist.class));
        cards.add(new SetCardInfo("Volcanic Dragon", 88, Rarity.UNCOMMON, mage.cards.v.VolcanicDragon.class));
        cards.add(new SetCardInfo("Wall of Runes", 37, Rarity.COMMON, mage.cards.w.WallOfRunes.class));
        cards.add(new SetCardInfo("Warden of Evos Isle", 38, Rarity.UNCOMMON, mage.cards.w.WardenOfEvosIsle.class));
        cards.add(new SetCardInfo("Waterkin Shaman", 39, Rarity.COMMON, mage.cards.w.WaterkinShaman.class));
        cards.add(new SetCardInfo("Waterknot", 40, Rarity.COMMON, mage.cards.w.Waterknot.class));
        cards.add(new SetCardInfo("Wildwood Patrol", 108, Rarity.COMMON, mage.cards.w.WildwoodPatrol.class));
        cards.add(new SetCardInfo("Windreader Sphinx", 41, Rarity.RARE, mage.cards.w.WindreaderSphinx.class));
        cards.add(new SetCardInfo("Windstorm Drake", 42, Rarity.UNCOMMON, mage.cards.w.WindstormDrake.class));
        cards.add(new SetCardInfo("Winged Words", 43, Rarity.COMMON, mage.cards.w.WingedWords.class));
        cards.add(new SetCardInfo("Witch's Familiar", 66, Rarity.COMMON, mage.cards.w.WitchsFamiliar.class));
        cards.add(new SetCardInfo("Woodland Mystic", 109, Rarity.COMMON, mage.cards.w.WoodlandMystic.class));
        cards.add(new SetCardInfo("World Shaper", 110, Rarity.RARE, mage.cards.w.WorldShaper.class));
        cards.add(new SetCardInfo("Zephyr Gull", 44, Rarity.COMMON, mage.cards.z.ZephyrGull.class));
    }
}
