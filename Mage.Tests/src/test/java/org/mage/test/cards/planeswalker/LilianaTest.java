package org.mage.test.cards.planeswalker;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9
 */
public class LilianaTest extends CardTestPlayerBase {

    @Test
    public void testCreatureGainsZombieAsAdditionalType() {
        /*
        Binding Mummy {1}{W}
        Creature - Zombie 2/2
        Whenever another Zombie enters the battlefield under your control, you may tap target artifact or creature.
         */
        String bMummy = "Binding Mummy";

        /*
        Liliana, Death's Majesty {3}{B}{B}
         Planeswalker — Liliana 5 loyalty
        [+1] : Create a 2/2 black Zombie creature token. Put the top two cards of your library into your graveyard.
        [-3] : Return target creature card from your graveyard to the battlefield. That creature is a black Zombie in addition to its other colors and types.
        [-7] : Destroy all non-Zombie creatures.
         */
        String liliannaDM = "Liliana, Death's Majesty";

        /*
        Winged Shepherd {5}{W}
        Creature - Angel 3/3
        Flying, vigilance
        Cycling {W}
         */
        String wShepherd = "Winged Shepherd";

        String yOx = "Yoked Ox"; // {W} 0/4

        addCard(Zone.BATTLEFIELD, playerA, bMummy);
        addCard(Zone.HAND, playerA, liliannaDM);
        addCard(Zone.GRAVEYARD, playerA, wShepherd);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerB, yOx);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, liliannaDM);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3:"); // Liliana -3
        addTarget(playerA, wShepherd); // returns to battlefield and become zombie on top of other types
        setChoice(playerA, true); // use Binding Mummy ability
        addTarget(playerA, yOx); // tap the ox

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, bMummy, 1);
        assertPermanentCount(playerA, liliannaDM, 1);
        assertPermanentCount(playerA, wShepherd, 1);
        assertPermanentCount(playerB, yOx, 1);
        assertCounterCount(playerA, liliannaDM, CounterType.LOYALTY, 2);
        assertType(wShepherd, CardType.CREATURE, SubType.ZOMBIE); // should have subtype zombie on top of angel type
        assertType(wShepherd, CardType.CREATURE, SubType.ANGEL);
        assertTapped(yOx, true);
    }

    @Test
    public void testCastingCreaturesFromGraveTriggerDesecratedTomb() {

        /*
        Liliana, Untouched by Death {2}{B}{B}
        Legendary Planeswalker — Liliana
        +1: Put the top three cards of your library into your graveyard. If at least one of them is a Zombie card, each opponent loses 2 life and you gain 2 life.
        −2: Target creature gets -X/-X until end of turn, where X is the number of Zombies you control.
        −3: You may cast Zombie cards from your graveyard this turn.
         */
        String liliannaUbD = "Liliana, Untouched by Death";

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, liliannaUbD);
        /*
         * Carrion Feeder {B}
         * Creature — Zombie
         * Carrion Feeder can’t block.
         * Sacrifice a creature: Put a +1/+1 counter on Carrion Feeder.
         */
        addCard(Zone.GRAVEYARD, playerA, "Carrion Feeder");

        // Whenever one or more creature cards leave your graveyard, create a 1/1 black Bat creature token with flying.
        addCard(Zone.BATTLEFIELD, playerA, "Desecrated Tomb", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, liliannaUbD);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3:"); // Liliana -3
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Carrion Feeder");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, liliannaUbD, 1);
        assertCounterCount(playerA, liliannaUbD, CounterType.LOYALTY, 1);
        assertPermanentCount(playerA, "Carrion Feeder", 1);

        assertPermanentCount(playerA, "Bat Token", 1);
    }
}
