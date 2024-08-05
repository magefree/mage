package org.mage.test.cards.single.otc;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DuneChanterTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DuneChanter Dune Chanter} {2}{G}
     * Creature — Plant Druid
     * Reach
     * Lands you control and land cards you own that aren’t on the battlefield are Deserts in addition to their other types.
     * Lands you control have “{T}: Add one mana of any color.”
     * {T}: Mill two cards. You gain 1 life for each land card milled this way.
     * 2/3
     */
    private static final String chanter = "Dune Chanter";

    private static void checkBattlefield(String info, Player player, Game game, int count) {
        int amount = game
                .getBattlefield()
                .getAllActivePermanents(player.getId())
                .stream()
                .filter(p -> p.getSubtype(game).contains(SubType.DESERT))
                .mapToInt(k -> 1)
                .sum();
        Assert.assertEquals(info, count, amount);
    }

    private static void checkTopLibrary(String info, Player player, Game game, boolean check) {
        boolean hasDesert = player.getLibrary().getFromTop(game).getSubtype(game).contains(SubType.DESERT);
        Assert.assertEquals(info, check, hasDesert);
    }

    private static void checkHand(String info, Player player, Game game, int count) {
        int amount = player
                .getHand()
                .getCards(game)
                .stream()
                .filter(c -> c.getSubtype(game).contains(SubType.DESERT))
                .mapToInt(k -> 1)
                .sum();
        Assert.assertEquals(info, count, amount);
    }

    private static void checkGraveyard(String info, Player player, Game game, int count) {
        int amount = player
                .getGraveyard()
                .getCards(game)
                .stream()
                .filter(c -> c.getSubtype(game).contains(SubType.DESERT))
                .mapToInt(k -> 1)
                .sum();
        Assert.assertEquals(info, count, amount);
    }

    private static void checkExile(String info, Player player, Game game, int count) {
        int amount = game
                .getExile()
                .getAllCards(game, player.getId())
                .stream()
                .filter(c -> c.getSubtype(game).contains(SubType.DESERT))
                .mapToInt(k -> 1)
                .sum();
        Assert.assertEquals(info, count, amount);
    }

    @Test
    public void test_Deserts_All_Zones() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, chanter);
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 3);
        addCard(Zone.HAND, playerA, "Tropical Island");
        addCard(Zone.GRAVEYARD, playerA, "Volcanic Island");
        addCard(Zone.EXILED, playerA, "Scrubland");
        addCard(Zone.LIBRARY, playerA, "Badlands");

        runCode("Check battlefield before", 1, PhaseStep.UPKEEP, playerA, (i, p, g) -> DuneChanterTest.checkBattlefield(i, p, g, 0));
        runCode("Check hand before       ", 1, PhaseStep.UPKEEP, playerA, (i, p, g) -> DuneChanterTest.checkHand(i, p, g, 0));
        runCode("Check library before    ", 1, PhaseStep.UPKEEP, playerA, (i, p, g) -> DuneChanterTest.checkTopLibrary(i, p, g, false));
        runCode("Check graveyard before  ", 1, PhaseStep.UPKEEP, playerA, (i, p, g) -> DuneChanterTest.checkGraveyard(i, p, g, 0));
        runCode("Check exile before      ", 1, PhaseStep.UPKEEP, playerA, (i, p, g) -> DuneChanterTest.checkExile(i, p, g, 0));

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, chanter, true);

        runCode("Check battlefield after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (i, p, g) -> DuneChanterTest.checkBattlefield(i, p, g, 3));
        runCode("Check hand after       ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (i, p, g) -> DuneChanterTest.checkHand(i, p, g, 1));
        runCode("Check library after    ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (i, p, g) -> DuneChanterTest.checkTopLibrary(i, p, g, true));
        runCode("Check graveyard after  ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (i, p, g) -> DuneChanterTest.checkGraveyard(i, p, g, 1));
        runCode("Check exile after      ", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (i, p, g) -> DuneChanterTest.checkExile(i, p, g, 1));

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, chanter, 1);
    }
}
