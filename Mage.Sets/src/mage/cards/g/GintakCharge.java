package mage.cards.g;

import java.util.UUID;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author muz
 */
public final class GintakCharge extends CardImpl {

    public GintakCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");


        // Gin'tak Charge deals 2 damage to any target.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
    }

    private GintakCharge(final GintakCharge card) {
        super(card);
    }

    @Override
    public GintakCharge copy() {
        return new GintakCharge(this);
    }
}
