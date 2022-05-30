package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElturelSurvivors extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(DefendingPlayerControlsPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public ElturelSurvivors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Myriad
        this.addAbility(new MyriadAbility());

        // As long as Elturel Survivors is attacking, it gets +X/+0, where X is the number of lands defending player controls.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.WhileOnBattlefield
        ).setText("as long as {this} is attacking, it gets +X/+0, " +
                "where X is the number of lands defending player controls")));
    }

    private ElturelSurvivors(final ElturelSurvivors card) {
        super(card);
    }

    @Override
    public ElturelSurvivors copy() {
        return new ElturelSurvivors(this);
    }
}
