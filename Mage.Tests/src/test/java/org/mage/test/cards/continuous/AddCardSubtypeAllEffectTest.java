package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddCardSubtypeAllEffectTest extends CardTestPlayerBase {

    /*
    Kudo, King Among Bears
    {G}{W}
    Legendary Creature — Bear
    Other creatures have base power and toughness 2/2 and are Bears in addition to their other types.
    2/2
     */
    private static final String kudo = "Kudo, King Among Bears";

    /*
    Fugitive Wizard
    {U}
    Creature — Human Wizard
    1/1
     */
    private static final String fugitive = "Fugitive Wizard";

    @Test
    public void testAddCardSubtypeAllEffect() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, fugitive, 3);
        addCard(Zone.BATTLEFIELD, playerB, fugitive, 3);
        addCard(Zone.BATTLEFIELD, playerA, kudo);
        addCard(Zone.BATTLEFIELD, playerA, "Savannah", 2);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
            if (permanent.getName().equals(fugitive)) {
                assertTrue(permanent.hasSubtype(SubType.BEAR, currentGame));
                assertTrue(permanent.hasSubtype(SubType.WIZARD, currentGame));
                assertTrue(permanent.hasSubtype(SubType.HUMAN, currentGame));
                assertEquals(2, permanent.getPower().getModifiedBaseValue());
                assertEquals(2, permanent.getToughness().getModifiedBaseValue());
            }
        }

        assertSubtype(kudo, SubType.BEAR);
    }
}
