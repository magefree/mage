
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author North
 */
public final class Incinerate extends CardImpl {

    public Incinerate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new IncinerateEffect());
        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));
    }

    private Incinerate(final Incinerate card) {
        super(card);
    }

    @Override
    public Incinerate copy() {
        return new Incinerate(this);
    }
}

class IncinerateEffect extends ContinuousRuleModifyingEffectImpl {

    public IncinerateEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment, true, false);
        staticText = "A creature dealt damage this way can't be regenerated this turn";
    }

    private IncinerateEffect(final IncinerateEffect effect) {
        super(effect);
    }

    @Override
    public IncinerateEffect copy() {
        return new IncinerateEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.REGENERATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        DamagedByWatcher watcher = game.getState().getWatcher(DamagedByWatcher.class, source.getSourceId());
        if (watcher != null) {
            return watcher.wasDamaged(event.getTargetId(), game);
        }
        return false;
    }

}
