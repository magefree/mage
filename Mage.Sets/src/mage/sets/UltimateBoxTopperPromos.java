
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author JayDi85
 */
public final class UltimateBoxTopperPromos extends ExpansionSet {

    private static final UltimateBoxTopperPromos instance = new UltimateBoxTopperPromos();

    public static UltimateBoxTopperPromos getInstance() {
        return instance;
    }

    private UltimateBoxTopperPromos() {
        super("Ultimate Box Topper Promos", "PUMA", ExpansionSet.buildDate(2018, 12, 7), SetType.PROMOTIONAL);
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Ancient Tomb", "U31", Rarity.MYTHIC, mage.cards.a.AncientTomb.class));
        cards.add(new SetCardInfo("Balefire Dragon", "U14", Rarity.MYTHIC, mage.cards.b.BalefireDragon.class));
        cards.add(new SetCardInfo("Bitterblossom", "U7", Rarity.MYTHIC, mage.cards.b.Bitterblossom.class));
        cards.add(new SetCardInfo("Cavern of Souls", "U32", Rarity.MYTHIC, mage.cards.c.CavernOfSouls.class));
        cards.add(new SetCardInfo("Celestial Colonnade", "U33", Rarity.MYTHIC, mage.cards.c.CelestialColonnade.class));
        cards.add(new SetCardInfo("Creeping Tar Pit", "U34", Rarity.MYTHIC, mage.cards.c.CreepingTarPit.class));
        cards.add(new SetCardInfo("Dark Depths", "U35", Rarity.MYTHIC, mage.cards.d.DarkDepths.class));
        cards.add(new SetCardInfo("Demonic Tutor", "U8", Rarity.MYTHIC, mage.cards.d.DemonicTutor.class));
        cards.add(new SetCardInfo("Emrakul, the Aeons Torn", "U1", Rarity.MYTHIC, mage.cards.e.EmrakulTheAeonsTorn.class));
        cards.add(new SetCardInfo("Engineered Explosives", "U28", Rarity.MYTHIC, mage.cards.e.EngineeredExplosives.class));
        cards.add(new SetCardInfo("Eternal Witness", "U16", Rarity.MYTHIC, mage.cards.e.EternalWitness.class));
        cards.add(new SetCardInfo("Fulminator Mage", "U26", Rarity.MYTHIC, mage.cards.f.FulminatorMage.class));
        cards.add(new SetCardInfo("Gaddock Teeg", "U21", Rarity.MYTHIC, mage.cards.g.GaddockTeeg.class));
        cards.add(new SetCardInfo("Goryo's Vengeance", "U9", Rarity.MYTHIC, mage.cards.g.GoryosVengeance.class));
        cards.add(new SetCardInfo("Karakas", "U36", Rarity.MYTHIC, mage.cards.k.Karakas.class));
        cards.add(new SetCardInfo("Karn Liberated", "U2", Rarity.MYTHIC, mage.cards.k.KarnLiberated.class));
        cards.add(new SetCardInfo("Kitchen Finks", "U27", Rarity.MYTHIC, mage.cards.k.KitchenFinks.class));
        cards.add(new SetCardInfo("Kozilek, Butcher of Truth", "U3", Rarity.MYTHIC, mage.cards.k.KozilekButcherOfTruth.class));
        cards.add(new SetCardInfo("Lavaclaw Reaches", "U37", Rarity.MYTHIC, mage.cards.l.LavaclawReaches.class));
        cards.add(new SetCardInfo("Leovold, Emissary of Trest", "U22", Rarity.MYTHIC, mage.cards.l.LeovoldEmissaryOfTrest.class));
        cards.add(new SetCardInfo("Life from the Loam", "U17", Rarity.MYTHIC, mage.cards.l.LifeFromTheLoam.class));
        cards.add(new SetCardInfo("Liliana of the Veil", "U10", Rarity.MYTHIC, mage.cards.l.LilianaOfTheVeil.class));
        cards.add(new SetCardInfo("Lord of Extinction", "U23", Rarity.MYTHIC, mage.cards.l.LordOfExtinction.class));
        cards.add(new SetCardInfo("Maelstrom Pulse", "U24", Rarity.MYTHIC, mage.cards.m.MaelstromPulse.class));
        cards.add(new SetCardInfo("Mana Vault", "U29", Rarity.MYTHIC, mage.cards.m.ManaVault.class));
        cards.add(new SetCardInfo("Mikaeus, the Unhallowed", "U11", Rarity.MYTHIC, mage.cards.m.MikaeusTheUnhallowed.class));
        cards.add(new SetCardInfo("Noble Hierarch", "U18", Rarity.MYTHIC, mage.cards.n.NobleHierarch.class));
        cards.add(new SetCardInfo("Platinum Emperion", "U30", Rarity.MYTHIC, mage.cards.p.PlatinumEmperion.class));
        cards.add(new SetCardInfo("Raging Ravine", "U38", Rarity.MYTHIC, mage.cards.r.RagingRavine.class));
        cards.add(new SetCardInfo("Reanimate", "U12", Rarity.MYTHIC, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Sigarda, Host of Herons", "U25", Rarity.MYTHIC, mage.cards.s.SigardaHostOfHerons.class));
        cards.add(new SetCardInfo("Snapcaster Mage", "U5", Rarity.MYTHIC, mage.cards.s.SnapcasterMage.class));
        cards.add(new SetCardInfo("Stirring Wildwood", "U39", Rarity.MYTHIC, mage.cards.s.StirringWildwood.class));
        cards.add(new SetCardInfo("Tarmogoyf", "U19", Rarity.MYTHIC, mage.cards.t.Tarmogoyf.class));
        cards.add(new SetCardInfo("Tasigur, the Golden Fang", "U13", Rarity.MYTHIC, mage.cards.t.TasigurTheGoldenFang.class));
        cards.add(new SetCardInfo("Temporal Manipulation", "U6", Rarity.MYTHIC, mage.cards.t.TemporalManipulation.class));
        cards.add(new SetCardInfo("Through the Breach", "U15", Rarity.MYTHIC, mage.cards.t.ThroughTheBreach.class));
        cards.add(new SetCardInfo("Ulamog, the Infinite Gyre", "U4", Rarity.MYTHIC, mage.cards.u.UlamogTheInfiniteGyre.class));
        cards.add(new SetCardInfo("Urborg, Tomb of Yawgmoth", "U40", Rarity.MYTHIC, mage.cards.u.UrborgTombOfYawgmoth.class));
        cards.add(new SetCardInfo("Vengevine", "U20", Rarity.MYTHIC, mage.cards.v.Vengevine.class));
    }
}
