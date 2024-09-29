
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author nigelzor
 */
public final class ManaSkimmer extends CardImpl {

    public ManaSkimmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Mana Skimmer deals damage to a player, tap target land that player controls. That land doesn't untap during its controller's next untap step.
        this.addAbility(new ManaSkimmerTriggeredAbility());
    }

    private ManaSkimmer(final ManaSkimmer card) {
        super(card);
    }

    @Override
    public ManaSkimmer copy() {
        return new ManaSkimmer(this);
    }
}

class ManaSkimmerTriggeredAbility extends TriggeredAbilityImpl {

    ManaSkimmerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TapTargetEffect(), false);
        addEffect(new DontUntapInControllersNextUntapStepTargetEffect());
    }

    private ManaSkimmerTriggeredAbility(final ManaSkimmerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ManaSkimmerTriggeredAbility copy() {
        return new ManaSkimmerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent source = game.getPermanent(event.getSourceId());
        if (source != null && source.getId().equals(this.getSourceId())) {
            FilterLandPermanent filter = new FilterLandPermanent("land that player controls");
            filter.add(new ControllerIdPredicate(event.getPlayerId()));
            filter.setMessage("land controlled by " + game.getPlayer(event.getTargetId()).getLogName());
            this.getTargets().clear();
            this.addTarget(new TargetPermanent(filter));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage to a player, tap target land that player controls. That land doesn't untap during its controller's next untap step.";
    }
}
