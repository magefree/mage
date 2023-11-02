
package mage.cards.f;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class FontOfMythos extends CardImpl {

    public FontOfMythos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.addAbility(new FontOfMythosAbility());
    }

    private FontOfMythos(final FontOfMythos card) {
        super(card);
    }

    @Override
    public FontOfMythos copy() {
        return new FontOfMythos(this);
    }

}

class FontOfMythosAbility extends TriggeredAbilityImpl {

    public FontOfMythosAbility() {
        super(Zone.BATTLEFIELD, new DrawCardTargetEffect(2));
    }

    private FontOfMythosAbility(final FontOfMythosAbility ability) {
        super(ability);
    }

    @Override
    public FontOfMythosAbility copy() {
        return new FontOfMythosAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        this.getEffects().get(0).setTargetPointer(new FixedTarget(game.getActivePlayerId(), game));
        return true;
    }

    @Override
    public String getRule() {
        return "At the beginning of each player's draw step, that player draws two additional cards.";
    }

}
