package mage.cards.m;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MammothGrowth extends CardImpl {

    public MammothGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Target creature gets +4/+4 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Foretell {G}
        this.addAbility(new ForetellAbility(this, "{G}"));
    }

    private MammothGrowth(final MammothGrowth card) {
        super(card);
    }

    @Override
    public MammothGrowth copy() {
        return new MammothGrowth(this);
    }
}
