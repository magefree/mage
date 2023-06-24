package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlagueReaver extends CardImpl {

    public PlagueReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // At the beginning of your end step, sacrifice each other creature you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new PlagueReaverSacrificeEffect(), TargetController.YOU, false
        ));

        // Discard two cards, Sacrifice Plague Reaver: Choose target opponent. Return Plague Reaver to the battlefield under that player's control at the beginning of their next upkeep.
        Ability ability = new SimpleActivatedAbility(
                new PlagueReaverTriggerEffect(), new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS))
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private PlagueReaver(final PlagueReaver card) {
        super(card);
    }

    @Override
    public PlagueReaver copy() {
        return new PlagueReaver(this);
    }
}

class PlagueReaverSacrificeEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    PlagueReaverSacrificeEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice each other creature you control";
    }

    private PlagueReaverSacrificeEffect(final PlagueReaverSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public PlagueReaverSacrificeEffect copy() {
        return new PlagueReaverSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            if (permanent == null) {
                continue;
            }
            permanent.sacrifice(source, game);
        }
        return true;
    }
}

class PlagueReaverTriggerEffect extends OneShotEffect {

    PlagueReaverTriggerEffect() {
        super(Outcome.Benefit);
        staticText = "choose target opponent. Return {this} to the battlefield " +
                "under that player's control at the beginning of their next upkeep";
    }

    private PlagueReaverTriggerEffect(final PlagueReaverTriggerEffect effect) {
        super(effect);
    }

    @Override
    public PlagueReaverTriggerEffect copy() {
        return new PlagueReaverTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new PlagueReaverDelayedTriggeredAbility(source.getFirstTarget(), source), source);
        return true;
    }
}

class PlagueReaverDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID playerId;

    PlagueReaverDelayedTriggeredAbility(UUID playerId, Ability source) {
        super(new PlagueReaverReturnEffect(playerId).setTargetPointer(
                new FixedTarget(source.getSourceId(), source.getSourceObjectZoneChangeCounter())
        ), Duration.Custom, true, false);
        this.playerId = playerId;
    }

    private PlagueReaverDelayedTriggeredAbility(final PlagueReaverDelayedTriggeredAbility ability) {
        super(ability);
        this.playerId = ability.playerId;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.isActivePlayer(playerId);
    }

    @Override
    public PlagueReaverDelayedTriggeredAbility copy() {
        return new PlagueReaverDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Return {this} to the battlefield under that player's control " +
                "at the beginning of their next upkeep.";
    }
}

class PlagueReaverReturnEffect extends OneShotEffect {

    private final UUID playerId;

    PlagueReaverReturnEffect(UUID playerId) {
        super(Outcome.Benefit);
        this.playerId = playerId;
    }

    private PlagueReaverReturnEffect(final PlagueReaverReturnEffect effect) {
        super(effect);
        this.playerId = effect.playerId;
    }

    @Override
    public PlagueReaverReturnEffect copy() {
        return new PlagueReaverReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(playerId);
        Card card = game.getCard(targetPointer.getFirst(game, source));
        return player != null
                && card != null
                && player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
