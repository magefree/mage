
package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author TheElk801
 */
public final class ReignOfChaos extends CardImpl {

    private static final FilterPermanent filterPlains = new FilterLandPermanent(SubType.PLAINS, "Plains");
    private static final FilterPermanent filterWhite  = new FilterCreaturePermanent("white creature");
    private static final FilterPermanent filterIsland = new FilterLandPermanent(SubType.ISLAND, "Island");
    private static final FilterPermanent filterBlue   = new FilterCreaturePermanent("blue creature");

    static {
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public ReignOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Choose one - Destroy target Plains and target white creature; or destroy target Island and target blue creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect().setTargetPointer(new EachTargetPointer()));
        this.getSpellAbility().addTarget(new TargetPermanent(filterPlains));
        this.getSpellAbility().addTarget(new TargetPermanent(filterWhite));
        Mode mode = new Mode(new DestroyTargetEffect().setTargetPointer(new EachTargetPointer()));
        mode.addTarget(new TargetPermanent(filterIsland));
        mode.addTarget(new TargetPermanent(filterBlue));
        this.getSpellAbility().addMode(mode);
    }

    private ReignOfChaos(final ReignOfChaos card) {
        super(card);
    }

    @Override
    public ReignOfChaos copy() {
        return new ReignOfChaos(this);
    }
}
