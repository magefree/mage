package org.mage.test.cards.single.thb;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */

public class OneWithTheStarsTest extends CardTestPlayerBase {

    private static final String stars = "One with the Stars";
    private static final String knight = "Dragonsoul Knight";
    private static final String brute = "Gingerbrute";
    private static final String shrine = "Honden of Cleansing Fire";
    private static final String blossom = "Bitterblossom";

    private void makeRainbowLand(int count) {
        for (int i = 0; i < count; i++) {
            addCustomCardWithAbility(
                    "Rainbow", playerA, new AnyColorManaAbility(),
                    null, CardType.LAND, "", Zone.BATTLEFIELD
            );
        }
    }

    @Test
    public void testDragonsoulKnight() {
        addCard(Zone.HAND, playerA, stars);
        addCard(Zone.BATTLEFIELD, playerA, knight);
        makeRainbowLand(9);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, stars, knight);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{W}{U}");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertType(knight, CardType.ENCHANTMENT, true);
        assertType(knight, CardType.CREATURE, false);
        assertNotSubtype(knight, SubType.HUMAN);
        assertNotSubtype(knight, SubType.KNIGHT);
        assertNotSubtype(knight, SubType.DRAGON);
        assertAbility(playerA, knight, FlyingAbility.getInstance(), true);
        assertAbility(playerA, knight, TrampleAbility.getInstance(), true);
    }

    @Test
    public void testGingerbrute() {
        addCard(Zone.HAND, playerA, stars);
        addCard(Zone.BATTLEFIELD, playerA, brute);
        makeRainbowLand(4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, stars, brute);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertType(brute, CardType.ENCHANTMENT, true);
        assertType(brute, CardType.ARTIFACT, false);
        assertType(brute, CardType.CREATURE, false);
        assertNotSubtype(brute, SubType.GOLEM);
        assertNotSubtype(brute, SubType.FOOD);
    }

    @Test
    public void testShrine() {
        addCard(Zone.HAND, playerA, stars);
        addCard(Zone.BATTLEFIELD, playerA, shrine);
        makeRainbowLand(4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, stars, shrine);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertType(shrine, CardType.ENCHANTMENT, SubType.SHRINE);
    }

    @Test
    public void testBitterblossom() {
        addCard(Zone.HAND, playerA, stars);
        addCard(Zone.BATTLEFIELD, playerA, blossom);
        makeRainbowLand(4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, stars, blossom);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertType(blossom, CardType.ENCHANTMENT, true);
        assertType(blossom, CardType.TRIBAL, false);
        assertNotSubtype(blossom, SubType.FAERIE);
    }
}
