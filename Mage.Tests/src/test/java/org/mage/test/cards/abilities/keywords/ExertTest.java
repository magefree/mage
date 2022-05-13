package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author escplan9
 */
public class ExertTest extends CardTestPlayerBase {

    @Test
    public void exertGustWalker() {

        String gWalker = "Gust Walker";


        addCard(Zone.BATTLEFIELD, playerA, gWalker);
        attack(1, playerA, gWalker);
        setChoice(playerA, true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertAbility(playerA, gWalker, FlyingAbility.getInstance(), true);
        assertPowerToughness(playerA, gWalker, 3, 3);
    }

    @Test
    public void exertHoodedBrawler() {

        String brawler = "Hooded Brawler";


        addCard(Zone.BATTLEFIELD, playerA, brawler);
        attack(1, playerA, brawler);
        setChoice(playerA, true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, brawler, 5, 4);
    }


    @Test
    public void exertBitterbladeWarrior() {

        String warrior = "Bitterblade Warrior";


        addCard(Zone.BATTLEFIELD, playerA, warrior);
        attack(1, playerA, warrior);
        setChoice(playerA, true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, warrior, DeathtouchAbility.getInstance(), true);
        assertPowerToughness(playerA, warrior, 3, 2);
    }

    @Test
    public void exertRhetCropSpearmaster() {

        String spearmaster = "Rhet-Crop Spearmaster";


        addCard(Zone.BATTLEFIELD, playerA, spearmaster);
        attack(1, playerA, spearmaster);
        setChoice(playerA, true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, spearmaster, FirstStrikeAbility.getInstance(), true);
        assertPowerToughness(playerA, spearmaster, 4, 1);
    }

    @Test
    public void exertTahCropElite(){
        String elite = "Tah-Crop Elite";
        addCard(Zone.BATTLEFIELD, playerA, elite);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        attack(1, playerA, elite);
        setChoice(playerA, true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
        assertPowerToughness(playerA, elite, 3, 3);
        assertPowerToughness(playerB, "Grizzly Bears", 2, 2);
    }

    @Test
    public void exertEmberhornMinotaur(){
        String minotaur = "Emberhorn Minotaur";

        addCard(Zone.BATTLEFIELD, playerA, minotaur);
        attack(1, playerA, minotaur);
        setChoice(playerA, true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, minotaur, new MenaceAbility(), true);
        assertPowerToughness(playerA, minotaur, 5, 4);
    }

    @Test
    public void exertNefcropEntangler(){
        String entangler = "Nef-Crop Entangler";

        addCard(Zone.BATTLEFIELD, playerA, entangler);
        attack(1, playerA, entangler);
        setChoice(playerA, true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, entangler, 3, 3);
    }

    @Test
    public void exertTrueheartTwins(){
        String twins = "Trueheart Twins";
        addCard(Zone.BATTLEFIELD, playerA, twins);
        addCard(Zone.BATTLEFIELD, playerA, "Hyena Pack");
        addCard(Zone.BATTLEFIELD, playerB, "Dune Beetle");
        attack(1, playerA, twins);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPowerToughness(playerA, twins, 5, 4);
        assertPowerToughness(playerA, "Hyena Pack", 4, 4);
        assertPowerToughness(playerB, "Dune Beetle", 1, 4);

    }

    @Test
    public void exertOtherCreatureTrueheartTwins(){
        String twins = "Trueheart Twins";
        String gWalker = "Gust Walker";


        addCard(Zone.BATTLEFIELD, playerA, gWalker);
        addCard(Zone.BATTLEFIELD, playerA, twins);
        attack(1, playerA, gWalker);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPowerToughness(playerA, twins, 5, 4);
        assertPowerToughness(playerA, gWalker, 4, 3);


    }

    @Test
    public void exertUsedDoesNotUntapNextUntapStep() {
        
        /*
        Glory-Bound Initiate {1}{W}
        Creature - Human Warrior 3/1
        You may exert Glory-Bound Intiate as it attacks. When you do, it gets +1/+3 and gains lifelink until end of turn. 
        */
        String gbInitiate = "Glory-Bound Initiate";

        addCard(Zone.BATTLEFIELD, playerA, gbInitiate);
        attack(1, playerA, gbInitiate);
        setChoice(playerA, true);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertTapped(gbInitiate, true); // does not untap due to exert
        assertLife(playerA, 24); // exerted 4/4 lifelink
        assertLife(playerB, 16);
    }

    @Test
    public void exertNotUsedRegularAttack() {
        
        /*
        Glory-Bound Initiate {1}{W}
        Creature - Human Warrior 3/1
        You may exert Glory-Bound Intiate as it attacks. When you do, it gets +1/+3 and gains lifelink until end of turn. 
        */
        String gbInitiate = "Glory-Bound Initiate";

        addCard(Zone.BATTLEFIELD, playerA, gbInitiate);
        attack(1, playerA, gbInitiate);
        setChoice(playerA, false);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertTapped(gbInitiate, false); // untaps as normal
        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

    /*
     * "If you gain control of another player's creature until end of turn and exert it,
     * it will untap during that player's untap step."
     * See issue #3183
    */
    @Test
    public void stolenExertCreatureShouldUntapDuringOwnersUntapStep() {
                        
        /*
        Glory-Bound Initiate {1}{W}
        Creature - Human Warrior 3/1
        You may exert Glory-Bound Intiate as it attacks. When you do, it gets +1/+3 and gains lifelink until end of turn. 
        */
        String gbInitiate = "Glory-Bound Initiate";
        String aTreason = "Act of Treason"; // {2}{R} sorcery gain control target creature, untap, gains haste until end of turn

        addCard(Zone.HAND, playerA, gbInitiate);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerB, aTreason);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, gbInitiate);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, aTreason, gbInitiate);
        attack(2, playerB, gbInitiate);
        setChoice(playerB, true);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, aTreason, 1);
        assertLife(playerA, 16);
        assertLife(playerB, 24);
        assertTapped(gbInitiate, false); // stolen creature exerted does untap during owner's untap step
    }

    @Test
    public void combatCelebrantExertedCannotExertAgainDuringNextCombatPhase() {
        /*
         * Combat Celebrant 2R
         * Creature - Human Warrior 4/1
         * If Combat Celebrant hasn't been exerted this turn, you may exert it as it attacks.
         * When you do, untap all other creatures you control and after this phase, there is an additional combat phase.
         */
        String cCelebrant = "Combat Celebrant";
        String memnite = "Memnite"; // {0} 1/1

        addCard(Zone.BATTLEFIELD, playerA, cCelebrant);
        addCard(Zone.BATTLEFIELD, playerA, memnite);

        setStrictChooseMode(true);

        // First combat phase
        attack(1, playerA, cCelebrant);
        attack(1, playerA, memnite);
        setChoice(playerA, true); // exert for extra turn

        setStopAt(1, PhaseStep.COMBAT_DAMAGE);
        execute();

        assertLife(playerB, 15); // 4 + 1
        assertTapped(cCelebrant, true);
        assertTapped(memnite, false);

        // Second combat phase
        attack(1, playerA, memnite);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 14); // 4 + 1 + 1 (Celebrant once, Memnite twice)
        assertTapped(cCelebrant, true);
        assertTapped(memnite, true);
    }

    /*
     * Reported bug: Combat Celebrant able to exert again despite being exerted already if Always Watching is in play.
     * (Or presumably any Vigilance granting effect)
     */
    @Test
    public void combatCelebrantExertedCannotExertDuringNextCombatPhase_InteractionWithAlwaysWatching() {
        /*
         * Combat Celebrant 2R
         * Creature - Human Warrior 4/1
         * If Combat Celebrant hasn't been exerted this turn, you may exert it as it attacks.
         * When you do, untap all other creatures you control and after this phase, there is an additional combat phase.
         */
        String cCelebrant = "Combat Celebrant";

        /*
         * Always Watching 1WW
         * Enchantment
         * Non-token creatures you control get +1/+1 and have vigilance.
         */
        String aWatching = "Always Watching";
        String memnite = "Memnite"; // {0} 1/1

        addCard(Zone.BATTLEFIELD, playerA, aWatching);
        addCard(Zone.BATTLEFIELD, playerA, cCelebrant);
        addCard(Zone.BATTLEFIELD, playerA, memnite);

        setStrictChooseMode(true);

        // First combat phase
        attack(1, playerA, cCelebrant);
        attack(1, playerA, memnite);
        setChoice(playerA, true); // exert for extra turn

        setStopAt(1, PhaseStep.COMBAT_DAMAGE);
        execute();

        assertLife(playerB, 13); // 5 + 2 (Celebrant once, Memnite once with +1/+1 on both)
        assertTapped(cCelebrant, false);
        assertTapped(memnite, false);

        // Extra combat phase
        attack(1, playerA, cCelebrant);
        attack(1, playerA, memnite);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped(cCelebrant, false);
        assertTapped(memnite, false);
        assertLife(playerB, 6); // 5 + 2 + 5 + 2 (Celebrant twice, Memnite twice with +1/+1 on both)
    }

    /*
     * Reported bug: Combat Celebrant able to attack again despite being exerted already if Arlinn Kord granted him vigilance.
    */
    @Test
    public void combatCelebrantExertedCannotExertAgainDuringNextCombatPhase_InteractionWithArlinnKord() {
        /*
         * Combat Celebrant 2R
         * Creature - Human Warrior 4/1
         * If Combat Celebrant hasn't been exerted this turn, you may exert it as it attacks.
         * When you do, untap all other creatures you control and after this phase, there is an additional combat phase.
        */
        String cCelebrant = "Combat Celebrant";
        
        /*
         * Arlinn Kord {2}{R}{G}
         * Planeswalker — Arlinn 3 loyalty
         * +1: Until end of turn, up to one target creature gets +2/+2 and gains vigilance and haste.
         * 0: Create a 2/2 green Wolf creature token. Transform Arlinn Kord.
        */
        String aKord = "Arlinn Kord";

        addCard(Zone.BATTLEFIELD, playerA, aKord);
        addCard(Zone.BATTLEFIELD, playerA, cCelebrant);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:"); // grant +2/+2 vig and haste to celebrant
        addTarget(playerA, cCelebrant);

        // First combat phase
        attack(1, playerA, cCelebrant);
        setChoice(playerA, true); // exert for extra turn

        // Second combat phase
        attack(1, playerA, cCelebrant);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped(cCelebrant, false);
        assertCounterCount(aKord, CounterType.LOYALTY, 4);
        assertLife(playerB, 8); // 6 + 6 = 12 damage from Celebrant attacking twice
        assertPowerToughness(playerA, cCelebrant, 6, 3);
    }
}
