package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Plopman
 */
public final class Portal extends ExpansionSet {

    private static final Portal instance = new Portal();

    public static Portal getInstance() {
        return instance;
    }

    private Portal() {
        super("Portal", "POR", ExpansionSet.buildDate(1997, 5, 1), SetType.SUPPLEMENTAL);
        this.blockName = "Beginner";
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;

        cards.add(new SetCardInfo("Alabaster Dragon", 1, Rarity.RARE, mage.cards.a.AlabasterDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Alluring Scent", 157, Rarity.RARE, mage.cards.a.AlluringScent.class, RETRO_ART));
        cards.add(new SetCardInfo("Anaconda", 158, Rarity.UNCOMMON, mage.cards.a.Anaconda.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Anaconda", "158+", Rarity.UNCOMMON, mage.cards.a.Anaconda.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Ancestral Memories", 40, Rarity.RARE, mage.cards.a.AncestralMemories.class, RETRO_ART));
        cards.add(new SetCardInfo("Angelic Blessing", 2, Rarity.COMMON, mage.cards.a.AngelicBlessing.class, RETRO_ART));
        cards.add(new SetCardInfo("Archangel", 3, Rarity.RARE, mage.cards.a.Archangel.class, RETRO_ART));
        cards.add(new SetCardInfo("Ardent Militia", 4, Rarity.UNCOMMON, mage.cards.a.ArdentMilitia.class, RETRO_ART));
        cards.add(new SetCardInfo("Armageddon", 5, Rarity.RARE, mage.cards.a.Armageddon.class, RETRO_ART));
        cards.add(new SetCardInfo("Armored Pegasus", 6, Rarity.COMMON, mage.cards.a.ArmoredPegasus.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Armored Pegasus", "6d", Rarity.COMMON, mage.cards.a.ArmoredPegasus.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Arrogant Vampire", 79, Rarity.UNCOMMON, mage.cards.a.ArrogantVampire.class, RETRO_ART));
        cards.add(new SetCardInfo("Assassin's Blade", 80, Rarity.UNCOMMON, mage.cards.a.AssassinsBlade.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Assassin's Blade", "80s", Rarity.UNCOMMON, mage.cards.a.AssassinsBlade.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Balance of Power", 41, Rarity.RARE, mage.cards.b.BalanceOfPower.class, RETRO_ART));
        cards.add(new SetCardInfo("Baleful Stare", 42, Rarity.UNCOMMON, mage.cards.b.BalefulStare.class, RETRO_ART));
        cards.add(new SetCardInfo("Bee Sting", 159, Rarity.UNCOMMON, mage.cards.b.BeeSting.class, RETRO_ART));
        cards.add(new SetCardInfo("Blaze", 118, Rarity.UNCOMMON, mage.cards.b.Blaze.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Blaze", "118s", Rarity.UNCOMMON, mage.cards.b.Blaze.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Blaze", "118+", Rarity.UNCOMMON, mage.cards.b.Blaze.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Blessed Reversal", 7, Rarity.RARE, mage.cards.b.BlessedReversal.class, RETRO_ART));
        cards.add(new SetCardInfo("Blinding Light", 8, Rarity.RARE, mage.cards.b.BlindingLight.class, RETRO_ART));
        cards.add(new SetCardInfo("Bog Imp", 81, Rarity.COMMON, mage.cards.b.BogImp.class, RETRO_ART));
        cards.add(new SetCardInfo("Bog Raiders", 82, Rarity.COMMON, mage.cards.b.BogRaiders.class, RETRO_ART));
        cards.add(new SetCardInfo("Bog Wraith", 83, Rarity.UNCOMMON, mage.cards.b.BogWraith.class, RETRO_ART));
        cards.add(new SetCardInfo("Boiling Seas", 119, Rarity.UNCOMMON, mage.cards.b.BoilingSeas.class, RETRO_ART));
        cards.add(new SetCardInfo("Border Guard", 9, Rarity.COMMON, mage.cards.b.BorderGuard.class, RETRO_ART));
        cards.add(new SetCardInfo("Breath of Life", 10, Rarity.COMMON, mage.cards.b.BreathOfLife.class, RETRO_ART));
        cards.add(new SetCardInfo("Bull Hippo", 160, Rarity.UNCOMMON, mage.cards.b.BullHippo.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Bull Hippo", "160d", Rarity.UNCOMMON, mage.cards.b.BullHippo.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Burning Cloak", 120, Rarity.COMMON, mage.cards.b.BurningCloak.class, RETRO_ART));
        cards.add(new SetCardInfo("Capricious Sorcerer", 43, Rarity.RARE, mage.cards.c.CapriciousSorcerer.class, RETRO_ART));
        cards.add(new SetCardInfo("Charging Bandits", 84, Rarity.UNCOMMON, mage.cards.c.ChargingBandits.class, RETRO_ART));
        cards.add(new SetCardInfo("Charging Paladin", 11, Rarity.UNCOMMON, mage.cards.c.ChargingPaladin.class, RETRO_ART));
        cards.add(new SetCardInfo("Charging Rhino", 161, Rarity.RARE, mage.cards.c.ChargingRhino.class, RETRO_ART));
        cards.add(new SetCardInfo("Cloak of Feathers", 44, Rarity.COMMON, mage.cards.c.CloakOfFeathers.class, RETRO_ART));
        cards.add(new SetCardInfo("Cloud Dragon", 45, Rarity.RARE, mage.cards.c.CloudDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Cloud Pirates", 46, Rarity.COMMON, mage.cards.c.CloudPirates.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud Pirates", "46d", Rarity.COMMON, mage.cards.c.CloudPirates.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud Spirit", 47, Rarity.UNCOMMON, mage.cards.c.CloudSpirit.class, RETRO_ART));
        cards.add(new SetCardInfo("Command of Unsummoning", 48, Rarity.UNCOMMON, mage.cards.c.CommandOfUnsummoning.class, RETRO_ART));
        cards.add(new SetCardInfo("Coral Eel", 49, Rarity.COMMON, mage.cards.c.CoralEel.class, RETRO_ART));
        cards.add(new SetCardInfo("Craven Giant", 121, Rarity.COMMON, mage.cards.c.CravenGiant.class, RETRO_ART));
        cards.add(new SetCardInfo("Craven Knight", 85, Rarity.COMMON, mage.cards.c.CravenKnight.class, RETRO_ART));
        cards.add(new SetCardInfo("Cruel Bargain", 86, Rarity.RARE, mage.cards.c.CruelBargain.class, RETRO_ART));
        cards.add(new SetCardInfo("Cruel Fate", 50, Rarity.RARE, mage.cards.c.CruelFate.class, RETRO_ART));
        cards.add(new SetCardInfo("Cruel Tutor", 87, Rarity.RARE, mage.cards.c.CruelTutor.class, RETRO_ART));
        cards.add(new SetCardInfo("Deep Wood", 162, Rarity.UNCOMMON, mage.cards.d.DeepWood.class, RETRO_ART));
        cards.add(new SetCardInfo("Deep-Sea Serpent", 51, Rarity.UNCOMMON, mage.cards.d.DeepSeaSerpent.class, RETRO_ART));
        cards.add(new SetCardInfo("Defiant Stand", 12, Rarity.UNCOMMON, mage.cards.d.DefiantStand.class, RETRO_ART));
        cards.add(new SetCardInfo("Deja Vu", 53, Rarity.COMMON, mage.cards.d.DejaVu.class, RETRO_ART));
        cards.add(new SetCardInfo("Desert Drake", 122, Rarity.UNCOMMON, mage.cards.d.DesertDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Devastation", 123, Rarity.RARE, mage.cards.d.Devastation.class, RETRO_ART));
        cards.add(new SetCardInfo("Devoted Hero", 13, Rarity.COMMON, mage.cards.d.DevotedHero.class, RETRO_ART));
        cards.add(new SetCardInfo("Djinn of the Lamp", 52, Rarity.RARE, mage.cards.d.DjinnOfTheLamp.class, RETRO_ART));
        cards.add(new SetCardInfo("Dread Charge", 88, Rarity.RARE, mage.cards.d.DreadCharge.class, RETRO_ART));
        cards.add(new SetCardInfo("Dread Reaper", 89, Rarity.RARE, mage.cards.d.DreadReaper.class, RETRO_ART));
        cards.add(new SetCardInfo("Dry Spell", 90, Rarity.UNCOMMON, mage.cards.d.DrySpell.class, RETRO_ART));
        cards.add(new SetCardInfo("Earthquake", 124, Rarity.RARE, mage.cards.e.Earthquake.class, RETRO_ART));
        cards.add(new SetCardInfo("Ebon Dragon", 91, Rarity.RARE, mage.cards.e.EbonDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Elite Cat Warrior", 163, Rarity.COMMON, mage.cards.e.EliteCatWarrior.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Elite Cat Warrior", "163+", Rarity.COMMON, mage.cards.e.EliteCatWarrior.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Elven Cache", 164, Rarity.COMMON, mage.cards.e.ElvenCache.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Elven Cache", "164s", Rarity.COMMON, mage.cards.e.ElvenCache.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Elvish Ranger", 165, Rarity.COMMON, mage.cards.e.ElvishRanger.class, RETRO_ART));
        cards.add(new SetCardInfo("Endless Cockroaches", 92, Rarity.RARE, mage.cards.e.EndlessCockroaches.class, RETRO_ART));
        cards.add(new SetCardInfo("Exhaustion", 54, Rarity.RARE, mage.cards.e.Exhaustion.class, RETRO_ART));
        cards.add(new SetCardInfo("False Peace", 14, Rarity.COMMON, mage.cards.f.FalsePeace.class, RETRO_ART));
        cards.add(new SetCardInfo("Feral Shadow", 93, Rarity.COMMON, mage.cards.f.FeralShadow.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Feral Shadow", "93d", Rarity.COMMON, mage.cards.f.FeralShadow.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Final Strike", 94, Rarity.RARE, mage.cards.f.FinalStrike.class, RETRO_ART));
        cards.add(new SetCardInfo("Fire Dragon", 125, Rarity.RARE, mage.cards.f.FireDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Fire Imp", 126, Rarity.UNCOMMON, mage.cards.f.FireImp.class, RETRO_ART));
        cards.add(new SetCardInfo("Fire Snake", 127, Rarity.COMMON, mage.cards.f.FireSnake.class, RETRO_ART));
        cards.add(new SetCardInfo("Fire Tempest", 128, Rarity.RARE, mage.cards.f.FireTempest.class, RETRO_ART));
        cards.add(new SetCardInfo("Flashfires", 129, Rarity.UNCOMMON, mage.cards.f.Flashfires.class, RETRO_ART));
        cards.add(new SetCardInfo("Fleet-Footed Monk", 15, Rarity.COMMON, mage.cards.f.FleetFootedMonk.class, RETRO_ART));
        cards.add(new SetCardInfo("Flux", 55, Rarity.UNCOMMON, mage.cards.f.Flux.class, RETRO_ART));
        cards.add(new SetCardInfo("Foot Soldiers", 16, Rarity.COMMON, mage.cards.f.FootSoldiers.class, RETRO_ART));
        cards.add(new SetCardInfo("Forest", 212, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "212s", Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 213, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "213s", Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 214, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "214s", Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 215, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "215s", Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forked Lightning", 130, Rarity.RARE, mage.cards.f.ForkedLightning.class, RETRO_ART));
        cards.add(new SetCardInfo("Fruition", 166, Rarity.COMMON, mage.cards.f.Fruition.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Fruition", "166s", Rarity.COMMON, mage.cards.f.Fruition.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Giant Octopus", 56, Rarity.COMMON, mage.cards.g.GiantOctopus.class, RETRO_ART));
        cards.add(new SetCardInfo("Giant Spider", 167, Rarity.COMMON, mage.cards.g.GiantSpider.class, RETRO_ART));
        cards.add(new SetCardInfo("Gift of Estates", 17, Rarity.RARE, mage.cards.g.GiftOfEstates.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Bully", 131, Rarity.COMMON, mage.cards.g.GoblinBully.class, RETRO_ART));
        cards.add(new SetCardInfo("Gorilla Warrior", 168, Rarity.COMMON, mage.cards.g.GorillaWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Gravedigger", 95, Rarity.UNCOMMON, mage.cards.g.Gravedigger.class, RETRO_ART));
        cards.add(new SetCardInfo("Grizzly Bears", 169, Rarity.COMMON, mage.cards.g.GrizzlyBears.class, RETRO_ART));
        cards.add(new SetCardInfo("Hand of Death", 96, Rarity.COMMON, mage.cards.h.HandOfDeath.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Hand of Death", "96+", Rarity.COMMON, mage.cards.h.HandOfDeath.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Harsh Justice", 18, Rarity.RARE, mage.cards.h.HarshJustice.class, RETRO_ART));
        cards.add(new SetCardInfo("Highland Giant", 132, Rarity.COMMON, mage.cards.h.HighlandGiant.class, RETRO_ART));
        cards.add(new SetCardInfo("Hill Giant", 133, Rarity.COMMON, mage.cards.h.HillGiant.class, RETRO_ART));
        cards.add(new SetCardInfo("Horned Turtle", 57, Rarity.COMMON, mage.cards.h.HornedTurtle.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Horned Turtle", "57s", Rarity.COMMON, mage.cards.h.HornedTurtle.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Howling Fury", 97, Rarity.COMMON, mage.cards.h.HowlingFury.class, RETRO_ART));
        cards.add(new SetCardInfo("Hulking Cyclops", 134, Rarity.UNCOMMON, mage.cards.h.HulkingCyclops.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Hulking Cyclops", "134s", Rarity.UNCOMMON, mage.cards.h.HulkingCyclops.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Hulking Goblin", 135, Rarity.COMMON, mage.cards.h.HulkingGoblin.class, RETRO_ART));
        cards.add(new SetCardInfo("Hurricane", 170, Rarity.RARE, mage.cards.h.Hurricane.class, RETRO_ART));
        cards.add(new SetCardInfo("Ingenious Thief", 58, Rarity.UNCOMMON, mage.cards.i.IngeniousThief.class, RETRO_ART));
        cards.add(new SetCardInfo("Island", 200, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "200s", Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 201, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "201s", Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 202, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "202s", Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 203, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "203s", Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Jungle Lion", 171, Rarity.COMMON, mage.cards.j.JungleLion.class, RETRO_ART));
        cards.add(new SetCardInfo("Keen-Eyed Archers", 19, Rarity.COMMON, mage.cards.k.KeenEyedArchers.class, RETRO_ART));
        cards.add(new SetCardInfo("King's Assassin", 98, Rarity.RARE, mage.cards.k.KingsAssassin.class, RETRO_ART));
        cards.add(new SetCardInfo("Knight Errant", 20, Rarity.COMMON, mage.cards.k.KnightErrant.class, RETRO_ART));
        cards.add(new SetCardInfo("Last Chance", 136, Rarity.RARE, mage.cards.l.LastChance.class, RETRO_ART));
        cards.add(new SetCardInfo("Lava Axe", 137, Rarity.COMMON, mage.cards.l.LavaAxe.class, RETRO_ART));
        cards.add(new SetCardInfo("Lava Flow", 138, Rarity.UNCOMMON, mage.cards.l.LavaFlow.class, RETRO_ART));
        cards.add(new SetCardInfo("Lizard Warrior", 139, Rarity.COMMON, mage.cards.l.LizardWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Man-o'-War", 59, Rarity.UNCOMMON, mage.cards.m.ManOWar.class, RETRO_ART));
        cards.add(new SetCardInfo("Mercenary Knight", 99, Rarity.RARE, mage.cards.m.MercenaryKnight.class, RETRO_ART));
        cards.add(new SetCardInfo("Merfolk of the Pearl Trident", 60, Rarity.COMMON, mage.cards.m.MerfolkOfThePearlTrident.class, RETRO_ART));
        cards.add(new SetCardInfo("Mind Knives", 100, Rarity.COMMON, mage.cards.m.MindKnives.class, RETRO_ART));
        cards.add(new SetCardInfo("Mind Rot", 101, Rarity.COMMON, mage.cards.m.MindRot.class, RETRO_ART));
        cards.add(new SetCardInfo("Minotaur Warrior", 140, Rarity.COMMON, mage.cards.m.MinotaurWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Mobilize", 172, Rarity.COMMON, mage.cards.m.Mobilize.class, RETRO_ART));
        cards.add(new SetCardInfo("Monstrous Growth", 173, Rarity.COMMON, mage.cards.m.MonstrousGrowth.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Monstrous Growth", "173+", Rarity.COMMON, mage.cards.m.MonstrousGrowth.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Moon Sprite", 174, Rarity.UNCOMMON, mage.cards.m.MoonSprite.class, RETRO_ART));
        cards.add(new SetCardInfo("Mountain", 208, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "208s", Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 209, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "209s", Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 210, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "210s", Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 211, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "211s", Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain Goat", 141, Rarity.UNCOMMON, mage.cards.m.MountainGoat.class, RETRO_ART));
        cards.add(new SetCardInfo("Muck Rats", 102, Rarity.COMMON, mage.cards.m.MuckRats.class, RETRO_ART));
        cards.add(new SetCardInfo("Mystic Denial", 61, Rarity.UNCOMMON, mage.cards.m.MysticDenial.class, RETRO_ART));
        cards.add(new SetCardInfo("Natural Order", 175, Rarity.RARE, mage.cards.n.NaturalOrder.class, RETRO_ART));
        cards.add(new SetCardInfo("Natural Spring", 176, Rarity.UNCOMMON, mage.cards.n.NaturalSpring.class, RETRO_ART));
        cards.add(new SetCardInfo("Nature's Cloak", 177, Rarity.RARE, mage.cards.n.NaturesCloak.class, RETRO_ART));
        cards.add(new SetCardInfo("Nature's Lore", 178, Rarity.COMMON, mage.cards.n.NaturesLore.class, RETRO_ART));
        cards.add(new SetCardInfo("Nature's Ruin", 103, Rarity.UNCOMMON, mage.cards.n.NaturesRuin.class, RETRO_ART));
        cards.add(new SetCardInfo("Needle Storm", 179, Rarity.UNCOMMON, mage.cards.n.NeedleStorm.class, RETRO_ART));
        cards.add(new SetCardInfo("Noxious Toad", 104, Rarity.UNCOMMON, mage.cards.n.NoxiousToad.class, RETRO_ART));
        cards.add(new SetCardInfo("Omen", 62, Rarity.COMMON, mage.cards.o.Omen.class, RETRO_ART));
        cards.add(new SetCardInfo("Owl Familiar", 63, Rarity.COMMON, mage.cards.o.OwlFamiliar.class, RETRO_ART));
        cards.add(new SetCardInfo("Panther Warriors", 180, Rarity.COMMON, mage.cards.p.PantherWarriors.class, RETRO_ART));
        cards.add(new SetCardInfo("Path of Peace", 21, Rarity.COMMON, mage.cards.p.PathOfPeace.class, RETRO_ART));
        cards.add(new SetCardInfo("Personal Tutor", 64, Rarity.UNCOMMON, mage.cards.p.PersonalTutor.class, RETRO_ART));
        cards.add(new SetCardInfo("Phantom Warrior", 65, Rarity.RARE, mage.cards.p.PhantomWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Pillaging Horde", 142, Rarity.RARE, mage.cards.p.PillagingHorde.class, RETRO_ART));
        cards.add(new SetCardInfo("Plains", 196, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "196s", Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 197, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "197s", Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 198, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "198s", Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 199, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "199s", Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plant Elemental", 181, Rarity.UNCOMMON, mage.cards.p.PlantElemental.class, RETRO_ART));
        cards.add(new SetCardInfo("Primeval Force", 182, Rarity.RARE, mage.cards.p.PrimevalForce.class, RETRO_ART));
        cards.add(new SetCardInfo("Prosperity", 66, Rarity.RARE, mage.cards.p.Prosperity.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Prosperity", "66s", Rarity.RARE, mage.cards.p.Prosperity.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyroclasm", 143, Rarity.RARE, mage.cards.p.Pyroclasm.class, RETRO_ART));
        cards.add(new SetCardInfo("Python", 105, Rarity.COMMON, mage.cards.p.Python.class, RETRO_ART));
        cards.add(new SetCardInfo("Raging Cougar", 144, Rarity.COMMON, mage.cards.r.RagingCougar.class, RETRO_ART));
        cards.add(new SetCardInfo("Raging Goblin", 145, Rarity.COMMON, mage.cards.r.RagingGoblin.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Raging Goblin", "145+", Rarity.COMMON, mage.cards.r.RagingGoblin.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Raging Minotaur", 146, Rarity.COMMON, mage.cards.r.RagingMinotaur.class, RETRO_ART));
        cards.add(new SetCardInfo("Rain of Salt", 147, Rarity.UNCOMMON, mage.cards.r.RainOfSalt.class, RETRO_ART));
        cards.add(new SetCardInfo("Rain of Tears", 106, Rarity.UNCOMMON, mage.cards.r.RainOfTears.class, RETRO_ART));
        cards.add(new SetCardInfo("Raise Dead", 107, Rarity.COMMON, mage.cards.r.RaiseDead.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Raise Dead", "107s", Rarity.COMMON, mage.cards.r.RaiseDead.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Redwood Treefolk", 183, Rarity.COMMON, mage.cards.r.RedwoodTreefolk.class, RETRO_ART));
        cards.add(new SetCardInfo("Regal Unicorn", 22, Rarity.COMMON, mage.cards.r.RegalUnicorn.class, RETRO_ART));
        cards.add(new SetCardInfo("Renewing Dawn", 23, Rarity.UNCOMMON, mage.cards.r.RenewingDawn.class, RETRO_ART));
        cards.add(new SetCardInfo("Rowan Treefolk", 184, Rarity.COMMON, mage.cards.r.RowanTreefolk.class, RETRO_ART));
        cards.add(new SetCardInfo("Sacred Knight", 24, Rarity.COMMON, mage.cards.s.SacredKnight.class, RETRO_ART));
        cards.add(new SetCardInfo("Sacred Nectar", 25, Rarity.COMMON, mage.cards.s.SacredNectar.class, RETRO_ART));
        cards.add(new SetCardInfo("Scorching Spear", 148, Rarity.COMMON, mage.cards.s.ScorchingSpear.class, RETRO_ART));
        cards.add(new SetCardInfo("Scorching Winds", 149, Rarity.UNCOMMON, mage.cards.s.ScorchingWinds.class, RETRO_ART));
        cards.add(new SetCardInfo("Seasoned Marshal", 26, Rarity.UNCOMMON, mage.cards.s.SeasonedMarshal.class, RETRO_ART));
        cards.add(new SetCardInfo("Serpent Assassin", 108, Rarity.RARE, mage.cards.s.SerpentAssassin.class, RETRO_ART));
        cards.add(new SetCardInfo("Serpent Warrior", 109, Rarity.COMMON, mage.cards.s.SerpentWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Skeletal Crocodile", 110, Rarity.COMMON, mage.cards.s.SkeletalCrocodile.class, RETRO_ART));
        cards.add(new SetCardInfo("Skeletal Snake", 111, Rarity.COMMON, mage.cards.s.SkeletalSnake.class, RETRO_ART));
        cards.add(new SetCardInfo("Snapping Drake", 67, Rarity.COMMON, mage.cards.s.SnappingDrake.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Snapping Drake", "67d", Rarity.COMMON, mage.cards.s.SnappingDrake.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Sorcerous Sight", 68, Rarity.COMMON, mage.cards.s.SorcerousSight.class, RETRO_ART));
        cards.add(new SetCardInfo("Soul Shred", 112, Rarity.COMMON, mage.cards.s.SoulShred.class, RETRO_ART));
        cards.add(new SetCardInfo("Spined Wurm", 185, Rarity.COMMON, mage.cards.s.SpinedWurm.class, RETRO_ART));
        cards.add(new SetCardInfo("Spiritual Guardian", 27, Rarity.RARE, mage.cards.s.SpiritualGuardian.class, RETRO_ART));
        cards.add(new SetCardInfo("Spitting Earth", 150, Rarity.COMMON, mage.cards.s.SpittingEarth.class, RETRO_ART));
        cards.add(new SetCardInfo("Spotted Griffin", 28, Rarity.COMMON, mage.cards.s.SpottedGriffin.class, RETRO_ART));
        cards.add(new SetCardInfo("Stalking Tiger", 186, Rarity.COMMON, mage.cards.s.StalkingTiger.class, RETRO_ART));
        cards.add(new SetCardInfo("Starlight", 29, Rarity.UNCOMMON, mage.cards.s.Starlight.class, RETRO_ART));
        cards.add(new SetCardInfo("Starlit Angel", 30, Rarity.UNCOMMON, mage.cards.s.StarlitAngel.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Starlit Angel", "30s", Rarity.UNCOMMON, mage.cards.s.StarlitAngel.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Steadfastness", 31, Rarity.COMMON, mage.cards.s.Steadfastness.class, RETRO_ART));
        cards.add(new SetCardInfo("Stern Marshal", 32, Rarity.RARE, mage.cards.s.SternMarshal.class, RETRO_ART));
        cards.add(new SetCardInfo("Stone Rain", 151, Rarity.COMMON, mage.cards.s.StoneRain.class, RETRO_ART));
        cards.add(new SetCardInfo("Storm Crow", 69, Rarity.COMMON, mage.cards.s.StormCrow.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Storm Crow", "69d", Rarity.COMMON, mage.cards.s.StormCrow.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Summer Bloom", 187, Rarity.RARE, mage.cards.s.SummerBloom.class, RETRO_ART));
        cards.add(new SetCardInfo("Swamp", 204, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 205, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 206, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 207, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Sylvan Tutor", 188, Rarity.RARE, mage.cards.s.SylvanTutor.class, RETRO_ART));
        cards.add(new SetCardInfo("Symbol of Unsummoning", 70, Rarity.COMMON, mage.cards.s.SymbolOfUnsummoning.class, RETRO_ART));
        cards.add(new SetCardInfo("Taunt", 71, Rarity.RARE, mage.cards.t.Taunt.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Taunt", "71s", Rarity.RARE, mage.cards.t.Taunt.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Temporary Truce", 33, Rarity.RARE, mage.cards.t.TemporaryTruce.class, RETRO_ART));
        cards.add(new SetCardInfo("Theft of Dreams", 72, Rarity.UNCOMMON, mage.cards.t.TheftOfDreams.class, RETRO_ART));
        cards.add(new SetCardInfo("Thing from the Deep", 73, Rarity.RARE, mage.cards.t.ThingFromTheDeep.class, RETRO_ART));
        cards.add(new SetCardInfo("Thundering Wurm", 189, Rarity.RARE, mage.cards.t.ThunderingWurm.class, RETRO_ART));
        cards.add(new SetCardInfo("Thundermare", 152, Rarity.RARE, mage.cards.t.Thundermare.class, RETRO_ART));
        cards.add(new SetCardInfo("Tidal Surge", 74, Rarity.COMMON, mage.cards.t.TidalSurge.class, RETRO_ART));
        cards.add(new SetCardInfo("Time Ebb", 75, Rarity.COMMON, mage.cards.t.TimeEbb.class, RETRO_ART));
        cards.add(new SetCardInfo("Touch of Brilliance", 76, Rarity.COMMON, mage.cards.t.TouchOfBrilliance.class, RETRO_ART));
        cards.add(new SetCardInfo("Treetop Defense", 190, Rarity.RARE, mage.cards.t.TreetopDefense.class, RETRO_ART));
        cards.add(new SetCardInfo("Undying Beast", 113, Rarity.COMMON, mage.cards.u.UndyingBeast.class, RETRO_ART));
        cards.add(new SetCardInfo("Untamed Wilds", 191, Rarity.UNCOMMON, mage.cards.u.UntamedWilds.class, RETRO_ART));
        cards.add(new SetCardInfo("Valorous Charge", 34, Rarity.UNCOMMON, mage.cards.v.ValorousCharge.class, RETRO_ART));
        cards.add(new SetCardInfo("Vampiric Feast", 114, Rarity.UNCOMMON, mage.cards.v.VampiricFeast.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Vampiric Feast", "114s", Rarity.UNCOMMON, mage.cards.v.VampiricFeast.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Vampiric Touch", 115, Rarity.COMMON, mage.cards.v.VampiricTouch.class, RETRO_ART));
        cards.add(new SetCardInfo("Venerable Monk", 35, Rarity.UNCOMMON, mage.cards.v.VenerableMonk.class, RETRO_ART));
        cards.add(new SetCardInfo("Vengeance", 36, Rarity.UNCOMMON, mage.cards.v.Vengeance.class, RETRO_ART));
        cards.add(new SetCardInfo("Virtue's Ruin", 116, Rarity.UNCOMMON, mage.cards.v.VirtuesRuin.class, RETRO_ART));
        cards.add(new SetCardInfo("Volcanic Dragon", 153, Rarity.RARE, mage.cards.v.VolcanicDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Volcanic Hammer", 154, Rarity.COMMON, mage.cards.v.VolcanicHammer.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Granite", 155, Rarity.UNCOMMON, mage.cards.w.WallOfGranite.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Swords", 37, Rarity.UNCOMMON, mage.cards.w.WallOfSwords.class, RETRO_ART));
        cards.add(new SetCardInfo("Warrior's Charge", 38, Rarity.COMMON, mage.cards.w.WarriorsCharge.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Warrior's Charge", "38+", Rarity.COMMON, mage.cards.w.WarriorsCharge.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Whiptail Wurm", 192, Rarity.UNCOMMON, mage.cards.w.WhiptailWurm.class, RETRO_ART));
        cards.add(new SetCardInfo("Wicked Pact", 117, Rarity.RARE, mage.cards.w.WickedPact.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Wicked Pact", "117s", Rarity.RARE, mage.cards.w.WickedPact.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Willow Dryad", 193, Rarity.COMMON, mage.cards.w.WillowDryad.class, RETRO_ART));
        cards.add(new SetCardInfo("Wind Drake", 77, Rarity.COMMON, mage.cards.w.WindDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Winds of Change", 156, Rarity.RARE, mage.cards.w.WindsOfChange.class, RETRO_ART));
        cards.add(new SetCardInfo("Winter's Grasp", 194, Rarity.UNCOMMON, mage.cards.w.WintersGrasp.class, RETRO_ART));
        cards.add(new SetCardInfo("Withering Gaze", 78, Rarity.UNCOMMON, mage.cards.w.WitheringGaze.class, RETRO_ART));
        cards.add(new SetCardInfo("Wood Elves", 195, Rarity.RARE, mage.cards.w.WoodElves.class, RETRO_ART));
        cards.add(new SetCardInfo("Wrath of God", 39, Rarity.RARE, mage.cards.w.WrathOfGod.class, RETRO_ART));
    }

    @Override
    public BoosterCollator createCollator() {
        return new PortalCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/por.html
// Packs receive 7 seeded cards for an ally colour pair (2 basics, 4 commons, 1 uncommon),
// and then 1 rare, 2 uncommons, 5 basics (one of each colour) from the rest of the pool.
class PortalCollator implements BoosterCollator {
    private final CardRun common = new CardRun(true, "81", "10", "131", "68", "186", "85", "14", "135", "69", "164", "105", "15", "140", "74", "183", "102", "13", "132", "44", "163", "82", "31", "144", "63", "164", "81", "6", "145", "76", "184", "97", "19", "132", "56", "172", "93", "25", "133", "75", "169", "107", "24", "120", "60", "180", "101", "28", "139", "49", "166", "110", "9", "148", "46", "193", "115", "9", "131", "68", "186", "85", "14", "135", "69", "166", "105", "10", "140", "74", "183", "102", "13", "137", "49", "163", "82", "31", "144", "63", "180", "115", "6", "145", "76", "184", "97", "19", "137", "56", "172", "93", "25", "133", "75", "169", "107", "24", "120", "60", "168", "101", "28", "139", "44", "168", "110", "15", "148", "46", "193");
    private final CardRun uncommon = new CardRun(true, "126", "55", "158", "36", "95", "138", "48", "174", "29", "116", "141", "64", "158", "34", "106", "129", "51", "179", "23", "83", "149", "58", "176", "29", "79", "138", "64", "162", "12", "95", "126", "42", "160", "36", "103", "141", "55", "191", "4", "103", "129", "78", "162", "4", "106", "155", "48", "174", "34", "83", "119", "58", "174", "34", "90", "126", "42", "160", "23", "90", "138", "55", "160", "23", "83", "149", "78", "191", "12", "83", "149", "58", "191", "12", "79", "119", "58", "179", "29", "79", "129", "51", "162", "36", "95", "119", "42", "160", "36", "116", "155", "48", "176", "35", "116", "138", "51", "176", "35", "106", "141", "64", "191", "35", "103", "155", "78", "179", "4", "90");
    private final CardRun seedGW = new CardRun(true, "196", "213", "173", "165", "20", "2", "11", "197", "214", "173", "165", "38", "2", "26", "198", "215", "173", "171", "20", "2", "159", "199", "212", "173", "171", "20", "2", "181", "197", "213", "171", "165", "38", "2", "26", "198", "214", "171", "165", "20", "38", "159", "199", "215", "171", "165", "20", "38", "181");
    private final CardRun seedWU = new CardRun(true, "196", "203", "21", "16", "77", "57", "37", "197", "200", "22", "16", "77", "62", "30", "197", "201", "21", "22", "77", "62", "47", "198", "201", "22", "16", "57", "62", "61", "198", "202", "21", "16", "57", "62", "30", "199", "202", "21", "22", "77", "57", "61");
    private final CardRun seedRG = new CardRun(true, "208", "213", "154", "127", "185", "178", "192", "210", "214", "154", "127", "167", "185", "194", "209", "215", "151", "127", "178", "167", "134", "211", "212", "151", "127", "167", "185", "147", "210", "213", "151", "154", "178", "167", "192", "209", "214", "154", "185", "167", "151", "194", "211", "215", "151", "154", "185", "178", "134");
    private final CardRun seedUB = new CardRun(true, "204", "203", "109", "100", "67", "53", "104", "205", "200", "100", "113", "70", "67", "80", "206", "201", "113", "109", "53", "70", "72", "207", "201", "109", "100", "53", "70", "59", "205", "202", "113", "109", "70", "67", "80", "206", "202", "100", "113", "67", "53", "59");
    private final CardRun seedBR = new CardRun(true, "208", "205", "112", "96", "150", "146", "84", "210", "204", "96", "111", "146", "121", "114", "209", "205", "96", "111", "121", "150", "122", "211", "206", "111", "112", "121", "150", "118", "210", "207", "96", "112", "146", "150", "84", "209", "206", "111", "96", "146", "150", "114", "211", "207", "112", "96", "146", "121", "122", "204", "209", "112", "96", "146", "121", "118", "205", "211", "111", "112", "121", "150");
    private final CardRun rare = new CardRun(false, "1", "157", "40", "3", "5", "41", "7", "8", "43", "161", "45", "86", "50", "87", "123", "52", "88", "89", "124", "91", "92", "54", "94", "125", "128", "130", "17", "18", "170", "98", "136", "99", "175", "177", "65", "142", "182", "66", "143", "108", "27", "32", "187", "188", "71", "33", "73", "189", "152", "190", "153", "117", "156", "195", "39");

    private final BoosterStructure C5 = new BoosterStructure(
            common, common, common, common, common
    );
    private final BoosterStructure U2 = new BoosterStructure(
            uncommon, uncommon
    );
    private final BoosterStructure R1 = new BoosterStructure(rare);

    private final BoosterStructure Sgw = new BoosterStructure(
            seedGW, seedGW, seedGW, seedGW, seedGW,  seedGW, seedGW
    );
    private final BoosterStructure Swu = new BoosterStructure(
            seedWU, seedWU, seedWU, seedWU, seedWU,  seedWU, seedWU
    );
    private final BoosterStructure Srg = new BoosterStructure(
            seedRG, seedRG, seedRG, seedRG, seedRG,  seedRG, seedRG
    );
    private final BoosterStructure Sub = new BoosterStructure(
            seedUB, seedUB, seedUB, seedUB, seedUB,  seedUB, seedUB
    );
    private final BoosterStructure Sbr = new BoosterStructure(
            seedBR, seedBR, seedBR, seedBR, seedBR,  seedBR, seedBR
    );

    private final RarityConfiguration commonRuns = new RarityConfiguration(C5);

    private final RarityConfiguration uncommonRuns = new RarityConfiguration(U2);

    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);

// ratios based on one copy of the BR sheet for every two copies of the other sheets with two colour pairs on them
    private final RarityConfiguration seededRuns = new RarityConfiguration(
            Sgw, Sgw, Sgw, Sgw, Sgw,  Sgw, Sgw, Sgw, Sgw, Sgw,  Sgw, Sgw, Sgw, Sgw,
            Swu, Swu, Swu, Swu, Swu,  Swu, Swu, Swu, Swu, Swu,  Swu, Swu,
            Srg, Srg, Srg, Srg, Srg,  Srg, Srg, Srg, Srg, Srg,  Srg, Srg, Srg, Srg,
            Sub, Sub, Sub, Sub, Sub,  Sub, Sub, Sub, Sub, Sub,  Sub, Sub,
            Sbr, Sbr, Sbr, Sbr, Sbr,  Sbr, Sbr, Sbr, Sbr, Sbr,  Sbr, Sbr, Sbr
    );

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(seededRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(commonRuns.getNext().makeRun());
        return booster;
    }
}