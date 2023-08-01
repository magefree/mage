package mage.game;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.CommanderReplacementEffect;
import mage.abilities.effects.common.cost.CommanderCostModification;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.*;
import mage.game.mulligan.Mulligan;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.CommanderInfoWatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author JRHerlehy
 */
public abstract class GameTinyLeadersImpl extends GameImpl {

    protected boolean alsoHand; // replace also commander going to library
    protected boolean alsoLibrary; // replace also commander going to library

    // 103.7a  In a two-player game, the player who plays first skips the draw step
    // (see rule 504, "Draw Step") of his or her first turn.
    protected boolean startingPlayerSkipsDraw = true;

    public GameTinyLeadersImpl(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife) {
        super(attackOption, range, mulligan, startLife, 50, 7);
    }

    public GameTinyLeadersImpl(final GameTinyLeadersImpl game) {
        super(game);
        this.alsoHand = game.alsoHand;
        this.startingPlayerSkipsDraw = game.startingPlayerSkipsDraw;
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        // add game mode specific watchers here
        //state.addWatcher(new CommanderPlaysCountWatcher());

        // move tiny leader to command zone
        for (UUID playerId : state.getPlayerList(startingPlayerId)) {
            Player player = getPlayer(playerId);
            if (player != null) {
                String commanderName = player.getMatchPlayer().getDeck().getName();
                Card commander = findCommander(this, player, commanderName);
                if (commander != null) {
                    // already exists - just move to zone (example: game restart by Karn Liberated)
                    commander.moveToZone(Zone.COMMAND, null, this, true);
                } else {
                    // create new commander
                    commander = getCommanderCard(commanderName, player.getId());
                    if (commander != null) {
                        Set<Card> cards = new HashSet<>();
                        cards.add(commander);
                        this.loadCards(cards, playerId);
                        player.addCommanderId(commander.getId());
                        commander.moveToZone(Zone.COMMAND, null, this, true);
                        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new InfoEffect("Commander effects"));
                        ability.addEffect(new CommanderReplacementEffect(commander.getId(), alsoHand, alsoLibrary, false, "Commander"));
                        ability.addEffect(new CommanderCostModification(commander));
                        // Commander rule #4 was removed Jan. 18, 2016
                        // ability.addEffect(new CommanderManaReplacementEffect(player.getId(), CardUtil.getColorIdentity(commander)));
                        CommanderInfoWatcher watcher = new CommanderInfoWatcher("Commander", commander.getId(), false);
                        getState().addWatcher(watcher);
                        watcher.addCardInfoToCommander(this);
                        this.getState().addAbility(ability, null);
                    } else {
                        // GameWorker.call processing errors and write it in magediag.log by defalt
                        // Test use case: create tiny game with random generated deck - game freezes with empty battlefield
                        throw new IllegalStateException("Commander card could not be created. Name: [" + player.getMatchPlayer().getDeck().getName() + ']');
                    }
                }
            }
        }
        super.init(choosingPlayerId);
        if (startingPlayerSkipsDraw) {
            state.getTurnMods().add(new TurnMod(startingPlayerId).withSkipStep(PhaseStep.DRAW));
        }
    }

    private Card findCommander(Game game, Player player, String commanderName) {
        return game.getCommanderCardsFromAnyZones(player, CommanderCardType.ANY, Zone.ALL)
                .stream()
                .filter(c -> CardUtil.haveSameNames(c, commanderName, game))
                .findFirst()
                .orElse(null);
    }

    /**
     * Name of Tiny Leader comes from the deck name (it's not in the sideboard)
     * Additionally, it was taken into account that WOTC had missed a few color
     * combinations when making Legendary Creatures at 3 CMC. There are two
     * Commanders available to use for the missing color identities: Sultai [UBG
     * 3/3] and Glass [colorless 3/3]
     *
     * @param commanderName
     * @param ownerId
     * @return
     */
    public static Card getCommanderCard(String commanderName, UUID ownerId) {
        Card commander = null;
        if (commanderName != null) {
            switch (commanderName) {
                case "Sultai":
                    commander = new DefaultCommander(ownerId, commanderName, "{U}{B}{G}");
                    break;
                case "Glass":
                    commander = new DefaultCommander(ownerId, commanderName, "{C}{C}{C}");
                    break;
                default:
                    CardInfo cardInfo = CardRepository.instance.findCard(commanderName);
                    if (cardInfo != null) {
                        commander = cardInfo.getCard();
                    }
            }
        }
        return commander;
    }

    public void setAlsoHand(boolean alsoHand) {
        this.alsoHand = alsoHand;
    }

    public void setAlsoLibrary(boolean alsoLibrary) {
        this.alsoLibrary = alsoLibrary;
    }
}

class DefaultCommander extends CardImpl {

    public DefaultCommander(UUID ownerId, String commanderName, String manaString) {
        super(ownerId, new CardSetInfo(commanderName, "", "999", Rarity.RARE), new CardType[]{CardType.CREATURE}, manaString);
        this.supertype.add(SuperType.LEGENDARY);

        if (manaString.contains("{G}")) {
            this.color.setGreen(true);
        }
        if (manaString.contains("{W}")) {
            this.color.setWhite(true);
        }
        if (manaString.contains("{U}")) {
            this.color.setBlue(true);
        }
        if (manaString.contains("{B}")) {
            this.color.setBlack(true);
        }
        if (manaString.contains("{R}")) {
            this.color.setRed(true);
        }
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

    }

    public DefaultCommander(final DefaultCommander card) {
        super(card);
    }

    @Override
    public DefaultCommander copy() {
        return new DefaultCommander(this);
    }
}
