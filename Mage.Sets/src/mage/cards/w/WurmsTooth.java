

package mage.cards.w;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class WurmsTooth extends CardImpl {

    public WurmsTooth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.addAbility(new WurmsToothAbility());
    }

    private WurmsTooth(final WurmsTooth card) {
        super(card);
    }

    @Override
    public WurmsTooth copy() {
        return new WurmsTooth(this);
    }

}

class WurmsToothAbility extends TriggeredAbilityImpl {

    public WurmsToothAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1), true);
    }

    private WurmsToothAbility(final WurmsToothAbility ability) {
        super(ability);
    }

    @Override
    public WurmsToothAbility copy() {
        return new WurmsToothAbility(this);
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
        return "Whenever a player casts a green spell, you may gain 1 life.";
    }

}
