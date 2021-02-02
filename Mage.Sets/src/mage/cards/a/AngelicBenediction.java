
package mage.cards.a;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class AngelicBenediction extends CardImpl {

    public AngelicBenediction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");


        this.addAbility(new ExaltedAbility());
        // Whenever a creature you control attacks alone, you may tap target creature.
        this.addAbility(new AngelicBenedictionTriggeredAbility());
    }

    private AngelicBenediction(final AngelicBenediction card) {
        super(card);
    }

    @Override
    public AngelicBenediction copy() {
        return new AngelicBenediction(this);
    }
}

class AngelicBenedictionTriggeredAbility extends TriggeredAbilityImpl {

    public AngelicBenedictionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TapTargetEffect(), true);
        this.addTarget(new TargetCreaturePermanent());
    }

    public AngelicBenedictionTriggeredAbility(final AngelicBenedictionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AngelicBenedictionTriggeredAbility copy() {
        return new AngelicBenedictionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.isActivePlayer(this.controllerId)) {
            if (game.getCombat().attacksAlone()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control attacks alone, you may tap target creature";
    }
}