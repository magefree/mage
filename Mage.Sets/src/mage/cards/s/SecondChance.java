
package mage.cards.s;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.XorLessLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class SecondChance extends CardImpl {

    public SecondChance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");


        // At the beginning of your upkeep, if you have 5 or less life, sacrifice Second Chance and take an extra turn after this one.
        TriggeredAbility ability  = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(), TargetController.YOU, false);
        ability.addEffect(new AddExtraTurnControllerEffect());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new XorLessLifeCondition(XorLessLifeCondition.CheckType.CONTROLLER, 5), "At the beginning of your upkeep, if you have 5 or less life, sacrifice {this} and take an extra turn after this one"));
        
    }

    private SecondChance(final SecondChance card) {
        super(card);
    }

    @Override
    public SecondChance copy() {
        return new SecondChance(this);
    }
    

}
