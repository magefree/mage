
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;

/**
 *
 * @author Plopman
 */
public final class ImpendingDisaster extends CardImpl {

    public ImpendingDisaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");


        // At the beginning of your upkeep, if there are seven or more lands on the battlefield, sacrifice Impending Disaster and destroy all lands.
        TriggeredAbility ability  = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(), TargetController.YOU, false);
        ability.addEffect(new DestroyAllEffect(new FilterLandPermanent()));
        ImpendingDisasterCondition contition = new ImpendingDisasterCondition();
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, contition, "At the beginning of your upkeep, if there are seven or more lands on the battlefield, sacrifice {this} and destroy all lands"));
        
    }

    private ImpendingDisaster(final ImpendingDisaster card) {
        super(card);
    }

    @Override
    public ImpendingDisaster copy() {
        return new ImpendingDisaster(this);
    }
    
    class ImpendingDisasterCondition implements Condition {

        @Override
        public boolean apply(Game game, Ability source) {
            return game.getBattlefield().count(new FilterLandPermanent(), source.getControllerId(), source, game) >= 7;
        }
    }
}
