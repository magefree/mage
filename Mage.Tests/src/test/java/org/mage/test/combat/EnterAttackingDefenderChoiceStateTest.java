package org.mage.test.combat;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetDefender;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestComputerPlayer;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 508.4
 * If a creature is put onto the battlefield attacking, its controller chooses which defending player, planeswalker
 * a defending player controls, or battle a defending player protects it’s attacking as it enters the battlefield
 * (unless the effect that put it onto the battlefield specifies what it’s attacking). Similarly, if an effect states
 * that a creature is attacking, its controller chooses which defending player, planeswalker a defending player
 * controls, or battle a defending player protects it’s attacking (unless the effect has already specified). Such
 * creatures are “attacking” but, for the purposes of trigger events and effects, they never “attacked.” They remain
 * attacking creatures until they’re removed from combat or the combat phase ends, whichever comes first.
 *
 * @author JayDi85
 */
public class EnterAttackingDefenderChoiceStateTest extends CardTestPlayerBase {

    // the player sees the game in the defender choice dialog, so it must show all permanents of the same move
    // with all continuous effects applied: the battlefield of the attacking player at every defender choice
    private final List<String> battlefieldOnDefenderChoice = new ArrayList<>();

    @Override
    protected TestPlayer createPlayer(String name, RangeOfInfluence rangeOfInfluence) {
        return new TestPlayer(new TestComputerPlayer(name, rangeOfInfluence)) {
            @Override
            public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
                if (target instanceof TargetDefender) {
                    battlefieldOnDefenderChoice.add(game.getBattlefield().getAllActivePermanents(this.getId())
                            .stream()
                            .filter(permanent -> permanent.isCreature(game))
                            .map(permanent -> permanent.getName() + " " + permanent.getPower().getValue())
                            .sorted()
                            .collect(Collectors.joining(", ")));
                }
                return super.chooseTarget(outcome, target, source, game);
            }
        };
    }

    @Test
    public void test_EnterAttacking_Tokens() {
        // Whenever this creature attacks, create two 1/1 red Human creature tokens that are tapped and attacking.
        addCard(Zone.BATTLEFIELD, playerA, "Hanweir Garrison"); // 2/3
        //
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem"); // creatures you control get +1/+1
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3

        attack(3, playerA, "Hanweir Garrison", playerB);
        addTarget(playerA, playerB); // defender for the first Human token
        addTarget(playerA, playerB); // defender for the second Human token

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        Assert.assertEquals(Arrays.asList(
                "Hanweir Garrison 3, Human Token 2, Human Token 2",
                "Hanweir Garrison 3, Human Token 2, Human Token 2"
        ), battlefieldOnDefenderChoice);
        assertLife(playerB, 20 - 3 - 2 - 2);
    }

    @Test
    public void test_EnterAttacking_Cards() {
        // If a creature you control would enter while you control an attacking creature,
        // instead it enters tapped and attacking.
        addCard(Zone.BATTLEFIELD, playerA, "Kira Nerys, Firebrand"); // 4/3
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem"); // creatures you control get +1/+1
        // Look at the top six cards of your library. Put up to two creature cards with mana value 3 or less
        // from among them onto the battlefield. Put the rest on the bottom of your library in any order.
        addCard(Zone.HAND, playerA, "Collected Company"); // {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion"); // 2/2
        addCard(Zone.LIBRARY, playerA, "Elite Vanguard"); // 2/1
        addCard(Zone.LIBRARY, playerA, "Island"); // top of library, for the draw of turn 3
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3

        attack(3, playerA, "Grizzly Bears", playerB);

        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerA, "Collected Company");
        addTarget(playerA, "Silvercoat Lion^Elite Vanguard"); // creatures to put onto the battlefield
        addTarget(playerA, playerB); // defender for the first creature
        addTarget(playerA, playerB); // defender for the second creature
        setChoice(playerA, "Whenever another creature you control enters"); // order of Kira's two +1/+1 triggers

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        // Kira's +1/+1 triggers resolve after the creatures entered, so they aren't in the dialog yet
        Assert.assertEquals(Arrays.asList(
                "Elite Vanguard 3, Grizzly Bears 3, Kira Nerys, Firebrand 5, Silvercoat Lion 3",
                "Elite Vanguard 3, Grizzly Bears 3, Kira Nerys, Firebrand 5, Silvercoat Lion 3"
        ), battlefieldOnDefenderChoice);
        assertLife(playerB, 20 - 3 - (3 + 1) - (3 + 1));
    }
}
