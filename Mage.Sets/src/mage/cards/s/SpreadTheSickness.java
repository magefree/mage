

package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class SpreadTheSickness extends CardImpl {

    public SpreadTheSickness (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");

        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new ProliferateEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public SpreadTheSickness (final SpreadTheSickness card) {
        super(card);
    }

    @Override
    public SpreadTheSickness copy() {
        return new SpreadTheSickness(this);
    }

}
