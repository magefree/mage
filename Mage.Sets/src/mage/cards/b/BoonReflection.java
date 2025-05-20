package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.GainDoubleLifeReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BoonReflection extends CardImpl {

    public BoonReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // If you would gain life, you gain twice that much life instead.
        this.addAbility(new SimpleStaticAbility(new GainDoubleLifeReplacementEffect()));
    }

    private BoonReflection(final BoonReflection card) {
        super(card);
    }

    @Override
    public BoonReflection copy() {
        return new BoonReflection(this);
    }
}
