
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

/**
 *
 * @author djbrez
 */
public final class DawningPurist extends CardImpl {

    public DawningPurist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Dawning Purist deals combat damage to a player, you may destroy target enchantment that player controls.
        this.addAbility(new DawningPuristTriggeredAbility());

        // Morph {1}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{1}{W}")));
    }

    private DawningPurist(final DawningPurist card) {
        super(card);
    }

    @Override
    public DawningPurist copy() {
        return new DawningPurist(this);
    }
}

class DawningPuristTriggeredAbility extends TriggeredAbilityImpl {

    DawningPuristTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), true);
    }

    DawningPuristTriggeredAbility(final DawningPuristTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DawningPuristTriggeredAbility copy() {
        return new DawningPuristTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
            FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("enchantment that player controls");
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
        return "Whenever {this} deals combat damage to a player, you may destroy target enchantment that player controls.";
    }
}
