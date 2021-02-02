
package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class NaturalSpring extends CardImpl {

    public NaturalSpring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");


        // Target player gains 8 life.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(8));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private NaturalSpring(final NaturalSpring card) {
        super(card);
    }

    @Override
    public NaturalSpring copy() {
        return new NaturalSpring(this);
    }
}
