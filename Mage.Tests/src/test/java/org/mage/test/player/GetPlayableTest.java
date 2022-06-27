package org.mage.test.player;

import mage.abilities.ActivatedAbility;
import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tests for {@link mage.players.PlayerImpl#getPlayable(Game, boolean, Zone, boolean)}
 *
 * @author DeepCrimson
 */
public class GetPlayableTest extends CardTestPlayerBase {

    /**
     * If there are no valid targets, getPlayable() doesn't return
     * Valorous Stance as a playable option even though it's in hand
     * and mana is available to cast it.
     */
    @Test
    public void testCannotPlayValorousStance() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Valorous Stance");
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        assertAllCommandsUsed();
        execute();

        Permanent plains = getPermanent("Plains", playerA);
        ActivatedAbility tapForWhiteMana = (ActivatedAbility) plains.getAbilities().get(1);

        List<ActivatedAbility> expectedPlayableAbilities = new ArrayList<>(
                Collections.singletonList(tapForWhiteMana)
        );
        List<ActivatedAbility> actualPlayableAbilities = playerA.getPlayable(currentGame, true);

        assertAbilitiesAreEqual(expectedPlayableAbilities, actualPlayableAbilities);
    }

    /**
     * If there is a valid target, getPlayable() does return
     * Valorous Stance as a playable card.
     */
    @Test
    public void testCanPlayValorousStance() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Valorous Stance");
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        assertAllCommandsUsed();
        execute();

        Card valorousStance = getHandCards(playerA).get(0);
        Permanent plains = getPermanent("Plains", playerA);
        ActivatedAbility tapForWhiteMana = (ActivatedAbility) plains.getAbilities().get(1);

        List<ActivatedAbility> expectedPlayableAbilities = new ArrayList<>();
        expectedPlayableAbilities.add(valorousStance.getSpellAbility());
        expectedPlayableAbilities.add(tapForWhiteMana);
        List<ActivatedAbility> actualPlayableAbilities = playerA.getPlayable(currentGame, true);

        assertAbilitiesAreEqual(expectedPlayableAbilities, actualPlayableAbilities);
    }

    /**
     * Assert that the actual list of playable abilities available to the player is equal to the expected list of
     * playable abilities. Rather than comparing the actual and expected lists directly, this method uses a recursive
     * comparison and ignores the following fields' values:
     * <ul>
     *     <li>{@code id}</li>
     *     <li>{@code modes}</li>
     *     <li>{@code originalId}</li>
     *     <li>{@code sourceId}</li>
     * </ul>
     */
    private void assertAbilitiesAreEqual(List<ActivatedAbility> expectedPlayableAbilities, List<ActivatedAbility> actualPlayableAbilities) {
        Assertions.assertThat(actualPlayableAbilities)
                .usingRecursiveComparison()
                // Ignore fields which end in "id" (case insensitive) or "modes"
                .ignoringFieldsMatchingRegexes("(?i).*id", ".*modes")
                .isEqualTo(expectedPlayableAbilities);
    }
}
