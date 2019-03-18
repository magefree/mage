
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class HardenedScales extends CardImpl {

    public HardenedScales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");

        // If one or more +1/+1 counters would be put on a creature you control, that many plus one +1/+1 counters are put on it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HardenedScalesEffect()));

    }

    public HardenedScales(final HardenedScales card) {
        super(card);
    }

    @Override
    public HardenedScales copy() {
        return new HardenedScales(this);
    }
}

class HardenedScalesEffect extends ReplacementEffectImpl {

    HardenedScalesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "If one or more +1/+1 counters would be put on a creature you control, that many plus one +1/+1 counters are put on it instead";
    }

    HardenedScalesEffect(final HardenedScalesEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int amount = event.getAmount();
        if (amount >= 1) {
            event.setAmount(amount + 1);
        }        
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getData().equals(CounterType.P1P1.getName())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            if (permanent != null && permanent.isControlledBy(source.getControllerId())
                    && permanent.isCreature()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HardenedScalesEffect copy() {
        return new HardenedScalesEffect(this);
    }
}
