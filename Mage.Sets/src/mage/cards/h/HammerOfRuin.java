

package mage.cards.h;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class HammerOfRuin extends CardImpl {

    public HammerOfRuin (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 0)));
        // Whenever equipped creature deals combat damage to a player, you may destroy target Equipment that player controls.
        this.addAbility(new HammerOfRuinTriggeredAbility());
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));
    }

    public HammerOfRuin (final HammerOfRuin card) {
        super(card);
    }

    @Override
    public HammerOfRuin copy() {
        return new HammerOfRuin(this);
    }

}

class HammerOfRuinTriggeredAbility extends TriggeredAbilityImpl {

    HammerOfRuinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), true);
    }

    HammerOfRuinTriggeredAbility(final HammerOfRuinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HammerOfRuinTriggeredAbility copy() {
        return new HammerOfRuinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
        Permanent p = game.getPermanent(event.getSourceId());
        if (damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId())) {
            FilterPermanent filter = new FilterPermanent("Equipment that player controls");
            filter.add(SubType.EQUIPMENT.getPredicate());
            filter.add(new ControllerIdPredicate(event.getPlayerId()));
            filter.setMessage("creature controlled by " + game.getPlayer(event.getTargetId()).getLogName());

            this.getTargets().clear();
            this.addTarget(new TargetPermanent(filter));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, you may destroy target Equipment that player controls.";
    }
}
