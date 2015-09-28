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
package org.mage.test.cards.copy;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CopySpellTest extends CardTestPlayerBase {

    @Test
    public void copyChainOfVapor() {
        // Return target nonland permanent to its owner's hand. Then that permanent's controller may sacrifice a land. If the player does, he or she may copy this spell and may choose a new target for that copy.
        addCard(Zone.HAND, playerA, "Chain of Vapor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chain of Vapor", "Pillarfield Ox");
        setChoice(playerB, "Yes");
        addTarget(playerB, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Island", 1);
        assertHandCount(playerB, "Pillarfield Ox", 1);
        assertHandCount(playerA, "Silvercoat Lion", 1);
    }

    @Test
    public void ZadaHedronGrinderBoost() {
        // Target creature gets +3/+3 and gains flying until end of turn.
        addCard(Zone.HAND, playerA, "Angelic Blessing", 1); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        // Whenever you cast an instant or sorcery spell that targets only Zada, Hedron Grinder, copy that spell for each other creature you control that the spell could target. Each copy targets a different one of those creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Zada, Hedron Grinder", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angelic Blessing", "Zada, Hedron Grinder");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Angelic Blessing", 1);
        assertPowerToughness(playerA, "Pillarfield Ox", 5, 7);
        assertAbility(playerA, "Pillarfield Ox", FlyingAbility.getInstance(), true);
        assertPowerToughness(playerA, "Zada, Hedron Grinder", 6, 6);
        assertAbility(playerA, "Zada, Hedron Grinder", FlyingAbility.getInstance(), true);

        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);
        assertAbility(playerB, "Silvercoat Lion", FlyingAbility.getInstance(), false);
    }

    @Test
    public void ZadaHedronGrinderBoostWithCharm() {
        // Choose two -
        // • Counter target spell.
        // • Return target permanent to its owner's hand.
        // • Tap all creatures your opponents control.
        // • Draw a card.
        addCard(Zone.HAND, playerA, "Cryptic Command", 1); // {2}{U}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // Whenever you cast an instant or sorcery spell that targets only Zada, Hedron Grinder, copy that spell for each other creature you control that the spell could target. Each copy targets a different one of those creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Zada, Hedron Grinder", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cryptic Command", "mode=2Zada, Hedron Grinder");
        setModeChoice(playerA, "2"); // Return target permanent to its owner's hand
        setModeChoice(playerA, "4"); // Draw a card

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);

        assertGraveyardCount(playerA, "Cryptic Command", 1);
        assertPermanentCount(playerA, "Zada, Hedron Grinder", 0);
        assertPermanentCount(playerA, "Pillarfield Ox", 0);
        assertHandCount(playerA, 4); // 2 draw + 2 creatures returned to hand
    }
}
