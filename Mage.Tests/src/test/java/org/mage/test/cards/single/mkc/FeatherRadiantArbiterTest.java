package org.mage.test.cards.single.mkc;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author brahle, JayDi85
 */
public class FeatherRadiantArbiterTest extends CardTestPlayerBase {

    @Test
    public void test_CopyForEachChosenCreature() {
        // Whenever you cast a noncreature spell that targets only Feather, you may choose any number of other
        // creatures that spell could target and pay {2} for each of those creatures. If you do, for each of those
        // creatures, copy that spell. The copy targets that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter"); // 4/3
        //
        // Target creature gets +3/+3 until end of turn.
        addCard(Zone.HAND, playerA, "Giant Growth"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1 + 2 * 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // lion 2/2, opponent's creature can be chosen too

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Feather, Radiant Arbiter");
        addTarget(playerA, "Grizzly Bears^Silvercoat Lion"); // creatures to copy for
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // order of copies on the stack

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Feather, Radiant Arbiter", 4 + 3, 3 + 3);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 3, 2 + 3);
        assertPowerToughness(playerB, "Silvercoat Lion", 2 + 3, 2 + 3);
        assertTappedCount("Forest", true, 1 + 2 * 2);
    }

    @Test
    public void test_ChooseNoCreatures_NoCopies() {
        // Whenever you cast a noncreature spell that targets only Feather, you may choose any number of other
        // creatures that spell could target and pay {2} for each of those creatures. If you do, for each of those
        // creatures, copy that spell. The copy targets that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter"); // 4/3
        //
        // Target creature gets +3/+3 until end of turn.
        addCard(Zone.HAND, playerA, "Giant Growth"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1 + 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Feather, Radiant Arbiter");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // no creatures to copy for

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Feather, Radiant Arbiter", 4 + 3, 3 + 3);
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
        assertTappedCount("Forest", true, 1);
    }

    @Test
    public void test_CantPay_NoCopies() {
        // Whenever you cast a noncreature spell that targets only Feather, you may choose any number of other
        // creatures that spell could target and pay {2} for each of those creatures. If you do, for each of those
        // creatures, copy that spell. The copy targets that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter"); // 4/3
        //
        // Target creature gets +3/+3 until end of turn.
        addCard(Zone.HAND, playerA, "Giant Growth"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1 + 1); // not enough for {2}
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Feather, Radiant Arbiter");
        addTarget(playerA, "Grizzly Bears"); // creature to copy for

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Feather, Radiant Arbiter", 4 + 3, 3 + 3);
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
    }

    @Test
    public void test_AllTargetsOnFeather_CopyTargetsChosenCreatureOnly() {
        // If you cast a noncreature spell that has multiple targets, but it's targeting Feather, Radiant Arbiter
        // with all of them, Feather's last ability will trigger. In that case, each of those copies will also only
        // target one of the chosen creatures.
        // (2024-02-02)

        // Whenever you cast a noncreature spell that targets only Feather, you may choose any number of other
        // creatures that spell could target and pay {2} for each of those creatures. If you do, for each of those
        // creatures, copy that spell. The copy targets that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter"); // 4/3
        //
        // Target creature gets +1/+1 until end of turn.
        // Target creature gets +1/+1 until end of turn.
        // Target creature gets +1/+1 until end of turn.
        addCard(Zone.HAND, playerA, "Seeds of Strength"); // {G}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Savannah", 2 + 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Seeds of Strength");
        addTarget(playerA, "Feather, Radiant Arbiter");
        addTarget(playerA, "Feather, Radiant Arbiter");
        addTarget(playerA, "Feather, Radiant Arbiter");
        addTarget(playerA, "Grizzly Bears"); // creature to copy for

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Feather, Radiant Arbiter", 4 + 3, 3 + 3);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 3, 2 + 3);
    }

    @Test
    public void test_SpellTargetsAnotherObject_NoTrigger() {
        // Whenever you cast a noncreature spell that targets only Feather, you may choose any number of other
        // creatures that spell could target and pay {2} for each of those creatures. If you do, for each of those
        // creatures, copy that spell. The copy targets that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter"); // 4/3
        //
        // Target creature gets +1/+1 until end of turn.
        // Target creature gets +1/+1 until end of turn.
        // Target creature gets +1/+1 until end of turn.
        addCard(Zone.HAND, playerA, "Seeds of Strength"); // {G}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Savannah", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Seeds of Strength");
        addTarget(playerA, "Feather, Radiant Arbiter");
        addTarget(playerA, "Feather, Radiant Arbiter");
        addTarget(playerA, "Grizzly Bears");
        checkStackSize("no trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Feather, Radiant Arbiter", 4 + 2, 3 + 2);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 1);
    }

    @Test
    public void test_SpellWithoutTargets_NoTrigger() {
        // Whenever you cast a noncreature spell that targets only Feather, you may choose any number of other
        // creatures that spell could target and pay {2} for each of those creatures. If you do, for each of those
        // creatures, copy that spell. The copy targets that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter"); // 4/3
        //
        // Draw two cards.
        addCard(Zone.HAND, playerA, "Divination"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Divination");
        checkStackSize("no trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 2);
    }

    @Test
    public void test_CreatureSpell_NoTrigger() {
        // Whenever you cast a noncreature spell that targets only Feather, you may choose any number of other
        // creatures that spell could target and pay {2} for each of those creatures. If you do, for each of those
        // creatures, copy that spell. The copy targets that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter"); // 4/3, Angel
        //
        // Mutate {3}{U}
        // Whenever this creature mutates, draw a card.
        addCard(Zone.HAND, playerA, "Dreamtail Heron"); // {4}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        // mutating creature spell targets only Feather
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dreamtail Heron using Mutate", "Feather, Radiant Arbiter");
        checkStackSize("no trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        setChoice(playerA, true); // mutate under

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Feather, Radiant Arbiter", 1);
        assertHandCount(playerA, 1); // mutate trigger
    }

    @Test
    public void test_OpponentCastsSpell_NoTrigger() {
        // Whenever you cast a noncreature spell that targets only Feather, you may choose any number of other
        // creatures that spell could target and pay {2} for each of those creatures. If you do, for each of those
        // creatures, copy that spell. The copy targets that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter"); // 4/3
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        // Shock deals 2 damage to any target.
        addCard(Zone.HAND, playerB, "Shock"); // {R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock", "Feather, Radiant Arbiter");
        checkStackSize("no trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerA, "Feather, Radiant Arbiter", 2);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_CreatureThatSpellCantTarget_NotOffered() {
        // Whenever you cast a noncreature spell that targets only Feather, you may choose any number of other
        // creatures that spell could target and pay {2} for each of those creatures. If you do, for each of those
        // creatures, copy that spell. The copy targets that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter"); // 4/3
        //
        // Target creature gets +3/+3 until end of turn.
        addCard(Zone.HAND, playerA, "Giant Growth"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1 + 2);
        //
        // Hexproof
        addCard(Zone.BATTLEFIELD, playerB, "Gladecover Scout"); // elf 1/1

        // spell can't target opponent's hexproof creature, so no creatures to choose - no choose dialog
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Feather, Radiant Arbiter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Feather, Radiant Arbiter", 4 + 3, 3 + 3);
        assertPowerToughness(playerB, "Gladecover Scout", 1, 1);
    }

    @Test
    public void test_AuraSpell_CopyBecomesToken() {
        // Whenever you cast a noncreature spell that targets only Feather, you may choose any number of other
        // creatures that spell could target and pay {2} for each of those creatures. If you do, for each of those
        // creatures, copy that spell. The copy targets that creature. (Copies of permanent spells become tokens.)
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter"); // 4/3
        //
        // Enchant creature
        // When Angelic Gift enters, draw a card.
        // Enchanted creature has flying.
        addCard(Zone.HAND, playerA, "Angelic Gift"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2 + 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angelic Gift", "Feather, Radiant Arbiter");
        addTarget(playerA, "Grizzly Bears"); // creature to copy for

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Angelic Gift", 1 + 1); // card + token
        assertTokenCount(playerA, "Angelic Gift", 1);
        assertHandCount(playerA, 1 + 1); // one draw for each aura
        assertAbility(playerA, "Grizzly Bears", FlyingAbility.getInstance(), true);
    }

    @Test
    public void test_FeatherReturnedBeforeResolve_IsOtherCreature() {
        // 400.7. An object that moves from one zone to another becomes a new object with no memory of or relation to
        // its previous existence.

        // Whenever you cast a noncreature spell that targets only Feather, you may choose any number of other
        // creatures that spell could target and pay {2} for each of those creatures. If you do, for each of those
        // creatures, copy that spell. The copy targets that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Feather, Radiant Arbiter"); // 4/3
        //
        // Target creature gets +3/+3 until end of turn.
        addCard(Zone.HAND, playerA, "Giant Growth"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1 + 2);
        //
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Cloudshift"); // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // blink Feather in response to its trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Feather, Radiant Arbiter");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Feather, Radiant Arbiter", "Giant Growth");
        // the returned Feather is a new object, so it's "other creature" for the first trigger
        addTarget(playerA, "Feather, Radiant Arbiter"); // creature to copy for

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // copy targets the new Feather, the original Giant Growth's target is illegal now
        assertPowerToughness(playerA, "Feather, Radiant Arbiter", 4 + 3, 3 + 3);
        assertGraveyardCount(playerA, "Giant Growth", 1);
    }
}
