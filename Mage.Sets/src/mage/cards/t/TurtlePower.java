package mage.cards.t;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author muz
 */
public final class TurtlePower extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.TURTLE, "Turtles");

    public TurtlePower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Turtles you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter)));
    }

    private TurtlePower(final TurtlePower card) {
        super(card);
    }

    @Override
    public TurtlePower copy() {
        return new TurtlePower(this);
    }
}
