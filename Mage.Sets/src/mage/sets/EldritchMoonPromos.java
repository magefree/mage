package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pemn
 */
public class EldritchMoonPromos extends ExpansionSet {

    private static final EldritchMoonPromos instance = new EldritchMoonPromos();

    public static EldritchMoonPromos getInstance() {
        return instance;
    }

    private EldritchMoonPromos() {
        super("Eldritch Moon Promos", "PEMN", ExpansionSet.buildDate(2016, 7, 22), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Abolisher of Bloodlines", "111s", Rarity.RARE, mage.cards.a.AbolisherOfBloodlines.class));
        cards.add(new SetCardInfo("Assembled Alphas", 117, Rarity.RARE, mage.cards.a.AssembledAlphas.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Assembled Alphas", "117s", Rarity.RARE, mage.cards.a.AssembledAlphas.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bedlam Reveler", "118s", Rarity.RARE, mage.cards.b.BedlamReveler.class));
        cards.add(new SetCardInfo("Bloodhall Priest", "181s", Rarity.RARE, mage.cards.b.BloodhallPriest.class));
        cards.add(new SetCardInfo("Brisela, Voice of Nightmares", "15bs", Rarity.MYTHIC, mage.cards.b.BriselaVoiceOfNightmares.class));
        cards.add(new SetCardInfo("Bruna, the Fading Light", "15as", Rarity.RARE, mage.cards.b.BrunaTheFadingLight.class));
        cards.add(new SetCardInfo("Coax from the Blind Eternities", "51s", Rarity.RARE, mage.cards.c.CoaxFromTheBlindEternities.class));
        cards.add(new SetCardInfo("Collective Brutality", "85s", Rarity.RARE, mage.cards.c.CollectiveBrutality.class));
        cards.add(new SetCardInfo("Collective Defiance", "123s", Rarity.RARE, mage.cards.c.CollectiveDefiance.class));
        cards.add(new SetCardInfo("Collective Effort", "17s", Rarity.RARE, mage.cards.c.CollectiveEffort.class));
        cards.add(new SetCardInfo("Cryptbreaker", "86s", Rarity.RARE, mage.cards.c.Cryptbreaker.class));
        cards.add(new SetCardInfo("Dark Salvation", "87s", Rarity.RARE, mage.cards.d.DarkSalvation.class));
        cards.add(new SetCardInfo("Decimator of the Provinces", "2s", Rarity.MYTHIC, mage.cards.d.DecimatorOfTheProvinces.class));
        cards.add(new SetCardInfo("Deploy the Gatewatch", "20s", Rarity.MYTHIC, mage.cards.d.DeployTheGatewatch.class));
        cards.add(new SetCardInfo("Distended Mindbender", "3s", Rarity.RARE, mage.cards.d.DistendedMindbender.class));
        cards.add(new SetCardInfo("Docent of Perfection", "56s", Rarity.RARE, mage.cards.d.DocentOfPerfection.class));
        cards.add(new SetCardInfo("Elder Deep-Fiend", "5s", Rarity.RARE, mage.cards.e.ElderDeepFiend.class));
        cards.add(new SetCardInfo("Eldritch Evolution", "155s", Rarity.RARE, mage.cards.e.EldritchEvolution.class));
        cards.add(new SetCardInfo("Emrakul's Evangel", "156s", Rarity.RARE, mage.cards.e.EmrakulsEvangel.class));
        cards.add(new SetCardInfo("Emrakul, the Promised End", "6s", Rarity.MYTHIC, mage.cards.e.EmrakulThePromisedEnd.class));
        cards.add(new SetCardInfo("Eternal Scourge", "7s", Rarity.RARE, mage.cards.e.EternalScourge.class));
        cards.add(new SetCardInfo("Final Iteration", "56s", Rarity.RARE, mage.cards.f.FinalIteration.class));
        cards.add(new SetCardInfo("Geier Reach Sanitarium", "203s", Rarity.RARE, mage.cards.g.GeierReachSanitarium.class));
        cards.add(new SetCardInfo("Gisa and Geralf", "183s", Rarity.MYTHIC, mage.cards.g.GisaAndGeralf.class));
        cards.add(new SetCardInfo("Gisela, the Broken Blade", "28s", Rarity.MYTHIC, mage.cards.g.GiselaTheBrokenBlade.class));
        cards.add(new SetCardInfo("Grim Flayer", "184s", Rarity.MYTHIC, mage.cards.g.GrimFlayer.class));
        cards.add(new SetCardInfo("Hanweir Battlements", "204s", Rarity.RARE, mage.cards.h.HanweirBattlements.class));
        cards.add(new SetCardInfo("Hanweir Garrison", "130as", Rarity.RARE, mage.cards.h.HanweirGarrison.class));
        cards.add(new SetCardInfo("Hanweir, the Writhing Township", "130bs", Rarity.RARE, mage.cards.h.HanweirTheWrithingTownship.class));
        cards.add(new SetCardInfo("Harmless Offering", "131s", Rarity.RARE, mage.cards.h.HarmlessOffering.class));
        cards.add(new SetCardInfo("Heron's Grace Champion", 185, Rarity.RARE, mage.cards.h.HeronsGraceChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heron's Grace Champion", "185s", Rarity.RARE, mage.cards.h.HeronsGraceChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Identity Thief", 64, Rarity.RARE, mage.cards.i.IdentityThief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Identity Thief", "64s", Rarity.RARE, mage.cards.i.IdentityThief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Impetuous Devils", "132s", Rarity.RARE, mage.cards.i.ImpetuousDevils.class));
        cards.add(new SetCardInfo("Imprisoned in the Moon", "65s", Rarity.RARE, mage.cards.i.ImprisonedInTheMoon.class));
        cards.add(new SetCardInfo("Ishkanah, Grafwidow", "162s", Rarity.MYTHIC, mage.cards.i.IshkanahGrafwidow.class));
        cards.add(new SetCardInfo("Liliana, the Last Hope", "93s", Rarity.MYTHIC, mage.cards.l.LilianaTheLastHope.class));
        cards.add(new SetCardInfo("Lupine Prototype", "197s", Rarity.RARE, mage.cards.l.LupinePrototype.class));
        cards.add(new SetCardInfo("Mausoleum Wanderer", "69s", Rarity.RARE, mage.cards.m.MausoleumWanderer.class));
        cards.add(new SetCardInfo("Mind's Dilation", "70s", Rarity.MYTHIC, mage.cards.m.MindsDilation.class));
        cards.add(new SetCardInfo("Mirrorwing Dragon", "136s", Rarity.MYTHIC, mage.cards.m.MirrorwingDragon.class));
        cards.add(new SetCardInfo("Nahiri's Wrath", "137s", Rarity.MYTHIC, mage.cards.n.NahirisWrath.class));
        cards.add(new SetCardInfo("Niblis of Frost", 72, Rarity.RARE, mage.cards.n.NiblisOfFrost.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Niblis of Frost", "72s", Rarity.RARE, mage.cards.n.NiblisOfFrost.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Noosegraf Mob", 98, Rarity.RARE, mage.cards.n.NoosegrafMob.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Noosegraf Mob", "98s", Rarity.RARE, mage.cards.n.NoosegrafMob.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oath of Liliana", "99s", Rarity.RARE, mage.cards.o.OathOfLiliana.class));
        cards.add(new SetCardInfo("Permeating Mass", "165s", Rarity.RARE, mage.cards.p.PermeatingMass.class));
        cards.add(new SetCardInfo("Providence", "37s", Rarity.RARE, mage.cards.p.Providence.class));
        cards.add(new SetCardInfo("Sanctifier of Souls", 39, Rarity.RARE, mage.cards.s.SanctifierOfSouls.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sanctifier of Souls", "39s", Rarity.RARE, mage.cards.s.SanctifierOfSouls.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Selfless Spirit", "40s", Rarity.RARE, mage.cards.s.SelflessSpirit.class));
        cards.add(new SetCardInfo("Sigarda's Aid", "41s", Rarity.RARE, mage.cards.s.SigardasAid.class));
        cards.add(new SetCardInfo("Soul Separator", "199s", Rarity.RARE, mage.cards.s.SoulSeparator.class));
        cards.add(new SetCardInfo("Spell Queller", "189s", Rarity.RARE, mage.cards.s.SpellQueller.class));
        cards.add(new SetCardInfo("Spirit of the Hunt", "170s", Rarity.RARE, mage.cards.s.SpiritOfTheHunt.class));
        cards.add(new SetCardInfo("Splendid Reclamation", "171s", Rarity.RARE, mage.cards.s.SplendidReclamation.class));
        cards.add(new SetCardInfo("Stitcher's Graft", "200s", Rarity.RARE, mage.cards.s.StitchersGraft.class));
        cards.add(new SetCardInfo("Stromkirk Condemned", "106s", Rarity.RARE, mage.cards.s.StromkirkCondemned.class));
        cards.add(new SetCardInfo("Stromkirk Occultist", "146s", Rarity.RARE, mage.cards.s.StromkirkOccultist.class));
        cards.add(new SetCardInfo("Summary Dismissal", "75s", Rarity.RARE, mage.cards.s.SummaryDismissal.class));
        cards.add(new SetCardInfo("Tamiyo, Field Researcher", "190s", Rarity.MYTHIC, mage.cards.t.TamiyoFieldResearcher.class));
        cards.add(new SetCardInfo("Thalia's Lancers", "47s", Rarity.RARE, mage.cards.t.ThaliasLancers.class));
        cards.add(new SetCardInfo("Thalia, Heretic Cathar", 46, Rarity.RARE, mage.cards.t.ThaliaHereticCathar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thalia, Heretic Cathar", "46s", Rarity.RARE, mage.cards.t.ThaliaHereticCathar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tree of Perdition", "109s", Rarity.MYTHIC, mage.cards.t.TreeOfPerdition.class));
        cards.add(new SetCardInfo("Ulrich, Uncontested Alpha", "191s", Rarity.MYTHIC, mage.cards.u.UlrichUncontestedAlpha.class));
        cards.add(new SetCardInfo("Ulrich of the Krallenhorde", "191s", Rarity.MYTHIC, mage.cards.u.UlrichOfTheKrallenhorde.class));
        cards.add(new SetCardInfo("Ulvenwald Observer", 176, Rarity.RARE, mage.cards.u.UlvenwaldObserver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ulvenwald Observer", "176s", Rarity.RARE, mage.cards.u.UlvenwaldObserver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unsubstantiate", 79, Rarity.UNCOMMON, mage.cards.u.Unsubstantiate.class));
        cards.add(new SetCardInfo("Voldaren Pariah", "111s", Rarity.RARE, mage.cards.v.VoldarenPariah.class));
        cards.add(new SetCardInfo("Wharf Infiltrator", "80s", Rarity.RARE, mage.cards.w.WharfInfiltrator.class));
     }
}
