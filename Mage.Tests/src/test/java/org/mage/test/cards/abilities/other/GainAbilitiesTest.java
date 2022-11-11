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
