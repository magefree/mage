package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class SpliceOnArcaneTest extends CardTestPlayerBase {

    /**
     * Test that it works to cast Through the Breach by slicing it on an arcane
     * spell
     */
    @Test
    public void testSpliceThroughTheBreach() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // Sorcery - Arcane  {R}
        // Lava Spike deals 3 damage to target player.
        addCard(Zone.HAND, playerA, "Lava Spike", 1);
        // You may put a creature card from your hand onto the battlefield. That creature gains haste. Sacrifice that creature at the beginning of the next end step.
        // Splice onto Arcane {2}{R}{R} (As you cast an Arcane spell, you may reveal this card from your hand and pay its splice cost. If you do, add this card's effects to that spell.)
        addCard(Zone.HAND, playerA, "Through the Breach", 1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lava Spike", playerB);
        // activate splice: yes -> card with splice ability -> new target for spliced ability
        setChoice(playerA, true);
        addTarget(playerA, "Through the Breach");
        setChoice(playerA, "Silvercoat Lion"); // target for spliced ability: put from hand to battlefield

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertGraveyardCount(playerA, "Lava Spike", 1);
        assertHandCount(playerA, "Through the Breach", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertAbility(playerA, "Silvercoat Lion", HasteAbility.getInstance(), true);
        Assert.assertEquals("All available mana has to be used", "[]", playerA.getManaAvailable(currentGame).toString());
    }

    @Test
    public void testSpliceTorrentOfStone() {

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Sorcery - Arcane  {R}
        // Lava Spike deals 3 damage to target player.
        addCard(Zone.HAND, playerA, "Lava Spike", 1);
        // Torrent of Stone deals 4 damage to target creature.
        // Splice onto Arcane-Sacrifice two Mountains. (As you cast an Arcane spell, you may reveal this card from your hand and pay its splice cost. If you do, add this card's effects to that spell.)
        addCard(Zone.HAND, playerA, "Torrent of Stone", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        // cast arcane Lava Spike
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lava Spike", playerB);
        // activate splice: yes -> card with splice ability -> new target for spliced ability
        setChoice(playerA, true);
        addTarget(playerA, "Torrent of Stone");
        addTarget(playerA, "Silvercoat Lion"); // target for spliced ability: 4 damage

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertGraveyardCount(playerA, "Lava Spike", 1);
        assertHandCount(playerA, "Torrent of Stone", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Mountain", 0);
        Assert.assertEquals("No more mana available", "[]", playerA.getManaAvailable(currentGame).toString());
    }

    /**
     * Nourishing Shoal's interaction with Splicing Through the Breach is
     * bugged.
     * You should still need to pay 2RR as an additional cost, which is
     * not affected by the alternate casting method of Shoal, but you are able
     * to Splice it for free.
     * This is a very relevant bug right now due to the appearance of the deck
     * over the weekend, and it makes the deck absurdly powerful.
     */
    @Test
    public void testSpliceThroughTheBreach2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // You may exile a green card with converted mana cost X from your hand rather than pay Nourishing Shoal's mana cost.
        // You gain X life.
        addCard(Zone.HAND, playerA, "Nourishing Shoal", 1); // {X}{G}{G}
        addCard(Zone.HAND, playerA, "Giant Growth", 1);
        // You may put a creature card from your hand onto the battlefield. That creature gains haste. Sacrifice that creature at the beginning of the next end step.
        // Splice onto Arcane {2}{R}{R} (As you cast an Arcane spell, you may reveal this card from your hand and pay its splice cost. If you do, add this card's effects to that spell.)
        addCard(Zone.HAND, playerA, "Through the Breach", 1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nourishing Shoal");
        // activate splice: yes -> card with splice ability -> new target for spliced ability
        setChoice(playerA, true);
        addTarget(playerA, "Through the Breach");
        setChoice(playerA, "Silvercoat Lion"); // target for spliced ability: put from hand to battlefield

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 21);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Nourishing Shoal", 1);
        assertHandCount(playerA, "Through the Breach", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertAbility(playerA, "Silvercoat Lion", HasteAbility.getInstance(), true);

        Assert.assertEquals("All available mana has to be used", "[]", playerA.getManaAvailable(currentGame).toString());
    }

    /**
     * Cards involved: Nourishing Shoal, Goryo's Vengeance, Griselbrand,
     * Terminate
     * <p>
     * I actually noticed this bug on the 1.4.3 client, but I didn't see it in
     * the change log for 1.4.4, so I assume it is still unknown. Also, it is a
     * bit of a rules corner case and I haven't seen anyone else report it, so
     * the players of this deck may actually not realize it's incorrect.
     * <p>
     * The scenario was that I cast a Nourishing Shoal with a Goryo's Vengeance
     * spliced to it targeting Griselbrand in my graveyard and exiling
     * Worldspine Wurm. My opponent responded with a Snapcaster Mage, so to
     * deprive them of their ability to reuse their counterspell, I cast the
     * Goryo's Vengeance on the Griselbrand. This one resolved. They then used
     * Terminate on the Griselbrand after I had activated it once. When the
     * Shoal tried to resolve, it should have been countered due to no legal
     * target. However, it caused me to gain 11 life. It did not resurrect
     * Griselbrand (correctly), but it should have done nothing at all.
     * <p>
     * I include the info about the Terminate because thinking through, it could
     * be pertinent. I would guess what is going on here is one of two things.
     * Either the client doesn't recognize the Shoal with a spliced Vengeance as
     * a spell with a single target (because Shoal normally doesn't have a
     * target) or because the Griselbrand ended up back in the graveyard before
     * the Shoal tried to resolve, the client thought its target was still
     * valid. I lean toward the former since the Shoal/Vengeance properly failed
     * to resurrect the now dead again Griselbrand, so I don't think it was
     * reading that as the target, but I'm not certain. I will try to reproduce
     * the error against a bot and update this report.
     */
    @Test
    @Ignore
    public void testCounteredBecauseOfNoLegalTarget() {
        // TODO: rewrite test, it's wrong and misleading-- user report about Griselbrand was destroyed by Terminate after splice announce, but tests don't use it at all (Griselbrand legal target all the time)

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        // You may exile a green card with converted mana cost X from your hand rather than pay Nourishing Shoal's mana cost.
        // You gain X life.
        addCard(Zone.HAND, playerA, "Nourishing Shoal", 1); // "{X}{G}{G}"
        // Return target legendary creature card from your graveyard to the battlefield. That creature gains haste. Exile it at the beginning of the next end step.
        // Splice onto Arcane {2}{B}
        addCard(Zone.HAND, playerA, "Goryo's Vengeance", 1); // {1}{B}
        addCard(Zone.GRAVEYARD, playerA, "Griselbrand", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nourishing Shoal");
        setChoice(playerA, "X=3");
        setChoice(playerA, true); // splice
        addTarget(playerA, "Griselbrand");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goryo's Vengeance", "Griselbrand", "Nourishing Shoal");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Nourishing Shoal", 1);
        assertGraveyardCount(playerA, "Goryo's Vengeance", 1);
        assertPermanentCount(playerA, "Griselbrand", 1);

        assertLife(playerA, 20); // no life gain because Nourishing Shoal has to be countered having no legal targets (from Goryo's V.)
        assertLife(playerB, 20);

    }
}
