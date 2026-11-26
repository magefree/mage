package org.mage.test.cards.single.cmr;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ColfenorTheLastYewTest extends CardTestPlayerBase {

    @Test
    public void testOnlyLegalTargetsOffered() {
        addCard(Zone.BATTLEFIELD, playerA, "Colfenor, the Last Yew"); // 3/7
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.GRAVEYARD, playerA, "Ancient Brontodon"); // 9/9
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Colfenor, the Last Yew");
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        runCode("check offered targets", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals(info + " - trigger must be on the stack", 1, game.getStack().size());
            Ability ability = game.getStack().getFirstOrNull().getStackAbility();
            Set<UUID> possible = ability.getTargets().get(0).possibleTargets(player.getId(), ability, game);
            Set<UUID> expected = player.getGraveyard()
                    .getCards(game)
                    .stream()
                    .filter(card -> card.getName().equals("Grizzly Bears"))
                    .map(MageObject::getId)
                    .collect(Collectors.toSet());
            Assert.assertEquals(info + " - only other lesser toughness card is legal", expected, possible);
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Colfenor, the Last Yew", 1);
        assertGraveyardCount(playerA, "Ancient Brontodon", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void testReturnsOtherCreatureCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Colfenor, the Last Yew");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Colfenor, the Last Yew");
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Colfenor, the Last Yew", 1);
        assertHandCount(playerA, "Grizzly Bears", 1);
    }
}
