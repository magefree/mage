package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author gp66
 */
public final class EssenceSymbiote extends CardImpl {

    public EssenceSymbiote(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature you control mutates, put a +1/+1 counter on that creature and you gain 2 life.
        Ability ability = new EssenceSymbioteTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on that creature"));
        // You gain 2 life when Essence Symbiote’s ability resolves, even if you can’t put a +1/+1 counter on the mutated creature
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private EssenceSymbiote(final EssenceSymbiote card) {
        super(card);
    }

    @Override
    public EssenceSymbiote copy() {
        return new EssenceSymbiote(this);
    }
}

class EssenceSymbioteTriggeredAbility extends TriggeredAbilityImpl {

    EssenceSymbioteTriggeredAbility(Zone zone, Effect effect) {
        super(zone, effect, false);
        setTriggerPhrase("Whenever a creature you control mutates, ");
    }

    private EssenceSymbioteTriggeredAbility(final EssenceSymbioteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EssenceSymbioteTriggeredAbility copy() {
        return new EssenceSymbioteTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        // TODO: Implement this
        //return event.getType() == GameEvent.EventType.CREATURE_MUTATED;
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // TODO: Implement this
        return false;
    }
}
