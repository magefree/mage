package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SunriseSovereign extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Giant creatures");

    static {
        filter.add(SubType.GIANT.getPredicate());
    }

    public SunriseSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Other Giant creatures you control get +2/+2 and have trample.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                2, 2, Duration.WhileOnBattlefield, filter, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        ).setText("and have trample"));
        this.addAbility(ability);
    }

    private SunriseSovereign(final SunriseSovereign card) {
        super(card);
    }

    @Override
    public SunriseSovereign copy() {
        return new SunriseSovereign(this);
    }
}
