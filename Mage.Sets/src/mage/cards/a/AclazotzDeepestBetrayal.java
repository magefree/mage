package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TransformAbility;
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
public final class AclazotzDeepestBetrayal extends CardImpl {

    public AclazotzDeepestBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.secondSideCardClazz = mage.cards.t.TempleOfTheDead.class;

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Aclazotz attacks, each opponent discards a card. For each opponent who can't, you draw a card.
        this.addAbility(new AttacksTriggeredAbility(new AclazotzDeepestBetrayalEffect()));

        // Whenever an opponent discards a land card, create a 1/1 black Bat creature token with flying.
        this.addAbility(new AclazotzDeepestBetrayalTriggeredAbility());

        // When Aclazotz dies, return it to the battlefield tapped and transformed under its owner's control.
        this.addAbility(new TransformAbility());
        this.addAbility(new DiesSourceTriggeredAbility(new AclazotzDeepestBetrayalTransformEffect()));
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
        if (controller == null) {
            return false;
        }
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (!(sourceObject instanceof Card)) {
            return false;
        }
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        controller.moveCards((Card) sourceObject, Zone.BATTLEFIELD, source, game, true, false, true, null);
        return true;
    }
}