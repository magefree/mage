package org.mage.test.cards.single.blc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class FortuneTellersTalentTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.f.FortuneTellersTalent Fortune Teller's Talent} {U}
     * Enchantment — Class
     * You may look at the top card of your library any time.
     * {3}{U}: Level 2
     * As long as you’ve cast a spell this turn, you may play cards from the top of your library.
     * {2}{U}: Level 3
     * Spells you cast from anywhere other than your hand cost {2} less to cast.
     */
    private static final String fortuneTeller = "Fortune Teller's Talent";

    private void assertClassLevel(String cardName, int level) {
        Permanent permanent = getPermanent(cardName);
        Assert.assertEquals(
                cardName + " should be level " + level +
                        " but was level " + permanent.getClassLevel(),
                level, permanent.getClassLevel()
        );
    }

    @Test
    public void test_PlayFromLibrary() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Island", 14);
        // {3}{U}: Instant: Return all attacking creatures to their owner’s hand.
        addCard(Zone.LIBRARY, playerA, "Aetherize", 4);
        addCard(Zone.HAND, playerA, fortuneTeller);

        int islandsUsed = 0;

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fortuneTeller, true);
        islandsUsed++;

//        checkPermanentTapped("Used " + islandsUsed + " Islands", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island", true, islandsUsed);

        // Level 2: As long as you’ve cast a spell this turn, you may play cards from the top of your library.
        // (Fortune Teller counts)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{U}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        islandsUsed += 4;

        checkPermanentTapped("Used " + islandsUsed + " Islands", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island", true, islandsUsed);

        checkHandCardCount("not in hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aetherize", 0);
        checkPlayableAbility("from library", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Aetherize", true);
        //This will cast from the top of the library
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aetherize", true);
        islandsUsed += 4;

        checkPermanentTapped("Used " + islandsUsed + " Islands", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island", true, islandsUsed);

        // Level 3: Spells you cast from anywhere other than your hand cost {2} less to cast.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{U}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        islandsUsed += 3;

        checkHandCardCount("not in hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aetherize", 0);
        checkPlayableAbility("from library", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Aetherize", true);
        //This will cast from the top of the library
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aetherize", true);
        islandsUsed += 2; // Should have had cost reduced by 2

        checkPermanentTapped("Used " + islandsUsed + " Islands", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island", true, islandsUsed);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertClassLevel(fortuneTeller, 3);
        assertLibraryCount(playerA, "Aetherize", 2);

    }

}
