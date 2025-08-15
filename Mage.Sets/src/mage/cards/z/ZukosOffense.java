package mage.cards.z;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZukosOffense extends CardImpl {

    public ZukosOffense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Zuko's Offense deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private ZukosOffense(final ZukosOffense card) {
        super(card);
    }

    @Override
    public ZukosOffense copy() {
        return new ZukosOffense(this);
    }
}
