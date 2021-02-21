package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BurningYardTrainer extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.KNIGHT);

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BurningYardTrainer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Burning-Yard Trainer enters the battlefield, another target Knight you control gets +2/+2 and gains trample and haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostTargetEffect(2, 2, Duration.EndOfTurn)
                        .setText("another target Knight you control gets +2/+2")
        );
        ability.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample"));
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and haste until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BurningYardTrainer(final BurningYardTrainer card) {
        super(card);
    }

    @Override
    public BurningYardTrainer copy() {
        return new BurningYardTrainer(this);
    }
}
