package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class PossessedPortal extends CardImpl {

    public PossessedPortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{8}");

        // If a player would draw a card, that player skips that draw instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PossessedPortalReplacementEffect()));
        
        // At the beginning of each end step, each player sacrifices a permanent unless they discard a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new PossessedPortalEffect(), TargetController.ANY, false));
    }

    private PossessedPortal(final PossessedPortal card) {
        super(card);
    }

    @Override
    public PossessedPortal copy() {
        return new PossessedPortal(this);
    }
}

class PossessedPortalReplacementEffect extends ReplacementEffectImpl {

    PossessedPortalReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "If a player would draw a card, that player skips that draw instead";
    }

    private PossessedPortalReplacementEffect(final PossessedPortalReplacementEffect effect) {
        super(effect);
    }

    @Override
    public PossessedPortalReplacementEffect copy() {
        return new PossessedPortalReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}

class PossessedPortalEffect extends OneShotEffect {
    
    PossessedPortalEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player sacrifices a permanent unless they discard a card";
    }
    
    private PossessedPortalEffect(final PossessedPortalEffect effect) {
        super(effect);
    }
    
    @Override
    public PossessedPortalEffect copy() {
        return new PossessedPortalEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            if (player.getHand().isEmpty() || !player.chooseUse(
                    Outcome.Discard, "Discard a card or sacrifice a permanent?",
                    null, "Discard a card", "Sacrifice a permanent", source, game)
                    || player.discard(1, false, false, source, game).isEmpty()) {
                // no discard, so try to sacrifice
                TargetSacrifice target = new TargetSacrifice(StaticFilters.FILTER_PERMANENT);
                if (target.canChoose(player.getId(), source, game)) {
                    player.choose(Outcome.Sacrifice, target, source, game);
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        permanent.sacrifice(source, game);
                    }
                }
            }
        }
        return true;
    }
}
