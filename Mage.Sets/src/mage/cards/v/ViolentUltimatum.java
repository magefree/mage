

package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class ViolentUltimatum extends CardImpl {

    public ViolentUltimatum (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}{R}{R}{R}{G}{G}");


        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(3, 3, new FilterPermanent(), false));
    }

    public ViolentUltimatum (final ViolentUltimatum card) {
        super(card);
    }

    @Override
    public ViolentUltimatum copy() {
        return new ViolentUltimatum(this);
    }

}
