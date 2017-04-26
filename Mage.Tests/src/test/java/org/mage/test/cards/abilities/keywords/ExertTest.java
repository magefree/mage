package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9
 */
public class ExertTest extends CardTestPlayerBase {

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
        setChoice(playerA, "Yes");
        
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
        setChoice(playerA, "No");
        
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();
        
        assertTapped(gbInitiate, false); // untaps as normal
        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }
    
    /*
    "If you gain control of another player's creature until end of turn and exert it, it will untap during that player's untap step."
    See issue #3183
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
        setChoice(playerB, "Yes");
        
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();
        
        assertGraveyardCount(playerB, aTreason, 1);
        assertLife(playerA, 16);
        assertLife(playerB, 24);
        assertTapped(gbInitiate, false); // stolen creature exerted does untap during owner's untap step
    }

    @Test
    public void combatCelebrantExertedCannotAttackDuringNextCombatPhase() {
        /*
        Combat Celebrant 2R
        Creature - Human Warrior 4/1
        If Combat Celebrant hasn't been exerted this turn, you may exert it as it attacks. When you do, untap all other creatures you control and after this phase, there is an additional combat phase.
        */
        String cCelebrant = "Combat Celebrant";
        String memnite = "Memnite"; // {0} 1/1

        addCard(Zone.BATTLEFIELD, playerA, cCelebrant);
        addCard(Zone.BATTLEFIELD, playerA, memnite);

        attack(1, playerA, cCelebrant);
        attack(1, playerA, memnite);
        setChoice(playerA, "Yes"); // exert for extra turn and untap all creatures
        attack(1, playerA, cCelebrant); // should not be able to attack again due to "if has not been exerted this turn"
        attack(1, playerA, memnite);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 14); // 4 + 1 + 1 (Celebrant once, Memnite twice)
        assertTapped(cCelebrant, true);
        assertTapped(memnite, true);
    }
    
    /*
     * Reported bug: Combat Celebrant able to attack again despite being exerted if Always Watching is in play. (Or presumably any Vigilance granting effect)
     * NOTE: this test is failing at the moment due to bug in code. See issue #3195
    */
    @Test
    public void combatCelebrantExertedCannotAttackDuringNextCombatPhase_InteractionWithAlwaysWatching() {
        /*
        Combat Celebrant 2R
        Creature - Human Warrior 4/1
        If Combat Celebrant hasn't been exerted this turn, you may exert it as it attacks. When you do, untap all other creatures you control and after this phase, there is an additional combat phase.
        */
        String cCelebrant = "Combat Celebrant";
        
        /*
        Always Watching 1WW
        Enchantment
        Non-token creatures you control get +1/+1 and have vigilance. 
        */
        String aWatching = "Always Watching";
        String memnite = "Memnite"; // {0} 1/1

        addCard(Zone.BATTLEFIELD, playerA, aWatching);
        addCard(Zone.BATTLEFIELD, playerA, cCelebrant);
        addCard(Zone.BATTLEFIELD, playerA, memnite);

        attack(1, playerA, cCelebrant);
        attack(1, playerA, memnite);
        setChoice(playerA, "Yes"); // exert for extra turn and untap all creatures
        attack(1, playerA, cCelebrant); // should not be able to attack again due to "if has not been exerted this turn"
        attack(1, playerA, memnite);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped(cCelebrant, false);
        assertTapped(memnite, false);
        assertLife(playerB, 11); // 5 + 2 + 2 (Celebrant once, Memnite twice with +1/+1 on both)
    }
}
