package org.mage.test.cards.single.clb;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

public class DelayedBlastFireballTest extends CardTestPlayerBase {
    static final String fireball = "Delayed Blast Fireball";

    @Test
    // Author: alexander-novo
    // Issue: magefree/mage#10435
    // If you create a copy of a card in exile and then cast that copy, it should be cast from exile.
    // But if we do this with Delayed Blast Fireball (and Mizzix's Mastery, for instance), it won't deal the extra damage for casting from exile.
    public void testCopyCardAndCastFromExile() {
        String mastery = "Mizzix's Mastery";

        // Prep for exiling fireball from graveyard, copying, and then casting copy
        addCard(Zone.GRAVEYARD, playerA, fireball);
        addCard(Zone.HAND, playerA, mastery);

        // Enough mana to cast mastery
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // Cast Mizzix's Mastery targetting delayed blast fireball. This hsoul exile it, copy it into exile, and then cast the copy from exile, which should end up dealing 5 damage to player B
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mastery, fireball);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 5);
    }
}
