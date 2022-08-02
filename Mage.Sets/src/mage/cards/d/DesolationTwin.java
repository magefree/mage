
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.EldraziToken;
import mage.game.stack.Spell;

/**
 *
 * @author fireshoes
 */
public final class DesolationTwin extends CardImpl {

    public DesolationTwin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // When you cast Desolation Twin, create a 10/10 colorless Eldrazi creature token.
        this.addAbility(new DesolationTwinOnCastAbility());
    }

    private DesolationTwin(final DesolationTwin card) {
        super(card);
    }

    @Override
    public DesolationTwin copy() {
        return new DesolationTwin(this);
    }
}

class DesolationTwinOnCastAbility extends TriggeredAbilityImpl {

    DesolationTwinOnCastAbility() {
        super(Zone.STACK, new CreateTokenEffect(new EldraziToken()));
        setTriggerPhrase("When you cast this spell, ");
    }

    DesolationTwinOnCastAbility(DesolationTwinOnCastAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = (Spell) game.getObject(event.getTargetId());
        return this.getSourceId().equals(spell.getSourceId());
    }

    @Override
    public DesolationTwinOnCastAbility copy() {
        return new DesolationTwinOnCastAbility(this);
    }
}
