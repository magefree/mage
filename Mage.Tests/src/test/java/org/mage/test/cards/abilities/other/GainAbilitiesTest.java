package org.mage.test.cards.abilities.other;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GainAbilitiesTest extends CardTestPlayerBase {

    @Test
    public void test_AttachmentSingleton() {
        // {2}{W}
        // Enchanted creature gets +2/+2.
        // Enchanted creature has vigilance as long as you control a black or green permanent.
        addCard(Zone.HAND, playerA, "Abzan Runemark@attach", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears@bear", 1); // 2/2

        // attach all
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "@attach.1", "@bear");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "@attach.2", "@bear");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "@bear", VigilanceAbility.class, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent permanent = getPermanent("Balduvian Bears");
        Assert.assertEquals("must have only 1 singleton ability instance from two attachments",
                1, permanent.getAbilities(currentGame).stream().filter(VigilanceAbility.class::isInstance).count());
    }

    @Test
    public void test_AttachmentUnique() {
        // {R}
        // Enchanted creature has "{R}, {T}, Discard a card: Draw a card."
        addCard(Zone.HAND, playerA, "Epiphany Storm@attach", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears@bear", 1); // 2/2

        // attach all
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "@attach.1", "@bear");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "@attach.2", "@bear");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent permanent = getPermanent("Balduvian Bears");
        Assert.assertEquals("must have 2 dynamic ability instances from two attachments",
                2, permanent.getAbilities(currentGame).stream().filter(
                        a -> a.getEffects().stream().anyMatch(DrawCardSourceControllerEffect.class::isInstance)
                ).count());
    }

    @Test
    public void testGainAbilityControlledSpells() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        addCard(Zone.GRAVEYARD, playerA, "Hoarding Broodlord"); // gives Pestilent Spirit convoke
        addCard(Zone.HAND, playerA, "Reanimate"); // to put Hoarding Broodlord in play
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1); // to cast Reanimate
        addCard(Zone.BATTLEFIELD, playerA, "Firesong and Sunspeaker"); // gives Shock lifelink
        addCard(Zone.LIBRARY, playerA, "Shock", 1); // to find with Hoarding Broodlord
        addCard(Zone.HAND, playerA, "Covetous Urge"); // makes Pestilent Spirit castable from exile
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4); // to cast Covetous Urge
        addCard(Zone.HAND, playerB, "Pestilent Spirit"); // gives Shock deathtouch
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring"); // to cast Pestilent Spirit

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Hoarding Broodlord"); // tap Swamp, lose 8 life, find Shock
        addTarget(playerA, "Shock");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Covetous Urge", playerB); // tap four Islands, find Pestilent Spirit
        setChoice(playerA, "Pestilent Spirit");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}{C}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pestilent Spirit"); // tap Sol Ring and Hoarding Broodlord
        addTarget(playerA, "Hoarding Broodlord"); // convoke to pay B
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Firesong and Sunspeaker"); // convoke, lethal, gain 2 life
        addTarget(playerA, "Firesong and Sunspeaker"); // convoke to pay R

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 8 + 2); // confirms lifelink ability was added to Shock
        assertGraveyardCount(playerA, "Firesong and Sunspeaker", 1); // must be lethal damage, confirms deathtouch ability added
        assertTapped("Hoarding Broodlord", true); // confirms convoke ability added

    }

    @Ignore
    // TODO: GainAbilityControlledSpellsEffect needs improvement to properly apply only to playable cards in non-hand zones
    // TODO: Figure out how to make the ability apply to the reflexive trigger
    @Test
    public void testGainAbilityControlledSpellsOnly() {

        addCard(Zone.BATTLEFIELD, playerB, "Firesong and Sunspeaker"); // shouldn't give Searing Blood lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Walking Corpse"); // creature to target
        addCard(Zone.HAND, playerA, "Covetous Urge"); // makes Searing Blood castable from exile
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4); // to cast Covetous Urge
        addCard(Zone.HAND, playerB, "Searing Blood"); // to find with Covetous Urge
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2); // to cast Searing Blood

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Covetous Urge", playerB); // tap four Islands, find Searing Blood
        setChoice(playerA, "Searing Blood");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Searing Blood", "Walking Corpse");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Walking Corpse", 1);
        assertLife(playerB, 20); // lifelink should not apply
        assertLife(playerA, 20 - 3);

    }

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9565
     *  1. Cast all three of Frondland Felidar, Jubilant Skybonder, and Proud Wildbonder.
     *  2. When the third one is cast (order doesn't matter), the other two will lose their abilities
     */
    @Ignore
    @Test
    public void gainAbilitiesDontRemoveEachOther() {
        // {2}{W}{G}
        // Vigilance
        // Creatures you control with vigilance have “{1}, {T}: Tap target creature.”
        String frondlandFelidar = "Frondland Felidar";
        // {1}{W/U}{W/U}
        // Flying
        // Creatures you control with flying have “Spells your opponents cast that target this creature cost {2} more to cast.”
        String jubilantSkybonder = "Jubilant Skybonder";
        // {2}{R/G}{R/G}
        // Trample
        // Creatures you control with trample have “You may have this creature assign its combat damage as though it weren’t blocked.”
        String proudWildbonder = "Proud Wildbonder";

        addCard(Zone.HAND, playerA, frondlandFelidar);
        addCard(Zone.HAND, playerA, jubilantSkybonder);
        addCard(Zone.HAND, playerA, proudWildbonder);
        addCard(Zone.BATTLEFIELD, playerA, "Alloy Myr", 11);

        showAvailableAbilities("", 1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, frondlandFelidar, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, jubilantSkybonder, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, proudWildbonder);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        Permanent frondlandFelidarPerm = getPermanent(frondlandFelidar);
        Permanent jubilantSkybonderPerm = getPermanent(jubilantSkybonder);
        Permanent proudWildbonderPerm = getPermanent(proudWildbonder);

        Assert.assertEquals(4, frondlandFelidarPerm.getAbilities(currentGame).size());  // Cast + Vigilence/Flying/Trample + "creature you control gain..." + Ability Gained from own effect.
        Assert.assertEquals(4, jubilantSkybonderPerm.getAbilities(currentGame).size());  // Cast + Vigilence/Flying/Trample + "creature you control gain..." + Ability Gained from own effect.
        Assert.assertEquals(4, proudWildbonderPerm.getAbilities(currentGame).size());  // Cast + Vigilence/Flying/Trample + "creature you control gain..." + Ability Gained from own effect.
    }
}
