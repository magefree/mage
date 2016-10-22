/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */

package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;
import mage.constants.Rarity;
import mage.cards.CardGraphicInfo;

/**
 *
 * @author Plopman
 */

public class Portal extends ExpansionSet {

    private static final Portal fINSTANCE = new Portal();

    /**
     *
     * @return
     */
    public static Portal getInstance() {
        return fINSTANCE;
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
        cards.add(new SetCardInfo("Alabaster Dragon", 163, Rarity.RARE, mage.cards.a.AlabasterDragon.class));
        cards.add(new SetCardInfo("Alluring Scent", 80, Rarity.RARE, mage.cards.a.AlluringScent.class));
        cards.add(new SetCardInfo("Anaconda", 81, Rarity.UNCOMMON, mage.cards.a.Anaconda.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Anaconda", 82, Rarity.UNCOMMON, mage.cards.a.Anaconda.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Ancestral Memories", 41, Rarity.RARE, mage.cards.a.AncestralMemories.class));
        cards.add(new SetCardInfo("Angelic Blessing", 164, Rarity.COMMON, mage.cards.a.AngelicBlessing.class));
        cards.add(new SetCardInfo("Archangel", 165, Rarity.RARE, mage.cards.a.Archangel.class));
        cards.add(new SetCardInfo("Ardent Militia", 166, Rarity.UNCOMMON, mage.cards.a.ArdentMilitia.class));
        cards.add(new SetCardInfo("Armageddon", 167, Rarity.RARE, mage.cards.a.Armageddon.class));
        cards.add(new SetCardInfo("Armored Pegasus", 168, Rarity.COMMON, mage.cards.a.ArmoredPegasus.class));
        cards.add(new SetCardInfo("Arrogant Vampire", 1, Rarity.UNCOMMON, mage.cards.a.ArrogantVampire.class));
        cards.add(new SetCardInfo("Balance of Power", 42, Rarity.RARE, mage.cards.b.BalanceOfPower.class));
        cards.add(new SetCardInfo("Baleful Stare", 43, Rarity.UNCOMMON, mage.cards.b.BalefulStare.class));
        cards.add(new SetCardInfo("Bee Sting", 83, Rarity.UNCOMMON, mage.cards.b.BeeSting.class));
        cards.add(new SetCardInfo("Blaze", 122, Rarity.UNCOMMON, mage.cards.b.Blaze.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Blaze", 123, Rarity.UNCOMMON, mage.cards.b.Blaze.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Blessed Reversal", 169, Rarity.RARE, mage.cards.b.BlessedReversal.class));
        cards.add(new SetCardInfo("Blinding Light", 170, Rarity.RARE, mage.cards.b.BlindingLight.class));
        cards.add(new SetCardInfo("Bog Imp", 3, Rarity.COMMON, mage.cards.b.BogImp.class));
        cards.add(new SetCardInfo("Bog Raiders", 4, Rarity.COMMON, mage.cards.b.BogRaiders.class));
        cards.add(new SetCardInfo("Bog Wraith", 5, Rarity.UNCOMMON, mage.cards.b.BogWraith.class));
        cards.add(new SetCardInfo("Boiling Seas", 124, Rarity.UNCOMMON, mage.cards.b.BoilingSeas.class));
        cards.add(new SetCardInfo("Border Guard", 171, Rarity.COMMON, mage.cards.b.BorderGuard.class));
        cards.add(new SetCardInfo("Breath of Life", 172, Rarity.COMMON, mage.cards.b.BreathOfLife.class));
        cards.add(new SetCardInfo("Bull Hippo", 84, Rarity.UNCOMMON, mage.cards.b.BullHippo.class));
        cards.add(new SetCardInfo("Burning Cloak", 125, Rarity.COMMON, mage.cards.b.BurningCloak.class));
        cards.add(new SetCardInfo("Capricious Sorcerer", 44, Rarity.RARE, mage.cards.c.CapriciousSorcerer.class));
        cards.add(new SetCardInfo("Charging Bandits", 6, Rarity.UNCOMMON, mage.cards.c.ChargingBandits.class));
        cards.add(new SetCardInfo("Charging Paladin", 173, Rarity.UNCOMMON, mage.cards.c.ChargingPaladin.class));
        cards.add(new SetCardInfo("Charging Rhino", 85, Rarity.RARE, mage.cards.c.ChargingRhino.class));
        cards.add(new SetCardInfo("Cloak of Feathers", 45, Rarity.COMMON, mage.cards.c.CloakOfFeathers.class));
        cards.add(new SetCardInfo("Cloud Dragon", 46, Rarity.RARE, mage.cards.c.CloudDragon.class));
        cards.add(new SetCardInfo("Cloud Pirates", 47, Rarity.COMMON, mage.cards.c.CloudPirates.class));
        cards.add(new SetCardInfo("Cloud Spirit", 48, Rarity.UNCOMMON, mage.cards.c.CloudSpirit.class));
        cards.add(new SetCardInfo("Coral Eel", 50, Rarity.COMMON, mage.cards.c.CoralEel.class));
        cards.add(new SetCardInfo("Craven Giant", 126, Rarity.COMMON, mage.cards.c.CravenGiant.class));
        cards.add(new SetCardInfo("Craven Knight", 7, Rarity.COMMON, mage.cards.c.CravenKnight.class));
        cards.add(new SetCardInfo("Cruel Bargain", 8, Rarity.RARE, mage.cards.c.CruelBargain.class));
        cards.add(new SetCardInfo("Cruel Tutor", 9, Rarity.RARE, mage.cards.c.CruelTutor.class));
        cards.add(new SetCardInfo("Deep-Sea Serpent", 52, Rarity.UNCOMMON, mage.cards.d.DeepSeaSerpent.class));
        cards.add(new SetCardInfo("Deja Vu", 53, Rarity.COMMON, mage.cards.d.DejaVu.class));
        cards.add(new SetCardInfo("Desert Drake", 127, Rarity.UNCOMMON, mage.cards.d.DesertDrake.class));
        cards.add(new SetCardInfo("Devastation", 128, Rarity.RARE, mage.cards.d.Devastation.class));
        cards.add(new SetCardInfo("Devoted Hero", 175, Rarity.COMMON, mage.cards.d.DevotedHero.class));
        cards.add(new SetCardInfo("Djinn of the Lamp", 54, Rarity.RARE, mage.cards.d.DjinnOfTheLamp.class));
        cards.add(new SetCardInfo("Dread Reaper", 11, Rarity.RARE, mage.cards.d.DreadReaper.class));
        cards.add(new SetCardInfo("Dry Spell", 12, Rarity.UNCOMMON, mage.cards.d.DrySpell1.class));
        cards.add(new SetCardInfo("Earthquake", 129, Rarity.RARE, mage.cards.e.Earthquake.class));
        cards.add(new SetCardInfo("Ebon Dragon", 13, Rarity.RARE, mage.cards.e.EbonDragon.class));
        cards.add(new SetCardInfo("Elite Cat Warrior", 87, Rarity.COMMON, mage.cards.e.EliteCatWarrior1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Elite Cat Warrior", 88, Rarity.COMMON, mage.cards.e.EliteCatWarrior1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Elven Cache", 89, Rarity.COMMON, mage.cards.e.ElvenCache.class));
        cards.add(new SetCardInfo("Elvish Ranger", 90, Rarity.COMMON, mage.cards.e.ElvishRanger.class));
        cards.add(new SetCardInfo("Endless Cockroaches", 14, Rarity.RARE, mage.cards.e.EndlessCockroaches.class));
        cards.add(new SetCardInfo("Exhaustion", 55, Rarity.RARE, mage.cards.e.Exhaustion.class));
        cards.add(new SetCardInfo("False Peace", 176, Rarity.COMMON, mage.cards.f.FalsePeace.class));
        cards.add(new SetCardInfo("Feral Shadow", 15, Rarity.COMMON, mage.cards.f.FeralShadow.class));
        cards.add(new SetCardInfo("Final Strike", 16, Rarity.RARE, mage.cards.f.FinalStrike.class));
        cards.add(new SetCardInfo("Fire Dragon", 130, Rarity.RARE, mage.cards.f.FireDragon.class));
        cards.add(new SetCardInfo("Fire Imp", 131, Rarity.UNCOMMON, mage.cards.f.FireImp.class));
        cards.add(new SetCardInfo("Fire Snake", 132, Rarity.COMMON, mage.cards.f.FireSnake.class));
        cards.add(new SetCardInfo("Fire Tempest", 133, Rarity.RARE, mage.cards.f.FireTempest.class));
        cards.add(new SetCardInfo("Flashfires", 134, Rarity.UNCOMMON, mage.cards.f.Flashfires.class));
        cards.add(new SetCardInfo("Fleet-Footed Monk", 177, Rarity.COMMON, mage.cards.f.FleetFootedMonk.class));
	cards.add(new SetCardInfo("Flux", 56, Rarity.UNCOMMON, mage.cards.f.Flux.class));
        cards.add(new SetCardInfo("Foot Soldiers", 178, Rarity.COMMON, mage.cards.f.FootSoldiers.class));
        cards.add(new SetCardInfo("Forest", 203, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 204, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 205, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 206, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Fruition", 91, Rarity.COMMON, mage.cards.f.Fruition.class));
        cards.add(new SetCardInfo("Giant Octopus", 57, Rarity.COMMON, mage.cards.g.GiantOctopus.class));
        cards.add(new SetCardInfo("Giant Spider", 92, Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Gift of Estates", 179, Rarity.RARE, mage.cards.g.GiftOfEstates.class));
        cards.add(new SetCardInfo("Goblin Bully", 136, Rarity.COMMON, mage.cards.g.GoblinBully.class));
        cards.add(new SetCardInfo("Gorilla Warrior", 93, Rarity.COMMON, mage.cards.g.GorillaWarrior.class));
        cards.add(new SetCardInfo("Gravedigger", 17, Rarity.UNCOMMON, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Grizzly Bears", 94, Rarity.COMMON, mage.cards.g.GrizzlyBears.class));
        cards.add(new SetCardInfo("Hand of Death", 18, Rarity.COMMON, mage.cards.h.HandOfDeath.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Hand of Death", 19, Rarity.COMMON, mage.cards.h.HandOfDeath.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Highland Giant", 137, Rarity.COMMON, mage.cards.h.HighlandGiant.class));
        cards.add(new SetCardInfo("Hill Giant", 138, Rarity.COMMON, mage.cards.h.HillGiant.class));
        cards.add(new SetCardInfo("Horned Turtle", 58, Rarity.COMMON, mage.cards.h.HornedTurtle.class));
        cards.add(new SetCardInfo("Howling Fury", 20, Rarity.COMMON, mage.cards.h.HowlingFury.class));
        cards.add(new SetCardInfo("Hulking Cyclops", 139, Rarity.UNCOMMON, mage.cards.h.HulkingCyclops.class));
        cards.add(new SetCardInfo("Hulking Goblin", 140, Rarity.COMMON, mage.cards.h.HulkingGoblin.class));
        cards.add(new SetCardInfo("Hurricane", 95, Rarity.RARE, mage.cards.h.Hurricane.class));
        cards.add(new SetCardInfo("Ingenious Thief", 59, Rarity.UNCOMMON, mage.cards.i.IngeniousThief.class));
        cards.add(new SetCardInfo("Island", 207, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 208, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 209, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 210, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Jungle Lion", 96, Rarity.COMMON, mage.cards.j.JungleLion.class));
        cards.add(new SetCardInfo("Keen-Eyed Archers", 181, Rarity.COMMON, mage.cards.k.KeenEyedArchers.class));
        cards.add(new SetCardInfo("King's Assassin", 21, Rarity.RARE, mage.cards.k.KingsAssassin.class));
        cards.add(new SetCardInfo("Knight Errant", 182, Rarity.COMMON, mage.cards.k.KnightErrant.class));
        cards.add(new SetCardInfo("Last Chance", 141, Rarity.RARE, mage.cards.l.LastChance.class));
        cards.add(new SetCardInfo("Lava Axe", 142, Rarity.COMMON, mage.cards.l.LavaAxe.class));
        cards.add(new SetCardInfo("Lava Flow", 143, Rarity.UNCOMMON, mage.cards.l.LavaFlow.class));
        cards.add(new SetCardInfo("Lizard Warrior", 144, Rarity.COMMON, mage.cards.l.LizardWarrior.class));
        cards.add(new SetCardInfo("Man-o'-War", 60, Rarity.UNCOMMON, mage.cards.m.ManOWar.class));
        cards.add(new SetCardInfo("Mercenary Knight", 22, Rarity.RARE, mage.cards.m.MercenaryKnight.class));
        cards.add(new SetCardInfo("Merfolk of the Pearl Trident", 61, Rarity.COMMON, mage.cards.m.MerfolkOfThePearlTrident.class));
        cards.add(new SetCardInfo("Mind Knives", 23, Rarity.COMMON, mage.cards.m.MindKnives.class));
        cards.add(new SetCardInfo("Mind Rot", 24, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Minotaur Warrior", 145, Rarity.COMMON, mage.cards.m.MinotaurWarrior.class));
        cards.add(new SetCardInfo("Mobilize", 97, Rarity.COMMON, mage.cards.m.Mobilize.class));
        cards.add(new SetCardInfo("Monstrous Growth", 98, Rarity.COMMON, mage.cards.m.MonstrousGrowth.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Monstrous Growth", 99, Rarity.COMMON, mage.cards.m.MonstrousGrowth.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Moon Sprite", 100, Rarity.UNCOMMON, mage.cards.m.MoonSprite.class));
        cards.add(new SetCardInfo("Mountain", 211, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 212, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 213, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 214, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain Goat", 146, Rarity.UNCOMMON, mage.cards.m.MountainGoat.class));
        cards.add(new SetCardInfo("Muck Rats", 25, Rarity.COMMON, mage.cards.m.MuckRats.class));
        cards.add(new SetCardInfo("Mystic Denial", 62, Rarity.UNCOMMON, mage.cards.m.MysticDenial.class));
        cards.add(new SetCardInfo("Natural Order", 101, Rarity.RARE, mage.cards.n.NaturalOrder.class));
        cards.add(new SetCardInfo("Natural Spring", 102, Rarity.UNCOMMON, mage.cards.n.NaturalSpring.class));
        cards.add(new SetCardInfo("Nature's Cloak", 103, Rarity.RARE, mage.cards.n.NaturesCloak.class));
        cards.add(new SetCardInfo("Nature's Lore", 104, Rarity.COMMON, mage.cards.n.NaturesLore.class));
        cards.add(new SetCardInfo("Nature's Ruin", 26, Rarity.UNCOMMON, mage.cards.n.NaturesRuin.class));
        cards.add(new SetCardInfo("Needle Storm", 105, Rarity.UNCOMMON, mage.cards.n.NeedleStorm.class));
        cards.add(new SetCardInfo("Noxious Toad", 27, Rarity.UNCOMMON, mage.cards.n.NoxiousToad.class));
        cards.add(new SetCardInfo("Omen", 63, Rarity.COMMON, mage.cards.o.Omen.class));
        cards.add(new SetCardInfo("Owl Familiar", 64, Rarity.COMMON, mage.cards.o.OwlFamiliar.class));
        cards.add(new SetCardInfo("Panther Warriors", 106, Rarity.COMMON, mage.cards.p.PantherWarriors.class));
        cards.add(new SetCardInfo("Personal Tutor", 65, Rarity.UNCOMMON, mage.cards.p.PersonalTutor.class));
        cards.add(new SetCardInfo("Phantom Warrior", 66, Rarity.RARE, mage.cards.p.PhantomWarrior.class));
        cards.add(new SetCardInfo("Pillaging Horde", 147, Rarity.RARE, mage.cards.p.PillagingHorde.class));
        cards.add(new SetCardInfo("Plains", 215, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 216, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 217, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 218, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plant Elemental", 107, Rarity.UNCOMMON, mage.cards.p.PlantElemental.class));
        cards.add(new SetCardInfo("Primeval Force", 108, Rarity.RARE, mage.cards.p.PrimevalForce.class));
        cards.add(new SetCardInfo("Prosperity", 67, Rarity.RARE, mage.cards.p.Prosperity.class));
        cards.add(new SetCardInfo("Pyroclasm", 148, Rarity.RARE, mage.cards.p.Pyroclasm.class));
        cards.add(new SetCardInfo("Python", 28, Rarity.COMMON, mage.cards.p.Python.class));
        cards.add(new SetCardInfo("Raging Cougar", 149, Rarity.COMMON, mage.cards.r.RagingCougar.class));
        cards.add(new SetCardInfo("Raging Goblin", 150, Rarity.COMMON, mage.cards.r.RagingGoblin.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Raging Goblin", 151, Rarity.COMMON, mage.cards.r.RagingGoblin.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Raging Minotaur", 152, Rarity.COMMON, mage.cards.r.RagingMinotaur.class));
        cards.add(new SetCardInfo("Rain of Salt", 153, Rarity.UNCOMMON, mage.cards.r.RainOfSalt.class));
        cards.add(new SetCardInfo("Rain of Tears", 29, Rarity.UNCOMMON, mage.cards.r.RainOfTears.class));
        cards.add(new SetCardInfo("Raise Dead", 30, Rarity.COMMON, mage.cards.r.RaiseDead.class));
        cards.add(new SetCardInfo("Redwood Treefolk", 109, Rarity.COMMON, mage.cards.r.RedwoodTreefolk.class));
        cards.add(new SetCardInfo("Regal Unicorn", 184, Rarity.COMMON, mage.cards.r.RegalUnicorn.class));
        cards.add(new SetCardInfo("Renewing Dawn", 185, Rarity.UNCOMMON, mage.cards.r.RenewingDawn.class));
        cards.add(new SetCardInfo("Rowan Treefolk", 110, Rarity.COMMON, mage.cards.r.RowanTreefolk.class));
        cards.add(new SetCardInfo("Sacred Knight", 186, Rarity.COMMON, mage.cards.s.SacredKnight.class));
        cards.add(new SetCardInfo("Sacred Nectar", 187, Rarity.COMMON, mage.cards.s.SacredNectar.class));
        cards.add(new SetCardInfo("Scorching Spear", 154, Rarity.COMMON, mage.cards.s.ScorchingSpear.class));
        cards.add(new SetCardInfo("Seasoned Marshal", 188, Rarity.UNCOMMON, mage.cards.s.SeasonedMarshal.class));
        cards.add(new SetCardInfo("Serpent Assassin", 31, Rarity.RARE, mage.cards.s.SerpentAssassin.class));
        cards.add(new SetCardInfo("Serpent Warrior", 32, Rarity.COMMON, mage.cards.s.SerpentWarrior.class));
        cards.add(new SetCardInfo("Skeletal Crocodile", 33, Rarity.COMMON, mage.cards.s.SkeletalCrocodile.class));
        cards.add(new SetCardInfo("Skeletal Snake", 34, Rarity.COMMON, mage.cards.s.SkeletalSnake.class));
        cards.add(new SetCardInfo("Snapping Drake", 68, Rarity.COMMON, mage.cards.s.SnappingDrake.class));
        cards.add(new SetCardInfo("Sorcerous Sight", 69, Rarity.COMMON, mage.cards.s.SorcerousSight.class));
        cards.add(new SetCardInfo("Soul Shred", 35, Rarity.COMMON, mage.cards.s.SoulShred.class));
        cards.add(new SetCardInfo("Spined Wurm", 111, Rarity.COMMON, mage.cards.s.SpinedWurm.class));
        cards.add(new SetCardInfo("Spiritual Guardian", 189, Rarity.RARE, mage.cards.s.SpiritualGuardian.class));
        cards.add(new SetCardInfo("Spitting Earth", 156, Rarity.COMMON, mage.cards.s.SpittingEarth.class));
        cards.add(new SetCardInfo("Spotted Griffin", 190, Rarity.COMMON, mage.cards.s.SpottedGriffin.class));
        cards.add(new SetCardInfo("Stalking Tiger", 112, Rarity.COMMON, mage.cards.s.StalkingTiger.class));
        cards.add(new SetCardInfo("Starlight", 191, Rarity.UNCOMMON, mage.cards.s.Starlight.class));
        cards.add(new SetCardInfo("Starlit Angel", 192, Rarity.UNCOMMON, mage.cards.s.StarlitAngel.class));
        cards.add(new SetCardInfo("Steadfastness", 193, Rarity.COMMON, mage.cards.s.Steadfastness.class));
        cards.add(new SetCardInfo("Stern Marshal", 194, Rarity.RARE, mage.cards.s.SternMarshal.class));
        cards.add(new SetCardInfo("Stone Rain", 157, Rarity.COMMON, mage.cards.s.StoneRain.class));
        cards.add(new SetCardInfo("Storm Crow", 70, Rarity.COMMON, mage.cards.s.StormCrow.class));
        cards.add(new SetCardInfo("Summer Bloom", 113, Rarity.RARE, mage.cards.s.SummerBloom.class));
        cards.add(new SetCardInfo("Swamp", 219, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 220, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 221, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 222, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Sylvan Tutor", 114, Rarity.RARE, mage.cards.s.SylvanTutor.class));
        cards.add(new SetCardInfo("Symbol of Unsummoning", 71, Rarity.COMMON, mage.cards.s.SymbolOfUnsummoning.class));
        cards.add(new SetCardInfo("Taunt", 72, Rarity.RARE, mage.cards.t.Taunt.class));
        cards.add(new SetCardInfo("Theft of Dreams", 73, Rarity.UNCOMMON, mage.cards.t.TheftOfDreams.class));
        cards.add(new SetCardInfo("Thing from the Deep", 74, Rarity.RARE, mage.cards.t.ThingFromTheDeep.class));
        cards.add(new SetCardInfo("Thundering Wurm", 115, Rarity.RARE, mage.cards.t.ThunderingWurm.class));
        cards.add(new SetCardInfo("Thundermare", 158, Rarity.RARE, mage.cards.t.Thundermare.class));
        cards.add(new SetCardInfo("Tidal Surge", 75, Rarity.COMMON, mage.cards.t.TidalSurge.class));
        cards.add(new SetCardInfo("Time Ebb", 76, Rarity.COMMON, mage.cards.t.TimeEbb.class));
        cards.add(new SetCardInfo("Touch of Brilliance", 77, Rarity.COMMON, mage.cards.t.TouchOfBrilliance.class));
        cards.add(new SetCardInfo("Undying Beast", 36, Rarity.COMMON, mage.cards.u.UndyingBeast.class));
        cards.add(new SetCardInfo("Untamed Wilds", 117, Rarity.UNCOMMON, mage.cards.u.UntamedWilds.class));
        cards.add(new SetCardInfo("Valorous Charge", 196, Rarity.UNCOMMON, mage.cards.v.ValorousCharge.class));
        cards.add(new SetCardInfo("Vampiric Feast", 37, Rarity.UNCOMMON, mage.cards.v.VampiricFeast.class));
        cards.add(new SetCardInfo("Vampiric Touch", 38, Rarity.COMMON, mage.cards.v.VampiricTouch.class));
        cards.add(new SetCardInfo("Venerable Monk", 197, Rarity.UNCOMMON, mage.cards.v.VenerableMonk.class));
        cards.add(new SetCardInfo("Vengeance", 198, Rarity.UNCOMMON, mage.cards.v.Vengeance.class));
        cards.add(new SetCardInfo("Virtue's Ruin", 39, Rarity.UNCOMMON, mage.cards.v.VirtuesRuin.class));
        cards.add(new SetCardInfo("Volcanic Dragon", 159, Rarity.RARE, mage.cards.v.VolcanicDragon.class));
        cards.add(new SetCardInfo("Volcanic Hammer", 160, Rarity.COMMON, mage.cards.v.VolcanicHammer.class));
        cards.add(new SetCardInfo("Wall of Granite", 161, Rarity.UNCOMMON, mage.cards.w.WallOfGranite.class));
        cards.add(new SetCardInfo("Wall of Swords", 199, Rarity.UNCOMMON, mage.cards.w.WallOfSwords.class));
        cards.add(new SetCardInfo("Warrior's Charge", 200, Rarity.COMMON, mage.cards.w.WarriorsCharge1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Warrior's Charge", 201, Rarity.COMMON, mage.cards.w.WarriorsCharge1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Whiptail Wurm", 118, Rarity.UNCOMMON, mage.cards.w.WhiptailWurm.class));
        cards.add(new SetCardInfo("Wicked Pact", 40, Rarity.RARE, mage.cards.w.WickedPact.class));
        cards.add(new SetCardInfo("Willow Dryad", 119, Rarity.COMMON, mage.cards.w.WillowDryad.class));
        cards.add(new SetCardInfo("Wind Drake", 78, Rarity.COMMON, mage.cards.w.WindDrake.class));
        cards.add(new SetCardInfo("Winds of Change", 162, Rarity.RARE, mage.cards.w.WindsOfChange.class));
        cards.add(new SetCardInfo("Winter's Grasp", 120, Rarity.UNCOMMON, mage.cards.w.WintersGrasp.class));
        cards.add(new SetCardInfo("Withering Gaze", 79, Rarity.UNCOMMON, mage.cards.w.WitheringGaze.class));
        cards.add(new SetCardInfo("Wood Elves", 121, Rarity.RARE, mage.cards.w.WoodElves.class));
        cards.add(new SetCardInfo("Wrath of God", 202, Rarity.RARE, mage.cards.w.WrathOfGod.class));
    }
}