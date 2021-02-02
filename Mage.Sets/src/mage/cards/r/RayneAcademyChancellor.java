
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.EnchantedSourceCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class RayneAcademyChancellor extends CardImpl {

    public RayneAcademyChancellor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card. You may draw an additional card if Rayne, Academy Chancellor is enchanted.
        this.addAbility(new RayneAcademyChancellorTriggeredAbility());
    }

    private RayneAcademyChancellor(final RayneAcademyChancellor card) {
        super(card);
    }

    @Override
    public RayneAcademyChancellor copy() {
        return new RayneAcademyChancellor(this);
    }
}

class RayneAcademyChancellorTriggeredAbility extends TriggeredAbilityImpl {
    
    RayneAcademyChancellorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(2), new DrawCardSourceControllerEffect(1), new EnchantedSourceCondition(), "you may draw a card. You may draw an additional card if {this} is enchanted."), true);
    }
    
    RayneAcademyChancellorTriggeredAbility(final RayneAcademyChancellorTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public RayneAcademyChancellorTriggeredAbility copy() {
        return new RayneAcademyChancellorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        Player targetter = game.getPlayer(event.getPlayerId());
        if (controller != null && targetter != null && !controller.getId().equals(targetter.getId())) {
            if (event.getTargetId().equals(controller.getId())) {
                return true;
            }
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent != null && this.isControlledBy(permanent.getControllerId())) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card. You may draw an additional card if {this} is enchanted.";
    }
}
