
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author Loki
 */
public final class BlindCreeper extends CardImpl {

    public BlindCreeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE, SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a player casts a spell, Blind Creeper gets -1/-1 until end of turn.
        this.addAbility(new BlindCreeperAbility());
    }

    private BlindCreeper(final BlindCreeper card) {
        super(card);
    }

    @Override
    public BlindCreeper copy() {
        return new BlindCreeper(this);
    }
}

class BlindCreeperAbility extends TriggeredAbilityImpl {

    public BlindCreeperAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(-1, -1, Duration.EndOfTurn), false);
        setTriggerPhrase("Whenever a player casts a spell, ");
    }

    public BlindCreeperAbility(final BlindCreeperAbility ability) {
        super(ability);
    }

    @Override
    public BlindCreeperAbility copy() {
        return new BlindCreeperAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getStack().getSpell(event.getTargetId()) != null;
    }
}