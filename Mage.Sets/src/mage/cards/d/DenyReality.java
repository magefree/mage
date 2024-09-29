

package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class DenyReality extends CardImpl {

    public DenyReality (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{B}");

        
        this.getSpellAbility().addTarget(new TargetPermanent());
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.addAbility(new CascadeAbility());
    }

    private DenyReality(final DenyReality card) {
        super(card);
    }

    @Override
    public DenyReality copy() {
        return new DenyReality(this);
    }

}
