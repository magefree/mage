
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LoneFox
 */
public final class DespoticScepter extends CardImpl {

    private static final FilterPermanent FILTER = new FilterPermanent("permanent you own");

    static {
        FILTER.add(TargetController.YOU.getOwnerPredicate());
    }

    public DespoticScepter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {tap}: Destroy target permanent you own. It can't be regenerated.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(true), new TapSourceCost());
        ability.addTarget(new TargetPermanent(FILTER));
        this.addAbility(ability);
    }

    private DespoticScepter(final DespoticScepter card) {
        super(card);
    }

    @Override
    public DespoticScepter copy() {
        return new DespoticScepter(this);
    }
}
