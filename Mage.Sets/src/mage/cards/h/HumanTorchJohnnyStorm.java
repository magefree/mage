package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetOpponent;
import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HumanTorchJohnnyStorm extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.HERO, "you control another Hero");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public HumanTorchJohnnyStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you draw a card, if you control another Hero, Human Torch deals 1 damage to target opponent.
        Ability ability = new DrawCardControllerTriggeredAbility(new DamageTargetEffect(1), false)
            .withInterveningIf(condition);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Power-up -- {6}{R}: Put three +1/+1 counters on Human Torch.
        this.addAbility(new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
            new ManaCostsImpl<>("{6}{R}")
        ));
    }

    private HumanTorchJohnnyStorm(final HumanTorchJohnnyStorm card) {
        super(card);
    }

    @Override
    public HumanTorchJohnnyStorm copy() {
        return new HumanTorchJohnnyStorm(this);
    }
}
