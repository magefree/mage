
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author emerald000
 */
public final class CephalidConstable extends CardImpl {

    public CephalidConstable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.CEPHALID);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Cephalid Constable deals combat damage to a player, return up to that many target permanents that player controls to their owners' hands.
        Ability ability = new CephalidConstableTriggeredAbility();
        ability.addTarget(new TargetPermanent(0, 1, new FilterPermanent(), false)); // Ajusted when it triggers.
        this.addAbility(ability);
    }

    private CephalidConstable(final CephalidConstable card) {
        super(card);
    }

    @Override
    public CephalidConstable copy() {
        return new CephalidConstable(this);
    }
}

class CephalidConstableTriggeredAbility extends TriggeredAbilityImpl {

    CephalidConstableTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), false);
    }

    CephalidConstableTriggeredAbility(final CephalidConstableTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CephalidConstableTriggeredAbility copy() {
        return new CephalidConstableTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.sourceId) && ((DamagedPlayerEvent) event).isCombatDamage()) {
            FilterPermanent filter = new FilterPermanent("permanent" + (event.getAmount() > 1 ? "s" : "") + " damaged player control");
            filter.add(new ControllerIdPredicate(event.getPlayerId()));
            Target target = new TargetPermanent(0, event.getAmount(), filter, false);
            this.getTargets().clear();
            this.getTargets().add(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, return up to that many target permanents that player controls to their owner's hand.";
    }

}
