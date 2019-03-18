
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author TheElk801
 */
public final class HallarTheFirefletcher extends CardImpl {

    public HallarTheFirefletcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast a spell, if that spell was kicked, put a +1/+1 counter on Hallar, the Firefletcher, then Hallar deals damage equal to the number of +1/+1 counters on it to each opponent.
        this.addAbility(new HallarTheFirefletcherTriggeredAbility());
    }

    public HallarTheFirefletcher(final HallarTheFirefletcher card) {
        super(card);
    }

    @Override
    public HallarTheFirefletcher copy() {
        return new HallarTheFirefletcher(this);
    }
}

class HallarTheFirefletcherTriggeredAbility extends TriggeredAbilityImpl {

    HallarTheFirefletcherTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on {this}"), true);
        this.addEffect(new DamagePlayersEffect(Outcome.Benefit, new CountersSourceCount(CounterType.P1P1), TargetController.OPPONENT)
                .setText("then {this} deals damage equal to the number of +1/+1 counters on it to each opponent")
        );
    }

    HallarTheFirefletcherTriggeredAbility(final HallarTheFirefletcherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HallarTheFirefletcherTriggeredAbility copy() {
        return new HallarTheFirefletcherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.isControlledBy(controllerId)) {
            for (Ability ability : spell.getAbilities()) {
                if (ability instanceof KickerAbility && ((KickerAbility) ability).getKickedCounter(game, spell.getSpellAbility()) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell, if that spell was kicked," + super.getRule();
    }
}
