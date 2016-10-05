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

import mage.constants.SetType;
import mage.cards.ExpansionSet;

import java.util.GregorianCalendar;
import mage.constants.Rarity;
import java.util.List;

/**
 *
 * @author noxx
 */
public class Weatherlight extends ExpansionSet {

    private static final Weatherlight fINSTANCE = new Weatherlight();

    public static Weatherlight getInstance() {
        return fINSTANCE;
    }

    private Weatherlight() {
        super("Weatherlight", "WTH", "mage.sets.weatherlight", new GregorianCalendar(1997, 5, 31).getTime(), SetType.EXPANSION);
        this.blockName = "Mirage";
        this.parentSet = Mirage.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Abduction", 30, Rarity.UNCOMMON, mage.cards.a.Abduction.class));
        cards.add(new SetCardInfo("Abeyance", 117, Rarity.RARE, mage.cards.a.Abeyance.class));
        cards.add(new SetCardInfo("Abjure", 31, Rarity.COMMON, mage.cards.a.Abjure.class));
        cards.add(new SetCardInfo("Abyssal Gatekeeper", 1, Rarity.COMMON, mage.cards.a.AbyssalGatekeeper.class));
        cards.add(new SetCardInfo("Aether Flash", 88, Rarity.UNCOMMON, mage.cards.a.AetherFlash.class));
        cards.add(new SetCardInfo("Agonizing Memories", 2, Rarity.UNCOMMON, mage.cards.a.AgonizingMemories.class));
        cards.add(new SetCardInfo("Alabaster Dragon", 118, Rarity.RARE, mage.cards.a.AlabasterDragon.class));
        cards.add(new SetCardInfo("Alms", 119, Rarity.COMMON, mage.cards.a.Alms.class));
        cards.add(new SetCardInfo("Ancestral Knowledge", 32, Rarity.RARE, mage.cards.a.AncestralKnowledge.class));
        cards.add(new SetCardInfo("Angelic Renewal", 120, Rarity.COMMON, mage.cards.a.AngelicRenewal.class));
        cards.add(new SetCardInfo("Arctic Wolves", 60, Rarity.UNCOMMON, mage.cards.a.ArcticWolves.class));
        cards.add(new SetCardInfo("Ardent Militia", 121, Rarity.COMMON, mage.cards.a.ArdentMilitia.class));
        cards.add(new SetCardInfo("Argivian Find", 122, Rarity.UNCOMMON, mage.cards.a.ArgivianFind.class));
        cards.add(new SetCardInfo("Argivian Restoration", 34, Rarity.UNCOMMON, mage.cards.a.ArgivianRestoration.class));
        cards.add(new SetCardInfo("Aura of Silence", 123, Rarity.UNCOMMON, mage.cards.a.AuraOfSilence.class));
        cards.add(new SetCardInfo("Barrow Ghoul", 3, Rarity.COMMON, mage.cards.b.BarrowGhoul.class));
        cards.add(new SetCardInfo("Benalish Knight", 125, Rarity.COMMON, mage.cards.b.BenalishKnight.class));
        cards.add(new SetCardInfo("Benalish Missionary", 126, Rarity.COMMON, mage.cards.b.BenalishMissionary.class));
        cards.add(new SetCardInfo("Bloodrock Cyclops", 90, Rarity.COMMON, mage.cards.b.BloodrockCyclops.class));
        cards.add(new SetCardInfo("Blossoming Wreath", 62, Rarity.COMMON, mage.cards.b.BlossomingWreath.class));
        cards.add(new SetCardInfo("Bogardan Firefiend", 91, Rarity.COMMON, mage.cards.b.BogardanFirefiend.class));
        cards.add(new SetCardInfo("Boiling Blood", 92, Rarity.COMMON, mage.cards.b.BoilingBlood.class));
        cards.add(new SetCardInfo("Bone Dancer", 4, Rarity.RARE, mage.cards.b.BoneDancer.class));
        cards.add(new SetCardInfo("Briar Shield", 63, Rarity.COMMON, mage.cards.b.BriarShield.class));
        cards.add(new SetCardInfo("Bubble Matrix", 147, Rarity.RARE, mage.cards.b.BubbleMatrix.class));
        cards.add(new SetCardInfo("Buried Alive", 5, Rarity.UNCOMMON, mage.cards.b.BuriedAlive.class));
        cards.add(new SetCardInfo("Call of the Wild", 64, Rarity.RARE, mage.cards.c.CallOfTheWild.class));
        cards.add(new SetCardInfo("Chimeric Sphere", 148, Rarity.UNCOMMON, mage.cards.c.ChimericSphere.class));
        cards.add(new SetCardInfo("Cinder Giant", 93, Rarity.UNCOMMON, mage.cards.c.CinderGiant.class));
        cards.add(new SetCardInfo("Cinder Wall", 94, Rarity.COMMON, mage.cards.c.CinderWall.class));
        cards.add(new SetCardInfo("Cloud Djinn", 36, Rarity.UNCOMMON, mage.cards.c.CloudDjinn.class));
        cards.add(new SetCardInfo("Cone of Flame", 95, Rarity.UNCOMMON, mage.cards.c.ConeOfFlame.class));
        cards.add(new SetCardInfo("Debt of Loyalty", 127, Rarity.RARE, mage.cards.d.DebtOfLoyalty.class));
        cards.add(new SetCardInfo("Dense Foliage", 66, Rarity.RARE, mage.cards.d.DenseFoliage.class));
        cards.add(new SetCardInfo("Disrupt", 37, Rarity.COMMON, mage.cards.d.Disrupt.class));
        cards.add(new SetCardInfo("Doomsday", 8, Rarity.RARE, mage.cards.d.Doomsday.class));
        cards.add(new SetCardInfo("Downdraft", 67, Rarity.UNCOMMON, mage.cards.d.Downdraft.class));
        cards.add(new SetCardInfo("Duskrider Falcon", 128, Rarity.COMMON, mage.cards.d.DuskriderFalcon.class));
        cards.add(new SetCardInfo("Dwarven Berserker", 97, Rarity.COMMON, mage.cards.d.DwarvenBerserker.class));
        cards.add(new SetCardInfo("Dwarven Thaumaturgist", 98, Rarity.RARE, mage.cards.d.DwarvenThaumaturgist.class));
        cards.add(new SetCardInfo("Empyrial Armor", 129, Rarity.COMMON, mage.cards.e.EmpyrialArmor.class));
        cards.add(new SetCardInfo("Fallow Wurm", 68, Rarity.UNCOMMON, mage.cards.f.FallowWurm.class));
        cards.add(new SetCardInfo("Familiar Ground", 69, Rarity.UNCOMMON, mage.cards.f.FamiliarGround.class));
        cards.add(new SetCardInfo("Fatal Blow", 9, Rarity.COMMON, mage.cards.f.FatalBlow.class));
        cards.add(new SetCardInfo("Fervor", 99, Rarity.RARE, mage.cards.f.Fervor.class));
        cards.add(new SetCardInfo("Festering Evil", 10, Rarity.UNCOMMON, mage.cards.f.FesteringEvil.class));
        cards.add(new SetCardInfo("Firestorm", 101, Rarity.RARE, mage.cards.f.Firestorm.class));
        cards.add(new SetCardInfo("Fire Whip", 100, Rarity.COMMON, mage.cards.f.FireWhip.class));
        cards.add(new SetCardInfo("Fit of Rage", 102, Rarity.COMMON, mage.cards.f.FitOfRage.class));
        cards.add(new SetCardInfo("Fledgling Djinn", 11, Rarity.COMMON, mage.cards.f.FledglingDjinn.class));
        cards.add(new SetCardInfo("Fog Elemental", 40, Rarity.COMMON, mage.cards.f.FogElemental.class));
        cards.add(new SetCardInfo("Foriysian Brigade", 130, Rarity.UNCOMMON, mage.cards.f.ForiysianBrigade.class));
        cards.add(new SetCardInfo("Gaea's Blessing", 71, Rarity.UNCOMMON, mage.cards.g.GaeasBlessing.class));
        cards.add(new SetCardInfo("Gallowbraid", 12, Rarity.RARE, mage.cards.g.Gallowbraid.class));
        cards.add(new SetCardInfo("Gemstone Mine", 164, Rarity.UNCOMMON, mage.cards.g.GemstoneMine.class));
        cards.add(new SetCardInfo("Gerrard's Wisdom", 131, Rarity.UNCOMMON, mage.cards.g.GerrardsWisdom.class));
        cards.add(new SetCardInfo("Goblin Bomb", 103, Rarity.RARE, mage.cards.g.GoblinBomb.class));
        cards.add(new SetCardInfo("Goblin Grenadiers", 104, Rarity.UNCOMMON, mage.cards.g.GoblinGrenadiers.class));
        cards.add(new SetCardInfo("Goblin Vandal", 105, Rarity.COMMON, mage.cards.g.GoblinVandal.class));
        cards.add(new SetCardInfo("Guided Strike", 132, Rarity.COMMON, mage.cards.g.GuidedStrike.class));
        cards.add(new SetCardInfo("Harvest Wurm", 72, Rarity.COMMON, mage.cards.h.HarvestWurm.class));
        cards.add(new SetCardInfo("Haunting Misery", 13, Rarity.COMMON, mage.cards.h.HauntingMisery.class));
        cards.add(new SetCardInfo("Heavy Ballista", 133, Rarity.COMMON, mage.cards.h.HeavyBallista.class));
        cards.add(new SetCardInfo("Hidden Horror", 14, Rarity.UNCOMMON, mage.cards.h.HiddenHorror.class));
        cards.add(new SetCardInfo("Hurloon Shaman", 108, Rarity.UNCOMMON, mage.cards.h.HurloonShaman.class));
        cards.add(new SetCardInfo("Jabari's Banner", 150, Rarity.UNCOMMON, mage.cards.j.JabarisBanner.class));
        cards.add(new SetCardInfo("Lava Hounds", 109, Rarity.UNCOMMON, mage.cards.l.LavaHounds.class));
        cards.add(new SetCardInfo("Llanowar Behemoth", 74, Rarity.UNCOMMON, mage.cards.l.LlanowarBehemoth.class));
        cards.add(new SetCardInfo("Llanowar Druid", 75, Rarity.COMMON, mage.cards.l.LlanowarDruid.class));
        cards.add(new SetCardInfo("Llanowar Sentinel", 76, Rarity.COMMON, mage.cards.l.LlanowarSentinel.class));
        cards.add(new SetCardInfo("Lotus Vale", 165, Rarity.RARE, mage.cards.l.LotusVale.class));
        cards.add(new SetCardInfo("Mana Chains", 41, Rarity.COMMON, mage.cards.m.ManaChains.class));
        cards.add(new SetCardInfo("Mana Web", 152, Rarity.RARE, mage.cards.m.ManaWeb.class));
        cards.add(new SetCardInfo("Manta Ray", 42, Rarity.COMMON, mage.cards.m.MantaRay.class));
        cards.add(new SetCardInfo("Maraxus of Keld", 111, Rarity.RARE, mage.cards.m.MaraxusOfKeld.class));
        cards.add(new SetCardInfo("Master of Arms", 136, Rarity.UNCOMMON, mage.cards.m.MasterOfArms.class));
        cards.add(new SetCardInfo("Merfolk Traders", 43, Rarity.COMMON, mage.cards.m.MerfolkTraders.class));
        cards.add(new SetCardInfo("Mind Stone", 153, Rarity.COMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Mischievous Poltergeist", 16, Rarity.UNCOMMON, mage.cards.m.MischievousPoltergeist.class));
        cards.add(new SetCardInfo("Mistmoon Griffin", 137, Rarity.UNCOMMON, mage.cards.m.MistmoonGriffin.class));
        cards.add(new SetCardInfo("Morinfen", 17, Rarity.RARE, mage.cards.m.Morinfen.class));
        cards.add(new SetCardInfo("Mwonvuli Ooze", 77, Rarity.RARE, mage.cards.m.MwonvuliOoze.class));
        cards.add(new SetCardInfo("Nature's Kiss", 78, Rarity.COMMON, mage.cards.n.NaturesKiss.class));
        cards.add(new SetCardInfo("Nature's Resurgence", 79, Rarity.RARE, mage.cards.n.NaturesResurgence.class));
        cards.add(new SetCardInfo("Necratog", 18, Rarity.UNCOMMON, mage.cards.n.Necratog.class));
        cards.add(new SetCardInfo("Null Rod", 154, Rarity.RARE, mage.cards.n.NullRod.class));
        cards.add(new SetCardInfo("Odylic Wraith", 19, Rarity.UNCOMMON, mage.cards.o.OdylicWraith.class));
        cards.add(new SetCardInfo("Ophidian", 45, Rarity.COMMON, mage.cards.o.Ophidian.class));
        cards.add(new SetCardInfo("Orcish Settlers", 112, Rarity.UNCOMMON, mage.cards.o.OrcishSettlers.class));
        cards.add(new SetCardInfo("Paradigm Shift", 46, Rarity.RARE, mage.cards.p.ParadigmShift.class));
        cards.add(new SetCardInfo("Peacekeeper", 138, Rarity.RARE, mage.cards.p.Peacekeeper.class));
        cards.add(new SetCardInfo("Pendrell Mists", 47, Rarity.RARE, mage.cards.p.PendrellMists.class));
        cards.add(new SetCardInfo("Phantom Warrior", 48, Rarity.UNCOMMON, mage.cards.p.PhantomWarrior.class));
        cards.add(new SetCardInfo("Phantom Wings", 49, Rarity.COMMON, mage.cards.p.PhantomWings.class));
        cards.add(new SetCardInfo("Phyrexian Furnace", 155, Rarity.UNCOMMON, mage.cards.p.PhyrexianFurnace.class));
        cards.add(new SetCardInfo("Psychic Vortex", 50, Rarity.RARE, mage.cards.p.PsychicVortex.class));
        cards.add(new SetCardInfo("Razortooth Rats", 20, Rarity.COMMON, mage.cards.r.RazortoothRats.class));
        cards.add(new SetCardInfo("Redwood Treefolk", 80, Rarity.COMMON, mage.cards.r.RedwoodTreefolk.class));
        cards.add(new SetCardInfo("Relearn", 51, Rarity.UNCOMMON, mage.cards.r.Relearn.class));
        cards.add(new SetCardInfo("Revered Unicorn", 139, Rarity.UNCOMMON, mage.cards.r.ReveredUnicorn.class));
        cards.add(new SetCardInfo("Roc Hatchling", 113, Rarity.UNCOMMON, mage.cards.r.RocHatchling.class));
        cards.add(new SetCardInfo("Rogue Elephant", 81, Rarity.COMMON, mage.cards.r.RogueElephant.class));
        cards.add(new SetCardInfo("Sage Owl", 52, Rarity.COMMON, mage.cards.s.SageOwl.class));
        cards.add(new SetCardInfo("Scorched Ruins", 166, Rarity.RARE, mage.cards.s.ScorchedRuins.class));
        cards.add(new SetCardInfo("Serenity", 140, Rarity.RARE, mage.cards.s.Serenity.class));
        cards.add(new SetCardInfo("Serra's Blessing", 141, Rarity.UNCOMMON, mage.cards.s.SerrasBlessing.class));
        cards.add(new SetCardInfo("Serrated Biskelion", 156, Rarity.UNCOMMON, mage.cards.s.SerratedBiskelion.class));
        cards.add(new SetCardInfo("Shadow Rider", 21, Rarity.COMMON, mage.cards.s.ShadowRider.class));
        cards.add(new SetCardInfo("Soul Shepherd", 142, Rarity.COMMON, mage.cards.s.SoulShepherd.class));
        cards.add(new SetCardInfo("Southern Paladin", 143, Rarity.RARE, mage.cards.s.SouthernPaladin.class));
        cards.add(new SetCardInfo("Spinning Darkness", 23, Rarity.COMMON, mage.cards.s.SpinningDarkness.class));
        cards.add(new SetCardInfo("Steel Golem", 157, Rarity.UNCOMMON, mage.cards.s.SteelGolem.class));
        cards.add(new SetCardInfo("Strands of Night", 24, Rarity.UNCOMMON, mage.cards.s.StrandsOfNight.class));
        cards.add(new SetCardInfo("Straw Golem", 158, Rarity.UNCOMMON, mage.cards.s.StrawGolem.class));
        cards.add(new SetCardInfo("Striped Bears", 82, Rarity.COMMON, mage.cards.s.StripedBears.class));
        cards.add(new SetCardInfo("Tariff", 144, Rarity.RARE, mage.cards.t.Tariff.class));
        cards.add(new SetCardInfo("Tendrils of Despair", 25, Rarity.COMMON, mage.cards.t.TendrilsOfDespair.class));
        cards.add(new SetCardInfo("Thunderbolt", 115, Rarity.COMMON, mage.cards.t.Thunderbolt.class));
        cards.add(new SetCardInfo("Thundermare", 116, Rarity.RARE, mage.cards.t.Thundermare.class));
        cards.add(new SetCardInfo("Tolarian Drake", 55, Rarity.COMMON, mage.cards.t.TolarianDrake.class));
        cards.add(new SetCardInfo("Tolarian Entrancer", 56, Rarity.RARE, mage.cards.t.TolarianEntrancer.class));
        cards.add(new SetCardInfo("Tolarian Serpent", 57, Rarity.RARE, mage.cards.t.TolarianSerpent.class));
        cards.add(new SetCardInfo("Touchstone", 161, Rarity.UNCOMMON, mage.cards.t.Touchstone.class));
        cards.add(new SetCardInfo("Tranquil Grove", 84, Rarity.RARE, mage.cards.t.TranquilGrove.class));
        cards.add(new SetCardInfo("Uktabi Efreet", 85, Rarity.COMMON, mage.cards.u.UktabiEfreet.class));
        cards.add(new SetCardInfo("Urborg Justice", 26, Rarity.RARE, mage.cards.u.UrborgJustice.class));
        cards.add(new SetCardInfo("Veteran Explorer", 86, Rarity.UNCOMMON, mage.cards.v.VeteranExplorer.class));
        cards.add(new SetCardInfo("Vitalize", 87, Rarity.COMMON, mage.cards.v.Vitalize.class));
        cards.add(new SetCardInfo("Vodalian Illusionist", 58, Rarity.UNCOMMON, mage.cards.v.VodalianIllusionist.class));
        cards.add(new SetCardInfo("Winding Canyons", 167, Rarity.RARE, mage.cards.w.WindingCanyons.class));
        cards.add(new SetCardInfo("Xanthic Statue", 163, Rarity.RARE, mage.cards.x.XanthicStatue.class));
        cards.add(new SetCardInfo("Zombie Scavengers", 29, Rarity.COMMON, mage.cards.z.ZombieScavengers.class));
    }

}
