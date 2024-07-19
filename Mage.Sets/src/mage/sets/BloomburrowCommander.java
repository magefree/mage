package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class BloomburrowCommander extends ExpansionSet {

    private static final BloomburrowCommander instance = new BloomburrowCommander();

    public static BloomburrowCommander getInstance() {
        return instance;
    }

    private BloomburrowCommander() {
        super("Bloomburrow Commander", "BLC", ExpansionSet.buildDate(2024, 8, 2), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Agate Instigator", 21, Rarity.RARE, mage.cards.a.AgateInstigator.class));
        cards.add(new SetCardInfo("Ant Queen", 80, Rarity.RARE, mage.cards.a.AntQueen.class));
        cards.add(new SetCardInfo("Arcane Signet", 127, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Baleful Strix", 86, Rarity.RARE, mage.cards.b.BalefulStrix.class));
        cards.add(new SetCardInfo("Beastmaster Ascension", 118, Rarity.RARE, mage.cards.b.BeastmasterAscension.class));
        cards.add(new SetCardInfo("Birds of Paradise", 81, Rarity.RARE, mage.cards.b.BirdsOfParadise.class));
        cards.add(new SetCardInfo("Blasphemous Act", 114, Rarity.RARE, mage.cards.b.BlasphemousAct.class));
        cards.add(new SetCardInfo("Boros Signet", 128, Rarity.UNCOMMON, mage.cards.b.BorosSignet.class));
        cards.add(new SetCardInfo("Calamity of Cinders", 23, Rarity.RARE, mage.cards.c.CalamityOfCinders.class));
        cards.add(new SetCardInfo("Casualties of War", 125, Rarity.RARE, mage.cards.c.CasualtiesOfWar.class));
        cards.add(new SetCardInfo("Chaos Warp", 115, Rarity.RARE, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Chart a Course", 110, Rarity.UNCOMMON, mage.cards.c.ChartACourse.class));
        cards.add(new SetCardInfo("Chatterfang, Squirrel General", 82, Rarity.MYTHIC, mage.cards.c.ChatterfangSquirrelGeneral.class));
        cards.add(new SetCardInfo("Command Tower", 130, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Cut a Deal", 105, Rarity.UNCOMMON, mage.cards.c.CutADeal.class));
        cards.add(new SetCardInfo("Derevi, Empyrial Tactician", 87, Rarity.RARE, mage.cards.d.DereviEmpyrialTactician.class));
        cards.add(new SetCardInfo("Domri, Anarch of Bolas", 98, Rarity.RARE, mage.cards.d.DomriAnarchOfBolas.class));
        cards.add(new SetCardInfo("Elspeth, Sun's Champion", 97, Rarity.MYTHIC, mage.cards.e.ElspethSunsChampion.class));
        cards.add(new SetCardInfo("Exotic Orchard", 131, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Explore", 216, Rarity.COMMON, mage.cards.e.Explore.class));
        cards.add(new SetCardInfo("Farseek", 119, Rarity.UNCOMMON, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Flubs, the Fool", 356, Rarity.MYTHIC, mage.cards.f.FlubsTheFool.class));
        cards.add(new SetCardInfo("Garruk, Cursed Huntsman", 99, Rarity.MYTHIC, mage.cards.g.GarrukCursedHuntsman.class));
        cards.add(new SetCardInfo("Generous Gift", 106, Rarity.UNCOMMON, mage.cards.g.GenerousGift.class));
        cards.add(new SetCardInfo("Gilded Goose", 83, Rarity.RARE, mage.cards.g.GildedGoose.class));
        cards.add(new SetCardInfo("Harmonize", 120, Rarity.UNCOMMON, mage.cards.h.Harmonize.class));
        cards.add(new SetCardInfo("Ink-Eyes, Servant of Oni", 77, Rarity.RARE, mage.cards.i.InkEyesServantOfOni.class));
        cards.add(new SetCardInfo("Ishai, Ojutai Dragonspeaker", 89, Rarity.MYTHIC, mage.cards.i.IshaiOjutaiDragonspeaker.class));
        cards.add(new SetCardInfo("Jace, the Mind Sculptor", 75, Rarity.MYTHIC, mage.cards.j.JaceTheMindSculptor.class));
        cards.add(new SetCardInfo("Karn, the Great Creator", 73, Rarity.MYTHIC, mage.cards.k.KarnTheGreatCreator.class));
        cards.add(new SetCardInfo("Kwain, Itinerant Meddler", 90, Rarity.RARE, mage.cards.k.KwainItinerantMeddler.class));
        cards.add(new SetCardInfo("Kykar, Wind's Fury", 91, Rarity.MYTHIC, mage.cards.k.KykarWindsFury.class));
        cards.add(new SetCardInfo("Liliana of the Dark Realms", 78, Rarity.MYTHIC, mage.cards.l.LilianaOfTheDarkRealms.class));
        cards.add(new SetCardInfo("Loyal Warhound", 107, Rarity.RARE, mage.cards.l.LoyalWarhound.class));
        cards.add(new SetCardInfo("Luminous Broodmoth", 74, Rarity.MYTHIC, mage.cards.l.LuminousBroodmoth.class));
        cards.add(new SetCardInfo("Maelstrom Pulse", 126, Rarity.RARE, mage.cards.m.MaelstromPulse.class));
        cards.add(new SetCardInfo("Marrow-Gnawer", 79, Rarity.RARE, mage.cards.m.MarrowGnawer.class));
        cards.add(new SetCardInfo("Martial Impetus", 108, Rarity.UNCOMMON, mage.cards.m.MartialImpetus.class));
        cards.add(new SetCardInfo("Narset, Parter of Veils", 76, Rarity.RARE, mage.cards.n.NarsetParterOfVeils.class));
        cards.add(new SetCardInfo("Nissa, Who Shakes the World", 84, Rarity.RARE, mage.cards.n.NissaWhoShakesTheWorld.class));
        cards.add(new SetCardInfo("Prosperous Innkeeper", 121, Rarity.UNCOMMON, mage.cards.p.ProsperousInnkeeper.class));
        cards.add(new SetCardInfo("Rapid Hybridization", 111, Rarity.UNCOMMON, mage.cards.r.RapidHybridization.class));
        cards.add(new SetCardInfo("Rites of Flourishing", 122, Rarity.RARE, mage.cards.r.RitesOfFlourishing.class));
        cards.add(new SetCardInfo("Saw in Half", 113, Rarity.RARE, mage.cards.s.SawInHalf.class));
        cards.add(new SetCardInfo("Second Harvest", 123, Rarity.RARE, mage.cards.s.SecondHarvest.class));
        cards.add(new SetCardInfo("Sol Ring", 129, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Squirrel Mob", 85, Rarity.RARE, mage.cards.s.SquirrelMob.class));
        cards.add(new SetCardInfo("Sunbird's Invocation", 116, Rarity.RARE, mage.cards.s.SunbirdsInvocation.class));
        cards.add(new SetCardInfo("Swarmyard", 133, Rarity.RARE, mage.cards.s.Swarmyard.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 109, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Tamiyo, Field Researcher", 100, Rarity.MYTHIC, mage.cards.t.TamiyoFieldResearcher.class));
        cards.add(new SetCardInfo("Teferi, Time Raveler", 92, Rarity.RARE, mage.cards.t.TeferiTimeRaveler.class));
        cards.add(new SetCardInfo("Tempt with Discovery", 124, Rarity.RARE, mage.cards.t.TemptWithDiscovery.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 345, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("The Gitrog Monster", 88, Rarity.MYTHIC, mage.cards.t.TheGitrogMonster.class));
        cards.add(new SetCardInfo("Warstorm Surge", 117, Rarity.RARE, mage.cards.w.WarstormSurge.class));
        cards.add(new SetCardInfo("Wizard Class", 112, Rarity.UNCOMMON, mage.cards.w.WizardClass.class));
    }
}
