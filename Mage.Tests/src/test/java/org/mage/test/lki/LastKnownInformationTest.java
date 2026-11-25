package org.mage.test.lki;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 608.2h
 * If an effect requires information from the game (such as the number of creatures on the battlefield), the
 * answer is determined only once, when the effect is applied. If the effect requires information from a
 * specific object, including the source of the ability itself, the effect uses the current information of that
 * object if it’s in the public zone it was expected to be in; if it’s no longer in that zone, or if the effect
 * has moved it from a public zone to a hidden zone, the effect uses the object’s last known information.
 * See rule 113.7a. If an ability states that an object does something, it’s the object as it exists—or as it
 * most recently existed—that does it, not the ability.
 * <p>
 * 707.2
 * When copying an object, the copy acquires the copiable values of the original object’s characteristics and,
 * when copying a spell, choices made when casting or activating it (modes, targets, the value of X, whether it
 * was kicked, how it will affect multiple targets, and so on). The copiable values are the values derived from
 * the text printed on the object (that text being name, mana cost, color indicator, card type, subtype,
 * supertype, rules text, power, toughness, loyalty, and/or defense), as modified by other copy effects, by its
 * face-down status, and by “as . . . enters” and “as . . . is turned face up” abilities that set power and
 * toughness (and may also set additional characteristics). Other effects (including type-changing and
 * text-changing effects), status, counters, and stickers are not copied.
 *
 * @author noxx, JayDi85
 */
public class LastKnownInformationTest extends CardTestPlayerBase {

