package org.mage.test.cards.abilities.other;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
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
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "@attach.2", "@bear");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "@bear", VigilanceAbility.class, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

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
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "@attach.2", "@bear");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        //checkAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "@bear", VigilanceAbility.class, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        Permanent permanent = getPermanent("Balduvian Bears");
        Assert.assertEquals("must have 2 dynamic ability instances from two attachments",
                2, permanent.getAbilities(currentGame).stream().filter(
                        a -> a.getEffects().stream().anyMatch(DrawCardSourceControllerEffect.class::isInstance)
                ).count());
    }

}
