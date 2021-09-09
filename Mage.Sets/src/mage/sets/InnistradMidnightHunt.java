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

    private static final List<String> unfinished = Arrays.asList("Arlinn, the Pack's Hope", "Arlinn, the Moon's Fury", "Baneblade Scoundrel", "Baneclaw Marauder", "Bird Admirer", "Wing Shredder", "Brimstone Vandal", "Brutal Cathar", "Moonrage Brute", "Burly Breaker", "Dire-Strain Demolisher", "Celestus Sanctifier", "Curse of Leeches", "Leeching Lurker", "Gavony Dawnguard", "Graveyard Trespasser", "Graveyard Glutton", "Hound Tamer", "Untamed Pup", "Kessig Naturalist", "Lord of the Ulvenwald", "Reckless Stormseeker", "Storm-Charged Slasher", "Spellrune Painter", "Spellrune Howler", "Suspicious Stowaway", "Seafaring Werewolf", "Tavern Ruffian", "Tavern Smasher", "The Celestus", "Tovolar, Dire Overlord", "Tovolar, the Midnight Scourge", "Vadrik, Astral Archmage", "Village Watch", "Village Reavers");
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

        cards.add(new SetCardInfo("Arcane Infusion", 210, Rarity.UNCOMMON, mage.cards.a.ArcaneInfusion.class));
        cards.add(new SetCardInfo("Arrogant Outlaw", 84, Rarity.COMMON, mage.cards.a.ArrogantOutlaw.class));
        cards.add(new SetCardInfo("Augur of Autumn", 168, Rarity.RARE, mage.cards.a.AugurOfAutumn.class));
        cards.add(new SetCardInfo("Bird Admirer", 169, Rarity.COMMON, mage.cards.b.BirdAdmirer.class));
        cards.add(new SetCardInfo("Bladebrand", 87, Rarity.COMMON, mage.cards.b.Bladebrand.class));
        cards.add(new SetCardInfo("Bladestitched Skaab", 212, Rarity.UNCOMMON, mage.cards.b.BladestitchedSkaab.class));
        cards.add(new SetCardInfo("Bloodthirsty Adversary", 129, Rarity.MYTHIC, mage.cards.b.BloodthirstyAdversary.class));
        cards.add(new SetCardInfo("Briarbridge Tracker", 172, Rarity.RARE, mage.cards.b.BriarbridgeTracker.class));
        cards.add(new SetCardInfo("Brimstone Vandal", 130, Rarity.COMMON, mage.cards.b.BrimstoneVandal.class));
        cards.add(new SetCardInfo("Burly Breaker", 174, Rarity.UNCOMMON, mage.cards.b.BurlyBreaker.class));
        cards.add(new SetCardInfo("Can't Stay Away", 213, Rarity.RARE, mage.cards.c.CantStayAway.class));
        cards.add(new SetCardInfo("Candlegrove Witch", 8, Rarity.COMMON, mage.cards.c.CandlegroveWitch.class));
        cards.add(new SetCardInfo("Candlelit Cavalry", 175, Rarity.COMMON, mage.cards.c.CandlelitCavalry.class));
        cards.add(new SetCardInfo("Candletrap", 9, Rarity.COMMON, mage.cards.c.Candletrap.class));
        cards.add(new SetCardInfo("Celestus Sanctifier", 12, Rarity.COMMON, mage.cards.c.CelestusSanctifier.class));
        cards.add(new SetCardInfo("Champion of the Perished", 91, Rarity.RARE, mage.cards.c.ChampionOfThePerished.class));
        cards.add(new SetCardInfo("Clear Shot", 176, Rarity.UNCOMMON, mage.cards.c.ClearShot.class));
        cards.add(new SetCardInfo("Consider", 44, Rarity.COMMON, mage.cards.c.Consider.class));
        cards.add(new SetCardInfo("Contortionist Troupe", 178, Rarity.UNCOMMON, mage.cards.c.ContortionistTroupe.class));
        cards.add(new SetCardInfo("Corpse Cobble", 214, Rarity.UNCOMMON, mage.cards.c.CorpseCobble.class));
        cards.add(new SetCardInfo("Croaking Counterpart", 215, Rarity.RARE, mage.cards.c.CroakingCounterpart.class));
        cards.add(new SetCardInfo("Curse of Shaken Faith", 134, Rarity.RARE, mage.cards.c.CurseOfShakenFaith.class));
        cards.add(new SetCardInfo("Curse of Silence", 326, Rarity.RARE, mage.cards.c.CurseOfSilence.class));
        cards.add(new SetCardInfo("Curse of Surveillance", 46, Rarity.RARE, mage.cards.c.CurseOfSurveillance.class));
        cards.add(new SetCardInfo("Dawnhart Rejuvenator", 180, Rarity.COMMON, mage.cards.d.DawnhartRejuvenator.class));
        cards.add(new SetCardInfo("Dawnhart Wardens", 216, Rarity.UNCOMMON, mage.cards.d.DawnhartWardens.class));
        cards.add(new SetCardInfo("Defend the Celestus", 182, Rarity.UNCOMMON, mage.cards.d.DefendTheCelestus.class));
        cards.add(new SetCardInfo("Defenestrate", 95, Rarity.COMMON, mage.cards.d.Defenestrate.class));
        cards.add(new SetCardInfo("Delver of Secrets", 47, Rarity.UNCOMMON, mage.cards.d.DelverOfSecrets.class));
        cards.add(new SetCardInfo("Deserted Beach", 260, Rarity.RARE, mage.cards.d.DesertedBeach.class));
        cards.add(new SetCardInfo("Dire-Strain Demolisher", 174, Rarity.UNCOMMON, mage.cards.d.DireStrainDemolisher.class));
        cards.add(new SetCardInfo("Dire-Strain Rampage", 219, Rarity.RARE, mage.cards.d.DireStrainRampage.class));
        cards.add(new SetCardInfo("Diregraf Horde", 96, Rarity.COMMON, mage.cards.d.DiregrafHorde.class));
        cards.add(new SetCardInfo("Diregraf Rebirth", 220, Rarity.UNCOMMON, mage.cards.d.DiregrafRebirth.class));
        cards.add(new SetCardInfo("Dissipate", 49, Rarity.UNCOMMON, mage.cards.d.Dissipate.class));
        cards.add(new SetCardInfo("Duel for Dominance", 184, Rarity.COMMON, mage.cards.d.DuelForDominance.class));
        cards.add(new SetCardInfo("Eaten Alive", 99, Rarity.COMMON, mage.cards.e.EatenAlive.class));
        cards.add(new SetCardInfo("Embodiment of Flame", 141, Rarity.UNCOMMON, mage.cards.e.EmbodimentOfFlame.class));
        cards.add(new SetCardInfo("Faith Flare", 19, Rarity.COMMON, mage.cards.f.FaithFlare.class));
        cards.add(new SetCardInfo("Faithful Mending", 221, Rarity.UNCOMMON, mage.cards.f.FaithfulMending.class));
        cards.add(new SetCardInfo("Falkenrath Perforator", 136, Rarity.COMMON, mage.cards.f.FalkenrathPerforator.class));
        cards.add(new SetCardInfo("Famished Foragers", 138, Rarity.COMMON, mage.cards.f.FamishedForagers.class));
        cards.add(new SetCardInfo("Festival Crasher", 140, Rarity.COMMON, mage.cards.f.FestivalCrasher.class));
        cards.add(new SetCardInfo("Field of Ruin", 262, Rarity.UNCOMMON, mage.cards.f.FieldOfRuin.class));
        cards.add(new SetCardInfo("Flame Channeler", 141, Rarity.UNCOMMON, mage.cards.f.FlameChanneler.class));
        cards.add(new SetCardInfo("Fleshtaker", 222, Rarity.UNCOMMON, mage.cards.f.Fleshtaker.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Foul Play", 101, Rarity.UNCOMMON, mage.cards.f.FoulPlay.class));
        cards.add(new SetCardInfo("Galvanic Iteration", 224, Rarity.RARE, mage.cards.g.GalvanicIteration.class));
        cards.add(new SetCardInfo("Ghoulcaller's Harvest", 225, Rarity.RARE, mage.cards.g.GhoulcallersHarvest.class));
        cards.add(new SetCardInfo("Grafted Identity", 57, Rarity.RARE, mage.cards.g.GraftedIdentity.class));
        cards.add(new SetCardInfo("Graveyard Glutton", 104, Rarity.RARE, mage.cards.g.GraveyardGlutton.class));
        cards.add(new SetCardInfo("Graveyard Trespasser", 104, Rarity.RARE, mage.cards.g.GraveyardTrespasser.class));
        cards.add(new SetCardInfo("Haunted Ridge", 263, Rarity.RARE, mage.cards.h.HauntedRidge.class));
        cards.add(new SetCardInfo("Hedgewitch's Mask", 23, Rarity.COMMON, mage.cards.h.HedgewitchsMask.class));
        cards.add(new SetCardInfo("Hobbling Zombie", 106, Rarity.COMMON, mage.cards.h.HobblingZombie.class));
        cards.add(new SetCardInfo("Hound Tamer", 187, Rarity.UNCOMMON, mage.cards.h.HoundTamer.class));
        cards.add(new SetCardInfo("Howl of the Hunt", 188, Rarity.COMMON, mage.cards.h.HowlOfTheHunt.class));
        cards.add(new SetCardInfo("Hungry for More", 228, Rarity.UNCOMMON, mage.cards.h.HungryForMore.class));
        cards.add(new SetCardInfo("Immolation", 144, Rarity.COMMON, mage.cards.i.Immolation.class));
        cards.add(new SetCardInfo("Infernal Grasp", 107, Rarity.UNCOMMON, mage.cards.i.InfernalGrasp.class));
        cards.add(new SetCardInfo("Insectile Aberration", 47, Rarity.UNCOMMON, mage.cards.i.InsectileAberration.class));
        cards.add(new SetCardInfo("Island", 270, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Jack-o'-Lantern", 254, Rarity.COMMON, mage.cards.j.JackOLantern.class));
        cards.add(new SetCardInfo("Jadar, Ghoulcaller of Nephalia", 108, Rarity.RARE, mage.cards.j.JadarGhoulcallerOfNephalia.class));
        cards.add(new SetCardInfo("Join the Dance", 229, Rarity.UNCOMMON, mage.cards.j.JoinTheDance.class));
        cards.add(new SetCardInfo("Kessig Naturalist", 231, Rarity.UNCOMMON, mage.cards.k.KessigNaturalist.class));
        cards.add(new SetCardInfo("Lier, Disciple of the Drowned", 59, Rarity.MYTHIC, mage.cards.l.LierDiscipleOfTheDrowned.class));
        cards.add(new SetCardInfo("Lord of the Ulvenwald", 231, Rarity.UNCOMMON, mage.cards.l.LordOfTheUlvenwald.class));
        cards.add(new SetCardInfo("Loyal Gryff", 26, Rarity.UNCOMMON, mage.cards.l.LoyalGryff.class));
        cards.add(new SetCardInfo("Lunar Frenzy", 147, Rarity.UNCOMMON, mage.cards.l.LunarFrenzy.class));
        cards.add(new SetCardInfo("Might of the Old Ways", 189, Rarity.COMMON, mage.cards.m.MightOfTheOldWays.class));
        cards.add(new SetCardInfo("Moonsilver Key", 255, Rarity.UNCOMMON, mage.cards.m.MoonsilverKey.class));
        cards.add(new SetCardInfo("Morbid Opportunist", 113, Rarity.UNCOMMON, mage.cards.m.MorbidOpportunist.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mystic Monstrosity", 256, Rarity.UNCOMMON, mage.cards.m.MysticMonstrosity.class));
        cards.add(new SetCardInfo("Mystic Skull", 256, Rarity.UNCOMMON, mage.cards.m.MysticSkull.class));
        cards.add(new SetCardInfo("Neblegast Intruder", 64, Rarity.UNCOMMON, mage.cards.n.NeblegastIntruder.class));
        cards.add(new SetCardInfo("Odric's Outrider", 29, Rarity.UNCOMMON, mage.cards.o.OdricsOutrider.class));
        cards.add(new SetCardInfo("Organ Hoarder", 66, Rarity.COMMON, mage.cards.o.OrganHoarder.class));
        cards.add(new SetCardInfo("Overgrown Farmland", 265, Rarity.RARE, mage.cards.o.OvergrownFarmland.class));
        cards.add(new SetCardInfo("Patrician Geist", 69, Rarity.RARE, mage.cards.p.PatricianGeist.class));
        cards.add(new SetCardInfo("Pestilent Wolf", 192, Rarity.COMMON, mage.cards.p.PestilentWolf.class));
        cards.add(new SetCardInfo("Pithing Needle", 257, Rarity.RARE, mage.cards.p.PithingNeedle.class));
        cards.add(new SetCardInfo("Plains", 268, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Play with Fire", 154, Rarity.UNCOMMON, mage.cards.p.PlayWithFire.class));
        cards.add(new SetCardInfo("Poppet Factory", 71, Rarity.MYTHIC, mage.cards.p.PoppetFactory.class));
        cards.add(new SetCardInfo("Poppet Stitcher", 71, Rarity.MYTHIC, mage.cards.p.PoppetStitcher.class));
        cards.add(new SetCardInfo("Reckless Stormseeker", 157, Rarity.RARE, mage.cards.r.RecklessStormseeker.class));
        cards.add(new SetCardInfo("Rite of Harmony", 236, Rarity.RARE, mage.cards.r.RiteOfHarmony.class));
        cards.add(new SetCardInfo("Ritual Guardian", 30, Rarity.COMMON, mage.cards.r.RitualGuardian.class));
        cards.add(new SetCardInfo("Ritual of Hope", 31, Rarity.UNCOMMON, mage.cards.r.RitualOfHope.class));
        cards.add(new SetCardInfo("Rockfall Vale", 266, Rarity.RARE, mage.cards.r.RockfallVale.class));
        cards.add(new SetCardInfo("Sacred Fire", 239, Rarity.UNCOMMON, mage.cards.s.SacredFire.class));
        cards.add(new SetCardInfo("Saryth, the Viper's Fang", 197, Rarity.RARE, mage.cards.s.SarythTheVipersFang.class));
        cards.add(new SetCardInfo("Seafaring Werewolf", 80, Rarity.RARE, mage.cards.s.SeafaringWerewolf.class));
        cards.add(new SetCardInfo("Secrets of the Key", 73, Rarity.COMMON, mage.cards.s.SecretsOfTheKey.class));
        cards.add(new SetCardInfo("Shipwreck Marsh", 267, Rarity.RARE, mage.cards.s.ShipwreckMarsh.class));
        cards.add(new SetCardInfo("Sigarda, Champion of Light", 240, Rarity.MYTHIC, mage.cards.s.SigardaChampionOfLight.class));
        cards.add(new SetCardInfo("Slaughter Specialist", 122, Rarity.RARE, mage.cards.s.SlaughterSpecialist.class));
        cards.add(new SetCardInfo("Snarling Wolf", 199, Rarity.COMMON, mage.cards.s.SnarlingWolf.class));
        cards.add(new SetCardInfo("Spectral Adversary", 77, Rarity.MYTHIC, mage.cards.s.SpectralAdversary.class));
        cards.add(new SetCardInfo("Spellrune Howler", 160, Rarity.UNCOMMON, mage.cards.s.SpellruneHowler.class));
        cards.add(new SetCardInfo("Spellrune Painter", 160, Rarity.UNCOMMON, mage.cards.s.SpellrunePainter.class));
        cards.add(new SetCardInfo("Startle", 78, Rarity.COMMON, mage.cards.s.Startle.class));
        cards.add(new SetCardInfo("Storm-Charged Slasher", 157, Rarity.RARE, mage.cards.s.StormChargedSlasher.class));
        cards.add(new SetCardInfo("Stormrider Spirit", 79, Rarity.COMMON, mage.cards.s.StormriderSpirit.class));
        cards.add(new SetCardInfo("Stuffed Bear", 259, Rarity.COMMON, mage.cards.s.StuffedBear.class));
        cards.add(new SetCardInfo("Sungold Barrage", 36, Rarity.COMMON, mage.cards.s.SungoldBarrage.class));
        cards.add(new SetCardInfo("Suspicious Stowaway", 80, Rarity.RARE, mage.cards.s.SuspiciousStowaway.class));
        cards.add(new SetCardInfo("Swamp", 272, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Tainted Adversary", 124, Rarity.MYTHIC, mage.cards.t.TaintedAdversary.class));
        cards.add(new SetCardInfo("Tavern Ruffian", 163, Rarity.COMMON, mage.cards.t.TavernRuffian.class));
        cards.add(new SetCardInfo("Tavern Smasher", 163, Rarity.COMMON, mage.cards.t.TavernSmasher.class));
        cards.add(new SetCardInfo("Thermo-Alchemist", 164, Rarity.UNCOMMON, mage.cards.t.ThermoAlchemist.class));
        cards.add(new SetCardInfo("Triskaidekaphile", 81, Rarity.RARE, mage.cards.t.Triskaidekaphile.class));
        cards.add(new SetCardInfo("Unnatural Growth", 206, Rarity.RARE, mage.cards.u.UnnaturalGrowth.class));
        cards.add(new SetCardInfo("Unruly Mob", 40, Rarity.COMMON, mage.cards.u.UnrulyMob.class));
        cards.add(new SetCardInfo("Untamed Pup", 187, Rarity.UNCOMMON, mage.cards.u.UntamedPup.class));
        cards.add(new SetCardInfo("Vampire Socialite", 249, Rarity.UNCOMMON, mage.cards.v.VampireSocialite.class));
        cards.add(new SetCardInfo("Vanquish the Horde", 41, Rarity.RARE, mage.cards.v.VanquishTheHorde.class));
        cards.add(new SetCardInfo("Village Reavers", 165, Rarity.UNCOMMON, mage.cards.v.VillageReavers.class));
        cards.add(new SetCardInfo("Village Watch", 165, Rarity.UNCOMMON, mage.cards.v.VillageWatch.class));
        cards.add(new SetCardInfo("Voldaren Ambusher", 166, Rarity.UNCOMMON, mage.cards.v.VoldarenAmbusher.class));
        cards.add(new SetCardInfo("Willow Geist", 207, Rarity.RARE, mage.cards.w.WillowGeist.class));
        cards.add(new SetCardInfo("Wing Shredder", 169, Rarity.COMMON, mage.cards.w.WingShredder.class));
        cards.add(new SetCardInfo("Wrenn and Seven", 208, Rarity.MYTHIC, mage.cards.w.WrennAndSeven.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is fully implemented
    }
}
