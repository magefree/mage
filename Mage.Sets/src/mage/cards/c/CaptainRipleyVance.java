package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author weirddan455
 */
public final class CaptainRipleyVance extends CardImpl {

    public CaptainRipleyVance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you cast your third spell each turn, put a +1/+1 counter on Captain Ripley Vance, then it deals damage equal to its power to any target.
        this.addAbility(new CaptainRipleyVanceTriggeredAbility(), new SpellsCastWatcher());
    }

    private CaptainRipleyVance(final CaptainRipleyVance card) {
        super(card);
    }

    @Override
    public CaptainRipleyVance copy() {
        return new CaptainRipleyVance(this);
    }
}

class CaptainRipleyVanceTriggeredAbility extends TriggeredAbilityImpl {

    public CaptainRipleyVanceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        addEffect(new DamageTargetEffect(new SourcePermanentPowerCount()).setText("it deals damage equal to its power to any target").concatBy(", then"));
        addTarget(new TargetAnyTarget());
        setTriggerPhrase("Whenever you cast your third spell each turn, ");
    }

    private CaptainRipleyVanceTriggeredAbility(final CaptainRipleyVanceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CaptainRipleyVanceTriggeredAbility copy() {
        return new CaptainRipleyVanceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            return watcher != null && watcher.getSpellsCastThisTurn(this.getControllerId()).size() == 3;
        }
        return false;
    }
}
