
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class Lichenthrope extends CardImpl {

    public Lichenthrope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // If damage would be dealt to Lichenthrope, put that many -1/-1 counters on it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LichenthropeEffect()));

        // At the beginning of your upkeep, remove a -1/-1 counter from Lichenthrope.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance()), TargetController.YOU, false));
    }

    private Lichenthrope(final Lichenthrope card) {
        super(card);
    }

    @Override
    public Lichenthrope copy() {
        return new Lichenthrope(this);
    }
}

class LichenthropeEffect extends ReplacementEffectImpl {

    LichenthropeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "If damage would be dealt to {this}, put that many -1/-1 counters on it instead";
    }

    private LichenthropeEffect(final LichenthropeEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            p.addCounters(CounterType.M1M1.createInstance(damageEvent.getAmount()), source.getControllerId(), source, game);
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
    public LichenthropeEffect copy() {
        return new LichenthropeEffect(this);
    }
}
