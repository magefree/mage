/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class JinxedRingTest extends CardTestPlayerBase {

    @Test
    public void testShatterstorm() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Destroy all artifacts. They can't be regenerated.
        addCard(Zone.HAND, playerA, "Shatterstorm", 1); // Sorcery - {2}{R}{R}
        // Whenever a nontoken permanent is put into your graveyard from the battlefield, Jinxed Ring deals 1 damage to you.
        // Sacrifice a creature: Target opponent gains control of Jinxed Ring.
        addCard(Zone.BATTLEFIELD, playerA, "Jinxed Ring", 1);

        // Bonded Construct can't attack alone.
        addCard(Zone.BATTLEFIELD, playerA, "Bonded Construct", 2); // Artifact Creature - Construct 2/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shatterstorm");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Shatterstorm", 1);
        assertGraveyardCount(playerA, "Jinxed Ring", 1);
        assertGraveyardCount(playerA, "Bonded Construct", 2);
        assertLife(playerA, 17);
        assertLife(playerB, 20);

    }

    /**
     * Card works fine on destroying individual things, but after I gave it to
     * my opponent, when I cast Shatterstorm there were no triggers. Maybe a
     * problem because Jinxed Ring goes to my graveyard and other permanents go
     * to their owner's graveyards, while Jinxed Ring looks at permanent's
     * entering "your graveyard."
     */
    @Test
    public void testShatterstormControlledByOpponent() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        // Exchange control of target artifact or creature and another target permanent that shares one of those types with it.
        addCard(Zone.HAND, playerA, "Legerdemain", 1); // Sorcery - {2}{U}{U}
        // Destroy all artifacts. They can't be regenerated.
        addCard(Zone.HAND, playerA, "Shatterstorm", 1); // Sorcery - {2}{R}{R}
        // Whenever a nontoken permanent is put into your graveyard from the battlefield, Jinxed Ring deals 1 damage to you.
        // Sacrifice a creature: Target opponent gains control of Jinxed Ring.
        addCard(Zone.BATTLEFIELD, playerA, "Jinxed Ring", 1);

        // Bonded Construct can't attack alone.
        addCard(Zone.BATTLEFIELD, playerB, "Bonded Construct", 2); // Artifact Creature - Construct 2/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Legerdemain", "Jinxed Ring^Bonded Construct");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shatterstorm");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Legerdemain", 1);
        assertGraveyardCount(playerA, "Shatterstorm", 1);
        assertGraveyardCount(playerA, "Jinxed Ring", 1);
        assertGraveyardCount(playerB, "Bonded Construct", 2);
        assertLife(playerA, 20);
        assertLife(playerB, 18);

    }

}
