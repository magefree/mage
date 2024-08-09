package org.mage.test.cards.single.snc;

import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.b.BallroomBrawlers Ballroom Brawlers}
 * Whenever Ballroom Brawlers attacks, Ballroom Brawlers and up to one other target creature you control
 * both gain your choice of first strike or lifelink until end of turn.
 *
 * This tests an edge case of GainsChoiceOfAbilitiesEffect
 *
 * @author notgreat
 */
public class BallroomBrawlersTest extends CardTestPlayerBase {
    @Test
    public void testSoloFirstStrike() {
        addCard(Zone.BATTLEFIELD, playerA, "Ballroom Brawlers");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        attack(1, playerA, "Ballroom Brawlers");
        attack(1, playerA, "Raging Goblin");

        addTarget(playerA, TestPlayer.TARGET_SKIP );
        setChoice(playerA, "first strike");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertAbility(playerA, "Ballroom Brawlers", LifelinkAbility.getInstance(), false);
        assertAbility(playerA, "Raging Goblin", LifelinkAbility.getInstance(), false);
        assertAbility(playerA, "Ballroom Brawlers", FirstStrikeAbility.getInstance(), true);
        assertAbility(playerA, "Raging Goblin", FirstStrikeAbility.getInstance(), false);
    }
    @Test
    public void testBothLifelink() {
        addCard(Zone.BATTLEFIELD, playerA, "Ballroom Brawlers");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        attack(1, playerA, "Ballroom Brawlers");
        attack(1, playerA, "Raging Goblin");

        addTarget(playerA, "Raging Goblin" );
        setChoice(playerA, "lifelink");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertAbility(playerA, "Ballroom Brawlers", LifelinkAbility.getInstance(), true);
        assertAbility(playerA, "Raging Goblin", LifelinkAbility.getInstance(), true);
        assertAbility(playerA, "Ballroom Brawlers", FirstStrikeAbility.getInstance(), false);
        assertAbility(playerA, "Raging Goblin", FirstStrikeAbility.getInstance(), false);
    }

}
