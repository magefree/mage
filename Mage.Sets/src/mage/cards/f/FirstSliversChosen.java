package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirstSliversChosen extends CardImpl {

    public FirstSliversChosen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sliver creatures you control have exalted.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new ExaltedAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_SLIVERS
        )));
    }

    private FirstSliversChosen(final FirstSliversChosen card) {
        super(card);
    }

    @Override
    public FirstSliversChosen copy() {
        return new FirstSliversChosen(this);
    }
}
