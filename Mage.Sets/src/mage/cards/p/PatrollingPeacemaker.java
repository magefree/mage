package mage.cards.p;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PatrollingPeacemaker extends CardImpl {

    public PatrollingPeacemaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // This creature enters with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                "with two +1/+1 counters on it"
        ));

        // Whenever an opponent commits a crime, proliferate.
        this.addAbility(new PatrollingPeacemakerTriggeredAbility());
    }

    private PatrollingPeacemaker(final PatrollingPeacemaker card) {
        super(card);
    }

    @Override
    public PatrollingPeacemaker copy() {
        return new PatrollingPeacemaker(this);
    }
}

class PatrollingPeacemakerTriggeredAbility extends TriggeredAbilityImpl {

    PatrollingPeacemakerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ProliferateEffect());
        setTriggerPhrase("Whenever an opponent commits a crime, ");
    }

    private PatrollingPeacemakerTriggeredAbility(final PatrollingPeacemakerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PatrollingPeacemakerTriggeredAbility copy() {
        return new PatrollingPeacemakerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_CAST:
            case ACTIVATED_ABILITY:
            case TRIGGERED_ABILITY:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getOpponents(getControllerId()).contains(CommittedCrimeTriggeredAbility.getCriminal(event, game));
    }
}
