
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class EverlastingTorment extends CardImpl {

    public EverlastingTorment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B/R}");

        // Players can't gain life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantGainLifeAllEffect()));

        // Damage can't be prevented.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new DamageCantBePreventedEffect(Duration.WhileOnBattlefield, "Damage can't be prevented", true, false)));

        // All damage is dealt as though its source had wither.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DamageDealtAsIfSourceHadWitherEffect()));

    }

    public EverlastingTorment(final EverlastingTorment card) {
        super(card);
    }

    @Override
    public EverlastingTorment copy() {
        return new EverlastingTorment(this);
    }
}

class DamageDealtAsIfSourceHadWitherEffect extends ReplacementEffectImpl {

    public DamageDealtAsIfSourceHadWitherEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "All damage is dealt as though its source had wither";
    }

    public DamageDealtAsIfSourceHadWitherEffect(final DamageDealtAsIfSourceHadWitherEffect effect) {
        super(effect);
    }

    @Override
    public DamageDealtAsIfSourceHadWitherEffect copy() {
        return new DamageDealtAsIfSourceHadWitherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damageAmount = event.getAmount();
        if (damageAmount > 0) {
            Counter counter = CounterType.M1M1.createInstance(damageAmount);
            Permanent creatureDamaged = game.getPermanent(event.getTargetId());
            if (creatureDamaged != null) {
                creatureDamaged.addCounters(counter, source, game);
            }
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_CREATURE;
    }

    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}
