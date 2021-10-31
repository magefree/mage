package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmbodimentOfFlame extends CardImpl {

    public EmbodimentOfFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setRed(true);
        this.nightCard = true;
        this.transformable = true;

        // Whenever a spell you control deals damage, put a flame counter on Embodiment of Flame.
        this.addAbility(new EmbodimentOfFlameTriggeredAbility());

        // {1}, Remove a flame counter from Embodiment of Flame: Exile the top card of your library. You may play that card this turn.
        Ability ability = new SimpleActivatedAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1)
                        .setText("exile the top card of your library. You may play that card this turn"),
                new GenericManaCost(1)
        );
        ability.addCost(new RemoveCountersSourceCost(CounterType.FLAME.createInstance()));
        this.addAbility(ability);
    }

    private EmbodimentOfFlame(final EmbodimentOfFlame card) {
        super(card);
    }

    @Override
    public EmbodimentOfFlame copy() {
        return new EmbodimentOfFlame(this);
    }
}

class EmbodimentOfFlameTriggeredAbility extends TriggeredAbilityImpl {

    EmbodimentOfFlameTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.FLAME.createInstance()));
    }

    private EmbodimentOfFlameTriggeredAbility(final EmbodimentOfFlameTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EmbodimentOfFlameTriggeredAbility copy() {
        return new EmbodimentOfFlameTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpellOrLKIStack(event.getSourceId());
        return spell != null && isControlledBy(spell.getControllerId()) && spell.isInstantOrSorcery(game);
    }

    @Override
    public String getRule() {
        return "Whenever a spell you control deals damage, put a flame counter on {this}.";
    }
}
