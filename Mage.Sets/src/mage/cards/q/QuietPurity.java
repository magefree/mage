

package mage.cards.q;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class QuietPurity extends CardImpl {

    public QuietPurity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");
        this.subtype.add(SubType.ARCANE);

        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }


    private QuietPurity(final QuietPurity card) {
        super(card);
    }

    @Override
    public QuietPurity copy() {
        return new QuietPurity(this);
    }

}
