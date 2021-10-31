package mage.cards.f;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlameChanneler extends CardImpl {

    public FlameChanneler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.transformable=true;this.secondSideCardClazz=mage.cards.e.EmbodimentOfFlame.class;

        // When a spell you control deals damage, transform Flame Channeler.
        this.addAbility(new TransformAbility());
        this.addAbility(new FlameChannelerTriggeredAbility());
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

    FlameChannelerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect(true));
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

    @Override
    public String getRule() {
        return "When a spell you control deals damage, transform {this}.";
    }
}
