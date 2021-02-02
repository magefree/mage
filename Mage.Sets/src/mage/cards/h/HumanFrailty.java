
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

/**
 * @author noxx
 */
public final class HumanFrailty extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Human creature");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    public HumanFrailty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Destroy target Human creature.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private HumanFrailty(final HumanFrailty card) {
        super(card);
    }

    @Override
    public HumanFrailty copy() {
        return new HumanFrailty(this);
    }
}
