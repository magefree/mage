package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConiferWurm extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("the number of snow permanents you control");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Snow permanents you control", xValue);

    public ConiferWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {3}{G}: Conifer Wurm gets +X/+X until end of turn, where X is the number of snow permanents you control.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn)
                        .setText("{this} gets +X/+X until end of turn, where X is the number of snow permanents you control"),
                new ManaCostsImpl<>("{3}{G}")
        ).addHint(hint));
    }

    private ConiferWurm(final ConiferWurm card) {
        super(card);
    }

    @Override
    public ConiferWurm copy() {
        return new ConiferWurm(this);
    }
}
