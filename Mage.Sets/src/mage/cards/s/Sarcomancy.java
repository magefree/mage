
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author LevelX2
 */
public final class Sarcomancy extends CardImpl {
   
    public Sarcomancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");


        // When Sarcomancy enters the battlefield, create a 2/2 black Zombie creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ZombieToken(), 1), false));
        // At the beginning of your upkeep, if there are no Zombies on the battlefield, Sarcomancy deals 1 damage to you.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DamageControllerEffect(1), TargetController.YOU, false),
                new PermanentsOnTheBattlefieldCondition(new FilterPermanent(SubType.ZOMBIE, "Zombies"), ComparisonType.EQUAL_TO, 0, false),
                "At the beginning of your upkeep, if there are no Zombies on the battlefield, {this} deals 1 damage to you."));        
    }

    private Sarcomancy(final Sarcomancy card) {
        super(card);
    }

    @Override
    public Sarcomancy copy() {
        return new Sarcomancy(this);
    }
}
