
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

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
            Cost discardCost = new DiscardCardCost();
            if (player != null && discardCost.canPay(source, source, playerId, game)
                    && player.chooseUse(Outcome.Discard, "Discard a card? (Otherwise sacrifice a permanent)", source, game)) {
                discardCost.pay(source, game, source, playerId, true, null);
            }
            else {
                Cost sacrificeCost = new SacrificeTargetCost(new TargetControlledPermanent());
                sacrificeCost.pay(source, game, source, playerId, true, null);
            }
        }
        return true;
    }
}
