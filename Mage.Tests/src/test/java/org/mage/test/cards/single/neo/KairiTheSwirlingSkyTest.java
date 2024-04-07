package org.mage.test.cards.single.neo;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class KairiTheSwirlingSkyTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.k.KairiTheSwirlingSky Kairi, the Swirling Sky} {4}{U}{U}
     * Legendary Creature — Dragon Spirit
     * Flying, ward {3}
     * When Kairi, the Swirling Sky dies, choose one —
     * • Return any number of target nonland permanents with total mana value 6 or less to their owners’ hands.
     * • Mill six cards, then return up to two instant and/or sorcery cards from your graveyard to your hand.
     * 6/6
     */

    private static final String kairi = "Kairi, the Swirling Sky";

    @Test
    public void test_Return_MV6() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, kairi);
        addCard(Zone.BATTLEFIELD, playerA, "Colossal Dreadmaw");
        addCard(Zone.HAND, playerA, "Doom Blade");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        setModeChoice(playerA, "1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", kairi);
        addTarget(playerA, "Colossal Dreadmaw"); // returning dreadmaw

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Colossal Dreadmaw", 1);
    }

    @Test
    public void test_Return_MV4_MV0() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, kairi);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Abbey Griffin");
        addCard(Zone.HAND, playerA, "Doom Blade");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        setModeChoice(playerA, "1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", kairi);
        addTarget(playerA, "Abbey Griffin^Memnite"); // returning both Griffin & Memnite

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Abbey Griffin", 1);
        assertHandCount(playerA, "Memnite", 1);
    }

    @Test
    public void test_Cant_Return_MV7() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, kairi);
        addCard(Zone.BATTLEFIELD, playerA, "Axebane Stag");
        addCard(Zone.HAND, playerA, "Doom Blade");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        setModeChoice(playerA, "1"); // choose mode #1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", kairi);
        addTarget(playerA, "Axebane Stag"); // returning Axebane Stag is an invalid target

        setStopAt(1, PhaseStep.END_TURN);
        String foundError = "";
        try {
            execute();
        } catch (AssertionError e) {
            foundError = e.getMessage();
        }
        Assert.assertEquals("PlayerA - Targets list was setup by addTarget with [Axebane Stag], but not used", foundError.split("\n")[0]);
    }
}
