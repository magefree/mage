package mage.cards.d;

import java.util.*;
import java.util.stream.Collectors;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.UntilYourNextTurnDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.continuous.BoostTargetPerpetuallyEffect;
import mage.cards.*;
import mage.choices.Choice;
import mage.choices.ChoiceHintType;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.command.emblems.*;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.common.TargetSacrifice;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;
import mage.target.targetpointer.TargetPointer;
import mage.util.CardUtil;
import mage.util.RandomUtil;

/**
 *
 * @author karapuzz14
 */
public final class DavrielSoulBroker extends CardImpl {

    public DavrielSoulBroker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DAVRIEL);
        this.setStartingLoyalty(4);

        // +1: Until your next turn, whenever an opponent attacks you and/or planeswalkers you control, they discard a card. If they can't, they sacrifice an attacking creature.
        this.addAbility(new LoyaltyAbility(new CreateDelayedTriggeredAbilityEffect(
                new UntilYourNextTurnDelayedTriggeredAbility(
                        new DavrielSoulBrokerTriggeredAbility()
                )
        ), 1));

        // −2: Accept one of Davriel's offers, then accept one of Davriel's conditions.
        this.addAbility(new LoyaltyAbility(new DavrielSoulBrokerAcceptOffersAndConditionsEffect(), -2));

        // −3: Target creature an opponent controls perpetually gets -3/-3.
        ContinuousEffect effect = new BoostTargetPerpetuallyEffect(-3, -3);
        effect.setText("Target creature an opponent controls perpetually gets -3/-3");

        Ability ability = new LoyaltyAbility(effect, -3);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private DavrielSoulBroker(final DavrielSoulBroker card) {
        super(card);
    }

    @Override
    public DavrielSoulBroker copy() {
        return new DavrielSoulBroker(this);
    }
}

class DavrielSoulBrokerTriggeredAbility extends TriggeredAbilityImpl {

    public DavrielSoulBrokerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DavrielSoulBrokerFirstEffect(), false);
    }

    private DavrielSoulBrokerTriggeredAbility(final DavrielSoulBrokerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DavrielSoulBrokerTriggeredAbility copy() {
        return new DavrielSoulBrokerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID playerId = game.getCombat()
                .getAttackers()
                .stream()
                .filter(attacker -> isControlledBy(game.getCombat().getDefendingPlayerId(attacker, game)))
                .map(game::getControllerId)
                .filter(game.getOpponents(getControllerId())::contains)
                .findFirst()
                .orElse(null);
        if (playerId == null) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(playerId));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks you and/or planeswalkers you control, " +
                "they discard a card. If they can't, they sacrifice an attacking creature";
    }
}

class DavrielSoulBrokerFirstEffect extends OneShotEffect {

    DavrielSoulBrokerFirstEffect() {
        super(Outcome.Discard);
    }

    private DavrielSoulBrokerFirstEffect(final DavrielSoulBrokerFirstEffect effect) {
        super(effect);
    }

    @Override
    public DavrielSoulBrokerFirstEffect copy() {
        return new DavrielSoulBrokerFirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        for(UUID target : getTargetPointer().getTargets(game, source)) {
            Player opponent = game.getPlayer(target);
            if (opponent == null) {
                continue;
            }
            // they discard a card
            Cards discardedCards = opponent.discard(1, false, false, source, game);

            // If they can't, they sacrifice an attacking creature.
            if (discardedCards == null || discardedCards.isEmpty()) {
                TargetSacrifice targetSacrifice = new TargetSacrifice(StaticFilters.FILTER_AN_ATTACKING_CREATURE);
                if (targetSacrifice.canChoose(opponent.getId(), source, game)) {
                    opponent.choose(Outcome.Sacrifice, targetSacrifice, source, game);

                    Permanent permanent = game.getPermanent(targetSacrifice.getFirstTarget());
                    if (permanent != null && permanent.sacrifice(source, game)) {
                        affectedTargets++;
                    }
                }
            } else {
                affectedTargets++;
            }
        }
        return affectedTargets > 0;
    }

}

class DavrielSoulBrokerAcceptOffersAndConditionsEffect extends OneShotEffect {

    private Effects offers = new Effects();
    private Effects conditions = new Effects();

