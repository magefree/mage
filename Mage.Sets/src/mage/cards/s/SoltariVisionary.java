
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class SoltariVisionary extends CardImpl {

    public SoltariVisionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.SOLTARI);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());

        // Whenever Soltari Visionary deals damage to a player, destroy target enchantment that player controls.
        this.addAbility(new SoltariVisionaryTriggeredAbility());
    }

    private SoltariVisionary(final SoltariVisionary card) {
        super(card);
    }

    @Override
    public SoltariVisionary copy() {
        return new SoltariVisionary(this);
    }
}

class SoltariVisionaryTriggeredAbility extends TriggeredAbilityImpl {

    SoltariVisionaryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }

    private SoltariVisionaryTriggeredAbility(final SoltariVisionaryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SoltariVisionaryTriggeredAbility copy() {
        return new SoltariVisionaryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent soltari = game.getPermanent(event.getSourceId());
        if (soltari != null && soltari.getId().equals(this.getSourceId())) {
            FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("enchantment that player controls.");
            filter.add(new ControllerIdPredicate(event.getPlayerId()));
            filter.setMessage("enchantment controlled by " + game.getPlayer(event.getTargetId()).getLogName());
            this.getTargets().clear();
            this.addTarget(new TargetPermanent(filter));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage to a player, destroy target enchantment that player controls.";
    }
}
