package org.mage.test.utils;

import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.basiclands.Island;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardScanner;
import mage.constants.MultiplayerAttackOption;
import mage.constants.Outcome;
import mage.constants.PlanarDieRollResult;
import mage.constants.RangeOfInfluence;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.TwoPlayerDuel;
import mage.game.mulligan.MulliganType;
import mage.player.human.HumanPlayer;
import mage.players.Player;
import mage.util.RandomUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public class RandomTest {

    @BeforeClass
    public static void setup() {
        CardScanner.scan();
    }

    @Test
    public void test_SeedAndSameResults() {
        RandomUtil.setSeed(123);
        List<Integer> listSameA = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            listSameA.add(RandomUtil.nextInt());
        }

        RandomUtil.setSeed(321);
        List<Integer> listDifferent = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            listDifferent.add(RandomUtil.nextInt());
        }

        RandomUtil.setSeed(123);
        List<Integer> listSameB = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            listSameB.add(RandomUtil.nextInt());
        }

        Assert.assertEquals("same seed must have same random values", listSameA.stream().mapToInt(Integer::intValue).sum(), listSameB.stream().mapToInt(Integer::intValue).sum());
        Assert.assertNotEquals("different seed must have different random values", listSameA.stream().mapToInt(Integer::intValue).sum(), listDifferent.stream().mapToInt(Integer::intValue).sum());
    }

    @Test
    public void test_SeedAndSameRandomDecks() {
        RandomUtil.setSeed(123);
        DeckCardLists listSameA = DeckTestUtils.buildRandomDeckAndInitCards("WGUBR", false, "GRN");
        String infoSameA = listSameA.getCards().stream().map(c -> (c.getSetCode() + "-" + c.getCardName())).collect(Collectors.joining(","));

        RandomUtil.setSeed(321);
        DeckCardLists listDifferent = DeckTestUtils.buildRandomDeckAndInitCards("WGUBR", false, "GRN");
        String infoDifferent = listDifferent.getCards().stream().map(c -> (c.getSetCode() + "-" + c.getCardName())).collect(Collectors.joining(","));

        RandomUtil.setSeed(123);
        DeckCardLists listSameB = DeckTestUtils.buildRandomDeckAndInitCards("WGUBR", false, "GRN");
        String infoSameB = listSameB.getCards().stream().map(c -> (c.getSetCode() + "-" + c.getCardName())).collect(Collectors.joining(","));

        Assert.assertEquals("same seed must have same deck", infoSameA, infoSameB);
        Assert.assertNotEquals("different seed must have different deck", infoSameA, infoDifferent);
    }

    @Test
    public void test_SeededCardPickUsesCollectionOrder() {
        UUID ownerId = UUID.randomUUID();
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 60, 20, 7);
        // Hash order is the reverse of collection order.
        List<Card> cards = Arrays.asList(islandWithHash(ownerId, 1), islandWithHash(ownerId, 0));
        game.loadCards(new LinkedHashSet<>(cards), ownerId);

        RandomUtil.setSeed(123);
        int index = RandomUtil.nextInt(cards.size());
        RandomUtil.setSeed(123);
        Assert.assertSame(cards.get(index), new CardsImpl(cards).getRandom(game));
    }

    private static Card islandWithHash(UUID ownerId, int hash) {
        return new Island(ownerId, new CardSetInfo("Island", "10E", "368", Rarity.LAND)) {
            @Override
            public int hashCode() {
                return hash;
            }
        };
    }

    @Test
    @Ignore
    public void test_GenerateRandomPng() throws IOException {
        String dest = "f:/test/xmage/";
        int height = 512;
        int weight = 512;
        BufferedImage image = new BufferedImage(weight, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < weight; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, RandomUtil.nextBoolean() ? Color.white.getRGB() : Color.black.getRGB());
            }
        }
        ImageIO.write(image, "png", new File(dest + "xmage_random.png"));
    }

    @Test
    @Ignore
    public void test_GenerateRandomDicePng() throws IOException {
        String dest = "f:/test/xmage/";
        //RandomUtil.setSeed(123);
        Player player = new HumanPlayer("random", RangeOfInfluence.ALL, 1);
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 60, 50, 7);

        int height = 512;
        int weight = 512;
        BufferedImage image = new BufferedImage(weight, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < weight; x++) {
            for (int y = 0; y < height; y++) {
                // roll dice
                int diceVal = player.rollDice(Outcome.Neutral, null, game, 12);
                int colorMult = Math.floorDiv(255, 12);

                image.setRGB(x, y, new Color(colorMult * diceVal, colorMult * diceVal, colorMult * diceVal).getRGB());
            }
        }
        ImageIO.write(image, "png", new File(dest + "xmage_random_dice.png"));
    }

    @Test
    @Ignore
    public void test_GenerateRandomPlanarDicePng() throws IOException {
        String dest = "f:/test/xmage/";
        //RandomUtil.setSeed(123);
        Player player = new HumanPlayer("random", RangeOfInfluence.ALL, 1);
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 60, 50, 7);

        int height = 512;
        int weight = 512;
        BufferedImage image = new BufferedImage(weight, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < weight; x++) {
            for (int y = 0; y < height; y++) {
                // roll planar dice
                PlanarDieRollResult res = player.rollPlanarDie(Outcome.Neutral, null, game);
                image.setRGB(x, y, new Color(
                        res.equals(PlanarDieRollResult.CHAOS_ROLL) ? 255 : 0,
                        res.equals(PlanarDieRollResult.PLANAR_ROLL) ? 255 : 0,
                        res.equals(PlanarDieRollResult.BLANK_ROLL) ? 255 : 0
                ).getRGB());
            }
        }
        ImageIO.write(image, "png", new File(dest + "xmage_random_planar_dice.png"));
    }

    @Test
    @Ignore
    public void test_GenerateRandomLibraryShufflePng() throws IOException {
        String dest = "f:/test/xmage/";
        //RandomUtil.setSeed(123);
        Player player = new HumanPlayer("random", RangeOfInfluence.ALL, 1);
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 60, 50, 7);
        Deck deck = DeckTestUtils.buildRandomDeck("WGUBR", false, "GRN");
        player.getLibrary().addAll(deck.getMaindeckCards(), game);

        // multiple cards analyze
        for (int i = 0; i < player.getLibrary().size(); i++) {
            UUID cardId = player.getLibrary().getCardList().get(i);
            //int halfIndex = Math.floorDiv(player.getLibrary().size(), 2);
            int colorMult = Math.floorDiv(255, player.getLibrary().size());

            int height = 512;
            int weight = 512;
            BufferedImage image = new BufferedImage(weight, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < weight; x++) {
                for (int y = 0; y < height; y++) {
                    // shuffle, search card position and draw
                    player.getLibrary().shuffle();
                    int cardPos = player.getLibrary().getCardPosition(cardId);
                    //image.setRGB(x, y, cardPos < halfIndex ? Color.white.getRGB() : Color.black.getRGB());
                    image.setRGB(x, y, new Color(colorMult * cardPos, colorMult * cardPos, colorMult * cardPos).getRGB());
                }
            }
            ImageIO.write(image, "png", new File(dest + "xmage_random_shuffle_" + i + ".png"));
        }
    }
}
