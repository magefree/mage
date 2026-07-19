package org.mage.test.serverside;

import mage.abilities.hint.HintUtils;
import mage.abilities.icon.CardIconType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.view.CardView;
import mage.view.GameView;
import mage.view.PlayerView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
            Assertions.assertEquals(1, gameView.getMyHand().values().size(), "must have 1 card in hand");
            CardView cardView = gameView.getMyHand().values().stream().findFirst().get();
            Assertions.assertEquals(0, cardView.getCardIcons().size(), "must have non x cost card icons in hand");
        });

        // cast and put on stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chalice of the Void");
        setChoice(playerA, "X=2");

        // stack (visible)
        runCode("card icons on stack (spell)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            Assertions.assertEquals(1, gameView.getStack().values().size(), "must have 1 card in stack");
            CardView cardView = gameView.getStack().values().stream().findFirst().get();
            Assertions.assertEquals(1, cardView.getCardIcons().size(), "must have x cost card icons in stack");
            Assertions.assertEquals("x=2", cardView.getCardIcons().get(0).getText(), "x cost text");
        });

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chalice of the Void", 1);

        // battlefield (card, not visible)
        runCode("card icons in battlefield (card)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            PlayerView playerView = gameView.getPlayers().get(0);
            Assertions.assertEquals(player.getName(), playerView.getName(), "player");
            CardView cardView = playerView.getBattlefield().values().stream().filter(p -> p.getName().equals("Chalice of the Void")).findFirst().orElse(null);
            Assertions.assertNotNull(cardView, "must have 1 chalice in battlefield");
            Assertions.assertEquals(1, cardView.getCardIcons().size(), "must have x cost card icons in battlefield (card)");
            Assertions.assertEquals("x=2", cardView.getCardIcons().get(0).getText(), "x cost text");
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_CostX_StackCopy() {
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
            Assertions.assertEquals(2, gameView.getStack().values().size(), "must have 2 cards in stack");
            CardView originalCardView = gameView.getStack().values()
                    .stream()
                    .filter(c -> !c.isOriginalACopy())
                    .findFirst()
                    .get();
            CardView copiedCardView = gameView.getStack().values()
                    .stream()
                    .filter(c -> c.isOriginalACopy())
                    .findFirst()
                    .get();
            Assertions.assertNotNull(originalCardView, "stack must have original spell");
            Assertions.assertNotNull(copiedCardView, "stack must have copied spell");
            Assertions.assertNotEquals(originalCardView.getId(), copiedCardView.getId(), "must find two spells on stack");
            Assertions.assertEquals(1, originalCardView.getCardIcons().size(), "original spell must have x cost card icons");
            Assertions.assertEquals(1, copiedCardView.getCardIcons().size(), "copied spell must have x cost card icons");
            Assertions.assertEquals("x=2", originalCardView.getCardIcons().get(0).getText(), "original x cost text");
            Assertions.assertEquals("x=2", copiedCardView.getCardIcons().get(0).getText(), "copied x cost text");
        });

        // must resolve copied creature spell as a token
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grenzo, Dungeon Warden", 2);

        // battlefield (card and copied card as token)
        runCode("card icons in battlefield (copied)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            PlayerView playerView = gameView.getPlayers().get(0);
            Assertions.assertEquals(player.getName(), playerView.getName(), "player");
            // copied spell goes as token to battlefield, not copied card - so must check isToken
            // original
            CardView originalCardView = playerView.getBattlefield().values()
                    .stream()
                    .filter(p -> p.getName().equals("Grenzo, Dungeon Warden"))
                    .filter(p -> !p.isToken())
                    .findFirst()
                    .orElse(null);
            Assertions.assertNotNull(originalCardView, "original card must be in battlefield");
            Assertions.assertEquals(1, originalCardView.getCardIcons().size(), "original must have x cost card icons");
            Assertions.assertEquals("x=2", originalCardView.getCardIcons().get(0).getText(), "original x cost text");
            //
            CardView copiedCardView = playerView.getBattlefield().values()
                    .stream()
                    .filter(p -> p.getName().equals("Grenzo, Dungeon Warden"))
                    .filter(p -> p.isToken())
                    .findFirst()
                    .orElse(null);
            Assertions.assertNotNull(copiedCardView, "copied card must be in battlefield");
            Assertions.assertEquals(1, copiedCardView.getCardIcons().size(), "copied must have x cost card icons");
            Assertions.assertEquals("x=2", copiedCardView.getCardIcons().get(0).getText(), "copied x cost text");
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_CostX_TokenCopy() {
        //Legend Rule doesn't apply
        addCard(Zone.BATTLEFIELD, playerA, "Mirror Gallery", 1);
        // Grenzo, Dungeon Warden enters the battlefield with X +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Grenzo, Dungeon Warden", 1);// {X}{B}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // Create a token that's a copy of target creature you control.
        // should not copy the X value of the Grenzo
        addCard(Zone.HAND, playerA, "Quasiduplicate", 1); // {1}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // cast Grenzo
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 1);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grenzo, Dungeon Warden");
        setChoice(playerA, "X=2");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // cast Quasiduplicate
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Quasiduplicate", "Grenzo, Dungeon Warden");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grenzo, Dungeon Warden", 2);

        // battlefield (card and copied card as token)
        runCode("card icons in battlefield (cloned)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            PlayerView playerView = gameView.getPlayers().get(0);
            Assertions.assertEquals(player.getName(), playerView.getName(), "player");
            // original
            CardView originalCardView = playerView.getBattlefield().values()
                    .stream()
                    .filter(p -> p.getName().equals("Grenzo, Dungeon Warden"))
                    .filter(p -> !p.isToken())
                    .findFirst()
                    .orElse(null);
            Assertions.assertNotNull(originalCardView, "original card must be in battlefield");
            Assertions.assertEquals(1, originalCardView.getCardIcons().size(), "original must have x cost card icons");
            Assertions.assertEquals("x=2", originalCardView.getCardIcons().get(0).getText(), "original x cost text");
            //
            CardView copiedCardView = playerView.getBattlefield().values()
                    .stream()
                    .filter(p -> p.getName().equals("Grenzo, Dungeon Warden"))
                    .filter(p -> p.isToken())
                    .findFirst()
                    .orElse(null);
            Assertions.assertNotNull(copiedCardView, "copied card must be in battlefield");
            Assertions.assertEquals(1, copiedCardView.getCardIcons().size(), "copied must have x cost card icons");
            Assertions.assertEquals("x=0", copiedCardView.getCardIcons().get(0).getText(), "copied x cost text");
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
            Assertions.assertEquals(1, gameView.getMyHand().values().size(), "must have 1 card in hand");
            CardView cardView = gameView.getMyHand().values().stream().findFirst().get();
            Assertions.assertEquals(0, cardView.getCardIcons().size(), "must have non x cost card icons in hand");
        });

        // spell cast
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cinder Elemental");

        // stack (spell cast - not visible)
        runCode("card icons on stack (spell cast - not visible)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            Assertions.assertEquals(1, gameView.getStack().values().size(), "must have 1 card in stack");
            CardView cardView = gameView.getStack().values().stream().findFirst().get();
            Assertions.assertEquals(0, cardView.getCardIcons().size(), "must have not x cost card icons in stack");
        });

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cinder Elemental", 1);

        // battlefield (card, not visible)
        runCode("card icons in battlefield (card)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            PlayerView playerView = gameView.getPlayers().get(0);
            Assertions.assertEquals(player.getName(), playerView.getName(), "player");
            CardView cardView = playerView.getBattlefield().values().stream().filter(p -> p.getName().equals("Cinder Elemental")).findFirst().orElse(null);
            Assertions.assertNotNull(cardView, "must have Cinder Elemental in battlefield");
            Assertions.assertEquals(0, cardView.getCardIcons().size(), "must have not x cost card icons in battlefield (card)");
        });

        // ACTIVATE ABILITY (x must be visible in stack, but not visible after resolve)
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{R}");
        setChoice(playerA, "X=2");
        addTarget(playerA, playerB);

        // stack (ability activated - visible)
        runCode("card icons on stack (ability activated - visible)", 3, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            Assertions.assertEquals(1, gameView.getStack().values().size(), "ability activated - must have 1 card in stack");
            CardView cardView = gameView.getStack().values().stream().findFirst().get();
            Assertions.assertTrue(cardView.getCardIcons().stream().anyMatch(x -> x.getText().equals("x=2")), "ability activated - must have x cost card icons in stack");
        });

        // battlefield (ability activated, not visible)
        runCode("card icons in battlefield (ability activated)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            PlayerView playerView = gameView.getPlayers().get(0);
            Assertions.assertEquals(player.getName(), playerView.getName(), "player");
            CardView cardView = playerView.getBattlefield().values().stream().filter(p -> p.getName().equals("Cinder Elemental")).findFirst().orElse(null);
            Assertions.assertNotNull(cardView, "ability activated - must have Cinder Elemental in battlefield");
            Assertions.assertEquals(0, cardView.getCardIcons().size(), "ability activated - must have not x cost card icons in battlefield");
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
        // As Agadeem, the Undercrypt enters the battlefield, you may pay 3 life. If you don't, it enters tapped.
        addCard(Zone.HAND, playerA, "Agadeem's Awakening", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        // hand (not visible)
        runCode("card icons in hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            CardView cardView = gameView.getMyHand().values().stream()
                    .filter(c -> c.getName().equals("Agadeem's Awakening"))
                    .findFirst()
                    .orElse(null);
            Assertions.assertEquals(0, cardView.getCardIcons().size(), "main must have non x cost card icons in hand");
            Assertions.assertEquals(0, cardView.getSecondCardFace().getCardIcons().size(), "right must have non x cost card icons in hand");
        });

        // play spell and check X
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Agadeem's Awakening");
        setChoice(playerA, "X=2");
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        // stack (spell - visible)
        runCode("card icons on stack (visible)", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            Assertions.assertEquals(1, gameView.getStack().values().size(), "must have 1 card in stack");
            CardView cardView = gameView.getStack().values().stream()
                    .filter(c -> c.getName().equals("Agadeem's Awakening"))
                    .findFirst()
                    .orElse(null);
            Assertions.assertEquals(1, cardView.getCardIcons().size(), "main must have x cost card icons in stack");
            Assertions.assertNull(cardView.getSecondCardFace(), "right must be null in stack");
        });
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // play land and check X
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Agadeem, the Undercrypt");
        setChoice(playerA, false); // not pay life
        runCode("card icons in battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            PlayerView playerView = gameView.getPlayers().get(0);
            Assertions.assertEquals(player.getName(), playerView.getName(), "player");
            CardView cardView = playerView.getBattlefield().values().stream().filter(p -> p.getName().equals("Agadeem, the Undercrypt")).findFirst().orElse(null);
            Assertions.assertNotNull(cardView, "must have Agadeem, the Undercrypt in battlefield");
            Assertions.assertEquals(0, cardView.getCardIcons().size(), "main must have not x cost card icons in battlefield");
            Assertions.assertNull(cardView.getSecondCardFace(), "second side must be null");
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_RestrictionsIcon() {
        // Felhide Brawler can't block unless you control another Minotaur.
        addCard(Zone.BATTLEFIELD, playerA, "Felhide Brawler", 1);
        //
        addCard(Zone.HAND, playerA, "Felhide Brawler", 1); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // active restriction
        runCode("has restrictions", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            PlayerView playerView = gameView.getPlayers().get(0);
            Assertions.assertEquals(player.getName(), playerView.getName(), "player");
            CardView cardView = playerView.getBattlefield().values().stream().filter(p -> p.getName().equals("Felhide Brawler")).findFirst().orElse(null);
            Assertions.assertNotNull(cardView, "must have 1 creature in battlefield");
            Assertions.assertTrue(cardView.getRules().stream().anyMatch(s -> s.startsWith(HintUtils.HINT_ICON_RESTRICT)), "creature must have restriction hint");
            Assertions.assertTrue(cardView.getCardIcons().stream().anyMatch(icon -> icon.getIconType().equals(CardIconType.OTHER_HAS_RESTRICTIONS)), "creature must have restriction icon");
        });

        // cast another creature and disable restriction
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Felhide Brawler");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("no restrictions", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            PlayerView playerView = gameView.getPlayers().get(0);
            Assertions.assertEquals(player.getName(), playerView.getName(), "player");
            Assertions.assertEquals(2, playerView.getBattlefield().values()
                    .stream()
                    .filter(p -> p.getName().equals("Felhide Brawler"))
                    .count(), "must have 2 creature in battlefield"
            );
            CardView cardView = playerView.getBattlefield().values().stream().filter(p -> p.getName().equals("Felhide Brawler")).findFirst().orElse(null);
            Assertions.assertNotNull(cardView, "can't find creature");
            Assertions.assertFalse(cardView.getRules().stream().anyMatch(s -> s.startsWith(HintUtils.HINT_ICON_RESTRICT)), "creature must not have restriction hint");
            Assertions.assertFalse(cardView.getCardIcons().stream().anyMatch(icon -> icon.getIconType().equals(CardIconType.OTHER_HAS_RESTRICTIONS)), "creature must not have restriction icon");
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
