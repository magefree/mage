package mage.cards.a;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnthemOfChampions extends CardImpl {

    public AnthemOfChampions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{W}");

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES
        )));
    }

    private AnthemOfChampions(final AnthemOfChampions card) {
        super(card);
    }

    @Override
    public AnthemOfChampions copy() {
        return new AnthemOfChampions(this);
    }
}
