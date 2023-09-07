package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author arcox
 */
public final class NineLives extends CardImpl {

    public NineLives(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // If a source would deal damage to you, prevent that damage and put an incarnation counter on Nine Lives.
        this.addAbility(new SimpleStaticAbility(new NineLivesPreventionEffect()));

        // When there are nine or more incarnation counters on Nine Lives, exile it.
        this.addAbility(new NineLivesStateTriggeredAbility());

        // When Nine Lives leaves the battlefield, you lose the game.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new LoseGameSourceControllerEffect(), false));
    }

    private NineLives(final NineLives card) {
        super(card);
    }

    @Override
    public NineLives copy() {
        return new NineLives(this);
    }
}


class NineLivesPreventionEffect extends PreventionEffectImpl {

    public NineLivesPreventionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "If a source would deal damage to you, prevent that damage and put an incarnation counter on {this}";
    }

    private NineLivesPreventionEffect(final NineLivesPreventionEffect effect) {
        super(effect);
    }

    @Override
    public NineLivesPreventionEffect copy() {
        return new NineLivesPreventionEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), event.getAmount(), ((DamageEvent) event).isCombatDamage());
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                Permanent nineLives = source.getSourcePermanentIfItStillExists(game);
                if (nineLives != null) {
                    nineLives.addCounters(CounterType.INCARNATION.createInstance(1), source.getControllerId(), source, game);
                }
            }
            event.setAmount(0);
            game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), damage));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            return event.getTargetId().equals(source.getControllerId());
        }
        return false;
    }
}


class NineLivesStateTriggeredAbility extends StateTriggeredAbility {

    public NineLivesStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileSourceEffect());
    }

    private NineLivesStateTriggeredAbility(final NineLivesStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NineLivesStateTriggeredAbility copy() {
        return new NineLivesStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && permanent.getCounters(game).getCount(CounterType.INCARNATION) >= 9;
    }

    @Override
    public String getRule() {
        return "When there are nine or more incarnation counters on {this}, exile it.";
    }
}
