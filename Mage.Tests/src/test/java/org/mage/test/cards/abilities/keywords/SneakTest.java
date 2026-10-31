package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 702.190a
 * Sneak is a keyword that represents a static ability that functions while the spell with sneak is on the stack.
 * “Sneak [cost]” means “Any time you could cast an instant during your declare blockers step, you may cast this spell
 * by paying [cost] and returning an unblocked creature you control to its owner’s hand rather than paying this
 * spell’s mana cost.”
 * <p>
 * 702.190b
 * A permanent spell whose sneak cost was paid enters the battlefield tapped and attacking (see rule 506.3a).
 * It will be attacking the same player, planeswalker, or battle as the creature that was returned to its owner’s hand
 * to pay the sneak cost of the spell that became that permanent.
 * <p>
 * 506.4c
 * If a creature is attacking a planeswalker or battle, removing that planeswalker or battle from combat doesn’t remove
 * that creature from combat. It continues to be an attacking creature, although it is not attacking any player,
 * planeswalker, or battle. It may be blocked. If it is unblocked, it will deal no combat damage.
 * <p>
 * 508.4a
 * If a creature would be put onto the battlefield attacking a certain player, and that player is no longer in the
 * game, the creature is put onto the battlefield but is never considered an attacking creature. The same is true if a
 * creature would be put onto the battlefield attacking a planeswalker or battle and that permanent is no longer on the
 * battlefield, is no longer a planeswalker or battle, is a planeswalker that is no longer controlled by a defending
 * player, or is a battle that is no longer protected by a defending player.
 * <p>
 * 707.10
 * To copy a spell, activated ability, or triggered ability means to put a copy of it onto the stack; a copy of a spell
 * isn’t cast and a copy of an activated ability isn’t activated. A copy of a spell or ability copies both the
 * characteristics of the spell or ability and all decisions made for it, including modes, targets, the value of X,
 * and additional or alternative costs. (See rule 601, “Casting Spells.”) Choices that are normally made on resolution
 * are not copied. If an effect of the copy refers to objects used to pay its costs, it uses the objects used to pay
 * the costs of the original spell or ability. A copy of a spell is owned by the player under whose control it was put
 * on the stack. A copy of a spell or ability is controlled by the player under whose control it was put on the stack.
 * A copy of a spell is itself a spell, even though it has no spell card associated with it. A copy of an ability is
 * itself an ability.
 *
 * @author JayDi85
 */
public class SneakTest extends CardTestPlayerBase {

    @Test
    public void test_Sneak_AttackSamePlayer() {
        // Sneak {3}{W/B}
        // When this creature enters, you gain 3 life.
        addCard(Zone.HAND, playerA, "Foot Ninjas"); // {3}{W/B}, 5/5
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3

        attack(1, playerA, "Grizzly Bears", playerB);

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Foot Ninjas with Sneak");
        setChoice(playerA, "Grizzly Bears"); // unblocked attacker to return

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertTapped("Foot Ninjas", true);
        assertCounterCount(playerB, "Jace Beleren", CounterType.LOYALTY, 3);
        assertLife(playerB, 20 - 5);
        assertLife(playerA, 20 + 3);
    }

    @Test
    public void test_Sneak_AttackSamePlaneswalker() {
        // Sneak {3}{W/B}
        // When this creature enters, you gain 3 life.
        addCard(Zone.HAND, playerA, "Foot Ninjas"); // {3}{W/B}, 5/5
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3

        attack(1, playerA, "Grizzly Bears", "Jace Beleren");

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Foot Ninjas with Sneak");
        setChoice(playerA, "Grizzly Bears"); // unblocked attacker to return

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertTapped("Foot Ninjas", true);
        assertGraveyardCount(playerB, "Jace Beleren", 1);
        assertLife(playerB, 20);
        assertLife(playerA, 20 + 3);
    }

    @Test
    public void test_SneakAndPlaneswalkerLeft_NotAttacking() {
        // Sneak {3}{W/B}
        // When this creature enters, you gain 3 life.
        addCard(Zone.HAND, playerA, "Foot Ninjas"); // {3}{W/B}, 5/5
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}, 3 damage to any target
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        attack(1, playerA, "Grizzly Bears", "Jace Beleren");

