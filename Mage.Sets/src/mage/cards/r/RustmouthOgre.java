
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPermanent;

/**
 *
 * @author dustinconrad
 */
public final class RustmouthOgre extends CardImpl {

    public RustmouthOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.OGRE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever Rustmouth Ogre deals combat damage to a player, you may destroy target artifact that player controls.
        this.addAbility(new RustmouthOgreTriggeredAbility());
    }

    private RustmouthOgre(final RustmouthOgre card) {
        super(card);
    }

    @Override
    public RustmouthOgre copy() {
        return new RustmouthOgre(this);
    }
}

class RustmouthOgreTriggeredAbility extends TriggeredAbilityImpl {

    RustmouthOgreTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), true);
    }

    private RustmouthOgreTriggeredAbility(final RustmouthOgreTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RustmouthOgreTriggeredAbility copy() {
        return new RustmouthOgreTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
            FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact that player controls");
            filter.add(new ControllerIdPredicate(event.getPlayerId()));
            filter.setMessage("artifact controlled by " + game.getPlayer(event.getTargetId()).getLogName());

            this.getTargets().clear();
            this.addTarget(new TargetPermanent(filter));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may destroy target artifact that player controls.";
    }
}