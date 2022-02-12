package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author spjspj
 */
public final class ChandrasPyrohelix extends CardImpl {

    public ChandrasPyrohelix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Chandra's Pyrohelix deals 2 damage divided as you choose among one or two targets.
        this.getSpellAbility().addEffect(new DamageMultiEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(2));
    }

    private ChandrasPyrohelix(final ChandrasPyrohelix card) {
        super(card);
    }

    @Override
    public ChandrasPyrohelix copy() {
        return new ChandrasPyrohelix(this);
    }
}
