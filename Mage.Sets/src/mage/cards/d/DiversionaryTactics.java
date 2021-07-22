
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class DiversionaryTactics extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public DiversionaryTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(2, 2, filter, false)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private DiversionaryTactics(final DiversionaryTactics card) {
        super(card);
    }

    @Override
    public DiversionaryTactics copy() {
        return new DiversionaryTactics(this);
    }
}
