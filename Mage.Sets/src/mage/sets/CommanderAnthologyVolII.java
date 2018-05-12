/*
 * Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
 *
 * @author rystan
 */
public class CommanderAnthologyVolII extends ExpansionSet {

    private static final CommanderAnthologyVolII instance = new CommanderAnthologyVolII();

    public static CommanderAnthologyVolII getInstance() {
        return instance;
    }
    
    private CommanderAnthologyVolII() {
        super("Commander Anthology Vol. II", "CM2", ExpansionSet.buildDate(2018, 6, 8), SetType.SUPPLEMENTAL);
        this.blockName = "Commander Anthology Vol. II";
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Abzan Falconer", 999, Rarity.UNCOMMON, mage.cards.a.AbzanFalconer.class));
        cards.add(new SetCardInfo("Acidic Slime", 999, Rarity.UNCOMMON, mage.cards.a.AcidicSlime.class));
        cards.add(new SetCardInfo("Ancient Amphitheater", 999, Rarity.RARE, mage.cards.a.AncientAmphitheater.class));
        cards.add(new SetCardInfo("Ancient Excavation", 999, Rarity.UNCOMMON, mage.cards.a.AncientExcavation.class));
        cards.add(new SetCardInfo("Angel of Serenity", 999, Rarity.MYTHIC, mage.cards.a.AngelOfSerenity.class));
        cards.add(new SetCardInfo("Anya, Merciless Angel", 8, Rarity.MYTHIC, mage.cards.a.AnyaMercilessAngel.class));
        cards.add(new SetCardInfo("Arbiter of Knollridge", 999, Rarity.RARE, mage.cards.a.ArbiterOfKnollridge.class));
        cards.add(new SetCardInfo("Arcane Lighthouse", 999, Rarity.UNCOMMON, mage.cards.a.ArcaneLighthouse.class));
        cards.add(new SetCardInfo("Arcane Sanctum", 999, Rarity.UNCOMMON, mage.cards.a.ArcaneSanctum.class));
        cards.add(new SetCardInfo("Artisan of Kozilek", 999, Rarity.UNCOMMON, mage.cards.a.ArtisanOfKozilek.class));
        cards.add(new SetCardInfo("Ash Barrens", 999, Rarity.COMMON, mage.cards.a.AshBarrens.class));
        cards.add(new SetCardInfo("Astral Cornucopia", 999, Rarity.RARE, mage.cards.a.AstralCornucopia.class));
        cards.add(new SetCardInfo("Atraxa, Praetors' Voice", 10, Rarity.MYTHIC, mage.cards.a.AtraxaPraetorsVoice.class));
        cards.add(new SetCardInfo("Avatar of Woe", 999, Rarity.RARE, mage.cards.a.AvatarOfWoe.class));
        cards.add(new SetCardInfo("Azorius Chancery", 999, Rarity.UNCOMMON, mage.cards.a.AzoriusChancery.class));
        cards.add(new SetCardInfo("Bane of the Living", 999, Rarity.RARE, mage.cards.b.BaneOfTheLiving.class));
        cards.add(new SetCardInfo("Banishing Light", 999, Rarity.UNCOMMON, mage.cards.b.BanishingLight.class));
        cards.add(new SetCardInfo("Barren Moor", 999, Rarity.COMMON, mage.cards.b.BarrenMoor.class));
        cards.add(new SetCardInfo("Basalt Monolith", 999, Rarity.UNCOMMON, mage.cards.b.BasaltMonolith.class));
        cards.add(new SetCardInfo("Beetleback Chief", 999, Rarity.UNCOMMON, mage.cards.b.BeetlebackChief.class));
        cards.add(new SetCardInfo("Bitter Feud", 999, Rarity.RARE, mage.cards.b.BitterFeud.class));
        cards.add(new SetCardInfo("Blade of Selves", 999, Rarity.RARE, mage.cards.b.BladeOfSelves.class));
        cards.add(new SetCardInfo("Blasphemous Act", 999, Rarity.RARE, mage.cards.b.BlasphemousAct.class));
        cards.add(new SetCardInfo("Blasted Landscape", 999, Rarity.UNCOMMON, mage.cards.b.BlastedLandscape.class));
        cards.add(new SetCardInfo("Bogardan Hellkite", 999, Rarity.MYTHIC, mage.cards.b.BogardanHellkite.class));
        cards.add(new SetCardInfo("Borderland Behemoth", 999, Rarity.RARE, mage.cards.b.BorderlandBehemoth.class));
        cards.add(new SetCardInfo("Boros Cluestone", 999, Rarity.COMMON, mage.cards.b.BorosCluestone.class));
        cards.add(new SetCardInfo("Boros Garrison", 999, Rarity.UNCOMMON, mage.cards.b.BorosGarrison.class));
        cards.add(new SetCardInfo("Boros Guildgate", 999, Rarity.COMMON, mage.cards.b.BorosGuildgate.class));
        cards.add(new SetCardInfo("Boros Signet", 999, Rarity.COMMON, mage.cards.b.BorosSignet.class));
        cards.add(new SetCardInfo("Bosh, Iron Golem", 5, Rarity.RARE, mage.cards.b.BoshIronGolem.class));
        cards.add(new SetCardInfo("Bottle Gnomes", 999, Rarity.UNCOMMON, mage.cards.b.BottleGnomes.class));
        cards.add(new SetCardInfo("Brave the Sands", 999, Rarity.UNCOMMON, mage.cards.b.BraveTheSands.class));
        cards.add(new SetCardInfo("Brawn", 999, Rarity.UNCOMMON, mage.cards.b.Brawn.class));
        cards.add(new SetCardInfo("Breath of Darigaaz", 999, Rarity.UNCOMMON, mage.cards.b.BreathOfDarigaaz.class));
        cards.add(new SetCardInfo("Bred for the Hunt", 999, Rarity.UNCOMMON, mage.cards.b.BredForTheHunt.class));
        cards.add(new SetCardInfo("Buried Alive", 999, Rarity.UNCOMMON, mage.cards.b.BuriedAlive.class));
        cards.add(new SetCardInfo("Buried Ruin", 999, Rarity.UNCOMMON, mage.cards.b.BuriedRuin.class));
        cards.add(new SetCardInfo("Butcher of Malakir", 999, Rarity.RARE, mage.cards.b.ButcherOfMalakir.class));
        cards.add(new SetCardInfo("Caged Sun", 999, Rarity.RARE, mage.cards.c.CagedSun.class));
        cards.add(new SetCardInfo("Cathars' Crusade", 999, Rarity.RARE, mage.cards.c.CatharsCrusade.class));
        cards.add(new SetCardInfo("Cathodion", 999, Rarity.UNCOMMON, mage.cards.c.Cathodion.class));
        cards.add(new SetCardInfo("Cauldron of Souls", 999, Rarity.RARE, mage.cards.c.CauldronOfSouls.class));
        cards.add(new SetCardInfo("Champion of Lambholt", 999, Rarity.RARE, mage.cards.c.ChampionOfLambholt.class));
        cards.add(new SetCardInfo("Chaos Warp", 999, Rarity.RARE, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Citadel Siege", 999, Rarity.RARE, mage.cards.c.CitadelSiege.class));
        cards.add(new SetCardInfo("Coldsteel Heart", 999, Rarity.UNCOMMON, mage.cards.c.ColdsteelHeart.class));
        cards.add(new SetCardInfo("Command Tower", 999, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Commander's Sphere", 999, Rarity.COMMON, mage.cards.c.CommandersSphere.class));
        cards.add(new SetCardInfo("Corpsejack Menace", 999, Rarity.RARE, mage.cards.c.CorpsejackMenace.class));
        cards.add(new SetCardInfo("Crib Swap", 999, Rarity.UNCOMMON, mage.cards.c.CribSwap.class));
        cards.add(new SetCardInfo("Crystalline Crawler", 999, Rarity.RARE, mage.cards.c.CrystallineCrawler.class));
        cards.add(new SetCardInfo("Cultivate", 999, Rarity.COMMON, mage.cards.c.Cultivate.class));
        cards.add(new SetCardInfo("Curse of the Nightly Hunt", 999, Rarity.UNCOMMON, mage.cards.c.CurseOfTheNightlyHunt.class));
        cards.add(new SetCardInfo("Custodi Soulbinders", 999, Rarity.RARE, mage.cards.c.CustodiSoulbinders.class));
        cards.add(new SetCardInfo("Damia, Sage of Stone", 2, Rarity.MYTHIC, mage.cards.d.DamiaSageOfStone.class));
        cards.add(new SetCardInfo("Daretti, Scrap Savant", 999, Rarity.MYTHIC, mage.cards.d.DarettiScrapSavant.class));
        cards.add(new SetCardInfo("Dark Hatchling", 999, Rarity.RARE, mage.cards.d.DarkHatchling.class));
        cards.add(new SetCardInfo("Darksteel Citadel", 999, Rarity.UNCOMMON, mage.cards.d.DarksteelCitadel.class));
        cards.add(new SetCardInfo("Darksteel Ingot", 999, Rarity.UNCOMMON, mage.cards.d.DarksteelIngot.class));
        cards.add(new SetCardInfo("Darkwater Catacombs", 999, Rarity.RARE, mage.cards.d.DarkwaterCatacombs.class));
        cards.add(new SetCardInfo("Dawnbreak Reclaimer", 999, Rarity.RARE, mage.cards.d.DawnbreakReclaimer.class));
        cards.add(new SetCardInfo("Dawnglare Invoker", 999, Rarity.COMMON, mage.cards.d.DawnglareInvoker.class));
        cards.add(new SetCardInfo("Deepglow Skate", 999, Rarity.RARE, mage.cards.d.DeepglowSkate.class));
        cards.add(new SetCardInfo("Desecrator Hag", 999, Rarity.COMMON, mage.cards.d.DesecratorHag.class));
        cards.add(new SetCardInfo("Desolation Giant", 999, Rarity.RARE, mage.cards.d.DesolationGiant.class));
        cards.add(new SetCardInfo("Dimir Aqueduct", 999, Rarity.UNCOMMON, mage.cards.d.DimirAqueduct.class));
        cards.add(new SetCardInfo("Dimir Signet", 999, Rarity.COMMON, mage.cards.d.DimirSignet.class));
        cards.add(new SetCardInfo("Disaster Radius", 999, Rarity.RARE, mage.cards.d.DisasterRadius.class));
        cards.add(new SetCardInfo("Disdainful Stroke", 999, Rarity.COMMON, mage.cards.d.DisdainfulStroke.class));
        cards.add(new SetCardInfo("Dormant Volcano", 999, Rarity.UNCOMMON, mage.cards.d.DormantVolcano.class));
        cards.add(new SetCardInfo("Dreadship Reef", 999, Rarity.UNCOMMON, mage.cards.d.DreadshipReef.class));
        cards.add(new SetCardInfo("Dream Pillager", 999, Rarity.RARE, mage.cards.d.DreamPillager.class));
        cards.add(new SetCardInfo("Dreamborn Muse", 999, Rarity.RARE, mage.cards.d.DreambornMuse.class));
        cards.add(new SetCardInfo("Dreamstone Hedron", 999, Rarity.UNCOMMON, mage.cards.d.DreamstoneHedron.class));
        cards.add(new SetCardInfo("Drifting Meadow", 999, Rarity.COMMON, mage.cards.d.DriftingMeadow.class));
        cards.add(new SetCardInfo("Dualcaster Mage", 999, Rarity.RARE, mage.cards.d.DualcasterMage.class));
        cards.add(new SetCardInfo("Duelist's Heritage", 999, Rarity.RARE, mage.cards.d.DuelistsHeritage.class));
        cards.add(new SetCardInfo("Duneblast", 999, Rarity.RARE, mage.cards.d.Duneblast.class));
        cards.add(new SetCardInfo("Earthquake", 999, Rarity.RARE, mage.cards.e.Earthquake.class));
        cards.add(new SetCardInfo("Elite Scaleguard", 999, Rarity.UNCOMMON, mage.cards.e.EliteScaleguard.class));
        cards.add(new SetCardInfo("Enduring Scalelord", 999, Rarity.UNCOMMON, mage.cards.e.EnduringScalelord.class));
        cards.add(new SetCardInfo("Epochrasite", 999, Rarity.RARE, mage.cards.e.Epochrasite.class));
        cards.add(new SetCardInfo("Eternal Witness", 999, Rarity.UNCOMMON, mage.cards.e.EternalWitness.class));
        cards.add(new SetCardInfo("Everflowing Chalice", 999, Rarity.UNCOMMON, mage.cards.e.EverflowingChalice.class));
        cards.add(new SetCardInfo("Evolving Wilds", 999, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Exotic Orchard", 999, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Extractor Demon", 999, Rarity.RARE, mage.cards.e.ExtractorDemon.class));
        cards.add(new SetCardInfo("Fact or Fiction", 999, Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Faith's Fetters", 999, Rarity.COMMON, mage.cards.f.FaithsFetters.class));
        cards.add(new SetCardInfo("Faithless Looting", 999, Rarity.COMMON, mage.cards.f.FaithlessLooting.class));
        cards.add(new SetCardInfo("Fall of the Hammer", 999, Rarity.COMMON, mage.cards.f.FallOfTheHammer.class));
        cards.add(new SetCardInfo("Fathom Mage", 999, Rarity.RARE, mage.cards.f.FathomMage.class));
        cards.add(new SetCardInfo("Feldon of the Third Path", 6, Rarity.MYTHIC, mage.cards.f.FeldonOfTheThirdPath.class));
        cards.add(new SetCardInfo("Fellwar Stone", 999, Rarity.UNCOMMON, mage.cards.f.FellwarStone.class));
        cards.add(new SetCardInfo("Festercreep", 999, Rarity.COMMON, mage.cards.f.Festercreep.class));
        cards.add(new SetCardInfo("Fiery Confluence", 999, Rarity.RARE, mage.cards.f.FieryConfluence.class));
        cards.add(new SetCardInfo("Fire Diamond", 999, Rarity.UNCOMMON, mage.cards.f.FireDiamond.class));
        cards.add(new SetCardInfo("Flamekin Village", 999, Rarity.RARE, mage.cards.f.FlamekinVillage.class));
        cards.add(new SetCardInfo("Flametongue Kavu", 999, Rarity.UNCOMMON, mage.cards.f.FlametongueKavu.class));
        cards.add(new SetCardInfo("Fleshbag Marauder", 999, Rarity.UNCOMMON, mage.cards.f.FleshbagMarauder.class));
        cards.add(new SetCardInfo("Forgotten Ancient", 999, Rarity.RARE, mage.cards.f.ForgottenAncient.class));
        cards.add(new SetCardInfo("Forgotten Cave", 999, Rarity.COMMON, mage.cards.f.ForgottenCave.class));
        cards.add(new SetCardInfo("Fumiko the Lowblood", 100, Rarity.RARE, mage.cards.f.FumikoTheLowblood.class));
        cards.add(new SetCardInfo("Ghave, Guru of Spores", 157, Rarity.MYTHIC, mage.cards.g.GhaveGuruOfSpores.class));
        cards.add(new SetCardInfo("Ghost Quarter", 999, Rarity.UNCOMMON, mage.cards.g.GhostQuarter.class));
        cards.add(new SetCardInfo("Gisela, Blade of Goldnight", 9, Rarity.MYTHIC, mage.cards.g.GiselaBladeOfGoldnight.class));
        cards.add(new SetCardInfo("Goblin Welder", 999, Rarity.RARE, mage.cards.g.GoblinWelder.class));
        cards.add(new SetCardInfo("Golgari Rot Farm", 999, Rarity.UNCOMMON, mage.cards.g.GolgariRotFarm.class));
        cards.add(new SetCardInfo("Golgari Signet", 999, Rarity.COMMON, mage.cards.g.GolgariSignet.class));
        cards.add(new SetCardInfo("Grave Pact", 999, Rarity.RARE, mage.cards.g.GravePact.class));
        cards.add(new SetCardInfo("Gravedigger", 999, Rarity.COMMON, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Great Furnace", 999, Rarity.COMMON, mage.cards.g.GreatFurnace.class));
        cards.add(new SetCardInfo("Grip of Phyresis", 999, Rarity.UNCOMMON, mage.cards.g.GripOfPhyresis.class));
        cards.add(new SetCardInfo("Hamletback Goliath", 999, Rarity.RARE, mage.cards.h.HamletbackGoliath.class));
        cards.add(new SetCardInfo("Hammerfist Giant", 999, Rarity.RARE, mage.cards.h.HammerfistGiant.class));
        cards.add(new SetCardInfo("Hardened Scales", 999, Rarity.RARE, mage.cards.h.HardenedScales.class));
        cards.add(new SetCardInfo("Herald of the Host", 999, Rarity.UNCOMMON, mage.cards.h.HeraldOfTheHost.class));
        cards.add(new SetCardInfo("Hoard-Smelter Dragon", 999, Rarity.RARE, mage.cards.h.HoardSmelterDragon.class));
        cards.add(new SetCardInfo("Hostility", 999, Rarity.RARE, mage.cards.h.Hostility.class));
        cards.add(new SetCardInfo("Hunted Dragon", 999, Rarity.RARE, mage.cards.h.HuntedDragon.class));
        cards.add(new SetCardInfo("Ichor Wellspring", 999, Rarity.COMMON, mage.cards.i.IchorWellspring.class));
        cards.add(new SetCardInfo("Ikra Shidiqi, the Usurper", 11, Rarity.MYTHIC, mage.cards.i.IkraShidiqiTheUsurper.class));
        cards.add(new SetCardInfo("Impact Resonance", 999, Rarity.RARE, mage.cards.i.ImpactResonance.class));
        cards.add(new SetCardInfo("Incite Rebellion", 999, Rarity.RARE, mage.cards.i.InciteRebellion.class));
        cards.add(new SetCardInfo("Inferno Titan", 999, Rarity.MYTHIC, mage.cards.i.InfernoTitan.class));
        cards.add(new SetCardInfo("Ingot Chewer", 999, Rarity.COMMON, mage.cards.i.IngotChewer.class));
        cards.add(new SetCardInfo("Inspiring Call", 999, Rarity.UNCOMMON, mage.cards.i.InspiringCall.class));
        cards.add(new SetCardInfo("Ishai, Ojutai Dragonspeaker", 12, Rarity.MYTHIC, mage.cards.i.IshaiOjutaiDragonspeaker.class));
        cards.add(new SetCardInfo("Jalum Tome", 999, Rarity.RARE, mage.cards.j.JalumTome.class));
        cards.add(new SetCardInfo("Jareth, Leonine Titan", 30, Rarity.COMMON, mage.cards.j.JarethLeonineTitan.class));
        cards.add(new SetCardInfo("Juniper Order Ranger", 999, Rarity.UNCOMMON, mage.cards.j.JuniperOrderRanger.class));
        cards.add(new SetCardInfo("Junk Diver", 999, Rarity.RARE, mage.cards.j.JunkDiver.class));
        cards.add(new SetCardInfo("Jwar Isle Refuge", 999, Rarity.UNCOMMON, mage.cards.j.JwarIsleRefuge.class));
        cards.add(new SetCardInfo("Kalemne, Disciple of Iroas", 7, Rarity.MYTHIC, mage.cards.k.KalemneDiscipleOfIroas.class));
        cards.add(new SetCardInfo("Kalemne's Captain", 999, Rarity.RARE, mage.cards.k.KalemnesCaptain.class));
        cards.add(new SetCardInfo("Kalonian Hydra", 999, Rarity.MYTHIC, mage.cards.k.KalonianHydra.class));
        cards.add(new SetCardInfo("Languish", 999, Rarity.RARE, mage.cards.l.Languish.class));
        cards.add(new SetCardInfo("Lhurgoyf", 999, Rarity.RARE, mage.cards.l.Lhurgoyf.class));
        cards.add(new SetCardInfo("Lightning Greaves", 999, Rarity.UNCOMMON, mage.cards.l.LightningGreaves.class));
        cards.add(new SetCardInfo("Liquimetal Coating", 999, Rarity.UNCOMMON, mage.cards.l.LiquimetalCoating.class));
        cards.add(new SetCardInfo("Living Death", 999, Rarity.RARE, mage.cards.l.LivingDeath.class));
        cards.add(new SetCardInfo("Lonely Sandbar", 999, Rarity.COMMON, mage.cards.l.LonelySandbar.class));
        cards.add(new SetCardInfo("Loreseeker's Stone", 999, Rarity.UNCOMMON, mage.cards.l.LoreseekersStone.class));
        cards.add(new SetCardInfo("Loxodon Warhammer", 999, Rarity.UNCOMMON, mage.cards.l.LoxodonWarhammer.class));
        cards.add(new SetCardInfo("Magma Giant", 999, Rarity.RARE, mage.cards.m.MagmaGiant.class));
        cards.add(new SetCardInfo("Magmaquake", 999, Rarity.RARE, mage.cards.m.Magmaquake.class));
        cards.add(new SetCardInfo("Magus of the Wheel", 999, Rarity.RARE, mage.cards.m.MagusOfTheWheel.class));
        cards.add(new SetCardInfo("Manifold Insights", 999, Rarity.RARE, mage.cards.m.ManifoldInsights.class));
        cards.add(new SetCardInfo("Master Biomancer", 999, Rarity.MYTHIC, mage.cards.m.MasterBiomancer.class));
        cards.add(new SetCardInfo("Memory Erosion", 999, Rarity.RARE, mage.cards.m.MemoryErosion.class));
        cards.add(new SetCardInfo("Merciless Eviction", 999, Rarity.RARE, mage.cards.m.MercilessEviction.class));
        cards.add(new SetCardInfo("Meteor Blast", 999, Rarity.UNCOMMON, mage.cards.m.MeteorBlast.class));
        cards.add(new SetCardInfo("Migratory Route", 999, Rarity.UNCOMMON, mage.cards.m.MigratoryRoute.class));
        cards.add(new SetCardInfo("Mind Stone", 999, Rarity.UNCOMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Minds Aglow", 999, Rarity.RARE, mage.cards.m.MindsAglow.class));
        cards.add(new SetCardInfo("Mirrorweave", 999, Rarity.RARE, mage.cards.m.Mirrorweave.class));
        cards.add(new SetCardInfo("Mortify", 999, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Mortivore", 999, Rarity.RARE, mage.cards.m.Mortivore.class));
        cards.add(new SetCardInfo("Mulldrifter", 999, Rarity.UNCOMMON, mage.cards.m.Mulldrifter.class));
        cards.add(new SetCardInfo("Murmuring Bosk", 999, Rarity.RARE, mage.cards.m.MurmuringBosk.class));
        cards.add(new SetCardInfo("Mycosynth Wellspring", 999, Rarity.COMMON, mage.cards.m.MycosynthWellspring.class));
        cards.add(new SetCardInfo("Myr Battlesphere", 999, Rarity.RARE, mage.cards.m.MyrBattlesphere.class));
        cards.add(new SetCardInfo("Myr Retriever", 999, Rarity.UNCOMMON, mage.cards.m.MyrRetriever.class));
        cards.add(new SetCardInfo("Myr Sire", 999, Rarity.COMMON, mage.cards.m.MyrSire.class));
        cards.add(new SetCardInfo("Necroplasm", 999, Rarity.RARE, mage.cards.n.Necroplasm.class));
        cards.add(new SetCardInfo("Nezumi Graverobber", 999, Rarity.UNCOMMON, mage.cards.n.NezumiGraverobber.class));
        cards.add(new SetCardInfo("Oblivion Stone", 999, Rarity.RARE, mage.cards.o.OblivionStone.class));
        cards.add(new SetCardInfo("Opal Palace", 999, Rarity.COMMON, mage.cards.o.OpalPalace.class));
        cards.add(new SetCardInfo("Opulent Palace", 999, Rarity.UNCOMMON, mage.cards.o.OpulentPalace.class));
        cards.add(new SetCardInfo("Oreskos Explorer", 999, Rarity.UNCOMMON, mage.cards.o.OreskosExplorer.class));
        cards.add(new SetCardInfo("Orim's Thunder", 999, Rarity.COMMON, mage.cards.o.OrimsThunder.class));
        cards.add(new SetCardInfo("Orzhov Advokist", 999, Rarity.UNCOMMON, mage.cards.o.OrzhovAdvokist.class));
        cards.add(new SetCardInfo("Orzhov Signet", 999, Rarity.COMMON, mage.cards.o.OrzhovSignet.class));
        cards.add(new SetCardInfo("Palladium Myr", 999, Rarity.UNCOMMON, mage.cards.p.PalladiumMyr.class));
        cards.add(new SetCardInfo("Panic Spellbomb", 999, Rarity.COMMON, mage.cards.p.PanicSpellbomb.class));
        cards.add(new SetCardInfo("Patron of the Nezumi", 999, Rarity.RARE, mage.cards.p.PatronOfTheNezumi.class));
        cards.add(new SetCardInfo("Pentavus", 999, Rarity.RARE, mage.cards.p.Pentavus.class));
        cards.add(new SetCardInfo("Phyrexia's Core", 999, Rarity.UNCOMMON, mage.cards.p.PhyrexiasCore.class));
        cards.add(new SetCardInfo("Pilgrim's Eye", 999, Rarity.COMMON, mage.cards.p.PilgrimsEye.class));
        cards.add(new SetCardInfo("Pristine Talisman", 999, Rarity.COMMON, mage.cards.p.PristineTalisman.class));
        cards.add(new SetCardInfo("Putrefy", 999, Rarity.UNCOMMON, mage.cards.p.Putrefy.class));
        cards.add(new SetCardInfo("Relic Crush", 999, Rarity.COMMON, mage.cards.r.RelicCrush.class));
        cards.add(new SetCardInfo("Reliquary Tower", 999, Rarity.UNCOMMON, mage.cards.r.ReliquaryTower.class));
        cards.add(new SetCardInfo("Reveillark", 999, Rarity.RARE, mage.cards.r.Reveillark.class));
        cards.add(new SetCardInfo("Reyhan, Last of the Abzan", 13, Rarity.RARE, mage.cards.r.ReyhanLastOfTheAbzan.class));
        cards.add(new SetCardInfo("Riddlekeeper", 999, Rarity.RARE, mage.cards.r.Riddlekeeper.class));
        cards.add(new SetCardInfo("Rise from the Grave", 999, Rarity.UNCOMMON, mage.cards.r.RiseFromTheGrave.class));
        cards.add(new SetCardInfo("Rite of the Raging Storm", 999, Rarity.UNCOMMON, mage.cards.r.RiteOfTheRagingStorm.class));
        cards.add(new SetCardInfo("Ruby Medallion", 999, Rarity.RARE, mage.cards.r.RubyMedallion.class));
        cards.add(new SetCardInfo("Rupture Spire", 999, Rarity.COMMON, mage.cards.r.RuptureSpire.class));
        cards.add(new SetCardInfo("Sandsteppe Citadel", 999, Rarity.UNCOMMON, mage.cards.s.SandsteppeCitadel.class));
        cards.add(new SetCardInfo("Sandstone Oracle", 999, Rarity.UNCOMMON, mage.cards.s.SandstoneOracle.class));
        cards.add(new SetCardInfo("Scavenging Ooze", 999, Rarity.RARE, mage.cards.s.ScavengingOoze.class));
        cards.add(new SetCardInfo("Scrap Mastery", 999, Rarity.RARE, mage.cards.s.ScrapMastery.class));
        cards.add(new SetCardInfo("Scythe Specter", 999, Rarity.RARE, mage.cards.s.ScytheSpecter.class));
        cards.add(new SetCardInfo("Seaside Citadel", 999, Rarity.UNCOMMON, mage.cards.s.SeasideCitadel.class));
        cards.add(new SetCardInfo("Secluded Steppe", 999, Rarity.COMMON, mage.cards.s.SecludedSteppe.class));
        cards.add(new SetCardInfo("Seer's Sundial", 999, Rarity.RARE, mage.cards.s.SeersSundial.class));
        cards.add(new SetCardInfo("Sewer Nemesis", 999, Rarity.RARE, mage.cards.s.SewerNemesis.class));
        cards.add(new SetCardInfo("Shared Trauma", 999, Rarity.RARE, mage.cards.s.SharedTrauma.class));
        cards.add(new SetCardInfo("Sign in Blood", 999, Rarity.COMMON, mage.cards.s.SignInBlood.class));
        cards.add(new SetCardInfo("Simic Growth Chamber", 999, Rarity.UNCOMMON, mage.cards.s.SimicGrowthChamber.class));
        cards.add(new SetCardInfo("Simic Signet", 999, Rarity.COMMON, mage.cards.s.SimicSignet.class));
        cards.add(new SetCardInfo("Skullbriar, the Walking Grave", 165, Rarity.RARE, mage.cards.s.SkullbriarTheWalkingGrave.class));
        cards.add(new SetCardInfo("Slipstream Eel", 999, Rarity.COMMON, mage.cards.s.SlipstreamEel.class));
        cards.add(new SetCardInfo("Smoldering Crater", 999, Rarity.COMMON, mage.cards.s.SmolderingCrater.class));
        cards.add(new SetCardInfo("Sol Ring", 999, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 999, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Solidarity of Heroes", 999, Rarity.UNCOMMON, mage.cards.s.SolidarityOfHeroes.class));
        cards.add(new SetCardInfo("Spell Crumple", 999, Rarity.UNCOMMON, mage.cards.s.SpellCrumple.class));
        cards.add(new SetCardInfo("Spine of Ish Sah", 999, Rarity.RARE, mage.cards.s.SpineOfIshSah.class));
        cards.add(new SetCardInfo("Spitebellows", 999, Rarity.UNCOMMON, mage.cards.s.Spitebellows.class));
        cards.add(new SetCardInfo("Spitting Image", 999, Rarity.RARE, mage.cards.s.SpittingImage.class));
        cards.add(new SetCardInfo("Staff of Nin", 999, Rarity.RARE, mage.cards.s.StaffOfNin.class));
        cards.add(new SetCardInfo("Starstorm", 999, Rarity.RARE, mage.cards.s.Starstorm.class));
        cards.add(new SetCardInfo("Steel Hellkite", 999, Rarity.RARE, mage.cards.s.SteelHellkite.class));
        cards.add(new SetCardInfo("Stinkdrinker Daredevil", 999, Rarity.COMMON, mage.cards.s.StinkdrinkerDaredevil.class));
        cards.add(new SetCardInfo("Stitch Together", 999, Rarity.UNCOMMON, mage.cards.s.StitchTogether.class));
        cards.add(new SetCardInfo("Stoneshock Giant", 999, Rarity.UNCOMMON, mage.cards.s.StoneshockGiant.class));
        cards.add(new SetCardInfo("Sublime Exhalation", 999, Rarity.RARE, mage.cards.s.SublimeExhalation.class));
        cards.add(new SetCardInfo("Sun Titan", 999, Rarity.MYTHIC, mage.cards.s.SunTitan.class));
        cards.add(new SetCardInfo("Sungrass Prairie", 999, Rarity.RARE, mage.cards.s.SungrassPrairie.class));
        cards.add(new SetCardInfo("Sunrise Sovereign", 999, Rarity.RARE, mage.cards.s.SunriseSovereign.class));
        cards.add(new SetCardInfo("Svogthos, the Restless Tomb", 999, Rarity.UNCOMMON, mage.cards.s.SvogthosTheRestlessTomb.class));
        cards.add(new SetCardInfo("Swiftfoot Boots", 999, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class));
        cards.add(new SetCardInfo("Sylvan Reclamation", 999, Rarity.UNCOMMON, mage.cards.s.SylvanReclamation.class));
        cards.add(new SetCardInfo("Syphon Flesh", 999, Rarity.UNCOMMON, mage.cards.s.SyphonFlesh.class));
        cards.add(new SetCardInfo("Syphon Mind", 999, Rarity.COMMON, mage.cards.s.SyphonMind.class));
        cards.add(new SetCardInfo("Szadek, Lord of Secrets", 168, Rarity.RARE, mage.cards.s.SzadekLordOfSecrets.class));
        cards.add(new SetCardInfo("Taurean Mauler", 999, Rarity.RARE, mage.cards.t.TaureanMauler.class));
        cards.add(new SetCardInfo("Temple of the False God", 999, Rarity.UNCOMMON, mage.cards.t.TempleOfTheFalseGod.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 999, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Tezzeret's Gambit", 999, Rarity.UNCOMMON, mage.cards.t.TezzeretsGambit.class));
        cards.add(new SetCardInfo("The Mimeoplasm", 1, Rarity.MYTHIC, mage.cards.t.TheMimeoplasm.class));
        cards.add(new SetCardInfo("Thought Vessel", 999, Rarity.COMMON, mage.cards.t.ThoughtVessel.class));
        cards.add(new SetCardInfo("Thrummingbird", 999, Rarity.UNCOMMON, mage.cards.t.Thrummingbird.class));
        cards.add(new SetCardInfo("Thundercloud Shaman", 999, Rarity.UNCOMMON, mage.cards.t.ThundercloudShaman.class));
        cards.add(new SetCardInfo("Trading Post", 999, Rarity.RARE, mage.cards.t.TradingPost.class));
        cards.add(new SetCardInfo("Tranquil Thicket", 999, Rarity.COMMON, mage.cards.t.TranquilThicket.class));
        cards.add(new SetCardInfo("Treasure Cruise", 999, Rarity.COMMON, mage.cards.t.TreasureCruise.class));
        cards.add(new SetCardInfo("Tribute to the Wild", 999, Rarity.UNCOMMON, mage.cards.t.TributeToTheWild.class));
        cards.add(new SetCardInfo("Triskelavus", 999, Rarity.RARE, mage.cards.t.Triskelavus.class));
        cards.add(new SetCardInfo("Troll Ascetic", 999, Rarity.RARE, mage.cards.t.TrollAscetic.class));
        cards.add(new SetCardInfo("Tuktuk the Explorer", 124, Rarity.RARE, mage.cards.t.TuktukTheExplorer.class));
        cards.add(new SetCardInfo("Tuskguard Captain", 999, Rarity.UNCOMMON, mage.cards.t.TuskguardCaptain.class));
        cards.add(new SetCardInfo("Tyrant's Familiar", 999, Rarity.RARE, mage.cards.t.TyrantsFamiliar.class));
        cards.add(new SetCardInfo("Underground River", 999, Rarity.RARE, mage.cards.u.UndergroundRiver.class));
        cards.add(new SetCardInfo("Unnerve", 999, Rarity.COMMON, mage.cards.u.Unnerve.class));
        cards.add(new SetCardInfo("Unstable Obelisk", 999, Rarity.UNCOMMON, mage.cards.u.UnstableObelisk.class));
        cards.add(new SetCardInfo("Urza's Incubator", 999, Rarity.RARE, mage.cards.u.UrzasIncubator.class));
        cards.add(new SetCardInfo("Victory's Herald", 999, Rarity.RARE, mage.cards.v.VictorysHerald.class));
        cards.add(new SetCardInfo("Vivid Crag", 999, Rarity.UNCOMMON, mage.cards.v.VividCrag.class));
        cards.add(new SetCardInfo("Vivid Meadow", 999, Rarity.UNCOMMON, mage.cards.v.VividMeadow.class));
        cards.add(new SetCardInfo("Volcanic Offering", 999, Rarity.RARE, mage.cards.v.VolcanicOffering.class));
        cards.add(new SetCardInfo("Vorel of the Hull Clade", 169, Rarity.RARE, mage.cards.v.VorelOfTheHullClade.class));
        cards.add(new SetCardInfo("Vorosh, the Hunter", 3, Rarity.RARE, mage.cards.v.VoroshTheHunter.class));
        cards.add(new SetCardInfo("Vow of Flight", 999, Rarity.UNCOMMON, mage.cards.v.VowOfFlight.class));
        cards.add(new SetCardInfo("Vow of Malice", 999, Rarity.UNCOMMON, mage.cards.v.VowOfMalice.class));
        cards.add(new SetCardInfo("Vow of Wildness", 999, Rarity.UNCOMMON, mage.cards.v.VowOfWildness.class));
        cards.add(new SetCardInfo("Vulturous Zombie", 999, Rarity.RARE, mage.cards.v.VulturousZombie.class));
        cards.add(new SetCardInfo("Warchief Giant", 999, Rarity.UNCOMMON, mage.cards.w.WarchiefGiant.class));
        cards.add(new SetCardInfo("Warmonger Hellkite", 999, Rarity.RARE, mage.cards.w.WarmongerHellkite.class));
        cards.add(new SetCardInfo("Warstorm Surge", 999, Rarity.RARE, mage.cards.w.WarstormSurge.class));
        cards.add(new SetCardInfo("Wayfarer's Bauble", 999, Rarity.COMMON, mage.cards.w.WayfarersBauble.class));
        cards.add(new SetCardInfo("Whipflare", 999, Rarity.UNCOMMON, mage.cards.w.Whipflare.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 999, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
        cards.add(new SetCardInfo("Windfall", 999, Rarity.UNCOMMON, mage.cards.w.Windfall.class));
        cards.add(new SetCardInfo("Wonder", 999, Rarity.UNCOMMON, mage.cards.w.Wonder.class));
        cards.add(new SetCardInfo("Word of Seizing", 999, Rarity.RARE, mage.cards.w.WordOfSeizing.class));
        cards.add(new SetCardInfo("Worn Powerstone", 999, Rarity.UNCOMMON, mage.cards.w.WornPowerstone.class));
        cards.add(new SetCardInfo("Wrexial, the Risen Deep", 171, Rarity.MYTHIC, mage.cards.w.WrexialTheRisenDeep.class));
        cards.add(new SetCardInfo("Wurmcoil Engine", 999, Rarity.MYTHIC, mage.cards.w.WurmcoilEngine.class));
        cards.add(new SetCardInfo("Yavimaya Elder", 999, Rarity.COMMON, mage.cards.y.YavimayaElder.class));
    }
}
