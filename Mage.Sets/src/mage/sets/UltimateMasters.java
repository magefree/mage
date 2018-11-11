
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author JayDi85
 */
public final class UltimateMasters extends ExpansionSet {

    private static final UltimateMasters instance = new UltimateMasters();

    public static UltimateMasters getInstance() {
        return instance;
    }

    private UltimateMasters() {
        super("Ultimate Masters", "UMA", ExpansionSet.buildDate(2018, 12, 7), SetType.SUPPLEMENTAL);
        this.blockName = "Reprint";
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;

        cards.add(new SetCardInfo("Ancient Tomb", 236, Rarity.RARE, mage.cards.a.AncientTomb.class));
        cards.add(new SetCardInfo("Balefire Dragon", 124, Rarity.MYTHIC, mage.cards.b.BalefireDragon.class));
        cards.add(new SetCardInfo("Bitterblossom", 85, Rarity.MYTHIC, mage.cards.b.Bitterblossom.class));
        cards.add(new SetCardInfo("Cavern of Souls", 237, Rarity.MYTHIC, mage.cards.c.CavernOfSouls.class));
        cards.add(new SetCardInfo("Celestial Colonnade", 238, Rarity.RARE, mage.cards.c.CelestialColonnade.class));
        cards.add(new SetCardInfo("Creeping Tar Pit", 239, Rarity.RARE, mage.cards.c.CreepingTarPit.class));
        cards.add(new SetCardInfo("Dark Depths", 241, Rarity.MYTHIC, mage.cards.d.DarkDepths.class));
        cards.add(new SetCardInfo("Demonic Tutor", 93, Rarity.RARE, mage.cards.d.DemonicTutor.class));
        cards.add(new SetCardInfo("Emrakul, the Aeons Torn", 4, Rarity.MYTHIC, mage.cards.e.EmrakulTheAeonsTorn.class));
        cards.add(new SetCardInfo("Engineered Explosives", 227, Rarity.RARE, mage.cards.e.EngineeredExplosives.class));
        cards.add(new SetCardInfo("Entomb", 94, Rarity.RARE, mage.cards.e.Entomb.class));
        cards.add(new SetCardInfo("Eternal Witness", 163, Rarity.UNCOMMON, mage.cards.e.EternalWitness.class));
        cards.add(new SetCardInfo("Fulminator Mage", 215, Rarity.RARE, mage.cards.f.FulminatorMage.class));
        cards.add(new SetCardInfo("Gaddock Teeg", 199, Rarity.RARE, mage.cards.g.GaddockTeeg.class));
        cards.add(new SetCardInfo("Goryo's Vengeance", 99, Rarity.RARE, mage.cards.g.GoryosVengeance.class));
        cards.add(new SetCardInfo("Karakas", 244, Rarity.MYTHIC, mage.cards.k.Karakas.class));
        cards.add(new SetCardInfo("Karn Liberated", 5, Rarity.MYTHIC, mage.cards.k.KarnLiberated.class));
        cards.add(new SetCardInfo("Kitchen Finks", 216, Rarity.UNCOMMON, mage.cards.k.KitchenFinks.class));
        cards.add(new SetCardInfo("Kozilek, Butcher of Truth", 6, Rarity.MYTHIC, mage.cards.k.KozilekButcherOfTruth.class));
        cards.add(new SetCardInfo("Lavaclaw Reaches", 245, Rarity.RARE, mage.cards.l.LavaclawReaches.class));
        cards.add(new SetCardInfo("Leovold, Emissary of Trest", 202, Rarity.MYTHIC, mage.cards.l.LeovoldEmissaryOfTrest.class));
        cards.add(new SetCardInfo("Life from the Loam", 172, Rarity.RARE, mage.cards.l.LifeFromTheLoam.class));
        cards.add(new SetCardInfo("Liliana of the Veil", 104, Rarity.MYTHIC, mage.cards.l.LilianaOfTheVeil.class));
        cards.add(new SetCardInfo("Lord of Extinction", 203, Rarity.MYTHIC, mage.cards.l.LordOfExtinction.class));
        cards.add(new SetCardInfo("Maelstrom Pulse", 204, Rarity.RARE, mage.cards.m.MaelstromPulse.class));
        cards.add(new SetCardInfo("Mana Vault", 229, Rarity.MYTHIC, mage.cards.m.ManaVault.class));
        cards.add(new SetCardInfo("Mikaeus, the Unhallowed", 106, Rarity.MYTHIC, mage.cards.m.MikaeusTheUnhallowed.class));
        cards.add(new SetCardInfo("Noble Hierarch", 174, Rarity.RARE, mage.cards.n.NobleHierarch.class));
        cards.add(new SetCardInfo("Platinum Emperion", 233, Rarity.MYTHIC, mage.cards.p.PlatinumEmperion.class));
        cards.add(new SetCardInfo("Raging Ravine", 249, Rarity.RARE, mage.cards.r.RagingRavine.class));
        cards.add(new SetCardInfo("Reanimate", 110, Rarity.RARE, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Sigarda, Host of Herons", 206, Rarity.MYTHIC, mage.cards.s.SigardaHostOfHerons.class));
        cards.add(new SetCardInfo("Snapcaster Mage", 71, Rarity.MYTHIC, mage.cards.s.SnapcasterMage.class));
        cards.add(new SetCardInfo("Stirring Wildwood", 251, Rarity.RARE, mage.cards.s.StirringWildwood.class));
        cards.add(new SetCardInfo("Tarmogoyf", 187, Rarity.MYTHIC, mage.cards.t.Tarmogoyf.class));
        cards.add(new SetCardInfo("Tasigur, the Golden Fang", 117, Rarity.RARE, mage.cards.t.TasigurTheGoldenFang.class));
        cards.add(new SetCardInfo("Temporal Manipulation", 77, Rarity.MYTHIC, mage.cards.t.TemporalManipulation.class));
        cards.add(new SetCardInfo("Through the Breach", 152, Rarity.RARE, mage.cards.t.ThroughTheBreach.class));
        cards.add(new SetCardInfo("Ulamog, the Infinite Gyre", 7, Rarity.MYTHIC, mage.cards.u.UlamogTheInfiniteGyre.class));
        cards.add(new SetCardInfo("Urborg, Tomb of Yawgmoth", 254, Rarity.RARE, mage.cards.u.UrborgTombOfYawgmoth.class));
        cards.add(new SetCardInfo("Vengevine", 189, Rarity.MYTHIC, mage.cards.v.Vengevine.class));
    }
}
