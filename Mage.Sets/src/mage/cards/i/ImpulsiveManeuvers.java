
package mage.cards.i;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.filter.StaticFilters;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public final class ImpulsiveManeuvers extends CardImpl {

    public ImpulsiveManeuvers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}{R}");

        // Whenever a creature attacks, flip a coin. If you win the flip, the next time that creature would deal combat damage this turn, it deals double that damage instead. If you lose the flip, the next time that creature would deal combat damage this turn, prevent that damage.
        this.addAbility(new AttacksAllTriggeredAbility(new ImpulsiveManeuversEffect(), false, StaticFilters.FILTER_PERMANENT_CREATURE,
                SetTargetPointer.PERMANENT, false));
    }

    private ImpulsiveManeuvers(final ImpulsiveManeuvers card) {
        super(card);
    }

    @Override
    public ImpulsiveManeuvers copy() {
        return new ImpulsiveManeuvers(this);
    }
}

class ImpulsiveManeuversEffect extends PreventionEffectImpl {
    
    private boolean wonFlip;

    public ImpulsiveManeuversEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        staticText = "flip a coin. If you win the flip, the next time that creature would deal combat damage this turn, it deals double that damage instead. If you lose the flip, the next time that creature would deal combat damage this turn, prevent that damage";
    }

    public ImpulsiveManeuversEffect(final ImpulsiveManeuversEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        this.wonFlip = game.getPlayer(source.getControllerId()).flipCoin(source, game, true);
        super.init(source, game);
    }

    @Override
    public ImpulsiveManeuversEffect copy() {
        return new ImpulsiveManeuversEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT ||
                event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        if (object == null) {
            game.informPlayers("Couldn't find source of damage");
            return false;
        }
        return event.getSourceId().equals(this.getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && object != null) {
            if (super.applies(event, source, game) && event instanceof DamageEvent && event.getAmount() > 0) {
                DamageEvent damageEvent = (DamageEvent) event;
                if (damageEvent.isCombatDamage()) {
                    if (wonFlip) {
                        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
                        this.discard();
                    } else {
                        preventDamageAction(event, source, game);
                        this.discard();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
