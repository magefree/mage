package org.mage.test.cards.single.woc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Codermann63
 */

public class LiberatedLivestockTest extends CardTestPlayerBase {
    /*
     * Liberated Livestock {5}{W} Sorcery
     * When Liberated Livestock dies, create a 1/1 white Cat creature token with lifelink,
     * a 1/1 white Bird creature token with flying, and a 2/4 white Ox creature token.
     * For each of those tokens, you may put an Aura card from your hand and/or graveyard onto the battlefield attached to it.
     */
    private static final String LIBERATEDLIVESTOCK = "Liberated Livestock";
    // 1 B B - instant- destroy target creature
    private static final String MURDER = "Murder";
    /*
    1 U - instant
    Choose target nonlegendary creature.
    The next time one or more creatures or planeswalkers enter the battlefield this turn,
    they enter as copies of the chosen creature.
     */
    private static final String MYSTICREFLECTION = "Mystic Reflection";
    // {G}{4} - enchantment - If an effect would create one or more tokens under your control, it creates twice that many of those tokens instead.
    //If an effect would put one or more counters on a permanent you control, it puts twice that many of those counters on that permanent instead.
    private static final String DOUBLINGSEASON = "Doubling Season";
    // G - aura - Enchant creature. Whenever enchanted creature deals damage to an opponent, you may draw a card.
    private static final String KEENSENSE ="Keen Sense";
    // 1 G - aura - Enchant creature Enchanted creature gets +2/+2, has reach, and is every creature type.
    private static final String ARACHNOFORM ="Arachnoform";

    private static final String CATTOKEN = "Cat Token";
    private static final String BIRDTOKEN = "Bird Token";
    private static final String OXTOKEN = "Ox Token";


