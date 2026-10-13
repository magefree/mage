package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author muz
 */
public final class HeartShapedHerb extends CardImpl {

    public HeartShapedHerb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");


        // If a source an opponent controls would deal damage to you, prevent 1 of that damage.
        this.addAbility(new SimpleStaticAbility(new HeartShapedHerbPreventionEffect()));

        // {2}, {T}, Sacrifice this artifact: You may sacrifice a creature.
        // If you do, return that card to the battlefield under its owner's control with three +1/+1 counters on it and you become the monarch.
        Ability ability = new SimpleActivatedAbility(new HeartShapedHerbOneShotEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private HeartShapedHerb(final HeartShapedHerb card) {
        super(card);
    }

    @Override
    public HeartShapedHerb copy() {
        return new HeartShapedHerb(this);
    }
}

class HeartShapedHerbPreventionEffect extends PreventionEffectImpl {

    HeartShapedHerbPreventionEffect() {
        super(Duration.WhileOnBattlefield, 1, false, false);
        this.staticText = "If a source an opponent controls would deal damage to you, prevent 1 of that damage";
    }

    private HeartShapedHerbPreventionEffect(final HeartShapedHerbPreventionEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getControllerId())) {
            UUID sourceControllerId = game.getControllerId(event.getSourceId());
            if (sourceControllerId != null && game.getOpponents(source.getControllerId()).contains(sourceControllerId)) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public HeartShapedHerbPreventionEffect copy() {
        return new HeartShapedHerbPreventionEffect(this);
    }
}

class HeartShapedHerbOneShotEffect extends OneShotEffect {

    HeartShapedHerbOneShotEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may sacrifice a creature. If you do, return"
         + " that card to the battlefield under its owner's control with three"
         + " +1/+1 counters on it and you become the monarch";
    }

    private HeartShapedHerbOneShotEffect(final HeartShapedHerbOneShotEffect effect) {
        super(effect);
    }

    @Override
    public HeartShapedHerbOneShotEffect copy() {
        return new HeartShapedHerbOneShotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.chooseUse(outcome, "Sacrifice a creature?", source, game)) {
            return false;
        }

        SacrificeTargetCost cost = new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE);
        if (!cost.canPay(source, source, controller.getId(), game)) {
            return false;
        }

        cost.clearPaid();
        if (!cost.pay(source, game, source, controller.getId(), false) || cost.getPermanents().isEmpty()) {
            return false;
        }

        Permanent sacrificed = cost.getPermanents().get(0);
        if (sacrificed != null) {
            Card sacrificedCard = game.getCard(sacrificed.getId());
            if (sacrificedCard != null) {
                Player owner = game.getPlayer(sacrificedCard.getOwnerId());
                if (owner != null) {
                    owner.moveCards(sacrificedCard, Zone.BATTLEFIELD, source, game);
                }
            }
            Permanent returned = game.getPermanent(sacrificed.getId());
            if (returned != null) {
                returned.addCounters(CounterType.P1P1.createInstance(3), source.getControllerId(), source, game);
            }
        }

        new BecomesMonarchSourceEffect().apply(game, source);
        return true;
    }
}
