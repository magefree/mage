package mage.players;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.Modes;
import mage.abilities.TriggeredAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.ManaCost;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.constants.MultiAmountType;
import mage.constants.Outcome;
import mage.constants.RangeOfInfluence;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.draft.Draft;
import mage.game.match.Match;
import mage.game.permanent.Permanent;
import mage.game.tournament.Tournament;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.util.MultiAmountMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.google.common.collect.Iterables.getOnlyElement;

public class StubPlayer extends PlayerImpl implements Player {

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game) {
        if (target instanceof TargetPlayer) {
            for (Player player : game.getPlayers().values()) {
                if (player.getId().equals(getId()) && target.canTarget(getId(), game)) {
                    target.add(player.getId(), game);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        cards.getCards(game).stream().map(MageItem::getId).forEach(cardId -> target.add(cardId, game));
        return true;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        UUID cardId = getOnlyElement(cards.getCards(game)).getId();
        if (chooseScry(game, cardId)) {
            target.add(cardId, game);
            return true;
        }
        return false;
    }

    public List<UUID> chooseDiscardBottom(Game game, int count, List<UUID> cardIds) {
        return cardIds.subList(0, count);
    }

    public boolean chooseScry(Game game, UUID cardId) {
        return false;
    }

    @Override
    public void shuffleLibrary(Ability source, Game game) {

    }

    public StubPlayer(String name, RangeOfInfluence range) {
        super(name, range);
    }

    @Override
    public void abort() {

    }

    @Override
    public void skip() {

    }

    @Override
    public Player copy() {
        return null;
    }

    @Override
    public boolean priority(Game game) {
        return false;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game, Map<String, Serializable> options) {
        return false;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        if (target.getFilter().getMessage() != null && target.getFilter().getMessage().endsWith(" more) to put on the bottom of your library")) {
            chooseDiscardBottom(game, target.getMinNumberOfTargets(), new ArrayList<>(target.possibleTargets(null, source, game)))
                    .forEach(cardId -> target.add(cardId, game));
        }
        return false;
    }

    @Override
    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean chooseMulligan(Game game) {
        return false;
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, String secondMessage, String trueText, String falseText, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        return false;
    }

    @Override
    public boolean choosePile(Outcome outcome, String message, List<? extends Card> pile1, List<? extends Card> pile2, Game game) {
        return false;
    }

    @Override
    public boolean playMana(Ability ability, ManaCost unpaid, String promptText, Game game) {
        return false;
    }

    @Override
    public int announceXMana(int min, int max, int multiplier, String message, Game game, Ability ability) {
        return 0;
    }

    @Override
    public int announceXCost(int min, int max, String message, Game game, Ability ability, VariableCost variableCost) {
        return 0;
    }

    @Override
    public int chooseReplacementEffect(Map<String, String> abilityMap, Game game) {
        return 0;
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game) {
        return null;
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        return null;
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {

    }

    @Override
    public void selectBlockers(Ability source, Game game, UUID defendingPlayerId) {

    }

    @Override
    public UUID chooseAttackerOrder(List<Permanent> attacker, Game game) {
        return null;
    }

    @Override
    public UUID chooseBlockerOrder(List<Permanent> blockers, CombatGroup combatGroup, List<UUID> blockerOrder, Game game) {
        return null;
    }

    @Override
    public void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID attackerId, Ability source, Game game) {

    }

    @Override
    public int getAmount(int min, int max, String message, Game game) {
        return 0;
    }

    @Override
    public List<Integer> getMultiAmountWithIndividualConstraints(Outcome outcome, List<MultiAmountMessage> messages,
            int min, int max, MultiAmountType type, Game game) {
        return null;
    }

    @Override
    public void sideboard(Match match, Deck deck) {

    }

    @Override
    public void construct(Tournament tournament, Deck deck) {

    }

    @Override
    public void pickCard(List<Card> cards, Deck deck, Draft draft) {

    }
    
    @Override
    public void addPhyrexianToColors(FilterMana colors) {
        
    }

    @Override
    public FilterMana getPhyrexianColors() {
        return (new FilterMana());
    }
}