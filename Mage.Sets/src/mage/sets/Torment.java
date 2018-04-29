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

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public class Torment extends ExpansionSet {

    private static final Torment instance = new Torment();

    public static Torment getInstance() {
        return instance;
    }

    private Torment() {
        super("Torment", "TOR", ExpansionSet.buildDate(2002, 1, 26), SetType.EXPANSION);
        this.blockName = "Odyssey";
        this.parentSet = Odyssey.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Accelerate", 90, Rarity.COMMON, mage.cards.a.Accelerate.class));
        cards.add(new SetCardInfo("Acorn Harvest", 118, Rarity.COMMON, mage.cards.a.AcornHarvest.class));
        cards.add(new SetCardInfo("Ambassador Laquatus", 23, Rarity.RARE, mage.cards.a.AmbassadorLaquatus.class));
        cards.add(new SetCardInfo("Angel of Retribution", 1, Rarity.RARE, mage.cards.a.AngelOfRetribution.class));
        cards.add(new SetCardInfo("Anurid Scavenger", 119, Rarity.COMMON, mage.cards.a.AnuridScavenger.class));
        cards.add(new SetCardInfo("Aquamoeba", 24, Rarity.COMMON, mage.cards.a.Aquamoeba.class));
        cards.add(new SetCardInfo("Arrogant Wurm", 120, Rarity.UNCOMMON, mage.cards.a.ArrogantWurm.class));
        cards.add(new SetCardInfo("Aven Trooper", 2, Rarity.COMMON, mage.cards.a.AvenTrooper.class));
        cards.add(new SetCardInfo("Balshan Collaborator", 25, Rarity.UNCOMMON, mage.cards.b.BalshanCollaborator.class));
        cards.add(new SetCardInfo("Balthor the Stout", 91, Rarity.RARE, mage.cards.b.BalthorTheStout.class));
        cards.add(new SetCardInfo("Barbarian Outcast", 92, Rarity.COMMON, mage.cards.b.BarbarianOutcast.class));
        cards.add(new SetCardInfo("Basking Rootwalla", 121, Rarity.COMMON, mage.cards.b.BaskingRootwalla.class));
        cards.add(new SetCardInfo("Boneshard Slasher", 50, Rarity.UNCOMMON, mage.cards.b.BoneshardSlasher.class));
        cards.add(new SetCardInfo("Breakthrough", 26, Rarity.UNCOMMON, mage.cards.b.Breakthrough.class));
        cards.add(new SetCardInfo("Cabal Coffers", 139, Rarity.UNCOMMON, mage.cards.c.CabalCoffers.class));
        cards.add(new SetCardInfo("Cabal Ritual", 51, Rarity.COMMON, mage.cards.c.CabalRitual.class));
        cards.add(new SetCardInfo("Cabal Surgeon", 52, Rarity.COMMON, mage.cards.c.CabalSurgeon.class));
        cards.add(new SetCardInfo("Cabal Torturer", 53, Rarity.COMMON, mage.cards.c.CabalTorturer.class));
        cards.add(new SetCardInfo("Centaur Chieftain", 122, Rarity.UNCOMMON, mage.cards.c.CentaurChieftain.class));
        cards.add(new SetCardInfo("Centaur Veteran", 123, Rarity.COMMON, mage.cards.c.CentaurVeteran.class));
        cards.add(new SetCardInfo("Cephalid Aristocrat", 27, Rarity.COMMON, mage.cards.c.CephalidAristocrat.class));
        cards.add(new SetCardInfo("Cephalid Illusionist", 28, Rarity.UNCOMMON, mage.cards.c.CephalidIllusionist.class));
        cards.add(new SetCardInfo("Cephalid Sage", 29, Rarity.UNCOMMON, mage.cards.c.CephalidSage.class));
        cards.add(new SetCardInfo("Cephalid Snitch", 30, Rarity.COMMON, mage.cards.c.CephalidSnitch.class));
        cards.add(new SetCardInfo("Cephalid Vandal", 31, Rarity.RARE, mage.cards.c.CephalidVandal.class));
        cards.add(new SetCardInfo("Chainer, Dementia Master", 56, Rarity.RARE, mage.cards.c.ChainerDementiaMaster.class));
        cards.add(new SetCardInfo("Chainer's Edict", 57, Rarity.UNCOMMON, mage.cards.c.ChainersEdict.class));
        cards.add(new SetCardInfo("Churning Eddy", 32, Rarity.COMMON, mage.cards.c.ChurningEddy.class));
        cards.add(new SetCardInfo("Circular Logic", 33, Rarity.UNCOMMON, mage.cards.c.CircularLogic.class));
        cards.add(new SetCardInfo("Cleansing Meditation", 3, Rarity.UNCOMMON, mage.cards.c.CleansingMeditation.class));
        cards.add(new SetCardInfo("Compulsion", 34, Rarity.UNCOMMON, mage.cards.c.Compulsion.class));
        cards.add(new SetCardInfo("Coral Net", 35, Rarity.COMMON, mage.cards.c.CoralNet.class));
        cards.add(new SetCardInfo("Crackling Club", 93, Rarity.COMMON, mage.cards.c.CracklingClub.class));
        cards.add(new SetCardInfo("Crazed Firecat", 94, Rarity.UNCOMMON, mage.cards.c.CrazedFirecat.class));
        cards.add(new SetCardInfo("Crippling Fatigue", 58, Rarity.COMMON, mage.cards.c.CripplingFatigue.class));
        cards.add(new SetCardInfo("Dawn of the Dead", 59, Rarity.RARE, mage.cards.d.DawnOfTheDead.class));
        cards.add(new SetCardInfo("Deep Analysis", 36, Rarity.COMMON, mage.cards.d.DeepAnalysis.class));
        cards.add(new SetCardInfo("Devastating Dreams", 95, Rarity.RARE, mage.cards.d.DevastatingDreams.class));
        cards.add(new SetCardInfo("Dwell on the Past", 124, Rarity.UNCOMMON, mage.cards.d.DwellOnThePast.class));
        cards.add(new SetCardInfo("Enslaved Dwarf", 96, Rarity.COMMON, mage.cards.e.EnslavedDwarf.class));
        cards.add(new SetCardInfo("Equal Treatment", 4, Rarity.UNCOMMON, mage.cards.e.EqualTreatment.class));
        cards.add(new SetCardInfo("Faceless Butcher", 60, Rarity.COMMON, mage.cards.f.FacelessButcher.class));
        cards.add(new SetCardInfo("False Memories", 37, Rarity.RARE, mage.cards.f.FalseMemories.class));
        cards.add(new SetCardInfo("Far Wanderings", 125, Rarity.COMMON, mage.cards.f.FarWanderings.class));
        cards.add(new SetCardInfo("Fiery Temper", 97, Rarity.COMMON, mage.cards.f.FieryTemper.class));
        cards.add(new SetCardInfo("Flash of Defiance", 99, Rarity.COMMON, mage.cards.f.FlashOfDefiance.class));
        cards.add(new SetCardInfo("Frantic Purification", 6, Rarity.COMMON, mage.cards.f.FranticPurification.class));
        cards.add(new SetCardInfo("Ghostly Wings", 38, Rarity.COMMON, mage.cards.g.GhostlyWings.class));
        cards.add(new SetCardInfo("Gloomdrifter", 61, Rarity.UNCOMMON, mage.cards.g.Gloomdrifter.class));
        cards.add(new SetCardInfo("Gravegouger", 62, Rarity.COMMON, mage.cards.g.Gravegouger.class));
        cards.add(new SetCardInfo("Grim Lavamancer", 100, Rarity.RARE, mage.cards.g.GrimLavamancer.class));
        cards.add(new SetCardInfo("Grotesque Hybrid", 63, Rarity.UNCOMMON, mage.cards.g.GrotesqueHybrid.class));
        cards.add(new SetCardInfo("Gurzigost", 126, Rarity.RARE, mage.cards.g.Gurzigost.class));
        cards.add(new SetCardInfo("Hell-Bent Raider", 101, Rarity.RARE, mage.cards.h.HellBentRaider.class));
        cards.add(new SetCardInfo("Hydromorph Guardian", 39, Rarity.COMMON, mage.cards.h.HydromorphGuardian.class));
        cards.add(new SetCardInfo("Hydromorph Gull", 40, Rarity.UNCOMMON, mage.cards.h.HydromorphGull.class));
        cards.add(new SetCardInfo("Hypochondria", 7, Rarity.UNCOMMON, mage.cards.h.Hypochondria.class));
        cards.add(new SetCardInfo("Hypnox", 64, Rarity.RARE, mage.cards.h.Hypnox.class));
        cards.add(new SetCardInfo("Ichorid", 65, Rarity.RARE, mage.cards.i.Ichorid.class));
        cards.add(new SetCardInfo("Insidious Dreams", 66, Rarity.RARE, mage.cards.i.InsidiousDreams.class));
        cards.add(new SetCardInfo("Insist", 127, Rarity.RARE, mage.cards.i.Insist.class));
        cards.add(new SetCardInfo("Invigorating Falls", 128, Rarity.COMMON, mage.cards.i.InvigoratingFalls.class));
        cards.add(new SetCardInfo("Kamahl's Sledge", 102, Rarity.COMMON, mage.cards.k.KamahlsSledge.class));
        cards.add(new SetCardInfo("Krosan Constrictor", 129, Rarity.COMMON, mage.cards.k.KrosanConstrictor.class));
        cards.add(new SetCardInfo("Krosan Restorer", 130, Rarity.COMMON, mage.cards.k.KrosanRestorer.class));
        cards.add(new SetCardInfo("Laquatus's Champion", 67, Rarity.RARE, mage.cards.l.LaquatussChampion.class));
        cards.add(new SetCardInfo("Last Laugh", 68, Rarity.RARE, mage.cards.l.LastLaugh.class));
        cards.add(new SetCardInfo("Liquify", 41, Rarity.COMMON, mage.cards.l.Liquify.class));
        cards.add(new SetCardInfo("Llawan, Cephalid Empress", 42, Rarity.RARE, mage.cards.l.LlawanCephalidEmpress.class));
        cards.add(new SetCardInfo("Longhorn Firebeast", 103, Rarity.COMMON, mage.cards.l.LonghornFirebeast.class));
        cards.add(new SetCardInfo("Major Teroh", 8, Rarity.RARE, mage.cards.m.MajorTeroh.class));
        cards.add(new SetCardInfo("Mesmeric Fiend", 69, Rarity.COMMON, mage.cards.m.MesmericFiend.class));
        cards.add(new SetCardInfo("Militant Monk", 9, Rarity.COMMON, mage.cards.m.MilitantMonk.class));
        cards.add(new SetCardInfo("Mind Sludge", 70, Rarity.UNCOMMON, mage.cards.m.MindSludge.class));
        cards.add(new SetCardInfo("Morningtide", 10, Rarity.RARE, mage.cards.m.Morningtide.class));
        cards.add(new SetCardInfo("Mortal Combat", 71, Rarity.RARE, mage.cards.m.MortalCombat.class));
        cards.add(new SetCardInfo("Mortiphobia", 72, Rarity.UNCOMMON, mage.cards.m.Mortiphobia.class));
        cards.add(new SetCardInfo("Mutilate", 73, Rarity.RARE, mage.cards.m.Mutilate.class));
        cards.add(new SetCardInfo("Mystic Familiar", 11, Rarity.COMMON, mage.cards.m.MysticFamiliar.class));
        cards.add(new SetCardInfo("Nantuko Blightcutter", 131, Rarity.RARE, mage.cards.n.NantukoBlightcutter.class));
        cards.add(new SetCardInfo("Nantuko Calmer", 132, Rarity.COMMON, mage.cards.n.NantukoCalmer.class));
        cards.add(new SetCardInfo("Nantuko Cultivator", 133, Rarity.RARE, mage.cards.n.NantukoCultivator.class));
        cards.add(new SetCardInfo("Nantuko Shade", 74, Rarity.RARE, mage.cards.n.NantukoShade.class));
        cards.add(new SetCardInfo("Narcissism", 134, Rarity.UNCOMMON, mage.cards.n.Narcissism.class));
        cards.add(new SetCardInfo("Nostalgic Dreams", 135, Rarity.RARE, mage.cards.n.NostalgicDreams.class));
        cards.add(new SetCardInfo("Obsessive Search", 43, Rarity.COMMON, mage.cards.o.ObsessiveSearch.class));
        cards.add(new SetCardInfo("Organ Grinder", 75, Rarity.COMMON, mage.cards.o.OrganGrinder.class));
        cards.add(new SetCardInfo("Overmaster", 104, Rarity.RARE, mage.cards.o.Overmaster.class));
        cards.add(new SetCardInfo("Parallel Evolution", 136, Rarity.RARE, mage.cards.p.ParallelEvolution.class));
        cards.add(new SetCardInfo("Pardic Arsonist", 105, Rarity.UNCOMMON, mage.cards.p.PardicArsonist.class));
        cards.add(new SetCardInfo("Pardic Collaborator", 106, Rarity.UNCOMMON, mage.cards.p.PardicCollaborator.class));
        cards.add(new SetCardInfo("Pardic Lancer", 107, Rarity.COMMON, mage.cards.p.PardicLancer.class));
        cards.add(new SetCardInfo("Pay No Heed", 12, Rarity.COMMON, mage.cards.p.PayNoHeed.class));
        cards.add(new SetCardInfo("Petradon", 108, Rarity.RARE, mage.cards.p.Petradon.class));
        cards.add(new SetCardInfo("Petravark", 109, Rarity.COMMON, mage.cards.p.Petravark.class));
        cards.add(new SetCardInfo("Pitchstone Wall", 110, Rarity.UNCOMMON, mage.cards.p.PitchstoneWall.class));
        cards.add(new SetCardInfo("Plagiarize", 44, Rarity.RARE, mage.cards.p.Plagiarize.class));
        cards.add(new SetCardInfo("Possessed Aven", 45, Rarity.RARE, mage.cards.p.PossessedAven.class));
        cards.add(new SetCardInfo("Possessed Barbarian", 111, Rarity.RARE, mage.cards.p.PossessedBarbarian.class));
        cards.add(new SetCardInfo("Possessed Centaur", 137, Rarity.RARE, mage.cards.p.PossessedCentaur.class));
        cards.add(new SetCardInfo("Possessed Nomad", 13, Rarity.RARE, mage.cards.p.PossessedNomad.class));
        cards.add(new SetCardInfo("Psychotic Haze", 76, Rarity.COMMON, mage.cards.p.PsychoticHaze.class));
        cards.add(new SetCardInfo("Putrid Imp", 77, Rarity.COMMON, mage.cards.p.PutridImp.class));
        cards.add(new SetCardInfo("Pyromania", 112, Rarity.UNCOMMON, mage.cards.p.Pyromania.class));
        cards.add(new SetCardInfo("Radiate", 113, Rarity.RARE, mage.cards.r.Radiate.class));
        cards.add(new SetCardInfo("Rancid Earth", 78, Rarity.COMMON, mage.cards.r.RancidEarth.class));
        cards.add(new SetCardInfo("Reborn Hero", 14, Rarity.RARE, mage.cards.r.RebornHero.class));
        cards.add(new SetCardInfo("Restless Dreams", 79, Rarity.COMMON, mage.cards.r.RestlessDreams.class));
        cards.add(new SetCardInfo("Sengir Vampire", 80, Rarity.RARE, mage.cards.s.SengirVampire.class));
        cards.add(new SetCardInfo("Seton's Scout", 138, Rarity.UNCOMMON, mage.cards.s.SetonsScout.class));
        cards.add(new SetCardInfo("Shade's Form", 81, Rarity.COMMON, mage.cards.s.ShadesForm.class));
        cards.add(new SetCardInfo("Shambling Swarm", 82, Rarity.RARE, mage.cards.s.ShamblingSwarm.class));
        cards.add(new SetCardInfo("Sickening Dreams", 83, Rarity.UNCOMMON, mage.cards.s.SickeningDreams.class));
        cards.add(new SetCardInfo("Skullscorch", 114, Rarity.RARE, mage.cards.s.Skullscorch.class));
        cards.add(new SetCardInfo("Skywing Aven", 47, Rarity.COMMON, mage.cards.s.SkywingAven.class));
        cards.add(new SetCardInfo("Slithery Stalker", 84, Rarity.UNCOMMON, mage.cards.s.SlitheryStalker.class));
        cards.add(new SetCardInfo("Sonic Seizure", 115, Rarity.COMMON, mage.cards.s.SonicSeizure.class));
        cards.add(new SetCardInfo("Soul Scourge", 85, Rarity.COMMON, mage.cards.s.SoulScourge.class));
        cards.add(new SetCardInfo("Stern Judge", 16, Rarity.UNCOMMON, mage.cards.s.SternJudge.class));
        cards.add(new SetCardInfo("Strength of Isolation", 17, Rarity.UNCOMMON, mage.cards.s.StrengthOfIsolation.class));
        cards.add(new SetCardInfo("Strength of Lunacy", 86, Rarity.UNCOMMON, mage.cards.s.StrengthOfLunacy.class));
        cards.add(new SetCardInfo("Stupefying Touch", 48, Rarity.UNCOMMON, mage.cards.s.StupefyingTouch.class));
        cards.add(new SetCardInfo("Tainted Field", 140, Rarity.UNCOMMON, mage.cards.t.TaintedField.class));
        cards.add(new SetCardInfo("Tainted Isle", 141, Rarity.UNCOMMON, mage.cards.t.TaintedIsle.class));
        cards.add(new SetCardInfo("Tainted Peak", 142, Rarity.UNCOMMON, mage.cards.t.TaintedPeak.class));
        cards.add(new SetCardInfo("Tainted Wood", 143, Rarity.UNCOMMON, mage.cards.t.TaintedWood.class));
        cards.add(new SetCardInfo("Temporary Insanity", 116, Rarity.UNCOMMON, mage.cards.t.TemporaryInsanity.class));
        cards.add(new SetCardInfo("Teroh's Faithful", 18, Rarity.COMMON, mage.cards.t.TerohsFaithful.class));
        cards.add(new SetCardInfo("Teroh's Vanguard", 19, Rarity.UNCOMMON, mage.cards.t.TerohsVanguard.class));
        cards.add(new SetCardInfo("Transcendence", 20, Rarity.RARE, mage.cards.t.Transcendence.class));
        cards.add(new SetCardInfo("Turbulent Dreams", 49, Rarity.RARE, mage.cards.t.TurbulentDreams.class));
        cards.add(new SetCardInfo("Unhinge", 87, Rarity.COMMON, mage.cards.u.Unhinge.class));
        cards.add(new SetCardInfo("Vengeful Dreams", 21, Rarity.RARE, mage.cards.v.VengefulDreams.class));
        cards.add(new SetCardInfo("Violent Eruption", 117, Rarity.UNCOMMON, mage.cards.v.ViolentEruption.class));
        cards.add(new SetCardInfo("Waste Away", 88, Rarity.COMMON, mage.cards.w.WasteAway.class));
        cards.add(new SetCardInfo("Zombie Trailblazer", 89, Rarity.UNCOMMON, mage.cards.z.ZombieTrailblazer.class));
    }
}
