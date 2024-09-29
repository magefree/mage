package org.mage.test.cards.single.c21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.l.LivingLore Living Lore}
 * {3}{U}
 * Creature — Avatar
 *
 * As Living Lore enters the battlefield, exile an instant or sorcery card from your graveyard.
 * Living Lore’s power and toughness are each equal to the exiled card’s mana value.
 * Whenever Living Lore deals combat damage, you may sacrifice it. If you do, you may cast the exiled card without paying its mana cost.
 *
 * @author Susucr
 */
public class LivingLoreTest extends CardTestPlayerBase {

    private static final String livingLore = "Living Lore";
    private static final String unsummon = "Unsummon"; // bounce the lore
    private static final String cruelUltimatum = "Cruel Ultimatum"; // 7 mana sorcery
    private static final String bolt = "Lightning Bolt"; // 1 mana instant
    private static final String chaplainsBlessing = "Chaplain's Blessing"; // 1 mv sorcery "You gain 5 life."
    /**
     * Pull from Eternity
     * {W}
     * Instant
     *
     * Put target face-up exiled card into its owner’s graveyard.
     */
    private static final String pullFromEternity = "Pull from Eternity";

    /**
     * Reported bug: "If Living Lore is bounced to hand and recast, it is the size of the combined exiled cards' MV. That's incorrect."
     */
    @Test
    public void testZCC() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, livingLore);
        addCard(Zone.HAND, playerA, unsummon);
        addCard(Zone.GRAVEYARD, playerA, cruelUltimatum);
        addCard(Zone.GRAVEYARD, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4 + 1 + 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, livingLore, true);
        addTarget(playerA, cruelUltimatum);

        checkPT("check 7/7", 1, PhaseStep.PRECOMBAT_MAIN, playerA, livingLore, 7, 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, unsummon, livingLore, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, livingLore, true);
        addTarget(playerA, bolt);

        checkPT("check 1/1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, livingLore, 1, 1);

        attack(3, playerA, livingLore);
        setChoice(playerA, true); // yes to sacrifice
        setChoice(playerA, true); // yes to "you may cast"
        addTarget(playerA, playerB); // bolt opposing player

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, livingLore, 1);
        assertGraveyardCount(playerA, bolt, 1);
        assertExileCount(playerA, cruelUltimatum, 1); // never left the exile.
        assertLife(playerB, 20 - 1 - 3);
    }

    @Test
    public void testPullFromEternity() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, livingLore);
        addCard(Zone.HAND, playerA, pullFromEternity);
        addCard(Zone.GRAVEYARD, playerA, cruelUltimatum);
        addCard(Zone.GRAVEYARD, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, livingLore, true);
        addTarget(playerA, cruelUltimatum);

        checkPT("check 7/7", 1, PhaseStep.PRECOMBAT_MAIN, playerA, livingLore, 7, 7);

        // This will kill the Living Lore next time SBA are checked:
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pullFromEternity, cruelUltimatum);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, livingLore, 1);
        assertGraveyardCount(playerA, cruelUltimatum, 1);
        assertGraveyardCount(playerA, pullFromEternity, 1);
    }

    @Test
    public void testLivingLoreNotSharingExile() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, livingLore, 2);
        addCard(Zone.GRAVEYARD, playerA, cruelUltimatum);
        addCard(Zone.GRAVEYARD, playerA, chaplainsBlessing);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, livingLore, true);
        addTarget(playerA, chaplainsBlessing);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, livingLore, true);
        addTarget(playerA, cruelUltimatum);

        attack(3, playerA, livingLore);
        setChoice(playerA, true); // yes to sacrifice
        setChoice(playerA, true); // yes to "you may cast"

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, livingLore, 1);
        assertPermanentCount(playerA, livingLore, 1);
        assertPowerToughness(playerA, livingLore, 7, 7);
        assertLife(playerA, 20 + 5);
        assertLife(playerB, 20 - 1);
        assertGraveyardCount(playerA, chaplainsBlessing, 1);
        assertExileCount(playerA, cruelUltimatum, 1);
    }
}
