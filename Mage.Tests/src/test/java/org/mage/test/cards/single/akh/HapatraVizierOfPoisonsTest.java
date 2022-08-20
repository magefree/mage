package org.mage.test.cards.single.akh;

import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9
 */
public class HapatraVizierOfPoisonsTest extends CardTestPlayerBase {

    /*
        Hapatra, Vizier of Poisons BG
        Legendary Creature - Human Cleric 2/2
        Whenever Hapatra, Vizier of Poisons deals combat damage to a player, you may put a -1/-1 counter on target creature.
        Whenever you put one or more -1/-1 counters on a creature, create a 1/1 green Snake creature token with deathtouch.
     */
    private final String hapatra = "Hapatra, Vizier of Poisons";

    @Test
    public void hapatraCombatDamageToPlayer() {

        String gBears = "Grizzly Bears";
        addCard(Zone.BATTLEFIELD, playerA, hapatra);
        addCard(Zone.BATTLEFIELD, playerB, gBears);

        attack(3, playerA, hapatra);
        setChoice(playerA, true); // opt to place -1/-1 counter on creature
        addTarget(playerA, gBears);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 18);
        assertCounterCount(playerB, gBears, CounterType.M1M1, 1);
        assertPowerToughness(playerB, gBears, 1, 1); // 2/2 with -1/1 counter
        assertPermanentCount(playerA, "Snake Token", 1);
        assertAbility(playerA, "Snake Token", DeathtouchAbility.getInstance(), true);
    }

    /*
    Issue #3288.
    Hapatra, Vizier of Poisons is not triggering off of Infect. Tested with Blight Mamba
     */
    @Test
    public void infectDamageTriggersHapatra() {

        String bMamba = "Blight Mamba"; // {1}{G} 1/1 Creature - Snake, Infect with {1}{G}:Regen
        String wOmens = "Wall of Omens"; // {1}{W} 0/4 defender ETB: draw a card

        addCard(Zone.BATTLEFIELD, playerA, hapatra);
        addCard(Zone.BATTLEFIELD, playerA, bMamba);
        addCard(Zone.BATTLEFIELD, playerB, wOmens);

        attack(3, playerA, bMamba);
        block(3, playerB, wOmens, bMamba);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20);
        assertCounterCount(playerB, CounterType.POISON, 0);
        assertPowerToughness(playerB, wOmens, -1, 3); // 0/4 with -1/-1 counter
        assertCounterCount(playerB, wOmens, CounterType.M1M1, 1);
        assertPermanentCount(playerA, "Snake Token", 1);
        assertAbility(playerA, "Snake Token", DeathtouchAbility.getInstance(), true);
    }

    /*
     * Reported bug for issue #3252
     * Devoted Druid untap ability not triggering Hapatra
     */
    @Test
    public void devotedDruidTriggersHapatra() {

        /*
      Devoted Druid {1}{G}
        Creature - Elf Druid 0/2
        {T}: Add Green.
        Put a -1/-1 counter on Devoted Druid: Untap Devoted Druid.
         */
        String dDruid = "Devoted Druid";

        addCard(Zone.BATTLEFIELD, playerA, hapatra);
        addCard(Zone.BATTLEFIELD, playerA, dDruid);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Put");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, dDruid, CounterType.M1M1, 1);
        assertPowerToughness(playerA, dDruid, -1, 1); // 0/2 with -1/-1
        assertPermanentCount(playerA, "Snake Token", 1);
        assertAbility(playerA, "Snake Token", DeathtouchAbility.getInstance(), true);
    }

    /**
     * Testing fix for issue #5886
     * Tokens with wither/infect that deal damage were not triggering Hapatra's snake creating ability
     * @author jgray1206
     */
    @Test
    public void testTokensWithInfectTriggerHapatra() {
        String concordantCrossroads = "Concordant Crossroads"; //All creatures have haste
        String krakenHatchling = "Kraken Hatchling"; //Arbitrary creature to defend
        String triumphOfTheHordes = "Triumph of the Hordes"; //Creatures you control gain infect
        String sprout = "Sprout"; //Create a 1/1 Saproling creature token
        
        addCard(Zone.HAND, playerA, sprout, 1);
        addCard(Zone.HAND, playerA, triumphOfTheHordes, 1);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerA, hapatra, 1);
        addCard(Zone.BATTLEFIELD, playerA, concordantCrossroads, 1);
        addCard(Zone.BATTLEFIELD, playerB, krakenHatchling, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sprout);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, triumphOfTheHordes);

        attack(1, playerA, "Saproling Token");
        block(1, playerB, krakenHatchling, "Saproling Token");
        setStopAt(1, PhaseStep.END_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerB, krakenHatchling, -2, 2);
        assertCounterCount(playerB, krakenHatchling, CounterType.M1M1, 2);
        assertPermanentCount(playerA, "Snake Token", 1); //Should have triggered when Saproling added -1/-1 counter
    }
}
