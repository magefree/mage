package mage.cards.s;

import mage.abilities.effects.common.DamageTargetAndTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ShowerOfSparks extends CardImpl {

    public ShowerOfSparks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");
        
        // Shower of Sparks deals 1 damage to target creature and 1 damage to target player or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetAndTargetEffect(1, 1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker().setTargetTag(2));
    }

    private ShowerOfSparks(final ShowerOfSparks card) {
        super(card);
    }

    @Override
    public ShowerOfSparks copy() {
        return new ShowerOfSparks(this);
    }
}
