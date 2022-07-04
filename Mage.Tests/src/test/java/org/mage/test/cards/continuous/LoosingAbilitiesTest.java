package org.mage.test.cards.continuous;

import mage.abilities.keyword.SwampwalkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class LoosingAbilitiesTest extends CardTestPlayerBase {

    @Test
    public void GivingSwampwalkFromGraveyard() {

        // Swampwalk
        // As long as Filth is in your graveyard and you control a Swamp, creatures you control have swampwalk.
        addCard(Zone.GRAVEYARD, playerB, "Filth"); // Creature
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertAbility(playerB, "Silvercoat Lion", new SwampwalkAbility(), true);
    }

    /**
     * The Card in the graveyard should have no Swampwalk if Yixlid Jailer
     * effect was added later
     */
    @Test
    public void testYixlidJailerRemovesAbilities() {
        // Cards in graveyards lose all abilities.
        addCard(Zone.HAND, playerA, "Yixlid Jailer"); // Creature 2/1 - {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // Swampwalk
        // As long as Filth is in your graveyard and you control a Swamp, creatures you control have swampwalk.
        addCard(Zone.GRAVEYARD, playerB, "Filth");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yixlid Jailer");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertAbility(playerB, "Silvercoat Lion", new SwampwalkAbility(), false);
    }

    // https://github.com/magefree/mage/issues/1147
    @Test
    public void testYixlidJailerAbilitiesComeBack() {
        // Cards in graveyards lose all abilities.
        addCard(Zone.HAND, playerA, "Yixlid Jailer"); // Creature 2/1 - {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        // Gravecrawler can’t block.
        // You may cast Gravecrawler from your graveyard as long as you control a Zombie.
        addCard(Zone.GRAVEYARD, playerB, "Gravecrawler");
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Walking Corpse"); // Creature 2/2 - Zombie
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yixlid Jailer");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Yixlid Jailer");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Gravecrawler");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Yixlid Jailer", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertPermanentCount(playerB, "Gravecrawler", 1);
    }

    /**
     * Yixlid Jailer works incorrectly with reanimation spels - I cast Unearth
     * targeting Seasoned Pyromancer with a Yixlid Jailer in play, but didnt get
     * the Pyromancer's ETB trigger.
     * This is a bug as Jailer only affaects cards when they are on the battlefield.
     */
    @Test
    public void testYixlidJailerAndETBEffects() {
        // Cards in graveyards lose all abilities.
        addCard(Zone.HAND, playerA, "Yixlid Jailer"); // Creature 2/1 - {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // When Seasoned Pyromancer enters the battlefield, discard two cards, then draw two cards. For each nonland card discarded this way, create a 1/1 red Elemental creature token.
        // {3}{R}{R}, Exile Seasoned Pyromancer from your graveyard: Create two 1/1 red Elemental creature tokens.
        addCard(Zone.GRAVEYARD, playerB, "Seasoned Pyromancer");
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);
        // Return target creature card with converted mana cost 3 or less from your graveyard to the battlefield.
        // Cycling {2}
        addCard(Zone.HAND, playerB, "Unearth", 1); // Sorcery {B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yixlid Jailer");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Unearth");
        // Target card for Unearth
        addTarget(playerB, "Seasoned Pyromancer");
        // Discard 2 cards for Seasoned Pyromancer
        setChoice(playerB, "Lightning Bolt^Lightning Bolt");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Yixlid Jailer", 1);

        assertPermanentCount(playerB, "Seasoned Pyromancer", 1);

        assertGraveyardCount(playerB, "Unearth", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 2);

    }

    /**
     * If an ability triggers when the object that has it is put into a hidden
     * zone from a graveyard, that ability triggers from the graveyard, (such as
     * Golgari Brownscale), Yixlid Jailer will prevent that ability from
     * triggering. (2007-05-01)
     */
    @Test
    public void testYixlidJailerAndPutIntoHandEffect() {
        // Cards in graveyards lose all abilities.
        addCard(Zone.HAND, playerA, "Yixlid Jailer"); // Creature 2/1 - {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // When Golgari Brownscale is put into your hand from your graveyard, you gain 2 life.
        // Dredge 2 (If you would draw a card, instead you may put exactly X cards from the top of
        //   your library into your graveyard. If you do, return this card from your
        //   graveyard to your hand. Otherwise, draw a card. )
        addCard(Zone.GRAVEYARD, playerB, "Golgari Brownscale", 1); // Sorcery {B}
        // Return target creature card from your graveyard to your hand. If it’s a Zombie card, draw a card.
        addCard(Zone.HAND, playerB, "Cemetery Recruitment", 1); // Sorcery {1}{B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yixlid Jailer");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cemetery Recruitment");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Yixlid Jailer", 1);
        assertHandCount(playerB, "Golgari Brownscale", 1);
        assertGraveyardCount(playerB, "Cemetery Recruitment", 1);

        assertLife(playerB, 20);  // The trigger of Golgari Brownscale does not work because of Yixlid Jailer

    }

}
