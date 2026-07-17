package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SukiKyoshiCaptain extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.WARRIOR, "Warriors");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.WARRIOR, "attacking Warriors");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public SukiKyoshiCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other Warriors you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // {3}{W}: Attacking Warriors you control gain double strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilityControlledEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, filter2
        ), new ManaCostsImpl<>("{3}{W}")));
    }

    private SukiKyoshiCaptain(final SukiKyoshiCaptain card) {
        super(card);
    }

    @Override
    public SukiKyoshiCaptain copy() {
        return new SukiKyoshiCaptain(this);
    }
}
