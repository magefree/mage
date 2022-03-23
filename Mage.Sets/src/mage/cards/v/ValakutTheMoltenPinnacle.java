
package mage.cards.v;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Viserion
 */
public final class ValakutTheMoltenPinnacle extends CardImpl {

    static final FilterLandPermanent filter = new FilterLandPermanent("Mountain");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public ValakutTheMoltenPinnacle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        // Valakut, the Molten Pinnacle enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // Whenever a Mountain enters the battlefield under your control, if you control at least five other Mountains, you may have Valakut, the Molten Pinnacle deal 3 damage to any target.
        this.addAbility(new ValakutTheMoltenPinnacleTriggeredAbility());
        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

    }

    private ValakutTheMoltenPinnacle(final ValakutTheMoltenPinnacle card) {
        super(card);
    }

    @Override
    public ValakutTheMoltenPinnacle copy() {
        return new ValakutTheMoltenPinnacle(this);
    }
}

class ValakutTheMoltenPinnacleTriggeredAbility extends TriggeredAbilityImpl {

    ValakutTheMoltenPinnacleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(3), true);
        this.addTarget(new TargetAnyTarget());
    }

    ValakutTheMoltenPinnacleTriggeredAbility(ValakutTheMoltenPinnacleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getBattlefield().count(ValakutTheMoltenPinnacle.filter, getControllerId(), this, game) > 5;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isLand(game) && permanent.isControlledBy(this.getControllerId())) {
            if (permanent.hasSubtype(SubType.MOUNTAIN, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ValakutTheMoltenPinnacleTriggeredAbility copy() {
        return new ValakutTheMoltenPinnacleTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a Mountain enters the battlefield under your control, if you control at least five other Mountains, you may have {this} deal 3 damage to any target.";
    }
}
