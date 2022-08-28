package org.mage.test.serverside;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.view.CardView;
import mage.view.GameView;
import mage.view.PlayerView;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * GUI tests: card icons for cards
 *
 * @author JayDi85
 */
public class CardIconsTest extends CardTestPlayerBase {

    @Test
    public void test_CostX_Spells() {
        // Chalice of the Void enters the battlefield with X charge counters on it.
        // Whenever a player casts a spell with converted mana cost equal to the number of charge counters on Chalice of the Void, counter that spell.
        addCard(Zone.HAND, playerA, "Chalice of the Void", 1); // {X}{X}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        // hand (not visible)
        runCode("card icons in hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            Assert.assertEquals("must have 1 card in hand", 1, gameView.getHand().values().size());
            CardView cardView = gameView.getHand().values().stream().findFirst().get();
            Assert.assertEquals("must have non x cost card icons in hand", 0, cardView.getCardIcons().size());
        });

        // cast and put on stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chalice of the Void");
        setChoice(playerA, "X=2");

        // stack (visible)
        runCode("card icons on stack (spell)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            Assert.assertEquals("must have 1 card in stack", 1, gameView.getStack().values().size());
            CardView cardView = gameView.getStack().values().stream().findFirst().get();
            Assert.assertEquals("must have x cost card icons in stack", 1, cardView.getCardIcons().size());
            Assert.assertEquals("x cost text", "x=2", cardView.getCardIcons().get(0).getText());
        });

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chalice of the Void", 1);

        // battlefield (card, not visible)
        runCode("card icons in battlefield (card)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            PlayerView playerView = gameView.getPlayers().get(0);
            Assert.assertEquals("player", player.getName(), playerView.getName());
            CardView cardView = playerView.getBattlefield().values().stream().filter(p -> p.getName().equals("Chalice of the Void")).findFirst().orElse(null);
            Assert.assertNotNull("must have 1 chalice in battlefield", cardView);
            Assert.assertEquals("must have x cost card icons in battlefield (card)", 1, cardView.getCardIcons().size());
            Assert.assertEquals("x cost text", "x=2", cardView.getCardIcons().get(0).getText());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_CostX_Copies() {
        // Grenzo, Dungeon Warden enters the battlefield with X +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Grenzo, Dungeon Warden", 1);// {X}{B}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        // Copy target creature spell you control, except it isn't legendary if the spell is legendary.
        addCard(Zone.HAND, playerA, "Double Major", 1); // {G}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // cast and put on stack
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 1);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grenzo, Dungeon Warden");
        setChoice(playerA, "X=2");

        // prepare copy of spell
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 1);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major", "Grenzo, Dungeon Warden", "Grenzo, Dungeon Warden");
        checkStackSize("before copy spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        checkStackSize("after copy spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);

        // stack (copied spell)
        runCode("card icons on stack (copied spell)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            Assert.assertEquals("must have 2 cards in stack", 2, gameView.getStack().values().size());
            CardView originalCardView = gameView.getStack().values()
                    .stream()
                    .filter(c -> !c.getOriginalCard().isCopy())
                    .findFirst()
                    .get();
            CardView copiedCardView = gameView.getStack().values()
                    .stream()
                    .filter(c -> c.getOriginalCard().isCopy())
                    .findFirst()
                    .get();
            Assert.assertNotNull("stack must have original spell", originalCardView);
            Assert.assertNotNull("stack must have copied spell", copiedCardView);
            Assert.assertNotEquals("must find two spells on stack", originalCardView.getId(), copiedCardView.getId());
            Assert.assertEquals("original spell must have x cost card icons", 1, originalCardView.getCardIcons().size());
            Assert.assertEquals("copied spell must have x cost card icons", 1, copiedCardView.getCardIcons().size());
            Assert.assertEquals("original x cost text", "x=2", originalCardView.getCardIcons().get(0).getText());
            Assert.assertEquals("copied x cost text", "x=2", copiedCardView.getCardIcons().get(0).getText());
        });

        // must resolve copied creature spell as a token
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grenzo, Dungeon Warden", 2);

        // battlefield (card and copied card as token)
        runCode("card icons in battlefield (copied)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            PlayerView playerView = gameView.getPlayers().get(0);
            Assert.assertEquals("player", player.getName(), playerView.getName());
            // copied spell goes as token to battlefield, not copied card - so must check isToken
            // original
            CardView originalCardView = playerView.getBattlefield().values()
                    .stream()
                    .filter(p -> p.getName().equals("Grenzo, Dungeon Warden"))
                    .filter(p -> !p.isToken())
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull("original card must be in battlefield", originalCardView);
            Assert.assertEquals("original must have x cost card icons", 1, originalCardView.getCardIcons().size());
            Assert.assertEquals("original x cost text", "x=2", originalCardView.getCardIcons().get(0).getText());
            //
            CardView copiedCardView = playerView.getBattlefield().values()
                    .stream()
                    .filter(p -> p.getName().equals("Grenzo, Dungeon Warden"))
                    .filter(p -> p.isToken())
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull("copied card must be in battlefield", copiedCardView);
            Assert.assertEquals("copied must have x cost card icons", 1, copiedCardView.getCardIcons().size());
            Assert.assertEquals("copied x cost text", "x=0", copiedCardView.getCardIcons().get(0).getText());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_CostX_Abilities() {
        // X icon must be visible only for activated ability, not spell cast

        // {X}{R}, {tap}, Sacrifice Cinder Elemental: Cinder Elemental deals X damage to any target.
        addCard(Zone.HAND, playerA, "Cinder Elemental", 1); // {3}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // hand (not visible)
        runCode("card icons in hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            Assert.assertEquals("must have 1 card in hand", 1, gameView.getHand().values().size());
            CardView cardView = gameView.getHand().values().stream().findFirst().get();
            Assert.assertEquals("must have non x cost card icons in hand", 0, cardView.getCardIcons().size());
        });

        // spell cast
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cinder Elemental");

        // stack (spell cast - not visible)
        runCode("card icons on stack (spell cast - not visible)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            Assert.assertEquals("must have 1 card in stack", 1, gameView.getStack().values().size());
            CardView cardView = gameView.getStack().values().stream().findFirst().get();
            Assert.assertEquals("must have not x cost card icons in stack", 0, cardView.getCardIcons().size());
        });

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cinder Elemental", 1);

        // battlefield (card, not visible)
        runCode("card icons in battlefield (card)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            PlayerView playerView = gameView.getPlayers().get(0);
            Assert.assertEquals("player", player.getName(), playerView.getName());
            CardView cardView = playerView.getBattlefield().values().stream().filter(p -> p.getName().equals("Cinder Elemental")).findFirst().orElse(null);
            Assert.assertNotNull("must have Cinder Elemental in battlefield", cardView);
            Assert.assertEquals("must have not x cost card icons in battlefield (card)", 0, cardView.getCardIcons().size());
        });

        // ACTIVATE ABILITY (x must be visible in stack, but not visible after resolve)
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{R}");
        setChoice(playerA, "X=2");
        addTarget(playerA, playerB);

        // stack (ability activated - visible)
        runCode("card icons on stack (ability activated - visible)", 3, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            Assert.assertEquals("ability activated - must have 1 card in stack", 1, gameView.getStack().values().size());
            CardView cardView = gameView.getStack().values().stream().findFirst().get();
            Assert.assertEquals("ability activated - must have x cost card icons in stack", 1, cardView.getCardIcons().size());
        });

        // battlefield (ability activated, not visible)
        runCode("card icons in battlefield (ability activated)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            PlayerView playerView = gameView.getPlayers().get(0);
            Assert.assertEquals("player", player.getName(), playerView.getName());
            CardView cardView = playerView.getBattlefield().values().stream().filter(p -> p.getName().equals("Cinder Elemental")).findFirst().orElse(null);
            Assert.assertNotNull("ability activated - must have Cinder Elemental in battlefield", cardView);
            Assert.assertEquals("ability activated - must have not x cost card icons in battlefield", 0, cardView.getCardIcons().size());
        });

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_CostX_MDFC() {
        // Agadeem's Awakening
        // Sorcery {X}{B}{B}{B}
        // Return from your graveyard to the battlefield any number of target creature cards that each have a different converted mana cost X or less.
        //
        // Agadeem, the Undercrypt
        // Land
        // As Agadeem, the Undercrypt enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        addCard(Zone.HAND, playerA, "Agadeem's Awakening", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        // hand (not visible)
        runCode("card icons in hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            CardView cardView = gameView.getHand().values().stream()
                    .filter(c -> c.getName().equals("Agadeem's Awakening"))
                    .findFirst()
                    .orElse(null);
            Assert.assertEquals("main must have non x cost card icons in hand", 0, cardView.getCardIcons().size());
            Assert.assertEquals("right must have non x cost card icons in hand", 0, cardView.getSecondCardFace().getCardIcons().size());
        });

        // play spell and check X
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Agadeem's Awakening");
        setChoice(playerA, "X=2");
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        // stack (spell - visible)
        runCode("card icons on stack (visible)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            Assert.assertEquals("must have 1 card in stack", 1, gameView.getStack().values().size());
            CardView cardView = gameView.getStack().values().stream()
                    .filter(c -> c.getName().equals("Agadeem's Awakening"))
                    .findFirst()
                    .orElse(null);
            Assert.assertEquals("main must have x cost card icons in stack", 1, cardView.getCardIcons().size());
            Assert.assertNull("right must be null in stack", cardView.getSecondCardFace());
        });
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // play land and check X
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Agadeem, the Undercrypt");
        setChoice(playerA, false); // not pay life
        runCode("card icons in battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            PlayerView playerView = gameView.getPlayers().get(0);
            Assert.assertEquals("player", player.getName(), playerView.getName());
            CardView cardView = playerView.getBattlefield().values().stream().filter(p -> p.getName().equals("Agadeem, the Undercrypt")).findFirst().orElse(null);
            Assert.assertNotNull("must have Agadeem, the Undercrypt in battlefield", cardView);
            Assert.assertEquals("main must have not x cost card icons in battlefield", 0, cardView.getCardIcons().size());
            Assert.assertNull("second side must be null", cardView.getSecondCardFace());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
