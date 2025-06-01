package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PuPuUFO extends CardImpl {

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.TOWN));
    private static final Hint hint = new ValueHint("Towns you control", xValue);

    public PuPuUFO(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.CONSTRUCT);
        this.subtype.add(SubType.ALIEN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {T}: You may put a land card from your hand onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A), new TapSourceCost()
        ));

        // {3}: Until end of turn, this creature's base power becomes equal to the number of Towns you control.
        this.addAbility(new SimpleActivatedAbility(
                new SetBasePowerToughnessSourceEffect(
                        xValue, StaticValue.get(0), Duration.EndOfTurn, "until end of turn, " +
                        "this creature's base power becomes equal to the number of Towns you control"
                ), new GenericManaCost(3)
        ).addHint(hint));
    }

    private PuPuUFO(final PuPuUFO card) {
        super(card);
    }

    @Override
    public PuPuUFO copy() {
        return new PuPuUFO(this);
    }
}
