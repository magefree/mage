package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class DarkDeed extends CardImpl {

    public DarkDeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gets -4/-4 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-4, -4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DarkDeed(final DarkDeed card) {
        super(card);
    }

    @Override
    public DarkDeed copy() {
        return new DarkDeed(this);
    }
}
