
package org.mage.test.cards.enchantments;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class StarfieldOfNyxTest extends CardTestPlayerBase {

    /**
     * I had Starfield of Nyx out. With the upkeep trigger, I brought back a
     * Cloudform, which was the fifth enchantment. I had another Cloudform, and
     * Starfield of Nyx not only turned both of them into creatures (it
     * shouldn't, because they're auras), but it also destroyed them. The
     * manifests stayed on the battlefield without Flying or Hexproof.
     *
     */
    @Test
    public void testCloudform() {
        // At the beginning of your upkeep, if you control an artifact, put a 1/1 colorless Thopter artifact creature token with flying onto the battlefield.
        // Whenever one or more artifact creatures you control deal combat damage to a player, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Thopter Spy Network", 2); // {2}{U}{U}  - added to come to 5 enchantments on the battlefield
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // At the beginning of your upkeep, you may return target enchantment card from your graveyard to the battlefield.
        // As long as you control five or more enchantments, each other non-Aura enchantment you control is a creature in
        // addition to its other types and has base power and base toughness each equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Starfield of Nyx"); // "{4}{W}"
        // When Cloudform enters the battlefield, it becomes an Aura with enchant creature. Manifest the top card of your library and attach Cloudform to it.
        // Enchanted creature has flying and hexproof.
        addCard(Zone.HAND, playerA, "Cloudform"); // {1}{U}{U}
        addCard(Zone.GRAVEYARD, playerA, "Cloudform");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Starfield of Nyx");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudform");

        addTarget(playerA, "Cloudform");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Thopter Spy Network", 0);
        assertPowerToughness(playerA, "", 2, 2, Filter.ComparisonScope.All); // the manifested cards
        assertPermanentCount(playerA, "Starfield of Nyx", 1);
        assertPowerToughness(playerA, "Thopter Spy Network", 4, 4, Filter.ComparisonScope.All);
        assertPermanentCount(playerA, "Cloudform", 2);
    }

    /**
     * Regarding Starfield of Nyx: I tried to bring back a Singing Bell Strike
     * to enchant Silumgar, the Drifting Death. I chose the Aura, but instead of
     * enchanting Silumgar, it stayed in the graveyard. This shouldn't be the
     * case, because Auras only target when they're cast as spells.
     */
    @Test
    public void testHexproof() {
        // At the beginning of your upkeep, if you control an artifact, put a 1/1 colorless Thopter artifact creature token with flying onto the battlefield.
        // Whenever one or more artifact creatures you control deal combat damage to a player, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // At the beginning of your upkeep, you may return target enchantment card from your graveyard to the battlefield.
        // As long as you control five or more enchantments, each other non-Aura enchantment you control is a creature in
        // addition to its other types and has base power and base toughness each equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Starfield of Nyx"); // "{4}{W}"
        // Enchant creature
        // When Singing Bell Strike enters the battlefield, tap enchanted creature.
        // Enchanted creature doesn't untap during its controller's untap step.
        // Enchanted creature has "{6}: Untap this creature."
        addCard(Zone.GRAVEYARD, playerA, "Singing Bell Strike");

        addCard(Zone.BATTLEFIELD, playerB, "Silumgar, the Drifting Death", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Starfield of Nyx");

        addTarget(playerA, "Singing Bell Strike");
        setChoice(playerA, "Silumgar, the Drifting Death");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Starfield of Nyx", 1);
        assertPermanentCount(playerA, "Singing Bell Strike", 1);

        Permanent enchantment = getPermanent("Singing Bell Strike", playerA);
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent enchanted = currentGame.getPermanent(enchantment.getAttachedTo());
            Assert.assertEquals("Silumgar was enchanted", enchanted.getName().equals("Silumgar, the Drifting Death"), true);
        } else {
            Assert.assertEquals("Singing Bell Strike not on the battlefield", false, true);
        }
    }
    
    @Test
    public void testStarfieldOfNyxLayers() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Starfield of Nyx"); // enchantments you control become creatures
        addCard(Zone.BATTLEFIELD, playerA, "Humility"); // creatures lose all abilities and are 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Pharika, God of Affliction"); // enchantment
        addCard(Zone.BATTLEFIELD, playerA, "Emrakul, the Aeons Torn"); //15/15 creature
        addCard(Zone.BATTLEFIELD, playerA, "Crusade", 4); // enchantments to fulfill requirement of Starfield of Nyx
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertPowerToughness(playerA, "Pharika, God of Affliction", 3, 3, Filter.ComparisonScope.All);
        assertPowerToughness(playerA, "Humility", 4, 4, Filter.ComparisonScope.All);
        // Humility loses its ability in layer 6.  Layer 7 never gets Humility's effect
        assertPowerToughness(playerA, "Emrakul, the Aeons Torn", 15, 15, Filter.ComparisonScope.All); // PT not affected
        Permanent emrakul = getPermanent("Emrakul, the Aeons Torn", playerA.getId());
        Assert.assertNotNull(emrakul);
        Assert.assertFalse(emrakul.getAbilities().contains(FlyingAbility.getInstance())); // loses flying though
        
    }
}
