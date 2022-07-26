
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class ImminentDoom extends CardImpl {

    public ImminentDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Imminent Doom enters the battlefield with a doom counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.DOOM.createInstance(1)), "with a doom counter on it"));

        // Whenever you cast a spell with converted mana cost equal to the number of doom counters on Imminent Doom,
        // Imminent Doom deals that much damage to any target.
        // Then put a doom counter on Imminent Doom.
        Ability ability = new ImminentDoomTriggeredAbility();
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    private ImminentDoom(final ImminentDoom card) {
        super(card);
    }

    @Override
    public ImminentDoom copy() {
        return new ImminentDoom(this);
    }
}

class ImminentDoomTriggeredAbility extends TriggeredAbilityImpl {

    private static final String rule = "Whenever you cast a spell with mana value equal to the number of doom counters on {this}, " +
                                       "{this} deals that much damage to any target. Then put a doom counter on {this}.";

    public ImminentDoomTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ImminentDoomEffect());
    }

    public ImminentDoomTriggeredAbility(final ImminentDoomTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ImminentDoomTriggeredAbility copy() {
        return new ImminentDoomTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(this.getControllerId())) {
            return false;
        }

        Permanent imminentDoom = game.getPermanent(getSourceId());
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (imminentDoom == null || spell == null) {
            return false;
        }

        if (spell.getManaValue() == imminentDoom.getCounters(game).getCount(CounterType.DOOM)) {
            // store its current value
            game.getState().setValue(
                    "ImminentDoomCount" + getSourceId().toString(),
                    imminentDoom.getCounters(game).getCount(CounterType.DOOM)
            );
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return rule;
    }
}

class ImminentDoomEffect extends OneShotEffect {

    public ImminentDoomEffect() {
        super(Outcome.Detriment);
    }

    public ImminentDoomEffect(final ImminentDoomEffect effect) {
        super(effect);
    }

    @Override
    public ImminentDoomEffect copy() {
        return new ImminentDoomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent imminentDoom = game.getPermanent(source.getSourceId());
        if (imminentDoom == null) {
            return false;
        }

        Effect effect = new DamageTargetEffect((int) game.getState().getValue("ImminentDoomCount" + source.getSourceId().toString()));
        effect.apply(game, source);
        imminentDoom.addCounters(CounterType.DOOM.createInstance(), source.getControllerId(), source, game);
        return true;
    }
}
