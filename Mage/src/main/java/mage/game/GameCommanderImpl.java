package mage.game;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.CommanderChooseColorAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.CommanderReplacementEffect;
import mage.abilities.effects.common.cost.CommanderCostModification;
import mage.abilities.keyword.CompanionAbility;
import mage.cards.Card;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.filter.FilterMana;
import mage.game.mulligan.Mulligan;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.watchers.common.CommanderInfoWatcher;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.*;
import java.util.stream.Stream;

public abstract class GameCommanderImpl extends GameImpl {

    // private final Map<UUID, Cards> mulliganedCards = new HashMap<>();
    protected boolean checkCommanderDamage = true;

    // old commander's versions (before 2017) restrict return from hand or library to command zone
    protected boolean alsoHand = true;    // replace commander going to hand
    protected boolean alsoLibrary = true; // replace commander going to library

    protected boolean startingPlayerSkipsDraw = true;

    public GameCommanderImpl(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startingLife, int startingHandSize) {
        super(attackOption, range, mulligan, startingLife, startingHandSize);
    }

    public GameCommanderImpl(final GameCommanderImpl game) {
        super(game);
        this.alsoHand = game.alsoHand;
        this.alsoLibrary = game.alsoLibrary;
        this.startingPlayerSkipsDraw = game.startingPlayerSkipsDraw;
        this.checkCommanderDamage = game.checkCommanderDamage;
    }

    private void handlePipers(Player player, Set<Card> commanders) {
        int piperCount = commanders
                .stream()
                .filter(CommanderChooseColorAbility::checkCard)
                .mapToInt(x -> 1)
                .sum();
        if (piperCount < 1) {
            return;
        }
        FilterMana leftoverColors = new FilterMana();
        Stream.concat(
                player.getLibrary().getCards(this).stream(),
                player.getSideboard().getCards(this).stream()
        ).map(Card::getColorIdentity).forEach(leftoverColors::addAll);
        FilterMana nonPiperIdentity = new FilterMana();
        commanders
                .stream()
                .filter(card -> !CommanderChooseColorAbility.checkCard(card))
                .map(Card::getColorIdentity)
                .forEach(nonPiperIdentity::addAll);
        leftoverColors.removeAll(nonPiperIdentity);
        if (piperCount < leftoverColors.getColorCount()) {
            throw new UnsupportedOperationException("This deck should not be legal, something went wrong");
        }
        Iterator<ObjectColor> iterator = leftoverColors.getColors().listIterator();
        for (Card commander : commanders) {
            if (!CommanderChooseColorAbility.checkCard(commander)) {
                continue;
            }
            ObjectColor color;
            if (!iterator.hasNext()) {
                ChoiceColor choiceColor = new ChoiceColor(
                        true, "Choose a color for " + commander.getName()
                );
                player.choose(Outcome.Neutral, choiceColor, this);
                color = choiceColor.getColor();
            } else {
                color = iterator.next();
            }
            commander.getColor().addColor(color);
        }
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        // Karn Liberated calls it to restart game, all data and commanders must be re-initialized

        // add game mode specific watchers here
        //state.addWatcher(new CommanderPlaysCountWatcher());

        // move commanders to command zone
        for (UUID playerId : state.getPlayerList(startingPlayerId)) {
            Player player = getPlayer(playerId);
            if (player == null) {
                continue;
            }
            // add new commanders
            Set<Card> commanders = new HashSet<>();
            for (UUID cardId : player.getSideboard()) {
                Card card = this.getCard(cardId);
                if (card == null) {
                    continue;
                }
                // Check for companions. If it is the only card in the sideboard, it is the commander, not a companion.
                if (player.getSideboard().size() > 1 && card.getAbilities(this).stream().anyMatch(CompanionAbility.class::isInstance)) {
                    continue;
                }
                commanders.add(card);
                addCommander(card, player);
            }

            handlePipers(player, commanders);

            // init commanders
            for (UUID commanderId : this.getCommandersIds(player, CommanderCardType.ANY, false)) {
                Card commander = this.getCard(commanderId);
                if (commander != null) {
                    initCommander(commander, player);
                }
            }
        }

        super.init(choosingPlayerId);
        if (startingPlayerSkipsDraw) {
            state.getTurnMods().add(new TurnMod(startingPlayerId, PhaseStep.DRAW));
        }
    }

