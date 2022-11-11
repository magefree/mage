package org.mage.test.cards.single.ema;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DeathriteShamanTest extends CardTestPlayerBase {
    // {T}: Exile target land card from a graveyard. Add one mana of any color.
    // {B}, {T}: Exile target instant or sorcery card from a graveyard. Each opponent loses 2 life.
    // {G}, {T}: Exile target creature card from a graveyard. You gain 2 life.
    private static final String deathriteShaman = "Deathrite Shaman";

    /**
     * Check that the first ability uses the stack (and that the dynamic value works)
     */
    @Test
    public void manaAbility() {
        addCard(Zone.BATTLEFIELD, playerA, deathriteShaman);
        addCard(Zone.GRAVEYARD, playerA, "Island");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Exile");
        checkStackSize("Shaman's ability should be on the stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }
}
