
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttachedToControlledPermanentPredicate;
import mage.filter.predicate.permanent.AttachedToPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class MiracleWorker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Aura attached to a creature you control");

    static {
        filter.add(new AttachedToControlledPermanentPredicate());
        filter.add(new AttachedToPredicate(new FilterCreaturePermanent()));
        filter.add(SubType.AURA.getPredicate());
    }

    public MiracleWorker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Destroy target Aura attached to a creature you control.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MiracleWorker(final MiracleWorker card) {
        super(card);
    }

    @Override
    public MiracleWorker copy() {
        return new MiracleWorker(this);
    }
}
