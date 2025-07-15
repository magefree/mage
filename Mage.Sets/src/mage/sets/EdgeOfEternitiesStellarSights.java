package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author ReSech
 */
public final class EdgeOfEternitiesStellarSights extends ExpansionSet {

    private static final EdgeOfEternitiesStellarSights instance = new EdgeOfEternitiesStellarSights();

    public static EdgeOfEternitiesStellarSights getInstance() {
        return instance;
    }

    private EdgeOfEternitiesStellarSights() {
        super("Edge of Eternities: Stellar Sights", "EOS", ExpansionSet.buildDate(2025, 8, 1), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ancient Tomb", 1, Rarity.MYTHIC, mage.cards.a.AncientTomb.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Ancient Tomb", 46, Rarity.MYTHIC, mage.cards.a.AncientTomb.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Ancient Tomb", 91, Rarity.MYTHIC, mage.cards.a.AncientTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ancient Tomb", 136, Rarity.MYTHIC, mage.cards.a.AncientTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blinkmoth Nexus", 3, Rarity.RARE, mage.cards.b.BlinkmothNexus.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Blinkmoth Nexus", 48, Rarity.RARE, mage.cards.b.BlinkmothNexus.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Blinkmoth Nexus", 93, Rarity.RARE, mage.cards.b.BlinkmothNexus.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Blinkmoth Nexus", 138, Rarity.RARE, mage.cards.b.BlinkmothNexus.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Bonders' Enclave", 4, Rarity.RARE, mage.cards.b.BondersEnclave.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Bonders' Enclave", 49, Rarity.RARE, mage.cards.b.BondersEnclave.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Bonders' Enclave", 94, Rarity.RARE, mage.cards.b.BondersEnclave.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Bonders' Enclave", 139, Rarity.RARE, mage.cards.b.BondersEnclave.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Cascading Cataracts", 5, Rarity.RARE, mage.cards.c.CascadingCataracts.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Cascading Cataracts", 50, Rarity.RARE, mage.cards.c.CascadingCataracts.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Cathedral of War", 6, Rarity.RARE, mage.cards.c.CathedralOfWar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cathedral of War", 51, Rarity.RARE, mage.cards.c.CathedralOfWar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Celestial Colonnade", 7, Rarity.RARE, mage.cards.c.CelestialColonnade.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Celestial Colonnade", 52, Rarity.RARE, mage.cards.c.CelestialColonnade.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Celestial Colonnade", 97, Rarity.RARE, mage.cards.c.CelestialColonnade.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Celestial Colonnade", 142, Rarity.RARE, mage.cards.c.CelestialColonnade.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Contested War Zone", 8, Rarity.RARE, mage.cards.c.ContestedWarZone.class, FULL_ART));
        cards.add(new SetCardInfo("Creeping Tar Pit", 9, Rarity.RARE, mage.cards.c.CreepingTarPit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Creeping Tar Pit", 54, Rarity.RARE, mage.cards.c.CreepingTarPit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crystal Quarry", 10, Rarity.RARE, mage.cards.c.CrystalQuarry.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Crystal Quarry", 55, Rarity.RARE, mage.cards.c.CrystalQuarry.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Crystal Quarry", 100, Rarity.RARE, mage.cards.c.CrystalQuarry.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Crystal Quarry", 145, Rarity.RARE, mage.cards.c.CrystalQuarry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dust Bowl", 12, Rarity.MYTHIC, mage.cards.d.DustBowl.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Dust Bowl", 57, Rarity.MYTHIC, mage.cards.d.DustBowl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dust Bowl", 102, Rarity.MYTHIC, mage.cards.d.DustBowl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dust Bowl", 147, Rarity.MYTHIC, mage.cards.d.DustBowl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Echoing Deeps", 13, Rarity.RARE, mage.cards.e.EchoingDeeps.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Echoing Deeps", 58, Rarity.RARE, mage.cards.e.EchoingDeeps.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Echoing Deeps", 103, Rarity.RARE, mage.cards.e.EchoingDeeps.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Echoing Deeps", 148, Rarity.RARE, mage.cards.e.EchoingDeeps.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eldrazi Temple", 14, Rarity.RARE, mage.cards.e.EldraziTemple.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Eldrazi Temple", 59, Rarity.RARE, mage.cards.e.EldraziTemple.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Eldrazi Temple", 104, Rarity.RARE, mage.cards.e.EldraziTemple.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Eldrazi Temple", 149, Rarity.RARE, mage.cards.e.EldraziTemple.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Endless Sands", 15, Rarity.RARE, mage.cards.e.EndlessSands.class, FULL_ART));
        cards.add(new SetCardInfo("Gemstone Caverns", 16, Rarity.MYTHIC, mage.cards.g.GemstoneCaverns.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Gemstone Caverns", 61, Rarity.MYTHIC, mage.cards.g.GemstoneCaverns.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Gemstone Caverns", 106, Rarity.MYTHIC, mage.cards.g.GemstoneCaverns.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Gemstone Caverns", 151, Rarity.MYTHIC, mage.cards.g.GemstoneCaverns.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Grove of the Burnwillows", 17, Rarity.MYTHIC, mage.cards.g.GroveOfTheBurnwillows.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grove of the Burnwillows", 62, Rarity.MYTHIC, mage.cards.g.GroveOfTheBurnwillows.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("High Market", 18, Rarity.RARE, mage.cards.h.HighMarket.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("High Market", 63, Rarity.RARE, mage.cards.h.HighMarket.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("High Market", 108, Rarity.RARE, mage.cards.h.HighMarket.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("High Market", 153, Rarity.RARE, mage.cards.h.HighMarket.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Hissing Quagmire", 19, Rarity.RARE, mage.cards.h.HissingQuagmire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hissing Quagmire", 64, Rarity.RARE, mage.cards.h.HissingQuagmire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inkmoth Nexus", 20, Rarity.MYTHIC, mage.cards.i.InkmothNexus.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Inkmoth Nexus", 65, Rarity.MYTHIC, mage.cards.i.InkmothNexus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inkmoth Nexus", 110, Rarity.MYTHIC, mage.cards.i.InkmothNexus.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Inkmoth Nexus", 155, Rarity.MYTHIC, mage.cards.i.InkmothNexus.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Inventors' Fair", 21, Rarity.MYTHIC, mage.cards.i.InventorsFair.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Inventors' Fair", 66, Rarity.MYTHIC, mage.cards.i.InventorsFair.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Lavaclaw Reaches", 22, Rarity.RARE, mage.cards.l.LavaclawReaches.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lavaclaw Reaches", 67, Rarity.RARE, mage.cards.l.LavaclawReaches.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lotus Field", 23, Rarity.MYTHIC, mage.cards.l.LotusField.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lotus Field", 68, Rarity.MYTHIC, mage.cards.l.LotusField.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lumbering Falls", 24, Rarity.RARE, mage.cards.l.LumberingFalls.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lumbering Falls", 69, Rarity.RARE, mage.cards.l.LumberingFalls.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Confluence", 25, Rarity.MYTHIC, mage.cards.m.ManaConfluence.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Confluence", 70, Rarity.MYTHIC, mage.cards.m.ManaConfluence.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Confluence", 115, Rarity.MYTHIC, mage.cards.m.ManaConfluence.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Confluence", 160, Rarity.MYTHIC, mage.cards.m.ManaConfluence.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Meteor Crater", 26, Rarity.RARE, mage.cards.m.MeteorCrater.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Meteor Crater", 71, Rarity.RARE, mage.cards.m.MeteorCrater.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Meteor Crater", 116, Rarity.RARE, mage.cards.m.MeteorCrater.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Meteor Crater", 161, Rarity.RARE, mage.cards.m.MeteorCrater.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mirrorpool", 27, Rarity.MYTHIC, mage.cards.m.Mirrorpool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mirrorpool", 72, Rarity.MYTHIC, mage.cards.m.Mirrorpool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mutavault", 28, Rarity.MYTHIC, mage.cards.m.Mutavault.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mutavault", 73, Rarity.MYTHIC, mage.cards.m.Mutavault.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mutavault", 118, Rarity.MYTHIC, mage.cards.m.Mutavault.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mutavault", 163, Rarity.MYTHIC, mage.cards.m.Mutavault.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystifying Maze", 29, Rarity.RARE, mage.cards.m.MystifyingMaze.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystifying Maze", 74, Rarity.RARE, mage.cards.m.MystifyingMaze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystifying Maze", 119, Rarity.RARE, mage.cards.m.MystifyingMaze.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystifying Maze", 164, Rarity.RARE, mage.cards.m.MystifyingMaze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Needle Spires", 30, Rarity.RARE, mage.cards.n.NeedleSpires.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Needle Spires", 75, Rarity.RARE, mage.cards.n.NeedleSpires.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nesting Grounds", 31, Rarity.RARE, mage.cards.n.NestingGrounds.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Nesting Grounds", 76, Rarity.RARE, mage.cards.n.NestingGrounds.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Nesting Grounds", 121, Rarity.RARE, mage.cards.n.NestingGrounds.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Nesting Grounds", 166, Rarity.RARE, mage.cards.n.NestingGrounds.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Petrified Field", 32, Rarity.MYTHIC, mage.cards.p.PetrifiedField.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Petrified Field", 77, Rarity.MYTHIC, mage.cards.p.PetrifiedField.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plaza of Heroes", 33, Rarity.MYTHIC, mage.cards.p.PlazaOfHeroes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plaza of Heroes", 78, Rarity.MYTHIC, mage.cards.p.PlazaOfHeroes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plaza of Heroes", 123, Rarity.MYTHIC, mage.cards.p.PlazaOfHeroes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plaza of Heroes", 168, Rarity.MYTHIC, mage.cards.p.PlazaOfHeroes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Power Depot", 34, Rarity.RARE, mage.cards.p.PowerDepot.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Power Depot", 79, Rarity.RARE, mage.cards.p.PowerDepot.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raging Ravine", 35, Rarity.RARE, mage.cards.r.RagingRavine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raging Ravine", 80, Rarity.RARE, mage.cards.r.RagingRavine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reflecting Pool", 36, Rarity.MYTHIC, mage.cards.r.ReflectingPool.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Reflecting Pool", 81, Rarity.MYTHIC, mage.cards.r.ReflectingPool.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Reflecting Pool", 126, Rarity.MYTHIC, mage.cards.r.ReflectingPool.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Reflecting Pool", 171, Rarity.MYTHIC, mage.cards.r.ReflectingPool.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Scavenger Grounds", 37, Rarity.RARE, mage.cards.s.ScavengerGrounds.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Scavenger Grounds", 82, Rarity.RARE, mage.cards.s.ScavengerGrounds.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Shambling Vent", 38, Rarity.RARE, mage.cards.s.ShamblingVent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shambling Vent", 83, Rarity.RARE, mage.cards.s.ShamblingVent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stirring Wildwood", 39, Rarity.RARE, mage.cards.s.StirringWildwood.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stirring Wildwood", 84, Rarity.RARE, mage.cards.s.StirringWildwood.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strip Mine", 40, Rarity.MYTHIC, mage.cards.s.StripMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strip Mine", 85, Rarity.MYTHIC, mage.cards.s.StripMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strip Mine", 130, Rarity.MYTHIC, mage.cards.s.StripMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strip Mine", 173, Rarity.MYTHIC, mage.cards.s.StripMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strip Mine", 175, Rarity.MYTHIC, mage.cards.s.StripMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sunken Citadel", 41, Rarity.RARE, mage.cards.s.SunkenCitadel.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Sunken Citadel", 86, Rarity.RARE, mage.cards.s.SunkenCitadel.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Sunken Citadel", 131, Rarity.RARE, mage.cards.s.SunkenCitadel.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Sunken Citadel", 176, Rarity.RARE, mage.cards.s.SunkenCitadel.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarmyard", 42, Rarity.RARE, mage.cards.s.Swarmyard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarmyard", 87, Rarity.RARE, mage.cards.s.Swarmyard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarmyard", 132, Rarity.RARE, mage.cards.s.Swarmyard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarmyard", 177, Rarity.RARE, mage.cards.s.Swarmyard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terrain Generator", 43, Rarity.RARE, mage.cards.t.TerrainGenerator.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Terrain Generator", 88, Rarity.RARE, mage.cards.t.TerrainGenerator.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Terrain Generator", 133, Rarity.RARE, mage.cards.t.TerrainGenerator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terrain Generator", 178, Rarity.RARE, mage.cards.t.TerrainGenerator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thespian's Stage", 44, Rarity.RARE, mage.cards.t.ThespiansStage.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Thespian's Stage", 89, Rarity.RARE, mage.cards.t.ThespiansStage.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Thespian's Stage", 134, Rarity.RARE, mage.cards.t.ThespiansStage.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Thespian's Stage", 179, Rarity.RARE, mage.cards.t.ThespiansStage.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Wandering Fumarole", 45, Rarity.RARE, mage.cards.w.WanderingFumarole.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wandering Fumarole", 90, Rarity.RARE, mage.cards.w.WanderingFumarole.class, NON_FULL_USE_VARIOUS));
    }
}
