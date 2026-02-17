package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.token.FoodAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpicyOatmealPizza extends CardImpl {

    public SpicyOatmealPizza(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.subtype.add(SubType.FOOD);

        // When this artifact enters, it deals 4 damage to any target and 3 damage to you.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(4));
        ability.addEffect(new DamageControllerEffect(3).setText("and 3 damage to you"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {2}, {T}, Sacrifice this artifact: You gain 3 life.
        this.addAbility(new FoodAbility());
    }

    private SpicyOatmealPizza(final SpicyOatmealPizza card) {
        super(card);
    }

    @Override
    public SpicyOatmealPizza copy() {
        return new SpicyOatmealPizza(this);
    }
}
