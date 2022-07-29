package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */

public class SharuumTheHegemonTest extends CardTestPlayerBase {

    /**
     * http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=16732&p=172937&hilit=Sharuum+the+Hegemon#p172920
     * <p>
     * My Sharuum EDH deck uses the standard Sharuum + Clone Effect + Blood Artist as one of the win
     * conditions, but when I have Sharuum in plan and play a Clever Impersonator, targetting Sharuum
     * and choose to keep the Clever Impersonator and send the original Sharuum to the graveyard Xmage
     * never gives me the option to use the Sharuum Ability that the Clever Impersonator should get,
     * making the combo not work.
     * <p>
     * I run a Sharuum EDH deck that wins by cloning Sharuum for infinite death triggers. I know the rules
     * check out on this combo irl, but no matter how I stack the triggers for cloning Sharuum and her enter
     * the battlefield effect it does not work. It either ends with Sharuum in my graveyard or the reanimate
     * effect hits the stack before the legend rule applies
     * <p>
     * [1] Sharuum the Hegemon is on the battlefield.
     * [2] You cast Clone (or any other Clone-like card).
     * [3] When Clone resolves, you choose Sharuum for the replacement effect.
     * [4] Since fake-Sharuum entered the battlefield, its EtB ability triggers.
     * [5] State-based actions are checked and you are prompted to keep one Sharuum. You sacrifice real-Sharuum.
     * 116.2a Triggered abilities can trigger at any time, including while a spell is being cast, an ability is being activated, or a spell or
     * ability is resolving. (See rule 603, "Handling Triggered Abilities.") However, nothing actually happens at the time an ability triggers.
     * Each time a player would receive priority, each ability that has triggered but hasn't yet been put on the stack is put on the stack. See rule 116.5
     * 116.5. Each time a player would get priority, the game first performs all applicable state-based actions as a single event (see rule 704,
     * "State-Based Actions"), then repeats this process until no state-based actions are performed. Then triggered abilities are put on the stack
     * (see rule 603, "Handling Triggered Abilities"). These steps repeat in order until no further state-based actions are performed and no abilities
     * trigger. Then the player who would have received priority does so.
     * [6] Once State-based actions are finished, triggered abilities go on the stack. You put the EtB from [4] choosing real-Sharuum.
     * [7] Real-Sharuum enters the battlefield.
     * [8] Rinse and repeat.
     */
    @Test
    public void testCloneTriggered() {
        // When Sharuum the Hegemon enters the battlefield, you may return target artifact card from your graveyard to the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Sharuum the Hegemon", 1);
        addCard(Zone.HAND, playerA, "Clone", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // Whenever Blood Artist or another creature dies, target player loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true);
        setChoice(playerA, "Sharuum the Hegemon"); // what creature to clone

        addTarget(playerA, "Sharuum the Hegemon[only copy]"); // which legend to keep
        setChoice(playerA, "Whenever {this} or another creature dies"); // blood first
        addTarget(playerA, playerB); // damage by blood
        setChoice(playerA, true); // return
        // addTarget(playerA, "Sharuum the Hegemon"); // return real sharuum (Autochosen, only target)

        addTarget(playerA, "Sharuum the Hegemon[only copy]"); // which legend to keep
        setChoice(playerA, "Whenever {this} or another creature dies"); // blood first
        addTarget(playerA, playerB); // damage by blood
        setChoice(playerA, true); // return
        // addTarget(playerA, "Sharuum the Hegemon"); // return real sharuum (Autochosen, only target

        addTarget(playerA, "Sharuum the Hegemon[only copy]"); // which legend to keep
        setChoice(playerA, "Whenever {this} or another creature dies"); // blood first
        addTarget(playerA, playerB); // damage by blood
        setChoice(playerA, true); // return
        // addTarget(playerA, "Sharuum the Hegemon"); // return real sharuum (Autochosen, only target)

        addTarget(playerA, "Sharuum the Hegemon[only copy]"); // which legend to keep
        setChoice(playerA, "Whenever {this} or another creature dies"); // blood first
        addTarget(playerA, playerB); // damage by blood
        setChoice(playerA, false); // Don't use it anymore

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 24);
        assertLife(playerB, 16);


    }

}