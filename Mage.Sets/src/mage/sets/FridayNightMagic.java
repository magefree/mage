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
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class FridayNightMagic extends ExpansionSet {

    private static final FridayNightMagic instance = new FridayNightMagic();

    public static FridayNightMagic getInstance() {
        return instance;
    }

    private FridayNightMagic() {
        super("Friday Night Magic", "FNMP", ExpansionSet.buildDate(2011, 6, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Abzan Beastmaster", 180, Rarity.UNCOMMON, mage.cards.a.AbzanBeastmaster.class));
        cards.add(new SetCardInfo("Accumulated Knowledge", 51, Rarity.COMMON, mage.cards.a.AccumulatedKnowledge.class));
        cards.add(new SetCardInfo("Acidic Slime", 145, Rarity.UNCOMMON, mage.cards.a.AcidicSlime.class));
        cards.add(new SetCardInfo("Aether Hub", 205, Rarity.SPECIAL, mage.cards.a.AetherHub.class));
        cards.add(new SetCardInfo("Albino Troll", 20, Rarity.UNCOMMON, mage.cards.a.AlbinoTroll.class));
        cards.add(new SetCardInfo("Anathemancer", 122, Rarity.UNCOMMON, mage.cards.a.Anathemancer.class));
        cards.add(new SetCardInfo("Ancient Grudge", 144, Rarity.COMMON, mage.cards.a.AncientGrudge.class));
        cards.add(new SetCardInfo("Ancient Ziggurat", 118, Rarity.UNCOMMON, mage.cards.a.AncientZiggurat.class));
        cards.add(new SetCardInfo("Anticipate", 187, Rarity.COMMON, mage.cards.a.Anticipate.class));
        cards.add(new SetCardInfo("Armadillo Cloak", 69, Rarity.COMMON, mage.cards.a.ArmadilloCloak.class));
        cards.add(new SetCardInfo("Arrogant Wurm", 77, Rarity.UNCOMMON, mage.cards.a.ArrogantWurm.class));
        cards.add(new SetCardInfo("Artisan of Kozilek", 131, Rarity.UNCOMMON, mage.cards.a.ArtisanOfKozilek.class));
        cards.add(new SetCardInfo("Astral Slide", 76, Rarity.UNCOMMON, mage.cards.a.AstralSlide.class));
        cards.add(new SetCardInfo("Aura of Silence", 26, Rarity.UNCOMMON, mage.cards.a.AuraOfSilence.class));
        cards.add(new SetCardInfo("Avacyn's Pilgrim", 147, Rarity.COMMON, mage.cards.a.AvacynsPilgrim.class));
        cards.add(new SetCardInfo("Avalanche Riders", 52, Rarity.UNCOMMON, mage.cards.a.AvalancheRiders.class));
        cards.add(new SetCardInfo("Banisher Priest", 166, Rarity.UNCOMMON, mage.cards.b.BanisherPriest.class));
        cards.add(new SetCardInfo("Banishing Light", 172, Rarity.UNCOMMON, mage.cards.b.BanishingLight.class));
        cards.add(new SetCardInfo("Basking Rootwalla", 83, Rarity.COMMON, mage.cards.b.BaskingRootwalla.class));
        cards.add(new SetCardInfo("Bile Blight", 171, Rarity.UNCOMMON, mage.cards.b.BileBlight.class));
        cards.add(new SetCardInfo("Black Knight", 22, Rarity.UNCOMMON, mage.cards.b.BlackKnight.class));
        cards.add(new SetCardInfo("Blastoderm", 59, Rarity.COMMON, mage.cards.b.Blastoderm.class));
        cards.add(new SetCardInfo("Blighted Fen", 191, Rarity.UNCOMMON, mage.cards.b.BlightedFen.class));
        cards.add(new SetCardInfo("Bloodbraid Elf", 119, Rarity.UNCOMMON, mage.cards.b.BloodbraidElf.class));
        cards.add(new SetCardInfo("Bottle Gnomes", 32, Rarity.UNCOMMON, mage.cards.b.BottleGnomes.class));
        cards.add(new SetCardInfo("Brain Maggot", 174, Rarity.UNCOMMON, mage.cards.b.BrainMaggot.class));
        cards.add(new SetCardInfo("Brainstorm", 55, Rarity.COMMON, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Browbeat", 113, Rarity.UNCOMMON, mage.cards.b.Browbeat.class));
        cards.add(new SetCardInfo("Cabal Coffers", 89, Rarity.UNCOMMON, mage.cards.c.CabalCoffers.class));
        cards.add(new SetCardInfo("Cabal Therapy", 60, Rarity.UNCOMMON, mage.cards.c.CabalTherapy.class));
        cards.add(new SetCardInfo("Call of the Conclave", 155, Rarity.UNCOMMON, mage.cards.c.CallOfTheConclave.class));
        cards.add(new SetCardInfo("Call the Bloodline", 199, Rarity.UNCOMMON, mage.cards.c.CallTheBloodline.class));
        cards.add(new SetCardInfo("Capsize", 35, Rarity.COMMON, mage.cards.c.Capsize.class));
        cards.add(new SetCardInfo("Carnophage", 16, Rarity.COMMON, mage.cards.c.Carnophage.class));
        cards.add(new SetCardInfo("Carrion Feeder", 49, Rarity.COMMON, mage.cards.c.CarrionFeeder.class));
        cards.add(new SetCardInfo("Chainer's Edict", 74, Rarity.UNCOMMON, mage.cards.c.ChainersEdict.class));
        cards.add(new SetCardInfo("Circle of Protection: Red", 63, Rarity.COMMON, mage.cards.c.CircleOfProtectionRed.class));
        cards.add(new SetCardInfo("Circular Logic", 75, Rarity.UNCOMMON, mage.cards.c.CircularLogic.class));
        cards.add(new SetCardInfo("Clash of Wills", 189, Rarity.UNCOMMON, mage.cards.c.ClashOfWills.class));
        cards.add(new SetCardInfo("Cloudpost", 120, Rarity.COMMON, mage.cards.c.Cloudpost.class));
        cards.add(new SetCardInfo("Contagion Clasp", 137, Rarity.UNCOMMON, mage.cards.c.ContagionClasp.class));
        cards.add(new SetCardInfo("Counterspell", 66, Rarity.COMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Crumbling Vestige", 195, Rarity.COMMON, mage.cards.c.CrumblingVestige.class));
        cards.add(new SetCardInfo("Crystalline Sliver", 34, Rarity.UNCOMMON, mage.cards.c.CrystallineSliver.class));
        cards.add(new SetCardInfo("Cultivate", 135, Rarity.COMMON, mage.cards.c.Cultivate.class));
        cards.add(new SetCardInfo("Deep Analysis", 81, Rarity.COMMON, mage.cards.d.DeepAnalysis.class));
        cards.add(new SetCardInfo("Desert", 99, Rarity.UNCOMMON, mage.cards.d.Desert.class));
        cards.add(new SetCardInfo("Despise", 141, Rarity.UNCOMMON, mage.cards.d.Despise.class));
        cards.add(new SetCardInfo("Dimir Charm", 159, Rarity.UNCOMMON, mage.cards.d.DimirCharm.class));
        cards.add(new SetCardInfo("Disdainful Stroke", 177, Rarity.COMMON, mage.cards.d.DisdainfulStroke.class));
        cards.add(new SetCardInfo("Disenchant", 31, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Dismember", 143, Rarity.UNCOMMON, mage.cards.d.Dismember.class));
        cards.add(new SetCardInfo("Dissipate", 21, Rarity.UNCOMMON, mage.cards.d.Dissipate.class));
        cards.add(new SetCardInfo("Dissolve", 169, Rarity.UNCOMMON, mage.cards.d.Dissolve.class));
        cards.add(new SetCardInfo("Drain Life", 25, Rarity.COMMON, mage.cards.d.DrainLife.class));
        cards.add(new SetCardInfo("Duress", 65, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Elves of Deep Shadow", 68, Rarity.COMMON, mage.cards.e.ElvesOfDeepShadow.class));
        cards.add(new SetCardInfo("Elvish Mystic", 165, Rarity.COMMON, mage.cards.e.ElvishMystic.class));
        cards.add(new SetCardInfo("Elvish Visionary", 121, Rarity.COMMON, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("Encroaching Wastes", 167, Rarity.UNCOMMON, mage.cards.e.EncroachingWastes.class));
        cards.add(new SetCardInfo("Engineered Plague", 86, Rarity.UNCOMMON, mage.cards.e.EngineeredPlague.class));
        cards.add(new SetCardInfo("Eternal Witness", 94, Rarity.UNCOMMON, mage.cards.e.EternalWitness.class));
        cards.add(new SetCardInfo("Everflowing Chalice", 128, Rarity.UNCOMMON, mage.cards.e.EverflowingChalice.class));
        cards.add(new SetCardInfo("Evolving Wilds", 149, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Experiment One", 160, Rarity.UNCOMMON, mage.cards.e.ExperimentOne.class));
        cards.add(new SetCardInfo("Fact or Fiction", 61, Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Fanatic of Xenagos", 173, Rarity.UNCOMMON, mage.cards.f.FanaticOfXenagos.class));
        cards.add(new SetCardInfo("Farseek", 154, Rarity.COMMON, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Fatal Push", 208, Rarity.SPECIAL, mage.cards.f.FatalPush.class));
        cards.add(new SetCardInfo("Fiery Temper", 198, Rarity.UNCOMMON, mage.cards.f.FieryTemper.class));
        cards.add(new SetCardInfo("Fireblast", 18, Rarity.COMMON, mage.cards.f.Fireblast.class));
        cards.add(new SetCardInfo("Firebolt", 80, Rarity.UNCOMMON, mage.cards.f.Firebolt.class));
        cards.add(new SetCardInfo("Fire // Ice", 79, Rarity.UNCOMMON, mage.cards.f.FireIce.class));
        cards.add(new SetCardInfo("Fireslinger", 24, Rarity.COMMON, mage.cards.f.Fireslinger.class));
        cards.add(new SetCardInfo("Flametongue Kavu", 58, Rarity.UNCOMMON, mage.cards.f.FlametongueKavu.class));
        cards.add(new SetCardInfo("Flaying Tendrils", 196, Rarity.UNCOMMON, mage.cards.f.FlayingTendrils.class));
        cards.add(new SetCardInfo("Forbid", 27, Rarity.UNCOMMON, mage.cards.f.Forbid.class));
        cards.add(new SetCardInfo("Forbidden Alchemy", 146, Rarity.COMMON, mage.cards.f.ForbiddenAlchemy.class));
        cards.add(new SetCardInfo("Force Spike", 91, Rarity.COMMON, mage.cards.f.ForceSpike.class));
        cards.add(new SetCardInfo("Fortune's Favor", 201, Rarity.COMMON, mage.cards.f.FortunesFavor.class));
        cards.add(new SetCardInfo("Frenzied Goblin", 176, Rarity.UNCOMMON, mage.cards.f.FrenziedGoblin.class));
        cards.add(new SetCardInfo("Frost Walker", 181, Rarity.UNCOMMON, mage.cards.f.FrostWalker.class));
        cards.add(new SetCardInfo("Gatekeeper of Malakir", 126, Rarity.UNCOMMON, mage.cards.g.GatekeeperOfMalakir.class));
        cards.add(new SetCardInfo("Gerrard's Verdict", 82, Rarity.UNCOMMON, mage.cards.g.GerrardsVerdict.class));
        cards.add(new SetCardInfo("Ghor-Clan Rampager", 161, Rarity.UNCOMMON, mage.cards.g.GhorClanRampager.class));
        cards.add(new SetCardInfo("Ghostly Prison", 117, Rarity.UNCOMMON, mage.cards.g.GhostlyPrison.class));
        cards.add(new SetCardInfo("Giant Growth", 8, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Gitaxian Probe", 151, Rarity.COMMON, mage.cards.g.GitaxianProbe.class));
        cards.add(new SetCardInfo("Glistener Elf", 140, Rarity.COMMON, mage.cards.g.GlistenerElf.class));
        cards.add(new SetCardInfo("Goblin Bombardment", 37, Rarity.UNCOMMON, mage.cards.g.GoblinBombardment.class));
        cards.add(new SetCardInfo("Goblin Legionnaire", 85, Rarity.COMMON, mage.cards.g.GoblinLegionnaire.class));
        cards.add(new SetCardInfo("Goblin Ringleader", 87, Rarity.UNCOMMON, mage.cards.g.GoblinRingleader.class));
        cards.add(new SetCardInfo("Goblin Warchief", 72, Rarity.UNCOMMON, mage.cards.g.GoblinWarchief.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Goblin Warchief", 192, Rarity.UNCOMMON, mage.cards.g.GoblinWarchief.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Go for the Throat", 138, Rarity.UNCOMMON, mage.cards.g.GoForTheThroat.class));
        cards.add(new SetCardInfo("Grisly Salvage", 162, Rarity.COMMON, mage.cards.g.GrislySalvage.class));
        cards.add(new SetCardInfo("Hordeling Outburst", 178, Rarity.UNCOMMON, mage.cards.h.HordelingOutburst.class));
        cards.add(new SetCardInfo("Icy Manipulator", 67, Rarity.UNCOMMON, mage.cards.i.IcyManipulator.class));
        cards.add(new SetCardInfo("Impulse", 17, Rarity.COMMON, mage.cards.i.Impulse.class));
        cards.add(new SetCardInfo("Incendiary Flow", 202, Rarity.SPECIAL, mage.cards.i.IncendiaryFlow.class));
        cards.add(new SetCardInfo("Isochron Scepter", 102, Rarity.UNCOMMON, mage.cards.i.IsochronScepter.class));
        cards.add(new SetCardInfo("Izzet Charm", 157, Rarity.UNCOMMON, mage.cards.i.IzzetCharm.class));
        cards.add(new SetCardInfo("Jace's Ingenuity", 134, Rarity.UNCOMMON, mage.cards.j.JacesIngenuity.class));
        cards.add(new SetCardInfo("Jackal Pup", 14, Rarity.UNCOMMON, mage.cards.j.JackalPup.class));
        cards.add(new SetCardInfo("Judge's Familiar", 156, Rarity.UNCOMMON, mage.cards.j.JudgesFamiliar.class));
        cards.add(new SetCardInfo("Juggernaut", 62, Rarity.UNCOMMON, mage.cards.j.Juggernaut.class));
        cards.add(new SetCardInfo("Kird Ape", 64, Rarity.COMMON, mage.cards.k.KirdApe.class));
        cards.add(new SetCardInfo("Kitchen Finks", 106, Rarity.UNCOMMON, mage.cards.k.KitchenFinks.class));
        cards.add(new SetCardInfo("Krosan Grip", 123, Rarity.UNCOMMON, mage.cards.k.KrosanGrip.class));
        cards.add(new SetCardInfo("Krosan Tusker", 42, Rarity.COMMON, mage.cards.k.KrosanTusker.class));
        cards.add(new SetCardInfo("Krosan Warchief", 47, Rarity.UNCOMMON, mage.cards.k.KrosanWarchief.class));
        cards.add(new SetCardInfo("Life // Death", 78, Rarity.UNCOMMON, mage.cards.l.LifeDeath.class));
        cards.add(new SetCardInfo("Lightning Greaves", 111, Rarity.UNCOMMON, mage.cards.l.LightningGreaves.class));
        cards.add(new SetCardInfo("Lightning Rift", 48, Rarity.UNCOMMON, mage.cards.l.LightningRift.class));
        cards.add(new SetCardInfo("Lingering Souls", 148, Rarity.UNCOMMON, mage.cards.l.LingeringSouls.class));
        cards.add(new SetCardInfo("Llanowar Elves", 11, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Lobotomy", 71, Rarity.UNCOMMON, mage.cards.l.Lobotomy.class));
        cards.add(new SetCardInfo("Longbow Archer", 3, Rarity.UNCOMMON, mage.cards.l.LongbowArcher.class));
        cards.add(new SetCardInfo("Magma Jet", 104, Rarity.UNCOMMON, mage.cards.m.MagmaJet.class));
        cards.add(new SetCardInfo("Magma Spray", 170, Rarity.COMMON, mage.cards.m.MagmaSpray.class));
        cards.add(new SetCardInfo("Merrow Reejerey", 107, Rarity.UNCOMMON, mage.cards.m.MerrowReejerey.class));
        cards.add(new SetCardInfo("Mind Warp", 5, Rarity.UNCOMMON, mage.cards.m.MindWarp.class));
        cards.add(new SetCardInfo("Mogg Fanatic", 29, Rarity.UNCOMMON, mage.cards.m.MoggFanatic.class));
        cards.add(new SetCardInfo("Mother of Runes", 54, Rarity.UNCOMMON, mage.cards.m.MotherOfRunes.class));
        cards.add(new SetCardInfo("Mulldrifter", 109, Rarity.COMMON, mage.cards.m.Mulldrifter.class));
        cards.add(new SetCardInfo("Murderous Redcap", 110, Rarity.UNCOMMON, mage.cards.m.MurderousRedcap.class));
        cards.add(new SetCardInfo("Muscle Sliver", 33, Rarity.COMMON, mage.cards.m.MuscleSliver.class));
        cards.add(new SetCardInfo("Myr Enforcer", 105, Rarity.COMMON, mage.cards.m.MyrEnforcer.class));
        cards.add(new SetCardInfo("Nissa's Pilgrimage", 188, Rarity.COMMON, mage.cards.n.NissasPilgrimage.class));
        cards.add(new SetCardInfo("Noose Constrictor", 200, Rarity.SPECIAL, mage.cards.n.NooseConstrictor.class));
        cards.add(new SetCardInfo("Oblivion Ring", 114, Rarity.COMMON, mage.cards.o.OblivionRing.class));
        cards.add(new SetCardInfo("Ophidian", 13, Rarity.UNCOMMON, mage.cards.o.Ophidian.class));
        cards.add(new SetCardInfo("Orator of Ojutai", 184, Rarity.UNCOMMON, mage.cards.o.OratorOfOjutai.class));
        cards.add(new SetCardInfo("Path to Exile", 182, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Pendelhaven", 96, Rarity.UNCOMMON, mage.cards.p.Pendelhaven.class));
        cards.add(new SetCardInfo("Pillar of Flame", 150, Rarity.COMMON, mage.cards.p.PillarOfFlame.class));
        cards.add(new SetCardInfo("Priest of Titania", 36, Rarity.COMMON, mage.cards.p.PriestOfTitania.class));
        cards.add(new SetCardInfo("Prodigal Sorcerer", 9, Rarity.COMMON, mage.cards.p.ProdigalSorcerer.class));
        cards.add(new SetCardInfo("Qasali Pridemage", 124, Rarity.COMMON, mage.cards.q.QasaliPridemage.class));
        cards.add(new SetCardInfo("Quirion Ranger", 15, Rarity.COMMON, mage.cards.q.QuirionRanger.class));
        cards.add(new SetCardInfo("Rakdos Cackler", 158, Rarity.UNCOMMON, mage.cards.r.RakdosCackler.class));
        cards.add(new SetCardInfo("Rancor", 56, Rarity.COMMON, mage.cards.r.Rancor.class));
        cards.add(new SetCardInfo("Reanimate", 53, Rarity.UNCOMMON, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Reliquary Tower", 153, Rarity.UNCOMMON, mage.cards.r.ReliquaryTower.class));
        cards.add(new SetCardInfo("Remand", 92, Rarity.UNCOMMON, mage.cards.r.Remand.class));
        cards.add(new SetCardInfo("Renegade Rallier", 207, Rarity.SPECIAL, mage.cards.r.RenegadeRallier.class));
        cards.add(new SetCardInfo("Resurrection", 97, Rarity.UNCOMMON, mage.cards.r.Resurrection.class));
        cards.add(new SetCardInfo("Reverse Engineer", 206, Rarity.SPECIAL, mage.cards.r.ReverseEngineer.class));
        cards.add(new SetCardInfo("Rhox War Monk", 133, Rarity.UNCOMMON, mage.cards.r.RhoxWarMonk.class));
        cards.add(new SetCardInfo("Rift Bolt", 125, Rarity.COMMON, mage.cards.r.RiftBolt.class));
        cards.add(new SetCardInfo("Rise from the Tides", 197, Rarity.UNCOMMON, mage.cards.r.RiseFromTheTides.class));
        cards.add(new SetCardInfo("River Boa", 1, Rarity.UNCOMMON, mage.cards.r.RiverBoa.class));
        cards.add(new SetCardInfo("Roar of the Wurm", 90, Rarity.UNCOMMON, mage.cards.r.RoarOfTheWurm.class));
        cards.add(new SetCardInfo("Roast", 186, Rarity.UNCOMMON, mage.cards.r.Roast.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 115, Rarity.COMMON, mage.cards.s.SakuraTribeElder.class));
        cards.add(new SetCardInfo("Savage Lands", 139, Rarity.UNCOMMON, mage.cards.s.SavageLands.class));
        cards.add(new SetCardInfo("Scragnoth", 38, Rarity.UNCOMMON, mage.cards.s.Scragnoth.class));
        cards.add(new SetCardInfo("Seal of Cleansing", 57, Rarity.COMMON, mage.cards.s.SealOfCleansing.class));
        cards.add(new SetCardInfo("Searing Spear", 152, Rarity.COMMON, mage.cards.s.SearingSpear.class));
        cards.add(new SetCardInfo("Serrated Arrows", 101, Rarity.UNCOMMON, mage.cards.s.SerratedArrows.class));
        cards.add(new SetCardInfo("Serum Visions", 183, Rarity.COMMON, mage.cards.s.SerumVisions.class));
        cards.add(new SetCardInfo("Servo Exhibition", 203, Rarity.SPECIAL, mage.cards.s.ServoExhibition.class));
        cards.add(new SetCardInfo("Shock", 6, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Shrapnel Blast", 103, Rarity.UNCOMMON, mage.cards.s.ShrapnelBlast.class));
        cards.add(new SetCardInfo("Silver Knight", 46, Rarity.UNCOMMON, mage.cards.s.SilverKnight.class));
        cards.add(new SetCardInfo("Sin Collector", 163, Rarity.UNCOMMON, mage.cards.s.SinCollector.class));
        cards.add(new SetCardInfo("Slice and Dice", 45, Rarity.UNCOMMON, mage.cards.s.SliceAndDice.class));
        cards.add(new SetCardInfo("Smash to Smithereens", 190, Rarity.COMMON, mage.cards.s.SmashToSmithereens.class));
        cards.add(new SetCardInfo("Smother", 39, Rarity.UNCOMMON, mage.cards.s.Smother.class));
        cards.add(new SetCardInfo("Soltari Priest", 19, Rarity.UNCOMMON, mage.cards.s.SoltariPriest.class));
        cards.add(new SetCardInfo("Sparksmith", 41, Rarity.COMMON, mage.cards.s.Sparksmith.class));
        cards.add(new SetCardInfo("Spatial Contortion", 194, Rarity.UNCOMMON, mage.cards.s.SpatialContortion.class));
        cards.add(new SetCardInfo("Spellstutter Sprite", 129, Rarity.COMMON, mage.cards.s.SpellstutterSprite.class));
        cards.add(new SetCardInfo("Spike Feeder", 28, Rarity.UNCOMMON, mage.cards.s.SpikeFeeder.class));
        cards.add(new SetCardInfo("Squadron Hawk", 132, Rarity.COMMON, mage.cards.s.SquadronHawk.class));
        cards.add(new SetCardInfo("Staunch Defenders", 7, Rarity.UNCOMMON, mage.cards.s.StaunchDefenders.class));
        cards.add(new SetCardInfo("Stoke the Flames", 175, Rarity.UNCOMMON, mage.cards.s.StokeTheFlames.class));
        cards.add(new SetCardInfo("Stone Rain", 10, Rarity.COMMON, mage.cards.s.StoneRain.class));
        cards.add(new SetCardInfo("Suspension Field", 179, Rarity.UNCOMMON, mage.cards.s.SuspensionField.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 12, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Sylvan Scrying", 193, Rarity.UNCOMMON, mage.cards.s.SylvanScrying.class));
        cards.add(new SetCardInfo("Tectonic Edge", 142, Rarity.UNCOMMON, mage.cards.t.TectonicEdge.class));
        cards.add(new SetCardInfo("Teetering Peaks", 136, Rarity.COMMON, mage.cards.t.TeeteringPeaks.class));
        cards.add(new SetCardInfo("Tendrils of Agony", 95, Rarity.UNCOMMON, mage.cards.t.TendrilsOfAgony.class));
        cards.add(new SetCardInfo("Terminate", 70, Rarity.COMMON, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("Terror", 2, Rarity.COMMON, mage.cards.t.Terror.class));
        cards.add(new SetCardInfo("Thirst for Knowledge", 100, Rarity.UNCOMMON, mage.cards.t.ThirstForKnowledge.class));
        cards.add(new SetCardInfo("Tidehollow Sculler", 116, Rarity.UNCOMMON, mage.cards.t.TidehollowSculler.class));
        cards.add(new SetCardInfo("Tormented Hero", 168, Rarity.UNCOMMON, mage.cards.t.TormentedHero.class));
        cards.add(new SetCardInfo("Tormod's Crypt", 93, Rarity.UNCOMMON, mage.cards.t.TormodsCrypt.class));
        cards.add(new SetCardInfo("Treetop Village", 50, Rarity.UNCOMMON, mage.cards.t.TreetopVillage.class));
        cards.add(new SetCardInfo("Ultimate Price", 185, Rarity.UNCOMMON, mage.cards.u.UltimatePrice.class));
        cards.add(new SetCardInfo("Unlicensed Disintegration", 204, Rarity.SPECIAL, mage.cards.u.UnlicensedDisintegration.class));
        cards.add(new SetCardInfo("Volcanic Geyser", 4, Rarity.UNCOMMON, mage.cards.v.VolcanicGeyser.class));
        cards.add(new SetCardInfo("Wall of Blossoms", 23, Rarity.UNCOMMON, mage.cards.w.WallOfBlossoms.class));
        cards.add(new SetCardInfo("Wall of Omens", 130, Rarity.UNCOMMON, mage.cards.w.WallOfOmens.class));
        cards.add(new SetCardInfo("Wall of Roots", 98, Rarity.COMMON, mage.cards.w.WallOfRoots.class));
        cards.add(new SetCardInfo("Warleader's Helix", 164, Rarity.UNCOMMON, mage.cards.w.WarleadersHelix.class));
        cards.add(new SetCardInfo("Watchwolf", 112, Rarity.UNCOMMON, mage.cards.w.Watchwolf.class));
        cards.add(new SetCardInfo("Whipcorder", 40, Rarity.UNCOMMON, mage.cards.w.Whipcorder.class));
        cards.add(new SetCardInfo("White Knight", 30, Rarity.UNCOMMON, mage.cards.w.WhiteKnight.class));
        cards.add(new SetCardInfo("Wild Mongrel", 73, Rarity.COMMON, mage.cards.w.WildMongrel.class));
        cards.add(new SetCardInfo("Wild Nacatl", 127, Rarity.COMMON, mage.cards.w.WildNacatl.class));
        cards.add(new SetCardInfo("Willbender", 44, Rarity.UNCOMMON, mage.cards.w.Willbender.class));
        cards.add(new SetCardInfo("Wing Shards", 88, Rarity.UNCOMMON, mage.cards.w.WingShards.class));
        cards.add(new SetCardInfo("Withered Wretch", 43, Rarity.UNCOMMON, mage.cards.w.WitheredWretch.class));
        cards.add(new SetCardInfo("Wonder", 84, Rarity.UNCOMMON, mage.cards.w.Wonder.class));
        cards.add(new SetCardInfo("Wren's Run Vanquisher", 108, Rarity.UNCOMMON, mage.cards.w.WrensRunVanquisher.class));
    }

}
