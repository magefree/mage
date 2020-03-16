package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pbfz
 */
public class BattleForZendikarPromos extends ExpansionSet {

    private static final BattleForZendikarPromos instance = new BattleForZendikarPromos();

    public static BattleForZendikarPromos getInstance() {
        return instance;
    }

    private BattleForZendikarPromos() {
        super("Battle for Zendikar Promos", "PBFZ", ExpansionSet.buildDate(2015, 10, 3), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Akoum Firebird", "138s", Rarity.MYTHIC, mage.cards.a.AkoumFirebird.class));
        cards.add(new SetCardInfo("Akoum Hellkite", "139s", Rarity.RARE, mage.cards.a.AkoumHellkite.class));
        cards.add(new SetCardInfo("Aligned Hedron Network", "222s", Rarity.RARE, mage.cards.a.AlignedHedronNetwork.class));
        cards.add(new SetCardInfo("Ally Encampment", "228s", Rarity.RARE, mage.cards.a.AllyEncampment.class));
        cards.add(new SetCardInfo("Angelic Captain", "208s", Rarity.RARE, mage.cards.a.AngelicCaptain.class));
        cards.add(new SetCardInfo("Barrage Tyrant", 127, Rarity.RARE, mage.cards.b.BarrageTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Barrage Tyrant", "127s", Rarity.RARE, mage.cards.b.BarrageTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Beastcaller Savant", "170s", Rarity.RARE, mage.cards.b.BeastcallerSavant.class));
        cards.add(new SetCardInfo("Blight Herder", 2, Rarity.RARE, mage.cards.b.BlightHerder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blight Herder", "2s", Rarity.RARE, mage.cards.b.BlightHerder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bring to Light", "209s", Rarity.RARE, mage.cards.b.BringToLight.class));
        cards.add(new SetCardInfo("Brood Butcher", "199s", Rarity.RARE, mage.cards.b.BroodButcher.class));
        cards.add(new SetCardInfo("Brutal Expulsion", "200s", Rarity.RARE, mage.cards.b.BrutalExpulsion.class));
        cards.add(new SetCardInfo("Canopy Vista", "234s", Rarity.RARE, mage.cards.c.CanopyVista.class));
        cards.add(new SetCardInfo("Cinder Glade", "235s", Rarity.RARE, mage.cards.c.CinderGlade.class));
        cards.add(new SetCardInfo("Conduit of Ruin", "4s", Rarity.RARE, mage.cards.c.ConduitOfRuin.class));
        cards.add(new SetCardInfo("Defiant Bloodlord", 107, Rarity.RARE, mage.cards.d.DefiantBloodlord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defiant Bloodlord", "107s", Rarity.RARE, mage.cards.d.DefiantBloodlord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Desolation Twin", "6s", Rarity.RARE, mage.cards.d.DesolationTwin.class));
        cards.add(new SetCardInfo("Dragonmaster Outcast", "144s", Rarity.MYTHIC, mage.cards.d.DragonmasterOutcast.class));
        cards.add(new SetCardInfo("Drana, Liberator of Malakir", "109s", Rarity.MYTHIC, mage.cards.d.DranaLiberatorOfMalakir.class));
        cards.add(new SetCardInfo("Drowner of Hope", 57, Rarity.RARE, mage.cards.d.DrownerOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drowner of Hope", "57s", Rarity.RARE, mage.cards.d.DrownerOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dust Stalker", "202s", Rarity.RARE, mage.cards.d.DustStalker.class));
        cards.add(new SetCardInfo("Emeria Shepherd", "22s", Rarity.RARE, mage.cards.e.EmeriaShepherd.class));
        cards.add(new SetCardInfo("Endless One", "8s", Rarity.RARE, mage.cards.e.EndlessOne.class));
        cards.add(new SetCardInfo("Exert Influence", "77s", Rarity.RARE, mage.cards.e.ExertInfluence.class));
        cards.add(new SetCardInfo("Fathom Feeder", "203s", Rarity.RARE, mage.cards.f.FathomFeeder.class));
        cards.add(new SetCardInfo("Felidar Sovereign", "26s", Rarity.RARE, mage.cards.f.FelidarSovereign.class));
        cards.add(new SetCardInfo("From Beyond", "167s", Rarity.RARE, mage.cards.f.FromBeyond.class));
        cards.add(new SetCardInfo("Gideon, Ally of Zendikar", "29s", Rarity.MYTHIC, mage.cards.g.GideonAllyOfZendikar.class));
        cards.add(new SetCardInfo("Greenwarden of Murasa", "174s", Rarity.MYTHIC, mage.cards.g.GreenwardenOfMurasa.class));
        cards.add(new SetCardInfo("Gruesome Slaughter", "9s", Rarity.RARE, mage.cards.g.GruesomeSlaughter.class));
        cards.add(new SetCardInfo("Guardian of Tazeem", "78s", Rarity.RARE, mage.cards.g.GuardianOfTazeem.class));
        cards.add(new SetCardInfo("Guul Draz Overseer", "112s", Rarity.RARE, mage.cards.g.GuulDrazOverseer.class));
        cards.add(new SetCardInfo("Hero of Goma Fada", 31, Rarity.RARE, mage.cards.h.HeroOfGomaFada.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hero of Goma Fada", "31s", Rarity.RARE, mage.cards.h.HeroOfGomaFada.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kiora, Master of the Depths", "213s", Rarity.MYTHIC, mage.cards.k.KioraMasterOfTheDepths.class));
        cards.add(new SetCardInfo("Lantern Scout", "37s", Rarity.RARE, mage.cards.l.LanternScout.class));
        cards.add(new SetCardInfo("Lumbering Falls", "239s", Rarity.RARE, mage.cards.l.LumberingFalls.class));
        cards.add(new SetCardInfo("March from the Tomb", "214s", Rarity.RARE, mage.cards.m.MarchFromTheTomb.class));
        cards.add(new SetCardInfo("Munda, Ambush Leader", "215s", Rarity.RARE, mage.cards.m.MundaAmbushLeader.class));
        cards.add(new SetCardInfo("Nissa's Renewal", "180s", Rarity.RARE, mage.cards.n.NissasRenewal.class));
        cards.add(new SetCardInfo("Noyan Dar, Roil Shaper", "216s", Rarity.RARE, mage.cards.n.NoyanDarRoilShaper.class));
        cards.add(new SetCardInfo("Ob Nixilis Reignited", "119s", Rarity.MYTHIC, mage.cards.o.ObNixilisReignited.class));
        cards.add(new SetCardInfo("Oblivion Sower", "11s", Rarity.MYTHIC, mage.cards.o.OblivionSower.class));
        cards.add(new SetCardInfo("Omnath, Locus of Rage", "217s", Rarity.MYTHIC, mage.cards.o.OmnathLocusOfRage.class));
        cards.add(new SetCardInfo("Oran-Rief Hydra", 181, Rarity.RARE, mage.cards.o.OranRiefHydra.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oran-Rief Hydra", "181s", Rarity.RARE, mage.cards.o.OranRiefHydra.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Painful Truths", "120s", Rarity.RARE, mage.cards.p.PainfulTruths.class));
        cards.add(new SetCardInfo("Part the Waterveil", "80s", Rarity.MYTHIC, mage.cards.p.PartTheWaterveil.class));
        cards.add(new SetCardInfo("Planar Outburst", "42s", Rarity.RARE, mage.cards.p.PlanarOutburst.class));
        cards.add(new SetCardInfo("Prairie Stream", "241s", Rarity.RARE, mage.cards.p.PrairieStream.class));
        cards.add(new SetCardInfo("Prism Array", "81s", Rarity.RARE, mage.cards.p.PrismArray.class));
        cards.add(new SetCardInfo("Quarantine Field", "43s", Rarity.MYTHIC, mage.cards.q.QuarantineField.class));
        cards.add(new SetCardInfo("Radiant Flames", 151, Rarity.RARE, mage.cards.r.RadiantFlames.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radiant Flames", "151s", Rarity.RARE, mage.cards.r.RadiantFlames.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ruinous Path", 123, Rarity.RARE, mage.cards.r.RuinousPath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ruinous Path", "123s", Rarity.RARE, mage.cards.r.RuinousPath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sanctum of Ugin", "242s", Rarity.RARE, mage.cards.s.SanctumOfUgin.class));
        cards.add(new SetCardInfo("Scatter to the Winds", "85s", Rarity.RARE, mage.cards.s.ScatterToTheWinds.class));
        cards.add(new SetCardInfo("Scythe Leopard", 188, Rarity.UNCOMMON, mage.cards.s.ScytheLeopard.class));
        cards.add(new SetCardInfo("Serpentine Spike", "133s", Rarity.RARE, mage.cards.s.SerpentineSpike.class));
        cards.add(new SetCardInfo("Shambling Vent", "244s", Rarity.RARE, mage.cards.s.ShamblingVent.class));
        cards.add(new SetCardInfo("Shrine of the Forsaken Gods", "245s", Rarity.RARE, mage.cards.s.ShrineOfTheForsakenGods.class));
        cards.add(new SetCardInfo("Sire of Stagnation", "206s", Rarity.MYTHIC, mage.cards.s.SireOfStagnation.class));
        cards.add(new SetCardInfo("Smoldering Marsh", "247s", Rarity.RARE, mage.cards.s.SmolderingMarsh.class));
        cards.add(new SetCardInfo("Smothering Abomination", "99s", Rarity.RARE, mage.cards.s.SmotheringAbomination.class));
        cards.add(new SetCardInfo("Stasis Snare", 50, Rarity.UNCOMMON, mage.cards.s.StasisSnare.class));
        cards.add(new SetCardInfo("Sunken Hollow", "249s", Rarity.RARE, mage.cards.s.SunkenHollow.class));
        cards.add(new SetCardInfo("Ugin's Insight", "87s", Rarity.RARE, mage.cards.u.UginsInsight.class));
        cards.add(new SetCardInfo("Ulamog, the Ceaseless Hunger", "15s", Rarity.MYTHIC, mage.cards.u.UlamogTheCeaselessHunger.class));
        cards.add(new SetCardInfo("Undergrowth Champion", "197s", Rarity.MYTHIC, mage.cards.u.UndergrowthChampion.class));
        cards.add(new SetCardInfo("Veteran Warleader", "221s", Rarity.RARE, mage.cards.v.VeteranWarleader.class));
        cards.add(new SetCardInfo("Void Winnower", "17s", Rarity.MYTHIC, mage.cards.v.VoidWinnower.class));
        cards.add(new SetCardInfo("Wasteland Strangler", "102s", Rarity.RARE, mage.cards.w.WastelandStrangler.class));
        cards.add(new SetCardInfo("Woodland Wanderer", "198s", Rarity.RARE, mage.cards.w.WoodlandWanderer.class));
        cards.add(new SetCardInfo("Zada, Hedron Grinder", "162s", Rarity.RARE, mage.cards.z.ZadaHedronGrinder.class));
     }
}
