package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheDeckOfManyThings extends CardImpl {

    public TheDeckOfManyThings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.addSuperType(SuperType.LEGENDARY);

        // {2}, {T}: Roll a d20 and subtract the number of cards in your hand. If the result is 0 or less, discard your hand.
        // 1-9 | Return a card at random from your graveyard to your hand.
        // 10-19 | Draw two cards.
        // 20 | Put a creature card from any graveyard onto the battlefield under your control. When that creature dies, its owner loses the game.
        Ability ability = new SimpleActivatedAbility(new TheDeckOfManyThingsEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TheDeckOfManyThings(final TheDeckOfManyThings card) {
        super(card);
    }

    @Override
    public TheDeckOfManyThings copy() {
        return new TheDeckOfManyThings(this);
    }
}

class TheDeckOfManyThingsEffect extends RollDieWithResultTableEffect {

    TheDeckOfManyThingsEffect() {
        super(20, "roll a d20 and subtract the number of cards in your hand. If the result is 0 or less, discard your hand");
        this.addTableEntry(1, 9, new TheDeckOfManyThingsRandomEffect());
        this.addTableEntry(10, 19, new DrawCardSourceControllerEffect(2));
        this.addTableEntry(20, 20, new TheDeckOfManyThingsReturnEffect());
    }

    private TheDeckOfManyThingsEffect(final TheDeckOfManyThingsEffect effect) {
        super(effect);
    }

    @Override
    public TheDeckOfManyThingsEffect copy() {
        return new TheDeckOfManyThingsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int result = player.rollDice(outcome, source, game, sides) - player.getHand().size();
        if (result <= 0) {
            player.discard(player.getHand(), false, source, game);
        }
        this.applyResult(result, game, source);
        return true;
    }
}

class TheDeckOfManyThingsRandomEffect extends OneShotEffect {

    TheDeckOfManyThingsRandomEffect() {
        super(Outcome.ReturnToHand);
        staticText = "return a card at random from your graveyard to your hand";
    }

    private TheDeckOfManyThingsRandomEffect(final TheDeckOfManyThingsRandomEffect effect) {
        super(effect);
    }

    @Override
    public TheDeckOfManyThingsRandomEffect copy() {
        return new TheDeckOfManyThingsRandomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(StaticFilters.FILTER_CARD, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD);
        target.setRandom(true);
        target.setNotTarget(true);
        player.chooseTarget(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.HAND, source, game);
    }
}

class TheDeckOfManyThingsReturnEffect extends OneShotEffect {

    TheDeckOfManyThingsReturnEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "put a creature card from any graveyard onto the battlefield under your control. " +
                "When that creature dies, its owner loses the game";
    }

    private TheDeckOfManyThingsReturnEffect(final TheDeckOfManyThingsReturnEffect effect) {
        super(effect);
    }

    @Override
    public TheDeckOfManyThingsReturnEffect copy() {
        return new TheDeckOfManyThingsReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInGraveyard target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE);
        target.setNotTarget(true);
        if (!target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
            return false;
        }
        player.choose(outcome, target, source.getControllerId(), game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        game.addDelayedTriggeredAbility(new TheDeckOfManyThingsDelayedTriggeredAbility(permanent, game), source);
        return true;
    }
}

class TheDeckOfManyThingsDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final MageObjectReference mor;

    TheDeckOfManyThingsDelayedTriggeredAbility(Permanent permanent, Game game) {
        super(new LoseGameTargetPlayerEffect(), Duration.Custom, false, false);
        this.mor = new MageObjectReference(permanent, game);
    }

    private TheDeckOfManyThingsDelayedTriggeredAbility(final TheDeckOfManyThingsDelayedTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent() || !mor.refersTo(zEvent.getTarget(), game)) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(zEvent.getTarget().getOwnerId(), game));
        return true;
    }

    @Override
    public TheDeckOfManyThingsDelayedTriggeredAbility copy() {
        return new TheDeckOfManyThingsDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When that creature dies, its owner loses the game.";
    }
}