    @Test
    public void threeTokensAfterDeath() {
        addCard(Zone.HAND, playerA, MURDER);
        addCard(Zone.BATTLEFIELD, playerA, LIBERATEDLIVESTOCK);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.GRAVEYARD, playerA, KEENSENSE, 1);
        addCard(Zone.HAND, playerA, ARACHNOFORM, 1);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, MURDER, LIBERATEDLIVESTOCK);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        waitStackResolved(1, PhaseStep.END_TURN);

        execute();

        assertPermanentCount(playerA, CATTOKEN,1);
        assertPermanentCount(playerA, BIRDTOKEN,1);
        assertPermanentCount(playerA, OXTOKEN,1);
    }

    /**
     * Tests Liberated Livestock works with token doublers.
     * Expected to have 6 tokens each enchanted with a keen sense from the graveyard
     */
    @Test
    public void sixTokensAfterDeathWithDoublingSeason() {
        addCard(Zone.HAND, playerA, MURDER);
        addCard(Zone.BATTLEFIELD, playerA, LIBERATEDLIVESTOCK);
        addCard(Zone.BATTLEFIELD, playerA, DOUBLINGSEASON);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.GRAVEYARD, playerA, KEENSENSE, 6);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, MURDER, LIBERATEDLIVESTOCK);
        addTarget(playerA, KEENSENSE);
        addTarget(playerA, KEENSENSE);
        addTarget(playerA, KEENSENSE);
        addTarget(playerA, KEENSENSE);
        addTarget(playerA, KEENSENSE);
        addTarget(playerA, KEENSENSE);

        waitStackResolved(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, CATTOKEN,2);
        assertPermanentCount(playerA, BIRDTOKEN,2);
        assertPermanentCount(playerA, OXTOKEN,2);
        assertPermanentCount(playerA, KEENSENSE, 6);
        assertAttachedTo(playerA, KEENSENSE, CATTOKEN, true);
        assertAttachedTo(playerA, KEENSENSE, BIRDTOKEN, true);
        assertAttachedTo(playerA, KEENSENSE, OXTOKEN, true);

    }

    /**
     * Tests Liberated Livestock's interaction with Mystic Reflection
     * Expected every token should be transformed by Mystic Reflection's ability
     * (There should be 3 Liberated Livestock on the battlefield)
     */
    @Test
    public void interactionWithMysticReflection(){
        addCard(Zone.HAND, playerA, MURDER);
        addCard(Zone.HAND, playerA, MYSTICREFLECTION);
        addCard(Zone.BATTLEFIELD, playerA, LIBERATEDLIVESTOCK);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, KEENSENSE, 1);
        addCard(Zone.HAND, playerA, ARACHNOFORM, 1);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, MYSTICREFLECTION, LIBERATEDLIVESTOCK);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, MURDER, LIBERATEDLIVESTOCK);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        addTarget(playerA, TestPlayer.TARGET_SKIP);

        waitStackResolved(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, LIBERATEDLIVESTOCK,3);
    }

    /**
     * Comprehensive rules 701.3b
     * If the tokens created by Liberated Livestock has protection from the color of the auras
     * Then the aura is expected NOT to move from its current position and NOT be attached to the token
     */
    @Test
    public void tokensHavingProtection() {
        // 1 W - creature - Protection from green
        final String SPECTRALLYNX ="Spectral Lynx";

        addCard(Zone.HAND, playerA, MURDER);
        addCard(Zone.HAND, playerA, MYSTICREFLECTION);
        addCard(Zone.BATTLEFIELD, playerA, SPECTRALLYNX);
        addCard(Zone.BATTLEFIELD, playerA, LIBERATEDLIVESTOCK);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, KEENSENSE, 1);
        addCard(Zone.GRAVEYARD, playerA, ARACHNOFORM, 1);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, MYSTICREFLECTION, SPECTRALLYNX);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, MURDER, LIBERATEDLIVESTOCK);
        addTarget(playerA, KEENSENSE);
        addTarget(playerA, ARACHNOFORM);
        addTarget(playerA, TestPlayer.TARGET_SKIP);

        waitStackResolved(1, PhaseStep.END_TURN);

        execute();
        assertPermanentCount(playerA, SPECTRALLYNX,4);
        assertAttachedTo(playerA, KEENSENSE, SPECTRALLYNX, false);
        assertAttachedTo(playerA, ARACHNOFORM, SPECTRALLYNX, false);
        assertGraveyardCount(playerA, ARACHNOFORM, 1);
        assertHandCount(playerA, KEENSENSE, 1);
    }

    /**
     * Shroud prevents targeting, NOT attachment.
     * The attachment from Liberated Livestock is expected to work
     */
    @Test
    public void tokensHavingShroud() {
        // 4 G - Creature - shroud
        final String DEADLYINSECT = "Deadly Insect";
        // 1 B - Enchantment - Creatures lose all abilities. <- Only the important part for this test
        final String DRESSDOWN = "Dress Down";
        // 1 W -  instant - Destroy target artifact or enchantment.
        final String DISENCHANT = "Disenchant";

        addCard(Zone.HAND, playerA, MURDER);
        addCard(Zone.HAND, playerA, MYSTICREFLECTION);
        addCard(Zone.HAND, playerA, DISENCHANT);
        addCard(Zone.BATTLEFIELD, playerA, DEADLYINSECT);
        addCard(Zone.BATTLEFIELD, playerA, LIBERATEDLIVESTOCK);
        addCard(Zone.BATTLEFIELD, playerA, DRESSDOWN);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, KEENSENSE, 1);
        addCard(Zone.GRAVEYARD, playerA, ARACHNOFORM, 1);

        setStrictChooseMode(true);
        // Setup to give the tokens from Liberated Livestock shroud
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, MYSTICREFLECTION, DEADLYINSECT);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DISENCHANT, DRESSDOWN);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, MURDER, LIBERATEDLIVESTOCK);
        addTarget(playerA, KEENSENSE);
        addTarget(playerA, ARACHNOFORM);
        addTarget(playerA, TestPlayer.TARGET_SKIP);

        waitStackResolved(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, DEADLYINSECT,4);
        assertAttachedTo(playerA, KEENSENSE, DEADLYINSECT, true);
        assertAttachedTo(playerA, ARACHNOFORM, DEADLYINSECT, true);
        assertPermanentCount(playerA, ARACHNOFORM, 1);
        assertPermanentCount(playerA, KEENSENSE, 1);
    }

}
