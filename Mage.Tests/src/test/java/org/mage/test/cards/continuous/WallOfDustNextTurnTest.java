package org.mage.test.cards.continuous;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.watchers.common.AttackedThisTurnWatcher;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class WallOfDustNextTurnTest extends CardTestPlayerBase {

    @Test
    public void test_SingleOpponentMustAttack() {
        // Whenever Wall of Dust blocks a creature, that creature can't attack during its controller's next turn.
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Dust");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Ashcoat Bear", 1); // 2/2
        //
        Ability ability = new SimpleStaticAbility(new AttacksIfAbleAllEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, Duration.EndOfGame));
        ability.addWatcher(new AttackedThisTurnWatcher());
        addCustomCardWithAbility("all attacks", playerA, ability);

        // 1 - nothing
        checkPermanentCount("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkPermanentCount("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 1);
        checkLife("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 20);

        // 2 - auto-attack B -> A, 1 attacked, 1 blocked (by wall)
        block(2, playerA, "Wall of Dust", "Balduvian Bears");
        checkPermanentCount("turn 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkPermanentCount("turn 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 1);
        checkLife("turn 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 - 2);

        // 3 - nothing
        checkPermanentCount("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkPermanentCount("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 1);
        checkLife("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 - 2);

        // 4 - auto-attack, B -> A, 1 attacked, 1 can't attacked (by wall's abilitiy during next turn)
        checkPermanentCount("turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkPermanentCount("turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 1);
        checkLife("turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 - 2 * 2);

        // 5 - nothing
        checkPermanentCount("turn 5", 5, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkPermanentCount("turn 5", 5, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 1);
        checkLife("turn 5", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 - 2 * 2);

        // 6 - auto-attack, B -> A, 2 attacked
        checkPermanentCount("turn 6", 6, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkPermanentCount("turn 6", 6, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 1);
        checkLife("turn 6", 6, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 - 2 * 2 - 2 * 2);

        setStopAt(6, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }
}
