package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Plopman
 */
public final class Lhurgoyf extends CardImpl {

    private static final DynamicValue powerValue = new CardsInAllGraveyardsCount(StaticFilters.FILTER_CARD_CREATURE);
    private static final DynamicValue toughnessValue = new AdditiveDynamicValue(powerValue, StaticValue.get(1));

    public Lhurgoyf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.LHURGOYF);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Lhurgoyf's power is equal to the number of creature cards in all graveyards and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerToughnessSourceEffect(powerValue, toughnessValue, Duration.EndOfGame, SubLayer.CharacteristicDefining_7a)
                        .setText("{this}'s power is equal to the number of creature cards in all graveyards and its toughness is equal to that number plus 1")
        ));
    }

    private Lhurgoyf(final Lhurgoyf card) {
        super(card);
    }

    @Override
    public Lhurgoyf copy() {
        return new Lhurgoyf(this);
    }
}