    public DavrielSoulBrokerAcceptOffersAndConditionsEffect() {
        super(Outcome.Neutral);
        staticText = "Accept one of Davriel's offers, then accept one of Davriel's conditions.";

        // Offers and conditions list source: https://mtg.fandom.com/wiki/Davriel_Cane/Davriel%27s_Contracts
        offers.add(new DrawCardSourceControllerEffect(3)); // DONE
        offers.add(new ConjureCardEffect("Manor Guardian"));
        offers.add(new DavrielReturnFromGraveyardOfferEffect());

        FilterCard filter = new FilterCreatureCard();
        filter.add(HighestValueAmongCreatureCardsInYourGraveyardPredicate.instance);
        //offers.add(new ReturnFromGraveyardAtRandomEffect(filter, Zone.BATTLEFIELD)
        //        .setText("Return a random creature card " +
        //                "with the highest mana value from among cards in your graveyard to the battlefield"));
//
        //offers.add(new GetEmblemEffect(new DavrielSoulBrokerBoostCreaturesEmblem())); // DONE
        //offers.add(new GetEmblemEffect(new DavrielSoulBrokerCostReductionEmblem()));
        //offers.add(new GetEmblemEffect(new DavrielSoulBrokerPlaneswalkersBuffEmblem()));
        //offers.add(new GetEmblemEffect(new DavrielSoulBrokerGainLifeEmblem()));

        conditions.add(new LoseLifeSourceControllerEffect(6)); // DONE
        conditions.add(new DavrielExileConditionEffect());
        //conditions.add(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENTS, 2, "")); // DONE
        conditions.add(new DavrielBoostOpponentCreaturesConditionEffect());
        //conditions.add(new GetEmblemEffect(new DavrielSoulBrokerBoostOpponentCreaturesEmblem()));
        //conditions.add(new GetEmblemEffect(new DavrielSoulBrokerCostIncreaseEmblem())); // DONE
        //conditions.add(new GetEmblemEffect(new DavrielSoulBrokerTriggeredExileEmblem())); // DONE
        //conditions.add(new GetEmblemEffect(new DavrielSoulBrokerTriggeredLoseLifeEmblem())); // DONE



    }

    private DavrielSoulBrokerAcceptOffersAndConditionsEffect(final DavrielSoulBrokerAcceptOffersAndConditionsEffect effect) {
        super(effect);
        this.offers = effect.offers.copy();
        this.conditions = effect.conditions.copy();

    }

    @Override
    public DavrielSoulBrokerAcceptOffersAndConditionsEffect copy() {
        return new DavrielSoulBrokerAcceptOffersAndConditionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Effect chosenOffer = chooseOfferOrCondition(true, player, game);
        if(chosenOffer != null) {
            chosenOffer.apply(game, source);
        }
        else {
            return false;
        }

        Effect chosenCondition = chooseOfferOrCondition(false, player, game);
        if(chosenCondition != null) {
            chosenCondition.apply(game, source);
        }
        else {
            return false;
        }

        return true;
    }

    private Effect chooseOfferOrCondition(boolean isOffer, Player player, Game game) {
        Set<Effect> toSelect = new HashSet<>();
        while (toSelect.size() < 3) {
            if (isOffer) {
                toSelect.add(RandomUtil.randomFromCollection(offers));
            }
            else {
                toSelect.add(RandomUtil.randomFromCollection(conditions));
            }
        }
        Choice choice = new ChoiceImpl(true, ChoiceHintType.TEXT);
        String choiceMessage = "Accept one of Davriel's ";

        if(isOffer) {
            choiceMessage += "offers";
        }
        else {
            choiceMessage += "conditions";
        }
        choice.setMessage(choiceMessage);

        Map<String, Effect> effectRuleMap = new HashMap<>();
        for(Effect effect : toSelect) {
            if(effect instanceof OneShotEffect) {
                effectRuleMap.put(CardUtil.getTextWithFirstCharUpperCase(effect.getText(null)), effect);
            }
        }
        choice.setChoices(new LinkedHashSet<>(effectRuleMap.keySet()));
        player.choose(outcome, choice, game);
        String effectRule = choice.getChoice();
        if (effectRule == null) {
            return null;
        }
        return effectRuleMap.get(effectRule);
    }
}

