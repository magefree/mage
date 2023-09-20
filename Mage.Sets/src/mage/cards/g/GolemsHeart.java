
package mage.cards.g;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author Loki
 */
public final class GolemsHeart extends CardImpl {

    public GolemsHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Whenever a player casts an artifact spell, you may gain 1 life.
        this.addAbility(new GolemsHeartAbility());
    }

    private GolemsHeart(final GolemsHeart card) {
        super(card);
    }

    @Override
    public GolemsHeart copy() {
        return new GolemsHeart(this);
    }

}

class GolemsHeartAbility extends TriggeredAbilityImpl {

    public GolemsHeartAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1), true);
    }

    private GolemsHeartAbility(final GolemsHeartAbility ability) {
        super(ability);
    }

    @Override
    public GolemsHeartAbility copy() {
        return new GolemsHeartAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spell.isArtifact(game);
    }

    @Override
    public String getRule() {
        return "Whenever a player casts an artifact spell, you may gain 1 life.";
    }

}
