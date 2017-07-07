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

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.h.HighTide;
import mage.constants.Rarity;
import mage.constants.SetType;

public class MediaInserts extends ExpansionSet {

    private static final MediaInserts instance = new MediaInserts();

    public static MediaInserts getInstance() {
        return instance;
    }

    private MediaInserts() {
        super("Media Inserts", "MBP", ExpansionSet.buildDate(1990, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Acquire", 83, Rarity.RARE, mage.cards.a.Acquire.class));
        cards.add(new SetCardInfo("Aeronaut Tinkerer", 118, Rarity.COMMON, mage.cards.a.AeronautTinkerer.class));
        cards.add(new SetCardInfo("Ajani, Caller of the Pride", 72, Rarity.MYTHIC, mage.cards.a.AjaniCallerOfThePride.class));
        cards.add(new SetCardInfo("Ajani Steadfast", 99, Rarity.MYTHIC, mage.cards.a.AjaniSteadfast.class));
        cards.add(new SetCardInfo("Alhammarret, High Arbiter", 124, Rarity.SPECIAL, mage.cards.a.AlhammarretHighArbiter.class));
        cards.add(new SetCardInfo("Angelic Skirmisher", 90, Rarity.RARE, mage.cards.a.AngelicSkirmisher.class));
        cards.add(new SetCardInfo("Angel of Glory's Rise", 59, Rarity.RARE, mage.cards.a.AngelOfGlorysRise.class));
        cards.add(new SetCardInfo("Ankle Shanker", 93, Rarity.RARE, mage.cards.a.AnkleShanker.class));
        cards.add(new SetCardInfo("Arashin Sovereign", 112, Rarity.SPECIAL, mage.cards.a.ArashinSovereign.class));
        cards.add(new SetCardInfo("Archfiend of Depravity", 109, Rarity.SPECIAL, mage.cards.a.ArchfiendOfDepravity.class));
        cards.add(new SetCardInfo("Archfiend of Ifnir", 165, Rarity.RARE, mage.cards.a.ArchfiendOfIfnir.class));
        cards.add(new SetCardInfo("Arena", 1, Rarity.SPECIAL, mage.cards.a.Arena.class));
        cards.add(new SetCardInfo("Arrest", 53, Rarity.COMMON, mage.cards.a.Arrest.class));
        cards.add(new SetCardInfo("Assembled Alphas", 160, Rarity.RARE, mage.cards.a.AssembledAlphas.class));
        cards.add(new SetCardInfo("Avalanche Tusker", 94, Rarity.RARE, mage.cards.a.AvalancheTusker.class));
        cards.add(new SetCardInfo("Barrage Tyrant", 139, Rarity.SPECIAL, mage.cards.b.BarrageTyrant.class));
        cards.add(new SetCardInfo("Birds of Paradise", 28, Rarity.RARE, mage.cards.b.BirdsOfParadise.class));
        cards.add(new SetCardInfo("Bloodthrone Vampire", 31, Rarity.COMMON, mage.cards.b.BloodthroneVampire.class));
        cards.add(new SetCardInfo("Blue Elemental Blast", 5, Rarity.COMMON, mage.cards.b.BlueElementalBlast.class));
        cards.add(new SetCardInfo("Boltwing Marauder", 115, Rarity.SPECIAL, mage.cards.b.BoltwingMarauder.class));
        cards.add(new SetCardInfo("Bonescythe Sliver", 68, Rarity.RARE, mage.cards.b.BonescytheSliver.class));
        cards.add(new SetCardInfo("Breath of Malfegor", 58, Rarity.COMMON, mage.cards.b.BreathOfMalfegor.class));
        cards.add(new SetCardInfo("Brion Stoutarm", 17, Rarity.RARE, mage.cards.b.BrionStoutarm.class));
        cards.add(new SetCardInfo("Broodmate Dragon", 19, Rarity.RARE, mage.cards.b.BroodmateDragon.class));
        cards.add(new SetCardInfo("Canopy Vista", 167, Rarity.RARE, mage.cards.c.CanopyVista.class));
        cards.add(new SetCardInfo("Cathedral of War", 51, Rarity.RARE, mage.cards.c.CathedralOfWar.class));
        cards.add(new SetCardInfo("Celestial Colonnade", 23, Rarity.SPECIAL, mage.cards.c.CelestialColonnade.class));
        cards.add(new SetCardInfo("Chandra, Fire of Kaladesh", 997, Rarity.SPECIAL, mage.cards.c.ChandraFireOfKaladesh.class));
        cards.add(new SetCardInfo("Chandra, Flamecaller", 175, Rarity.MYTHIC, mage.cards.c.ChandraFlamecaller.class));
        cards.add(new SetCardInfo("Chandra, Pyromaster", 75, Rarity.MYTHIC, mage.cards.c.ChandraPyromaster.class));
        cards.add(new SetCardInfo("Chandra, Pyromaster", 102, Rarity.MYTHIC, mage.cards.c.ChandraPyromaster.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Chandra, Roaring Flame", 997, Rarity.SPECIAL, mage.cards.c.ChandraRoaringFlame.class));
        cards.add(new SetCardInfo("Chandra's Fury", 65, Rarity.COMMON, mage.cards.c.ChandrasFury.class));
        cards.add(new SetCardInfo("Chandra's Phoenix", 37, Rarity.RARE, mage.cards.c.ChandrasPhoenix.class));
        cards.add(new SetCardInfo("Cinder Glade", 168, Rarity.RARE, mage.cards.c.CinderGlade.class));
        cards.add(new SetCardInfo("Consume Spirit", 54, Rarity.UNCOMMON, mage.cards.c.ConsumeSpirit.class));
        cards.add(new SetCardInfo("Corrupt", 64, Rarity.UNCOMMON, mage.cards.c.Corrupt.class));
        cards.add(new SetCardInfo("Day of Judgment", 22, Rarity.RARE, mage.cards.d.DayOfJudgment.class));
        cards.add(new SetCardInfo("Deepfathom Skulker", 144, Rarity.RARE, mage.cards.d.DeepfathomSkulker.class));
        cards.add(new SetCardInfo("Defiant Bloodlord", 138, Rarity.SPECIAL, mage.cards.d.DefiantBloodlord.class));
        cards.add(new SetCardInfo("Devil's Play", 40, Rarity.RARE, mage.cards.d.DevilsPlay.class));
        cards.add(new SetCardInfo("Dragon Fodder", 119, Rarity.COMMON, mage.cards.d.DragonFodder.class));
        cards.add(new SetCardInfo("Dragonlord's Servant", 120, Rarity.SPECIAL, mage.cards.d.DragonlordsServant.class));
        cards.add(new SetCardInfo("Dragonscale General", 107, Rarity.SPECIAL, mage.cards.d.DragonscaleGeneral.class));
        cards.add(new SetCardInfo("Dread Defiler", 145, Rarity.RARE, mage.cards.d.DreadDefiler.class));
        cards.add(new SetCardInfo("Dreg Mangler", 55, Rarity.UNCOMMON, mage.cards.d.DregMangler.class));
        cards.add(new SetCardInfo("Drogskol Cavalry", 149, Rarity.RARE, mage.cards.d.DrogskolCavalry.class));
        cards.add(new SetCardInfo("Dromoka, the Eternal", 132, Rarity.SPECIAL, mage.cards.d.DromokaTheEternal.class));
        cards.add(new SetCardInfo("Drowner of Hope", 137, Rarity.SPECIAL, mage.cards.d.DrownerOfHope.class));
        cards.add(new SetCardInfo("Duress", 84, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Dwynen, Gilt-Leaf Daen", 125, Rarity.SPECIAL, mage.cards.d.DwynenGiltLeafDaen.class));
        cards.add(new SetCardInfo("Eidolon of Blossoms", 85, Rarity.RARE, mage.cards.e.EidolonOfBlossoms.class));
        cards.add(new SetCardInfo("Electrolyze", 42, Rarity.UNCOMMON, mage.cards.e.Electrolyze.class));
        cards.add(new SetCardInfo("Elusive Tormentor", 154, Rarity.RARE, mage.cards.e.ElusiveTormentor.class));
        cards.add(new SetCardInfo("Emrakul, the Aeons Torn", 163, Rarity.MYTHIC, mage.cards.e.EmrakulTheAeonsTorn.class));
        cards.add(new SetCardInfo("Evolving Wilds", 121, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Faithless Looting", 39, Rarity.COMMON, mage.cards.f.FaithlessLooting.class));
        cards.add(new SetCardInfo("Fated Conflagration", 79, Rarity.RARE, mage.cards.f.FatedConflagration.class));
        cards.add(new SetCardInfo("Feast of Blood", 43, Rarity.UNCOMMON, mage.cards.f.FeastOfBlood.class));
        cards.add(new SetCardInfo("Fireball", 4, Rarity.COMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Flameblade Angel", 152, Rarity.RARE, mage.cards.f.FlamebladeAngel.class));
        cards.add(new SetCardInfo("Flamerush Rider", 110, Rarity.SPECIAL, mage.cards.f.FlamerushRider.class));
        cards.add(new SetCardInfo("Foe-Razer Regent", 122, Rarity.SPECIAL, mage.cards.f.FoeRazerRegent.class));
        cards.add(new SetCardInfo("Frost Titan", 34, Rarity.MYTHIC, mage.cards.f.FrostTitan.class));
        cards.add(new SetCardInfo("Garruk, Apex Predator", 104, Rarity.MYTHIC, mage.cards.g.GarrukApexPredator.class));
        cards.add(new SetCardInfo("Garruk, Caller of Beasts", 76, Rarity.MYTHIC, mage.cards.g.GarrukCallerOfBeasts.class));
        cards.add(new SetCardInfo("Garruk Wildspeaker", 16, Rarity.RARE, mage.cards.g.GarrukWildspeaker.class));
        cards.add(new SetCardInfo("Gaze of Granite", 81, Rarity.RARE, mage.cards.g.GazeOfGranite.class));
        cards.add(new SetCardInfo("Genesis Hydra", 142, Rarity.SPECIAL, mage.cards.g.GenesisHydra.class));
        cards.add(new SetCardInfo("Giant Badger", 8, Rarity.SPECIAL, mage.cards.g.GiantBadger.class));
        cards.add(new SetCardInfo("Gideon, Ally of Zendikar", 172, Rarity.MYTHIC, mage.cards.g.GideonAllyOfZendikar.class));
        cards.add(new SetCardInfo("Gideon, Battle-Forged", 994, Rarity.SPECIAL, mage.cards.g.GideonBattleForged.class));
        cards.add(new SetCardInfo("Gladehart Cavalry", 147, Rarity.RARE, mage.cards.g.GladehartCavalry.class));
        cards.add(new SetCardInfo("Goblin Dark-Dwellers", 148, Rarity.RARE, mage.cards.g.GoblinDarkDwellers.class));
        cards.add(new SetCardInfo("Goblin Rabblemaster", 98, Rarity.RARE, mage.cards.g.GoblinRabblemaster.class));
        cards.add(new SetCardInfo("Gravecrawler", 41, Rarity.RARE, mage.cards.g.Gravecrawler.class));
        cards.add(new SetCardInfo("Grave Titan", 35, Rarity.MYTHIC, mage.cards.g.GraveTitan.class));
        cards.add(new SetCardInfo("Guul Draz Assassin", 26, Rarity.RARE, mage.cards.g.GuulDrazAssassin.class));
        cards.add(new SetCardInfo("Hamletback Goliath", 71, Rarity.RARE, mage.cards.h.HamletbackGoliath.class));
        cards.add(new SetCardInfo("Harbinger of the Hunt", 116, Rarity.SPECIAL, mage.cards.h.HarbingerOfTheHunt.class));
        cards.add(new SetCardInfo("Hero of Goma Fada", 136, Rarity.SPECIAL, mage.cards.h.HeroOfGomaFada.class));
        cards.add(new SetCardInfo("High Tide", 80, Rarity.COMMON, HighTide.class));
        cards.add(new SetCardInfo("Hixus, Prison Warden", 126, Rarity.SPECIAL, mage.cards.h.HixusPrisonWarden.class));
        cards.add(new SetCardInfo("Honored Hierarch", 129, Rarity.SPECIAL, mage.cards.h.HonoredHierarch.class));
        cards.add(new SetCardInfo("Honor of the Pure", 20, Rarity.RARE, mage.cards.h.HonorOfThePure.class));
        cards.add(new SetCardInfo("Inferno Titan", 36, Rarity.MYTHIC, mage.cards.i.InfernoTitan.class));
        cards.add(new SetCardInfo("Insidious Mist", 154, Rarity.RARE, mage.cards.i.InsidiousMist.class));
        cards.add(new SetCardInfo("Ivorytusk Fortress", 95, Rarity.RARE, mage.cards.i.IvorytuskFortress.class));
        cards.add(new SetCardInfo("Jace Beleren", 15, Rarity.RARE, mage.cards.j.JaceBeleren.class));
        cards.add(new SetCardInfo("Jace, Memory Adept", 73, Rarity.MYTHIC, mage.cards.j.JaceMemoryAdept.class));
        cards.add(new SetCardInfo("Jace, Telepath Unbound", 995, Rarity.SPECIAL, mage.cards.j.JaceTelepathUnbound.class));
        cards.add(new SetCardInfo("Jace, the Living Guildpact", 100, Rarity.MYTHIC, mage.cards.j.JaceTheLivingGuildpact.class));
        cards.add(new SetCardInfo("Jace, Unraveler of Secrets", 173, Rarity.MYTHIC, mage.cards.j.JaceUnravelerOfSecrets.class));
        cards.add(new SetCardInfo("Jace, Vryn's Prodigy", 995, Rarity.SPECIAL, mage.cards.j.JaceVrynsProdigy.class));
        cards.add(new SetCardInfo("Jaya Ballard, Task Mage", 18, Rarity.RARE, mage.cards.j.JayaBallardTaskMage.class));
        cards.add(new SetCardInfo("Karametra's Acolyte", 78, Rarity.UNCOMMON, mage.cards.k.KarametrasAcolyte.class));
        cards.add(new SetCardInfo("Knight Exemplar", 46, Rarity.RARE, mage.cards.k.KnightExemplar.class));
        cards.add(new SetCardInfo("Kor Skyfisher", 25, Rarity.COMMON, mage.cards.k.KorSkyfisher.class));
        cards.add(new SetCardInfo("Kothophed, Soul Hoarder", 127, Rarity.SPECIAL, mage.cards.k.KothophedSoulHoarder.class));
        cards.add(new SetCardInfo("Kytheon, Hero of Akros", 994, Rarity.SPECIAL, mage.cards.k.KytheonHeroOfAkros.class));
        cards.add(new SetCardInfo("Lightning Hounds", 10, Rarity.SPECIAL, mage.cards.l.LightningHounds.class));
        cards.add(new SetCardInfo("Liliana, Defiant Necromancer", 996, Rarity.SPECIAL, mage.cards.l.LilianaDefiantNecromancer.class));
        cards.add(new SetCardInfo("Liliana, Heretical Healer", 996, Rarity.SPECIAL, mage.cards.l.LilianaHereticalHealer.class));
        cards.add(new SetCardInfo("Liliana of the Dark Realms", 74, Rarity.MYTHIC, mage.cards.l.LilianaOfTheDarkRealms.class));
        cards.add(new SetCardInfo("Liliana, the Last Hope", 174, Rarity.MYTHIC, mage.cards.l.LilianaTheLastHope.class));
        cards.add(new SetCardInfo("Liliana Vess", 30, Rarity.RARE, mage.cards.l.LilianaVess.class));
        cards.add(new SetCardInfo("Liliana Vess", 101, Rarity.MYTHIC, mage.cards.l.LilianaVess.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Magister of Worth", 86, Rarity.SPECIAL, mage.cards.m.MagisterOfWorth.class));
        cards.add(new SetCardInfo("Mana Crypt", 6, Rarity.RARE, mage.cards.m.ManaCrypt.class));
        cards.add(new SetCardInfo("Markov Dreadknight", 151, Rarity.RARE, mage.cards.m.MarkovDreadknight.class));
        cards.add(new SetCardInfo("Memoricide", 29, Rarity.RARE, mage.cards.m.Memoricide.class));
        cards.add(new SetCardInfo("Merfolk Mesmerist", 45, Rarity.COMMON, mage.cards.m.MerfolkMesmerist.class));
        cards.add(new SetCardInfo("Mirran Crusader", 32, Rarity.RARE, mage.cards.m.MirranCrusader.class));
        cards.add(new SetCardInfo("Munda's Vanguard", 143, Rarity.RARE, mage.cards.m.MundasVanguard.class));
        cards.add(new SetCardInfo("Necromaster Dragon", 114, Rarity.SPECIAL, mage.cards.n.NecromasterDragon.class));
        cards.add(new SetCardInfo("Nephalia Moondrakes", 150, Rarity.RARE, mage.cards.n.NephaliaMoondrakes.class));
        cards.add(new SetCardInfo("Niblis of Frost", 158, Rarity.RARE, mage.cards.n.NiblisOfFrost.class));
        cards.add(new SetCardInfo("Nightveil Specter", 61, Rarity.RARE, mage.cards.n.NightveilSpecter.class));
        cards.add(new SetCardInfo("Nissa Revane", 27, Rarity.MYTHIC, mage.cards.n.NissaRevane.class));
        cards.add(new SetCardInfo("Nissa, Sage Animist", 998, Rarity.SPECIAL, mage.cards.n.NissaSageAnimist.class));
        cards.add(new SetCardInfo("Nissa, Vastwood Seer", 998, Rarity.SPECIAL, mage.cards.n.NissaVastwoodSeer.class));
        cards.add(new SetCardInfo("Nissa, Voice of Zendikar", 176, Rarity.MYTHIC, mage.cards.n.NissaVoiceOfZendikar.class));
        cards.add(new SetCardInfo("Nissa, Worldwaker", 103, Rarity.MYTHIC, mage.cards.n.NissaWorldwaker.class));
        cards.add(new SetCardInfo("Noosegraf Mob", 159, Rarity.RARE, mage.cards.n.NoosegrafMob.class));
        cards.add(new SetCardInfo("Ogre Arsonist", 63, Rarity.SPECIAL, mage.cards.o.OgreArsonist.class));
        cards.add(new SetCardInfo("Ogre Battledriver", 69, Rarity.RARE, mage.cards.o.OgreBattledriver.class));
        cards.add(new SetCardInfo("Ojutai's Command", 106, Rarity.SPECIAL, mage.cards.o.OjutaisCommand.class));
        cards.add(new SetCardInfo("Oran-Rief Hydra", 140, Rarity.SPECIAL, mage.cards.o.OranRiefHydra.class));
        cards.add(new SetCardInfo("Phyrexian Rager", 14, Rarity.COMMON, mage.cards.p.PhyrexianRager.class));
        cards.add(new SetCardInfo("Pia and Kiran Nalaar", 128, Rarity.SPECIAL, mage.cards.p.PiaAndKiranNalaar.class));
        cards.add(new SetCardInfo("Prairie Stream", 169, Rarity.RARE, mage.cards.p.PrairieStream.class));
        cards.add(new SetCardInfo("Primordial Hydra", 49, Rarity.MYTHIC, mage.cards.p.PrimordialHydra.class));
        cards.add(new SetCardInfo("Pristine Skywise", 113, Rarity.SPECIAL, mage.cards.p.PristineSkywise.class));
        cards.add(new SetCardInfo("Rakshasa Vizier", 96, Rarity.RARE, mage.cards.r.RakshasaVizier.class));
        cards.add(new SetCardInfo("Ratchet Bomb", 67, Rarity.RARE, mage.cards.r.RatchetBomb.class));
        cards.add(new SetCardInfo("Rattleclaw Mystic", 92, Rarity.RARE, mage.cards.r.RattleclawMystic.class));
        cards.add(new SetCardInfo("Ravenous Bloodseeker", 155, Rarity.UNCOMMON, mage.cards.r.RavenousBloodseeker.class));
        cards.add(new SetCardInfo("Relic Seeker", 123, Rarity.RARE, mage.cards.r.RelicSeeker.class));
        cards.add(new SetCardInfo("Render Silent", 66, Rarity.RARE, mage.cards.r.RenderSilent.class));
        cards.add(new SetCardInfo("Retaliator Griffin", 24, Rarity.RARE, mage.cards.r.RetaliatorGriffin.class));
        cards.add(new SetCardInfo("Ruinous Path", 135, Rarity.SPECIAL, mage.cards.r.RuinousPath.class));
        cards.add(new SetCardInfo("Sage-Eye Avengers", 108, Rarity.SPECIAL, mage.cards.s.SageEyeAvengers.class));
        cards.add(new SetCardInfo("Sage of the Inward Eye", 97, Rarity.RARE, mage.cards.s.SageOfTheInwardEye.class));
        cards.add(new SetCardInfo("Sanctifier of Souls", 157, Rarity.RARE, mage.cards.s.SanctifierOfSouls.class));
        cards.add(new SetCardInfo("Sandsteppe Citadel", 134, Rarity.SPECIAL, mage.cards.s.SandsteppeCitadel.class));
        cards.add(new SetCardInfo("Scavenging Ooze", 70, Rarity.RARE, mage.cards.s.ScavengingOoze.class));
        cards.add(new SetCardInfo("Scrap Trawler", 164, Rarity.RARE, mage.cards.s.ScrapTrawler.class));
        cards.add(new SetCardInfo("Scythe Leopard", 141, Rarity.SPECIAL, mage.cards.s.ScytheLeopard.class));
        cards.add(new SetCardInfo("Seeker of the Way", 130, Rarity.SPECIAL, mage.cards.s.SeekerOfTheWay.class));
        cards.add(new SetCardInfo("Serra Avatar", 48, Rarity.MYTHIC, mage.cards.s.SerraAvatar.class));
        cards.add(new SetCardInfo("Shamanic Revelation", 105, Rarity.SPECIAL, mage.cards.s.ShamanicRevelation.class));
        cards.add(new SetCardInfo("Siege Rhino", 133, Rarity.SPECIAL, mage.cards.s.SiegeRhino.class));
        cards.add(new SetCardInfo("Silverblade Paladin", 44, Rarity.RARE, mage.cards.s.SilverbladePaladin.class));
        cards.add(new SetCardInfo("Silver Drake", 13, Rarity.SPECIAL, mage.cards.s.SilverDrake.class));
        cards.add(new SetCardInfo("Skyship Stalker", 162, Rarity.RARE, mage.cards.s.SkyshipStalker.class));
        cards.add(new SetCardInfo("Smoldering Marsh", 170, Rarity.RARE, mage.cards.s.SmolderingMarsh.class));
        cards.add(new SetCardInfo("Soul of Ravnica", 87, Rarity.MYTHIC, mage.cards.s.SoulOfRavnica.class));
        cards.add(new SetCardInfo("Soul of Zendikar", 88, Rarity.MYTHIC, mage.cards.s.SoulOfZendikar.class));
        cards.add(new SetCardInfo("Soul Swallower", 153, Rarity.RARE, mage.cards.s.SoulSwallower.class));
        cards.add(new SetCardInfo("Spined Wurm", 11, Rarity.SPECIAL, mage.cards.s.SpinedWurm.class));
        cards.add(new SetCardInfo("Standstill", 57, Rarity.UNCOMMON, mage.cards.s.Standstill.class));
        cards.add(new SetCardInfo("Stealer of Secrets", 89, Rarity.COMMON, mage.cards.s.StealerOfSecrets.class));
        cards.add(new SetCardInfo("Steward of Valeron", 21, Rarity.COMMON, mage.cards.s.StewardOfValeron.class));
        cards.add(new SetCardInfo("Sultai Charm", 117, Rarity.SPECIAL, mage.cards.s.SultaiCharm.class));
        cards.add(new SetCardInfo("Sunblast Angel", 47, Rarity.RARE, mage.cards.s.SunblastAngel.class));
        cards.add(new SetCardInfo("Sunken Hollow", 171, Rarity.RARE, mage.cards.s.SunkenHollow.class));
        cards.add(new SetCardInfo("Supreme Verdict", 56, Rarity.RARE, mage.cards.s.SupremeVerdict.class));
        cards.add(new SetCardInfo("Surgical Extraction", 33, Rarity.RARE, mage.cards.s.SurgicalExtraction.class));
        cards.add(new SetCardInfo("Sylvan Caryatid", 77, Rarity.RARE, mage.cards.s.SylvanCaryatid.class));
        cards.add(new SetCardInfo("Temur War Shaman", 111, Rarity.SPECIAL, mage.cards.t.TemurWarShaman.class));
        cards.add(new SetCardInfo("Terastodon", 52, Rarity.RARE, mage.cards.t.Terastodon.class));
        cards.add(new SetCardInfo("Thalia, Heretic Cathar", 156, Rarity.RARE, mage.cards.t.ThaliaHereticCathar.class));
        cards.add(new SetCardInfo("Treasure Hunt", 38, Rarity.COMMON, mage.cards.t.TreasureHunt.class));
        cards.add(new SetCardInfo("Turnabout", 60, Rarity.UNCOMMON, mage.cards.t.Turnabout.class));
        cards.add(new SetCardInfo("Tyrant of Valakut", 146, Rarity.RARE, mage.cards.t.TyrantOfValakut.class));
        cards.add(new SetCardInfo("Ulvenwald Observer", 161, Rarity.RARE, mage.cards.u.UlvenwaldObserver.class));
        cards.add(new SetCardInfo("Valorous Stance", 131, Rarity.SPECIAL, mage.cards.v.ValorousStance.class));
        cards.add(new SetCardInfo("Vampire Nocturnus", 50, Rarity.MYTHIC, mage.cards.v.VampireNocturnus.class));
        cards.add(new SetCardInfo("Voidmage Husher", 62, Rarity.SPECIAL, mage.cards.v.VoidmageHusher.class));
        cards.add(new SetCardInfo("Warmonger", 12, Rarity.SPECIAL, mage.cards.w.Warmonger.class));
        cards.add(new SetCardInfo("Wash Out", 82, Rarity.UNCOMMON, mage.cards.w.WashOut.class));
        cards.add(new SetCardInfo("Wildfire Eternal", 166, Rarity.RARE, mage.cards.w.WildfireEternal.class));
        cards.add(new SetCardInfo("Windseeker Centaur", 7, Rarity.SPECIAL, mage.cards.w.WindseekerCentaur.class));
        cards.add(new SetCardInfo("Xathrid Necromancer", 91, Rarity.SPECIAL, mage.cards.x.XathridNecromancer.class));
    }
}
