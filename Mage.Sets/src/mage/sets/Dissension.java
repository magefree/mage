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
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * Created by IntelliJ IDEA. User: Loki Date: 20.12.10 Time: 21:40
 */
public class Dissension extends ExpansionSet {

    private static final Dissension instance = new Dissension();

    public static Dissension getInstance() {
        return instance;
    }

    private Dissension() {
        super("Dissension", "DIS", ExpansionSet.buildDate(2006, 4, 5), SetType.EXPANSION);
        this.blockName = "Ravnica";
        this.parentSet = RavnicaCityOfGuilds.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Aethermage's Touch", 101, Rarity.RARE, mage.cards.a.AethermagesTouch.class));
        cards.add(new SetCardInfo("Anthem of Rakdos", 102, Rarity.RARE, mage.cards.a.AnthemOfRakdos.class));
        cards.add(new SetCardInfo("Aquastrand Spider", 80, Rarity.COMMON, mage.cards.a.AquastrandSpider.class));
        cards.add(new SetCardInfo("Assault Zeppelid", 103, Rarity.COMMON, mage.cards.a.AssaultZeppelid.class));
        cards.add(new SetCardInfo("Aurora Eidolon", 1, Rarity.COMMON, mage.cards.a.AuroraEidolon.class));
        cards.add(new SetCardInfo("Avatar of Discord", 140, Rarity.RARE, mage.cards.a.AvatarOfDiscord.class));
        cards.add(new SetCardInfo("Azorius AEthermage", 104, Rarity.UNCOMMON, mage.cards.a.AzoriusAEthermage.class));
        cards.add(new SetCardInfo("Azorius Chancery", 170, Rarity.COMMON, mage.cards.a.AzoriusChancery.class));
        cards.add(new SetCardInfo("Azorius First-Wing", 105, Rarity.COMMON, mage.cards.a.AzoriusFirstWing.class));
        cards.add(new SetCardInfo("Azorius Guildmage", 141, Rarity.UNCOMMON, mage.cards.a.AzoriusGuildmage.class));
        cards.add(new SetCardInfo("Azorius Herald", 2, Rarity.UNCOMMON, mage.cards.a.AzoriusHerald.class));
        cards.add(new SetCardInfo("Azorius Signet", 159, Rarity.COMMON, mage.cards.a.AzoriusSignet.class));
        cards.add(new SetCardInfo("Beacon Hawk", 3, Rarity.COMMON, mage.cards.b.BeaconHawk.class));
        cards.add(new SetCardInfo("Biomantic Mastery", 142, Rarity.RARE, mage.cards.b.BiomanticMastery.class));
        cards.add(new SetCardInfo("Blessing of the Nephilim", 4, Rarity.UNCOMMON, mage.cards.b.BlessingOfTheNephilim.class));
        cards.add(new SetCardInfo("Blood Crypt", 171, Rarity.RARE, mage.cards.b.BloodCrypt.class));
        cards.add(new SetCardInfo("Bond of Agony", 38, Rarity.UNCOMMON, mage.cards.b.BondOfAgony.class));
        cards.add(new SetCardInfo("Bound // Determined", 149, Rarity.RARE, mage.cards.b.BoundDetermined.class));
        cards.add(new SetCardInfo("Brain Pry", 39, Rarity.UNCOMMON, mage.cards.b.BrainPry.class));
        cards.add(new SetCardInfo("Breeding Pool", 172, Rarity.RARE, mage.cards.b.BreedingPool.class));
        cards.add(new SetCardInfo("Bronze Bombshell", 160, Rarity.RARE, mage.cards.b.BronzeBombshell.class));
        cards.add(new SetCardInfo("Cackling Flames", 59, Rarity.COMMON, mage.cards.c.CacklingFlames.class));
        cards.add(new SetCardInfo("Carom", 6, Rarity.COMMON, mage.cards.c.Carom.class));
        cards.add(new SetCardInfo("Celestial Ancient", 7, Rarity.RARE, mage.cards.c.CelestialAncient.class));
        cards.add(new SetCardInfo("Coiling Oracle", 107, Rarity.COMMON, mage.cards.c.CoilingOracle.class));
        cards.add(new SetCardInfo("Condemn", 8, Rarity.UNCOMMON, mage.cards.c.Condemn.class));
        cards.add(new SetCardInfo("Court Hussar", 22, Rarity.UNCOMMON, mage.cards.c.CourtHussar.class));
        cards.add(new SetCardInfo("Crime // Punishment", 150, Rarity.RARE, mage.cards.c.CrimePunishment.class));
        cards.add(new SetCardInfo("Crypt Champion", 40, Rarity.UNCOMMON, mage.cards.c.CryptChampion.class));
        cards.add(new SetCardInfo("Cytoplast Manipulator", 23, Rarity.RARE, mage.cards.c.CytoplastManipulator.class));
        cards.add(new SetCardInfo("Cytoplast Root-Kin", 81, Rarity.RARE, mage.cards.c.CytoplastRootKin.class));
        cards.add(new SetCardInfo("Cytospawn Shambler", 82, Rarity.COMMON, mage.cards.c.CytospawnShambler.class));
        cards.add(new SetCardInfo("Delirium Skeins", 41, Rarity.COMMON, mage.cards.d.DeliriumSkeins.class));
        cards.add(new SetCardInfo("Demonfire", 60, Rarity.RARE, mage.cards.d.Demonfire.class));
        cards.add(new SetCardInfo("Demon's Jester", 42, Rarity.COMMON, mage.cards.d.DemonsJester.class));
        cards.add(new SetCardInfo("Dovescape", 143, Rarity.RARE, mage.cards.d.Dovescape.class));
        cards.add(new SetCardInfo("Dread Slag", 109, Rarity.RARE, mage.cards.d.DreadSlag.class));
        cards.add(new SetCardInfo("Drekavac", 43, Rarity.UNCOMMON, mage.cards.d.Drekavac.class));
        cards.add(new SetCardInfo("Enemy of the Guildpact", 44, Rarity.COMMON, mage.cards.e.EnemyOfTheGuildpact.class));
        cards.add(new SetCardInfo("Enigma Eidolon", 24, Rarity.COMMON, mage.cards.e.EnigmaEidolon.class));
        cards.add(new SetCardInfo("Entropic Eidolon", 45, Rarity.COMMON, mage.cards.e.EntropicEidolon.class));
        cards.add(new SetCardInfo("Evolution Vat", 161, Rarity.RARE, mage.cards.e.EvolutionVat.class));
        cards.add(new SetCardInfo("Experiment Kraj", 110, Rarity.RARE, mage.cards.e.ExperimentKraj.class));
        cards.add(new SetCardInfo("Flame-Kin War Scout", 61, Rarity.UNCOMMON, mage.cards.f.FlameKinWarScout.class));
        cards.add(new SetCardInfo("Flaring Flame-Kin", 62, Rarity.UNCOMMON, mage.cards.f.FlaringFlameKin.class));
        cards.add(new SetCardInfo("Freewind Equenaut", 9, Rarity.COMMON, mage.cards.f.FreewindEquenaut.class));
        cards.add(new SetCardInfo("Ghost Quarter", 173, Rarity.UNCOMMON, mage.cards.g.GhostQuarter.class));
        cards.add(new SetCardInfo("Gnat Alley Creeper", 63, Rarity.UNCOMMON, mage.cards.g.GnatAlleyCreeper.class));
        cards.add(new SetCardInfo("Gobhobbler Rats", 111, Rarity.COMMON, mage.cards.g.GobhobblerRats.class));
        cards.add(new SetCardInfo("Govern the Guildless", 25, Rarity.RARE, mage.cards.g.GovernTheGuildless.class));
        cards.add(new SetCardInfo("Grand Arbiter Augustin IV", 112, Rarity.RARE, mage.cards.g.GrandArbiterAugustinIV.class));
        cards.add(new SetCardInfo("Guardian of the Guildpact", 10, Rarity.COMMON, mage.cards.g.GuardianOfTheGuildpact.class));
        cards.add(new SetCardInfo("Haazda Exonerator", 11, Rarity.COMMON, mage.cards.h.HaazdaExonerator.class));
        cards.add(new SetCardInfo("Haazda Shield Mate", 12, Rarity.RARE, mage.cards.h.HaazdaShieldMate.class));
        cards.add(new SetCardInfo("Hallowed Fountain", 174, Rarity.RARE, mage.cards.h.HallowedFountain.class));
        cards.add(new SetCardInfo("Helium Squirter", 26, Rarity.COMMON, mage.cards.h.HeliumSquirter.class));
        cards.add(new SetCardInfo("Hellhole Rats", 113, Rarity.UNCOMMON, mage.cards.h.HellholeRats.class));
        cards.add(new SetCardInfo("Hide // Seek", 151, Rarity.UNCOMMON, mage.cards.h.HideSeek.class));
        cards.add(new SetCardInfo("Hit // Run", 152, Rarity.UNCOMMON, mage.cards.h.HitRun.class));
        cards.add(new SetCardInfo("Ignorant Bliss", 64, Rarity.UNCOMMON, mage.cards.i.IgnorantBliss.class));
        cards.add(new SetCardInfo("Indrik Stomphowler", 86, Rarity.UNCOMMON, mage.cards.i.IndrikStomphowler.class));
        cards.add(new SetCardInfo("Infernal Tutor", 46, Rarity.RARE, mage.cards.i.InfernalTutor.class));
        cards.add(new SetCardInfo("Isperia the Inscrutable", 114, Rarity.RARE, mage.cards.i.IsperiaTheInscrutable.class));
        cards.add(new SetCardInfo("Kill-Suit Cultist", 65, Rarity.COMMON, mage.cards.k.KillSuitCultist.class));
        cards.add(new SetCardInfo("Kindle the Carnage", 66, Rarity.UNCOMMON, mage.cards.k.KindleTheCarnage.class));
        cards.add(new SetCardInfo("Leafdrake Roost", 116, Rarity.UNCOMMON, mage.cards.l.LeafdrakeRoost.class));
        cards.add(new SetCardInfo("Loaming Shaman", 87, Rarity.RARE, mage.cards.l.LoamingShaman.class));
        cards.add(new SetCardInfo("Lyzolda, the Blood Witch", 117, Rarity.RARE, mage.cards.l.LyzoldaTheBloodWitch.class));
        cards.add(new SetCardInfo("Macabre Waltz", 47, Rarity.COMMON, mage.cards.m.MacabreWaltz.class));
        cards.add(new SetCardInfo("Magewright's Stone", 162, Rarity.UNCOMMON, mage.cards.m.MagewrightsStone.class));
        cards.add(new SetCardInfo("Might of the Nephilim", 88, Rarity.UNCOMMON, mage.cards.m.MightOfTheNephilim.class));
        cards.add(new SetCardInfo("Minister of Impediments", 144, Rarity.COMMON, mage.cards.m.MinisterOfImpediments.class));
        cards.add(new SetCardInfo("Mistral Charger", 13, Rarity.UNCOMMON, mage.cards.m.MistralCharger.class));
        cards.add(new SetCardInfo("Momir Vig, Simic Visionary", 118, Rarity.RARE, mage.cards.m.MomirVigSimicVisionary.class));
        cards.add(new SetCardInfo("Novijen, Heart of Progress", 175, Rarity.UNCOMMON, mage.cards.n.NovijenHeartOfProgress.class));
        cards.add(new SetCardInfo("Novijen Sages", 27, Rarity.RARE, mage.cards.n.NovijenSages.class));
        cards.add(new SetCardInfo("Ocular Halo", 28, Rarity.COMMON, mage.cards.o.OcularHalo.class));
        cards.add(new SetCardInfo("Odds // Ends", 153, Rarity.RARE, mage.cards.o.OddsEnds.class));
        cards.add(new SetCardInfo("Ogre Gatecrasher", 67, Rarity.COMMON, mage.cards.o.OgreGatecrasher.class));
        cards.add(new SetCardInfo("Omnibian", 119, Rarity.RARE, mage.cards.o.Omnibian.class));
        cards.add(new SetCardInfo("Overrule", 120, Rarity.COMMON, mage.cards.o.Overrule.class));
        cards.add(new SetCardInfo("Pain Magnification", 121, Rarity.UNCOMMON, mage.cards.p.PainMagnification.class));
        cards.add(new SetCardInfo("Paladin of Prahv", 14, Rarity.UNCOMMON, mage.cards.p.PaladinOfPrahv.class));
        cards.add(new SetCardInfo("Palliation Accord", 122, Rarity.UNCOMMON, mage.cards.p.PalliationAccord.class));
        cards.add(new SetCardInfo("Patagia Viper", 89, Rarity.UNCOMMON, mage.cards.p.PatagiaViper.class));
        cards.add(new SetCardInfo("Pillar of the Paruns", 176, Rarity.RARE, mage.cards.p.PillarOfTheParuns.class));
        cards.add(new SetCardInfo("Plaxcaster Frogling", 123, Rarity.UNCOMMON, mage.cards.p.PlaxcasterFrogling.class));
        cards.add(new SetCardInfo("Plaxmanta", 29, Rarity.UNCOMMON, mage.cards.p.Plaxmanta.class));
        cards.add(new SetCardInfo("Plumes of Peace", 124, Rarity.COMMON, mage.cards.p.PlumesOfPeace.class));
        cards.add(new SetCardInfo("Prahv, Spires of Order", 177, Rarity.UNCOMMON, mage.cards.p.PrahvSpiresOfOrder.class));
        cards.add(new SetCardInfo("Pride of the Clouds", 125, Rarity.RARE, mage.cards.p.PrideOfTheClouds.class));
        cards.add(new SetCardInfo("Proclamation of Rebirth", 15, Rarity.RARE, mage.cards.p.ProclamationOfRebirth.class));
        cards.add(new SetCardInfo("Proper Burial", 16, Rarity.RARE, mage.cards.p.ProperBurial.class));
        cards.add(new SetCardInfo("Protean Hulk", 90, Rarity.RARE, mage.cards.p.ProteanHulk.class));
        cards.add(new SetCardInfo("Psychic Possession", 30, Rarity.RARE, mage.cards.p.PsychicPossession.class));
        cards.add(new SetCardInfo("Psychotic Fury", 68, Rarity.COMMON, mage.cards.p.PsychoticFury.class));
        cards.add(new SetCardInfo("Pure // Simple", 154, Rarity.UNCOMMON, mage.cards.p.PureSimple.class));
        cards.add(new SetCardInfo("Ragamuffyn", 51, Rarity.UNCOMMON, mage.cards.r.Ragamuffyn.class));
        cards.add(new SetCardInfo("Rain of Gore", 126, Rarity.RARE, mage.cards.r.RainOfGore.class));
        cards.add(new SetCardInfo("Rakdos Carnarium", 178, Rarity.COMMON, mage.cards.r.RakdosCarnarium.class));
        cards.add(new SetCardInfo("Rakdos Guildmage", 145, Rarity.UNCOMMON, mage.cards.r.RakdosGuildmage.class));
        cards.add(new SetCardInfo("Rakdos Ickspitter", 128, Rarity.COMMON, mage.cards.r.RakdosIckspitter.class));
        cards.add(new SetCardInfo("Rakdos Pit Dragon", 69, Rarity.RARE, mage.cards.r.RakdosPitDragon.class));
        cards.add(new SetCardInfo("Rakdos Signet", 165, Rarity.COMMON, mage.cards.r.RakdosSignet.class));
        cards.add(new SetCardInfo("Rakdos the Defiler", 129, Rarity.RARE, mage.cards.r.RakdosTheDefiler.class));
        cards.add(new SetCardInfo("Ratcatcher", 52, Rarity.RARE, mage.cards.r.Ratcatcher.class));
        cards.add(new SetCardInfo("Research // Development", 155, Rarity.RARE, mage.cards.r.ResearchDevelopment.class));
        cards.add(new SetCardInfo("Riot Spikes", 146, Rarity.COMMON, mage.cards.r.RiotSpikes.class));
        cards.add(new SetCardInfo("Rise // Fall", 156, Rarity.UNCOMMON, mage.cards.r.RiseFall.class));
        cards.add(new SetCardInfo("Rix Maadi, Dungeon Palace", 179, Rarity.UNCOMMON, mage.cards.r.RixMaadiDungeonPalace.class));
        cards.add(new SetCardInfo("Sandstorm Eidolon", 70, Rarity.COMMON, mage.cards.s.SandstormEidolon.class));
        cards.add(new SetCardInfo("Seal of Doom", 53, Rarity.COMMON, mage.cards.s.SealOfDoom.class));
        cards.add(new SetCardInfo("Seal of Fire", 71, Rarity.COMMON, mage.cards.s.SealOfFire.class));
        cards.add(new SetCardInfo("Shielding Plax", 147, Rarity.COMMON, mage.cards.s.ShieldingPlax.class));
        cards.add(new SetCardInfo("Silkwing Scout", 31, Rarity.COMMON, mage.cards.s.SilkwingScout.class));
        cards.add(new SetCardInfo("Simic Basilisk", 91, Rarity.UNCOMMON, mage.cards.s.SimicBasilisk.class));
        cards.add(new SetCardInfo("Simic Growth Chamber", 180, Rarity.COMMON, mage.cards.s.SimicGrowthChamber.class));
        cards.add(new SetCardInfo("Simic Guildmage", 148, Rarity.UNCOMMON, mage.cards.s.SimicGuildmage.class));
        cards.add(new SetCardInfo("Simic Initiate", 92, Rarity.COMMON, mage.cards.s.SimicInitiate.class));
        cards.add(new SetCardInfo("Simic Ragworm", 93, Rarity.COMMON, mage.cards.s.SimicRagworm.class));
        cards.add(new SetCardInfo("Simic Signet", 166, Rarity.COMMON, mage.cards.s.SimicSignet.class));
        cards.add(new SetCardInfo("Simic Sky Swallower", 130, Rarity.RARE, mage.cards.s.SimicSkySwallower.class));
        cards.add(new SetCardInfo("Skullmead Cauldron", 167, Rarity.UNCOMMON, mage.cards.s.SkullmeadCauldron.class));
        cards.add(new SetCardInfo("Sky Hussar", 131, Rarity.UNCOMMON, mage.cards.s.SkyHussar.class));
        cards.add(new SetCardInfo("Skyscribing", 32, Rarity.UNCOMMON, mage.cards.s.Skyscribing.class));
        cards.add(new SetCardInfo("Slithering Shade", 55, Rarity.UNCOMMON, mage.cards.s.SlitheringShade.class));
        cards.add(new SetCardInfo("Soulsworn Jury", 17, Rarity.COMMON, mage.cards.s.SoulswornJury.class));
        cards.add(new SetCardInfo("Spell Snare", 33, Rarity.UNCOMMON, mage.cards.s.SpellSnare.class));
        cards.add(new SetCardInfo("Sporeback Troll", 94, Rarity.COMMON, mage.cards.s.SporebackTroll.class));
        cards.add(new SetCardInfo("Sprouting Phytohydra", 95, Rarity.RARE, mage.cards.s.SproutingPhytohydra.class));
        cards.add(new SetCardInfo("Squealing Devil", 72, Rarity.UNCOMMON, mage.cards.s.SquealingDevil.class));
        cards.add(new SetCardInfo("Stalking Vengeance", 73, Rarity.RARE, mage.cards.s.StalkingVengeance.class));
        cards.add(new SetCardInfo("Steeling Stance", 18, Rarity.COMMON, mage.cards.s.SteelingStance.class));
        cards.add(new SetCardInfo("Stoic Ephemera", 19, Rarity.UNCOMMON, mage.cards.s.StoicEphemera.class));
        cards.add(new SetCardInfo("Stomp and Howl", 96, Rarity.UNCOMMON, mage.cards.s.StompAndHowl.class));
        cards.add(new SetCardInfo("Supply // Demand", 157, Rarity.UNCOMMON, mage.cards.s.SupplyDemand.class));
        cards.add(new SetCardInfo("Taste for Mayhem", 75, Rarity.COMMON, mage.cards.t.TasteForMayhem.class));
        cards.add(new SetCardInfo("Thrive", 98, Rarity.COMMON, mage.cards.t.Thrive.class));
        cards.add(new SetCardInfo("Tidespout Tyrant", 34, Rarity.RARE, mage.cards.t.TidespoutTyrant.class));
        cards.add(new SetCardInfo("Transguild Courier", 168, Rarity.UNCOMMON, mage.cards.t.TransguildCourier.class));
        cards.add(new SetCardInfo("Trial // Error", 158, Rarity.UNCOMMON, mage.cards.t.TrialError.class));
        cards.add(new SetCardInfo("Trygon Predator", 133, Rarity.UNCOMMON, mage.cards.t.TrygonPredator.class));
        cards.add(new SetCardInfo("Twinstrike", 134, Rarity.UNCOMMON, mage.cards.t.Twinstrike.class));
        cards.add(new SetCardInfo("Utopia Sprawl", 99, Rarity.COMMON, mage.cards.u.UtopiaSprawl.class));
        cards.add(new SetCardInfo("Utvara Scalper", 76, Rarity.COMMON, mage.cards.u.UtvaraScalper.class));
        cards.add(new SetCardInfo("Valor Made Real", 20, Rarity.COMMON, mage.cards.v.ValorMadeReal.class));
        cards.add(new SetCardInfo("Verdant Eidolon", 100, Rarity.COMMON, mage.cards.v.VerdantEidolon.class));
        cards.add(new SetCardInfo("Vesper Ghoul", 57, Rarity.COMMON, mage.cards.v.VesperGhoul.class));
        cards.add(new SetCardInfo("Vigean Graftmage", 35, Rarity.UNCOMMON, mage.cards.v.VigeanGraftmage.class));
        cards.add(new SetCardInfo("Vigean Hydropon", 135, Rarity.COMMON, mage.cards.v.VigeanHydropon.class));
        cards.add(new SetCardInfo("Vision Skeins", 36, Rarity.COMMON, mage.cards.v.VisionSkeins.class));
        cards.add(new SetCardInfo("Voidslime", 137, Rarity.RARE, mage.cards.v.Voidslime.class));
        cards.add(new SetCardInfo("Wakestone Gargoyle", 21, Rarity.RARE, mage.cards.w.WakestoneGargoyle.class));
        cards.add(new SetCardInfo("Walking Archive", 169, Rarity.RARE, mage.cards.w.WalkingArchive.class));
        cards.add(new SetCardInfo("War's Toll", 77, Rarity.RARE, mage.cards.w.WarsToll.class));
        cards.add(new SetCardInfo("Whiptail Moloch", 79, Rarity.COMMON, mage.cards.w.WhiptailMoloch.class));
        cards.add(new SetCardInfo("Windreaver", 138, Rarity.RARE, mage.cards.w.Windreaver.class));
        cards.add(new SetCardInfo("Wit's End", 58, Rarity.RARE, mage.cards.w.WitsEnd.class));
        cards.add(new SetCardInfo("Wrecking Ball", 139, Rarity.COMMON, mage.cards.w.WreckingBall.class));
        cards.add(new SetCardInfo("Writ of Passage", 37, Rarity.COMMON, mage.cards.w.WritOfPassage.class));
    }
}
