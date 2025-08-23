package mage.cards.y;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.AddBasicLandTypeAllLandsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YavimayaCradleOfGrowth extends CardImpl {

    public YavimayaCradleOfGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // Each land is a Forest in addition to its other land types.
        this.addAbility(new SimpleStaticAbility(new AddBasicLandTypeAllLandsEffect(SubType.FOREST)));
    }

    private YavimayaCradleOfGrowth(final YavimayaCradleOfGrowth card) {
        super(card);
    }

    @Override
    public YavimayaCradleOfGrowth copy() {
        return new YavimayaCradleOfGrowth(this);
    }
}
