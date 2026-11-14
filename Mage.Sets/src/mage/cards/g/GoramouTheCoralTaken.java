package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DiscardedCardsEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.BloodToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class GoramouTheCoralTaken extends CardImpl {

    public GoramouTheCoralTaken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHARK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Goramou enters, create a Blood token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BloodToken())));

        // Whenever you discard one or more land cards, you may exile one of them from your graveyard. If you do, put that card onto the battlefield tapped at the beginning of the next end step.
        this.addAbility(new GoramouTheCoralTakenTriggeredAbility());
    }

    private GoramouTheCoralTaken(final GoramouTheCoralTaken card) {
        super(card);
    }

    @Override
    public GoramouTheCoralTaken copy() {
        return new GoramouTheCoralTaken(this);
    }
}

class GoramouTheCoralTakenTriggeredAbility extends TriggeredAbilityImpl {

    GoramouTheCoralTakenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GoramouTheCoralTakenEffect());
        setTriggerPhrase("Whenever you discard one or more land cards, ");
    }

    private GoramouTheCoralTakenTriggeredAbility(final GoramouTheCoralTakenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GoramouTheCoralTakenTriggeredAbility copy() {
        return new GoramouTheCoralTakenTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARDS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        DiscardedCardsEvent discarded = (DiscardedCardsEvent) event;
        Cards lands = new CardsImpl(discarded.getDiscardedCards().getCards(
            StaticFilters.FILTER_CARD_LANDS, getControllerId(), this, game
        ));
        if (lands.isEmpty()) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTargets(lands, game));
        return true;
    }
}

class GoramouTheCoralTakenEffect extends OneShotEffect {

    GoramouTheCoralTakenEffect() {
        super(Outcome.Benefit);
        staticText = "you may exile one of them from your graveyard. If you do, put that card onto the battlefield tapped at the beginning of the next end step";
    }

    private GoramouTheCoralTakenEffect(final GoramouTheCoralTakenEffect effect) {
        super(effect);
    }

    @Override
    public GoramouTheCoralTakenEffect copy() {
        return new GoramouTheCoralTakenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        cards.retainZone(Zone.GRAVEYARD, game);
        if (cards.isEmpty() || !controller.chooseUse(Outcome.Benefit, "Exile a discarded land card?", source, game)) {
            return true;
        }
        TargetCard target = new TargetCard(Zone.GRAVEYARD, new FilterCard("discarded card to exile"));
        if (controller.choose(Outcome.Benefit, cards, target, source, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null && controller.moveCards(card, Zone.EXILED, source, game)) {
                Effect effect = new ReturnToBattlefieldUnderYourControlTargetEffect(true, true).setTargetPointer(new FixedTarget(card, game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
            }
        }
        return true;
    }
}
