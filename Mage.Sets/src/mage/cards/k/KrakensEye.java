

package mage.cards.k;

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
public final class KrakensEye extends CardImpl {

    public KrakensEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.addAbility(new KrakensEyeAbility());
    }

    private KrakensEye(final KrakensEye card) {
        super(card);
    }

    @Override
    public KrakensEye copy() {
        return new KrakensEye(this);
    }

}

class KrakensEyeAbility extends TriggeredAbilityImpl {

    public KrakensEyeAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1), true);
    }

    private KrakensEyeAbility(final KrakensEyeAbility ability) {
        super(ability);
    }

    @Override
    public KrakensEyeAbility copy() {
        return new KrakensEyeAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spell.getColor(game).isBlue();
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a blue spell, you may gain 1 life.";
    }

}
