package mage.server.util;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Planes;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.GameCommanderImpl;
import mage.game.command.CommandObject;
import mage.game.command.Plane;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.RandomUtil;

import java.io.File;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public final class SystemUtil {

    private SystemUtil() {
    }

    public static final DateFormat dateFormat = new SimpleDateFormat("yy-M-dd HH:mm:ss");

    private static final String INIT_FILE_PATH = "config" + File.separator + "init.txt";
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SystemUtil.class);

    // replace ref group command like @group by real commands from that group
    // example:
    // [my group]
    // command 1
    // @another group
    // [another group]
    // command 2
    // command 3
    private static final String COMMAND_REF_PREFIX = "@";

    // transform special group names from init.txt to special commands in choose dialog:
    // [@mana add] -> MANA ADD
    private static final String COMMAND_MANA_ADD = "@mana add"; // TODO: not implemented
    private static final String COMMAND_LANDS_ADD = "@lands add"; // TODO: not implemented
    private static final String COMMAND_RUN_CUSTOM_CODE = "@run custom code"; // TODO: not implemented
    private static final String COMMAND_SHOW_OPPONENT_HAND = "@show opponent hand";
    private static final String COMMAND_SHOW_OPPONENT_LIBRARY = "@show opponent library";
    private static final String COMMAND_SHOW_MY_HAND = "@show my hand";
    private static final String COMMAND_SHOW_MY_LIBRARY = "@show my library";
    private static final String COMMAND_ACTIVATE_OPPONENT_ABILITY = "@activate opponent ability";
    private static final Map<String, String> supportedCommands = new HashMap<>();

    static {
        // special commands names in choose dialog
        supportedCommands.put(COMMAND_MANA_ADD, "MANA ADD");
        supportedCommands.put(COMMAND_LANDS_ADD, "LANDS ADD");
        supportedCommands.put(COMMAND_RUN_CUSTOM_CODE, "RUN CUSTOM CODE");
        supportedCommands.put(COMMAND_SHOW_OPPONENT_HAND, "SHOW OPPONENT HAND");
        supportedCommands.put(COMMAND_SHOW_OPPONENT_LIBRARY, "SHOW OPPONENT LIBRARY");
        supportedCommands.put(COMMAND_SHOW_MY_HAND, "SHOW MY HAND");
        supportedCommands.put(COMMAND_SHOW_MY_LIBRARY, "SHOW MY LIBRARY");
        supportedCommands.put(COMMAND_ACTIVATE_OPPONENT_ABILITY, "ACTIVATE OPPONENT ABILITY");
    }

    private static final Pattern patternGroup = Pattern.compile("\\[(.+)\\]"); // [test new card]
    private static final Pattern patternCommand = Pattern.compile("([\\w]+):([\\S ]+?):([\\S ]+):([\\d]+)"); // battlefield:Human:Island:10
    private static final Pattern patternCardInfo = Pattern.compile("([\\S ]+):([\\S ]+)"); // Island:XLN

    // show ext info for special commands
    private static final String PARAM_COLOR_COST = "color cost";
    private static final String PARAM_COLOR_COMMANDER = "color commander";
    private static final String PARAM_PT = "pt"; // power toughness
    private static final String PARAM_ABILITIES_COUNT = "abilities count";
    private static final String PARAM_ABILITIES_LIST = "abilities list";

    private static class CommandGroup {

        String name;
        boolean isSpecialCommand;
        List<String> commands = new ArrayList<>();

        public CommandGroup(String name) {
            this(name, false);
        }

        public CommandGroup(String name, boolean isSpecialCommand) {
            this.name = name;
            this.isSpecialCommand = isSpecialCommand;
        }

        public String getPrintName() {
            if (this.isSpecialCommand && supportedCommands.containsKey(this.name)) {
                return supportedCommands.get(this.name);
            } else {
                return this.name;
            }
        }

        public String getPrintNameWithStats() {
            String res = this.getPrintName();
            if (!this.isSpecialCommand) {
                res = res + " (" + this.commands.size() + " commands)";
            }

            return res;
        }
    }

    private static String getCardsListForSpecialInform(Game game, Set<UUID> cardsList, List<String> commandParams) {
        return getCardsListForSpecialInform(game, cardsList.stream().collect(Collectors.toList()), commandParams);
    }

    private static String getCardsListForSpecialInform(Game game, List<UUID> cardsList, List<String> commandParams) {
        // cards list with ext info

        List<String> res = new ArrayList<>();

        for (UUID cardID : cardsList) {
            Card card = game.getCard(cardID);
            if (card == null) {
                continue;
            }

            // basic info (card + set)
            String cardInfo = card.getName() + " - " + card.getExpansionSetCode();

            // optional info
            List<String> resInfo = new ArrayList<>();
            for (String param : commandParams) {
                switch (param) {
                    case PARAM_COLOR_COST:
                        resInfo.add(card.getColor(game).toString());
                        break;
                    case PARAM_COLOR_COMMANDER:
                        resInfo.add(card.getColorIdentity().toString());
                        break;
                    case PARAM_PT:
                        resInfo.add(card.getPower() + " / " + card.getToughness());
                        break;
                    case PARAM_ABILITIES_COUNT:
                        resInfo.add(String.valueOf(card.getAbilities(game).size()));
                        break;
                    case PARAM_ABILITIES_LIST:
                        resInfo.add(card.getAbilities(game).stream()
                                .map(Ability::getClass)
                                .map(Class::getSimpleName)
                                .collect(Collectors.joining(", "))
                        );
                        break;
                    default:
                        logger.warn("Unknown param for cards list: " + param);
                }
            }

            if (!resInfo.isEmpty()) {
                cardInfo += ": " + resInfo.stream().collect(Collectors.joining("; "));
            }

            res.add(cardInfo);
        }

        return res.stream().sorted().collect(Collectors.joining("\n"));
    }

    private static class CardCommandData {

        public String source;
        public String zone;
        public String player;
        public String cardName;
        public String cardSet;
        public Integer Amount;

        public Boolean OK;
        public String Error;
    }

    public static CardCommandData parseCardCommand(String commandLine) {
        CardCommandData com = new CardCommandData();
        com.source = commandLine.trim();
        com.OK = false;
        com.Error = "unknown error";

        Matcher matchCommand = patternCommand.matcher(com.source);
        if (!matchCommand.matches()) {
            com.Error = "Unknown command format";
            return com;
        }

        com.zone = matchCommand.group(1);
        com.player = matchCommand.group(2);
        try {
            com.Amount = Integer.parseInt(matchCommand.group(4));
        } catch (Throwable e) {
            com.Error = "Can't parse amount value [" + matchCommand.group(4) + "]";
            return com;
        }

        // card name can be with set
        String cardInfo = matchCommand.group(3);
        Matcher matchInfo = patternCardInfo.matcher(cardInfo);
        if (matchInfo.matches()) {
            // name with set
            com.cardName = matchInfo.group(1);
            com.cardSet = matchInfo.group(2);
        } else {
            // name only
            com.cardName = cardInfo;
            com.cardSet = "";
        }

        if (com.cardName.isEmpty()) {
            com.Error = "Card name is empty";
            return com;
        }

        if (com.Amount <= 0) {
            com.Error = "Amount [" + com.Amount + "] must be greater than 0";
            return com;
        }

        // all ok
        com.Error = "";
        com.OK = true;
        return com;
    }

    public static void addCardsForTesting(Game game) {
        addCardsForTesting(game, null, null);
    }

    /**
     * Replaces cards in player's hands by specified in config/init.txt.<br/>
     * <br/>
     * <b>Implementation note:</b><br/>
     * 1. Read init.txt line by line<br/>
     * 2. Parse line using for searching groups like: [group 1] 3. Parse line
     * using the following format: line ::=
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
            if (fileName == null) {
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
            // 3. process system commands
            // 4. run commands from selected group
            // 1. parse
            List<CommandGroup> groups = new ArrayList<>();

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
                        if (groupName.startsWith("@")) {
                            // special command group
                            if (supportedCommands.containsKey(groupName)) {
                                currentGroup = new CommandGroup(groupName, true);
                                groups.add(currentGroup);
                            } else {
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
            if (groups.size() == 1) {
                // not need to ask
                runGroup = groups.get(0);
            } else if (groups.size() > 1) {
                // need to ask
                logger.info("Found " + groups.size() + " groups. Need to select.");

                if (feedbackPlayer != null) {
                    // choice dialog
                    Map<String, String> list = new LinkedHashMap<>();
                    Map<String, Integer> sort = new LinkedHashMap<>();
                    for (int i = 0; i < groups.size(); i++) {
                        list.put(Integer.toString(i + 1), groups.get(i).getPrintNameWithStats());
                        sort.put(Integer.toString(i + 1), i);
                    }

                    Choice groupChoice = new ChoiceImpl(false);
                    groupChoice.setMessage("Choose commands group to run");
                    groupChoice.setKeyChoices(list);
                    groupChoice.setSortData(sort);

                    if (feedbackPlayer.choose(Outcome.Benefit, groupChoice, game)) {
                        String need = groupChoice.getChoiceKey();
                        if ((need != null) && list.containsKey(need)) {
                            runGroup = groups.get(Integer.parseInt(need) - 1);
                        }
                    }
                } else {
                    // select default
                    runGroup = groups.get(0);
                }

            }

            if (runGroup == null) {
                // was canceled
                logger.info("Command file was empty or canceled");
                return;
            }

            logger.info("Selected group [" + runGroup.name + "] with " + runGroup.commands.size() + " commands");

            // 3. system commands
            if (runGroup.isSpecialCommand) {

                Player opponent = game.getPlayer(game.getOpponents(feedbackPlayer.getId()).iterator().next());

                switch (runGroup.name) {

                    case COMMAND_SHOW_OPPONENT_HAND:
                        if (opponent != null) {
                            String info = getCardsListForSpecialInform(game, opponent.getHand(), runGroup.commands);
                            game.informPlayer(feedbackPlayer, info);
                        }
                        break;

                    case COMMAND_SHOW_OPPONENT_LIBRARY:
                        if (opponent != null) {
                            String info = getCardsListForSpecialInform(game, opponent.getLibrary().getCardList(), runGroup.commands);
                            game.informPlayer(feedbackPlayer, info);
                        }
                        break;

                    case COMMAND_SHOW_MY_HAND:
                        if (feedbackPlayer != null) {
                            String info = getCardsListForSpecialInform(game, feedbackPlayer.getHand(), runGroup.commands);
                            game.informPlayer(feedbackPlayer, info);
                        }
                        break;

                    case COMMAND_SHOW_MY_LIBRARY:
                        if (feedbackPlayer != null) {
                            String info = getCardsListForSpecialInform(game, feedbackPlayer.getLibrary().getCardList(), runGroup.commands);
                            game.informPlayer(feedbackPlayer, info);
                        }
                        break;

                    case COMMAND_ACTIVATE_OPPONENT_ABILITY:
                        // WARNING, maybe very bugged if called in wrong priority
                        // uses choose triggered ability dialog to select it
                        if (feedbackPlayer != null) {
                            UUID savedPriorityPlayer = null;
                            if (game.getActivePlayerId() != opponent.getId()) {
                                savedPriorityPlayer = game.getActivePlayerId();
                            }

                            // change active player to find and play selected abilities (it's danger and buggy code)
                            if (savedPriorityPlayer != null) {
                                game.getState().setPriorityPlayerId(opponent.getId());
                                game.firePriorityEvent(opponent.getId());
                            }

                            List<ActivatedAbility> abilities = opponent.getPlayable(game, true);
                            Map<String, String> choices = new HashMap<>();
                            abilities.forEach(ability -> {
                                MageObject object = ability.getSourceObject(game);
                                choices.put(ability.getId().toString(), object.getName() + ": " + ability.toString());
                            });
                            // TODO: set priority for us?
                            Choice choice = new ChoiceImpl();
                            choice.setMessage("Choose playable ability to activate by opponent " + opponent.getName());
                            choice.setKeyChoices(choices);
                            if (feedbackPlayer.choose(Outcome.Detriment, choice, game) && choice.getChoiceKey() != null) {
                                String needId = choice.getChoiceKey();
                                Optional<ActivatedAbility> ability = abilities.stream().filter(a -> a.getId().toString().equals(needId)).findFirst();
                                if (ability.isPresent()) {
                                    // TODO: set priority for player?
                                    ActivatedAbility activatedAbility = ability.get();
                                    game.informPlayers(feedbackPlayer.getLogName() + " as another player " + opponent.getLogName()
                                            + " trying to force an activate ability: " + activatedAbility.getGameLogMessage(game));
                                    if (opponent.activateAbility(activatedAbility, game)) {
                                        game.informPlayers("Force to activate ability: DONE");
                                    } else {
                                        game.informPlayers("Force to activate ability: FAIL");
                                    }
                                }
                            }
                            // restore original priority player
                            if (savedPriorityPlayer != null) {
                                game.getState().setPriorityPlayerId(savedPriorityPlayer);
                                game.firePriorityEvent(savedPriorityPlayer);
                            }
                        }
                        break;
                }

                return;
            }

            // insert group refs as extra commands (only user defined groups allowed, no special)
            Map<String, CommandGroup> otherGroupRefs = new HashMap<>();
            for (CommandGroup group : groups) {
                if (group != runGroup) {
                    otherGroupRefs.putIfAbsent(COMMAND_REF_PREFIX + group.name, group);
                }
            }
            for (int i = runGroup.commands.size() - 1; i >= 0; i--) {
                String line = runGroup.commands.get(i);
                // replace ref by real commands from another group
                if (line.startsWith(COMMAND_REF_PREFIX)) {
                    CommandGroup other = otherGroupRefs.getOrDefault(line, null);
                    if (other != null && !other.isSpecialCommand) {
                        logger.info("Replace ref group " + line + " by " + other.commands.size() + " commands");
                        runGroup.commands.remove(i);
                        runGroup.commands.addAll(i, other.commands);
                    } else {
                        logger.error("Can't find ref group: " + line);
                    }
                }
            }

            // 4. run commands
            for (String line : runGroup.commands) {

                CardCommandData command = parseCardCommand(line);
                if (!command.OK) {
                    logger.warn(command.Error + ": " + line);
                    continue;
                }

                Optional<Player> playerOptional = findPlayer(game, command.player);
                if (!playerOptional.isPresent()) {
                    logger.warn("Unknown player: " + line);
                    continue;
                }
                Player player = playerOptional.get();

                // SPECIAL token/emblem call (without SET name)
                if ("token".equalsIgnoreCase(command.zone)) {
                    // eg: token:Human:HippoToken:1
                    Class<?> c = Class.forName("mage.game.permanent.token." + command.cardName);
                    Constructor<?> cons = c.getConstructor();
                    Object token = cons.newInstance();
                    if (token instanceof mage.game.permanent.token.Token) {
                        ((mage.game.permanent.token.Token) token).putOntoBattlefield(command.Amount, game, null, player.getId(), false, false);
                        continue;
                    }
                } else if ("emblem".equalsIgnoreCase(command.zone)) {
                    // eg: emblem:Human:ElspethSunsChampionEmblem:1
                    Class<?> c = Class.forName("mage.game.command.emblems." + command.cardName);
                    Constructor<?> cons = c.getConstructor();
                    Object emblem = cons.newInstance();
                    if (emblem instanceof mage.game.command.Emblem) {
                        ((mage.game.command.Emblem) emblem).setControllerId(player.getId());
                        game.addEmblem((mage.game.command.Emblem) emblem, null, player.getId());
                        continue;
                    }
                } else if ("plane".equalsIgnoreCase(command.zone)) {
                    if (putPlaneToGame(game, player, command.cardName)) {
                        continue;
                    }
                } else if ("loyalty".equalsIgnoreCase(command.zone)) {
                    for (Permanent perm : game.getBattlefield().getAllActivePermanents(player.getId())) {
                        if (perm.getName().equals(command.cardName) && perm.getCardType().contains(CardType.PLANESWALKER)) {
                            perm.addCounters(CounterType.LOYALTY.createInstance(command.Amount), null, game);
                        }
                    }
                    continue;
                } else if ("stack".equalsIgnoreCase(command.zone)) {
                    // simple cast (without targets or modes)

                    // find card info
                    CardInfo cardInfo = CardRepository.instance.findCard(command.cardName);
                    if (cardInfo == null) {
                        logger.warn("Unknown card for stack command [" + command.cardName + "]: " + line);
                        continue;
                    }

                    // put card to game
                    Set<Card> cardsToLoad = new HashSet<>();
                    for (int i = 0; i < command.Amount; i++) {
                        cardsToLoad.add(cardInfo.getCard());
                    }
                    game.loadCards(cardsToLoad, player.getId());

                    // move card from exile to stack
                    for (Card card : cardsToLoad) {
                        putCardToZone(game, player, card, Zone.STACK);
                    }

                    continue;
                }

                Zone gameZone;
                if ("hand".equalsIgnoreCase(command.zone)) {
                    gameZone = Zone.HAND;
                } else if ("battlefield".equalsIgnoreCase(command.zone)) {
                    gameZone = Zone.BATTLEFIELD;
                } else if ("graveyard".equalsIgnoreCase(command.zone)) {
                    gameZone = Zone.GRAVEYARD;
                } else if ("library".equalsIgnoreCase(command.zone)) {
                    gameZone = Zone.LIBRARY;
                } else if ("token".equalsIgnoreCase(command.zone)) {
                    gameZone = Zone.BATTLEFIELD;
                } else if ("exiled".equalsIgnoreCase(command.zone)) {
                    gameZone = Zone.EXILED;
                } else if ("outside".equalsIgnoreCase(command.zone)) {
                    gameZone = Zone.OUTSIDE;
                } else if ("emblem".equalsIgnoreCase(command.zone)) {
                    gameZone = Zone.COMMAND;
                } else if ("plane".equalsIgnoreCase(command.zone)) {
                    gameZone = Zone.COMMAND;
                } else if ("commander".equalsIgnoreCase(command.zone)) {
                    gameZone = Zone.COMMAND;
                } else if ("sideboard".equalsIgnoreCase(command.zone)) {
                    gameZone = Zone.OUTSIDE;
                } else {
                    logger.warn("Unknown zone [" + command.zone + "]: " + line);
                    continue;
                }

                List<CardInfo> cards = null;
                if (command.cardSet.isEmpty()) {
                    // by name
                    cards = CardRepository.instance.findCards(command.cardName);
                } else {
                    // by name and set
                    cards = CardRepository.instance.findCards(new CardCriteria().setCodes(command.cardSet).name(command.cardName));
                }

                if (cards.isEmpty()) {
                    logger.warn("Unknown card [" + command.cardName + "]: " + line);
                    continue;
                }

                Set<Card> cardsToLoad = new HashSet<>();
                for (int i = 0; i < command.Amount; i++) {
                    CardInfo cardInfo = cards.get(RandomUtil.nextInt(cards.size()));
                    Card card = cardInfo != null ? cardInfo.getCard() : null;
                    if (card != null) {
                        cardsToLoad.add(card);
                    }
                }
                game.loadCards(cardsToLoad, player.getId());

                if ("commander".equalsIgnoreCase(command.zone) && cardsToLoad.size() > 0) {
                    // as commander (only commander games, look at init code in GameCommanderImpl)
                    if (game instanceof GameCommanderImpl) {
                        GameCommanderImpl gameCommander = (GameCommanderImpl) game;
                        cardsToLoad.forEach(card -> gameCommander.addCommander(card, player));
                        cardsToLoad.forEach(card -> gameCommander.initCommander(card, player));
                    } else {
                        logger.fatal("Commander card can be used in commander game only: " + command.cardName);
                    }
                } else if ("sideboard".equalsIgnoreCase(command.zone) && cardsToLoad.size() > 0) {
                    // put to sideboard
                    for (Card card : cardsToLoad) {
                        player.getSideboard().add(card);
                    }
                } else {
                    // as other card
                    for (Card card : cardsToLoad) {
                        putCardToZone(game, player, card, gameZone);
                    }
                }
            }
        } catch (Exception e) {
            logger.fatal("", e);
        }
    }

    /**
     * Put card to specified (battlefield zone will be tranformed to permanent with initialized effects)
     */
    private static void putCardToZone(Game game, Player player, Card card, Zone zone) {
        // TODO: replace by player.move?
        switch (zone) {
            case BATTLEFIELD:
                CardUtil.putCardOntoBattlefieldWithEffects(game, card, player);
                break;
            case LIBRARY:
                card.setZone(Zone.LIBRARY, game);
                player.getLibrary().putOnTop(card, game);
                break;
            case STACK:
                card.cast(game, game.getState().getZone(card.getId()), card.getSpellAbility(), player.getId());
                break;
            case EXILED:
                card.setZone(Zone.EXILED, game);
                game.getExile().getPermanentExile().add(card);
                break;
            case OUTSIDE:
                card.setZone(Zone.OUTSIDE, game);
                break;
            default:
                card.moveToZone(zone, null, game, false);
                break;
        }
        game.applyEffects();
        logger.info("Added card to player's " + zone.toString() + ": " + card.getName() + ", player = " + player.getName());
    }

    public static boolean putPlaneToGame(Game game, Player player, String planeClassName) {
        // remove the last plane and set its effects to discarded
        for (CommandObject comObject : game.getState().getCommand()) {
            if (comObject instanceof Plane) {
                if (comObject.getAbilities() != null) {
                    for (Ability ability : comObject.getAbilities()) {
                        for (Effect effect : ability.getEffects()) {
                            if (effect instanceof ContinuousEffect) {
                                ((ContinuousEffect) effect).discard();
                            }
                        }
                    }
                }
                game.getState().removeTriggersOfSourceId(comObject.getId());
                game.getState().getCommand().remove(comObject);
                break;
            }
        }

        // put new plane to game
        Planes planeType = Planes.fromClassName(planeClassName);
        Plane plane = Plane.createPlane(planeType);
        if (plane != null) {
            plane.setControllerId(player.getId());
            game.addPlane(plane, null, player.getId());
            return true;
        }
        return false;
    }

    /**
     * Find player by name.
     *
     * @param game
     * @param name
     * @return
     */
    private static Optional<Player> findPlayer(Game game, String name) {
        return game.getPlayers().values().stream()
                .filter(player -> player.getName().equals(name)).findFirst();

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
     * @param date1    the oldest date
     * @param date2    the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
