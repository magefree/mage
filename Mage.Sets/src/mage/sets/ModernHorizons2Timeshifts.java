package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author Jmlundeen
 */
public final class ModernHorizons2Timeshifts extends ExpansionSet {

    private static final ModernHorizons2Timeshifts instance = new ModernHorizons2Timeshifts();

    public static ModernHorizons2Timeshifts getInstance() {
        return instance;
    }

    private ModernHorizons2Timeshifts() {
        super("Modern Horizons 2 Timeshifts", "H2R", ExpansionSet.buildDate(2024, 6, 7), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Abiding Grace", 1, Rarity.UNCOMMON, mage.cards.a.AbidingGrace.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragon's Rage Channeler", 9, Rarity.UNCOMMON, mage.cards.d.DragonsRageChanneler.class, RETRO_ART));
        cards.add(new SetCardInfo("Dress Down", 4, Rarity.RARE, mage.cards.d.DressDown.class, RETRO_ART));
        cards.add(new SetCardInfo("Endurance", 14, Rarity.MYTHIC, mage.cards.e.Endurance.class, RETRO_ART));
        cards.add(new SetCardInfo("Esper Sentinel", 2, Rarity.RARE, mage.cards.e.EsperSentinel.class, RETRO_ART));
        cards.add(new SetCardInfo("Fury", 10, Rarity.MYTHIC, mage.cards.f.Fury.class, RETRO_ART));
        cards.add(new SetCardInfo("Grief", 7, Rarity.MYTHIC, mage.cards.g.Grief.class, RETRO_ART));
        cards.add(new SetCardInfo("Hard Evidence", 5, Rarity.COMMON, mage.cards.h.HardEvidence.class, RETRO_ART));
        cards.add(new SetCardInfo("Munitions Expert", 16, Rarity.UNCOMMON, mage.cards.m.MunitionsExpert.class, RETRO_ART));
        cards.add(new SetCardInfo("Ragavan, Nimble Pilferer", 11, Rarity.MYTHIC, mage.cards.r.RagavanNimblePilferer.class, RETRO_ART));
        cards.add(new SetCardInfo("Sling-Gang Lieutenant", 8, Rarity.UNCOMMON, mage.cards.s.SlingGangLieutenant.class, RETRO_ART));
        cards.add(new SetCardInfo("Solitude", 3, Rarity.MYTHIC, mage.cards.s.Solitude.class, RETRO_ART));
        cards.add(new SetCardInfo("Strike It Rich", 12, Rarity.UNCOMMON, mage.cards.s.StrikeItRich.class, RETRO_ART));
        cards.add(new SetCardInfo("Subtlety", 6, Rarity.MYTHIC, mage.cards.s.Subtlety.class, RETRO_ART));
        cards.add(new SetCardInfo("Tireless Provisioner", 15, Rarity.UNCOMMON, mage.cards.t.TirelessProvisioner.class, RETRO_ART));
        cards.add(new SetCardInfo("Unholy Heat", 13, Rarity.COMMON, mage.cards.u.UnholyHeat.class, RETRO_ART));
    }
}
