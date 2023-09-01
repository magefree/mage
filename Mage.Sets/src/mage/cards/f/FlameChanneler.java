package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
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
public final class FlameChanneler extends TransformingDoubleFacedCard {

    public FlameChanneler(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "{1}{R}",
                "Embodiment of Flame",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL, SubType.WIZARD}, "R"
        );
        this.getLeftHalfCard().setPT(2, 2);
        this.getRightHalfCard().setPT(3, 3);

        // When a spell you control deals damage, transform Flame Channeler.
        this.getLeftHalfCard().addAbility(new FlameChannelerTriggeredAbility(true));

        // Embodiment of Flame
        // Whenever a spell you control deals damage, put a flame counter on Embodiment of Flame.
        this.getRightHalfCard().addAbility(new FlameChannelerTriggeredAbility(false));

        // {1}, Remove a flame counter from Embodiment of Flame: Exile the top card of your library. You may play that card this turn.
        Ability ability = new SimpleActivatedAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1)
                        .setText("exile the top card of your library. You may play that card this turn"),
                new GenericManaCost(1)
        );
        ability.addCost(new RemoveCountersSourceCost(CounterType.FLAME.createInstance()));
        this.getRightHalfCard().addAbility(ability);
    }

    private FlameChanneler(final FlameChanneler card) {
        super(card);
    }

    @Override
    public FlameChanneler copy() {
        return new FlameChanneler(this);
    }
}

class FlameChannelerTriggeredAbility extends TriggeredAbilityImpl {

    FlameChannelerTriggeredAbility(boolean front) {
        super(Zone.BATTLEFIELD, front ? new TransformSourceEffect() : new AddCountersSourceEffect(CounterType.FLAME.createInstance()));
        this.setTriggerPhrase("When" + (front ? "" : "ever") + " a spell you control deals damage, ");
    }

    private FlameChannelerTriggeredAbility(final FlameChannelerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FlameChannelerTriggeredAbility copy() {
        return new FlameChannelerTriggeredAbility(this);
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
}
