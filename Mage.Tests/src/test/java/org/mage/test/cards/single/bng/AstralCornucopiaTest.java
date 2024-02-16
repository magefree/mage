package org.mage.test.cards.single.bng;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.a.AstralCornucopia Astral Cornucopia}
 * Artifact
 * {X}{X}{X}
 * Astral Cornucopia enters the battlefield with X charge counters on it.
 * {T}: Choose a color. Add one mana of that color for each charge counter on Astral Cornucopia.
 */
public class AstralCornucopiaTest extends CardTestPlayerBase {

    private static final String astralCornucopia = "Astral Cornucopia";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9392
     *
     * Astral Cornucopia taps for 1 mana regardless of how many charge counters it has on it.
     */
    @Test
    public void testCorrectManaAmount() {
        // Artifact {2}
        String arcaneSignet = "Arcane Signet";

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, astralCornucopia);
        addCard(Zone.HAND, playerA, arcaneSignet);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, astralCornucopia, true);
        setChoice(playerA, "X=2");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcaneSignet);

        execute();

        assertPermanentCount(playerA, astralCornucopia,1 );
        assertCounterCount(playerA, astralCornucopia, CounterType.CHARGE, 2);
        assertPermanentCount(playerA, arcaneSignet, 1);
    }
}
