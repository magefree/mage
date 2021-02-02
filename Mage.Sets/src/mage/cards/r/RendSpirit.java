

package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 * @author Loki
 */
public final class RendSpirit extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Spirit");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public RendSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private RendSpirit(final RendSpirit card) {
        super(card);
    }

    @Override
    public RendSpirit copy() {
        return new RendSpirit(this);
    }

}
