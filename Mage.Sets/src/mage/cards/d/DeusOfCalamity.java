
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class DeusOfCalamity extends CardImpl {

    public DeusOfCalamity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R/G}{R/G}{R/G}{R/G}{R/G}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Deus of Calamity deals 6 or more damage to an opponent, destroy target land that player controls.
        this.addAbility(new DeusOfCalamityTriggeredAbility());
    }

    private DeusOfCalamity(final DeusOfCalamity card) {
        super(card);
    }

    @Override
    public DeusOfCalamity copy() {
        return new DeusOfCalamity(this);
    }
}

class DeusOfCalamityTriggeredAbility extends TriggeredAbilityImpl {

    public DeusOfCalamityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }

    private DeusOfCalamityTriggeredAbility(final DeusOfCalamityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DeusOfCalamityTriggeredAbility copy() {
        return new DeusOfCalamityTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())
                && event.getAmount() > 5
                && game.getOpponents(this.getControllerId()).contains(event.getTargetId())) {
            FilterLandPermanent filter = new FilterLandPermanent("land of the damaged player");
            filter.add(new ControllerIdPredicate(event.getTargetId()));
            Target target = new TargetLandPermanent(filter);
            this.getTargets().clear();
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever Deus of Calamity deals 6 or more damage to an opponent, destroy target land that player controls.";
    }
}
