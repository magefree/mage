/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets;

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */

public class Commander2016 extends ExpansionSet {

    private static final Commander2016 fINSTANCE = new Commander2016();

    public static Commander2016 getInstance() {
        return fINSTANCE;
    }

    private Commander2016() {
        super("Commander 2016 Edition", "C16", ExpansionSet.buildDate(2016, 11, 11), SetType.SUPPLEMENTAL);
        this.blockName = "Command Zone";
        cards.add(new SetCardInfo("Abzan Charm", 177, Rarity.UNCOMMON, mage.cards.a.AbzanCharm.class));
        cards.add(new SetCardInfo("Abzan Falconer", 57, Rarity.UNCOMMON, mage.cards.a.AbzanFalconer.class));
        cards.add(new SetCardInfo("Academy Elite", 81, Rarity.RARE, mage.cards.a.AcademyElite.class));
        cards.add(new SetCardInfo("Aeon Chronicler", 82, Rarity.RARE, mage.cards.a.AeonChronicler.class));
        cards.add(new SetCardInfo("Akiri, Line-Slinger", 26, Rarity.RARE, mage.cards.a.AkiriLineSlinger.class));
        cards.add(new SetCardInfo("Akroan Horse", 241, Rarity.RARE, mage.cards.a.AkroanHorse.class));
        cards.add(new SetCardInfo("Alesha, Who Smiles at Death", 119, Rarity.RARE, mage.cards.a.AleshaWhoSmilesAtDeath.class));
        cards.add(new SetCardInfo("Ancient Excavation", 27, Rarity.UNCOMMON, mage.cards.a.AncientExcavation.class));
        cards.add(new SetCardInfo("Ankle Shanker", 178, Rarity.RARE, mage.cards.a.AnkleShanker.class));
        cards.add(new SetCardInfo("Arcane Denial", 83, Rarity.COMMON, mage.cards.a.ArcaneDenial.class));
        cards.add(new SetCardInfo("Arcane Sanctum", 281, Rarity.UNCOMMON, mage.cards.a.ArcaneSanctum.class));
        cards.add(new SetCardInfo("Armory Automaton", 51, Rarity.RARE, mage.cards.a.ArmoryAutomaton.class));
        cards.add(new SetCardInfo("Army of the Damned", 105, Rarity.MYTHIC, mage.cards.a.ArmyOfTheDamned.class));
        cards.add(new SetCardInfo("Artifact Mutation", 179, Rarity.RARE, mage.cards.a.ArtifactMutation.class));
        cards.add(new SetCardInfo("Ash Barrens", 56, Rarity.COMMON, mage.cards.a.AshBarrens.class));
        cards.add(new SetCardInfo("Assault Suit", 242, Rarity.UNCOMMON, mage.cards.a.AssaultSuit.class));
        cards.add(new SetCardInfo("Astral Cornucopia", 243, Rarity.RARE, mage.cards.a.AstralCornucopia.class));
        cards.add(new SetCardInfo("Atraxa, Praetors' Voice", 28, Rarity.MYTHIC, mage.cards.a.AtraxaPraetorsVoice.class));
        cards.add(new SetCardInfo("Aura Mutation", 180, Rarity.RARE, mage.cards.a.AuraMutation.class));
        cards.add(new SetCardInfo("Azorius Chancery", 282, Rarity.UNCOMMON, mage.cards.a.AzoriusChancery.class));
        cards.add(new SetCardInfo("Baleful Strix", 181, Rarity.UNCOMMON, mage.cards.b.BalefulStrix.class));
        cards.add(new SetCardInfo("Bane of the Living", 106, Rarity.RARE, mage.cards.b.BaneOfTheLiving.class));
        cards.add(new SetCardInfo("Beacon of Unrest", 107, Rarity.RARE, mage.cards.b.BeaconOfUnrest.class));
        cards.add(new SetCardInfo("Beast Within", 141, Rarity.UNCOMMON, mage.cards.b.BeastWithin.class));
        cards.add(new SetCardInfo("Beastmaster Ascension", 142, Rarity.RARE, mage.cards.b.BeastmasterAscension.class));
        cards.add(new SetCardInfo("Bituminous Blast", 182, Rarity.UNCOMMON, mage.cards.b.BituminousBlast.class));
        cards.add(new SetCardInfo("Blasphemous Act", 120, Rarity.RARE, mage.cards.b.BlasphemousAct.class));
        cards.add(new SetCardInfo("Blazing Archon", 58, Rarity.RARE, mage.cards.b.BlazingArchon.class));
        cards.add(new SetCardInfo("Blind Obedience", 59, Rarity.RARE, mage.cards.b.BlindObedience.class));
        cards.add(new SetCardInfo("Blinkmoth Urn", 244, Rarity.RARE, mage.cards.b.BlinkmothUrn.class));
        cards.add(new SetCardInfo("Blood Tyrant", 183, Rarity.RARE, mage.cards.b.BloodTyrant.class));
        cards.add(new SetCardInfo("Bloodbraid Elf", 184, Rarity.UNCOMMON, mage.cards.b.BloodbraidElf.class));
        cards.add(new SetCardInfo("Bonehoard", 245, Rarity.RARE, mage.cards.b.Bonehoard.class));
        cards.add(new SetCardInfo("Boompile", 52, Rarity.RARE, mage.cards.b.Boompile.class));
        cards.add(new SetCardInfo("Boros Charm", 185, Rarity.UNCOMMON, mage.cards.b.BorosCharm.class));
        cards.add(new SetCardInfo("Boros Garrison", 283, Rarity.UNCOMMON, mage.cards.b.BorosGarrison.class));
        cards.add(new SetCardInfo("Brave the Sands", 60, Rarity.UNCOMMON, mage.cards.b.BraveTheSands.class));
        cards.add(new SetCardInfo("Breath of Fury", 121, Rarity.RARE, mage.cards.b.BreathOfFury.class));
        cards.add(new SetCardInfo("Bred for the Hunt", 186, Rarity.UNCOMMON, mage.cards.b.BredForTheHunt.class));
        cards.add(new SetCardInfo("Breya, Etherium Shaper", 29, Rarity.MYTHIC, mage.cards.b.BreyaEtheriumShaper.class));
        cards.add(new SetCardInfo("Brutal Hordechief", 108, Rarity.MYTHIC, mage.cards.b.BrutalHordechief.class));
        cards.add(new SetCardInfo("Burgeoning", 143, Rarity.RARE, mage.cards.b.Burgeoning.class));
        cards.add(new SetCardInfo("Buried Ruin", 284, Rarity.UNCOMMON, mage.cards.b.BuriedRuin.class));
        cards.add(new SetCardInfo("Cauldron of Souls", 246, Rarity.RARE, mage.cards.c.CauldronOfSouls.class));
        cards.add(new SetCardInfo("Cathars' Crusade", 61, Rarity.RARE, mage.cards.c.CatharsCrusade.class));
        cards.add(new SetCardInfo("Caves of Koilos", 285, Rarity.RARE, mage.cards.c.CavesOfKoilos.class));
        cards.add(new SetCardInfo("Chain of Vapor", 84, Rarity.UNCOMMON, mage.cards.c.ChainOfVapor.class));
        cards.add(new SetCardInfo("Champion of Lambholt", 144, Rarity.RARE, mage.cards.c.ChampionOfLambholt.class));
        cards.add(new SetCardInfo("Chaos Warp", 122, Rarity.RARE, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Chasm Skulker", 85, Rarity.RARE, mage.cards.c.ChasmSkulker.class));
        cards.add(new SetCardInfo("Chief Engineer", 86, Rarity.RARE, mage.cards.c.ChiefEngineer.class));
        cards.add(new SetCardInfo("Chromatic Lantern", 247, Rarity.RARE, mage.cards.c.ChromaticLantern.class));
        cards.add(new SetCardInfo("Citadel Siege", 62, Rarity.RARE, mage.cards.c.CitadelSiege.class));
        cards.add(new SetCardInfo("Clan Defiance", 187, Rarity.RARE, mage.cards.c.ClanDefiance.class));
        cards.add(new SetCardInfo("Coastal Breach", 6, Rarity.RARE, mage.cards.c.CoastalBreach.class));
        cards.add(new SetCardInfo("Coiling Oracle", 188, Rarity.COMMON, mage.cards.c.CoilingOracle.class));
        cards.add(new SetCardInfo("Collective Voyage", 145, Rarity.RARE, mage.cards.c.CollectiveVoyage.class));
        cards.add(new SetCardInfo("Command Tower", 286, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Commander's Sphere", 248, Rarity.COMMON, mage.cards.c.CommandersSphere.class));
        cards.add(new SetCardInfo("Conqueror's Flail", 53, Rarity.RARE, mage.cards.c.ConquerorsFlail.class));
        cards.add(new SetCardInfo("Consuming Aberration", 189, Rarity.RARE, mage.cards.c.ConsumingAberration.class));
        cards.add(new SetCardInfo("Corpsejack Menace", 190, Rarity.RARE, mage.cards.c.CorpsejackMenace.class));
        cards.add(new SetCardInfo("Crackling Doom", 191, Rarity.RARE, mage.cards.c.CracklingDoom.class));
        cards.add(new SetCardInfo("Cranial Plating", 249, Rarity.UNCOMMON, mage.cards.c.CranialPlating.class));
        cards.add(new SetCardInfo("Crumbling Necropolis", 287, Rarity.UNCOMMON, mage.cards.c.CrumblingNecropolis.class));
        cards.add(new SetCardInfo("Crystalline Crawler", 54, Rarity.RARE, mage.cards.c.CrystallineCrawler.class));
        cards.add(new SetCardInfo("Cultivate", 146, Rarity.COMMON, mage.cards.c.Cultivate.class));
        cards.add(new SetCardInfo("Curtains' Call", 13, Rarity.RARE, mage.cards.c.CurtainsCall.class));
        cards.add(new SetCardInfo("Curse of Vengeance", 12, Rarity.RARE, mage.cards.c.CurseOfVengeance.class));
        cards.add(new SetCardInfo("Custodi Soulbinders", 63, Rarity.RARE, mage.cards.c.CustodiSoulbinders.class));
        cards.add(new SetCardInfo("Daretti, Scrap Savant", 123, Rarity.MYTHIC, mage.cards.d.DarettiScrapSavant.class));
        cards.add(new SetCardInfo("Darksteel Citadel", 288, Rarity.UNCOMMON, mage.cards.d.DarksteelCitadel.class));
        cards.add(new SetCardInfo("Darksteel Ingot", 250, Rarity.UNCOMMON, mage.cards.d.DarksteelIngot.class));
        cards.add(new SetCardInfo("Darkwater Catacombs", 289, Rarity.RARE, mage.cards.d.DarkwaterCatacombs.class));
        cards.add(new SetCardInfo("Dauntless Escort", 192, Rarity.RARE, mage.cards.d.DauntlessEscort.class));
        cards.add(new SetCardInfo("Decimate", 193, Rarity.RARE, mage.cards.d.Decimate.class));
        cards.add(new SetCardInfo("Deepglow Skate", 7, Rarity.RARE, mage.cards.d.DeepglowSkate.class));
        cards.add(new SetCardInfo("Den Protector", 147, Rarity.RARE, mage.cards.d.DenProtector.class));
        cards.add(new SetCardInfo("Devastation Tide", 87, Rarity.RARE, mage.cards.d.DevastationTide.class));
        cards.add(new SetCardInfo("Dimir Aqueduct", 290, Rarity.UNCOMMON, mage.cards.d.DimirAqueduct.class));
        cards.add(new SetCardInfo("Disdainful Stroke", 88, Rarity.COMMON, mage.cards.d.DisdainfulStroke.class));
        cards.add(new SetCardInfo("Dismal Backwater", 291, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Dispeller's Capsule", 64, Rarity.COMMON, mage.cards.d.DispellersCapsule.class));
        cards.add(new SetCardInfo("Divergent Transformations", 17, Rarity.RARE, mage.cards.d.DivergentTransformations.class));
        cards.add(new SetCardInfo("Dragon Mage", 124, Rarity.RARE, mage.cards.d.DragonMage.class));
        cards.add(new SetCardInfo("Dragonskull Summit", 292, Rarity.RARE, mage.cards.d.DragonskullSummit.class));
        cards.add(new SetCardInfo("Dreadship Reef", 293, Rarity.UNCOMMON, mage.cards.d.DreadshipReef.class));
        cards.add(new SetCardInfo("Duelist's Heritage", 1, Rarity.RARE, mage.cards.d.DuelistsHeritage.class));
        cards.add(new SetCardInfo("Duneblast", 194, Rarity.RARE, mage.cards.d.Duneblast.class));
        cards.add(new SetCardInfo("Edric, Spymaster of Trest", 195, Rarity.RARE, mage.cards.e.EdricSpymasterOfTrest.class));
        cards.add(new SetCardInfo("Elite Scaleguard", 65, Rarity.UNCOMMON, mage.cards.e.EliteScaleguard.class));
        cards.add(new SetCardInfo("Empyrial Plate", 251, Rarity.RARE, mage.cards.e.EmpyrialPlate.class));
        cards.add(new SetCardInfo("Enduring Scalelord", 196, Rarity.UNCOMMON, mage.cards.e.EnduringScalelord.class));
        cards.add(new SetCardInfo("Entrapment Maneuver", 2, Rarity.RARE, mage.cards.e.EntrapmentManeuver.class));
        cards.add(new SetCardInfo("Etched Oracle", 252, Rarity.UNCOMMON, mage.cards.e.EtchedOracle.class));
        cards.add(new SetCardInfo("Etherium Sculptor", 89, Rarity.COMMON, mage.cards.e.EtheriumSculptor.class));
        cards.add(new SetCardInfo("Etherium-Horn Sorcerer", 197, Rarity.RARE, mage.cards.e.EtheriumHornSorcerer.class));
        cards.add(new SetCardInfo("Ethersworn Adjudicator", 90, Rarity.MYTHIC, mage.cards.e.EtherswornAdjudicator.class));
        cards.add(new SetCardInfo("Evacuation", 91, Rarity.RARE, mage.cards.e.Evacuation.class));
        cards.add(new SetCardInfo("Everflowing Chalice", 253, Rarity.UNCOMMON, mage.cards.e.EverflowingChalice.class));
        cards.add(new SetCardInfo("Everlasting Torment", 233, Rarity.RARE, mage.cards.e.EverlastingTorment.class));
        cards.add(new SetCardInfo("Evolving Wilds", 294, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Executioner's Capsule", 109, Rarity.COMMON, mage.cards.e.ExecutionersCapsule.class));
        cards.add(new SetCardInfo("Exotic Orchard", 295, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Far Wanderings", 148, Rarity.COMMON, mage.cards.f.FarWanderings.class));
        cards.add(new SetCardInfo("Farseek", 149, Rarity.COMMON, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Fathom Mage", 198, Rarity.RARE, mage.cards.f.FathomMage.class));
        cards.add(new SetCardInfo("Fellwar Stone", 254, Rarity.UNCOMMON, mage.cards.f.FellwarStone.class));
        cards.add(new SetCardInfo("Festercreep", 110, Rarity.COMMON, mage.cards.f.Festercreep.class));
        cards.add(new SetCardInfo("Filigree Angel", 199, Rarity.RARE, mage.cards.f.FiligreeAngel.class));
        cards.add(new SetCardInfo("Forbidden Orchard", 296, Rarity.RARE, mage.cards.f.ForbiddenOrchard.class));
        cards.add(new SetCardInfo("Forest", 349, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 350, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 351, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forgotten Ancient", 150, Rarity.RARE, mage.cards.f.ForgottenAncient.class));
        cards.add(new SetCardInfo("Frontier Bivouac", 297, Rarity.UNCOMMON, mage.cards.f.FrontierBivouac.class));
        cards.add(new SetCardInfo("Gamekeeper", 151, Rarity.UNCOMMON, mage.cards.g.Gamekeeper.class));
        cards.add(new SetCardInfo("Ghastly Conscription", 111, Rarity.MYTHIC, mage.cards.g.GhastlyConscription.class));
        cards.add(new SetCardInfo("Ghave, Guru of Spores", 200, Rarity.MYTHIC, mage.cards.g.GhaveGuruOfSpores.class));
        cards.add(new SetCardInfo("Ghostly Prison", 66, Rarity.UNCOMMON, mage.cards.g.GhostlyPrison.class));
        cards.add(new SetCardInfo("Glint-Eye Nephilim", 201, Rarity.RARE, mage.cards.g.GlintEyeNephilim.class));
        cards.add(new SetCardInfo("Godo, Bandit Warlord", 125, Rarity.RARE, mage.cards.g.GodoBanditWarlord.class));
        cards.add(new SetCardInfo("Golgari Rot Farm", 298, Rarity.UNCOMMON, mage.cards.g.GolgariRotFarm.class));
        cards.add(new SetCardInfo("Golgari Signet", 255, Rarity.COMMON, mage.cards.g.GolgariSignet.class));
        cards.add(new SetCardInfo("Grab the Reins", 126, Rarity.UNCOMMON, mage.cards.g.GrabTheReins.class));
        cards.add(new SetCardInfo("Grand Coliseum", 299, Rarity.RARE, mage.cards.g.GrandColiseum.class));
        cards.add(new SetCardInfo("Grave Upheaval", 31, Rarity.UNCOMMON, mage.cards.g.GraveUpheaval.class));
        cards.add(new SetCardInfo("Gruul Signet", 256, Rarity.COMMON, mage.cards.g.GruulSignet.class));
        cards.add(new SetCardInfo("Gruul Turf", 300, Rarity.UNCOMMON, mage.cards.g.GruulTurf.class));
        cards.add(new SetCardInfo("Guiltfeeder", 112, Rarity.RARE, mage.cards.g.Guiltfeeder.class));
        cards.add(new SetCardInfo("Gwafa Hazid, Profiteer", 202, Rarity.RARE, mage.cards.g.GwafaHazidProfiteer.class));
        cards.add(new SetCardInfo("Hanna, Ship's Navigator", 203, Rarity.RARE, mage.cards.h.HannaShipsNavigator.class));
        cards.add(new SetCardInfo("Hardened Scales", 152, Rarity.RARE, mage.cards.h.HardenedScales.class));
        cards.add(new SetCardInfo("Hellkite Igniter", 127, Rarity.RARE, mage.cards.h.HellkiteIgniter.class));
        cards.add(new SetCardInfo("Hellkite Tyrant", 128, Rarity.MYTHIC, mage.cards.h.HellkiteTyrant.class));
        cards.add(new SetCardInfo("Homeward Path", 301, Rarity.RARE, mage.cards.h.HomewardPath.class));
        cards.add(new SetCardInfo("Hoofprints of the Stag", 67, Rarity.RARE, mage.cards.h.HoofprintsOfTheStag.class));
        cards.add(new SetCardInfo("Horizon Chimera", 204, Rarity.UNCOMMON, mage.cards.h.HorizonChimera.class));
        cards.add(new SetCardInfo("Howling Mine", 257, Rarity.RARE, mage.cards.h.HowlingMine.class));
        cards.add(new SetCardInfo("Humble Defector", 129, Rarity.UNCOMMON, mage.cards.h.HumbleDefector.class));
        cards.add(new SetCardInfo("Hushwing Gryff", 68, Rarity.RARE, mage.cards.h.HushwingGryff.class));
        cards.add(new SetCardInfo("Ichor Wellspring", 258, Rarity.COMMON, mage.cards.i.IchorWellspring.class));
        cards.add(new SetCardInfo("In Garruk's Wake", 113, Rarity.RARE, mage.cards.i.InGarruksWake.class));
        cards.add(new SetCardInfo("Inspiring Call", 153, Rarity.UNCOMMON, mage.cards.i.InspiringCall.class));
        cards.add(new SetCardInfo("Iroas, God of Victory", 205, Rarity.MYTHIC, mage.cards.i.IroasGodOfVictory.class));
        cards.add(new SetCardInfo("Island", 340, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 341, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 342, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Izzet Boilerworks", 302, Rarity.UNCOMMON, mage.cards.i.IzzetBoilerworks.class));
        cards.add(new SetCardInfo("Jor Kadeen, the Prevailer", 206, Rarity.RARE, mage.cards.j.JorKadeenThePrevailer.class));
        cards.add(new SetCardInfo("Jungle Hollow", 303, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Jungle Shrine", 304, Rarity.UNCOMMON, mage.cards.j.JungleShrine.class));
        cards.add(new SetCardInfo("Juniper Order Ranger", 207, Rarity.UNCOMMON, mage.cards.j.JuniperOrderRanger.class));
        cards.add(new SetCardInfo("Kalonian Hydra", 154, Rarity.MYTHIC, mage.cards.k.KalonianHydra.class));
        cards.add(new SetCardInfo("Karplusan Forest", 305, Rarity.RARE, mage.cards.k.KarplusanForest.class));
        cards.add(new SetCardInfo("Kazuul, Tyrant of the Cliffs", 130, Rarity.RARE, mage.cards.k.KazuulTyrantOfTheCliffs.class));
        cards.add(new SetCardInfo("Keening Stone", 259, Rarity.RARE, mage.cards.k.KeeningStone.class));
        cards.add(new SetCardInfo("Kodama's Reach", 155, Rarity.COMMON, mage.cards.k.KodamasReach.class));
        cards.add(new SetCardInfo("Korozda Guildmage", 208, Rarity.UNCOMMON, mage.cards.k.KorozdaGuildmage.class));
        cards.add(new SetCardInfo("Krosan Verge", 306, Rarity.UNCOMMON, mage.cards.k.KrosanVerge.class));
        cards.add(new SetCardInfo("Languish", 114, Rarity.RARE, mage.cards.l.Languish.class));
        cards.add(new SetCardInfo("Lavalanche", 209, Rarity.RARE, mage.cards.l.Lavalanche.class));
        cards.add(new SetCardInfo("Lightning Greaves", 260, Rarity.UNCOMMON, mage.cards.l.LightningGreaves.class));
        cards.add(new SetCardInfo("Loxodon Warhammer", 261, Rarity.UNCOMMON, mage.cards.l.LoxodonWarhammer.class));
        cards.add(new SetCardInfo("Lurking Predators", 156, Rarity.RARE, mage.cards.l.LurkingPredators.class));
        cards.add(new SetCardInfo("Magus of the Will", 14, Rarity.RARE, mage.cards.m.MagusOfTheWill.class));
        cards.add(new SetCardInfo("Managorger Hydra", 157, Rarity.RARE, mage.cards.m.ManagorgerHydra.class));
        cards.add(new SetCardInfo("Master Biomancer", 210, Rarity.MYTHIC, mage.cards.m.MasterBiomancer.class));
        cards.add(new SetCardInfo("Master of Etherium", 92, Rarity.RARE, mage.cards.m.MasterOfEtherium.class));
        cards.add(new SetCardInfo("Mentor of the Meek", 69, Rarity.RARE, mage.cards.m.MentorOfTheMeek.class));
        cards.add(new SetCardInfo("Merciless Eviction", 211, Rarity.RARE, mage.cards.m.MercilessEviction.class));
        cards.add(new SetCardInfo("Minds Aglow", 93, Rarity.RARE, mage.cards.m.MindsAglow.class));
        cards.add(new SetCardInfo("Mirror Entity", 70, Rarity.RARE, mage.cards.m.MirrorEntity.class));
        cards.add(new SetCardInfo("Mirrorweave", 234, Rarity.RARE, mage.cards.m.Mirrorweave.class));
        cards.add(new SetCardInfo("Mortify", 212, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Mosswort Bridge", 307, Rarity.RARE, mage.cards.m.MosswortBridge.class));
        cards.add(new SetCardInfo("Mountain", 346, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 347, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 348, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Murmuring Bosk", 308, Rarity.RARE, mage.cards.m.MurmuringBosk.class));
        cards.add(new SetCardInfo("Mycoloth", 158, Rarity.RARE, mage.cards.m.Mycoloth.class));
        cards.add(new SetCardInfo("Mycosynth Wellspring", 262, Rarity.COMMON, mage.cards.m.MycosynthWellspring.class));
        cards.add(new SetCardInfo("Myr Battlesphere", 263, Rarity.RARE, mage.cards.m.MyrBattlesphere.class));
        cards.add(new SetCardInfo("Myr Retriever", 264, Rarity.UNCOMMON, mage.cards.m.MyrRetriever.class));
        cards.add(new SetCardInfo("Myriad Landscape", 309, Rarity.UNCOMMON, mage.cards.m.MyriadLandscape.class));
        cards.add(new SetCardInfo("Mystic Monastery", 310, Rarity.UNCOMMON, mage.cards.m.MysticMonastery.class));
        cards.add(new SetCardInfo("Nath of the Gilt-Leaf", 213, Rarity.RARE, mage.cards.n.NathOfTheGiltLeaf.class));
        cards.add(new SetCardInfo("Naya Charm", 214, Rarity.UNCOMMON, mage.cards.n.NayaCharm.class));
        cards.add(new SetCardInfo("Necrogenesis", 215, Rarity.UNCOMMON, mage.cards.n.Necrogenesis.class));
        cards.add(new SetCardInfo("Necroplasm", 115, Rarity.RARE, mage.cards.n.Necroplasm.class));
        cards.add(new SetCardInfo("Nevinyrral's Disk", 265, Rarity.RARE, mage.cards.n.NevinyrralsDisk.class));
        cards.add(new SetCardInfo("Nomad Outpost", 311, Rarity.UNCOMMON, mage.cards.n.NomadOutpost.class));
        cards.add(new SetCardInfo("Oath of Druids", 159, Rarity.RARE, mage.cards.o.OathOfDruids.class));
        cards.add(new SetCardInfo("Oblation", 71, Rarity.RARE, mage.cards.o.Oblation.class));
        cards.add(new SetCardInfo("Opal Palace", 312, Rarity.COMMON, mage.cards.o.OpalPalace.class));
        cards.add(new SetCardInfo("Open the Vaults", 72, Rarity.RARE, mage.cards.o.OpenTheVaults.class));
        cards.add(new SetCardInfo("Opulent Palace", 313, Rarity.UNCOMMON, mage.cards.o.OpulentPalace.class));
        cards.add(new SetCardInfo("Order // Chaos", 240, Rarity.UNCOMMON, mage.cards.o.OrderChaos.class));
        cards.add(new SetCardInfo("Orzhov Basilica", 314, Rarity.UNCOMMON, mage.cards.o.OrzhovBasilica.class));
        cards.add(new SetCardInfo("Orzhov Signet", 266, Rarity.COMMON, mage.cards.o.OrzhovSignet.class));
        cards.add(new SetCardInfo("Past in Flames", 131, Rarity.MYTHIC, mage.cards.p.PastInFlames.class));
        cards.add(new SetCardInfo("Phyrexian Rebirth", 73, Rarity.RARE, mage.cards.p.PhyrexianRebirth.class));
        cards.add(new SetCardInfo("Plains", 337, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 338, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 339, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Primeval Protector", 23, Rarity.RARE, mage.cards.p.PrimevalProtector.class));
        cards.add(new SetCardInfo("Prismatic Geoscope", 55, Rarity.RARE, mage.cards.p.PrismaticGeoscope.class));
        cards.add(new SetCardInfo("Progenitor Mimic", 216, Rarity.MYTHIC, mage.cards.p.ProgenitorMimic.class));
        cards.add(new SetCardInfo("Propaganda", 94, Rarity.UNCOMMON, mage.cards.p.Propaganda.class));
        cards.add(new SetCardInfo("Psychosis Crawler", 267, Rarity.RARE, mage.cards.p.PsychosisCrawler.class));
        cards.add(new SetCardInfo("Putrefy", 217, Rarity.UNCOMMON, mage.cards.p.Putrefy.class));
        cards.add(new SetCardInfo("Quirion Explorer", 160, Rarity.COMMON, mage.cards.q.QuirionExplorer.class));
        cards.add(new SetCardInfo("Rakdos Carnarium", 315, Rarity.UNCOMMON, mage.cards.r.RakdosCarnarium.class));
        cards.add(new SetCardInfo("Rakdos Charm", 218, Rarity.UNCOMMON, mage.cards.r.RakdosCharm.class));
        cards.add(new SetCardInfo("Rakdos Signet", 268, Rarity.COMMON, mage.cards.r.RakdosSignet.class));
        cards.add(new SetCardInfo("Rampant Growth", 161, Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Read the Runes", 95, Rarity.RARE, mage.cards.r.ReadTheRunes.class));
        cards.add(new SetCardInfo("Realm Seekers", 162, Rarity.RARE, mage.cards.r.RealmSeekers.class));
        cards.add(new SetCardInfo("Reforge the Soul", 132, Rarity.RARE, mage.cards.r.ReforgeTheSoul.class));
        cards.add(new SetCardInfo("Reins of Power", 96, Rarity.RARE, mage.cards.r.ReinsOfPower.class));
        cards.add(new SetCardInfo("Reliquary Tower", 316, Rarity.UNCOMMON, mage.cards.r.ReliquaryTower.class));
        cards.add(new SetCardInfo("Reveillark", 74, Rarity.RARE, mage.cards.r.Reveillark.class));
        cards.add(new SetCardInfo("Reverse the Sands", 75, Rarity.RARE, mage.cards.r.ReverseTheSands.class));
        cards.add(new SetCardInfo("Rites of Flourishing", 163, Rarity.RARE, mage.cards.r.RitesOfFlourishing.class));
        cards.add(new SetCardInfo("Rootbound Crag", 317, Rarity.RARE, mage.cards.r.RootboundCrag.class));
        cards.add(new SetCardInfo("Rubblehulk", 219, Rarity.RARE, mage.cards.r.Rubblehulk.class));
        cards.add(new SetCardInfo("Rugged Highlands", 318, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Rupture Spire", 319, Rarity.COMMON, mage.cards.r.RuptureSpire.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 164, Rarity.COMMON, mage.cards.s.SakuraTribeElder.class));
        cards.add(new SetCardInfo("Sanctum Gargoyle", 76, Rarity.COMMON, mage.cards.s.SanctumGargoyle.class));
        cards.add(new SetCardInfo("Sandsteppe Citadel", 320, Rarity.UNCOMMON, mage.cards.s.SandsteppeCitadel.class));
        cards.add(new SetCardInfo("Sangromancer", 116, Rarity.RARE, mage.cards.s.Sangromancer.class));
        cards.add(new SetCardInfo("Satyr Wayfinder", 165, Rarity.COMMON, mage.cards.s.SatyrWayfinder.class));
        cards.add(new SetCardInfo("Savage Lands", 321, Rarity.UNCOMMON, mage.cards.s.SavageLands.class));
        cards.add(new SetCardInfo("Scavenging Ooze", 166, Rarity.RARE, mage.cards.s.ScavengingOoze.class));
        cards.add(new SetCardInfo("Seaside Citadel", 322, Rarity.UNCOMMON, mage.cards.s.SeasideCitadel.class));
        cards.add(new SetCardInfo("Seat of the Synod", 323, Rarity.COMMON, mage.cards.s.SeatOfTheSynod.class));
        cards.add(new SetCardInfo("Seeds of Renewal", 24, Rarity.RARE, mage.cards.s.SeedsOfRenewal.class));
        cards.add(new SetCardInfo("Selesnya Guildmage", 235, Rarity.UNCOMMON, mage.cards.s.SelesnyaGuildmage.class));
        cards.add(new SetCardInfo("Selesnya Sanctuary", 324, Rarity.UNCOMMON, mage.cards.s.SelesnyaSanctuary.class));
        cards.add(new SetCardInfo("Selvala, Explorer Returned", 220, Rarity.RARE, mage.cards.s.SelvalaExplorerReturned.class));
        cards.add(new SetCardInfo("Shadowblood Ridge", 325, Rarity.RARE, mage.cards.s.ShadowbloodRidge.class));
        cards.add(new SetCardInfo("Shamanic Revelation", 167, Rarity.RARE, mage.cards.s.ShamanicRevelation.class));
        cards.add(new SetCardInfo("Sharuum the Hegemon", 221, Rarity.MYTHIC, mage.cards.s.SharuumTheHegemon.class));
        cards.add(new SetCardInfo("Shimmer Myr", 269, Rarity.RARE, mage.cards.s.ShimmerMyr.class));
        cards.add(new SetCardInfo("Simic Growth Chamber", 326, Rarity.UNCOMMON, mage.cards.s.SimicGrowthChamber.class));
        cards.add(new SetCardInfo("Simic Signet", 270, Rarity.COMMON, mage.cards.s.SimicSignet.class));
        cards.add(new SetCardInfo("Skullclamp", 271, Rarity.UNCOMMON, mage.cards.s.Skullclamp.class));
        cards.add(new SetCardInfo("Slobad, Goblin Tinkerer", 133, Rarity.RARE, mage.cards.s.SlobadGoblinTinkerer.class));
        cards.add(new SetCardInfo("Sol Ring", 272, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 273, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Solidarity of Heroes", 168, Rarity.UNCOMMON, mage.cards.s.SolidarityOfHeroes.class));
        cards.add(new SetCardInfo("Soul of New Phyrexia", 274, Rarity.MYTHIC, mage.cards.s.SoulOfNewPhyrexia.class));
        cards.add(new SetCardInfo("Spellheart Chimera", 222, Rarity.UNCOMMON, mage.cards.s.SpellheartChimera.class));
        cards.add(new SetCardInfo("Spelltwine", 97, Rarity.RARE, mage.cards.s.Spelltwine.class));
        cards.add(new SetCardInfo("Sphere of Safety", 77, Rarity.UNCOMMON, mage.cards.s.SphereOfSafety.class));
        cards.add(new SetCardInfo("Sphinx Summoner", 223, Rarity.RARE, mage.cards.s.SphinxSummoner.class));
        cards.add(new SetCardInfo("Spinerock Knoll", 327, Rarity.RARE, mage.cards.s.SpinerockKnoll.class));
        cards.add(new SetCardInfo("Spitting Image", 236, Rarity.RARE, mage.cards.s.SpittingImage.class));
        cards.add(new SetCardInfo("Stalking Vengeance", 134, Rarity.RARE, mage.cards.s.StalkingVengeance.class));
        cards.add(new SetCardInfo("Sublime Exhalation", 5, Rarity.RARE, mage.cards.s.SublimeExhalation.class));
        cards.add(new SetCardInfo("Sunforger", 275, Rarity.RARE, mage.cards.s.Sunforger.class));
        cards.add(new SetCardInfo("Sungrass Prairie", 328, Rarity.RARE, mage.cards.s.SungrassPrairie.class));
        cards.add(new SetCardInfo("Sunpetal Grove", 329, Rarity.RARE, mage.cards.s.SunpetalGrove.class));
        cards.add(new SetCardInfo("Swamp", 343, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 344, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 345, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swan Song", 98, Rarity.RARE, mage.cards.s.SwanSong.class));
        cards.add(new SetCardInfo("Swiftfoot Boots", 276, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 330, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 78, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Sydri, Galvanic Genius", 224, Rarity.MYTHIC, mage.cards.s.SydriGalvanicGenius.class));
        cards.add(new SetCardInfo("Sylvan Reclamation", 44, Rarity.UNCOMMON, mage.cards.s.SylvanReclamation.class));
        cards.add(new SetCardInfo("Sylvok Explorer", 169, Rarity.COMMON, mage.cards.s.SylvokExplorer.class));
        cards.add(new SetCardInfo("Taurean Mauler", 135, Rarity.RARE, mage.cards.t.TaureanMauler.class));
        cards.add(new SetCardInfo("Temple Bell", 277, Rarity.RARE, mage.cards.t.TempleBell.class));
        cards.add(new SetCardInfo("Temple of the False God", 331, Rarity.UNCOMMON, mage.cards.t.TempleOfTheFalseGod.class));
        cards.add(new SetCardInfo("Tempt with Discovery", 170, Rarity.RARE, mage.cards.t.TemptWithDiscovery.class));
        cards.add(new SetCardInfo("Terminate", 225, Rarity.COMMON, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 332, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Tezzeret's Gambit", 99, Rarity.UNCOMMON, mage.cards.t.TezzeretsGambit.class));
        cards.add(new SetCardInfo("Thelonite Hermit", 171, Rarity.RARE, mage.cards.t.TheloniteHermit.class));
        cards.add(new SetCardInfo("Thopter Foundry", 237, Rarity.UNCOMMON, mage.cards.t.ThopterFoundry.class));
        cards.add(new SetCardInfo("Thornwood Falls", 333, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Thrummingbird", 100, Rarity.UNCOMMON, mage.cards.t.Thrummingbird.class));
        cards.add(new SetCardInfo("Thunderfoot Baloth", 172, Rarity.RARE, mage.cards.t.ThunderfootBaloth.class));
        cards.add(new SetCardInfo("Trading Post", 278, Rarity.RARE, mage.cards.t.TradingPost.class));
        cards.add(new SetCardInfo("Transguild Promenade", 334, Rarity.COMMON, mage.cards.t.TransguildPromenade.class));
        cards.add(new SetCardInfo("Trash for Treasure", 136, Rarity.RARE, mage.cards.t.TrashForTreasure.class));
        cards.add(new SetCardInfo("Treasure Cruise", 101, Rarity.COMMON, mage.cards.t.TreasureCruise.class));
        cards.add(new SetCardInfo("Trial // Error", 239, Rarity.UNCOMMON, mage.cards.t.TrialError.class));
        cards.add(new SetCardInfo("Trinket Mage", 102, Rarity.COMMON, mage.cards.t.TrinketMage.class));
        cards.add(new SetCardInfo("Tuskguard Captain", 173, Rarity.UNCOMMON, mage.cards.t.TuskguardCaptain.class));
        cards.add(new SetCardInfo("Underground River", 335, Rarity.RARE, mage.cards.u.UndergroundRiver.class));
        cards.add(new SetCardInfo("Utter End", 226, Rarity.RARE, mage.cards.u.UtterEnd.class));
        cards.add(new SetCardInfo("Vedalken Engineer", 103, Rarity.COMMON, mage.cards.v.VedalkenEngineer.class));
        cards.add(new SetCardInfo("Venser's Journal", 279, Rarity.RARE, mage.cards.v.VensersJournal.class));
        cards.add(new SetCardInfo("Veteran Explorer", 174, Rarity.UNCOMMON, mage.cards.v.VeteranExplorer.class));
        cards.add(new SetCardInfo("Vial Smasher the Fierce", 49, Rarity.MYTHIC, mage.cards.v.VialSmasherTheFierce.class));
        cards.add(new SetCardInfo("Volcanic Vision", 137, Rarity.RARE, mage.cards.v.VolcanicVision.class));
        cards.add(new SetCardInfo("Vorel of the Hull Clade", 227, Rarity.RARE, mage.cards.v.VorelOfTheHullClade.class));
        cards.add(new SetCardInfo("Vulturous Zombie", 228, Rarity.RARE, mage.cards.v.VulturousZombie.class));
        cards.add(new SetCardInfo("Wall of Blossoms", 175, Rarity.UNCOMMON, mage.cards.w.WallOfBlossoms.class));
        cards.add(new SetCardInfo("Waste Not", 117, Rarity.RARE, mage.cards.w.WasteNot.class));
        cards.add(new SetCardInfo("Wave of Reckoning", 79, Rarity.RARE, mage.cards.w.WaveOfReckoning.class));
        cards.add(new SetCardInfo("Wheel of Fate", 138, Rarity.RARE, mage.cards.w.WheelOfFate.class));
        cards.add(new SetCardInfo("Whims of the Fates", 139, Rarity.RARE, mage.cards.w.WhimsOfTheFates.class));
        cards.add(new SetCardInfo("Whipflare", 140, Rarity.UNCOMMON, mage.cards.w.Whipflare.class));
        cards.add(new SetCardInfo("Whispering Madness", 229, Rarity.RARE, mage.cards.w.WhisperingMadness.class));
        cards.add(new SetCardInfo("Whispersilk Cloak", 280, Rarity.UNCOMMON, mage.cards.w.WhispersilkCloak.class));
        cards.add(new SetCardInfo("Wight of Precinct Six", 118, Rarity.UNCOMMON, mage.cards.w.WightOfPrecinctSix.class));
        cards.add(new SetCardInfo("Wild Beastmaster", 176, Rarity.RARE, mage.cards.w.WildBeastmaster.class));
        cards.add(new SetCardInfo("Wilderness Elemental", 230, Rarity.UNCOMMON, mage.cards.w.WildernessElemental.class));
        cards.add(new SetCardInfo("Windborn Muse", 80, Rarity.RARE, mage.cards.w.WindbornMuse.class));
        cards.add(new SetCardInfo("Windbrisk Heights", 336, Rarity.RARE, mage.cards.w.WindbriskHeights.class));
        cards.add(new SetCardInfo("Windfall", 104, Rarity.UNCOMMON, mage.cards.w.Windfall.class));
        cards.add(new SetCardInfo("Worm Harvest", 238, Rarity.RARE, mage.cards.w.WormHarvest.class));
        cards.add(new SetCardInfo("Zedruu the Greathearted", 231, Rarity.MYTHIC, mage.cards.z.ZedruuTheGreathearted.class));
        cards.add(new SetCardInfo("Zhur-Taa Druid", 232, Rarity.COMMON, mage.cards.z.ZhurTaaDruid.class));
    }

}
