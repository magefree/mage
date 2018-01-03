package mage.server.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomUtil;

/**
 * @author JayDi85
 */
public final class SystemUtil {

    public static final DateFormat dateFormat = new SimpleDateFormat("yy-M-dd HH:mm:ss");

    private static final String INIT_FILE_PATH = "config" + File.separator + "init.txt";
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SystemUtil.class);

    private static final String COMMAND_MANA_ADD = "@mana add";
    private static final String COMMAND_LANDS_ADD = "@lands add";
    private static final String COMMAND_RUN_CUSTOM_CODE = "@run custom code";
    private static final String COMMAND_CLEAR_BATTLEFIELD = "@clear battlefield";
    private static final Map<String, String> supportedCommands = new HashMap<>();
    static {
        supportedCommands.put(COMMAND_MANA_ADD, "MANA ADD");
        supportedCommands.put(COMMAND_LANDS_ADD, "LANDS ADD");
        supportedCommands.put(COMMAND_RUN_CUSTOM_CODE, "RUN CUSTOM CODE");
        supportedCommands.put(COMMAND_CLEAR_BATTLEFIELD, "CLAR BATTLEFIELD");
    }

    private static class CommandGroup{
        String name;
        boolean isSpecialCommand;
        ArrayList<String> commands = new ArrayList<>();

        public CommandGroup(String name){
            this(name, false);
        }

        public CommandGroup(String name, boolean isSpecialCommand){
            this.name = name;
            this.isSpecialCommand = isSpecialCommand;
        }

        public String getPrintName(){
            if(this.isSpecialCommand && supportedCommands.containsKey(this.name)){
                return supportedCommands.get(this.name);
            } else {
                return this.name;
            }
        }

        public String getPrintNameWithStats(){
            String res = this.getPrintName();
            if(!this.isSpecialCommand){
                res = res + " (" + this.commands.size() + " commands)";
            }

            return res;
        }
    }

    public static void addCardsForTesting(Game game) {
        addCardsForTesting(game, null, null);
    }

    /**
     * Replaces cards in player's hands by specified in config/init.txt.<br/>
     * <br/>
     * <b>Implementation note:</b><br/>
     * 1. Read init.txt line by line<br/>
     * 2. Parse line using for searching groups like: [group 1]
     * 3. Parse line using the following format: line ::=
     * <zone>:<nickname>:<card name>:<amount><br/>
     * 4. If zone equals to 'hand', add card to player's library<br/>
     * 5a. Then swap added card with any card in player's hand<br/>
     * 5b. Parse next line (go to 2.), If EOF go to 4.<br/>
     * 6. Log message to all players that cards were added (to prevent unfair
     * play).<br/>
     * 7. Exit<br/>
     *
     * @param game
     */
    public static void addCardsForTesting(Game game, String fileSource, Player feedbackPlayer) {

        try {
            String fileName = fileSource;
            if(fileName == null){
                fileName = INIT_FILE_PATH;
            }

            File f = new File(fileName);
            if (!f.exists()) {
                logger.warn("Couldn't find init file: " + fileName);
                return;
            }

            logger.info("Parsing init file... ");

            // steps:
            // 1. parse groups and commands
            // 2. ask user if many groups
            // 3. run commands from selected group

            // 1. parse
            Pattern patternGroup = Pattern.compile("\\[(.+)\\]"); // [test new card]
            Pattern patternCard = Pattern.compile("([a-zA-Z]+):([\\w]+):([a-zA-Z ,\\/\\-.!'\\d:]+?):(\\d+)"); // battlefield:Human:Island:10
            ArrayList<CommandGroup> groups = new ArrayList<>();

            try (Scanner scanner = new Scanner(f)) {

                CommandGroup currentGroup = null;

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();

                    // skip comments
                    if (line.isEmpty() || line.startsWith("#") || line.startsWith("//")) {
                        continue;
                    }

                    // group
                    Matcher matchGroup = patternGroup.matcher(line);
                    if (matchGroup.matches()) {
                        String groupName = matchGroup.group(1);
                        if(groupName.startsWith("@")){
                            // special command group
                            if(supportedCommands.containsKey(groupName)){
                                groups.add(new CommandGroup(groupName, true));
                            }else {
                                logger.warn("Special group [" + groupName + "] is not supported.");
                            }
                            continue;
                        } else {
                            // basic group
                            currentGroup = new CommandGroup(groupName);
                            groups.add(currentGroup);
                            continue;
                        }
                    }

                    // command
                    if (currentGroup == null) {
                        currentGroup = new CommandGroup("default group");
                        groups.add(currentGroup);
                    }
                    currentGroup.commands.add(line);
                }
            }

            // 2. ask user
            CommandGroup runGroup = null;
            if(groups.size() == 1) {
                // not need to ask
                runGroup = groups.get(0);
            }else if(groups.size() > 1) {
                // need to ask
                logger.info("Founded " + groups.size() + " groups. Need to select.");

                if(feedbackPlayer != null){
                    // choice dialog
                    Map<String, String> list = new LinkedHashMap<>();
                    Map<String, Integer> sort = new LinkedHashMap<>();
                    for(Integer i = 0; i < groups.size(); i++){
                        list.put(Integer.toString(i + 1), groups.get(i).getPrintNameWithStats());
                        sort.put(Integer.toString(i + 1), i);
                    }

                    Choice groupChoice = new ChoiceImpl(false);
                    groupChoice.setMessage("Choose commands group to run");
                    groupChoice.setKeyChoices(list);
                    groupChoice.setSortData(sort);

                    if (feedbackPlayer.choose(Outcome.Benefit, groupChoice, game)) {
                        String need = groupChoice.getChoiceKey();
                        if ((need != null) && list.containsKey(need))
                        {
                            runGroup = groups.get(Integer.parseInt(need) - 1);
                        }
                    }
                }else{
                    // select default
                    runGroup = groups.get(0);
                }

            }

            if(runGroup == null) {
                // was canceled
                logger.info("Command file was empty or canceled");
                return;
            }

            logger.info("Selected group [" + runGroup.name + "] with " + runGroup.commands.size() + " commands");

            // 3. run commands
            for (String line: runGroup.commands) {

                Matcher matchCommand = patternCard.matcher(line);
                if (!matchCommand.matches()) {
                    logger.warn("Unknown command format: " + line);
                    continue;
                }

                String zone = matchCommand.group(1);
                String nickname = matchCommand.group(2);

                Optional<Player> playerOptional = findPlayer(game, nickname);
                if (!playerOptional.isPresent()) {
                    logger.warn("Unknown player: " + line);
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
                    logger.warn("Unknown zone: " + line);
                    continue;
                }

                String cardName = matchCommand.group(3);
                Integer amount = Integer.parseInt(matchCommand.group(4));

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
                    logger.warn("Unknown card [" + cardName + "]: " + line);
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
