package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class TheBigScore extends ExpansionSet {

    private static final TheBigScore instance = new TheBigScore();

    public static TheBigScore getInstance() {
        return instance;
    }

    private TheBigScore() {
        super("The Big Score", "BIG", ExpansionSet.buildDate(2024, 4, 19), SetType.SUPPLEMENTAL_STANDARD_LEGAL);
        this.blockName = "Outlaws of Thunder Junction";
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Ancient Cornucopia", 16, Rarity.MYTHIC, mage.cards.a.AncientCornucopia.class));
        cards.add(new SetCardInfo("Bristlebud Farmer", 17, Rarity.MYTHIC, mage.cards.b.BristlebudFarmer.class));
        cards.add(new SetCardInfo("Collector's Cage", 1, Rarity.MYTHIC, mage.cards.c.CollectorsCage.class));
        cards.add(new SetCardInfo("Esoteric Duplicator", 5, Rarity.MYTHIC, mage.cards.e.EsotericDuplicator.class));
        cards.add(new SetCardInfo("Generous Plunderer", 11, Rarity.MYTHIC, mage.cards.g.GenerousPlunderer.class));
        cards.add(new SetCardInfo("Grand Abolisher", 2, Rarity.MYTHIC, mage.cards.g.GrandAbolisher.class));
        cards.add(new SetCardInfo("Greed's Gambit", 8, Rarity.MYTHIC, mage.cards.g.GreedsGambit.class));
        cards.add(new SetCardInfo("Harvester of Misery", 9, Rarity.MYTHIC, mage.cards.h.HarvesterOfMisery.class));
        cards.add(new SetCardInfo("Hostile Investigator", 10, Rarity.MYTHIC, mage.cards.h.HostileInvestigator.class));
        cards.add(new SetCardInfo("Legion Extruder", 12, Rarity.MYTHIC, mage.cards.l.LegionExtruder.class));
        cards.add(new SetCardInfo("Loot, the Key to Everything", 21, Rarity.MYTHIC, mage.cards.l.LootTheKeyToEverything.class));
        cards.add(new SetCardInfo("Lost Jitte", 53, Rarity.MYTHIC, mage.cards.l.LostJitte.class));
        cards.add(new SetCardInfo("Lotus Ring", 24, Rarity.MYTHIC, mage.cards.l.LotusRing.class));
        cards.add(new SetCardInfo("Memory Vessel", 13, Rarity.MYTHIC, mage.cards.m.MemoryVessel.class));
        cards.add(new SetCardInfo("Molten Duplication", 14, Rarity.MYTHIC, mage.cards.m.MoltenDuplication.class));
        cards.add(new SetCardInfo("Nexus of Becoming", 25, Rarity.MYTHIC, mage.cards.n.NexusOfBecoming.class));
        cards.add(new SetCardInfo("Oltec Matterweaver", 3, Rarity.MYTHIC, mage.cards.o.OltecMatterweaver.class));
        cards.add(new SetCardInfo("Omenpath Journey", 18, Rarity.MYTHIC, mage.cards.o.OmenpathJourney.class));
        cards.add(new SetCardInfo("Pest Control", 22, Rarity.MYTHIC, mage.cards.p.PestControl.class));
        cards.add(new SetCardInfo("Rest in Peace", 4, Rarity.MYTHIC, mage.cards.r.RestInPeace.class));
        cards.add(new SetCardInfo("Sandstorm Salvager", 19, Rarity.MYTHIC, mage.cards.s.SandstormSalvager.class));
        cards.add(new SetCardInfo("Simulacrum Synthesizer", 6, Rarity.MYTHIC, mage.cards.s.SimulacrumSynthesizer.class));
        cards.add(new SetCardInfo("Sword of Wealth and Power", 26, Rarity.MYTHIC, mage.cards.s.SwordOfWealthAndPower.class));
        cards.add(new SetCardInfo("Tarnation Vista", 30, Rarity.MYTHIC, mage.cards.t.TarnationVista.class));
        cards.add(new SetCardInfo("Torpor Orb", 27, Rarity.MYTHIC, mage.cards.t.TorporOrb.class));
        cards.add(new SetCardInfo("Transmutation Font", 28, Rarity.MYTHIC, mage.cards.t.TransmutationFont.class));
        cards.add(new SetCardInfo("Vaultborn Tyrant", 20, Rarity.MYTHIC, mage.cards.v.VaultbornTyrant.class));
        cards.add(new SetCardInfo("Worldwalker Helm", 7, Rarity.MYTHIC, mage.cards.w.WorldwalkerHelm.class));
    }
}
