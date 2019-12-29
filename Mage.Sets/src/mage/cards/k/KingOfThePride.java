package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KingOfThePride extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.CAT, "Cats");

    public KingOfThePride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Other cats you control get +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 1, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private KingOfThePride(final KingOfThePride card) {
        super(card);
    }

    @Override
    public KingOfThePride copy() {
        return new KingOfThePride(this);
    }
}
