
package mage.cards.d;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.FlashAbility;
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
 * @author LevelX2
 */
public final class DictateOfKruphix extends CardImpl {

    public DictateOfKruphix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}{U}");


        // Flash
        this.addAbility(FlashAbility.getInstance());
        // At the beginning of each player's draw step, that player draws an additional card.
        this.addAbility(new DictateOfKruphixAbility());
    }

    private DictateOfKruphix(final DictateOfKruphix card) {
        super(card);
    }

    @Override
    public DictateOfKruphix copy() {
        return new DictateOfKruphix(this);
    }
}

class DictateOfKruphixAbility extends TriggeredAbilityImpl {

    public DictateOfKruphixAbility() {
        super(Zone.BATTLEFIELD, new DrawCardTargetEffect(1));
    }

    private DictateOfKruphixAbility(final DictateOfKruphixAbility ability) {
        super(ability);
    }

    @Override
    public DictateOfKruphixAbility copy() {
        return new DictateOfKruphixAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
        return true;
    }

    @Override
    public String getRule() {
        return "At the beginning of each player's draw step, that player draws an additional card.";
    }

}