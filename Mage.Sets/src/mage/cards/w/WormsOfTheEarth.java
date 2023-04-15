package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class WormsOfTheEarth extends CardImpl {

    public WormsOfTheEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}{B}");

        // Players can't play lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WormsOfTheEarthPlayEffect()));

        // Lands can't enter the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WormsOfTheEarthEnterEffect()));

        // At the beginning of each upkeep, any player may sacrifice two lands or have Worms of the Earth deal 5 damage to that player. If a player does either, destroy Worms of the Earth.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new WormsOfTheEarthDestroyEffect(), TargetController.EACH_PLAYER, false));
    }

    private WormsOfTheEarth(final WormsOfTheEarth card) {
        super(card);
    }

    @Override
    public WormsOfTheEarth copy() {
        return new WormsOfTheEarth(this);
    }
}

class WormsOfTheEarthPlayEffect extends ContinuousRuleModifyingEffectImpl {

    public WormsOfTheEarthPlayEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "Players can't play lands";
    }

    public WormsOfTheEarthPlayEffect(final WormsOfTheEarthPlayEffect effect) {
        super(effect);
    }

    @Override
    public WormsOfTheEarthPlayEffect copy() {
        return new WormsOfTheEarthPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}

class WormsOfTheEarthEnterEffect extends ContinuousRuleModifyingEffectImpl {

    public WormsOfTheEarthEnterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Lands can't enter the battlefield";
    }

    public WormsOfTheEarthEnterEffect(final WormsOfTheEarthEnterEffect effect) {
        super(effect);
    }

    @Override
    public WormsOfTheEarthEnterEffect copy() {
        return new WormsOfTheEarthEnterEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.BATTLEFIELD) {
            Card card = game.getCard(zEvent.getTargetId());
            return card != null && card.isLand(game);
        }
        return false;
    }
}

class WormsOfTheEarthDestroyEffect extends OneShotEffect {

    public WormsOfTheEarthDestroyEffect() {
        super(Outcome.Benefit);
        this.staticText = "any player may sacrifice two lands or have {this} deal 5 damage to that player. If a player does either, destroy {this}";
    }

    public WormsOfTheEarthDestroyEffect(final WormsOfTheEarthDestroyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            Cost cost = new SacrificeTargetCost(new TargetControlledPermanent(2, 2, new FilterControlledLandPermanent("lands"), false));
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.chooseUse(outcome, "Do you want to destroy " + sourcePermanent.getLogName() + "? (sacrifice two lands or have it deal 5 damage to you)", source, game)) {
                        cost.clearPaid();
                        if (cost.canPay(source, source, player.getId(), game)
                                && player.chooseUse(Outcome.Sacrifice, "Will you sacrifice two lands? (otherwise you'll be dealt 5 damage)", source, game)) {
                            if (!cost.pay(source, game, source, player.getId(), false, null)) {
                                player.damage(5, source.getSourceId(), source, game);
                            }
                        } else {
                            player.damage(5, source.getSourceId(), source, game);
                        }
                        sourcePermanent = game.getPermanent(source.getSourceId());
                        if (sourcePermanent != null) {
                            sourcePermanent.destroy(source, game, false);
                        }
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public WormsOfTheEarthDestroyEffect copy() {
        return new WormsOfTheEarthDestroyEffect(this);
    }
}
