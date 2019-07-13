package org.mage.test.utils;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PlanarDieRoll;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.TwoPlayerDuel;
import mage.game.mulligan.MulliganType;
import mage.player.human.HumanPlayer;
import mage.players.Player;
import mage.util.RandomUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public class RandomTest {

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
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 50);

        int height = 512;
        int weight = 512;
        BufferedImage image = new BufferedImage(weight, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < weight; x++) {
            for (int y = 0; y < height; y++) {
                // roll dice
                int diceVal = player.rollDice(game, 12);
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
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 50);

        int height = 512;
        int weight = 512;
        BufferedImage image = new BufferedImage(weight, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < weight; x++) {
            for (int y = 0; y < height; y++) {
                // roll planar dice
                PlanarDieRoll res = player.rollPlanarDie(game);
                image.setRGB(x, y, new Color(
                        res.equals(PlanarDieRoll.CHAOS_ROLL) ? 255 : 0,
                        res.equals(PlanarDieRoll.PLANAR_ROLL) ? 255 : 0,
                        res.equals(PlanarDieRoll.NIL_ROLL) ? 255 : 0
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
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 50);
        Deck deck = DeckTestUtils.buildRandomDeck("WGUBR", false, "GRN");
        player.getLibrary().addAll(deck.getCards(), game);

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
