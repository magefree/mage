

package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class ThroneOfGeth extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an artifact");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public ThroneOfGeth(UUID ownerId, CardSetInfo setInfo) {

        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {T}, Sacrifice an artifact: Proliferate.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ProliferateEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    public ThroneOfGeth(final ThroneOfGeth card) {
        super(card);
    }

    @Override
    public ThroneOfGeth copy() {
        return new ThroneOfGeth(this);
    }

}
