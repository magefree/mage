package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pw25
 */
public class WizardsPlayNetwork2025 extends ExpansionSet {

    private static final WizardsPlayNetwork2025 instance = new WizardsPlayNetwork2025();

    public static WizardsPlayNetwork2025 getInstance() {
        return instance;
    }

    private WizardsPlayNetwork2025() {
        super("Wizards Play Network 2025", "PW25", ExpansionSet.buildDate(2025, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Culling Ritual", 4, Rarity.RARE, mage.cards.c.CullingRitual.class));
        cards.add(new SetCardInfo("Despark", 2, Rarity.UNCOMMON, mage.cards.d.Despark.class));
        cards.add(new SetCardInfo("Dragon's Hoard", "1p", Rarity.RARE, mage.cards.d.DragonsHoard.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragonspeaker Shaman", 3, Rarity.RARE, mage.cards.d.DragonspeakerShaman.class));
        cards.add(new SetCardInfo("Monstrous Rage", 9, Rarity.RARE, mage.cards.m.MonstrousRage.class, RETRO_ART));
        cards.add(new SetCardInfo("Palladium Myr", 6, Rarity.RARE, mage.cards.p.PalladiumMyr.class, RETRO_ART));
        cards.add(new SetCardInfo("Rishkar's Expertise", 1, Rarity.RARE, mage.cards.r.RishkarsExpertise.class, RETRO_ART));
        cards.add(new SetCardInfo("Spectacular Spider-Man", 7, Rarity.RARE, mage.cards.s.SpectacularSpiderMan.class));
        cards.add(new SetCardInfo("Trinket Mage", 8, Rarity.RARE, mage.cards.t.TrinketMage.class, RETRO_ART));
        cards.add(new SetCardInfo("Zidane, Tantalus Thief", 5, Rarity.RARE, mage.cards.z.ZidaneTantalusThief.class));
    }
}
