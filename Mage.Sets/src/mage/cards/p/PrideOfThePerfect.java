package mage.cards.p;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrideOfThePerfect extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELF, "Elves");

    public PrideOfThePerfect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Elves you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 0, Duration.WhileOnBattlefield, filter, false
        )));
    }

    private PrideOfThePerfect(final PrideOfThePerfect card) {
        super(card);
    }

    @Override
    public PrideOfThePerfect copy() {
        return new PrideOfThePerfect(this);
    }
}
