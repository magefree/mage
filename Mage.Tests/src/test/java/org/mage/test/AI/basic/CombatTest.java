
package org.mage.test.AI.basic;

import org.mage.test.serverside.base.CardTestPlayerBaseAI;

/**
 *
 * @author LevelX2
 */
public class CombatTest extends CardTestPlayerBaseAI {

    /*
     * Combat is not integrated yet in the CardTestPlayerBaseAI
     *
     * TODO: Modify CardTestPlayerBaseAI to use the AI combat acting
     *
     * Tests to create:
     * AI is not attacking if opponent has a creature that can't block
     * AI is not blocking also if able if the damage the attacker will do will kill the AI
     * AI is not able to block with two or more creatures one attacking creature to kill it. Even if none of the AI creatures will die
     * AI attacks with a flyer even if opponent has a bigger flyer that kills AI
     * Opponent of AI has only 1 life. AI has more creatures that can attack taht do at least 1 damage than opponent has blockers. Ai should attack with all needed creatures
     *
     */
}
