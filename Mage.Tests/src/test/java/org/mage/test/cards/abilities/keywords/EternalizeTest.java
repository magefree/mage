package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.VigilanceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class EternalizeTest extends CardTestPlayerBase {

    // Creature - Human Cleric {3}{W}  2/3
    // Vigilance
    // Eternalize ({4}{W}{W}, Exile this card from your graveyard: Create a token that's a copy of it,
    // except it's a 4/4 black Zombie Human Cleric with no mana cost. Eternalize only as a sorcery.)
    private final String sentinel = "Steadfast Sentinel";

    @Test
    public void testEternalize() {
        addCard(Zone.GRAVEYARD, playerA, sentinel, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eternalize");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPermanentCount(playerA, sentinel, 1);
        assertPowerToughness(playerA, sentinel, 4, 4);
        assertAbility(playerA, sentinel, VigilanceAbility.getInstance(), true);
    }

    @Test
    public void testEternalizeAndFatalPush() {
        addCard(Zone.GRAVEYARD, playerA, sentinel, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);

        // Destroy target creature if it has converted mana cost 2 or less.
        // Revolt - Destroy that creature if it has converted mana cost 4 or less
        // instead if a permanent you controlled left the battlefield this turn.
        addCard(Zone.HAND, playerB, "Fatal Push"); // Instant {B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eternalize");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Fatal Push", sentinel);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Fatal Push", 1);

        assertPermanentCount(playerA, sentinel, 0);
        assertExileCount(playerA, sentinel, 1);
    }
}
