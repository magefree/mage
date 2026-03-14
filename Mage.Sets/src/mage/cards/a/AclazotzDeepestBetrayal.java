package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.BatToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetDiscard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class AclazotzDeepestBetrayal extends TransformingDoubleFacedCard {
    private static final Condition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 2, TargetController.ANY);
    private static final Hint hint = new ConditionHint(condition);

    public AclazotzDeepestBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.BAT, SubType.GOD}, "{3}{B}{B}",
                "Temple of the Dead",
                new SuperType[]{}, new CardType[]{CardType.LAND}, new SubType[]{}, "");

        this.getLeftHalfCard().setPT(4, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // Whenever Aclazotz attacks, each opponent discards a card. For each opponent who can't, you draw a card.
        this.getLeftHalfCard().addAbility(new AttacksTriggeredAbility(new AclazotzDeepestBetrayalEffect()));

        // Whenever an opponent discards a land card, create a 1/1 black Bat creature token with flying.
        this.getLeftHalfCard().addAbility(new AclazotzDeepestBetrayalTriggeredAbility());

        // When Aclazotz dies, return it to the battlefield tapped and transformed under its owner's control.
        this.getLeftHalfCard().addAbility(new DiesSourceTriggeredAbility(new AclazotzDeepestBetrayalTransformEffect()));

        // Temple of the Dead
        // {T}: Add {B}.
        this.getRightHalfCard().addAbility(new BlackManaAbility());

        // {2}{B}, {T}: Transform Temple of the Dead. Activate only if a player has one or fewer cards in hand and only as a sorcery.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{2}{B}"), condition
        ).setTiming(TimingRule.SORCERY);
        ability.addCost(new TapSourceCost());
        this.getRightHalfCard().addAbility(ability.addHint(hint));
    }

    private AclazotzDeepestBetrayal(final AclazotzDeepestBetrayal card) {
        super(card);
    }

    @Override
    public AclazotzDeepestBetrayal copy() {
        return new AclazotzDeepestBetrayal(this);
    }
}

// Inspired by Cruel Grimnarch
class AclazotzDeepestBetrayalEffect extends OneShotEffect {

    AclazotzDeepestBetrayalEffect() {
        super(Outcome.Benefit);
        this.staticText = "each opponent discards a card. For each opponent who can't, you draw a card";
    }

    private AclazotzDeepestBetrayalEffect(final AclazotzDeepestBetrayalEffect effect) {
        super(effect);
    }

    @Override
    public AclazotzDeepestBetrayalEffect copy() {
        return new AclazotzDeepestBetrayalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Map<UUID, Cards> cardsToDiscard = new HashMap<>();

        // choose cards to discard
        for (UUID playerId : game.getOpponents(source.getControllerId(), true)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int numberOfCardsToDiscard = Math.min(1, player.getHand().size());
            Cards cards = new CardsImpl();
            Target target = new TargetDiscard(numberOfCardsToDiscard, numberOfCardsToDiscard, StaticFilters.FILTER_CARD, playerId);
            player.chooseTarget(outcome, target, source, game);
            cards.addAll(target.getTargets());
            cardsToDiscard.put(playerId, cards);
        }

        // discard all chosen cards
        for (UUID playerId : game.getOpponents(source.getControllerId(), true)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int amountDiscarded = player.discard(cardsToDiscard.get(playerId), false, source, game).size();
            if (amountDiscarded == 0 && controller != null) {
                controller.drawCards(1, source, game);
            }
        }
        return true;
    }
}

// Inspired by Waste Not
class AclazotzDeepestBetrayalTriggeredAbility extends TriggeredAbilityImpl {

    AclazotzDeepestBetrayalTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new BatToken()), false);
        setTriggerPhrase("Whenever an opponent discards a land card, ");
    }

    private AclazotzDeepestBetrayalTriggeredAbility(final AclazotzDeepestBetrayalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AclazotzDeepestBetrayalTriggeredAbility copy() {
        return new AclazotzDeepestBetrayalTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.getControllerId()).contains(event.getPlayerId())) {
            Card discarded = game.getCard(event.getTargetId());
            return discarded != null && discarded.isLand(game);
        }
        return false;
    }
}

// Inspired by Edgar, Charmed Groom
class AclazotzDeepestBetrayalTransformEffect extends OneShotEffect {

    AclazotzDeepestBetrayalTransformEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield tapped and transformed under its owner's control";
    }

    private AclazotzDeepestBetrayalTransformEffect(final AclazotzDeepestBetrayalTransformEffect effect) {
        super(effect);
    }

    @Override
    public AclazotzDeepestBetrayalTransformEffect copy() {
        return new AclazotzDeepestBetrayalTransformEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = source.getSourceCardIfItStillExists(game);
        if (controller == null || card == null) {
            return false;
        }
        game.getState().setValue(TransformingDoubleFacedCard.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
        return true;
    }
}
