
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Wehk
 */
public final class GhirapurAetherGrid extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped artifacts you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public GhirapurAetherGrid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // Tap two untaped artifacts you control: Ghirapur Aether Grid deals 1 damage to any target
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(1),
                new TapTargetCost(new TargetControlledPermanent(2, 2, filter, true)));
        ability.addTarget(new TargetAnyTarget().withChooseHint("deals 1 damage to"));
        this.addAbility(ability);
    }

    private GhirapurAetherGrid(final GhirapurAetherGrid card) {
        super(card);
    }

    @Override
    public GhirapurAetherGrid copy() {
        return new GhirapurAetherGrid(this);
    }
}
