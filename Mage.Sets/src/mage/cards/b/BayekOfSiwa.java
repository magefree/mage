package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.HistoricPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BayekOfSiwa extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(HistoricPredicate.instance);
    }

    public BayekOfSiwa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // As long as it's your turn, other historic creatures you control have double strike.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(
                        DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
                ), MyTurnCondition.instance, "as long as it's your turn, " +
                "other historic creatures you control have double strike"
        )));

        // Disguise {1}{R}{W}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{1}{R}{W}")));
    }

    private BayekOfSiwa(final BayekOfSiwa card) {
        super(card);
    }

    @Override
    public BayekOfSiwa copy() {
        return new BayekOfSiwa(this);
    }
}
