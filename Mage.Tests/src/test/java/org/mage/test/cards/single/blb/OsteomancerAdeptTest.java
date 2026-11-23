package org.mage.test.cards.single.blb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class OsteomancerAdeptTest extends CardTestPlayerBase {

    @Test
    public void cannotCountTheCreatureBeingCast() {
        addCard(Zone.GRAVEYARD, playerA, "Forest", 2);
        checkPlayable("Daggerfang Duo", 3, false);
    }

    @Test
    public void tappingManalithCannotSupplyThirdCard() {
        addCard(Zone.GRAVEYARD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Manalith");
        checkPlayable("Daggerfang Duo", 2, false);
    }

    @Test
    public void sacrificingLotusPetalSuppliesThirdCard() {
        addCard(Zone.GRAVEYARD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Lotus Petal");
        checkPlayable("Daggerfang Duo", 2, true);
        assertExileCount(playerA, "Lotus Petal", 1);
    }

    @Test
    public void canExileThreeOtherCards() {
        addCard(Zone.GRAVEYARD, playerA, "Forest", 3);
        checkPlayable("Daggerfang Duo", 3, true);
    }

    @Test
    public void canSacrificeFood() {
        addCard(Zone.BATTLEFIELD, playerA, "Gingerbrute");
        checkPlayable("Daggerfang Duo", 3, true);
    }

    @Test
    public void sacrificeForTheSpellProvidesThirdCard() {
        addCard(Zone.GRAVEYARD, playerA, "Forest", 2);
        checkPlayable("Demon of Catastrophes", 4, true);
        assertExileCount(playerA, "Osteomancer Adept", 1);
    }

    @Test
    public void sacrificeForManaProvidesThirdCard() {
        addCard(Zone.GRAVEYARD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Blood Pet");
        checkPlayable("Daggerfang Duo", 2, true);
        assertExileCount(playerA, "Blood Pet", 1);
    }

    private void checkPlayable(String creature, int swamps, boolean expected) {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", swamps);
        addCard(Zone.BATTLEFIELD, playerA, "Osteomancer Adept");
        addCard(Zone.GRAVEYARD, playerA, creature);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Until end of turn");
        checkPlayableAbility("graveyard cast", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + creature, expected);
        if (expected) {
            castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, creature);
        }
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, creature, expected ? 1 : 0);
    }
}
