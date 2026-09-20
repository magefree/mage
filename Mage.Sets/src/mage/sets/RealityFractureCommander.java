package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class RealityFractureCommander extends ExpansionSet {

    private static final RealityFractureCommander instance = new RealityFractureCommander();

    public static RealityFractureCommander getInstance() {
        return instance;
    }

    private RealityFractureCommander() {
        super("Reality Fracture Commander", "FRC", ExpansionSet.buildDate(2026, 10, 2), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Akroma, Angel of Fury", 19, Rarity.MYTHIC, mage.cards.a.AkromaAngelOfFury.class));
        cards.add(new SetCardInfo("Arcane Signet", 20, Rarity.UNCOMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Archfiend of Despair", 46, Rarity.MYTHIC, mage.cards.a.ArchfiendOfDespair.class));
        cards.add(new SetCardInfo("Archon of Cruelty", 47, Rarity.MYTHIC, mage.cards.a.ArchonOfCruelty.class));
        cards.add(new SetCardInfo("Azorius Signet", 53, Rarity.UNCOMMON, mage.cards.a.AzoriusSignet.class));
        cards.add(new SetCardInfo("Battlefield Forge", 65, Rarity.RARE, mage.cards.b.BattlefieldForge.class));
        cards.add(new SetCardInfo("Brainstorm", 39, Rarity.COMMON, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Brainsurge", 40, Rarity.UNCOMMON, mage.cards.b.Brainsurge.class));
        cards.add(new SetCardInfo("Caves of Koilos", 66, Rarity.RARE, mage.cards.c.CavesOfKoilos.class));
        cards.add(new SetCardInfo("Chromatic Lantern", 54, Rarity.RARE, mage.cards.c.ChromaticLantern.class));
        cards.add(new SetCardInfo("Clifftop Retreat", 67, Rarity.RARE, mage.cards.c.ClifftopRetreat.class));
        cards.add(new SetCardInfo("Command Tower", 22, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Contaminated Landscape", 68, Rarity.COMMON, mage.cards.c.ContaminatedLandscape.class));
        cards.add(new SetCardInfo("Currency Converter", 55, Rarity.RARE, mage.cards.c.CurrencyConverter.class));
        cards.add(new SetCardInfo("Cursed Mirror", 49, Rarity.RARE, mage.cards.c.CursedMirror.class));
        cards.add(new SetCardInfo("Darksteel Angel", 98, Rarity.RARE, mage.cards.d.DarksteelAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Darksteel Angel", 13, Rarity.RARE, mage.cards.d.DarksteelAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Despark", 50, Rarity.UNCOMMON, mage.cards.d.Despark.class));
        cards.add(new SetCardInfo("Dimir Signet", 56, Rarity.UNCOMMON, mage.cards.d.DimirSignet.class));
        cards.add(new SetCardInfo("Dreadhorde Invasion", 48, Rarity.RARE, mage.cards.d.DreadhordeInvasion.class));
        cards.add(new SetCardInfo("Drowned Catacomb", 69, Rarity.RARE, mage.cards.d.DrownedCatacomb.class));
        cards.add(new SetCardInfo("Elspeth, Sun's Champion", 24, Rarity.MYTHIC, mage.cards.e.ElspethSunsChampion.class));
        cards.add(new SetCardInfo("Exotic Orchard", 70, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Fabled Passage", 71, Rarity.RARE, mage.cards.f.FabledPassage.class));
        cards.add(new SetCardInfo("Fact or Fiction", 41, Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Fellwar Stone", 57, Rarity.UNCOMMON, mage.cards.f.FellwarStone.class));
        cards.add(new SetCardInfo("Fetid Heath", 72, Rarity.RARE, mage.cards.f.FetidHeath.class));
        cards.add(new SetCardInfo("Flawless Maneuver", 25, Rarity.RARE, mage.cards.f.FlawlessManeuver.class));
        cards.add(new SetCardInfo("Ginger, Queen of Sweets", 14, Rarity.RARE, mage.cards.g.GingerQueenOfSweets.class));
        cards.add(new SetCardInfo("Glacial Fortress", 73, Rarity.RARE, mage.cards.g.GlacialFortress.class));
        cards.add(new SetCardInfo("Grand Crescendo", 26, Rarity.RARE, mage.cards.g.GrandCrescendo.class));
        cards.add(new SetCardInfo("Isolated Chapel", 74, Rarity.RARE, mage.cards.i.IsolatedChapel.class));
        cards.add(new SetCardInfo("Izzet Signet", 58, Rarity.UNCOMMON, mage.cards.i.IzzetSignet.class));
        cards.add(new SetCardInfo("Jhoira, Weatherlight Corsair", 8, Rarity.RARE, mage.cards.j.JhoiraWeatherlightCorsair.class));
        cards.add(new SetCardInfo("Kher Keep", 75, Rarity.RARE, mage.cards.k.KherKeep.class));
        cards.add(new SetCardInfo("Lingering Souls", 27, Rarity.UNCOMMON, mage.cards.l.LingeringSouls.class));
        cards.add(new SetCardInfo("Martial Coup", 28, Rarity.RARE, mage.cards.m.MartialCoup.class));
        cards.add(new SetCardInfo("Mass Polymorph", 42, Rarity.RARE, mage.cards.m.MassPolymorph.class));
        cards.add(new SetCardInfo("Memnarch, the Warden", 15, Rarity.RARE, mage.cards.m.MemnarchTheWarden.class));
        cards.add(new SetCardInfo("Mystic Gate", 76, Rarity.RARE, mage.cards.m.MysticGate.class));
        cards.add(new SetCardInfo("Niv-Mizzet, Ghost Counsel", 10, Rarity.RARE, mage.cards.n.NivMizzetGhostCounsel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Niv-Mizzet, Ghost Counsel", 95, Rarity.RARE, mage.cards.n.NivMizzetGhostCounsel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Occult Epiphany", 43, Rarity.RARE, mage.cards.o.OccultEpiphany.class));
        cards.add(new SetCardInfo("Overlord of the Mistmoors", 29, Rarity.MYTHIC, mage.cards.o.OverlordOfTheMistmoors.class));
        cards.add(new SetCardInfo("Path of Ancestry", 77, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Path to Exile", 30, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Perilous Landscape", 78, Rarity.COMMON, mage.cards.p.PerilousLandscape.class));
        cards.add(new SetCardInfo("Prairie Stream", 79, Rarity.RARE, mage.cards.p.PrairieStream.class));
        cards.add(new SetCardInfo("Proteus Staff", 59, Rarity.RARE, mage.cards.p.ProteusStaff.class));
        cards.add(new SetCardInfo("Radiant Summit", 80, Rarity.RARE, mage.cards.r.RadiantSummit.class));
        cards.add(new SetCardInfo("Rakdos Signet", 60, Rarity.UNCOMMON, mage.cards.r.RakdosSignet.class));
        cards.add(new SetCardInfo("Reflecting Pool", 23, Rarity.RARE, mage.cards.r.ReflectingPool.class));
        cards.add(new SetCardInfo("Restless Anchorage", 81, Rarity.RARE, mage.cards.r.RestlessAnchorage.class));
        cards.add(new SetCardInfo("Restless Spire", 82, Rarity.RARE, mage.cards.r.RestlessSpire.class));
        cards.add(new SetCardInfo("Secure the Wastes", 31, Rarity.RARE, mage.cards.s.SecureTheWastes.class));
        cards.add(new SetCardInfo("Serra's Emissary", 32, Rarity.MYTHIC, mage.cards.s.SerrasEmissary.class));
        cards.add(new SetCardInfo("Shark Typhoon", 44, Rarity.RARE, mage.cards.s.SharkTyphoon.class));
        cards.add(new SetCardInfo("Shivan Reef", 83, Rarity.RARE, mage.cards.s.ShivanReef.class));
        cards.add(new SetCardInfo("Skrelv's Hive", 33, Rarity.RARE, mage.cards.s.SkrelvsHive.class));
        cards.add(new SetCardInfo("Sol Ring", 21, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Staff of the Storyteller", 34, Rarity.RARE, mage.cards.s.StaffOfTheStoryteller.class));
        cards.add(new SetCardInfo("Stroke of Midnight", 35, Rarity.UNCOMMON, mage.cards.s.StrokeOfMidnight.class));
        cards.add(new SetCardInfo("Sulfur Falls", 84, Rarity.RARE, mage.cards.s.SulfurFalls.class));
        cards.add(new SetCardInfo("Sulfurous Springs", 85, Rarity.RARE, mage.cards.s.SulfurousSprings.class));
        cards.add(new SetCardInfo("Sunfall", 36, Rarity.RARE, mage.cards.s.Sunfall.class));
        cards.add(new SetCardInfo("Sunken Ruins", 86, Rarity.RARE, mage.cards.s.SunkenRuins.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 37, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Synthetic Destiny", 45, Rarity.RARE, mage.cards.s.SyntheticDestiny.class));
        cards.add(new SetCardInfo("Talisman of Creativity", 61, Rarity.UNCOMMON, mage.cards.t.TalismanOfCreativity.class));
        cards.add(new SetCardInfo("Talisman of Dominance", 62, Rarity.UNCOMMON, mage.cards.t.TalismanOfDominance.class));
        cards.add(new SetCardInfo("Talisman of Indulgence", 63, Rarity.UNCOMMON, mage.cards.t.TalismanOfIndulgence.class));
        cards.add(new SetCardInfo("Talisman of Progress", 64, Rarity.UNCOMMON, mage.cards.t.TalismanOfProgress.class));
        cards.add(new SetCardInfo("Turbulent Crater", 101, Rarity.RARE, mage.cards.t.TurbulentCrater.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Turbulent Crater", 16, Rarity.RARE, mage.cards.t.TurbulentCrater.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Turbulent Shore", 102, Rarity.RARE, mage.cards.t.TurbulentShore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Turbulent Shore", 17, Rarity.RARE, mage.cards.t.TurbulentShore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Turbulent Wetlands", 103, Rarity.RARE, mage.cards.t.TurbulentWetlands.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Turbulent Wetlands", 18, Rarity.RARE, mage.cards.t.TurbulentWetlands.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Underground River", 87, Rarity.RARE, mage.cards.u.UndergroundRiver.class));
        cards.add(new SetCardInfo("Whirlwind of Thought", 51, Rarity.RARE, mage.cards.w.WhirlwindOfThought.class));
        cards.add(new SetCardInfo("White Sun's Twilight", 38, Rarity.RARE, mage.cards.w.WhiteSunsTwilight.class));
        cards.add(new SetCardInfo("Windcrag Siege", 52, Rarity.RARE, mage.cards.w.WindcragSiege.class));
    }
}
