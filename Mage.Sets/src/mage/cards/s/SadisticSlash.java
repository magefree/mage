package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.MayhemAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class SadisticSlash extends CardImpl {

    public SadisticSlash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Target creature gets -5/-5 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-5, -5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Mayhem {1}{B}
        this.addAbility(new MayhemAbility(this, "{1}{B}"));
    }

    private SadisticSlash(final SadisticSlash card) {
        super(card);
    }

    @Override
    public SadisticSlash copy() {
        return new SadisticSlash(this);
    }
}
