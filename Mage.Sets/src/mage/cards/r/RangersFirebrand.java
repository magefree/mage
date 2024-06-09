package mage.cards.r;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RangersFirebrand extends CardImpl {

    public RangersFirebrand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Ranger's Firebrand deals 2 damage to any target. The Ring tempts you.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new TheRingTemptsYouEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private RangersFirebrand(final RangersFirebrand card) {
        super(card);
    }

    @Override
    public RangersFirebrand copy() {
        return new RangersFirebrand(this);
    }
}
