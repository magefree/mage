package mage.player.ai;

import mage.ApprovingObject;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.*;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.*;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.*;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;
import mage.cards.decks.DeckValidatorFactory;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.*;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.draft.Draft;
import mage.game.draft.RateCard;
import mage.game.events.GameEvent;
import mage.game.match.Match;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.game.tournament.Tournament;
import mage.player.ai.simulators.CombatGroupSimulator;
import mage.player.ai.simulators.CombatSimulator;
import mage.player.ai.simulators.CreatureSimulator;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.players.PlayerImpl;
import mage.players.net.UserData;
import mage.players.net.UserGroup;
import mage.target.*;
import mage.target.common.*;
import mage.util.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

/**
 * suitable for two player games and some multiplayer games
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class ComputerPlayer extends PlayerImpl implements Player {

    private static final Logger log = Logger.getLogger(ComputerPlayer.class);
    private long lastThinkTime; // msecs for last AI actions calc

    protected int PASSIVITY_PENALTY = 5; // Penalty value for doing nothing if some actions are available

    // debug only: set TRUE to debug simulation's code/games (on false sim thread will be stopped after few secs by timeout)
    protected boolean COMPUTER_DISABLE_TIMEOUT_IN_GAME_SIMULATIONS = false;

    private transient Map<Mana, Card> unplayable = new TreeMap<>();
    private transient List<Card> playableNonInstant = new ArrayList<>();
    private transient List<Card> playableInstant = new ArrayList<>();
    private transient List<ActivatedAbility> playableAbilities = new ArrayList<>();
    private transient List<PickedCard> pickedCards;
    private transient List<ColoredManaSymbol> chosenColors;

    private transient ManaCost currentUnpaidMana;

    public ComputerPlayer(String name, RangeOfInfluence range) {
        super(name, range);
        human = false;
        userData = UserData.getDefaultUserDataView();
        userData.setAvatarId(64);
        userData.setGroupId(UserGroup.COMPUTER.getGroupId());
        userData.setFlagName("computer.png");
        pickedCards = new ArrayList<>();
    }

    protected ComputerPlayer(UUID id) {
        super(id);
        human = false;
        userData = UserData.getDefaultUserDataView();
        userData.setAvatarId(64);
        userData.setGroupId(UserGroup.COMPUTER.getGroupId());
        userData.setFlagName("computer.png");
        pickedCards = new ArrayList<>();
    }

    public ComputerPlayer(final ComputerPlayer player) {
        super(player);
        this.lastThinkTime = player.lastThinkTime;
        this.PASSIVITY_PENALTY = player.PASSIVITY_PENALTY;
        this.COMPUTER_DISABLE_TIMEOUT_IN_GAME_SIMULATIONS = player.COMPUTER_DISABLE_TIMEOUT_IN_GAME_SIMULATIONS;
    }

    @Override
    public boolean chooseMulligan(Game game) {
        log.debug("chooseMulligan");
        if (hand.size() < 6
                || isTestsMode() // ignore mulligan in tests
                || game.getClass().getName().contains("Momir") // ignore mulligan in Momir games
        ) {
            return false;
        }
        Set<Card> lands = hand.getCards(new FilterLandCard(), game);
        return lands.size() < 2
                || lands.size() > hand.size() - 2;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game) {
        return choose(outcome, target, source, game, null);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game, Map<String, Serializable> options) {

        if (log.isDebugEnabled()) {
            log.debug("choose: " + outcome.toString() + ':' + target.toString());
        }

        // controller hints:
        // - target.getTargetController(), this.getId() -- player that must makes choices (must be same with this.getId)
        // - target.getAbilityController(), abilityControllerId -- affected player/controller for all actions/filters
        // - affected controler can be different from target controller (another player makes choices for controller)
        // sometimes a target selection can be made from a player that does not control the ability
        UUID abilityControllerId = playerId;
        if (target.getTargetController() != null
                && target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }
        UUID sourceId = source != null ? source.getSourceId() : null;

        boolean required = target.isRequired(sourceId, game);
        Set<UUID> possibleTargets = target.possibleTargets(abilityControllerId, source, game);
        if (possibleTargets.isEmpty() || target.getTargets().size() >= target.getNumberOfTargets()) {
            required = false;
        }

        UUID randomOpponentId;
        if (target.getTargetController() != null) {
            randomOpponentId = getRandomOpponent(target.getTargetController(), game);
        } else if (abilityControllerId != null) {
            randomOpponentId = getRandomOpponent(abilityControllerId, game);
        } else {
            randomOpponentId = getRandomOpponent(playerId, game);
        }

        if (target.getOriginalTarget() instanceof TargetPlayer) {
            return setTargetPlayer(outcome, target, null, abilityControllerId, randomOpponentId, game, required);
        }

        if (target.getOriginalTarget() instanceof TargetDiscard) {
            findPlayables(game);
            if (!unplayable.isEmpty()) {
                for (int i = unplayable.size() - 1; i >= 0; i--) {
                    if (target.canTarget(abilityControllerId, unplayable.values().toArray(new Card[0])[i].getId(), null, game)) {
                        target.add(unplayable.values().toArray(new Card[0])[i].getId(), game);
                        if (target.isChosen()) {
                            return true;
                        }
                    }
                }
            }
            if (!hand.isEmpty()) {
                for (int i = 0; i < hand.size(); i++) {
                    if (target.canTarget(abilityControllerId, hand.toArray(new UUID[0])[i], null, game)) {
                        target.add(hand.toArray(new UUID[0])[i], game);
                        if (target.isChosen()) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        if (target.getOriginalTarget() instanceof TargetControlledPermanent) {
            List<Permanent> targets;
            TargetControlledPermanent origTarget = (TargetControlledPermanent) target.getOriginalTarget();
            targets = threats(abilityControllerId, source, origTarget.getFilter(), game, target.getTargets());
            if (!outcome.isGood()) {
                Collections.reverse(targets);
            }
            for (Permanent permanent : targets) {
                if (origTarget.canTarget(abilityControllerId, permanent.getId(), source, game, false) && !target.getTargets().contains(permanent.getId())) {
                    target.add(permanent.getId(), game);
                    return true;
                }
            }
            return false;
        }

        if (target.getOriginalTarget() instanceof TargetPermanent) {

            FilterPermanent filter = null;
            if (target.getOriginalTarget().getFilter() instanceof FilterPermanent) {
                filter = (FilterPermanent) target.getOriginalTarget().getFilter();
            }
            if (filter == null) {
                throw new IllegalStateException("Unsupported permanent filter in computer's choose method: "
                        + target.getOriginalTarget().getClass().getCanonicalName());
            }

            List<Permanent> targets;
            if (outcome.isCanTargetAll()) {
                targets = threats(null, source, filter, game, target.getTargets());
            } else {
                if (outcome.isGood()) {
                    targets = threats(abilityControllerId, source, filter, game, target.getTargets());
                } else {
                    targets = threats(randomOpponentId, source, filter, game, target.getTargets());
                }
                if (targets.isEmpty() && target.isRequired()) {
                    if (!outcome.isGood()) {
                        targets = threats(abilityControllerId, source, filter, game, target.getTargets());
                    } else {
                        targets = threats(randomOpponentId, source, filter, game, target.getTargets());
                    }
                }
            }

            for (Permanent permanent : targets) {
                if (target.canTarget(abilityControllerId, permanent.getId(), null, game) && !target.getTargets().contains(permanent.getId())) {
                    // stop to add targets if not needed and outcome is no advantage for AI player
                    if (target.getNumberOfTargets() == target.getTargets().size()) {
                        if (outcome.isGood() && hasOpponent(permanent.getControllerId(), game)) {
                            return true;
                        }
                        if (!outcome.isGood() && !hasOpponent(permanent.getControllerId(), game)) {
                            return true;
                        }
                    }
                    // add the target
                    target.add(permanent.getId(), game);
                    if (target.doneChosing()) {
                        return true;
                    }
                }
            }
            return target.isChosen();
        }

        if (target.getOriginalTarget() instanceof TargetCardInHand
                || (target.getZone() == Zone.HAND && (target.getOriginalTarget() instanceof TargetCard))) {
            List<Card> cards = new ArrayList<>();
            for (UUID cardId : target.possibleTargets(this.getId(), source, game)) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    cards.add(card);
                }
            }
            while ((outcome.isGood() ? target.getTargets().size() < target.getMaxNumberOfTargets() : !target.isChosen())
                    && !cards.isEmpty()) {
                Card pick = pickTarget(abilityControllerId, cards, outcome, target, null, game);
                if (pick != null) {
                    target.addTarget(pick.getId(), null, game);
                    cards.remove(pick);
                }
            }
            return target.isChosen();
        }

        if (target.getOriginalTarget() instanceof TargetAnyTarget) {
            List<Permanent> targets;
            TargetAnyTarget origTarget = (TargetAnyTarget) target.getOriginalTarget();
            if (outcome.isGood()) {
                targets = threats(abilityControllerId, source, ((FilterCreaturePlayerOrPlaneswalker) origTarget.getFilter()).getPermanentFilter(), game, target.getTargets());
            } else {
                targets = threats(randomOpponentId, source, ((FilterCreaturePlayerOrPlaneswalker) origTarget.getFilter()).getPermanentFilter(), game, target.getTargets());
            }
            for (Permanent permanent : targets) {
                List<UUID> alreadyTargetted = target.getTargets();
                if (target.canTarget(abilityControllerId, permanent.getId(), null, game)) {
                    if (alreadyTargetted != null && !alreadyTargetted.contains(permanent.getId())) {
                        target.add(permanent.getId(), game);
                        return true;
                    }
                }
            }
            if (outcome.isGood()) {
                if (target.canTarget(abilityControllerId, abilityControllerId, null, game)) {
                    target.add(abilityControllerId, game);
                    return true;
                }
            } else if (target.canTarget(abilityControllerId, randomOpponentId, null, game)) {
                target.add(randomOpponentId, game);
                return true;
            }
            if (!required) {
                return false;
            }
        }

        if (target.getOriginalTarget() instanceof TargetCreatureOrPlayer) {
            List<Permanent> targets;
            TargetCreatureOrPlayer origTarget = (TargetCreatureOrPlayer) target.getOriginalTarget();
            if (outcome.isGood()) {
                targets = threats(abilityControllerId, source, ((FilterCreatureOrPlayer) origTarget.getFilter()).getCreatureFilter(), game, target.getTargets());
            } else {
                targets = threats(randomOpponentId, source, ((FilterCreatureOrPlayer) origTarget.getFilter()).getCreatureFilter(), game, target.getTargets());
            }
            for (Permanent permanent : targets) {
                List<UUID> alreadyTargeted = target.getTargets();
                if (target.canTarget(abilityControllerId, permanent.getId(), null, game)) {
                    if (alreadyTargeted != null && !alreadyTargeted.contains(permanent.getId())) {
                        target.add(permanent.getId(), game);
                        return true;
                    }
                }
            }
            if (outcome.isGood()) {
                if (target.canTarget(abilityControllerId, abilityControllerId, null, game)) {
                    target.add(abilityControllerId, game);
                    return true;
                }
            } else if (target.canTarget(abilityControllerId, randomOpponentId, null, game)) {
                target.add(randomOpponentId, game);
                return true;
            }
            if (!required) {
                return false;
            }
        }

        if (target.getOriginalTarget() instanceof TargetPermanentOrPlayer) {
            List<Permanent> targets;
            TargetPermanentOrPlayer origTarget = (TargetPermanentOrPlayer) target.getOriginalTarget();
            List<Permanent> ownedTargets = threats(abilityControllerId, source, ((FilterPermanentOrPlayer) origTarget.getFilter()).getPermanentFilter(), game, target.getTargets());
            List<Permanent> opponentTargets = threats(randomOpponentId, source, ((FilterPermanentOrPlayer) origTarget.getFilter()).getPermanentFilter(), game, target.getTargets());
            if (outcome.isGood()) {
                targets = ownedTargets;
            } else {
                targets = opponentTargets;
            }
            for (Permanent permanent : targets) {
                List<UUID> alreadyTargeted = target.getTargets();
                if (target.canTarget(abilityControllerId, permanent.getId(), null, game)) {
                    if (alreadyTargeted != null && !alreadyTargeted.contains(permanent.getId())) {
                        target.add(permanent.getId(), game);
                        return true;
                    }
                }
            }
            if (outcome.isGood()) {
                if (target.canTarget(abilityControllerId, abilityControllerId, null, game)) {
                    target.add(abilityControllerId, game);
                    return true;
                }
            } else if (target.canTarget(abilityControllerId, randomOpponentId, null, game)) {
                target.add(randomOpponentId, game);
                return true;
            }
            if (!target.isRequired(sourceId, game) || target.getNumberOfTargets() == 0) {
                return false;
            }
            if (target.canTarget(abilityControllerId, randomOpponentId, null, game)) {
                target.add(randomOpponentId, game);
                return true;
            }
            if (target.canTarget(abilityControllerId, abilityControllerId, null, game)) {
                target.add(abilityControllerId, game);
                return true;
            }
            if (outcome.isGood()) { // no other valid targets so use a permanent
                targets = opponentTargets;
            } else {
                targets = ownedTargets;
            }
            for (Permanent permanent : targets) {
                List<UUID> alreadyTargeted = target.getTargets();
                if (target.canTarget(abilityControllerId, permanent.getId(), null, game)) {
                    if (alreadyTargeted != null && !alreadyTargeted.contains(permanent.getId())) {
                        target.add(permanent.getId(), game);
                        return true;
                    }
                }
            }
            return false;
        }

        if (target.getOriginalTarget() instanceof TargetCardInGraveyard
                || (target.getZone() == Zone.GRAVEYARD && (target.getOriginalTarget() instanceof TargetCard))) {
            List<Card> cards = new ArrayList<>();
            for (Player player : game.getPlayers().values()) {
                for (Card card : player.getGraveyard().getCards(game)) {
                    if (target.canTarget(abilityControllerId, card.getId(), null, game)) {
                        cards.add(card);
                    }
                }
            }

            // exile cost workaround: exile is bad, but exile from graveyard in most cases is good (more exiled -- more good things you get, e.g. delve's pay)
            boolean isRealGood = outcome.isGood() || outcome == Outcome.Exile;
            while ((isRealGood ? target.getTargets().size() < target.getMaxNumberOfTargets() : !target.isChosen())
                    && !cards.isEmpty()) {
                Card pick = pickTarget(abilityControllerId, cards, outcome, target, null, game);
                if (pick != null) {
                    target.addTarget(pick.getId(), null, game);
                    cards.remove(pick);
                } else {
                    break;
                }
            }

            return target.isChosen();
        }

        if (target.getOriginalTarget() instanceof TargetCardInYourGraveyard
                || target.getOriginalTarget() instanceof TargetCardInASingleGraveyard) {
            List<UUID> alreadyTargeted = target.getTargets();
            TargetCard originalTarget = (TargetCard) target.getOriginalTarget();
            List<Card> cards = new ArrayList<>(game.getPlayer(abilityControllerId).getGraveyard().getCards(originalTarget.getFilter(), game));
            while (!cards.isEmpty()) {
                Card card = pickTarget(abilityControllerId, cards, outcome, target, null, game);
                if (card != null && alreadyTargeted != null && !alreadyTargeted.contains(card.getId())) {
                    target.add(card.getId(), game);
                    if (target.isChosen()) {
                        return true;
                    }
                }
            }
            return false;
        }

        if (target.getOriginalTarget() instanceof TargetCardInExile) {
            List<UUID> alreadyTargeted = target.getTargets();

            FilterCard filter = null;
            if (target.getOriginalTarget().getFilter() instanceof FilterCard) {
                filter = (FilterCard) target.getOriginalTarget().getFilter();
            }
            if (filter == null) {
                throw new IllegalStateException("Unsupported exile target filter in computer's choose method: "
                        + target.getOriginalTarget().getClass().getCanonicalName());
            }

            List<Card> cards = game.getExile().getCards(filter, game);
            while (!cards.isEmpty()) {
                Card card = pickTarget(abilityControllerId, cards, outcome, target, null, game);
                if (card != null && alreadyTargeted != null && !alreadyTargeted.contains(card.getId())) {
                    target.add(card.getId(), game);
                    if (target.isChosen()) {
                        return true;
                    }
                }
            }
            return false;
        }

        if (target.getOriginalTarget() instanceof TargetSource) {
            Set<UUID> targets;
            targets = target.possibleTargets(abilityControllerId, source, game);
            for (UUID targetId : targets) {
                MageObject targetObject = game.getObject(targetId);
                if (targetObject != null) {
                    List<UUID> alreadyTargeted = target.getTargets();
                    if (target.canTarget(abilityControllerId, targetObject.getId(), null, game)) {
                        if (alreadyTargeted != null && !alreadyTargeted.contains(targetObject.getId())) {
                            target.add(targetObject.getId(), game);
                            return true;
                        }
                    }
                }
            }
            if (!required) {
                return false;
            }
            throw new IllegalStateException("TargetSource wasn't handled in computer's choose method: " + target.getClass().getCanonicalName());
        }

        if (target.getOriginalTarget() instanceof TargetPermanentOrSuspendedCard) {
            Cards cards = new CardsImpl(possibleTargets);
            List<Card> possibleCards = new ArrayList<>(cards.getCards(game));
            while (!target.isChosen() && !possibleCards.isEmpty()) {
                Card pick = pickTarget(abilityControllerId, possibleCards, outcome, target, null, game);
                if (pick != null) {
                    target.addTarget(pick.getId(), null, game);
                    possibleCards.remove(pick);
                }
            }
            return target.isChosen();
        }

        throw new IllegalStateException("Target wasn't handled in computer's choose method: " + target.getClass().getCanonicalName());
    } //end of choose method

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        if (log.isDebugEnabled()) {
            log.debug("chooseTarget: " + outcome.toString() + ':' + target.toString());
        }

        // target - real target, make all changes and add targets to it
        // target.getOriginalTarget() - copy spell effect replaces original target with TargetWithAdditionalFilter
        // use originalTarget to get filters and target class info
        // source can be null (as example: legendary rule permanent selection)
        UUID sourceId = source != null ? source.getSourceId() : null;

        // sometimes a target selection can be made from a player that does not control the ability
        UUID abilityControllerId = playerId;
        if (target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }

        boolean required = target.isRequired(sourceId, game);
        Set<UUID> possibleTargets = target.possibleTargets(abilityControllerId, source, game);
        if (possibleTargets.isEmpty() || target.getTargets().size() >= target.getNumberOfTargets()) {
            required = false;
        }

        List<Permanent> goodList = new ArrayList<>();
        List<Permanent> badList = new ArrayList<>();
        List<Permanent> allList = new ArrayList<>();

        // TODO: improve to process multiple opponents instead random
        UUID randomOpponentId;
        if (target.getTargetController() != null) {
            randomOpponentId = getRandomOpponent(target.getTargetController(), game);
        } else if (source != null && source.getControllerId() != null) {
            randomOpponentId = getRandomOpponent(source.getControllerId(), game);
        } else {
            randomOpponentId = getRandomOpponent(playerId, game);
        }

        if (target.getOriginalTarget() instanceof TargetPlayer) {
            return setTargetPlayer(outcome, target, source, abilityControllerId, randomOpponentId, game, required);
        }

        // Angel of Serenity trigger
        if (target.getOriginalTarget() instanceof TargetCardInGraveyardBattlefieldOrStack) {
            Cards cards = new CardsImpl(possibleTargets);
            List<Card> possibleCards = new ArrayList<>(cards.getCards(game));
            for (Card card : possibleCards) {
                // check permanents first; they have more intrinsic worth
                if (card instanceof Permanent) {
                    Permanent p = ((Permanent) card);
                    if (outcome.isGood()
                            && p.isControlledBy(abilityControllerId)) {
                        if (target.canTarget(abilityControllerId, p.getId(), source, game)) {
                            if (target.getTargets().size() >= target.getMaxNumberOfTargets()) {
                                break;
                            }
                            target.addTarget(p.getId(), source, game);
                        }
                    }
                    if (!outcome.isGood()
                            && !p.isControlledBy(abilityControllerId)) {
                        if (target.canTarget(abilityControllerId, p.getId(), source, game)) {
                            if (target.getTargets().size() >= target.getMaxNumberOfTargets()) {
                                break;
                            }
                            target.addTarget(p.getId(), source, game);
                        }
                    }
                }
                // check the graveyards last
                if (game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                    if (outcome.isGood()
                            && card.isOwnedBy(abilityControllerId)) {
                        if (target.canTarget(abilityControllerId, card.getId(), source, game)) {
                            if (target.getTargets().size() >= target.getMaxNumberOfTargets()) {
                                break;
                            }
                            target.addTarget(card.getId(), source, game);
                        }
                    }
                    if (!outcome.isGood()
                            && !card.isOwnedBy(abilityControllerId)) {
                        if (target.canTarget(abilityControllerId, card.getId(), source, game)) {
                            if (target.getTargets().size() >= target.getMaxNumberOfTargets()) {
                                break;
                            }
                            target.addTarget(card.getId(), source, game);
                        }
                    }
                }
            }
            return target.isChosen();
        }

        if (target.getOriginalTarget() instanceof TargetDiscard
                || target.getOriginalTarget() instanceof TargetCardInHand) {
            if (outcome.isGood()) {
                // good
                Cards cards = new CardsImpl(possibleTargets);
                List<Card> cardsInHand = new ArrayList<>(cards.getCards(game));
                while (!target.isChosen()
                        && !cardsInHand.isEmpty()
                        && target.getMaxNumberOfTargets() > target.getTargets().size()) {
                    Card card = pickBestCard(cardsInHand, null, target, source, game);
                    if (card != null) {
                        if (target.canTarget(abilityControllerId, card.getId(), source, game)) {
                            target.addTarget(card.getId(), source, game);
                            cardsInHand.remove(card);
                            if (target.isChosen()) {
                                return true;
                            }
                        }
                    }
                }
            } else {
                // bad
                findPlayables(game);
                for (Card card : unplayable.values()) {
                    if (possibleTargets.contains(card.getId())
                            && target.canTarget(abilityControllerId, card.getId(), source, game)) {
                        target.addTarget(card.getId(), source, game);
                        if (target.isChosen()) {
                            return true;
                        }
                    }
                }
                if (!hand.isEmpty()) {
                    for (Card card : hand.getCards(game)) {
                        if (possibleTargets.contains(card.getId())
                                && target.canTarget(abilityControllerId, card.getId(), source, game)) {
                            target.addTarget(card.getId(), source, game);
                            if (target.isChosen()) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

        if (target.getOriginalTarget() instanceof TargetControlledPermanent) {
            TargetControlledPermanent origTarget = (TargetControlledPermanent) target.getOriginalTarget();
            List<Permanent> targets;
            targets = threats(abilityControllerId, source, origTarget.getFilter(), game, target.getTargets());
            if (!outcome.isGood()) {
                Collections.reverse(targets);
            }
            for (Permanent permanent : targets) {
                if (target.canTarget(abilityControllerId, permanent.getId(), source, game)) {
                    target.addTarget(permanent.getId(), source, game);
                    if (target.getNumberOfTargets() <= target.getTargets().size() && (!outcome.isGood() || target.getMaxNumberOfTargets() <= target.getTargets().size())) {
                        return true;
                    }
                }
            }
            return target.isChosen();

        }

        // TODO: implemented findBestPlayerTargets
        // TODO: add findBest*Targets for all target types
        if (target.getOriginalTarget() instanceof TargetPermanent) {

            FilterPermanent filter = null;
            if (target.getOriginalTarget().getFilter() instanceof FilterPermanent) {
                filter = (FilterPermanent) target.getOriginalTarget().getFilter();
            }
            if (filter == null) {
                throw new IllegalStateException("Unsupported permanent filter in computer's chooseTarget method: "
                        + target.getOriginalTarget().getClass().getCanonicalName());
            }

            findBestPermanentTargets(outcome, abilityControllerId, sourceId, source, filter,
                    game, target, goodList, badList, allList);

            // use good list all the time and add maximum targets
            for (Permanent permanent : goodList) {
                if (target.canTarget(abilityControllerId, permanent.getId(), source, game)) {
                    if (target.getTargets().size() >= target.getMaxNumberOfTargets()) {
                        break;
                    }
                    target.addTarget(permanent.getId(), source, game);
                }
            }

            // use bad list only on required target and add minimum targets
            if (required) {
                for (Permanent permanent : badList) {
                    if (target.getTargets().size() >= target.getMinNumberOfTargets()) {
                        break;
                    }
                    target.addTarget(permanent.getId(), source, game);
                }
            }
            return target.isChosen();
        }

        if (target.getOriginalTarget() instanceof TargetCreatureOrPlayer) {
            List<Permanent> targets;
            TargetCreatureOrPlayer origTarget = ((TargetCreatureOrPlayer) target.getOriginalTarget());
            if (outcome.isGood()) {
                targets = threats(abilityControllerId, source, ((FilterCreatureOrPlayer) origTarget.getFilter()).getCreatureFilter(), game, target.getTargets());
            } else {
                targets = threats(randomOpponentId, source, ((FilterCreatureOrPlayer) origTarget.getFilter()).getCreatureFilter(), game, target.getTargets());
            }

            if (targets.isEmpty()) {
                if (outcome.isGood()) {
                    if (target.canTarget(abilityControllerId, abilityControllerId, source, game)) {
                        return tryAddTarget(target, abilityControllerId, source, game);
                    }
                } else if (target.canTarget(abilityControllerId, randomOpponentId, source, game)) {
                    return tryAddTarget(target, randomOpponentId, source, game);
                }
            }

            if (targets.isEmpty() && target.isRequired(source)) {
                targets = game.getBattlefield().getActivePermanents(((FilterCreatureOrPlayer) origTarget.getFilter()).getCreatureFilter(), playerId, game);
            }
            for (Permanent permanent : targets) {
                List<UUID> alreadyTargeted = target.getTargets();
                if (target.canTarget(abilityControllerId, permanent.getId(), source, game)) {
                    if (alreadyTargeted != null && !alreadyTargeted.contains(permanent.getId())) {
                        return tryAddTarget(target, permanent.getId(), source, game);
                    }
                }
            }

            if (outcome.isGood()) {
                if (target.canTarget(abilityControllerId, abilityControllerId, source, game)) {
                    return tryAddTarget(target, abilityControllerId, source, game);
                }
            } else if (target.canTarget(abilityControllerId, randomOpponentId, source, game)) {
                return tryAddTarget(target, randomOpponentId, source, game);
            }

            return false;
        }

        if (target.getOriginalTarget() instanceof TargetAnyTarget) {
            List<Permanent> targets;
            TargetAnyTarget origTarget = ((TargetAnyTarget) target.getOriginalTarget());
            if (outcome.isGood()) {
                targets = threats(abilityControllerId, source, ((FilterCreaturePlayerOrPlaneswalker) origTarget.getFilter()).getPermanentFilter(), game, target.getTargets());
            } else {
                targets = threats(randomOpponentId, source, ((FilterCreaturePlayerOrPlaneswalker) origTarget.getFilter()).getPermanentFilter(), game, target.getTargets());
            }

            if (targets.isEmpty()) {
                if (outcome.isGood()) {
                    if (target.canTarget(abilityControllerId, abilityControllerId, source, game)) {
                        return tryAddTarget(target, abilityControllerId, source, game);
                    }
                } else if (target.canTarget(abilityControllerId, randomOpponentId, source, game)) {
                    return tryAddTarget(target, randomOpponentId, source, game);
                }
            }

            if (targets.isEmpty() && required) {
                targets = game.getBattlefield().getActivePermanents(((FilterCreaturePlayerOrPlaneswalker) origTarget.getFilter()).getPermanentFilter(), playerId, game);
            }
            for (Permanent permanent : targets) {
                List<UUID> alreadyTargeted = target.getTargets();
                if (target.canTarget(abilityControllerId, permanent.getId(), source, game)) {
                    if (alreadyTargeted != null && !alreadyTargeted.contains(permanent.getId())) {
                        tryAddTarget(target, permanent.getId(), source, game);
                    }
                }
            }

            if (outcome.isGood()) {
                if (target.canTarget(abilityControllerId, abilityControllerId, source, game)) {
                    return tryAddTarget(target, abilityControllerId, source, game);
                }
            } else if (target.canTarget(abilityControllerId, randomOpponentId, source, game)) {
                return tryAddTarget(target, randomOpponentId, source, game);
            }

            //if (!target.isRequired())
            return false;
        }

        if (target.getOriginalTarget() instanceof TargetPermanentOrPlayer) {
            List<Permanent> targets;
            TargetPermanentOrPlayer origTarget = ((TargetPermanentOrPlayer) target.getOriginalTarget());
            if (outcome.isGood()) {
                targets = threats(abilityControllerId, source, ((FilterPermanentOrPlayer) origTarget.getFilter()).getPermanentFilter(), game, target.getTargets());
            } else {
                targets = threats(randomOpponentId, source, ((FilterPermanentOrPlayer) origTarget.getFilter()).getPermanentFilter(), game, target.getTargets());
            }

            if (targets.isEmpty()) {
                if (outcome.isGood()) {
                    if (target.canTarget(abilityControllerId, abilityControllerId, source, game)) {
                        return tryAddTarget(target, abilityControllerId, source, game);
                    }
                } else if (target.canTarget(abilityControllerId, randomOpponentId, source, game)) {
                    return tryAddTarget(target, randomOpponentId, source, game);
                }
            }

            if (targets.isEmpty() && target.isRequired(source)) {
                targets = game.getBattlefield().getActivePermanents(((FilterPermanentOrPlayer) origTarget.getFilter()).getPermanentFilter(), playerId, game);
            }
            for (Permanent permanent : targets) {
                List<UUID> alreadyTargeted = target.getTargets();
                if (target.canTarget(abilityControllerId, permanent.getId(), source, game)) {
                    if (alreadyTargeted != null && !alreadyTargeted.contains(permanent.getId())) {
                        return tryAddTarget(target, permanent.getId(), source, game);
                    }
                }
            }
        }

        if (target.getOriginalTarget() instanceof TargetPlayerOrPlaneswalker
                || target.getOriginalTarget() instanceof TargetOpponentOrPlaneswalker) {
            List<Permanent> targets;
            TargetPermanentOrPlayer origTarget = ((TargetPermanentOrPlayer) target.getOriginalTarget());

            // TODO: in multiplayer game there many opponents - if random opponents don't have targets then AI must use next opponent, but it skips
            //  (e.g. you randomOpponentId must be replaced by List<UUID> randomOpponents)
            // normal cycle (good for you, bad for opponents)
            // possible good/bad permanents
            if (outcome.isGood()) {
                targets = threats(abilityControllerId, source, ((FilterPermanentOrPlayer) target.getFilter()).getPermanentFilter(), game, target.getTargets());
            } else {
                targets = threats(randomOpponentId, source, ((FilterPermanentOrPlayer) target.getFilter()).getPermanentFilter(), game, target.getTargets());
            }

            // possible good/bad players
            if (targets.isEmpty()) {
                if (outcome.isGood()) {
                    if (target.canTarget(abilityControllerId, abilityControllerId, source, game)) {
                        return tryAddTarget(target, abilityControllerId, source, game);
                    }
                } else if (target.canTarget(abilityControllerId, randomOpponentId, source, game)) {
                    return tryAddTarget(target, randomOpponentId, source, game);
                }
            }

            // can't find targets (e.g. effect is bad, but you need take targets from yourself)
            if (targets.isEmpty() && required) {
                targets = game.getBattlefield().getActivePermanents(origTarget.getFilterPermanent(), playerId, game);
            }

            // try target permanent
            for (Permanent permanent : targets) {
                List<UUID> alreadyTargeted = target.getTargets();
                if (target.canTarget(abilityControllerId, permanent.getId(), source, game)) {
                    if (alreadyTargeted != null && !alreadyTargeted.contains(permanent.getId())) {
                        return tryAddTarget(target, permanent.getId(), source, game);
                    }
                }
            }

            // try target player as normal
            if (outcome.isGood()) {
                if (target.canTarget(abilityControllerId, abilityControllerId, source, game)) {
                    return tryAddTarget(target, abilityControllerId, source, game);
                }
            } else if (target.canTarget(abilityControllerId, randomOpponentId, source, game)) {
                return tryAddTarget(target, randomOpponentId, source, game);
            }

            // try target player as bad (bad on itself, good on opponent)
            for (UUID opponentId : game.getOpponents(abilityControllerId)) {
                if (target.canTarget(abilityControllerId, opponentId, source, game)) {
                    return tryAddTarget(target, opponentId, source, game);
                }
            }
            if (target.canTarget(abilityControllerId, abilityControllerId, source, game)) {
                return tryAddTarget(target, abilityControllerId, source, game);
            }

            return false;
        }

        if (target.getOriginalTarget() instanceof TargetCardInGraveyard) {
            List<Card> cards = new ArrayList<>();
            for (Player player : game.getPlayers().values()) {
                cards.addAll(player.getGraveyard().getCards(game));
            }
            Card card = pickTarget(abilityControllerId, cards, outcome, target, source, game);
            if (card != null) {
                return tryAddTarget(target, card.getId(), source, game);
            }
            //if (!target.isRequired())
            return false;
        }

        if (target.getOriginalTarget() instanceof TargetCardInLibrary) {
            List<Card> cards = new ArrayList<>(game.getPlayer(abilityControllerId).getLibrary().getCards(game));
            Card card = pickTarget(abilityControllerId, cards, outcome, target, source, game);
            if (card != null) {
                return tryAddTarget(target, card.getId(), source, game);
            }
            return false;
        }

        if (target.getOriginalTarget() instanceof TargetCardInYourGraveyard) {
            List<Card> cards = new ArrayList<>(game.getPlayer(abilityControllerId).getGraveyard().getCards((FilterCard) target.getFilter(), game));
            while (!target.isChosen() && !cards.isEmpty()) {
                Card card = pickTarget(abilityControllerId, cards, outcome, target, source, game);
                if (card != null) {
                    target.addTarget(card.getId(), source, game);
                    cards.remove(card); // pickTarget don't remove cards (only on second+ tries)
                }
            }
            return target.isChosen();
        }

        if (target.getOriginalTarget() instanceof TargetSpell
                || target.getOriginalTarget() instanceof TargetStackObject) {
            if (!game.getStack().isEmpty()) {
                for (StackObject o : game.getStack()) {
                    if (o instanceof Spell
                            && !source.getId().equals(o.getStackAbility().getId())
                            && target.canTarget(abilityControllerId, o.getStackAbility().getId(), source, game)) {
                        return tryAddTarget(target, o.getId(), source, game);
                    }
                }
            }
            return false;
        }

        if (target.getOriginalTarget() instanceof TargetSpellOrPermanent) {
            // TODO: Also check if a spell should be selected
            TargetSpellOrPermanent origTarget = (TargetSpellOrPermanent) target.getOriginalTarget();
            List<Permanent> targets;
            boolean outcomeTargets = true;
            if (outcome.isGood()) {
                targets = threats(abilityControllerId, source, origTarget.getPermanentFilter(), game, target.getTargets());
            } else {
                targets = threats(randomOpponentId, source, origTarget.getPermanentFilter(), game, target.getTargets());
            }
            if (targets.isEmpty() && required) {
                targets = threats(null, source, origTarget.getPermanentFilter(), game, target.getTargets());
                Collections.reverse(targets);
                outcomeTargets = false;
            }
            for (Permanent permanent : targets) {
                if (target.canTarget(abilityControllerId, permanent.getId(), source, game)) {
                    target.addTarget(permanent.getId(), source, game);
                    if (!outcomeTargets || target.getMaxNumberOfTargets() <= target.getTargets().size()) {
                        return true;
                    }
                }
            }
            if (!game.getStack().isEmpty()) {
                for (StackObject stackObject : game.getStack()) {
                    if (stackObject instanceof Spell && source != null && !source.getId().equals(stackObject.getStackAbility().getId())) {
                        if (target.getFilter().match(stackObject, game)) {
                            return tryAddTarget(target, stackObject.getId(), source, game);
                        }
                    }
                }
            }
            return false;
        }

        if (target.getOriginalTarget() instanceof TargetCardInOpponentsGraveyard) {
            List<Card> cards = new ArrayList<>();
            for (UUID uuid : game.getOpponents(abilityControllerId)) {
                Player player = game.getPlayer(uuid);
                if (player != null) {
                    cards.addAll(player.getGraveyard().getCards(game));
                }
            }
            Card card = pickTarget(abilityControllerId, cards, outcome, target, source, game);
            if (card != null) {
                return tryAddTarget(target, card.getId(), source, game);
            }
            //if (!target.isRequired())
            return false;
        }

        if (target.getOriginalTarget() instanceof TargetDefender) {
            UUID randomDefender = RandomUtil.randomFromCollection(possibleTargets);
            target.addTarget(randomDefender, source, game);
            return target.isChosen();
        }

        if (target.getOriginalTarget() instanceof TargetCardInASingleGraveyard) {
            List<Card> cards = new ArrayList<>();
            for (Player player : game.getPlayers().values()) {
                cards.addAll(player.getGraveyard().getCards(game));
            }
            while (!target.isChosen() && !cards.isEmpty()) {
                Card pick = pickTarget(abilityControllerId, cards, outcome, target, source, game);
                if (pick != null) {
                    target.addTarget(pick.getId(), source, game);
                    cards.remove(pick); // pickTarget don't remove cards (only on second+ tries)
                }
            }
            return target.isChosen();
        }

        if (target.getOriginalTarget() instanceof TargetCardInExile) {

            FilterCard filter = null;
            if (target.getOriginalTarget().getFilter() instanceof FilterCard) {
                filter = (FilterCard) target.getOriginalTarget().getFilter();
            }
            if (filter == null) {
                throw new IllegalStateException("Unsupported exile target filter in computer's chooseTarget method: "
                        + target.getOriginalTarget().getClass().getCanonicalName());
            }

            List<Card> cards = new ArrayList<>();
            for (UUID uuid : target.possibleTargets(source.getControllerId(), source, game)) {
                Card card = game.getCard(uuid);
                if (card != null && game.getState().getZone(card.getId()) == Zone.EXILED) {
                    cards.add(card);
                }
            }
            while (!target.isChosen() && !cards.isEmpty()) {
                Card pick = pickTarget(abilityControllerId, cards, outcome, target, source, game);
                if (pick != null) {
                    target.addTarget(pick.getId(), source, game);
                    cards.remove(pick); // pickTarget don't remove cards (only on second+ tries)
                }
            }
            return target.isChosen();
        }

        if (target.getOriginalTarget() instanceof TargetActivatedAbility) {
            List<StackObject> stackObjects = new ArrayList<>();
            for (UUID uuid : target.possibleTargets(source.getControllerId(), source, game)) {
                StackObject stackObject = game.getStack().getStackObject(uuid);
                if (stackObject != null) {
                    stackObjects.add(stackObject);
                }
            }
            while (!target.isChosen() && !stackObjects.isEmpty()) {
                StackObject pick = stackObjects.get(0);
                if (pick != null) {
                    target.addTarget(pick.getId(), source, game);
                    stackObjects.remove(0);
                }
            }
            return target.isChosen();
        }

        if (target.getOriginalTarget() instanceof TargetCardInGraveyardBattlefieldOrStack) {
            List<Card> cards = new ArrayList<>();
            for (Player player : game.getPlayers().values()) {
                cards.addAll(player.getGraveyard().getCards(game));
                cards.addAll(game.getBattlefield().getAllActivePermanents(new FilterPermanent(), player.getId(), game));
            }
            Card card = pickTarget(abilityControllerId, cards, outcome, target, source, game);
            if (card != null) {
                return tryAddTarget(target, card.getId(), source, game);
            }
        }

        if (target.getOriginalTarget() instanceof TargetPermanentOrSuspendedCard) {
            Cards cards = new CardsImpl(possibleTargets);
            List<Card> possibleCards = new ArrayList<>(cards.getCards(game));
            while (!target.isChosen() && !possibleCards.isEmpty()) {
                Card pick = pickTarget(abilityControllerId, possibleCards, outcome, target, source, game);
                if (pick != null) {
                    target.addTarget(pick.getId(), source, game);
                    possibleCards.remove(pick);
                }
            }
            return target.isChosen();
        }

        throw new IllegalStateException("Target wasn't handled in computer's chooseTarget method: " + target.getClass().getCanonicalName());
    } //end of chooseTarget method

    protected Card pickTarget(UUID abilityControllerId, List<Card> cards, Outcome outcome, Target target, Ability source, Game game) {
        Card card;
        while (!cards.isEmpty()) {

            if (outcome.isGood()) {
                card = pickBestCard(cards, null, target, source, game);
            } else {
                card = pickWorstCard(cards, null, target, source, game);
            }
            if (!target.getTargets().contains(card.getId())) {
                if (source != null) {
                    if (target.canTarget(abilityControllerId, card.getId(), source, game)) {
                        return card;
                    }
                } else {
                    return card;
                }
            }
            cards.remove(card);
        }
        return null;
    }

    @Override
    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
        // TODO: make same code for chooseTarget (without filter and target type dependence)
        if (log.isDebugEnabled()) {
            log.debug("chooseTarget: " + outcome.toString() + ':' + target.toString());
        }

        UUID sourceId = source != null ? source.getSourceId() : null;

        // process multiple opponents by random
        List<UUID> opponents;
        if (target.getTargetController() != null) {
            opponents = new ArrayList<>(game.getOpponents(target.getTargetController()));
        } else if (source != null && source.getControllerId() != null) {
            opponents = new ArrayList<>(game.getOpponents(source.getControllerId()));
        } else {
            opponents = new ArrayList<>(game.getOpponents(getId()));
        }
        Collections.shuffle(opponents);

        List<Permanent> targets;

        // ONE KILL PRIORITY: player -> planeswalker -> creature
        if (outcome == Outcome.Damage) {
            // player kill
            for (UUID opponentId : opponents) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null
                        && target.canTarget(getId(), opponentId, source, game)
                        && opponent.getLife() <= target.getAmountRemaining()) {
                    return tryAddTarget(target, opponentId, opponent.getLife(), source, game);
                }
            }

            // permanents kill
            for (UUID opponentId : opponents) {
                targets = threats(opponentId, source, StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER_A, game, target.getTargets());

                // planeswalker kill
                for (Permanent permanent : targets) {
                    if (permanent.isPlaneswalker(game) && target.canTarget(getId(), permanent.getId(), source, game)) {
                        int loy = permanent.getCounters(game).getCount(CounterType.LOYALTY);
                        if (loy <= target.getAmountRemaining()) {
                            return tryAddTarget(target, permanent.getId(), loy, source, game);
                        }
                    }
                }

                // creature kill
                for (Permanent permanent : targets) {
                    if (permanent.isCreature(game) && target.canTarget(getId(), permanent.getId(), source, game)) {
                        if (permanent.getToughness().getValue() <= target.getAmountRemaining()) {
                            return tryAddTarget(target, permanent.getId(), permanent.getToughness().getValue(), source, game);
                        }
                    }
                }
            }
        }

        // NORMAL PRIORITY: planeswalker -> player -> creature
        // own permanents will be checked multiple times... that's ok
        for (UUID opponentId : opponents) {
            if (outcome.isGood()) {
                targets = threats(getId(), source, StaticFilters.FILTER_PERMANENT, game, target.getTargets());
            } else {
                targets = threats(opponentId, source, StaticFilters.FILTER_PERMANENT, game, target.getTargets());
            }

            // planeswalkers
            for (Permanent permanent : targets) {
                if (permanent.isPlaneswalker(game) && target.canTarget(getId(), permanent.getId(), source, game)) {
                    return tryAddTarget(target, permanent.getId(), target.getAmountRemaining(), source, game);
                }
            }

            // players
            if (outcome.isGood() && target.canTarget(getId(), getId(), source, game)) {
                return tryAddTarget(target, getId(), target.getAmountRemaining(), source, game);
            }
            if (!outcome.isGood() && target.canTarget(getId(), opponentId, source, game)) {
                return tryAddTarget(target, opponentId, target.getAmountRemaining(), source, game);
            }

            // creature
            for (Permanent permanent : targets) {
                if (permanent.isCreature(game) && target.canTarget(getId(), permanent.getId(), source, game)) {
                    return tryAddTarget(target, permanent.getId(), target.getAmountRemaining(), source, game);
                }
            }
        }

        // BAD PRIORITY, e.g. need bad target on yourself or good target on opponent
        // priority: creature (non killable, killable) -> planeswalker -> player
        if (!target.isRequired(sourceId, game)) {
            return false;
        }
        for (UUID opponentId : opponents) {
            if (!outcome.isGood()) {
                // bad on yourself, uses weakest targets
                targets = threats(getId(), source, StaticFilters.FILTER_PERMANENT, game, target.getTargets(), false);
            } else {
                targets = threats(opponentId, source, StaticFilters.FILTER_PERMANENT, game, target.getTargets(), false);
            }

            // creatures - non killable (TODO: add extra skill checks like undestructeable)
            for (Permanent permanent : targets) {
                if (permanent.isCreature(game) && target.canTarget(getId(), permanent.getId(), source, game)) {
                    int safeDamage = Math.min(permanent.getToughness().getValue() - 1, target.getAmountRemaining());
                    if (safeDamage > 0) {
                        return tryAddTarget(target, permanent.getId(), safeDamage, source, game);
                    }
                }
            }

            // creatures - all
            for (Permanent permanent : targets) {
                if (permanent.isCreature(game) && target.canTarget(getId(), permanent.getId(), source, game)) {
                    return tryAddTarget(target, permanent.getId(), target.getAmountRemaining(), source, game);
                }
            }

            // planeswalkers
            for (Permanent permanent : targets) {
                if (permanent.isPlaneswalker(game) && target.canTarget(getId(), permanent.getId(), source, game)) {
                    return tryAddTarget(target, permanent.getId(), target.getAmountRemaining(), source, game);
                }
            }
        }
        // players
        for (UUID opponentId : opponents) {
            if (target.canTarget(getId(), getId(), source, game)) {
                return tryAddTarget(target, getId(), target.getAmountRemaining(), source, game);
            } else if (target.canTarget(getId(), opponentId, source, game)) {
                return tryAddTarget(target, opponentId, target.getAmountRemaining(), source, game);
            }
        }

        // it's ok on no targets available
        log.warn("No proper AI target handling or can't find permanents/cards to target: " + target.getClass().getName());
        return false;
    }

    @Override
    public boolean priority(Game game) {
        game.resumeTimer(getTurnControlledBy());
        log.debug("priority");
        boolean result = priorityPlay(game);
        game.pauseTimer(getTurnControlledBy());
        return result;
    }

    private boolean priorityPlay(Game game) {
        UUID opponentId = game.getOpponents(playerId).iterator().next();
        if (game.isActivePlayer(playerId)) {
            if (game.isMainPhase() && game.getStack().isEmpty()) {
                playLand(game);
            }
            switch (game.getTurn().getStepType()) {
                case UPKEEP:
                    findPlayables(game);
                    break;
                case DRAW:
                    logState(game);
                    break;
                case PRECOMBAT_MAIN:
                    findPlayables(game);
                    if (!playableAbilities.isEmpty()) {
                        for (ActivatedAbility ability : playableAbilities) {
                            if (ability.canActivate(playerId, game).canActivate()) {
                                if (ability.getEffects().hasOutcome(ability, Outcome.PutLandInPlay)) {
                                    if (this.activateAbility(ability, game)) {
                                        return true;
                                    }
                                }
                                if (ability.getEffects().hasOutcome(ability, Outcome.PutCreatureInPlay)) {
                                    if (getOpponentBlockers(opponentId, game).size() <= 1) {
                                        if (this.activateAbility(ability, game)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case DECLARE_BLOCKERS:
                    findPlayables(game);
                    playRemoval(game.getCombat().getBlockers(), game);
                    playDamage(game.getCombat().getBlockers(), game);
                    break;
                case END_COMBAT:
                    findPlayables(game);
                    playDamage(game.getCombat().getBlockers(), game);
                    break;
                case POSTCOMBAT_MAIN:
                    findPlayables(game);
                    if (game.getStack().isEmpty()) {
                        if (!playableNonInstant.isEmpty()) {
                            for (Card card : playableNonInstant) {
                                if (card.getSpellAbility().canActivate(playerId, game).canActivate()) {
                                    if (this.activateAbility(card.getSpellAbility(), game)) {
                                        return true;
                                    }
                                }
                            }
                        }
                        if (!playableAbilities.isEmpty()) {
                            for (ActivatedAbility ability : playableAbilities) {
                                if (ability.canActivate(playerId, game).canActivate()) {
                                    if (!(ability.getEffects().get(0) instanceof BecomesCreatureSourceEffect)) {
                                        if (this.activateAbility(ability, game)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
            }
        } else {
            //respond to opponent events
            switch (game.getTurn().getStepType()) {
                case UPKEEP:
                    findPlayables(game);
                    break;
                case DECLARE_ATTACKERS:
                    findPlayables(game);
                    playRemoval(game.getCombat().getAttackers(), game);
                    playDamage(game.getCombat().getAttackers(), game);
                    break;
                case END_COMBAT:
                    findPlayables(game);
                    playDamage(game.getCombat().getAttackers(), game);
                    break;
            }
        }
        pass(game);
        return true;
    } // end priorityPlay method

    protected void playLand(Game game) {
        log.debug("playLand");
        Set<Card> lands = new LinkedHashSet<>();
        for (Card landCard : hand.getCards(new FilterLandCard(), game)) {
            // remove lands that can not be played
            boolean canPlay = false;
            for (Ability ability : landCard.getAbilities(game)) {
                if (ability instanceof PlayLandAbility) {
                    if (!game.getContinuousEffects().preventedByRuleModification(GameEvent.getEvent(GameEvent.EventType.PLAY_LAND, landCard.getId(), ability, playerId), null, game, true)) {
                        canPlay = true;
                    }
                }
            }
            if (canPlay) {
                lands.add(landCard);
            }
        }
        while (!lands.isEmpty() && this.canPlayLand()) {
            if (lands.size() == 1) {
                this.playLand(lands.iterator().next(), game, false);
            } else {
                playALand(lands, game);
            }
        }
    }

    protected void playALand(Set<Card> lands, Game game) {
        log.debug("playALand");
        //play a land that will allow us to play an unplayable
        for (Mana mana : unplayable.keySet()) {
            for (Card card : lands) {
                for (ActivatedManaAbilityImpl ability : card.getAbilities(game).getActivatedManaAbilities(Zone.BATTLEFIELD)) {
                    for (Mana netMana : ability.getNetMana(game)) {
                        if (netMana.enough(mana)) {
                            this.playLand(card, game, false);
                            lands.remove(card);
                            return;
                        }
                    }
                }
            }
        }
        //play a land that will get us closer to playing an unplayable
        for (Mana mana : unplayable.keySet()) {
            for (Card card : lands) {
                for (ActivatedManaAbilityImpl ability : card.getAbilities(game).getActivatedManaAbilities(Zone.BATTLEFIELD)) {
                    for (Mana netMana : ability.getNetMana(game)) {
                        if (mana.contains(netMana)) {
                            this.playLand(card, game, false);
                            lands.remove(card);
                            return;
                        }
                    }
                }
            }
        }
        //play first available land
        this.playLand(lands.iterator().next(), game, false);
        lands.remove(lands.iterator().next());
    }

    protected void findPlayables(Game game) {
        playableInstant.clear();
        playableNonInstant.clear();
        unplayable.clear();
        playableAbilities.clear();
        Set<Card> nonLands = hand.getCards(new FilterNonlandCard(), game);
        ManaOptions available = getManaAvailable(game);
//        available.addMana(manaPool.getMana());

        for (Card card : nonLands) {
            ManaOptions options = card.getManaCost().getOptions();
            if (!card.getManaCost().getVariableCosts().isEmpty()) {
                //don't use variable mana costs unless there is at least 3 extra mana for X
                for (Mana option : options) {
                    option.add(Mana.GenericMana(3));
                }
            }
            for (Mana mana : options) {
                for (Mana avail : available) {
                    if (mana.enough(avail)) {
                        SpellAbility ability = card.getSpellAbility();
                        GameEvent castEvent = GameEvent.getEvent(GameEvent.EventType.CAST_SPELL, ability.getId(), ability, playerId);
                        castEvent.setZone(game.getState().getZone(card.getMainCard().getId()));
                        if (ability != null && ability.canActivate(playerId, game).canActivate()
                                && !game.getContinuousEffects().preventedByRuleModification(castEvent, ability, game, true)) {
                            if (card.isInstant(game)
                                    || card.hasAbility(FlashAbility.getInstance(), game)) {
                                playableInstant.add(card);
                            } else {
                                playableNonInstant.add(card);
                            }
                        }
                    } else if (!playableInstant.contains(card) && !playableNonInstant.contains(card)) {
                        unplayable.put(mana.needed(avail), card);
                    }
                }
            }
        }
        // TODO: wtf?! change to player.getPlayable
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            for (ActivatedAbility ability : permanent.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD)) {
                if (!(ability instanceof ActivatedManaAbilityImpl) && ability.canActivate(playerId, game).canActivate()) {
                    if (ability instanceof EquipAbility && permanent.getAttachedTo() != null) {
                        continue;
                    }
                    ManaOptions abilityOptions = ability.getManaCosts().getOptions();
                    if (!ability.getManaCosts().getVariableCosts().isEmpty()) {
                        //don't use variable mana costs unless there is at least 3 extra mana for X
                        for (Mana option : abilityOptions) {
                            option.add(Mana.GenericMana(3));
                        }
                    }
                    if (abilityOptions.isEmpty()) {
                        playableAbilities.add(ability);
                    } else {
                        for (Mana mana : abilityOptions) {
                            for (Mana avail : available) {
                                if (mana.enough(avail)) {
                                    playableAbilities.add(ability);
                                }
                            }
                        }
                    }
                }
            }
        }
        for (Card card : graveyard.getCards(game)) {
            for (ActivatedAbility ability : card.getAbilities(game).getActivatedAbilities(Zone.GRAVEYARD)) {
                if (ability.canActivate(playerId, game).canActivate()) {
                    ManaOptions abilityOptions = ability.getManaCosts().getOptions();
                    if (abilityOptions.isEmpty()) {
                        playableAbilities.add(ability);
                    } else {
                        for (Mana mana : abilityOptions) {
                            for (Mana avail : available) {
                                if (mana.enough(avail)) {
                                    playableAbilities.add(ability);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("findPlayables: " + playableInstant.toString() + "---" + playableNonInstant.toString() + "---" + playableAbilities.toString());
        }
    }

    @Override
    public boolean playMana(Ability ability, ManaCost unpaid, String promptText, Game game) {
        payManaMode = true;
        currentUnpaidMana = unpaid;
        try {
            return playManaHandling(ability, unpaid, game);
        } finally {
            currentUnpaidMana = null;
            payManaMode = false;
        }
    }

    protected boolean playManaHandling(Ability ability, ManaCost unpaid, final Game game) {
//        log.info("paying for " + unpaid.getText());
        ApprovingObject approvingObject = game.getContinuousEffects().asThough(ability.getSourceId(), AsThoughEffectType.SPEND_OTHER_MANA, ability, ability.getControllerId(), game);
        ManaCost cost;
        List<MageObject> producers;
        if (unpaid instanceof ManaCosts) {
            ManaCosts<ManaCost> manaCosts = (ManaCosts<ManaCost>) unpaid;
            cost = manaCosts.get(manaCosts.size() - 1);
            producers = getSortedProducers((ManaCosts) unpaid, game);
        } else {
            cost = unpaid;
            producers = this.getAvailableManaProducers(game);
            producers.addAll(this.getAvailableManaProducersWithCost(game));
        }
        for (MageObject mageObject : producers) {
            // use color producing mana abilities with costs first that produce all color manas that are needed to pay
            // otherwise the computer may not be able to pay the cost for that source
            ManaAbility:
            for (ActivatedManaAbilityImpl manaAbility : getManaAbilitiesSortedByManaCount(mageObject, game)) {
                int colored = 0;
                for (Mana mana : manaAbility.getNetMana(game)) {
                    if (!unpaid.getMana().includesMana(mana)) {
                        continue ManaAbility;
                    }
                    colored += mana.countColored();
                }
                if (colored > 1 && (cost instanceof ColoredManaCost)) {
                    for (Mana netMana : manaAbility.getNetMana(game)) {
                        if (cost.testPay(netMana)) {
                            if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                                continue;
                            }
                            if (approvingObject != null && !canUseAsThoughManaToPayManaCost(cost, ability, netMana, manaAbility, mageObject, game)) {
                                continue;
                            }
                            if (activateAbility(manaAbility, game)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        for (MageObject mageObject : producers) {
            // pay all colored costs first
            for (ActivatedManaAbilityImpl manaAbility : getManaAbilitiesSortedByManaCount(mageObject, game)) {
                if (cost instanceof ColoredManaCost) {
                    for (Mana netMana : manaAbility.getNetMana(game)) {
                        if (cost.testPay(netMana) || approvingObject != null) {
                            if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                                continue;
                            }
                            if (approvingObject != null && !canUseAsThoughManaToPayManaCost(cost, ability, netMana, manaAbility, mageObject, game)) {
                                continue;
                            }
                            if (activateAbility(manaAbility, game)) {
                                return true;
                            }
                        }
                    }
                }
            }
            // pay snow covered mana
            for (ActivatedManaAbilityImpl manaAbility : getManaAbilitiesSortedByManaCount(mageObject, game)) {
                if (cost instanceof SnowManaCost) {
                    for (Mana netMana : manaAbility.getNetMana(game)) {
                        if (cost.testPay(netMana) || approvingObject != null) {
                            if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                                continue;
                            }
                            if (approvingObject != null && !canUseAsThoughManaToPayManaCost(cost, ability, netMana, manaAbility, mageObject, game)) {
                                continue;
                            }
                            if (activateAbility(manaAbility, game)) {
                                return true;
                            }
                        }
                    }
                }
            }
            // then pay hybrid
            for (ActivatedManaAbilityImpl manaAbility : getManaAbilitiesSortedByManaCount(mageObject, game)) {
                if (cost instanceof HybridManaCost) {
                    for (Mana netMana : manaAbility.getNetMana(game)) {
                        if (cost.testPay(netMana) || approvingObject != null) {
                            if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                                continue;
                            }
                            if (approvingObject != null && !canUseAsThoughManaToPayManaCost(cost, ability, netMana, manaAbility, mageObject, game)) {
                                continue;
                            }
                            if (activateAbility(manaAbility, game)) {
                                return true;
                            }
                        }
                    }
                }
            }
            // then pay mono hybrid
            for (ActivatedManaAbilityImpl manaAbility : getManaAbilitiesSortedByManaCount(mageObject, game)) {
                if (cost instanceof MonoHybridManaCost) {
                    for (Mana netMana : manaAbility.getNetMana(game)) {
                        if (cost.testPay(netMana) || approvingObject != null) {
                            if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                                continue;
                            }
                            if (approvingObject != null && !canUseAsThoughManaToPayManaCost(cost, ability, netMana, manaAbility, mageObject, game)) {
                                continue;
                            }
                            if (activateAbility(manaAbility, game)) {
                                return true;
                            }
                        }
                    }
                }
            }
            // pay colorless
            for (ActivatedManaAbilityImpl manaAbility : getManaAbilitiesSortedByManaCount(mageObject, game)) {
                if (cost instanceof ColorlessManaCost) {
                    for (Mana netMana : manaAbility.getNetMana(game)) {
                        if (cost.testPay(netMana) || approvingObject != null) {
                            if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                                continue;
                            }
                            if (approvingObject != null && !canUseAsThoughManaToPayManaCost(cost, ability, netMana, manaAbility, mageObject, game)) {
                                continue;
                            }
                            if (activateAbility(manaAbility, game)) {
                                return true;
                            }
                        }
                    }
                }
            }
            // finally pay generic
            for (ActivatedManaAbilityImpl manaAbility : getManaAbilitiesSortedByManaCount(mageObject, game)) {
                if (cost instanceof GenericManaCost) {
                    for (Mana netMana : manaAbility.getNetMana(game)) {
                        if (cost.testPay(netMana) || approvingObject != null) {
                            if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                                continue;
                            }
                            if (approvingObject != null && !canUseAsThoughManaToPayManaCost(cost, ability, netMana, manaAbility, mageObject, game)) {
                                continue;
                            }
                            if (activateAbility(manaAbility, game)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // pay phyrexian life costs
        if (cost.isPhyrexian()) {
            return cost.pay(ability, game, ability, playerId, false, null) || approvingObject != null;
        }

        // pay special mana like convoke cost (tap for pay)
        // GUI: user see "special" button while pay spell's cost
        // TODO: AI can't prioritize special mana types to pay, e.g. it will use first available
        SpecialAction specialAction = game.getState().getSpecialActions().getControlledBy(this.getId(), true)
                .values().stream().findFirst().orElse(null);
        ManaOptions specialMana = specialAction == null ? null : specialAction.getManaOptions(ability, game, unpaid);
        if (specialMana != null) {
            for (Mana netMana : specialMana) {
                if (cost.testPay(netMana) || approvingObject != null) {
                    if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                        continue;
                    }
                    specialAction.setUnpaidMana(unpaid);
                    if (activateAbility(specialAction, game)) {
                        return true;
                    }
                    // only one time try to pay
                    break;
                }
            }
        }

        return false;
    }

    boolean canUseAsThoughManaToPayManaCost(ManaCost checkCost, Ability abilityToPay, Mana manaOption, Ability manaAbility, MageObject manaProducer, Game game) {
        // asThoughMana can change producing mana type, so you must check it here
        // cause some effects adds additional checks in getAsThoughManaType (example: Draugr Necromancer with snow mana sources)

        // simulate real asThoughMana usage
        ManaPoolItem possiblePoolItem;
        if (manaOption instanceof ConditionalMana) {
            ConditionalMana conditionalNetMana = (ConditionalMana) manaOption;
            possiblePoolItem = new ManaPoolItem(
                    conditionalNetMana,
                    manaAbility.getSourceObject(game),
                    conditionalNetMana.getManaProducerOriginalId() != null ? conditionalNetMana.getManaProducerOriginalId() : manaAbility.getOriginalId()
            );
        } else {
            possiblePoolItem = new ManaPoolItem(
                    manaOption.getRed(),
                    manaOption.getGreen(),
                    manaOption.getBlue(),
                    manaOption.getWhite(),
                    manaOption.getBlack(),
                    manaOption.getGeneric() + manaOption.getColorless(),
                    manaProducer,
                    manaAbility.getOriginalId(),
                    manaOption.getFlag()
            );
        }

        // cost can contains multiple mana types, must check each type (is it possible to pay a cost)
        for (ManaType checkType : ManaUtil.getManaTypesInCost(checkCost)) {
            // affected asThoughMana effect must fit a checkType with pool mana
            ManaType possibleAsThoughPoolManaType = game.getContinuousEffects().asThoughMana(checkType, possiblePoolItem, abilityToPay.getSourceId(), abilityToPay, abilityToPay.getControllerId(), game);
            if (possibleAsThoughPoolManaType == null) {
                continue; // no affected asThough effects
            }
            boolean canPay;
            if (possibleAsThoughPoolManaType == ManaType.COLORLESS) {
                // colorless can be payed by any color from the pool
                canPay = possiblePoolItem.count() > 0;
            } else {
                // colored must be payed by specific color from the pool (AsThough already changed it to fit with mana pool)
                canPay = possiblePoolItem.get(possibleAsThoughPoolManaType) > 0;
            }
            if (canPay) {
                return true;
            }
        }

        return false;
    }

    private Abilities<ActivatedManaAbilityImpl> getManaAbilitiesSortedByManaCount(MageObject mageObject, final Game game) {
        Abilities<ActivatedManaAbilityImpl> manaAbilities = mageObject.getAbilities().getAvailableActivatedManaAbilities(Zone.BATTLEFIELD, playerId, game);
        if (manaAbilities.size() > 1) {
            // Sort mana abilities by number of produced manas, to use ability first that produces most mana (maybe also conditional if possible)
            Collections.sort(manaAbilities, new Comparator<ActivatedManaAbilityImpl>() {
                @Override
                public int compare(ActivatedManaAbilityImpl a1, ActivatedManaAbilityImpl a2) {
                    int a1Max = 0;
                    for (Mana netMana : a1.getNetMana(game)) {
                        if (netMana.count() > a1Max) {
                            a1Max = netMana.count();
                        }
                    }
                    int a2Max = 0;
                    for (Mana netMana : a2.getNetMana(game)) {
                        if (netMana.count() > a2Max) {
                            a2Max = netMana.count();
                        }
                    }
                    return a2Max - a1Max;
                }
            });
        }
        return manaAbilities;
    }

    /**
     * returns a list of Permanents that produce mana sorted by the number of
     * mana the Permanent produces that match the unpaid costs in ascending
     * order
     * <p>
     * the idea is that we should pay costs first from mana producers that
     * produce only one type of mana and save the multi-mana producers for those
     * costs that can't be paid by any other producers
     *
     * @param unpaid - the amount of unpaid mana costs
     * @param game
     * @return List<Permanent>
     */
    private List<MageObject> getSortedProducers(ManaCosts<ManaCost> unpaid, Game game) {
        List<MageObject> unsorted = this.getAvailableManaProducers(game);
        unsorted.addAll(this.getAvailableManaProducersWithCost(game));
        Map<MageObject, Integer> scored = new HashMap<>();
        for (MageObject mageObject : unsorted) {
            int score = 0;
            for (ManaCost cost : unpaid) {
                Abilities:
                for (ActivatedManaAbilityImpl ability : mageObject.getAbilities().getAvailableActivatedManaAbilities(Zone.BATTLEFIELD, playerId, game)) {
                    for (Mana netMana : ability.getNetMana(game)) {
                        if (cost.testPay(netMana)) {
                            score++;
                            break Abilities;
                        }
                    }
                }
            }
            if (score > 0) { // score mana producers that produce other mana types and have other uses higher
                score += mageObject.getAbilities().getAvailableActivatedManaAbilities(Zone.BATTLEFIELD, playerId, game).size();
                score += mageObject.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD).size();
                if (!mageObject.getCardType(game).contains(CardType.LAND)) {
                    score += 2;
                } else if (mageObject.getCardType(game).contains(CardType.CREATURE)) {
                    score += 2;
                }
            }
            scored.put(mageObject, score);
        }
        return sortByValue(scored);
    }

    private List<MageObject> sortByValue(Map<MageObject, Integer> map) {
        List<Entry<MageObject, Integer>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Entry<MageObject, Integer>>() {
            @Override
            public int compare(Entry<MageObject, Integer> o1, Entry<MageObject, Integer> o2) {
                return (o1.getValue().compareTo(o2.getValue()));
            }
        });
        List<MageObject> result = new ArrayList<>();
        for (Entry<MageObject, Integer> entry : list) {
            result.add(entry.getKey());
        }
        return result;
    }

    @Override
    public int announceXMana(int min, int max, int multiplier, String message, Game game, Ability ability) {
        log.debug("announceXMana");
        //TODO: improve this
        int xMin = min * multiplier;
        int xMax = (max == Integer.MAX_VALUE ? max : max * multiplier);
        int numAvailable = getAvailableManaProducers(game).size() - ability.getManaCosts().manaValue();
        if (numAvailable < 0) {
            numAvailable = 0;
        } else {
            if (numAvailable < xMin) {
                numAvailable = xMin;
            }
            if (numAvailable > xMax) {
                numAvailable = xMax;
            }
        }
        return numAvailable;
    }

    @Override
    public int announceXCost(int min, int max, String message, Game game, Ability ability, VariableCost variablCost) {
        log.debug("announceXCost");
        int value = RandomUtil.nextInt(CardUtil.overflowInc(max, 1));
        if (value < min) {
            value = min;
        }
        if (value < max) {
            value++;
        }
        return value;
    }

    @Override
    public void abort() {
        abort = true;
    }

    @Override
    public void skip() {
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, Ability source, Game game) {
        return this.chooseUse(outcome, message, null, null, null, source, game);
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, String secondMessage, String trueText, String falseText, Ability source, Game game) {
        log.debug("chooseUse: " + outcome.isGood());
        // Be proactive! Always use abilities, the evaluation function will decide if it's good or not
        // Otherwise some abilities won't be used by AI like LoseTargetEffect that has "bad" outcome
        // but still is good when targets opponent
        return outcome != Outcome.AIDontUseIt; // Added for Desecration Demon sacrifice ability
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        log.debug("choose 3");
        //TODO: improve this

        // choose creature type
        // TODO: WTF?! Creature types dialog text can changes, need to replace that code
        if (choice.getMessage() != null && (choice.getMessage().equals("Choose creature type") || choice.getMessage().equals("Choose a creature type"))) {
            chooseCreatureType(outcome, choice, game);
        }

        // choose the correct color to pay a spell
        if (outcome == Outcome.PutManaInPool && choice instanceof ChoiceColor && currentUnpaidMana != null) {
            if (currentUnpaidMana.containsColor(ColoredManaSymbol.W) && choice.getChoices().contains("White")) {
                choice.setChoice("White");
                return true;
            }
            if (currentUnpaidMana.containsColor(ColoredManaSymbol.R) && choice.getChoices().contains("Red")) {
                choice.setChoice("Red");
                return true;
            }
            if (currentUnpaidMana.containsColor(ColoredManaSymbol.G) && choice.getChoices().contains("Green")) {
                choice.setChoice("Green");
                return true;
            }
            if (currentUnpaidMana.containsColor(ColoredManaSymbol.U) && choice.getChoices().contains("Blue")) {
                choice.setChoice("Blue");
                return true;
            }
            if (currentUnpaidMana.containsColor(ColoredManaSymbol.B) && choice.getChoices().contains("Black")) {
                choice.setChoice("Black");
                return true;
            }
            if (currentUnpaidMana.getMana().getColorless() > 0 && choice.getChoices().contains("Colorless")) {
                choice.setChoice("Colorless");
                return true;
            }
        }

        // choose by random
        if (!choice.isChosen()) {
            choice.setRandomChoice();
        }

        return true;
    }

    protected boolean chooseCreatureType(Outcome outcome, Choice choice, Game game) {
        if (outcome == Outcome.Detriment) {
            // choose a creature type of opponent on battlefield or graveyard
            for (Permanent permanent : game.getBattlefield().getActivePermanents(this.getId(), game)) {
                if (game.getOpponents(this.getId()).contains(permanent.getControllerId())
                        && permanent.getCardType(game).contains(CardType.CREATURE)
                        && !permanent.getSubtype(game).isEmpty()) {
                    if (choice.getChoices().contains(permanent.getSubtype(game).get(0).toString())) {
                        choice.setChoice(permanent.getSubtype(game).get(0).toString());
                        break;
                    }
                }
            }
            // or in opponent graveyard
            if (!choice.isChosen()) {
                for (UUID opponentId : game.getOpponents(this.getId())) {
                    Player opponent = game.getPlayer(opponentId);
                    for (Card card : opponent.getGraveyard().getCards(game)) {
                        if (card != null && card.getCardType(game).contains(CardType.CREATURE) && !card.getSubtype(game).isEmpty()) {
                            if (choice.getChoices().contains(card.getSubtype(game).get(0).toString())) {
                                choice.setChoice(card.getSubtype(game).get(0).toString());
                                break;
                            }
                        }
                    }
                    if (choice.isChosen()) {
                        break;
                    }
                }
            }
        } else {
            // choose a creature type of hand or library
            for (UUID cardId : this.getHand()) {
                Card card = game.getCard(cardId);
                if (card != null && card.getCardType(game).contains(CardType.CREATURE) && !card.getSubtype(game).isEmpty()) {
                    if (choice.getChoices().contains(card.getSubtype(game).get(0).toString())) {
                        choice.setChoice(card.getSubtype(game).get(0).toString());
                        break;
                    }
                }
            }
            if (!choice.isChosen()) {
                for (UUID cardId : this.getLibrary().getCardList()) {
                    Card card = game.getCard(cardId);
                    if (card != null && card.getCardType(game).contains(CardType.CREATURE) && !card.getSubtype(game).isEmpty()) {
                        if (choice.getChoices().contains(card.getSubtype(game).get(0).toString())) {
                            choice.setChoice(card.getSubtype(game).get(0).toString());
                            break;
                        }
                    }
                }
            }
        }
        return choice.isChosen();
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        if (cards == null || cards.isEmpty()) {
            return target.isRequired(source);
        }

        // sometimes a target selection can be made from a player that does not control the ability
        UUID abilityControllerId = playerId;
        if (target.getTargetController() != null
                && target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }

        // we still use playerId when getting cards even if they don't control the search
        List<Card> cardChoices = new ArrayList<>(cards.getCards(target.getFilter(), playerId, source, game));
        while (!target.doneChosing()) {
            Card card = pickTarget(abilityControllerId, cardChoices, outcome, target, source, game);
            if (card != null) {
                target.addTarget(card.getId(), source, game);
                cardChoices.remove(card);
            } else {
                // We don't have any valid target to choose so stop choosing
                return target.getTargets().size() >= target.getNumberOfTargets();
            }
            if (outcome == Outcome.Neutral && target.getTargets().size() > target.getNumberOfTargets() + (target.getMaxNumberOfTargets() - target.getNumberOfTargets()) / 2) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game) {
        log.debug("choose 2");
        if (cards == null || cards.isEmpty()) {
            return true;
        }

        // sometimes a target selection can be made from a player that does not control the ability
        UUID abilityControllerId = playerId;
        if (target.getTargetController() != null
                && target.getAbilityController() != null) {
            abilityControllerId = target.getAbilityController();
        }

        List<Card> cardChoices = new ArrayList<>(cards.getCards(target.getFilter(), game));
        while (!target.doneChosing()) {
            Card card = pickTarget(abilityControllerId, cardChoices, outcome, target, null, game);
            if (card != null) {
                target.add(card.getId(), game);
                cardChoices.remove(card);
            } else {
                // We don't have any valid target to choose so stop choosing
                return target.getTargets().size() >= target.getNumberOfTargets();
            }
            if (outcome == Outcome.Neutral && target.getTargets().size() > target.getNumberOfTargets() + (target.getMaxNumberOfTargets() - target.getNumberOfTargets()) / 2) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean choosePile(Outcome outcome, String message, List<? extends Card> pile1, List<? extends Card> pile2, Game game) {
        //TODO: improve this
        return true;
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        log.debug("selectAttackers");
        UUID opponentId = game.getCombat().getDefenders().iterator().next();
        Attackers attackers = getPotentialAttackers(game);
        List<Permanent> blockers = getOpponentBlockers(opponentId, game);
        List<Permanent> actualAttackers = new ArrayList<>();
        if (blockers.isEmpty()) {
            actualAttackers = attackers.getAttackers();
        } else if (attackers.size() - blockers.size() >= game.getPlayer(opponentId).getLife()) {
            actualAttackers = attackers.getAttackers();
        } else {
            CombatSimulator combat = simulateAttack(attackers, blockers, opponentId, game);
            if (combat.rating > 2) {
                for (CombatGroupSimulator group : combat.groups) {
                    this.declareAttacker(group.attackers.get(0).id, group.defenderId, game, false);
                }
            }
        }
        for (Permanent attacker : actualAttackers) {
            this.declareAttacker(attacker.getId(), opponentId, game, false);
        }
    }

    @Override
    public void selectBlockers(Ability source, Game game, UUID defendingPlayerId) {
        log.debug("selectBlockers");

        List<Permanent> blockers = getAvailableBlockers(game);

        CombatSimulator sim = simulateBlock(CombatSimulator.load(game), blockers, game);

        List<CombatGroup> groups = game.getCombat().getGroups();
        for (int i = 0; i < groups.size(); i++) {
            for (CreatureSimulator creature : sim.groups.get(i).blockers) {
                groups.get(i).addBlocker(creature.id, playerId, game);
            }
        }
    }

    @Override
    public int chooseReplacementEffect(Map<String, String> rEffects, Game game) {
        log.debug("chooseReplacementEffect");
        //TODO: implement this
        return 0;
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        log.debug("chooseMode");
        if (modes.getMode() != null && modes.getMaxModes(game, source) == modes.getSelectedModes().size()) {
            // mode was already set by the AI
            return modes.getMode();
        }

        // spell modes simulated by AI, see addModeOptions
        // trigger modes chooses here
        // TODO: add AI support to select best modes, current code uses first valid mode
        AvailableMode:
        for (Mode mode : modes.getAvailableModes(source, game)) {
            for (UUID selectedModeId : modes.getSelectedModes()) {
                Mode selectedMode = modes.get(selectedModeId);
                if (selectedMode.getId().equals(mode.getId())) {
                    continue AvailableMode;
                }
            }
            if (mode.getTargets().canChoose(source.getControllerId(), source, game)) { // and where targets are available
                return mode;
            }
        }
        return null;
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game) {
        log.debug("chooseTriggeredAbility: " + abilities.toString());
        //TODO: improve this
        if (!abilities.isEmpty()) {
            return abilities.get(0);
        }
        return null;
    }

    @Override
    public void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID attackerId, Ability source, Game game) {
        log.debug("assignDamage");
        //TODO: improve this
        game.getPermanent(targets.get(0)).damage(damage, attackerId, source, game);
    }

    @Override
    // TODO: add AI support with outcome and replace random with min/max
    public int getAmount(int min, int max, String message, Game game) {
        log.debug("getAmount");
        if (message.startsWith("Assign damage to ")) {
            return min;
        }
        if (min < max && min == 0) {
            return RandomUtil.nextInt(CardUtil.overflowInc(max, 1));
        }
        return min;
    }

    @Override
    public List<Integer> getMultiAmount(Outcome outcome, List<String> messages, int min, int max, MultiAmountType type, Game game) {
        log.debug("getMultiAmount");

        int needCount = messages.size();
        List<Integer> defaultList = MultiAmountType.prepareDefaltValues(needCount, min, max);
        if (needCount == 0) {
            return defaultList;
        }

        // BAD effect
        // default list uses minimum possible values, so return it on bad effect
        // TODO: need something for damage target and mana logic here, current version is useless but better than random
        if (!outcome.isGood()) {
            return defaultList;
        }

        // GOOD effect
        // values must be stable, so AI must able to simulate it and choose correct actions
        // fill max values as much as possible
        return MultiAmountType.prepareMaxValues(needCount, min, max);
    }

    @Override
    public UUID chooseAttackerOrder(List<Permanent> attackers, Game game) {
        //TODO: improve this
        return attackers.iterator().next().getId();
    }

    @Override
    public UUID chooseBlockerOrder(List<Permanent> blockers, CombatGroup combatGroup, List<UUID> blockerOrder, Game game) {
        //TODO: improve this
        return blockers.iterator().next().getId();
    }

    @Override
    public List<MageObject> getAvailableManaProducers(Game game) {
        return super.getAvailableManaProducers(game);
    }

    @Override
    public void sideboard(Match match, Deck deck) {
        //TODO: improve this
        match.submitDeck(playerId, deck);
    }

    private static void addBasicLands(Deck deck, String landName, int number) {
        Set<String> landSets = TournamentUtil.getLandSetCodeForDeckSets(deck.getExpansionSetCodes());

        CardCriteria criteria = new CardCriteria();
        if (!landSets.isEmpty()) {
            criteria.setCodes(landSets.toArray(new String[landSets.size()]));
        }
        criteria.rarities(Rarity.LAND).name(landName);
        List<CardInfo> cards = CardRepository.instance.findCards(criteria);

        if (cards.isEmpty()) {
            criteria = new CardCriteria();
            criteria.rarities(Rarity.LAND).name(landName);
            criteria.setCodes("M15");
            cards = CardRepository.instance.findCards(criteria);
        }

        for (int i = 0; i < number; i++) {
            Card land = cards.get(RandomUtil.nextInt(cards.size())).getCard();
            deck.getCards().add(land);
        }
    }

    public static Deck buildDeck(int deckMinSize, List<Card> cardPool, final List<ColoredManaSymbol> colors) {
        return buildDeck(deckMinSize, cardPool, colors, false);
    }

    public static Deck buildDeck(int deckMinSize, List<Card> cardPool, final List<ColoredManaSymbol> colors, boolean onlyBasicLands) {
        if (onlyBasicLands) {
            return buildDeckWithOnlyBasicLands(deckMinSize, cardPool);
        } else {
            return buildDeckWithNormalCards(deckMinSize, cardPool, colors);
        }
    }

    public static Deck buildDeckWithOnlyBasicLands(int deckMinSize, List<Card> cardPool) {
        // random cards from card pool
        Deck deck = new Deck();
        final int DECK_SIZE = deckMinSize != 0 ? deckMinSize : 40;

        List<Card> sortedCards = new ArrayList<>(cardPool);
        if (!sortedCards.isEmpty()) {
            while (deck.getCards().size() < DECK_SIZE) {
                deck.getCards().add(sortedCards.get(RandomUtil.nextInt(sortedCards.size())));
            }
            return deck;
        } else {
            addBasicLands(deck, "Forest", DECK_SIZE);
            return deck;
        }
    }

    public static Deck buildDeckWithNormalCards(int deckMinSize, List<Card> cardPool, final List<ColoredManaSymbol> colors) {
        // top 23 cards plus basic lands until 40 deck size
        Deck deck = new Deck();
        final int DECK_SIZE = deckMinSize != 0 ? deckMinSize : 40;
        final int DECK_CARDS_COUNT = Math.floorDiv(deckMinSize * 23, 40); // 23 from 40
        final int DECK_LANDS_COUNT = DECK_SIZE - DECK_CARDS_COUNT;

        // sort card pool by top score
        List<Card> sortedCards = new ArrayList<>(cardPool);
        Collections.sort(sortedCards, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                Integer score1 = RateCard.rateCard(o1, colors);
                Integer score2 = RateCard.rateCard(o2, colors);
                return score2.compareTo(score1);
            }
        });

        // get top cards
        int cardNum = 0;
        while (deck.getCards().size() < DECK_CARDS_COUNT && sortedCards.size() > cardNum) {
            Card card = sortedCards.get(cardNum);
            if (!card.isBasic()) {
                deck.getCards().add(card);
                deck.getSideboard().remove(card);
            }
            cardNum++;
        }

        // add basic lands by color percent
        // TODO:  compensate for non basic lands
        Mana mana = new Mana();
        for (Card card : deck.getCards()) {
            mana.add(card.getManaCost().getMana());
        }
        double total = mana.getBlack() + mana.getBlue() + mana.getGreen() + mana.getRed() + mana.getWhite();

        // most frequent land is forest by default
        int mostLand = 0;
        String mostLandName = "Forest";
        if (mana.getGreen() > 0) {
            int number = (int) Math.round(mana.getGreen() / total * DECK_LANDS_COUNT);
            addBasicLands(deck, "Forest", number);
            mostLand = number;
        }

        if (mana.getBlack() > 0) {
            int number = (int) Math.round(mana.getBlack() / total * DECK_LANDS_COUNT);
            addBasicLands(deck, "Swamp", number);
            if (number > mostLand) {
                mostLand = number;
                mostLandName = "Swamp";
            }
        }

        if (mana.getBlue() > 0) {
            int number = (int) Math.round(mana.getBlue() / total * DECK_LANDS_COUNT);
            addBasicLands(deck, "Island", number);
            if (number > mostLand) {
                mostLand = number;
                mostLandName = "Island";
            }
        }

        if (mana.getWhite() > 0) {
            int number = (int) Math.round(mana.getWhite() / total * DECK_LANDS_COUNT);
            addBasicLands(deck, "Plains", number);
            if (number > mostLand) {
                mostLand = number;
                mostLandName = "Plains";
            }
        }

        if (mana.getRed() > 0) {
            int number = (int) Math.round(mana.getRed() / total * DECK_LANDS_COUNT);
            addBasicLands(deck, "Mountain", number);
            if (number > mostLand) {
                mostLandName = "Plains";
            }
        }

        // adds remaining lands (most popular name)
        addBasicLands(deck, mostLandName, DECK_SIZE - deck.getCards().size());

        return deck;
    }

    @Override
    public void construct(Tournament tournament, Deck deck) {
        DeckValidator deckValidator = DeckValidatorFactory.instance.createDeckValidator(tournament.getOptions().getMatchOptions().getDeckType());
        int deckMinSize = deckValidator != null ? deckValidator.getDeckMinSize() : 0;

        if (deck != null && deck.getCards().size() < deckMinSize && !deck.getSideboard().isEmpty()) {
            if (chosenColors == null) {
                for (Card card : deck.getSideboard()) {
                    rememberPick(card, RateCard.rateCard(card, null));
                }
                chosenColors = chooseDeckColorsIfPossible();
            }
            deck = buildDeck(deckMinSize, new ArrayList<>(deck.getSideboard()), chosenColors);
        }
        tournament.submitDeck(playerId, deck);
    }

    public Card pickBestCard(List<Card> cards, List<ColoredManaSymbol> chosenColors) {
        if (cards.isEmpty()) {
            return null;
        }
        Card bestCard = null;
        int maxScore = 0;
        for (Card card : cards) {
            int score = RateCard.rateCard(card, chosenColors);
            if (bestCard == null || score > maxScore) {
                maxScore = score;
                bestCard = card;
            }
        }
        return bestCard;
    }

    public Card pickBestCard(List<Card> cards, List<ColoredManaSymbol> chosenColors, Target target, Ability source, Game game) {
        if (cards.isEmpty()) {
            return null;
        }
        Card bestCard = null;
        int maxScore = 0;
        for (Card card : cards) {
            int score = RateCard.rateCard(card, chosenColors);
            boolean betterCard = false;
            if (bestCard == null) { // we need any card to prevent NPE in callers
                betterCard = true;
            } else if (score > maxScore) { // we need better card
                if (target != null && source != null && game != null) {
                    // but also check it can be targeted
                    betterCard = target.canTarget(getId(), card.getId(), source, game);
                } else {
                    // target object wasn't provided, so accepting it anyway
                    betterCard = true;
                }
            }
            // is it better than previous one?
            if (betterCard) {
                maxScore = score;
                bestCard = card;
            }
        }
        return bestCard;
    }

    public Card pickWorstCard(List<Card> cards, List<ColoredManaSymbol> chosenColors, Target target, Ability source, Game game) {
        if (cards.isEmpty()) {
            return null;
        }
        Card worstCard = null;
        int minScore = Integer.MAX_VALUE;
        for (Card card : cards) {
            int score = RateCard.rateCard(card, chosenColors);
            boolean worseCard = false;
            if (worstCard == null) { // we need any card to prevent NPE in callers
                worseCard = true;
            } else if (score < minScore) { // we need worse card
                if (target != null && source != null && game != null) {
                    // but also check it can be targeted
                    worseCard = target.canTarget(getId(), card.getId(), source, game);
                } else {
                    // target object wasn't provided, so accepting it anyway
                    worseCard = true;
                }
            }
            // is it worse than previous one?
            if (worseCard) {
                minScore = score;
                worstCard = card;
            }
        }
        return worstCard;
    }

    public Card pickWorstCard(List<Card> cards, List<ColoredManaSymbol> chosenColors) {
        if (cards.isEmpty()) {
            return null;
        }
        Card worstCard = null;
        int minScore = Integer.MAX_VALUE;
        for (Card card : cards) {
            int score = RateCard.rateCard(card, chosenColors);
            if (worstCard == null || score < minScore) {
                minScore = score;
                worstCard = card;
            }
        }
        return worstCard;
    }

    @Override
    public void pickCard(List<Card> cards, Deck deck, Draft draft) {
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("No cards to pick from.");
        }
        try {
            Card bestCard = pickBestCard(cards, chosenColors);
            int maxScore = RateCard.rateCard(bestCard, chosenColors);
            int pickedCardRate = RateCard.getBaseCardScore(bestCard);

            if (pickedCardRate <= 30) {
                // if card is bad
                // try to counter pick without any color restriction
                Card counterPick = pickBestCard(cards, null);
                int counterPickScore = RateCard.getBaseCardScore(counterPick);
                // card is really good
                // take it!
                if (counterPickScore >= 80) {
                    bestCard = counterPick;
                    maxScore = RateCard.rateCard(bestCard, chosenColors);
                }
            }

            String colors = "not chosen yet";
            // remember card if colors are not chosen yet
            if (chosenColors == null) {
                rememberPick(bestCard, maxScore);
                chosenColors = chooseDeckColorsIfPossible();
            }
            if (chosenColors != null) {
                colors = "";
                for (ColoredManaSymbol symbol : chosenColors) {
                    colors += symbol.toString();
                }
            }
            log.debug("[DEBUG] AI picked: " + bestCard.getName() + ", score=" + maxScore + ", deck colors=" + colors);
            draft.addPick(playerId, bestCard.getId(), null);
        } catch (Exception e) {
            log.debug("Exception during AI pick card for draft playerId= " + getId());
            draft.addPick(playerId, cards.get(0).getId(), null);
        }
    }

    /**
     * Remember picked card with its score.
     *
     * @param card
     * @param score
     */
    protected void rememberPick(Card card, int score) {
        pickedCards.add(new PickedCard(card, score));
    }

    /**
     * Choose 2 deck colors for draft: 1. there should be at least 3 cards in
     * card pool 2. at least 2 cards should have different colors 3. get card
     * colors as chosen starting from most rated card
     *
     * @return
     */
    protected List<ColoredManaSymbol> chooseDeckColorsIfPossible() {
        if (pickedCards.size() > 2) {
            // sort by score and color mana symbol count in descending order
            Collections.sort(pickedCards, new Comparator<PickedCard>() {
                @Override
                public int compare(PickedCard o1, PickedCard o2) {
                    if (o1.score.equals(o2.score)) {
                        Integer i1 = RateCard.getColorManaCount(o1.card);
                        Integer i2 = RateCard.getColorManaCount(o2.card);
                        return i2.compareTo(i1);
                    }
                    return o2.score.compareTo(o1.score);
                }
            });
            Set<String> chosenSymbols = new HashSet<>();
            for (PickedCard picked : pickedCards) {
                int differentColorsInCost = RateCard.getDifferentColorManaCount(picked.card);
                // choose only color card, but only if they are not too gold
                if (differentColorsInCost > 0 && differentColorsInCost < 3) {
                    // if some colors were already chosen, total amount shouldn't be more than 3
                    if (chosenSymbols.size() + differentColorsInCost < 4) {
                        for (String symbol : picked.card.getManaCostSymbols()) {
                            symbol = symbol.replace("{", "").replace("}", "");
                            if (RateCard.isColoredMana(symbol)) {
                                chosenSymbols.add(symbol);
                            }
                        }
                    }
                }
                // only two or three color decks are allowed
                if (chosenSymbols.size() > 1 && chosenSymbols.size() < 4) {
                    List<ColoredManaSymbol> colorsChosen = new ArrayList<>();
                    for (String symbol : chosenSymbols) {
                        ColoredManaSymbol manaSymbol = ColoredManaSymbol.lookup(symbol.charAt(0));
                        if (manaSymbol != null) {
                            colorsChosen.add(manaSymbol);
                        }
                    }
                    if (colorsChosen.size() > 1) {
                        // no need to remember picks anymore
                        pickedCards = null;
                        return colorsChosen;
                    }
                }
            }
        }
        return null;
    }

    private static class PickedCard {

        public Card card;
        public Integer score;

        public PickedCard(Card card, int score) {
            this.card = card;
            this.score = score;
        }
    }

    protected Attackers getPotentialAttackers(Game game) {
        log.debug("getAvailableAttackers");
        Attackers attackers = new Attackers();
        List<Permanent> creatures = super.getAvailableAttackers(game);
        for (Permanent creature : creatures) {
            int potential = combatPotential(creature, game);
            if (potential > 0 && creature.getPower().getValue() > 0) {
                List<Permanent> l = attackers.get(potential);
                if (l == null) {
                    attackers.put(potential, l = new ArrayList<>());
                }
                l.add(creature);
            }
        }
        return attackers;
    }

    protected int combatPotential(Permanent creature, Game game) {
        log.debug("combatPotential");
        if (!creature.canAttack(null, game)) {
            return 0;
        }
        int potential = creature.getPower().getValue();
        potential += creature.getAbilities().getEvasionAbilities().size();
        potential += creature.getAbilities().getProtectionAbilities().size();
        potential += creature.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId()) ? 1 : 0;
        potential += creature.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId()) ? 2 : 0;
        potential += creature.getAbilities().containsKey(TrampleAbility.getInstance().getId()) ? 1 : 0;
        return potential;
    }

    protected List<Permanent> getOpponentBlockers(UUID opponentId, Game game) {
        FilterCreatureForCombatBlock blockFilter = new FilterCreatureForCombatBlock();
        List<Permanent> blockers = game.getBattlefield().getAllActivePermanents(blockFilter, opponentId, game);
        return blockers;
    }

    protected CombatSimulator simulateAttack(Attackers attackers, List<Permanent> blockers, UUID opponentId, Game game) {
        log.debug("simulateAttack");
        List<Permanent> attackersList = attackers.getAttackers();
        CombatSimulator best = new CombatSimulator();
        int bestResult = 0;
        //use binary digits to calculate powerset of attackers
        int powerElements = (int) Math.pow(2, attackersList.size());
        for (int i = 1; i < powerElements; i++) {
            String binary = Integer.toBinaryString(i);
            while (binary.length() < attackersList.size()) {
                binary = '0' + binary;
            }
            List<Permanent> trialAttackers = new ArrayList<>();
            for (int j = 0; j < attackersList.size(); j++) {
                if (binary.charAt(j) == '1') {
                    trialAttackers.add(attackersList.get(j));
                }
            }
            CombatSimulator combat = new CombatSimulator();
            for (Permanent permanent : trialAttackers) {
                combat.groups.add(new CombatGroupSimulator(opponentId, Arrays.asList(permanent.getId()), new ArrayList<UUID>(), game));
            }
            CombatSimulator test = simulateBlock(combat, blockers, game);
            if (test.evaluate() > bestResult) {
                best = test;
                bestResult = test.evaluate();
            }
        }

        return best;
    }

    protected CombatSimulator simulateBlock(CombatSimulator combat, List<Permanent> blockers, Game game) {
        log.debug("simulateBlock");

        TreeNode<CombatSimulator> simulations;

        simulations = new TreeNode<>(combat);
        addBlockSimulations(blockers, simulations, game);
        combat.simulate(game);

        return getWorstSimulation(simulations);

    }

    protected void addBlockSimulations(List<Permanent> blockers, TreeNode<CombatSimulator> node, Game game) {
        int numGroups = node.getData().groups.size();
        Copier<CombatSimulator> copier = new Copier<>();
        for (Permanent blocker : blockers) {
            List<Permanent> subList = remove(blockers, blocker);
            for (int i = 0; i < numGroups; i++) {
                if (node.getData().groups.get(i).canBlock(blocker, game)) {
                    CombatSimulator combat = copier.copy(node.getData());
                    combat.groups.get(i).blockers.add(new CreatureSimulator(blocker));
                    TreeNode<CombatSimulator> child = new TreeNode<>(combat);
                    node.addChild(child);
                    addBlockSimulations(subList, child, game);
                    combat.simulate(game);
                }
            }
        }
    }

    protected List<Permanent> remove(List<Permanent> source, Permanent element) {
        List<Permanent> newList = new ArrayList<>();
        for (Permanent permanent : source) {
            if (!permanent.equals(element)) {
                newList.add(permanent);
            }
        }
        return newList;
    }

    protected CombatSimulator getBestSimulation(TreeNode<CombatSimulator> simulations) {
        CombatSimulator best = simulations.getData();
        int bestResult = best.evaluate();
        for (TreeNode<CombatSimulator> node : simulations.getChildren()) {
            CombatSimulator bestSub = getBestSimulation(node);
            if (bestSub.evaluate() > bestResult) {
                best = node.getData();
                bestResult = best.evaluate();
            }
        }
        return best;
    }

    protected CombatSimulator getWorstSimulation(TreeNode<CombatSimulator> simulations) {
        CombatSimulator worst = simulations.getData();
        int worstResult = worst.evaluate();
        for (TreeNode<CombatSimulator> node : simulations.getChildren()) {
            CombatSimulator worstSub = getWorstSimulation(node);
            if (worstSub.evaluate() < worstResult) {
                worst = node.getData();
                worstResult = worst.evaluate();
            }
        }
        return worst;
    }

    protected void findBestPermanentTargets(Outcome outcome, UUID abilityControllerId, UUID sourceId, Ability source, FilterPermanent filter, Game game, Target target,
                                            List<Permanent> goodList, List<Permanent> badList, List<Permanent> allList) {
        // searching for most valuable/powerfull permanents
        goodList.clear();
        badList.clear();
        allList.clear();
        List<UUID> usedTargets = target.getTargets();

        // search all
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, abilityControllerId, source, game)) {
            if (usedTargets.contains(permanent.getId())) {
                continue;
            }

            if (outcome.isGood()) {
                // good effect
                if (permanent.isControlledBy(abilityControllerId)) {
                    goodList.add(permanent);
                } else {
                    badList.add(permanent);
                }
            } else {
                // bad effect
                if (permanent.isControlledBy(abilityControllerId)) {
                    badList.add(permanent);
                } else {
                    goodList.add(permanent);
                }
            }
        }

        // sort from tiny to big (more valuable)
        PermanentComparator comparator = new PermanentComparator(game);
        goodList.sort(comparator);
        badList.sort(comparator);

        // most valueable goes first in good list
        Collections.reverse(goodList);
        // most weakest goes first in bad list (no need to reverse)
        //Collections.reverse(badList);

        allList.addAll(goodList);
        allList.addAll(badList);

        // "can target all mode" don't need your/opponent lists -- all targets goes with same value
        if (outcome.isCanTargetAll()) {
            allList.sort(comparator); // bad sort
            if (outcome.isGood()) {
                Collections.reverse(allList); // good sort
            }
            goodList.clear();
            goodList.addAll(allList);
            badList.clear();
            badList.addAll(allList);
        }
    }

    protected List<Permanent> threats(UUID playerId, Ability source, FilterPermanent filter, Game game, List<UUID> targets) {
        return threats(playerId, source, filter, game, targets, true);
    }

    protected List<Permanent> threats(UUID playerId, Ability source, FilterPermanent filter, Game game, List<UUID> targets, boolean mostValueableGoFirst) {
        // most valuable/powerfull permanents goes at first
        List<Permanent> threats;
        if (playerId == null) {
            threats = game.getBattlefield().getActivePermanents(filter, this.getId(), source, game); // all permanents within the range of the player
        } else {
            FilterPermanent filterCopy = filter.copy();
            filterCopy.add(new ControllerIdPredicate(playerId));
            threats = game.getBattlefield().getActivePermanents(filter, this.getId(), source, game);
        }
        Iterator<Permanent> it = threats.iterator();
        while (it.hasNext()) { // remove permanents already targeted
            Permanent test = it.next();
            if (targets.contains(test.getId()) || (playerId != null && !test.getControllerId().equals(playerId))) {
                it.remove();
            }
        }
        Collections.sort(threats, new PermanentComparator(game));
        if (mostValueableGoFirst) {
            Collections.reverse(threats);
        }
        return threats;
    }

    protected void logState(Game game) {
        if (log.isTraceEnabled()) {
            logList("Computer player " + name + " hand: ", new ArrayList<MageObject>(hand.getCards(game)));
        }
    }

    protected void logList(String message, List<MageObject> list) {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append(": ");
        for (MageObject object : list) {
            sb.append(object.getName()).append(',');
        }
        log.info(sb.toString());
    }

    protected void logAbilityList(String message, List<Ability> list) {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append(": ");
        for (Ability ability : list) {
            sb.append(ability.getRule()).append(',');
        }
        log.debug(sb.toString());
    }

    private void playRemoval(List<UUID> creatures, Game game) {
        for (UUID creatureId : creatures) {
            for (Card card : this.playableInstant) {
                if (card.getSpellAbility().canActivate(playerId, game).canActivate()) {
                    for (Effect effect : card.getSpellAbility().getEffects()) {
                        if (effect.getOutcome() == Outcome.DestroyPermanent || effect.getOutcome() == Outcome.ReturnToHand) {
                            if (card.getSpellAbility().getTargets().get(0).canTarget(creatureId, card.getSpellAbility(), game)) {
                                if (this.activateAbility(card.getSpellAbility(), game)) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void playDamage(List<UUID> creatures, Game game) {
        for (UUID creatureId : creatures) {
            Permanent creature = game.getPermanent(creatureId);
            for (Card card : this.playableInstant) {
                if (card.getSpellAbility().canActivate(playerId, game).canActivate()) {
                    for (Effect effect : card.getSpellAbility().getEffects()) {
                        if (effect instanceof DamageTargetEffect) {
                            if (card.getSpellAbility().getTargets().get(0).canTarget(creatureId, card.getSpellAbility(), game)) {
                                if (((DamageTargetEffect) effect).getAmount() > (creature.getPower().getValue() - creature.getDamage())) {
                                    if (this.activateAbility(card.getSpellAbility(), game)) {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        unplayable = new TreeMap<>();
        playableNonInstant = new ArrayList<>();
        playableInstant = new ArrayList<>();
        playableAbilities = new ArrayList<>();
    }

    @Override
    public void cleanUpOnMatchEnd() {
        super.cleanUpOnMatchEnd(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ComputerPlayer copy() {
        return new ComputerPlayer(this);
    }

    private boolean tryAddTarget(Target target, UUID id, Ability source, Game game) {
        // workaround to to check successfull targets add
        int before = target.getTargets().size();
        target.addTarget(id, source, game);
        int after = target.getTargets().size();
        return before != after;
    }

    private boolean tryAddTarget(Target target, UUID id, int amount, Ability source, Game game) {
        // workaround to to check successfull targets add
        int before = target.getTargets().size();
        target.addTarget(id, amount, source, game);
        int after = target.getTargets().size();
        return before != after;
    }

    /**
     * Sets a possible target player. Depends on bad/good outcome
     *
     * @param source null on choose and non-null on chooseTarget
     */
    private boolean setTargetPlayer(Outcome outcome, Target target, Ability source, UUID abilityControllerId, UUID randomOpponentId, Game game, boolean required) {
        Outcome affectedOutcome;
        if (abilityControllerId == this.playerId) {
            // selects for itself
            affectedOutcome = outcome;
        } else {
            // selects for another player
            affectedOutcome = Outcome.inverse(outcome);
        }

        if (target.getOriginalTarget() instanceof TargetOpponent) {
            if (source == null) {
                if (target.canTarget(randomOpponentId, game)) {
                    target.add(randomOpponentId, game);
                    return true;
                }
            } else if (target.canTarget(abilityControllerId, randomOpponentId, source, game)) {
                target.addTarget(randomOpponentId, source, game);
                return true;
            }
            for (UUID possibleOpponentId : game.getOpponents(abilityControllerId)) {
                if (source == null) {
                    if (target.canTarget(possibleOpponentId, game)) {
                        target.add(possibleOpponentId, game);
                        return true;
                    }
                } else if (target.canTarget(abilityControllerId, possibleOpponentId, source, game)) {
                    target.addTarget(possibleOpponentId, source, game);
                    return true;
                }
            }
            return false;
        }

        UUID sourceId = source != null ? source.getSourceId() : null;
        if (target.getOriginalTarget() instanceof TargetPlayer) {
            if (affectedOutcome.isGood()) {
                if (source == null) {
                    // good
                    if (target.canTarget(abilityControllerId, game)) {
                        target.add(abilityControllerId, game);
                        return true;
                    }
                    if (target.isRequired(sourceId, game)) {
                        if (target.canTarget(randomOpponentId, game)) {
                            target.add(randomOpponentId, game);
                            return true;
                        }
                    }
                } else {
                    // good
                    if (target.canTarget(abilityControllerId, abilityControllerId, source, game)) {
                        target.addTarget(abilityControllerId, source, game);
                        return true;
                    }
                    if (target.isRequired(sourceId, game)) {
                        if (target.canTarget(abilityControllerId, randomOpponentId, source, game)) {
                            target.addTarget(randomOpponentId, source, game);
                            return true;
                        }
                    }
                }
            } else if (source == null) {
                // bad
                if (target.canTarget(randomOpponentId, game)) {
                    target.add(randomOpponentId, game);
                    return true;
                }
                if (target.isRequired(sourceId, game)) {
                    if (target.canTarget(abilityControllerId, game)) {
                        target.add(abilityControllerId, game);
                        return true;
                    }
                }
            } else {
                // bad
                if (target.canTarget(abilityControllerId, randomOpponentId, source, game)) {
                    target.addTarget(randomOpponentId, source, game);
                    return true;
                }
                if (required) {
                    if (target.canTarget(abilityControllerId, abilityControllerId, source, game)) {
                        target.addTarget(abilityControllerId, source, game);
                        return true;
                    }
                }
            }
            return false;
        }

        return false;
    }

    /**
     * Returns an opponent by random
     *
     * @param abilityControllerId
     * @param game
     * @return
     */
    private UUID getRandomOpponent(UUID abilityControllerId, Game game) {
        return RandomUtil.randomFromCollection(game.getOpponents(abilityControllerId));
    }

    @Override
    public SpellAbility chooseAbilityForCast(Card card, Game game, boolean noMana) {
        Map<UUID, SpellAbility> useable = PlayerImpl.getCastableSpellAbilities(game, this.getId(), card, game.getState().getZone(card.getId()), noMana);
        return (SpellAbility) useable.values().stream().findFirst().orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Player obj = (Player) o;
        if (this.getId() == null || obj.getId() == null) {
            return false;
        }

        return this.getId().equals(obj.getId());
    }

    @Override
    public boolean isHuman() {
        if (human) {
            throw new IllegalStateException("Computer player can't be Human");
        } else {
            return false;
        }
    }

    @Override
    public void restore(Player player) {
        super.restore(player);

        // restore used in AI simulations
        // all human players converted to computer and analyse
        this.human = false;
    }

    public long getLastThinkTime() {
        return lastThinkTime;
    }

    public void setLastThinkTime(long lastThinkTime) {
        this.lastThinkTime = lastThinkTime;
    }
}
