package mage.server.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomUtil;

/**
 * @author nantuko
 */
public final class SystemUtil {

    public static final DateFormat dateFormat = new SimpleDateFormat("yy-M-dd HH:mm:ss");

    private static final String INIT_FILE_PATH = "config" + File.separator + "init.txt";
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SystemUtil.class);

    /**
     * Replaces cards in player's hands by specified in config/init.txt.<br/>
     * <br/>
     * <b>Implementation note:</b><br/>
     * 1. Read init.txt line by line<br/>
     * 2. Parse line using the following format: line ::=
     * <zone>:<nickname>:<card name>:<amount><br/>
     * 3. If zone equals to 'hand', add card to player's library<br/>
     * 3a. Then swap added card with any card in player's hand<br/>
     * 3b. Parse next line (go to 2.), If EOF go to 4.<br/>
     * 4. Log message to all players that cards were added (to prevent unfair
     * play).<br/>
     * 5. Exit<br/>
     *
     * @param game
     */
    public static void addCardsForTesting(Game game) {
        try {
            File f = new File(INIT_FILE_PATH);
            if (!f.exists()) {
                logger.warn("Couldn't find init file: " + INIT_FILE_PATH);
                return;
            }

            logger.info("Parsing init.txt... ");

            try (Scanner scanner = new Scanner(f)) {
                Pattern pattern = Pattern.compile("([a-zA-Z]+):([\\w]+):([a-zA-Z ,\\/\\-.!'\\d:]+?):(\\d+)");
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }

                    Matcher m = pattern.matcher(line);
                    if (!m.matches()) {
                        logger.warn("Init string wasn't parsed: " + line);
                        continue;
                    }

                    String zone = m.group(1);
                    String nickname = m.group(2);

                    Optional<Player> playerOptional = findPlayer(game, nickname);
                    if (!playerOptional.isPresent()) {
                        logger.warn("Was skipped because no player with that name: " + line);
                        continue;
                    }
                    Player player = playerOptional.get();

                    Zone gameZone;
                    if ("hand".equalsIgnoreCase(zone)) {
                        gameZone = Zone.HAND;
                    } else if ("battlefield".equalsIgnoreCase(zone)) {
                        gameZone = Zone.BATTLEFIELD;
                    } else if ("graveyard".equalsIgnoreCase(zone)) {
                        gameZone = Zone.GRAVEYARD;
                    } else if ("library".equalsIgnoreCase(zone)) {
                        gameZone = Zone.LIBRARY;
                    } else if ("token".equalsIgnoreCase(zone)) {
                        gameZone = Zone.BATTLEFIELD;
                    } else if ("emblem".equalsIgnoreCase(zone)) {
                        gameZone = Zone.COMMAND;
                    } else {
                        continue; // go parse next line
                    }

                    String cardName = m.group(3);
                    Integer amount = Integer.parseInt(m.group(4));

                    List<CardInfo> cards = CardRepository.instance.findCards(cardName);
                    if (cards.isEmpty()) {
                        if ("token".equalsIgnoreCase(zone)) {
                            // eg: token:Human:HippoToken:1
                            Class<?> c = Class.forName("mage.game.permanent.token." + cardName);
                            Constructor<?> cons = c.getConstructor();
                            Object token = cons.newInstance();
                            if (token != null && token instanceof mage.game.permanent.token.Token) {
                                ((mage.game.permanent.token.Token) token).putOntoBattlefield(amount, game, null, player.getId(), false, false);
                                continue;
                            }
                        } else if ("emblem".equalsIgnoreCase(zone)) {
                            // eg: emblem:Human:ElspethSunsChampionEmblem:1
                            Class<?> c = Class.forName("mage.game.command.emblems." + cardName);
                            Constructor<?> cons = c.getConstructor();
                            Object emblem = cons.newInstance();
                            if (emblem != null && emblem instanceof mage.game.command.Emblem) {
                                ((mage.game.command.Emblem) emblem).setControllerId(player.getId());
                                game.addEmblem((mage.game.command.Emblem) emblem, null, player.getId());
                                continue;
                            }
                        }
                        logger.warn("Couldn't find a card: " + cardName);
                        continue;
                    }

                    Set<Card> cardsToLoad = new HashSet<>();
                    for (int i = 0; i < amount; i++) {
                        CardInfo cardInfo = cards.get(RandomUtil.nextInt(cards.size()));
                        Card card = cardInfo != null ? cardInfo.getCard() : null;
                        if (card != null) {
                            cardsToLoad.add(card);
                        }
                    }
                    game.loadCards(cardsToLoad, player.getId());
                    for (Card card : cardsToLoad) {
                        swapWithAnyCard(game, player, card, gameZone);
                    }
                }
            }
        } catch (Exception e) {
            logger.fatal("", e);
        }
    }

    /**
     * Swap cards between specified card from library and any hand card.
     *
     * @param game
     * @param card Card to put to player's hand
     */
    private static void swapWithAnyCard(Game game, Player player, Card card, Zone zone) {
        // Put the card in Exile to start. Otherwise the game doesn't know where to remove the card from.
        game.getExile().getPermanentExile().add(card);
        game.setZone(card.getId(), Zone.EXILED);
        switch (zone) {
            case BATTLEFIELD:
                card.putOntoBattlefield(game, Zone.EXILED, null, player.getId());
                break;
            case LIBRARY:
                card.setZone(Zone.LIBRARY, game);
                game.getExile().getPermanentExile().remove(card);
                player.getLibrary().putOnTop(card, game);
                break;
            default:
                card.moveToZone(zone, null, game, false);
        }
        logger.info("Added card to player's " + zone.toString() + ": " + card.getName() + ", player = " + player.getName());
    }

    /**
     * Find player by name.
     *
     * @param game
     * @param name
     * @return
     */
    private static Optional<Player> findPlayer(Game game, String name) {
        for (Player player : game.getPlayers().values()) {
            if (player.getName().equals(name)) {
                return Optional.of(player);
            }
        }
        return Optional.empty();
    }

    public static String sanitize(String input) {
        //Pattern pattern = Pattern.compile("[^0-9a-zA-Z]");
        //Matcher matcher = pattern.matcher(input);
        //return matcher.replaceAll("");
        return input.replaceAll("[^a-zA-Z0-9]", "");
    }

    /**
     * Get a diff between two dates
     *
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
