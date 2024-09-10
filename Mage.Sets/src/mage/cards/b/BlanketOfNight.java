package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.AddBasicLandTypeAllLandsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Galatolol
 */
public final class BlanketOfNight extends CardImpl {

    public BlanketOfNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        // Each land is a Swamp in addition to its other land types.
        this.addAbility(new SimpleStaticAbility(new AddBasicLandTypeAllLandsEffect(SubType.SWAMP)));
    }

    private BlanketOfNight(final BlanketOfNight card) {
        super(card);
    }

    @Override
    public BlanketOfNight copy() {
        return new BlanketOfNight(this);
    }
}
