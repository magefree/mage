package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author cbt33, Loki (Heartbeat of Spring)
 */
public final class PriceOfGlory extends CardImpl {

    public PriceOfGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever a player taps a land for mana, if it's not that player's turn, destroy that land.
        this.addAbility(new PriceOfGloryAbility());
    }

    public PriceOfGlory(final PriceOfGlory card) {
        super(card);
    }

    @Override
    public PriceOfGlory copy() {
        return new PriceOfGlory(this);
    }
}

class PriceOfGloryAbility extends TriggeredAbilityImpl {

    private static final String staticText = "Whenever a player taps a land for mana, if it's not that player's turn, destroy that land.";

    public PriceOfGloryAbility() {
        super(Zone.BATTLEFIELD, new PriceOfGloryEffect());
    }

    public PriceOfGloryAbility(PriceOfGloryAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA
                && !game.inCheckPlayableState();        
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent == null) {
            return false;
        }
        if (permanent.isLand()
                && game.getState().getPlayersInRange(controllerId, game).contains(permanent.getControllerId())
                && !permanent.isControlledBy(game.getActivePlayerId())) { // intervening if clause
            getEffects().get(0).setTargetPointer(new FixedTarget(permanent, game));
            return true;
        }
        return false;
    }

    @Override
    public PriceOfGloryAbility copy() {
        return new PriceOfGloryAbility(this);
    }

    @Override
    public String getRule() {
        return staticText;
    }
}

class PriceOfGloryEffect extends OneShotEffect {

    public PriceOfGloryEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "if it's not that player's turn, destroy that land.";
    }

    public PriceOfGloryEffect(final PriceOfGloryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent land = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (land != null && !land.isControlledBy(game.getActivePlayerId())) { // intervening if clause has to be checked again
                land.destroy(source.getSourceId(), game, false);
            }
            return true;
        }
        return false;
    }

    @Override
    public PriceOfGloryEffect copy() {
        return new PriceOfGloryEffect(this);
    }
}
