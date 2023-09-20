

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;

/**
 *
 * @author Backfir3
 */
public final class TitaniasChosen extends CardImpl {

    public TitaniasChosen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
		
        // Whenever a player casts a green spell, put a +1/+1 counter on Titania's Chosen.
		this.addAbility(new TitaniasChosenAbility());
    }

    private TitaniasChosen(final TitaniasChosen card) {
        super(card);
    }

    @Override
    public TitaniasChosen copy() {
        return new TitaniasChosen(this);
    }

}

class TitaniasChosenAbility extends TriggeredAbilityImpl {

    public TitaniasChosenAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
    }

    private TitaniasChosenAbility(final TitaniasChosenAbility ability) {
        super(ability);
    }

    @Override
    public TitaniasChosenAbility copy() {
        return new TitaniasChosenAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spell.getColor(game).isGreen();
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a green spell, put a +1/+1 counter on Titania's Chosen.";
    }

}
