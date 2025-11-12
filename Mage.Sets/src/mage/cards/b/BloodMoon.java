package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.NonbasicLandsAreMountainsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BloodMoon extends CardImpl {

    public BloodMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Nonbasic lands are Mountains.
        this.addAbility(new SimpleStaticAbility(new NonbasicLandsAreMountainsEffect()));
    }

    private BloodMoon(final BloodMoon card) {
        super(card);
    }

    @Override
    public BloodMoon copy() {
        return new BloodMoon(this);
    }
}