    /**
     * see here for more information
     * http://www.slightlymagic.net/forum/viewtopic.php?f=116&t=14516
     *
     * Tests Safehold Elite with persist returns to battlefield with -1/-1
     * counter Murder Investigation has to put 2 tokens onto battlefield because
     * enchanted Safehold Elite was 2/2
     *
     * @author LevelX
     */
    @Test
    public void testPersistTriggersInTime() {
        // Safehold Elite {1}{G/W}
        // Creature - Elf Scout
        // 2/2
        // Persist
        addCard(Zone.BATTLEFIELD, playerA, "Safehold Elite");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // {1}{W}
        // Enchant creature you control
        // When enchanted creature dies, put X 1/1 white Soldier creature tokens onto the battlefield, where X is its power.
        addCard(Zone.HAND, playerA, "Murder Investigation", 1);

        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder Investigation", "Safehold Elite");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Safehold Elite");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, 2); // Let the Lightning Bolt and persist resolve
        // choose triggered ability order
        setChoice(playerA, "When enchanted creature dies");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Safehold Elite", "When enchanted creature dies");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Murder Investigation", 1);
        assertPermanentCount(playerA, "Safehold Elite", 0);
        // because enchanted Safehold Elite's P/T was 2/2, Murder Investigation has to put 2 Soldier onto the battlefield
        assertPermanentCount(playerA, "Soldier Token", 2);
        assertGraveyardCount(playerB, "Lightning Bolt", 2);

        assertActionsCount(playerB, 0);

    }

    /**
     * Here we test that Trostani's first ability checks the toughness on
     * resolve.
     *
     */
    @Test
    public void testTrostaniSelesnyasVoice1() {
        // Whenever another creature you control enters, you gain life equal to that creature's toughness.
        // {1}{G}{W}, {T}: Populate. (Create a tokenonto the battlefield that's a copy of a creature token you control.)
        addCard(Zone.BATTLEFIELD, playerA, "Trostani, Selesnya's Voice");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Giant Growth", 1);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Grizzly Bears", "Grizzly Bears", StackClause.WHILE_NOT_ON_STACK);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Giant Growth", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLife(playerA, 25);
    }

    /**
     * Here we test correct spell interaction by playing Cloudshift BEFORE Giant
     * Growth resolves. Cloudshift will remove 2/2 creature and it will return
     * as 2/2. Giant Growth will be fizzled. That means that player should gain
     * 2 + 2 life.
     */
    @Test
    public void testTrostaniSelesnyasVoice2() {
        addCard(Zone.BATTLEFIELD, playerA, "Trostani, Selesnya's Voice");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Giant Growth", 1);
        addCard(Zone.HAND, playerA, "Cloudshift", 1);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Grizzly Bears", "Grizzly Bears", StackClause.WHILE_NOT_ON_STACK);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Grizzly Bears", "Giant Growth",
                StackClause.WHILE_ON_STACK);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLife(playerA, 24);
    }

    /**
     * Here we test actual use of LKI by playing Cloudshift AFTER Giant Growth
     * resolves. Cloudshift will remove 5/5 creature and it will return as 2/2.
     * That means that player should gain 5 + 2 life.
     *
     */
    @Test
    public void testTrostaniSelesnyasVoice3() {
        addCard(Zone.BATTLEFIELD, playerA, "Trostani, Selesnya's Voice");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Giant Growth", 1);
        addCard(Zone.HAND, playerA, "Cloudshift", 1);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Grizzly Bears", "Grizzly Bears", StackClause.WHILE_NOT_ON_STACK);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Grizzly Bears", "Giant Growth",
                StackClause.WHILE_NOT_ON_STACK);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLife(playerA, 27);
    }

    @Test
    public void test_UseBattlefieldManaValue_AetherMutation() {
        // Return target creature to its owner's hand.
        // Create X 1/1 green Saproling creature tokens, where X is that creature's mana value.
        addCard(Zone.HAND, playerA, "Aether Mutation"); // {3}{G}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        //
        // You may have this creature enter as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerB, "Clone@clone"); // {3}{U}, printed mana value 4
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Gray Ogre"); // mana value 3

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        setChoice(playerB, true); // copy a creature
        setChoice(playerB, "Gray Ogre"); // mana value 3 on the battlefield

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Aether Mutation", "@clone"); // bounce the copy

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, "Clone", 1);
        assertPermanentCount(playerA, "Saproling Token", 3);
    }

    @Test
    public void test_UseBattlefieldManaValue_ArtifactMutation() {
        // Destroy target artifact. It can't be regenerated.
        // Create X 1/1 green Saproling creature tokens, where X is that artifact's mana value.
        addCard(Zone.HAND, playerA, "Artifact Mutation"); // {R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        //
        // You may have this enchantment enter as a copy of any artifact on the battlefield,
        // except it's an enchantment in addition to its other types.
        addCard(Zone.HAND, playerB, "Copy Artifact@copy"); // {1}{U}, printed mana value 2
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring"); // mana value 1

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Copy Artifact");
        setChoice(playerB, true); // copy an artifact
        setChoice(playerB, "Sol Ring"); // mana value 1 on the battlefield

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Artifact Mutation", "@copy"); // destroy the copy

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Copy Artifact", 1);
        assertPermanentCount(playerA, "Saproling Token", 1);
    }

    @Test
    public void test_UseBattlefieldManaValue_AuraMutation() {
        // Destroy target enchantment.
        // Create X 1/1 green Saproling creature tokens, where X is that enchantment's mana value.
        addCard(Zone.HAND, playerA, "Aura Mutation"); // {G}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        //
        // You may have this enchantment enter as a copy of any enchantment on the battlefield.
        addCard(Zone.HAND, playerB, "Copy Enchantment@copy"); // {2}{U}, printed mana value 3
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Honor of the Pure"); // mana value 2

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Copy Enchantment");
        setChoice(playerB, true); // copy an enchantment
        setChoice(playerB, "Honor of the Pure"); // mana value 2 on the battlefield

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Aura Mutation", "@copy"); // destroy the copy

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Copy Enchantment", 1);
        assertPermanentCount(playerA, "Saproling Token", 2);
    }

    @Test
    public void test_UseBattlefieldManaValue_DeathMutation() {
        // Destroy target nonblack creature. It can't be regenerated.
        // Create X 1/1 green Saproling creature tokens, where X is that creature's mana value.
        addCard(Zone.HAND, playerA, "Death Mutation"); // {6}{B}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        //
        // You may have this creature enter as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerB, "Clone@clone"); // {3}{U}, printed mana value 4
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Gray Ogre"); // red, mana value 3

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        setChoice(playerB, true); // copy a creature
        setChoice(playerB, "Gray Ogre"); // nonblack with mana value 3

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Death Mutation", "@clone"); // destroy the copy

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Clone", 1);
        assertPermanentCount(playerA, "Saproling Token", 3);
    }
}