enum HighestValueAmongCreatureCardsInYourGraveyardPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    private static final FilterCard filter = new FilterCreatureCard();
    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Player player = game.getPlayer(input.getPlayerId());
        int cmc = player.getGraveyard().getCards(filter, game)
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
        return input.getObject().getManaValue() >= cmc;
    }
}

class DavrielExileConditionEffect extends OneShotEffect {

    DavrielExileConditionEffect() {
        super(Outcome.Detriment);
        staticText = "Exile two cards from your hand. " +
                "If fewer than two cards were exiled this way, " +
                "each opponent draws cards equal to the difference";
    }

    private DavrielExileConditionEffect(final DavrielExileConditionEffect effect) {
        super(effect);
    }

    @Override
    public DavrielExileConditionEffect copy() {
        return new DavrielExileConditionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if(controller == null) {
            return false;
        }
        int exiledCardsCount = 0;
        if (!controller.getHand().isEmpty()) {
            TargetCard target = new TargetCard(2, 2, Zone.HAND, new FilterCard());
            if (controller.choose(Outcome.Exile, controller.getHand(), target, source, game)) {
                List<Card> cards = target.getTargets().stream()
                        .map(game::getCard)
                        .collect(Collectors.toList());

                exiledCardsCount = cards.size();
                Cards cardsToExile = new CardsImpl();
                cardsToExile.addAllCards(cards);
                controller.moveCards(cardsToExile, Zone.EXILED, source, game);
            }
        }

        if(exiledCardsCount < 2) {
            Set<UUID> opponents = game.getOpponents(source.getControllerId());
            for(UUID id : opponents) {
                Player opponent = game.getPlayer(id);
                opponent.drawCards(2 - exiledCardsCount, source, game);
            }
        }

        return true;
    }
}

class DavrielReturnFromGraveyardOfferEffect extends OneShotEffect {

    DavrielReturnFromGraveyardOfferEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return two random creature cards from your graveyard to your hand. They perpetually get +1/+1";
    }

    private DavrielReturnFromGraveyardOfferEffect(final DavrielReturnFromGraveyardOfferEffect effect) {
        super(effect);
    }

    @Override
    public DavrielReturnFromGraveyardOfferEffect copy() {
        return new DavrielReturnFromGraveyardOfferEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> creatureCards = controller.getGraveyard().getCards(new FilterCreatureCard(), game);
        Cards randomCards = new CardsImpl();
        for(int i = 0; i < 2; i++) {
            Card randomCard = RandomUtil.randomFromCollection(creatureCards);
            randomCards.add(randomCard);
            creatureCards.remove(randomCard);
        }

        randomCards.retainZone(Zone.GRAVEYARD, game); //verify the target card is still in the graveyard
        if (randomCards.isEmpty() || !controller.moveCards(randomCards, Zone.HAND, source, game)) {
            return false;
        }

        TargetPointer pointer = new FixedTargets(randomCards.getCards(game), game);
        BoostTargetPerpetuallyEffect effect = new BoostTargetPerpetuallyEffect(1, 1);
        effect.setTargetPointer(pointer);
        game.addEffect(effect, source);

        return true;
    }
}

class DavrielBoostOpponentCreaturesConditionEffect extends OneShotEffect {

    DavrielBoostOpponentCreaturesConditionEffect() {
        super(Outcome.Detriment);
        staticText = "Each creature you don't control perpetually gets +1/+1";
    }

    private DavrielBoostOpponentCreaturesConditionEffect(final DavrielBoostOpponentCreaturesConditionEffect effect) {
        super(effect);
    }

    @Override
    public DavrielBoostOpponentCreaturesConditionEffect copy() {
        return new DavrielBoostOpponentCreaturesConditionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<Permanent> targets = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CREATURES_YOU_DONT_CONTROL, source.getControllerId(), source, game);
        if(targets == null || targets.isEmpty()) {
            return false;
        }

        TargetPointer pointer = new FixedTargets(targets, game);
        BoostTargetPerpetuallyEffect effect = new BoostTargetPerpetuallyEffect(1, 1);
        effect.setTargetPointer(pointer);
        game.addEffect(effect, source);

        return true;
    }
}