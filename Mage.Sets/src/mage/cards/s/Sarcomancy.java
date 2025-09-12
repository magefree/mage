package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Sarcomancy extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterPermanent(SubType.ZOMBIE, "there are no Zombies on the battlefield"),
            ComparisonType.EQUAL_TO, 0, false
    );

    public Sarcomancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // When Sarcomancy enters the battlefield, create a 2/2 black Zombie creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ZombieToken())));

        // At the beginning of your upkeep, if there are no Zombies on the battlefield, Sarcomancy deals 1 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DamageControllerEffect(1)).withInterveningIf(condition));
    }

    private Sarcomancy(final Sarcomancy card) {
        super(card);
    }

    @Override
    public Sarcomancy copy() {
        return new Sarcomancy(this);
    }
}
