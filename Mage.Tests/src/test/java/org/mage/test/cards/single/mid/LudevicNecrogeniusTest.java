package org.mage.test.cards.single.mid;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 701.27i
 * If a double-faced permanent would have an “As [this permanent] transforms . . .” ability after it
 * transforms or converts, that ability is applied while that permanent is transforming or converting,
 * not afterward.
 *
 * @author JayDi85
 */
public class LudevicNecrogeniusTest extends CardTestPlayerBase {

    @Test
    public void test_TransformIntoCopyOfExiledCard() {
        // {X}{U}{U}{B}{B}, Exile X creature cards from your graveyard: Transform Ludevic. X can't be 0. Activate only
        // as a sorcery.
        // As this creature transforms into Olag, Ludevic's Hubris, it becomes a copy of a creature card exiled with
        // it, except its name is Olag, Ludevic's Hubris, it's 4/4, and it's a legendary blue and black Zombie in
        // addition to its other colors and types. Put a number of +1/+1 counters on Olag equal to the number of
        // creature cards exiled with it.
        addCard(Zone.BATTLEFIELD, playerA, "Ludevic, Necrogenius");
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 2 + 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.GRAVEYARD, playerA, "Elvish Visionary"); // elf 1/1

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{U}{U}{B}{B}");
        setChoice(playerA, "X=2"); // mana
        setChoice(playerA, "X=2"); // creature cards to exile
        setChoice(playerA, "Grizzly Bears^Elvish Visionary"); // exile for the cost
        // Ludevic, Necrogenius ruling:
        // You choose which exiled creature card Olag is copying as it transforms. It doesn't transform and
        // then become a copy.
        // (2021-09-24)
        setChoice(playerA, "Grizzly Bears"); // copy bear

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Olag, Ludevic's Hubris", 4 + 2, 4 + 2);
        assertCounterCount(playerA, "Olag, Ludevic's Hubris", CounterType.P1P1, 2);
        assertSubtype("Olag, Ludevic's Hubris", SubType.BEAR);
        assertSubtype("Olag, Ludevic's Hubris", SubType.ZOMBIE);
        assertNotSubtype("Olag, Ludevic's Hubris", SubType.ELF);
    }
}
