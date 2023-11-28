
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public final class Phytohydra extends CardImpl {

    public Phytohydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{W}{W}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // If damage would be dealt to Phytohydra, put that many +1/+1 counters on it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhytohydraEffect()));
    }

    private Phytohydra(final Phytohydra card) {
        super(card);
    }

    @Override
    public Phytohydra copy() {
        return new Phytohydra(this);
    }
}

class PhytohydraEffect extends ReplacementEffectImpl {
    PhytohydraEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "If damage would be dealt to {this}, put that many +1/+1 counters on it instead";
    }

    private PhytohydraEffect(final PhytohydraEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            p.addCounters(CounterType.P1P1.createInstance(damageEvent.getAmount()), source.getControllerId(), source, game);
        }
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public PhytohydraEffect copy() {
        return new PhytohydraEffect(this);
    }
}
