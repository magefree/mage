
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
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class ReignOfChaos extends CardImpl {

    private static final FilterPermanent filter1 = new FilterPermanent("Plains");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("white creature");
    private static final FilterPermanent filter3 = new FilterPermanent("Island");
    private static final FilterCreaturePermanent filter4 = new FilterCreaturePermanent("blue creature");

    static {
        filter1.add(SubType.PLAINS.getPredicate());
        filter2.add(new ColorPredicate(ObjectColor.WHITE));
        filter3.add(SubType.ISLAND.getPredicate());
        filter4.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public ReignOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Choose one - Destroy target Plains and target white creature; or destroy target Island and target blue creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(false, true));
        this.getSpellAbility().addTarget(new TargetPermanent(filter1));
        this.getSpellAbility().addTarget(new TargetPermanent(filter2));
        Mode mode = new Mode(new DestroyTargetEffect(false, true));
        mode.addTarget(new TargetPermanent(filter3));
        mode.addTarget(new TargetPermanent(filter4));
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
