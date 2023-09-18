
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author emerald000
 */
public final class ActiveVolcano extends CardImpl {

    private static final FilterPermanent filterBlue = new FilterPermanent("blue permanent");
    private static final FilterPermanent filterIsland = new FilterPermanent("Island");
    static {
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        filterIsland.add(SubType.ISLAND.getPredicate());
    }

    public ActiveVolcano(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");


        // Choose one - Destroy target blue permanent;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filterBlue));

        // or return target Island to its owner's hand.
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetPermanent(filterIsland));
        this.getSpellAbility().addMode(mode);
    }

    private ActiveVolcano(final ActiveVolcano card) {
        super(card);
    }

    @Override
    public ActiveVolcano copy() {
        return new ActiveVolcano(this);
    }
}
