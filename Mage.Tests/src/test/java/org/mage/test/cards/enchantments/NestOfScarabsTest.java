package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author escplan9
 */
public class NestOfScarabsTest extends CardTestPlayerBase {


    /*
    Nest of Scarabs 2B
    Enchantment
    Whenever you put one or more -1/-1 counters on a creature, create that many 1/1 black Insect creature tokens. 
    */
    private final String nestScarabs = "Nest of Scarabs";

    /*
     * Reported bug: Nest of Scarabs not triggering off -1/-1 counters placed on creatures.
     */
    @Test
    public void scarabs_SoulStinger_TwoCountersTwoTokens() {
        
        /*
        Soulstinger 3B
        Creature - Scorpion Demon 4/5
        When Soulstinger enters the battlefield, put two -1/-1 counter on target creature you control.
        When Soulstinger dies, you may put a -1/-1 counter on target creature for each -1/-1 counter on Soulstinger. 
        */
        String stinger = "Soulstinger";

        addCard(Zone.BATTLEFIELD, playerA, nestScarabs);
        addCard(Zone.HAND, playerA, stinger);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, stinger);
        addTarget(playerA, stinger); // place two -1/-1 counters on himself

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, nestScarabs, 1);
        assertPermanentCount(playerA, stinger, 1);
        assertCounterCount(playerA, stinger, CounterType.M1M1, 2);
        assertPowerToughness(playerA, stinger, 2, 3); // 4/5 with two -1/-1 counters
        assertPermanentCount(playerA, "Insect Token", 2); // two counters = two insects
    }

    /*
    * NOTE: test is failing due to bug in code. See issue #3402
    * Bug from testing:
      Cast Black Sun's Zenith targetting own creature and 3 of opponent's creatures. Only getting 1 token.
    */
    @Test
    public void scarabs_BlackSunsZenithTargettingSelfAndOpponent_4Counters4Tokens() {
        
        /*
        Black Sun's Zenith {X}{B}{B}
       Sorcery
       Put X -1/-1 counters on each creature. Shuffle Black Sun's Zenith into its owner's library.
        */
        String blackZenith = "Black Sun's Zenith";

        String hillGiant = "Hill Giant"; // {3}{R} 3/3
        String grizzly = "Grizzly Bears"; // {1}{G} 2/2
        String memnite = "Memnite"; // {0} 1/1
        String fugitive = "Fugitive Wizard"; // {U} 1/1

        addCard(Zone.BATTLEFIELD, playerA, nestScarabs);
        addCard(Zone.HAND, playerA, blackZenith);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, fugitive);

        addCard(Zone.BATTLEFIELD, playerB, grizzly);
        addCard(Zone.BATTLEFIELD, playerB, memnite);
        addCard(Zone.BATTLEFIELD, playerB, hillGiant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, blackZenith);
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, nestScarabs, 1);
        assertHandCount(playerA, blackZenith, 0);
        assertLibraryCount(playerA, blackZenith, 1); // shuffles back into library
        assertGraveyardCount(playerA, fugitive, 1);
        assertGraveyardCount(playerB, memnite, 1);
        assertPermanentCount(playerB, grizzly, 1);
        assertPermanentCount(playerB, hillGiant, 1);
        assertCounterCount(playerB, grizzly, CounterType.M1M1, 1);
        assertCounterCount(playerB, hillGiant, CounterType.M1M1, 1);
        assertPowerToughness(playerB, grizzly, 1, 1); // 2/2 with -1/-1 counter
        assertPowerToughness(playerB, hillGiant, 2, 2); // 3/3 with -1/-1 counter
        assertPermanentCount(playerA, "Insect Token", 4); // 4 counters = 4 insects
    }

    /*
    * NOTE: test is failing due to bug in code. See issue #3402
    * Bug from testing:
      Cast Black Sun's Zenith targetting own creature and 3 of opponent's creatures. Only getting 1 token.
      This time with both players having Nest of Scarabs out.
    */
    @Test
    public void scarabsForBothPlayers_BlackSunsZenithTargettingSelfAndOpponent_4Counters4Tokens() {
        
        /*
        Black Sun's Zenith {X}{B}{B}
       Sorcery
       Put X -1/-1 counters on each creature. Shuffle Black Sun's Zenith into its owner's library.
        */
        String blackZenith = "Black Sun's Zenith";

        String hillGiant = "Hill Giant"; // {3}{R} 3/3
        String grizzly = "Grizzly Bears"; // {1}{G} 2/2
        String memnite = "Memnite"; // {0} 1/1
        String fugitive = "Fugitive Wizard"; // {U} 1/1

        addCard(Zone.BATTLEFIELD, playerA, nestScarabs);
        addCard(Zone.HAND, playerA, blackZenith);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, fugitive);

        addCard(Zone.BATTLEFIELD, playerB, nestScarabs);
        addCard(Zone.BATTLEFIELD, playerB, grizzly);
        addCard(Zone.BATTLEFIELD, playerB, memnite);
        addCard(Zone.BATTLEFIELD, playerB, hillGiant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, blackZenith);
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, nestScarabs, 1);
        assertPermanentCount(playerB, nestScarabs, 1);
        assertHandCount(playerA, blackZenith, 0);
        assertLibraryCount(playerA, blackZenith, 1); // shuffles back into library
        assertGraveyardCount(playerA, fugitive, 1);
        assertGraveyardCount(playerB, memnite, 1);
        assertPermanentCount(playerB, grizzly, 1);
        assertPermanentCount(playerB, hillGiant, 1);
        assertCounterCount(playerB, grizzly, CounterType.M1M1, 1);
        assertCounterCount(playerB, hillGiant, CounterType.M1M1, 1);
        assertPowerToughness(playerB, grizzly, 1, 1); // 2/2 with -1/-1 counter
        assertPowerToughness(playerB, hillGiant, 2, 2); // 3/3 with -1/-1 counter
        assertPermanentCount(playerB, "Insect Token", 0); // playerB did not place the -1/-1 counters, should not trigger
        assertPermanentCount(playerA, "Insect Token", 4); // 4 counters = 4 insects        
    }

    /*
    Reported bug: Nest of Scarabs not triggering off infect damage dealt by creatures such as Blight Mamba
     */
    @Test
    public void scarab_infectDamageTriggers() {

        String bMamba = "Blight Mamba"; // {1}{G} 1/1 Creature - Snake, Infect with {1}{G}:Regen
        String wOmens = "Wall of Omens"; // {1}{W} 0/4 defender ETB: draw a card

        addCard(Zone.BATTLEFIELD, playerA, nestScarabs);
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
        assertPermanentCount(playerA, "Insect Token", 1);
    }

    /*
    
    Reported bug: Nest of Scarabs not triggering off wither damage dealt by creatures such as Sickle Ripper
     */
    @Test
    public void scarab_witherDamageTriggers() {

        String sickleRipper = "Sickle Ripper"; // {1}{B} 2/1 Creature - Elemental Warrior, Wither
        String wOmens = "Wall of Omens"; // {1}{W} 0/4 defender ETB: draw a card

        addCard(Zone.BATTLEFIELD, playerA, nestScarabs);
        addCard(Zone.BATTLEFIELD, playerA, sickleRipper);
        addCard(Zone.BATTLEFIELD, playerB, wOmens);

        attack(3, playerA, sickleRipper);
        block(3, playerB, wOmens, sickleRipper);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20);
        assertPowerToughness(playerB, wOmens, -2, 2); // 0/4 with two -1/-1 counters
        assertCounterCount(playerB, wOmens, CounterType.M1M1, 2);
        assertPermanentCount(playerA, "Insect Token", 2);
    }
}
