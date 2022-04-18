package org.mage.test.cards.abilities.keywords;

import mage.abilities.Ability;
import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class BlitzTest extends CardTestPlayerBase {

    private static final String decoy = "Riveteers Decoy";

    private void assertBlitzed(String cardName, boolean isBlitzed) {
        assertPermanentCount(playerA, cardName, 1);
        Permanent permanent = getPermanent(cardName);
        Assert.assertEquals(
                "Permanent should " + (isBlitzed ? "" : "not ") + "have haste", isBlitzed,
                permanent.hasAbility(HasteAbility.getInstance(), currentGame)
        );
        Assert.assertEquals(
                "Permanent should " + (isBlitzed ? "" : "not ") + "have card draw trigger", isBlitzed,
                permanent
                        .getAbilities(currentGame)
                        .stream()
                        .map(Ability::getRule)
                        .anyMatch("When this creature dies, draw a card."::equals)
        );
    }

    @Test
    public void testBlitz() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, decoy);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, decoy);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, decoy, 1);
        assertBlitzed(decoy, true);
    }

    @Test
    public void testBlitzSacrificed() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, decoy);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, decoy);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, decoy, 0);
        assertGraveyardCount(playerA, decoy, 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testNoBlitz() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, decoy);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, decoy);
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, decoy, 1);
        assertBlitzed(decoy, false);
    }
}