    public void initCommander(Card commander, Player player) {
        if (!Zone.EXILED.equals(getState().getZone(commander.getId()))) { // Exile check needed for Karn Liberated restart
            commander.moveToZone(Zone.COMMAND, null, this, true);
        }
        commander.getAbilities().setControllerId(player.getId());

        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new InfoEffect("Commander effects"));
        initCommanderEffects(commander, player, ability);
        CommanderInfoWatcher watcher = initCommanderWatcher(commander, checkCommanderDamage);
        getState().addWatcher(watcher);
        watcher.addCardInfoToCommander(this);
        this.getState().addAbility(ability, null);
    }

    public CommanderInfoWatcher initCommanderWatcher(Card commander, boolean checkCommanderDamage) {
        return new CommanderInfoWatcher("Commander", commander.getId(), checkCommanderDamage);
    }

    public void initCommanderEffects(Card commander, Player player, Ability commanderAbility) {
        // all commander effects must be independent from sourceId or controllerId
        commanderAbility.addEffect(new CommanderReplacementEffect(commander.getId(), alsoHand, alsoLibrary, false, "Commander"));
        commanderAbility.addEffect(new CommanderCostModification(commander));
    }

    //20130711
    /*903.8. The Commander variant uses an alternate mulligan rule.
     * Each time a player takes a mulligan, rather than shuffling their entire hand of cards into their library, that player exiles any number of cards from their hand face down.
     * Then the player draws a number of cards equal to one less than the number of cards they exiled this way.
     * That player may look at all cards exiled this way while taking mulligans.
     * Once a player keeps an opening hand, that player shuffles all cards they exiled this way into their library.
     * */
    //TODO implement may look at exile cards
    @Override
    public void mulligan(UUID playerId) {
        super.mulligan(playerId);
        // Paris mulligan - no longer used by default for commander
//        Player player = getPlayer(playerId);
//        TargetCardInHand target = new TargetCardInHand(1, player.getHand().size(), new FilterCard("card to mulligan"));
//        target.setNotTarget(true);
//        target.setRequired(false);
//        if (player.choose(Outcome.Exile, player.getHand(), target, this)) {
//            int numCards = target.getTargets().size();
//            for (UUID uuid : target.getTargets()) {
//                Card card = player.getHand().get(uuid, this);
//                if (card != null) {
//                    if (!mulliganedCards.containsKey(playerId)) {
//                        mulliganedCards.put(playerId, new CardsImpl());
//                    }
//                    player.getHand().remove(card);
//                    getExile().add(card);
//                    getState().setZone(card.getId(), Zone.EXILED);
//                    card.setFaceDown(true, this);
//                    mulliganedCards.get(playerId).add(card);
//                }
//            }
//            int deduction = 1;
//            if (freeMulligans > 0) {
//                if (usedFreeMulligans != null && usedFreeMulligans.containsKey(player.getId())) {
//                    int used = usedFreeMulligans.get(player.getId());
//                    if (used < freeMulligans) {
//                        deduction = 0;
//                        usedFreeMulligans.put(player.getId(), used + 1);
//                    }
//                } else {
//                    deduction = 0;
//                    {
//
//                    }
//                    usedFreeMulligans.put(player.getId(), 1);
//                }
//            }
//            player.drawCards(numCards - deduction, this);
//            fireInformEvent(new StringBuilder(player.getLogName())
//                    .append(" mulligans ")
//                    .append(numCards)
//                    .append(numCards == 1 ? " card" : " cards")
//                    .append(deduction == 0 ? " for free and draws " : " down to ")
//                    .append(Integer.toString(player.getHand().size()))
//                    .append(player.getHand().size() <= 1 ? " card" : " cards").toString());
//        }
    }

    @Override
    public void endMulligan(UUID playerId) {
        super.endMulligan(playerId);
        // Paris mulligan - no longer used by default for commander
//        //return cards to
//        Player player = getPlayer(playerId);
//        if (player != null && mulliganedCards.containsKey(playerId)) {
//            for (Card card : mulliganedCards.get(playerId).getCards(this)) {
//                if (card != null) {
//                    getExile().removeCard(card, this);
//                    player.getLibrary().putOnTop(card, this);
//                    getState().setZone(card.getId(), Zone.LIBRARY);
//                    card.setFaceDown(false, this);
//                }
//            }
//            if (mulliganedCards.get(playerId).size() > 0) {
//                player.shuffleLibrary(null, this);
//            }
//        }
    }

    /* 20130711
     *903.14a A player that's been dealt 21 or more combat damage by the same commander
     * over the course of the game loses the game. (This is a state-based action. See rule 704.)
     *
     */
    @Override
    protected boolean checkStateBasedActions() {
        for (Player player : getPlayers().values()) {
            for (UUID commanderId : this.getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, false)) {
                CommanderInfoWatcher damageWatcher = getState().getWatcher(CommanderInfoWatcher.class, commanderId);
                if (damageWatcher == null) {
                    continue;
                }
                for (Map.Entry<UUID, Integer> entrySet : damageWatcher.getDamageToPlayer().entrySet()) {
                    if (entrySet.getValue() > 20) {
                        Player opponent = getPlayer(entrySet.getKey());
                        if (opponent != null && !opponent.hasLost() && player.isInGame()) {
                            opponent.lost(this);
                        }
                    }
                }
            }
        }
        return super.checkStateBasedActions();
    }

    public void setAlsoHand(boolean alsoHand) {
        this.alsoHand = alsoHand;
    }

    public void setAlsoLibrary(boolean alsoLibrary) {
        this.alsoLibrary = alsoLibrary;
    }

    public boolean isCheckCommanderDamage() {
        return checkCommanderDamage;
    }

    public void setCheckCommanderDamage(boolean checkCommanderDamage) {
        this.checkCommanderDamage = checkCommanderDamage;
    }

    public void addCommander(Card card, Player player) {
        player.addCommanderId(card.getId());
    }

}
