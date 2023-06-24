package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.ExertAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Themberchaud extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.MOUNTAIN));
    private static final Hint hint = new ValueHint("Mountains you control", xValue);

    public Themberchaud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Themberchaud enters the battlefield, he deals X damage to each other creature without flying and each player, where X is the number of Mountains you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageAllEffect(xValue, filter)
                .setText("he deals X damage to each other creature without flying"));
        ability.addEffect(new DamagePlayersEffect(Outcome.Damage, xValue)
                .setText("and each player, where X is the number of Mountains you control"));
        this.addAbility(ability.addHint(hint));

        // You may exert Themberchaud as he attacks. When you do, he gains flying until end of turn.
        this.addAbility(new ExertAbility(new BecomesExertSourceTriggeredAbility(
                new GainAbilitySourceEffect(
                        FlyingAbility.getInstance(), Duration.EndOfTurn
                ).setText("he gains flying until end of turn")
        )));
    }

    private Themberchaud(final Themberchaud card) {
        super(card);
    }

    @Override
    public Themberchaud copy() {
        return new Themberchaud(this);
    }
}
