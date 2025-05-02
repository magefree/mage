package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public final class TimeSpiralTimeshifted extends ExpansionSet {

    private static final TimeSpiralTimeshifted instance = new TimeSpiralTimeshifted();

    public static TimeSpiralTimeshifted getInstance() {
        return instance;
    }

    private TimeSpiralTimeshifted() {
        super("Time Spiral Timeshifted", "TSB", ExpansionSet.buildDate(2006, 9, 9), SetType.EXPANSION);
        this.blockName = "Time Spiral";
        this.parentSet = TimeSpiral.getInstance();
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Akroma, Angel of Wrath", 1, Rarity.SPECIAL, mage.cards.a.AkromaAngelOfWrath.class, RETRO_ART));
        cards.add(new SetCardInfo("Arena", 117, Rarity.SPECIAL, mage.cards.a.Arena.class, RETRO_ART));
        cards.add(new SetCardInfo("Assault // Battery", 106, Rarity.SPECIAL, mage.cards.a.AssaultBattery.class));
        cards.add(new SetCardInfo("Auratog", 2, Rarity.SPECIAL, mage.cards.a.Auratog.class, RETRO_ART));
        cards.add(new SetCardInfo("Avalanche Riders", 55, Rarity.SPECIAL, mage.cards.a.AvalancheRiders.class, RETRO_ART));
        cards.add(new SetCardInfo("Avatar of Woe", 37, Rarity.SPECIAL, mage.cards.a.AvatarOfWoe.class, RETRO_ART));
        cards.add(new SetCardInfo("Avoid Fate", 73, Rarity.SPECIAL, mage.cards.a.AvoidFate.class, RETRO_ART));
        cards.add(new SetCardInfo("Bad Moon", 38, Rarity.SPECIAL, mage.cards.b.BadMoon.class, RETRO_ART));
        cards.add(new SetCardInfo("Browbeat", 56, Rarity.SPECIAL, mage.cards.b.Browbeat.class, RETRO_ART));
        cards.add(new SetCardInfo("Call of the Herd", 74, Rarity.SPECIAL, mage.cards.c.CallOfTheHerd.class, RETRO_ART));
        cards.add(new SetCardInfo("Celestial Dawn", 3, Rarity.SPECIAL, mage.cards.c.CelestialDawn.class, RETRO_ART));
        cards.add(new SetCardInfo("Claws of Gix", 107, Rarity.SPECIAL, mage.cards.c.ClawsOfGix.class, RETRO_ART));
        cards.add(new SetCardInfo("Coalition Victory", 91, Rarity.SPECIAL, mage.cards.c.CoalitionVictory.class, RETRO_ART));
        cards.add(new SetCardInfo("Cockatrice", 75, Rarity.SPECIAL, mage.cards.c.Cockatrice.class, RETRO_ART));
        cards.add(new SetCardInfo("Consecrate Land", 4, Rarity.SPECIAL, mage.cards.c.ConsecrateLand.class, RETRO_ART));
        cards.add(new SetCardInfo("Conspiracy", 39, Rarity.SPECIAL, mage.cards.c.Conspiracy.class, RETRO_ART));
        cards.add(new SetCardInfo("Craw Giant", 76, Rarity.SPECIAL, mage.cards.c.CrawGiant.class, RETRO_ART));
        cards.add(new SetCardInfo("Dandan", 19, Rarity.SPECIAL, mage.cards.d.Dandan.class, RETRO_ART));
        cards.add(new SetCardInfo("Darkness", 40, Rarity.SPECIAL, mage.cards.d.Darkness.class, RETRO_ART));
        cards.add(new SetCardInfo("Dauthi Slayer", 41, Rarity.SPECIAL, mage.cards.d.DauthiSlayer.class, RETRO_ART));
        cards.add(new SetCardInfo("Defiant Vanguard", 5, Rarity.SPECIAL, mage.cards.d.DefiantVanguard.class, RETRO_ART));
        cards.add(new SetCardInfo("Desert", 118, Rarity.SPECIAL, mage.cards.d.Desert.class, RETRO_ART));
        cards.add(new SetCardInfo("Desolation Giant", 57, Rarity.SPECIAL, mage.cards.d.DesolationGiant.class, RETRO_ART));
        cards.add(new SetCardInfo("Disenchant", 6, Rarity.SPECIAL, mage.cards.d.Disenchant.class, RETRO_ART));
        cards.add(new SetCardInfo("Disintegrate", 58, Rarity.SPECIAL, mage.cards.d.Disintegrate.class, RETRO_ART));
        cards.add(new SetCardInfo("Dodecapod", 108, Rarity.SPECIAL, mage.cards.d.Dodecapod.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragon Whelp", 59, Rarity.SPECIAL, mage.cards.d.DragonWhelp.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragonstorm", 60, Rarity.SPECIAL, mage.cards.d.Dragonstorm.class, RETRO_ART));
        cards.add(new SetCardInfo("Enduring Renewal", 7, Rarity.SPECIAL, mage.cards.e.EnduringRenewal.class, RETRO_ART));
        cards.add(new SetCardInfo("Eron the Relentless", 61, Rarity.SPECIAL, mage.cards.e.EronTheRelentless.class, RETRO_ART));
        cards.add(new SetCardInfo("Essence Sliver", 8, Rarity.SPECIAL, mage.cards.e.EssenceSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Evil Eye of Orms-by-Gore", 42, Rarity.SPECIAL, mage.cards.e.EvilEyeOfOrmsByGore.class, RETRO_ART));
        cards.add(new SetCardInfo("Faceless Butcher", 43, Rarity.SPECIAL, mage.cards.f.FacelessButcher.class, RETRO_ART));
        cards.add(new SetCardInfo("Feldon's Cane", 109, Rarity.SPECIAL, mage.cards.f.FeldonsCane.class, RETRO_ART));
        cards.add(new SetCardInfo("Fiery Justice", 92, Rarity.SPECIAL, mage.cards.f.FieryJustice.class, RETRO_ART));
        cards.add(new SetCardInfo("Fiery Temper", 62, Rarity.SPECIAL, mage.cards.f.FieryTemper.class, RETRO_ART));
        cards.add(new SetCardInfo("Fire Whip", 63, Rarity.SPECIAL, mage.cards.f.FireWhip.class, RETRO_ART));
        cards.add(new SetCardInfo("Flying Men", 20, Rarity.SPECIAL, mage.cards.f.FlyingMen.class, RETRO_ART));
        cards.add(new SetCardInfo("Funeral Charm", 44, Rarity.SPECIAL, mage.cards.f.FuneralCharm.class, RETRO_ART));
        cards.add(new SetCardInfo("Gaea's Blessing", 77, Rarity.SPECIAL, mage.cards.g.GaeasBlessing.class, RETRO_ART));
        cards.add(new SetCardInfo("Gaea's Liege", 78, Rarity.SPECIAL, mage.cards.g.GaeasLiege.class, RETRO_ART));
        cards.add(new SetCardInfo("Gemstone Mine", 119, Rarity.SPECIAL, mage.cards.g.GemstoneMine.class, RETRO_ART));
        cards.add(new SetCardInfo("Ghost Ship", 21, Rarity.SPECIAL, mage.cards.g.GhostShip.class, RETRO_ART));
        cards.add(new SetCardInfo("Giant Oyster", 22, Rarity.SPECIAL, mage.cards.g.GiantOyster.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Snowman", 64, Rarity.SPECIAL, mage.cards.g.GoblinSnowman.class, RETRO_ART));
        cards.add(new SetCardInfo("Grinning Totem", 110, Rarity.SPECIAL, mage.cards.g.GrinningTotem.class, RETRO_ART));
        cards.add(new SetCardInfo("Hail Storm", 79, Rarity.SPECIAL, mage.cards.h.HailStorm.class, RETRO_ART));
        cards.add(new SetCardInfo("Honorable Passage", 9, Rarity.SPECIAL, mage.cards.h.HonorablePassage.class, RETRO_ART));
        cards.add(new SetCardInfo("Hunting Moa", 80, Rarity.SPECIAL, mage.cards.h.HuntingMoa.class, RETRO_ART));
        cards.add(new SetCardInfo("Icatian Javelineers", 10, Rarity.SPECIAL, mage.cards.i.IcatianJavelineers.class, RETRO_ART));
        cards.add(new SetCardInfo("Jasmine Boreal", 93, Rarity.SPECIAL, mage.cards.j.JasmineBoreal.class, RETRO_ART));
        cards.add(new SetCardInfo("Jolrael, Empress of Beasts", 81, Rarity.SPECIAL, mage.cards.j.JolraelEmpressOfBeasts.class, RETRO_ART));
        cards.add(new SetCardInfo("Kobold Taskmaster", 65, Rarity.SPECIAL, mage.cards.k.KoboldTaskmaster.class, RETRO_ART));
        cards.add(new SetCardInfo("Krosan Cloudscraper", 82, Rarity.SPECIAL, mage.cards.k.KrosanCloudscraper.class, RETRO_ART));
        cards.add(new SetCardInfo("Leviathan", 23, Rarity.SPECIAL, mage.cards.l.Leviathan.class, RETRO_ART));
        cards.add(new SetCardInfo("Lightning Angel", 94, Rarity.SPECIAL, mage.cards.l.LightningAngel.class, RETRO_ART));
        cards.add(new SetCardInfo("Lord of Atlantis", 24, Rarity.SPECIAL, mage.cards.l.LordOfAtlantis.class, RETRO_ART));
        cards.add(new SetCardInfo("Merfolk Assassin", 25, Rarity.SPECIAL, mage.cards.m.MerfolkAssassin.class, RETRO_ART));
        cards.add(new SetCardInfo("Merieke Ri Berit", 95, Rarity.SPECIAL, mage.cards.m.MeriekeRiBerit.class, RETRO_ART));
        cards.add(new SetCardInfo("Mindless Automaton", 111, Rarity.SPECIAL, mage.cards.m.MindlessAutomaton.class, RETRO_ART));
        cards.add(new SetCardInfo("Mirari", 112, Rarity.SPECIAL, mage.cards.m.Mirari.class, RETRO_ART));
        cards.add(new SetCardInfo("Mistform Ultimus", 26, Rarity.SPECIAL, mage.cards.m.MistformUltimus.class, RETRO_ART));
        cards.add(new SetCardInfo("Moorish Cavalry", 11, Rarity.SPECIAL, mage.cards.m.MoorishCavalry.class, RETRO_ART));
        cards.add(new SetCardInfo("Mystic Enforcer", 96, Rarity.SPECIAL, mage.cards.m.MysticEnforcer.class, RETRO_ART));
        cards.add(new SetCardInfo("Mystic Snake", 97, Rarity.SPECIAL, mage.cards.m.MysticSnake.class, RETRO_ART));
        cards.add(new SetCardInfo("Nicol Bolas", 98, Rarity.SPECIAL, mage.cards.n.NicolBolas.class, RETRO_ART));
        cards.add(new SetCardInfo("Orcish Librarian", 66, Rarity.SPECIAL, mage.cards.o.OrcishLibrarian.class, RETRO_ART));
        cards.add(new SetCardInfo("Orgg", 67, Rarity.SPECIAL, mage.cards.o.Orgg.class, RETRO_ART));
        cards.add(new SetCardInfo("Ovinomancer", 27, Rarity.SPECIAL, mage.cards.o.Ovinomancer.class, RETRO_ART));
        cards.add(new SetCardInfo("Pandemonium", 68, Rarity.SPECIAL, mage.cards.p.Pandemonium.class, RETRO_ART));
        cards.add(new SetCardInfo("Pendelhaven", 120, Rarity.SPECIAL, mage.cards.p.Pendelhaven.class, RETRO_ART));
        cards.add(new SetCardInfo("Pirate Ship", 28, Rarity.SPECIAL, mage.cards.p.PirateShip.class, RETRO_ART));
        cards.add(new SetCardInfo("Prodigal Sorcerer", 29, Rarity.SPECIAL, mage.cards.p.ProdigalSorcerer.class, RETRO_ART));
        cards.add(new SetCardInfo("Psionic Blast", 30, Rarity.SPECIAL, mage.cards.p.PsionicBlast.class, RETRO_ART));
        cards.add(new SetCardInfo("Resurrection", 12, Rarity.SPECIAL, mage.cards.r.Resurrection.class, RETRO_ART));
        cards.add(new SetCardInfo("Sacred Mesa", 13, Rarity.SPECIAL, mage.cards.s.SacredMesa.class, RETRO_ART));
        cards.add(new SetCardInfo("Safe Haven", 121, Rarity.SPECIAL, mage.cards.s.SafeHaven.class, RETRO_ART));
        cards.add(new SetCardInfo("Scragnoth", 83, Rarity.SPECIAL, mage.cards.s.Scragnoth.class, RETRO_ART));
        cards.add(new SetCardInfo("Sengir Autocrat", 45, Rarity.SPECIAL, mage.cards.s.SengirAutocrat.class, RETRO_ART));
        cards.add(new SetCardInfo("Serrated Arrows", 114, Rarity.SPECIAL, mage.cards.s.SerratedArrows.class, RETRO_ART));
        cards.add(new SetCardInfo("Shadow Guildmage", 46, Rarity.SPECIAL, mage.cards.s.ShadowGuildmage.class, RETRO_ART));
        cards.add(new SetCardInfo("Shadowmage Infiltrator", 99, Rarity.SPECIAL, mage.cards.s.ShadowmageInfiltrator.class, RETRO_ART));
        cards.add(new SetCardInfo("Sindbad", 31, Rarity.SPECIAL, mage.cards.s.Sindbad.class, RETRO_ART));
        cards.add(new SetCardInfo("Sol'kanar the Swamp King", 100, Rarity.SPECIAL, mage.cards.s.SolkanarTheSwampKing.class, RETRO_ART));
        cards.add(new SetCardInfo("Soltari Priest", 14, Rarity.SPECIAL, mage.cards.s.SoltariPriest.class, RETRO_ART));
        cards.add(new SetCardInfo("Soul Collector", 47, Rarity.SPECIAL, mage.cards.s.SoulCollector.class, RETRO_ART));
        cards.add(new SetCardInfo("Spike Feeder", 84, Rarity.SPECIAL, mage.cards.s.SpikeFeeder.class, RETRO_ART));
        cards.add(new SetCardInfo("Spined Sliver", 101, Rarity.SPECIAL, mage.cards.s.SpinedSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Spitting Slug", 85, Rarity.SPECIAL, mage.cards.s.SpittingSlug.class, RETRO_ART));
        cards.add(new SetCardInfo("Squire", 15, Rarity.SPECIAL, mage.cards.s.Squire.class, RETRO_ART));
        cards.add(new SetCardInfo("Stormbind", 102, Rarity.SPECIAL, mage.cards.s.Stormbind.class, RETRO_ART));
        cards.add(new SetCardInfo("Stormscape Familiar", 32, Rarity.SPECIAL, mage.cards.s.StormscapeFamiliar.class, RETRO_ART));
        cards.add(new SetCardInfo("Stupor", 48, Rarity.SPECIAL, mage.cards.s.Stupor.class, RETRO_ART));
        cards.add(new SetCardInfo("Suq'Ata Lancer", 69, Rarity.SPECIAL, mage.cards.s.SuqAtaLancer.class, RETRO_ART));
        cards.add(new SetCardInfo("Swamp Mosquito", 49, Rarity.SPECIAL, mage.cards.s.SwampMosquito.class, RETRO_ART));
        cards.add(new SetCardInfo("Teferi's Moat", 103, Rarity.SPECIAL, mage.cards.t.TeferisMoat.class, RETRO_ART));
        cards.add(new SetCardInfo("Thallid", 86, Rarity.SPECIAL, mage.cards.t.Thallid.class, RETRO_ART));
        cards.add(new SetCardInfo("The Rack", 113, Rarity.SPECIAL, mage.cards.t.TheRack.class, RETRO_ART));
        cards.add(new SetCardInfo("Thornscape Battlemage", 87, Rarity.SPECIAL, mage.cards.t.ThornscapeBattlemage.class, RETRO_ART));
        cards.add(new SetCardInfo("Tormod's Crypt", 115, Rarity.SPECIAL, mage.cards.t.TormodsCrypt.class, RETRO_ART));
        cards.add(new SetCardInfo("Tribal Flames", 70, Rarity.SPECIAL, mage.cards.t.TribalFlames.class, RETRO_ART));
        cards.add(new SetCardInfo("Twisted Abomination", 50, Rarity.SPECIAL, mage.cards.t.TwistedAbomination.class, RETRO_ART));
        cards.add(new SetCardInfo("Uncle Istvan", 51, Rarity.SPECIAL, mage.cards.u.UncleIstvan.class, RETRO_ART));
        cards.add(new SetCardInfo("Undead Warchief", 52, Rarity.SPECIAL, mage.cards.u.UndeadWarchief.class, RETRO_ART));
        cards.add(new SetCardInfo("Undertaker", 53, Rarity.SPECIAL, mage.cards.u.Undertaker.class, RETRO_ART));
        cards.add(new SetCardInfo("Unstable Mutation", 33, Rarity.SPECIAL, mage.cards.u.UnstableMutation.class, RETRO_ART));
        cards.add(new SetCardInfo("Uthden Troll", 71, Rarity.SPECIAL, mage.cards.u.UthdenTroll.class, RETRO_ART));
        cards.add(new SetCardInfo("Valor", 16, Rarity.SPECIAL, mage.cards.v.Valor.class, RETRO_ART));
        cards.add(new SetCardInfo("Verdeloth the Ancient", 88, Rarity.SPECIAL, mage.cards.v.VerdelothTheAncient.class, RETRO_ART));
        cards.add(new SetCardInfo("Vhati il-Dal", 104, Rarity.SPECIAL, mage.cards.v.VhatiIlDal.class, RETRO_ART));
        cards.add(new SetCardInfo("Void", 105, Rarity.SPECIAL, mage.cards.v.Void.class, RETRO_ART));
        cards.add(new SetCardInfo("Voidmage Prodigy", 34, Rarity.SPECIAL, mage.cards.v.VoidmageProdigy.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Roots", 89, Rarity.SPECIAL, mage.cards.w.WallOfRoots.class, RETRO_ART));
        cards.add(new SetCardInfo("War Barge", 116, Rarity.SPECIAL, mage.cards.w.WarBarge.class, RETRO_ART));
        cards.add(new SetCardInfo("Whirling Dervish", 90, Rarity.SPECIAL, mage.cards.w.WhirlingDervish.class, RETRO_ART));
        cards.add(new SetCardInfo("Whispers of the Muse", 35, Rarity.SPECIAL, mage.cards.w.WhispersOfTheMuse.class, RETRO_ART));
        cards.add(new SetCardInfo("Wildfire Emissary", 72, Rarity.SPECIAL, mage.cards.w.WildfireEmissary.class, RETRO_ART));
        cards.add(new SetCardInfo("Willbender", 36, Rarity.SPECIAL, mage.cards.w.Willbender.class, RETRO_ART));
        cards.add(new SetCardInfo("Witch Hunter", 17, Rarity.SPECIAL, mage.cards.w.WitchHunter.class, RETRO_ART));
        cards.add(new SetCardInfo("Withered Wretch", 54, Rarity.SPECIAL, mage.cards.w.WitheredWretch.class, RETRO_ART));
        cards.add(new SetCardInfo("Zhalfirin Commander", 18, Rarity.SPECIAL, mage.cards.z.ZhalfirinCommander.class, RETRO_ART));
    }
}
