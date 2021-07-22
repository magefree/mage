
package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.IntimidateAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class LandfallTest extends CardTestPlayerBase {

    @Test
    public void testNormalUse() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Plains");

        // Instant - {1}{W}
        // Target player gains 4 life.
        // Landfall - If you had a land enter the battlefield under your control this turn, that player gains 8 life instead.
        addCard(Zone.HAND, playerA, "Rest for the Weary", 2);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rest for the Weary");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Rest for the Weary");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 4);
        assertGraveyardCount(playerA, "Rest for the Weary", 2);
        assertLife(playerA, 32); // + 8 from 1 turn + 4 from second turn
        assertLife(playerB, 20);

    }

    /**
     * If you Hive Mind an opponent's Rest for the Weary and redirect its target
     * to yourself when it's not your turn, the game spits out this message and
     * rolls back to before Rest for the Weary was cast.
     *
     */
    @Test
    public void testHiveMind() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // Whenever a player casts an instant or sorcery spell, each other player copies that spell. Each of those players may choose new targets for their copy.
        addCard(Zone.BATTLEFIELD, playerB, "Hive Mind");

        // Instant - {1}{W}
        // Target player gains 4 life.
        // Landfall - If you had a land enter the battlefield under your control this turn, that player gains 8 life instead.
        addCard(Zone.HAND, playerA, "Rest for the Weary", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rest for the Weary");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Rest for the Weary", 1);
        assertLife(playerA, 24);
        assertLife(playerB, 24);

    }

    @Test
    public void testSurrakarMarauder() {
        // Landfall - Whenever a land enters the battlefield under your control, Surrakar Marauder gains intimidate until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Surrakar Marauder", 1);
        addCard(Zone.HAND, playerA, "Plains");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 1);

        assertAbility(playerA, "Surrakar Marauder", IntimidateAbility.getInstance(), true);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    /**
     * Searing Blaze's landfall doesn't appear to be working. My opponent played
     * a mountain, then played searing blaze targeting my Tasigur, the Golden
     * Fang. It only dealt 1 damage to me, where it should've dealt 3, because
     * my opponent had played a land.
     */
    @Test
    public void testSearingBlaze() {
        // Searing Blaze deals 1 damage to target player and 1 damage to target creature that player controls.
        // Landfall - If you had a land enter the battlefield under your control this turn, Searing Blaze deals 3 damage to that player and 3 damage to that creature instead.
        addCard(Zone.HAND, playerA, "Searing Blaze", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Mountain");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Searing Blaze");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mountain", 2);
        assertGraveyardCount(playerA, "Searing Blaze", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertGraveyardCount(playerB, "Silvercoat Lion", 1);

    }

    @Test
    public void testGroundswellWithoutLandfall() {
        // Target creature gets +2/+2 until end of turn.
        //Landfall - If you had a land enter the battlefield under your control this turn, that creature gets +4/+4 until end of turn instead.
        addCard(Zone.HAND, playerB, "Groundswell", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        attack(2, playerB, "Silvercoat Lion");
        castSpell(2, PhaseStep.DECLARE_ATTACKERS, playerB, "Groundswell", "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerB, "Forest", 1);
        assertGraveyardCount(playerB, "Groundswell", 1);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 4, 4);

        assertLife(playerA, 16); // 2 + 4
        assertLife(playerB, 20);
    }

    /*
     21:09: Turn 8 arucki (25 - 16)
     21:09: arucki draws a card
     21:09: Ability triggers: Sylvan Library [868] - At the beginning of your draw step, you may draw two additional cards. If you do, choose two cards in your hand drawn this turn. For each of those cards, pay 4 life or put the card on top of your library.
     21:09: arucki draws two cards
     21:09: arucki loses 4 life
     21:09: arucki pays 4 life to keep a card on hand
     21:09: arucki puts 1 card(s) back to library
     21:09: arucki plays Twilight Mire [f4d]
     21:10: arucki activates: Inkmoth Nexus [1b5] becomes a 1/1 Blinkmoth artifact creature with flying and infect until end of turn. It's still a land. (It deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.). from Inkmoth Nexus [1b5]
     21:10: arucki casts Groundswell [b28] targeting Inkmoth Nexus [1b5]
     21:10: Ability triggers: Wild Defiance [990] - Whenever a creature you control becomes the target of an instant or sorcery spell, that creature gets +3/+3 until end of turn
     21:10: arucki puts Groundswell [b28] from stack into their graveyard
     21:10: arucki attacks with 1 creature
     21:10: Attacker: Inkmoth Nexus 1b5 unblocked
     */
    @Test
    public void testGroundswellWithLandfall() {
        // Target creature gets +2/+2 until end of turn.
        //Landfall - If you had a land enter the battlefield under your control this turn, that creature gets +4/+4 until end of turn instead.
        addCard(Zone.HAND, playerB, "Groundswell", 1); // Instant
        addCard(Zone.BATTLEFIELD, playerB, "Forest");
        addCard(Zone.HAND, playerB, "Twilight Mire");

        // Whenever a creature you control becomes the target of an instant or sorcery spell, that creature gets +3/+3 until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Wild Defiance", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Twilight Mire");
        attack(2, playerB, "Silvercoat Lion");
        castSpell(2, PhaseStep.DECLARE_ATTACKERS, playerB, "Groundswell", "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerB, "Twilight Mire", 1);
        assertGraveyardCount(playerB, "Groundswell", 1);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 9, 9);

        assertLife(playerA, 11); // 2 + 4 + 3
        assertLife(playerB, 20);
    }
}
