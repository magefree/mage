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
public class Visions extends ExpansionSet {

    private static final Visions instance = new Visions();

    public static Visions getInstance() {
        return instance;
    }

    private Visions() {
        super("Visions", "VIS", ExpansionSet.buildDate(1997, 1, 11), SetType.EXPANSION);
        this.blockName = "Mirage";
        this.parentSet = Mirage.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Anvil of Bogardan", 141, Rarity.RARE, mage.cards.a.AnvilOfBogardan.class));
        cards.add(new SetCardInfo("Archangel", 101, Rarity.RARE, mage.cards.a.Archangel.class));
        cards.add(new SetCardInfo("Army Ants", 126, Rarity.UNCOMMON, mage.cards.a.ArmyAnts.class));
        cards.add(new SetCardInfo("Betrayal", 26, Rarity.COMMON, mage.cards.b.Betrayal.class));
        cards.add(new SetCardInfo("Blanket of Night", 2, Rarity.UNCOMMON, mage.cards.b.BlanketOfNight.class));
        cards.add(new SetCardInfo("Breezekeeper", 27, Rarity.COMMON, mage.cards.b.Breezekeeper.class));
        cards.add(new SetCardInfo("Bull Elephant", 51, Rarity.COMMON, mage.cards.b.BullElephant.class));
        cards.add(new SetCardInfo("Chronatog", 28, Rarity.RARE, mage.cards.c.Chronatog.class));
        cards.add(new SetCardInfo("City of Solitude", 52, Rarity.RARE, mage.cards.c.CityOfSolitude.class));
        cards.add(new SetCardInfo("Cloud Elemental", 29, Rarity.COMMON, mage.cards.c.CloudElemental.class));
        cards.add(new SetCardInfo("Coercion", 4, Rarity.COMMON, mage.cards.c.Coercion.class));
        cards.add(new SetCardInfo("Coral Atoll", 160, Rarity.UNCOMMON, mage.cards.c.CoralAtoll.class));
        cards.add(new SetCardInfo("Creeping Mold", 53, Rarity.UNCOMMON, mage.cards.c.CreepingMold.class));
        cards.add(new SetCardInfo("Crypt Rats", 5, Rarity.COMMON, mage.cards.c.CryptRats.class));
        cards.add(new SetCardInfo("Daraja Griffin", 102, Rarity.UNCOMMON, mage.cards.d.DarajaGriffin.class));
        cards.add(new SetCardInfo("Death Watch", 7, Rarity.COMMON, mage.cards.d.DeathWatch.class));
        cards.add(new SetCardInfo("Desertion", 30, Rarity.RARE, mage.cards.d.Desertion.class));
        cards.add(new SetCardInfo("Diamond Kaleidoscope", 143, Rarity.RARE, mage.cards.d.DiamondKaleidoscope.class));
        cards.add(new SetCardInfo("Dormant Volcano", 161, Rarity.UNCOMMON, mage.cards.d.DormantVolcano.class));
        cards.add(new SetCardInfo("Dragon Mask", 144, Rarity.UNCOMMON, mage.cards.d.DragonMask.class));
        cards.add(new SetCardInfo("Dwarven Vigilantes", 77, Rarity.COMMON, mage.cards.d.DwarvenVigilantes.class));
        cards.add(new SetCardInfo("Elephant Grass", 54, Rarity.UNCOMMON, mage.cards.e.ElephantGrass.class));
        cards.add(new SetCardInfo("Elven Cache", 55, Rarity.COMMON, mage.cards.e.ElvenCache.class));
        cards.add(new SetCardInfo("Emerald Charm", 56, Rarity.COMMON, mage.cards.e.EmeraldCharm.class));
        cards.add(new SetCardInfo("Equipoise", 103, Rarity.RARE, mage.cards.e.Equipoise.class));
        cards.add(new SetCardInfo("Everglades", 162, Rarity.UNCOMMON, mage.cards.e.Everglades.class));
        cards.add(new SetCardInfo("Eye of Singularity", 104, Rarity.RARE, mage.cards.e.EyeOfSingularity.class));
        cards.add(new SetCardInfo("Fallen Askari", 9, Rarity.COMMON, mage.cards.f.FallenAskari.class));
        cards.add(new SetCardInfo("Femeref Enchantress", 129, Rarity.RARE, mage.cards.f.FemerefEnchantress.class));
        cards.add(new SetCardInfo("Feral Instinct", 57, Rarity.COMMON, mage.cards.f.FeralInstinct.class));
        cards.add(new SetCardInfo("Fireblast", 79, Rarity.COMMON, mage.cards.f.Fireblast.class));
        cards.add(new SetCardInfo("Firestorm Hellkite", 130, Rarity.RARE, mage.cards.f.FirestormHellkite.class));
        cards.add(new SetCardInfo("Flooded Shoreline", 32, Rarity.RARE, mage.cards.f.FloodedShoreline.class));
        cards.add(new SetCardInfo("Freewind Falcon", 105, Rarity.COMMON, mage.cards.f.FreewindFalcon.class));
        cards.add(new SetCardInfo("Funeral Charm", 11, Rarity.COMMON, mage.cards.f.FuneralCharm.class));
        cards.add(new SetCardInfo("Giant Caterpillar", 58, Rarity.COMMON, mage.cards.g.GiantCaterpillar.class));
        cards.add(new SetCardInfo("Goblin Recruiter", 80, Rarity.UNCOMMON, mage.cards.g.GoblinRecruiter.class));
        cards.add(new SetCardInfo("Goblin Swine-Rider", 81, Rarity.COMMON, mage.cards.g.GoblinSwineRider.class));
        cards.add(new SetCardInfo("Gossamer Chains", 106, Rarity.COMMON, mage.cards.g.GossamerChains.class));
        cards.add(new SetCardInfo("Griffin Canyon", 163, Rarity.RARE, mage.cards.g.GriffinCanyon.class));
        cards.add(new SetCardInfo("Hearth Charm", 82, Rarity.COMMON, mage.cards.h.HearthCharm.class));
        cards.add(new SetCardInfo("Helm of Awakening", 145, Rarity.UNCOMMON, mage.cards.h.HelmOfAwakening.class));
        cards.add(new SetCardInfo("Hope Charm", 108, Rarity.COMMON, mage.cards.h.HopeCharm.class));
        cards.add(new SetCardInfo("Hulking Cyclops", 84, Rarity.UNCOMMON, mage.cards.h.HulkingCyclops.class));
        cards.add(new SetCardInfo("Impulse", 34, Rarity.COMMON, mage.cards.i.Impulse.class));
        cards.add(new SetCardInfo("Infantry Veteran", 109, Rarity.COMMON, mage.cards.i.InfantryVeteran.class));
        cards.add(new SetCardInfo("Inspiration", 35, Rarity.COMMON, mage.cards.i.Inspiration.class));
        cards.add(new SetCardInfo("Jamuraan Lion", 110, Rarity.COMMON, mage.cards.j.JamuraanLion.class));
        cards.add(new SetCardInfo("Jungle Basin", 164, Rarity.UNCOMMON, mage.cards.j.JungleBasin.class));
        cards.add(new SetCardInfo("Kaervek's Spite", 13, Rarity.RARE, mage.cards.k.KaerveksSpite.class));
        cards.add(new SetCardInfo("Karoo", 165, Rarity.UNCOMMON, mage.cards.k.Karoo.class));
        cards.add(new SetCardInfo("Keeper of Kookus", 85, Rarity.COMMON, mage.cards.k.KeeperOfKookus.class));
        cards.add(new SetCardInfo("King Cheetah", 60, Rarity.COMMON, mage.cards.k.KingCheetah.class));
        cards.add(new SetCardInfo("Knight of Valor", 111, Rarity.COMMON, mage.cards.k.KnightOfValor.class));
        cards.add(new SetCardInfo("Lightning Cloud", 87, Rarity.RARE, mage.cards.l.LightningCloud.class));
        cards.add(new SetCardInfo("Longbow Archer", 112, Rarity.UNCOMMON, mage.cards.l.LongbowArcher.class));
        cards.add(new SetCardInfo("Magma Mine", 149, Rarity.UNCOMMON, mage.cards.m.MagmaMine.class));
        cards.add(new SetCardInfo("Man-o'-War", 37, Rarity.COMMON, mage.cards.m.ManOWar.class));
        cards.add(new SetCardInfo("Miraculous Recovery", 113, Rarity.UNCOMMON, mage.cards.m.MiraculousRecovery.class));
        cards.add(new SetCardInfo("Mortal Wound", 63, Rarity.COMMON, mage.cards.m.MortalWound.class));
        cards.add(new SetCardInfo("Mundungu", 132, Rarity.UNCOMMON, mage.cards.m.Mundungu.class));
        cards.add(new SetCardInfo("Mystic Veil", 38, Rarity.COMMON, mage.cards.m.MysticVeil.class));
        cards.add(new SetCardInfo("Natural Order", 64, Rarity.RARE, mage.cards.n.NaturalOrder.class));
        cards.add(new SetCardInfo("Necromancy", 14, Rarity.UNCOMMON, mage.cards.n.Necromancy.class));
        cards.add(new SetCardInfo("Necrosavant", 15, Rarity.RARE, mage.cards.n.Necrosavant.class));
        cards.add(new SetCardInfo("Nekrataal", 16, Rarity.UNCOMMON, mage.cards.n.Nekrataal.class));
        cards.add(new SetCardInfo("Panther Warriors", 65, Rarity.COMMON, mage.cards.p.PantherWarriors.class));
        cards.add(new SetCardInfo("Parapet", 114, Rarity.COMMON, mage.cards.p.Parapet.class));
        cards.add(new SetCardInfo("Phyrexian Marauder", 151, Rarity.RARE, mage.cards.p.PhyrexianMarauder.class));
        cards.add(new SetCardInfo("Phyrexian Walker", 152, Rarity.COMMON, mage.cards.p.PhyrexianWalker.class));
        cards.add(new SetCardInfo("Prosperity", 40, Rarity.UNCOMMON, mage.cards.p.Prosperity.class));
        cards.add(new SetCardInfo("Python", 18, Rarity.COMMON, mage.cards.p.Python.class));
        cards.add(new SetCardInfo("Quicksand", 166, Rarity.UNCOMMON, mage.cards.q.Quicksand.class));
        cards.add(new SetCardInfo("Quirion Ranger", 67, Rarity.COMMON, mage.cards.q.QuirionRanger.class));
        cards.add(new SetCardInfo("Raging Gorilla", 90, Rarity.COMMON, mage.cards.r.RagingGorilla.class));
        cards.add(new SetCardInfo("Rainbow Efreet", 41, Rarity.RARE, mage.cards.r.RainbowEfreet.class));
        cards.add(new SetCardInfo("Relentless Assault", 91, Rarity.RARE, mage.cards.r.RelentlessAssault.class));
        cards.add(new SetCardInfo("Relic Ward", 116, Rarity.UNCOMMON, mage.cards.r.RelicWard.class));
        cards.add(new SetCardInfo("Remedy", 117, Rarity.COMMON, mage.cards.r.Remedy.class));
        cards.add(new SetCardInfo("Resistance Fighter", 118, Rarity.COMMON, mage.cards.r.ResistanceFighter.class));
        cards.add(new SetCardInfo("Retribution of the Meek", 119, Rarity.RARE, mage.cards.r.RetributionOfTheMeek.class));
        cards.add(new SetCardInfo("Righteous Aura", 120, Rarity.COMMON, mage.cards.r.RighteousAura.class));
        cards.add(new SetCardInfo("River Boa", 68, Rarity.COMMON, mage.cards.r.RiverBoa.class));
        cards.add(new SetCardInfo("Rowen", 69, Rarity.RARE, mage.cards.r.Rowen.class));
        cards.add(new SetCardInfo("Scalebane's Elite", 135, Rarity.UNCOMMON, mage.cards.s.ScalebanesElite.class));
        cards.add(new SetCardInfo("Shrieking Drake", 43, Rarity.COMMON, mage.cards.s.ShriekingDrake.class));
        cards.add(new SetCardInfo("Simoon", 136, Rarity.UNCOMMON, mage.cards.s.Simoon.class));
        cards.add(new SetCardInfo("Sisay's Ring", 154, Rarity.COMMON, mage.cards.s.SisaysRing.class));
        cards.add(new SetCardInfo("Snake Basket", 155, Rarity.RARE, mage.cards.s.SnakeBasket.class));
        cards.add(new SetCardInfo("Solfatara", 93, Rarity.COMMON, mage.cards.s.Solfatara.class));
        cards.add(new SetCardInfo("Spider Climb", 70, Rarity.COMMON, mage.cards.s.SpiderClimb.class));
        cards.add(new SetCardInfo("Spitting Drake", 95, Rarity.UNCOMMON, mage.cards.s.SpittingDrake.class));
        cards.add(new SetCardInfo("Squandered Resources", 137, Rarity.RARE, mage.cards.s.SquanderedResources.class));
        cards.add(new SetCardInfo("Stampeding Wildebeests", 71, Rarity.UNCOMMON, mage.cards.s.StampedingWildebeests.class));
        cards.add(new SetCardInfo("Summer Bloom", 72, Rarity.UNCOMMON, mage.cards.s.SummerBloom.class));
        cards.add(new SetCardInfo("Sun Clasp", 121, Rarity.COMMON, mage.cards.s.SunClasp.class));
        cards.add(new SetCardInfo("Suq'Ata Assassin", 19, Rarity.UNCOMMON, mage.cards.s.SuqAtaAssassin.class));
        cards.add(new SetCardInfo("Suq'Ata Lancer", 96, Rarity.COMMON, mage.cards.s.SuqAtaLancer.class));
        cards.add(new SetCardInfo("Talruum Champion", 97, Rarity.COMMON, mage.cards.t.TalruumChampion.class));
        cards.add(new SetCardInfo("Talruum Piper", 98, Rarity.UNCOMMON, mage.cards.t.TalruumPiper.class));
        cards.add(new SetCardInfo("Tar Pit Warrior", 20, Rarity.COMMON, mage.cards.t.TarPitWarrior.class));
        cards.add(new SetCardInfo("Teferi's Honor Guard", 122, Rarity.UNCOMMON, mage.cards.t.TeferisHonorGuard.class));
        cards.add(new SetCardInfo("Teferi's Puzzle Box", 156, Rarity.RARE, mage.cards.t.TeferisPuzzleBox.class));
        cards.add(new SetCardInfo("Teferi's Realm", 44, Rarity.RARE, mage.cards.t.TeferisRealm.class));
        cards.add(new SetCardInfo("Tempest Drake", 139, Rarity.UNCOMMON, mage.cards.t.TempestDrake.class));
        cards.add(new SetCardInfo("Tithe", 123, Rarity.RARE, mage.cards.t.Tithe.class));
        cards.add(new SetCardInfo("Tremor", 99, Rarity.COMMON, mage.cards.t.Tremor.class));
        cards.add(new SetCardInfo("Triangle of War", 158, Rarity.RARE, mage.cards.t.TriangleOfWar.class));
        cards.add(new SetCardInfo("Uktabi Orangutan", 73, Rarity.UNCOMMON, mage.cards.u.UktabiOrangutan.class));
        cards.add(new SetCardInfo("Undiscovered Paradise", 167, Rarity.RARE, mage.cards.u.UndiscoveredParadise.class));
        cards.add(new SetCardInfo("Undo", 47, Rarity.COMMON, mage.cards.u.Undo.class));
        cards.add(new SetCardInfo("Urborg Mindsucker", 21, Rarity.COMMON, mage.cards.u.UrborgMindsucker.class));
        cards.add(new SetCardInfo("Vampiric Tutor", 22, Rarity.RARE, mage.cards.v.VampiricTutor.class));
        cards.add(new SetCardInfo("Vanishing", 48, Rarity.COMMON, mage.cards.v.Vanishing.class));
        cards.add(new SetCardInfo("Viashino Sandstalker", 100, Rarity.UNCOMMON, mage.cards.v.ViashinoSandstalker.class));
        cards.add(new SetCardInfo("Viashivan Dragon", 140, Rarity.RARE, mage.cards.v.ViashivanDragon.class));
        cards.add(new SetCardInfo("Vision Charm", 49, Rarity.COMMON, mage.cards.v.VisionCharm.class));
        cards.add(new SetCardInfo("Wake of Vultures", 24, Rarity.COMMON, mage.cards.w.WakeOfVultures.class));
        cards.add(new SetCardInfo("Wand of Denial", 159, Rarity.RARE, mage.cards.w.WandOfDenial.class));
        cards.add(new SetCardInfo("Warrior's Honor", 124, Rarity.COMMON, mage.cards.w.WarriorsHonor.class));
        cards.add(new SetCardInfo("Warthog", 74, Rarity.COMMON, mage.cards.w.Warthog.class));
        cards.add(new SetCardInfo("Waterspout Djinn", 50, Rarity.UNCOMMON, mage.cards.w.WaterspoutDjinn.class));
        cards.add(new SetCardInfo("Wicked Reward", 25, Rarity.COMMON, mage.cards.w.WickedReward.class));
        cards.add(new SetCardInfo("Zhalfirin Crusader", 125, Rarity.RARE, mage.cards.z.ZhalfirinCrusader.class));
    }
}
