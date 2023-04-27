package mage.cards.c;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.TrainingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CloakedCadet extends CardImpl {

    public CloakedCadet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Training
        this.addAbility(new TrainingAbility());

        // Whenever one or more +1/+1 counters are put on one or more Humans you control, draw a card. This ability triggers only once each turn.
        this.addAbility(new CloakedCadetTriggeredAbility());
    }

    private CloakedCadet(final CloakedCadet card) {
        super(card);
    }

    @Override
    public CloakedCadet copy() {
        return new CloakedCadet(this);
    }
}

class CloakedCadetTriggeredAbility extends TriggeredAbilityImpl {

    CloakedCadetTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.setTriggersOnce(true);
        setTriggerPhrase("Whenever one or more +1/+1 counters are put on one or more Humans you control, ");
    }

    private CloakedCadetTriggeredAbility(final CloakedCadetTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CloakedCadetTriggeredAbility copy() {
        return new CloakedCadetTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null
                && isControlledBy(permanent.getControllerId())
                && permanent.hasSubtype(SubType.HUMAN, game)
                && event.getData().equals(CounterType.P1P1.getName());
    }
}
