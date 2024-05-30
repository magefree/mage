package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ModernHorizons3Commander extends ExpansionSet {

    private static final ModernHorizons3Commander instance = new ModernHorizons3Commander();

    public static ModernHorizons3Commander getInstance() {
        return instance;
    }

    private ModernHorizons3Commander() {
        super("Modern Horizons 3 Commander", "M3C", ExpansionSet.buildDate(2024, 6, 7), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Adarkar Wastes", 316, Rarity.RARE, mage.cards.a.AdarkarWastes.class));
        cards.add(new SetCardInfo("Aether Hub", 317, Rarity.UNCOMMON, mage.cards.a.AetherHub.class));
        cards.add(new SetCardInfo("Aethergeode Miner", 193, Rarity.RARE, mage.cards.a.AethergeodeMiner.class));
        cards.add(new SetCardInfo("Aethersphere Harvester", 280, Rarity.RARE, mage.cards.a.AethersphereHarvester.class));
        cards.add(new SetCardInfo("Aethersquall Ancient", 174, Rarity.RARE, mage.cards.a.AethersquallAncient.class));
        cards.add(new SetCardInfo("Aetherstorm Roc", 164, Rarity.RARE, mage.cards.a.AetherstormRoc.class));
        cards.add(new SetCardInfo("Aethertide Whale", 175, Rarity.RARE, mage.cards.a.AethertideWhale.class));
        cards.add(new SetCardInfo("Aetherworks Marvel", 281, Rarity.MYTHIC, mage.cards.a.AetherworksMarvel.class));
        cards.add(new SetCardInfo("Akroma's Will", 165, Rarity.RARE, mage.cards.a.AkromasWill.class));
        cards.add(new SetCardInfo("Angel of Invention", 166, Rarity.MYTHIC, mage.cards.a.AngelOfInvention.class));
        cards.add(new SetCardInfo("Apex Devastator", 220, Rarity.MYTHIC, mage.cards.a.ApexDevastator.class));
        cards.add(new SetCardInfo("Arcane Signet", 283, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Archon of Cruelty", 197, Rarity.MYTHIC, mage.cards.a.ArchonOfCruelty.class));
        cards.add(new SetCardInfo("Austere Command", 167, Rarity.RARE, mage.cards.a.AustereCommand.class));
        cards.add(new SetCardInfo("Azlask, the Swelling Scourge", 5, Rarity.MYTHIC, mage.cards.a.AzlaskTheSwellingScourge.class));
        cards.add(new SetCardInfo("Azorius Chancery", 319, Rarity.COMMON, mage.cards.a.AzoriusChancery.class));
        cards.add(new SetCardInfo("Barrowgoyf", 50, Rarity.RARE, mage.cards.b.Barrowgoyf.class));
        cards.add(new SetCardInfo("Battlefield Forge", 321, Rarity.RARE, mage.cards.b.BattlefieldForge.class));
        cards.add(new SetCardInfo("Bident of Thassa", 177, Rarity.RARE, mage.cards.b.BidentOfThassa.class));
        cards.add(new SetCardInfo("Bloodbraid Challenger", 70, Rarity.RARE, mage.cards.b.BloodbraidChallenger.class));
        cards.add(new SetCardInfo("Brudiclad, Telchor Engineer", 257, Rarity.MYTHIC, mage.cards.b.BrudicladTelchorEngineer.class));
        cards.add(new SetCardInfo("Burnished Hart", 284, Rarity.UNCOMMON, mage.cards.b.BurnishedHart.class));
        cards.add(new SetCardInfo("Castle Vantress", 327, Rarity.RARE, mage.cards.c.CastleVantress.class));
        cards.add(new SetCardInfo("Cayth, Famed Mechanist", 6, Rarity.MYTHIC, mage.cards.c.CaythFamedMechanist.class));
        cards.add(new SetCardInfo("Coalition Relic", 286, Rarity.RARE, mage.cards.c.CoalitionRelic.class));
        cards.add(new SetCardInfo("Combustible Gearhulk", 210, Rarity.MYTHIC, mage.cards.c.CombustibleGearhulk.class));
        cards.add(new SetCardInfo("Command Tower", 331, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Confiscation Coup", 178, Rarity.RARE, mage.cards.c.ConfiscationCoup.class));
        cards.add(new SetCardInfo("Conversion Apparatus", 76, Rarity.RARE, mage.cards.c.ConversionApparatus.class));
        cards.add(new SetCardInfo("Coveted Jewel", 287, Rarity.RARE, mage.cards.c.CovetedJewel.class));
        cards.add(new SetCardInfo("Crib Swap", 168, Rarity.UNCOMMON, mage.cards.c.CribSwap.class));
        cards.add(new SetCardInfo("Decoction Module", 288, Rarity.UNCOMMON, mage.cards.d.DecoctionModule.class));
        cards.add(new SetCardInfo("Demolition Field", 335, Rarity.UNCOMMON, mage.cards.d.DemolitionField.class));
        cards.add(new SetCardInfo("Drowner of Hope", 182, Rarity.RARE, mage.cards.d.DrownerOfHope.class));
        cards.add(new SetCardInfo("Era of Innovation", 183, Rarity.UNCOMMON, mage.cards.e.EraOfInnovation.class));
        cards.add(new SetCardInfo("Farewell", 170, Rarity.RARE, mage.cards.f.Farewell.class));
        cards.add(new SetCardInfo("Final Act", 52, Rarity.RARE, mage.cards.f.FinalAct.class));
        cards.add(new SetCardInfo("Frostboil Snarl", 334, Rarity.RARE, mage.cards.f.FrostboilSnarl.class));
        cards.add(new SetCardInfo("Furycalm Snarl", 345, Rarity.RARE, mage.cards.f.FurycalmSnarl.class));
        cards.add(new SetCardInfo("Glimmer of Genius", 187, Rarity.UNCOMMON, mage.cards.g.GlimmerOfGenius.class));
        cards.add(new SetCardInfo("Goldspan Dragon", 212, Rarity.MYTHIC, mage.cards.g.GoldspanDragon.class));
        cards.add(new SetCardInfo("Gonti's Aether Heart", 294, Rarity.MYTHIC, mage.cards.g.GontisAetherHeart.class));
        cards.add(new SetCardInfo("Grenzo, Havoc Raiser", 213, Rarity.RARE, mage.cards.g.GrenzoHavocRaiser.class));
        cards.add(new SetCardInfo("Hideous Taskmaster", 57, Rarity.RARE, mage.cards.h.HideousTaskmaster.class));
        cards.add(new SetCardInfo("Izzet Boilerworks", 350, Rarity.UNCOMMON, mage.cards.i.IzzetBoilerworks.class));
        cards.add(new SetCardInfo("Jyoti, Moag Ancient", 8, Rarity.MYTHIC, mage.cards.j.JyotiMoagAncient.class));
        cards.add(new SetCardInfo("Legion Loyalty", 171, Rarity.MYTHIC, mage.cards.l.LegionLoyalty.class));
        cards.add(new SetCardInfo("Lightning Runner", 215, Rarity.MYTHIC, mage.cards.l.LightningRunner.class));
        cards.add(new SetCardInfo("March from Velis Vel", 48, Rarity.RARE, mage.cards.m.MarchFromVelisVel.class));
        cards.add(new SetCardInfo("Midnight Clock", 189, Rarity.RARE, mage.cards.m.MidnightClock.class));
        cards.add(new SetCardInfo("Myr Battlesphere", 301, Rarity.RARE, mage.cards.m.MyrBattlesphere.class));
        cards.add(new SetCardInfo("Mystic Gate", 359, Rarity.RARE, mage.cards.m.MysticGate.class));
        cards.add(new SetCardInfo("Mystic Monastery", 360, Rarity.UNCOMMON, mage.cards.m.MysticMonastery.class));
        cards.add(new SetCardInfo("Oblivion Sower", 158, Rarity.MYTHIC, mage.cards.o.OblivionSower.class));
        cards.add(new SetCardInfo("Port Town", 364, Rarity.RARE, mage.cards.p.PortTown.class));
        cards.add(new SetCardInfo("Prairie Stream", 365, Rarity.RARE, mage.cards.p.PrairieStream.class));
        cards.add(new SetCardInfo("Professional Face-Breaker", 216, Rarity.RARE, mage.cards.p.ProfessionalFaceBreaker.class));
        cards.add(new SetCardInfo("Pyrogoyf", 59, Rarity.RARE, mage.cards.p.Pyrogoyf.class));
        cards.add(new SetCardInfo("Salvation Colossus", 43, Rarity.RARE, mage.cards.s.SalvationColossus.class));
        cards.add(new SetCardInfo("Shivan Reef", 375, Rarity.RARE, mage.cards.s.ShivanReef.class));
        cards.add(new SetCardInfo("Siege-Gang Lieutenant", 61, Rarity.RARE, mage.cards.s.SiegeGangLieutenant.class));
        cards.add(new SetCardInfo("Skyclave Apparition", 172, Rarity.RARE, mage.cards.s.SkyclaveApparition.class));
        cards.add(new SetCardInfo("Sol Ring", 305, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 306, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 173, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Talisman of Conviction", 307, Rarity.UNCOMMON, mage.cards.t.TalismanOfConviction.class));
        cards.add(new SetCardInfo("Talisman of Creativity", 308, Rarity.UNCOMMON, mage.cards.t.TalismanOfCreativity.class));
        cards.add(new SetCardInfo("Talisman of Progress", 313, Rarity.UNCOMMON, mage.cards.t.TalismanOfProgress.class));
        cards.add(new SetCardInfo("Temple of Enlightenment", 386, Rarity.RARE, mage.cards.t.TempleOfEnlightenment.class));
        cards.add(new SetCardInfo("Temple of Epiphany", 387, Rarity.RARE, mage.cards.t.TempleOfEpiphany.class));
        cards.add(new SetCardInfo("Temple of Triumph", 392, Rarity.RARE, mage.cards.t.TempleOfTriumph.class));
        cards.add(new SetCardInfo("Tezzeret's Gambit", 194, Rarity.RARE, mage.cards.t.TezzeretsGambit.class));
        cards.add(new SetCardInfo("Wayfarer's Bauble", 315, Rarity.COMMON, mage.cards.w.WayfarersBauble.class));
        cards.add(new SetCardInfo("Whirler Virtuoso", 278, Rarity.UNCOMMON, mage.cards.w.WhirlerVirtuoso.class));
    }
}
