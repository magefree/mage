
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.DevourAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class BloodsporeThrinax extends CardImpl {

    public BloodsporeThrinax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Devour 1
        this.addAbility(new DevourAbility(1));
        
        // Each other creature you control enters the battlefield with an additional X +1/+1 counters on it, where X is the number of +1/+1 counters on Bloodspire Thrinax.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BloodsporeThrinaxEntersBattlefieldEffect()));
    }

    private BloodsporeThrinax(final BloodsporeThrinax card) {
        super(card);
    }

    @Override
    public BloodsporeThrinax copy() {
        return new BloodsporeThrinax(this);
    }
}

class BloodsporeThrinaxEntersBattlefieldEffect extends ReplacementEffectImpl {

    public BloodsporeThrinaxEntersBattlefieldEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Each other creature you control enters the battlefield with an additional X +1/+1 counters on it, where X is the number of +1/+1 counters on {this}";
    }

    public BloodsporeThrinaxEntersBattlefieldEffect(BloodsporeThrinaxEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null && creature.isControlledBy(source.getControllerId())
                && creature.isCreature(game)
                && !event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent sourceCreature = game.getPermanent(source.getSourceId());
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (sourceCreature != null && creature != null) {
            int amount = sourceCreature.getCounters(game).getCount(CounterType.P1P1);
            if (amount > 0) {
                creature.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
            }
        }
        return false;
    }

    @Override
    public BloodsporeThrinaxEntersBattlefieldEffect copy() {
        return new BloodsporeThrinaxEntersBattlefieldEffect(this);
    }
}
