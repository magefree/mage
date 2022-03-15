
package org.mage.test.cards.continuous;

import mage.abilities.Ability;
import mage.constants.AbilityType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DependentEffectsTest extends CardTestPlayerBase {

    /**
     * Opalescence plus Enchanted Evening are still not wiping any lands.
     */
    @Test
    public void testLandsAreDestroyed() {
        // Each other non-Aura enchantment is a creature in addition to its other types 
        // and has base power and base toughness each equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Opalescence", 1); // {2}{W}{W}

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 9);
        addCard(Zone.BATTLEFIELD, playerA, "War Horn", 1);

        // All permanents are enchantments in addition to their other types.
        addCard(Zone.HAND, playerA, "Enchanted Evening"); //  {3}{W/U}{W/U}

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Opalescence");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Enchanted Evening");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, "Enchanted Evening", 5, 5);
        assertPowerToughness(playerA, "War Horn", 3, 3);

        assertPermanentCount(playerA, "Island", 0);
        assertPermanentCount(playerB, "Plains", 0);
    }

    /**
     * Opalescence is dependent on Enchanted Evening, so it will be applied
     * after it regardless of timestamp.
     *
     * Tokens can also have mana costs, and as a consequence of that, converted
     * mana costs. A token created with Rite of Replication would have the mana
     * cost of the creature it targeted. Most tokens do not have mana costs
     * though.
     *
     * Tokens with no mana costs would be 0/0, as you said, and would indeed be
     * put into owner's graveyard next time State Based Actionas are performed.
     * Tokens with mana costs would naturally have whatever power and toughness
     * their CMC indicated.
     */
    @Test
    public void testTokensAreDestroyed() {
        // Each other non-Aura enchantment is a creature in addition to its other types and has base power and base toughness each equal to its converted mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Opalescence", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 9);
        // Kicker {5}
        // Create a tokenthat's a copy of target creature onto the battlefield. If Rite of Replication was kicked, put five of those tokens onto the battlefield instead.
        addCard(Zone.HAND, playerA, "Rite of Replication", 1); // This token can have a cmc
        // All permanents are enchantments in addition to their other types.
        addCard(Zone.HAND, playerA, "Enchanted Evening"); //  {3}{W/U}{W/U}

        addCard(Zone.BATTLEFIELD, playerB, "Cobblebrute", 1); // 5/2  cmc = 4
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // Put two 1/1 white Soldier creature tokens onto the battlefield.
        addCard(Zone.HAND, playerB, "Raise the Alarm"); //  Instant {1}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rite of Replication", "Cobblebrute");
        setChoice(playerA, false); // no kicker
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Raise the Alarm");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Enchanted Evening");
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Rite of Replication", 1);
        assertGraveyardCount(playerB, "Raise the Alarm", 1);

        assertPowerToughness(playerA, "Enchanted Evening", 5, 5);

        assertPowerToughness(playerB, "Cobblebrute", 4, 4);
        assertPowerToughness(playerA, "Cobblebrute", 4, 4);

        assertPermanentCount(playerB, "Soldier Token", 0);
        assertPermanentCount(playerA, "Island", 0);
        assertPermanentCount(playerB, "Plains", 0);
    }

    @Test
    public void testYixlidJailerAndNecroticOozeBasic() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // As long as Necrotic Ooze is on the battlefield, it has all activated abilities of all creature cards in all graveyards
        addCard(Zone.BATTLEFIELD, playerA, "Necrotic Ooze", 1);
        // {2}{1},{T}: Tap target creature.
        addCard(Zone.GRAVEYARD, playerA, "Akroan Jailer", 1);
        // {T}: Target attacking creature gets +1/+1 until end of turn.
        addCard(Zone.GRAVEYARD, playerB, "Anointer of Champions", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent necroticOoze = getPermanent("Necrotic Ooze", playerA);
        int numberOfActivatedAbilities = 0;
        for (Ability ability : necroticOoze.getAbilities(currentGame)) {
            if (ability.getAbilityType() == AbilityType.ACTIVATED) {
                numberOfActivatedAbilities++;
            }
        }
        Assert.assertEquals("Two abilities for Necrotic Ooze", 2, numberOfActivatedAbilities);
    }

    @Test
    public void testYixlidJailerAndNecroticOozeLooseAll() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // As long as Necrotic Ooze is on the battlefield, it has all activated abilities of all creature cards in all graveyards
        addCard(Zone.BATTLEFIELD, playerA, "Necrotic Ooze", 1);
        // {2}{1},{T}: Tap target creature.
        addCard(Zone.GRAVEYARD, playerA, "Akroan Jailer", 1);
        // {T}: Target attacking creature gets +1/+1 until end of turn.
        addCard(Zone.GRAVEYARD, playerB, "Anointer of Champions", 1);

        // Cards in graveyards lose all abilities.
        addCard(Zone.HAND, playerA, "Yixlid Jailer", 1); // Creature - {1}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yixlid Jailer");
        
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Yixlid Jailer", 1);

        Permanent necroticOoze = getPermanent("Necrotic Ooze", playerA);
        int numberOfActivatedAbilities = 0;
        for (Ability ability : necroticOoze.getAbilities(currentGame)) {
            if (ability.getAbilityType() == AbilityType.ACTIVATED) {
                numberOfActivatedAbilities++;
            }
        }
        Assert.assertEquals("All abilities from cards in graveyard are removed - so no abilities for Necrotic Ooze", 0, numberOfActivatedAbilities);
    }

}
