package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BecomesTargetControlledPermanentTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.command.emblems.LukkaWaywardBonderEmblem;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.UUID;

import static mage.constants.Outcome.Benefit;

/**
 * @author TheElk801
 */
public final class MilaCraftyCompanion extends ModalDoubleFacesCard {

    public MilaCraftyCompanion(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.FOX}, "{1}{W}{W}",
                "Lukka, Wayward Bonder",
                new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.LUKKA}, "{4}{R}{R}"
        );

        // 1.
        // Mila, Crafty Companion
        // Legendary Creature - Fox
        this.getLeftHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getLeftHalfCard().setPT(2, 3);

        // Whenever an opponent attacks one or more planeswalkers you control, put a loyalty counter on each planeswalker you control.
        this.getLeftHalfCard().addAbility(new MilaCraftyCompanionTriggeredAbility());

        // Whenever a permanent you control becomes the target of a spell or ability and opponent controls, you may draw a card.
        this.getLeftHalfCard().addAbility(new BecomesTargetControlledPermanentTriggeredAbility(
                new DrawCardSourceControllerEffect(1), true
        ));

        // 2.
        // Lukka, Wayward Bonder
        // Legendary Planeswalker - Lukka
        this.getRightHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getRightHalfCard().setStartingLoyalty(5);

        // +1: You may discard a card. If you do, draw a card. If a creature card was discarded this way, draw two cards instead.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new LukkaWaywardBonderDiscardEffect(), 1));

        // −2: Return target creature card from your graveyard to the battlefield. It gains haste. Exile it at the beginning of your next upkeep.
        Ability ability = new LoyaltyAbility(new LukkaWaywardBonderReturnEffect(), -2);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getRightHalfCard().addAbility(ability);

        // −7: You get an emblem with "Whenever a creature enters the battlefield under your control, it deals damage equal to its power to any target."
        this.getRightHalfCard().addAbility(new LoyaltyAbility(
                new GetEmblemEffect(new LukkaWaywardBonderEmblem()), -7
        ));
    }

    private MilaCraftyCompanion(final MilaCraftyCompanion card) {
        super(card);
    }

    @Override
    public MilaCraftyCompanion copy() {
        return new MilaCraftyCompanion(this);
    }
}

class MilaCraftyCompanionTriggeredAbility extends TriggeredAbilityImpl {

    MilaCraftyCompanionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersAllEffect(
                CounterType.LOYALTY.createInstance(), StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER
        ), false);
    }

    private MilaCraftyCompanionTriggeredAbility(final MilaCraftyCompanionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MilaCraftyCompanionTriggeredAbility copy() {
        return new MilaCraftyCompanionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat()
                .getAttackers()
                .stream()
                .filter(attackerId -> game.getOpponents(getControllerId()).contains(game.getControllerId(attackerId)))
                .map(game.getCombat()::getDefenderId)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isPlaneswalker(game))
                .map(Controllable::getControllerId)
                .anyMatch(getControllerId()::equals);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks one or more planeswalkers you control, " +
                "put a loyalty counter on each planeswalker you control.";
    }
}

class LukkaWaywardBonderDiscardEffect extends OneShotEffect {

    LukkaWaywardBonderDiscardEffect() {
        super(Benefit);
        staticText = "you may discard a card. If you do, draw a card. " +
                "If a creature card was discarded this way, draw two cards instead";
    }

    private LukkaWaywardBonderDiscardEffect(final LukkaWaywardBonderDiscardEffect effect) {
        super(effect);
    }

    @Override
    public LukkaWaywardBonderDiscardEffect copy() {
        return new LukkaWaywardBonderDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.discard(0, 1, false, source, game).getRandom(game);
        if (card == null) {
            return false;
        }
        player.drawCards(card.isCreature(game) ? 2 : 1, source, game);
        return true;
    }
}

class LukkaWaywardBonderReturnEffect extends OneShotEffect {

    LukkaWaywardBonderReturnEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature card from your graveyard to the battlefield. " +
                "It gains haste. Exile it at the beginning of your next upkeep";
    }

    private LukkaWaywardBonderReturnEffect(final LukkaWaywardBonderReturnEffect effect) {
        super(effect);
    }

    @Override
    public LukkaWaywardBonderReturnEffect copy() {
        return new LukkaWaywardBonderReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(
                new ExileTargetEffect()
                        .setText("Exile it at the beginning of your next upkeep.")
                        .setTargetPointer(new FixedTarget(permanent, game)),
                Duration.Custom, true
        ), source);
        return true;
    }
}
