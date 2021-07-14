package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/prna
 */
public class RavnicaAllegiancePromos extends ExpansionSet {

    private static final RavnicaAllegiancePromos instance = new RavnicaAllegiancePromos();

    public static RavnicaAllegiancePromos getInstance() {
        return instance;
    }

    private RavnicaAllegiancePromos() {
        super("Ravnica Allegiance Promos", "PRNA", ExpansionSet.buildDate(2019, 1, 25), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Absorb", "151p", Rarity.RARE, mage.cards.a.Absorb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Absorb", "151s", Rarity.RARE, mage.cards.a.Absorb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Amplifire", "92s", Rarity.RARE, mage.cards.a.Amplifire.class));
        cards.add(new SetCardInfo("Awaken the Erstwhile", "61s", Rarity.RARE, mage.cards.a.AwakenTheErstwhile.class));
        cards.add(new SetCardInfo("Bedeck // Bedazzle", "221p", Rarity.RARE, mage.cards.b.BedeckBedazzle.class));
        cards.add(new SetCardInfo("Bedevil", "157p", Rarity.RARE, mage.cards.b.Bedevil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bedevil", "157s", Rarity.RARE, mage.cards.b.Bedevil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Benthic Biomancer", "32p", Rarity.RARE, mage.cards.b.BenthicBiomancer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Benthic Biomancer", "32s", Rarity.RARE, mage.cards.b.BenthicBiomancer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Biogenic Ooze", "122p", Rarity.MYTHIC, mage.cards.b.BiogenicOoze.class));
        cards.add(new SetCardInfo("Biomancer's Familiar", "158s", Rarity.RARE, mage.cards.b.BiomancersFamiliar.class));
        cards.add(new SetCardInfo("Blood Crypt", "245p", Rarity.RARE, mage.cards.b.BloodCrypt.class));
        cards.add(new SetCardInfo("Breeding Pool", "246p", Rarity.RARE, mage.cards.b.BreedingPool.class));
        cards.add(new SetCardInfo("Cindervines", "161p", Rarity.RARE, mage.cards.c.Cindervines.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cindervines", "161s", Rarity.RARE, mage.cards.c.Cindervines.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deputy of Detention", "165p", Rarity.RARE, mage.cards.d.DeputyOfDetention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deputy of Detention", "165s", Rarity.RARE, mage.cards.d.DeputyOfDetention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Electrodominance", "99p", Rarity.RARE, mage.cards.e.Electrodominance.class));
        cards.add(new SetCardInfo("Emergency Powers", "169s", Rarity.MYTHIC, mage.cards.e.EmergencyPowers.class));
        cards.add(new SetCardInfo("End-Raze Forerunners", "124s", Rarity.RARE, mage.cards.e.EndRazeForerunners.class));
        cards.add(new SetCardInfo("Ethereal Absolution", "170s", Rarity.RARE, mage.cards.e.EtherealAbsolution.class));
        cards.add(new SetCardInfo("Font of Agonies", "74s", Rarity.RARE, mage.cards.f.FontOfAgonies.class));
        cards.add(new SetCardInfo("Gate Colossus", 232, Rarity.UNCOMMON, mage.cards.g.GateColossus.class));
        cards.add(new SetCardInfo("Godless Shrine", "248p", Rarity.RARE, mage.cards.g.GodlessShrine.class));
        cards.add(new SetCardInfo("Growth Spiral", 178, Rarity.COMMON, mage.cards.g.GrowthSpiral.class));
        cards.add(new SetCardInfo("Growth-Chamber Guardian", "128p", Rarity.RARE, mage.cards.g.GrowthChamberGuardian.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Growth-Chamber Guardian", "128s", Rarity.RARE, mage.cards.g.GrowthChamberGuardian.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gruul Spellbreaker", "179p", Rarity.RARE, mage.cards.g.GruulSpellbreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gruul Spellbreaker", "179s", Rarity.RARE, mage.cards.g.GruulSpellbreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Guardian Project", "130p", Rarity.RARE, mage.cards.g.GuardianProject.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Guardian Project", "130s", Rarity.RARE, mage.cards.g.GuardianProject.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gutterbones", "76p", Rarity.RARE, mage.cards.g.Gutterbones.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gutterbones", "76s", Rarity.RARE, mage.cards.g.Gutterbones.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", "251p", Rarity.RARE, mage.cards.h.HallowedFountain.class));
        cards.add(new SetCardInfo("Hero of Precinct One", "11p", Rarity.RARE, mage.cards.h.HeroOfPrecinctOne.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hero of Precinct One", "11s", Rarity.RARE, mage.cards.h.HeroOfPrecinctOne.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hydroid Krasis", "183p", Rarity.MYTHIC, mage.cards.h.HydroidKrasis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hydroid Krasis", "183s", Rarity.MYTHIC, mage.cards.h.HydroidKrasis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Immolation Shaman", "106s", Rarity.RARE, mage.cards.i.ImmolationShaman.class));
        cards.add(new SetCardInfo("Incubation Druid", "131p", Rarity.RARE, mage.cards.i.IncubationDruid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Incubation Druid", "131s", Rarity.RARE, mage.cards.i.IncubationDruid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Judith, the Scourge Diva", "185p", Rarity.RARE, mage.cards.j.JudithTheScourgeDiva.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Judith, the Scourge Diva", "185s", Rarity.RARE, mage.cards.j.JudithTheScourgeDiva.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaya's Wrath", "187p", Rarity.RARE, mage.cards.k.KayasWrath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaya's Wrath", "187s", Rarity.RARE, mage.cards.k.KayasWrath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaya, Orzhov Usurper", "186p", Rarity.MYTHIC, mage.cards.k.KayaOrzhovUsurper.class));
        cards.add(new SetCardInfo("Lavinia, Azorius Renegade", "189s", Rarity.RARE, mage.cards.l.LaviniaAzoriusRenegade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lavinia, Azorius Renegade", 189, Rarity.RARE, mage.cards.l.LaviniaAzoriusRenegade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Light Up the Stage", 107, Rarity.UNCOMMON, mage.cards.l.LightUpTheStage.class));
        cards.add(new SetCardInfo("Lumbering Battlement", "15s", Rarity.RARE, mage.cards.l.LumberingBattlement.class));
        cards.add(new SetCardInfo("Mass Manipulation", "42p", Rarity.RARE, mage.cards.m.MassManipulation.class));
        cards.add(new SetCardInfo("Mirror March", "108s", Rarity.RARE, mage.cards.m.MirrorMarch.class));
        cards.add(new SetCardInfo("Mortify", 192, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Nikya of the Old Ways", "193s", Rarity.RARE, mage.cards.n.NikyaOfTheOldWays.class));
        cards.add(new SetCardInfo("Plaza of Harmony", "254p", Rarity.RARE, mage.cards.p.PlazaOfHarmony.class));
        cards.add(new SetCardInfo("Precognitive Perception", "45s", Rarity.RARE, mage.cards.p.PrecognitivePerception.class));
        cards.add(new SetCardInfo("Priest of Forgotten Gods", "83p", Rarity.RARE, mage.cards.p.PriestOfForgottenGods.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Priest of Forgotten Gods", "83s", Rarity.RARE, mage.cards.p.PriestOfForgottenGods.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos Firewheeler", 197, Rarity.UNCOMMON, mage.cards.r.RakdosFirewheeler.class));
        cards.add(new SetCardInfo("Rakdos, the Showstopper", "199s", Rarity.MYTHIC, mage.cards.r.RakdosTheShowstopper.class));
        cards.add(new SetCardInfo("Rampage of the Clans", "134s", Rarity.RARE, mage.cards.r.RampageOfTheClans.class));
        cards.add(new SetCardInfo("Ravager Wurm", "200s", Rarity.MYTHIC, mage.cards.r.RavagerWurm.class));
        cards.add(new SetCardInfo("Rix Maadi Reveler", "109s", Rarity.RARE, mage.cards.r.RixMaadiReveler.class));
        cards.add(new SetCardInfo("Seraph of the Scales", "205p", Rarity.MYTHIC, mage.cards.s.SeraphOfTheScales.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seraph of the Scales", "205s", Rarity.MYTHIC, mage.cards.s.SeraphOfTheScales.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Simic Ascendancy", "207s", Rarity.RARE, mage.cards.s.SimicAscendancy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Simic Ascendancy", 207, Rarity.RARE, mage.cards.s.SimicAscendancy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skarrgan Hellkite", "114p", Rarity.MYTHIC, mage.cards.s.SkarrganHellkite.class));
        cards.add(new SetCardInfo("Smothering Tithe", "22p", Rarity.RARE, mage.cards.s.SmotheringTithe.class));
        cards.add(new SetCardInfo("Spawn of Mayhem", "85p", Rarity.MYTHIC, mage.cards.s.SpawnOfMayhem.class));
        cards.add(new SetCardInfo("Sphinx of Foresight", "55s", Rarity.RARE, mage.cards.s.SphinxOfForesight.class));
        cards.add(new SetCardInfo("Stomping Ground", "259p", Rarity.RARE, mage.cards.s.StompingGround.class));
        cards.add(new SetCardInfo("Teysa Karlov", "212p", Rarity.RARE, mage.cards.t.TeysaKarlov.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teysa Karlov", "212s", Rarity.RARE, mage.cards.t.TeysaKarlov.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Theater of Horrors", "213s", Rarity.RARE, mage.cards.t.TheaterOfHorrors.class));
        cards.add(new SetCardInfo("Tithe Taker", "27p", Rarity.RARE, mage.cards.t.TitheTaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tithe Taker", "27s", Rarity.RARE, mage.cards.t.TitheTaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unbreakable Formation", "29p", Rarity.RARE, mage.cards.u.UnbreakableFormation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unbreakable Formation", "29s", Rarity.RARE, mage.cards.u.UnbreakableFormation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zegana, Utopian Speaker", "214s", Rarity.RARE, mage.cards.z.ZeganaUtopianSpeaker.class));
    }
}
