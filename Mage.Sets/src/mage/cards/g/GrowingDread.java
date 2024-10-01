package mage.cards.g;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrowingDread extends CardImpl {

    public GrowingDread(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{U}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Growing Dread enters, manifest dread.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ManifestDreadEffect()));

        // Whenever you turn a permanent face up, put a +1/+1 counter on it.
        this.addAbility(new GrowingDreadTriggeredAbility());
    }

    private GrowingDread(final GrowingDread card) {
        super(card);
    }

    @Override
    public GrowingDread copy() {
        return new GrowingDread(this);
    }
}

class GrowingDreadTriggeredAbility extends TriggeredAbilityImpl {

    GrowingDreadTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on it"));
        setTriggerPhrase("Whenever you turn a permanent face up, ");
    }

    private GrowingDreadTriggeredAbility(final GrowingDreadTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GrowingDreadTriggeredAbility copy() {
        return new GrowingDreadTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TURNED_FACE_UP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(event.getPlayerId())) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
            return true;
        }
        return false;
    }
}
