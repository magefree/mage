
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author TheElk801
 */
public final class MountainTitan extends CardImpl {

    public MountainTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{R}{R}: Until end of turn, whenever you cast a black spell, put a +1/+1 counter on Mountain Titan.
        this.addAbility(new SimpleActivatedAbility(
                new CreateDelayedTriggeredAbilityEffect(new MountainTitanDelayedTriggeredAbility())
                        .setText("until end of turn, whenever you cast a black spell, put a +1/+1 counter on {this}"),
                new ManaCostsImpl<>("{1}{R}{R}")
        ));
    }

    private MountainTitan(final MountainTitan card) {
        super(card);
    }

    @Override
    public MountainTitan copy() {
        return new MountainTitan(this);
    }
}

class MountainTitanDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public MountainTitanDelayedTriggeredAbility() {
        super(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), Duration.EndOfTurn, false, false);
    }

    public MountainTitanDelayedTriggeredAbility(final MountainTitanDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MountainTitanDelayedTriggeredAbility copy() {
        return new MountainTitanDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.getColor(game).isBlack()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "whenever you cast a black spell, put a +1/+1 counter on {this}";
    }
}
