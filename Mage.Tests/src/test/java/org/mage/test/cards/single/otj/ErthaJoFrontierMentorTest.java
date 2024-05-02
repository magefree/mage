package org.mage.test.cards.single.otj;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ErthaJoFrontierMentorTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.e.ErthaJoFrontierMentor Ertha Jo, Frontier Mentor} {2}{R}{W}
     * Legendary Creature — Kor Advisor
     * When Ertha Jo, Frontier Mentor enters the battlefield, create a 1/1 red Mercenary creature token with “{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery.”
     * Whenever you activate an ability that targets a creature or player, copy that ability. You may choose new targets for the copy.
     * 2/4
     */
    private static final String ertha = "Ertha Jo, Frontier Mentor";

    @Test
    public void Test_TargetPlayer() {
        setStrictChooseMode(true);

        // Sacrifice Bile Urchin: Target player loses 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Bile Urchin", 1);
        addCard(Zone.BATTLEFIELD, playerA, ertha, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice", playerB);
        setChoice(playerA, true); // choose to change the targets for the copy
        addTarget(playerA, playerA); // have the copy target playerA

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 19);
    }

    @Test
    public void Test_TargetCreature() {
        setStrictChooseMode(true);

        // {T}: Target creature gets +1/+1 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Wyluli Wolf", 1);
        addCard(Zone.BATTLEFIELD, playerA, ertha, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}", ertha);
        setChoice(playerA, false); // choose not to change the targets for the copy

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, ertha, 2 + 2, 4 + 2);
    }

    @Test
    public void Test_TargetLand_NoCopy() {
        setStrictChooseMode(true);

        // {2}{R}, {T}: Destroy target nonbasic land.
        addCard(Zone.BATTLEFIELD, playerA, "Dwarven Miner", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Plateau", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Tropical Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, ertha, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}", "Plateau");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Plateau", 1);
    }
}