        // the bear keeps attacking, but attacks nothing
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Lightning Bolt", "Jace Beleren");
        waitStackResolved(1, PhaseStep.DECLARE_BLOCKERS, playerA);

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Foot Ninjas with Sneak");
        setChoice(playerA, "Grizzly Bears"); // unblocked attacker to return

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Jace Beleren", 1);
        assertHandCount(playerA, "Grizzly Bears", 1);
        assertTapped("Foot Ninjas", true);
        assertAttacking("Foot Ninjas", false);
        assertLife(playerB, 20);
        assertLife(playerA, 20 + 3);
    }

    @Test
    public void test_SneakAndCopy_DoubleMajor() {
        // Sneak {3}{W/B}
        // When this creature enters, you gain 3 life.
        addCard(Zone.HAND, playerA, "Foot Ninjas"); // {3}{W/B}, 5/5
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        //
        // Copy target creature spell you control, except it isn't legendary if the spell is legendary.
        addCard(Zone.HAND, playerA, "Double Major"); // {G}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3

        attack(1, playerA, "Grizzly Bears", playerB);

        activateManaAbility(1, PhaseStep.DECLARE_BLOCKERS, playerA, "{T}: Add {W}", 4);
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Foot Ninjas with Sneak");
        setChoice(playerA, "Grizzly Bears"); // unblocked attacker to return

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Double Major", "Foot Ninjas", "Foot Ninjas");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Foot Ninjas", 2);
        assertTappedCount("Foot Ninjas", true, 2);
        assertCounterCount(playerB, "Jace Beleren", CounterType.LOYALTY, 3);
        assertLife(playerB, 20 - 5 - 5);
        assertLife(playerA, 20 + 3 + 3);
    }

    @Test
    public void test_SneakFromGraveyard_NinjaTeen() {
        // Whenever a creature you control leaves the battlefield, each opponent loses 1 life.
        // Level 2: Creatures you control get +1/+0 and have menace.
        // Level 3: Creature cards in your graveyard have sneak {3}{B}.
        // Level 3: You may cast creature spells from your graveyard using their sneak abilities.
        addCard(Zone.BATTLEFIELD, playerA, "Ninja Teen");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3); // {1}{B} + {B} for levels
        //
        addCard(Zone.GRAVEYARD, playerA, "Hill Giant"); // giant 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4); // {3}{B} for sneak
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}: Level 2");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}: Level 3");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        attack(1, playerA, "Grizzly Bears", playerB);

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Hill Giant with Sneak");
        setChoice(playerA, "Grizzly Bears"); // unblocked attacker to return

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertTapped("Hill Giant", true);
        assertCounterCount(playerB, "Jace Beleren", CounterType.LOYALTY, 3);
        assertLife(playerB, 20 - 1 - 4); // bear left the battlefield + giant 4/3 with level 2
    }

    @Test
    public void test_SneakAndEntersTrigger_TurncoatKunoichi() {
        // Sneak {2}{W}{B}
        // When this creature enters, choose target creature an opponent controls. Exile that creature until this
        // creature leaves the battlefield. If this creature's sneak cost was paid, instead exile the chosen creature.
        addCard(Zone.HAND, playerA, "Turncoat Kunoichi"); // {2}{W}{B}, 3/2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // giant 3/3
        addCard(Zone.HAND, playerB, "Lightning Bolt"); // {R}, 3 damage to any target
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        attack(1, playerA, "Grizzly Bears", playerB);

        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Turncoat Kunoichi with Sneak");
        setChoice(playerA, "Grizzly Bears"); // unblocked attacker to return
        addTarget(playerA, "Hill Giant"); // creature to exile

        // the giant stays in exile after Kunoichi leaves the battlefield
        castSpell(1, PhaseStep.END_COMBAT, playerB, "Lightning Bolt", "Turncoat Kunoichi");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Turncoat Kunoichi", 1);
        assertExileCount(playerB, "Hill Giant", 1);
        assertCounterCount(playerB, "Jace Beleren", CounterType.LOYALTY, 3);
        assertLife(playerB, 20 - 3);
    }
}
