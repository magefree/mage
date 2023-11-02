package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BargainTest extends CardTestPlayerBase {

    /**
     * Troublemaker Ouphe
     * {1}{G}
     * Creature — Ouphe
     * <p>
     * Bargain (You may sacrifice an artifact, enchantment, or token as you cast this spell.)
     * When Troublemaker Ouphe enters the battlefield, if it was bargained, exile target artifact or enchantment an opponent controls.
     */
    private static final String troublemakerOuphe = "Troublemaker Ouphe";

    // Artifact to be targetted by Troublemaker's Ouphe trigger, and be bargain fodder.
    private static final String glider = "Aesthir Glider";
    // To blink Ouphe.
    private static final String cloudshift = "Cloudshift";

    /**
     * Torch the Tower
     * {R}
     * Instant
     * <p>
     * Bargain (You may sacrifice an artifact, enchantment, or token as you cast this spell.)
     * Torch the Tower deals 2 damage to target creature or planeswalker. If this spell was bargained, instead it deals 3 damage to that permanent and you scry 1.
     * If a permanent dealt damage by Torch the Tower would die this turn, exile it instead.
     */
    private static final String torchTheTower = "Torch the Tower";

    // 3 damage to target -- to finish off creatures and check torch's exile.
    private static final String lightningBolt = "Lightning Bolt";

    // 4/4 Artifact
    private static final String stoneGolem = "Stone Golem";

    /**
     * Hamlet Glutton
     * {5}{G}{G}
     * Creature — Giant
     * <p>
     * Bargain (You may sacrifice an artifact, enchantment, or token as you cast this spell.)
     * <p>
     * This spell costs {2} less to cast if it’s bargained.
     * <p>
     * Trample
     * <p>
     * When Hamlet Glutton enters the battlefield, you gain 3 life.
     * <p>
     * 6/6
     */
    private static final String glutton = "Hamlet Glutton";

    private static final String relic = "Darksteel Relic"; // {0} Artifact

    @Test
    public void testBargainNotPaidOuphe() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, troublemakerOuphe);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, glider); // Could be bargain.

        addCard(Zone.BATTLEFIELD, playerB, glider);

        setStrictChooseMode(true);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, troublemakerOuphe);
        setChoice(playerA, false); // Do not bargain.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, glider, 1);
    }

    @Test
    public void testBargainNothingToBargain() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, troublemakerOuphe);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        addCard(Zone.BATTLEFIELD, playerB, glider);

        setStrictChooseMode(true);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, troublemakerOuphe);
        // Nothing to bargain.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, glider, 1);
    }

    @Test
    public void testBargainPaidOupheNothingToTarget() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, troublemakerOuphe);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, glider); // Is an artifact, hence something you can sac to Bargain.

        setStrictChooseMode(true);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, troublemakerOuphe);
        setChoice(playerA, true); // true to bargain
        setChoice(playerA, glider); // choose to sac glider.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, glider, 1);
    }

    @Test
    public void testBargainPaidOuphe() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, troublemakerOuphe);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, glider); // Is an artifact, hence something you can sac to Bargain.

        addCard(Zone.BATTLEFIELD, playerB, glider);

        setStrictChooseMode(true);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, troublemakerOuphe);
        setChoice(playerA, true); // true to bargain
        setChoice(playerA, glider); // choose to sac glider.
        addTarget(playerA, glider); // exile opponent's glider.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, glider, 0);
        assertExileCount(playerB, glider, 1);
        assertGraveyardCount(playerA, glider, 1);
    }

    @Test
    public void testBargainPaidOupheThenFlicker() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, troublemakerOuphe);
        addCard(Zone.HAND, playerA, cloudshift);
        addCard(Zone.BATTLEFIELD, playerA, "Savannah", 3);
        addCard(Zone.BATTLEFIELD, playerA, glider); // Is an artifact, hence something you can sac to Bargain.

        addCard(Zone.BATTLEFIELD, playerB, glider);
        addCard(Zone.BATTLEFIELD, playerB, stoneGolem);

        setStrictChooseMode(true);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, troublemakerOuphe);
        setChoice(playerA, true); // true to bargain
        setChoice(playerA, glider); // choose to sac glider.
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1); // let Ouphe resolve, trigger on the stack.
        addTarget(playerA, glider); // exile opponent's glider with etb.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cloudshift, troublemakerOuphe); // blink Ouphe.
        // No trigger. It is no longer bargaining.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, glider, 0);
        assertExileCount(playerB, glider, 1);
        assertPermanentCount(playerB, stoneGolem, 1);
        assertExileCount(playerB, stoneGolem, 0);
        assertGraveyardCount(playerA, glider, 1);
    }

    @Test
    public void testBargainNotPaidTorch() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, torchTheTower, 1);
        addCard(Zone.HAND, playerA, lightningBolt, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, glider); // Could be bargain.

        addCard(Zone.BATTLEFIELD, playerB, stoneGolem);

        setStrictChooseMode(true);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, torchTheTower, stoneGolem);
        setChoice(playerA, false); // Do not bargain.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerB, stoneGolem, 2);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lightningBolt, stoneGolem);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, glider, 1);
        assertPermanentCount(playerB, stoneGolem, 0);
        assertExileCount(playerB, stoneGolem, 1);
    }

    @Test
    public void testBargainPaidTorch() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, torchTheTower, 1);
        addCard(Zone.HAND, playerA, lightningBolt, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, glider); // Could be bargain.
        addCard(Zone.LIBRARY, playerA, "Plains"); // see the card on scry

        addCard(Zone.BATTLEFIELD, playerB, stoneGolem);

        setStrictChooseMode(true);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, torchTheTower, stoneGolem);
        setChoice(playerA, true); // Do bargain.
        setChoice(playerA, glider); // Bargain the glider away.
        addTarget(playerA, "Plains"); // scry the Plains away

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerB, stoneGolem, 3);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lightningBolt, stoneGolem);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, glider, 0);
        assertPermanentCount(playerB, stoneGolem, 0);
        assertExileCount(playerB, stoneGolem, 1);
    }

    @Test
    public void testBargainOn5ManaGlutton() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, glutton);
        addCard(Zone.HAND, playerA, relic);

        checkPlayableAbility("before relic", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hamlet Glutton", false);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, relic, true);

        checkPlayableAbility("after relic", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hamlet Glutton", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, glutton, true);
        setChoice(playerA, true); // Do bargain.
        setChoice(playerA, relic); // Bargain the relic away.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 + 3);
    }

    @Test
    public void testBargainOn7ManaGlutton() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        addCard(Zone.HAND, playerA, glutton);
        addCard(Zone.HAND, playerA, relic);

        checkPlayableAbility("before relic", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hamlet Glutton", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, relic, true);

        checkPlayableAbility("after relic", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hamlet Glutton", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, glutton, true);
        setChoice(playerA, true); // Do bargain.
        setChoice(playerA, relic); // Bargain the relic away.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 + 3);
        assertTappedCount("Forest", true, 5);
    }

    @Test
    public void testNoBargainOn7ManaGlutton() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        addCard(Zone.HAND, playerA, glutton);
        addCard(Zone.HAND, playerA, relic);

        checkPlayableAbility("before relic", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hamlet Glutton", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, relic, true);

        checkPlayableAbility("after relic", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hamlet Glutton", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, glutton, true);
        setChoice(playerA, false); // No bargain.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 + 3);
        assertTappedCount("Forest", true, 7);
    }

    @Test
    public void testCantBargainOn7ManaGlutton() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        addCard(Zone.HAND, playerA, glutton);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, glutton, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 + 3);
        assertTappedCount("Forest", true, 7);
    }
}
