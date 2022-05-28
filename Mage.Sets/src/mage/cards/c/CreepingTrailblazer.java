package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CreepingTrailblazer extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.ELEMENTAL, "Elementals");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.ELEMENTAL, "Elemental you control");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter2);

    public CreepingTrailblazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Elementals you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, filter, true
        )));

        // {2}{R}{G}: Creeping Trailblazer gets +1/+1 until end of turn for each Elemental you control.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn, true)
                        .setText("{this} gets +1/+1 until end of turn for each Elemental you control."),
                new ManaCostsImpl<>("{2}{R}{G}")
        ));
    }

    private CreepingTrailblazer(final CreepingTrailblazer card) {
        super(card);
    }

    @Override
    public CreepingTrailblazer copy() {
        return new CreepingTrailblazer(this);
    }
}
