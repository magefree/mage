
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class UnlikelyAlliance extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonattacking, nonblocking creature");

    static {
        filter.add(Predicates.not(AttackingPredicate.instance));
        filter.add(Predicates.not(BlockingPredicate.instance));
    }

    public UnlikelyAlliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // {1}{W}: Target nonattacking, nonblocking creature gets +0/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(0, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private UnlikelyAlliance(final UnlikelyAlliance card) {
        super(card);
    }

    @Override
    public UnlikelyAlliance copy() {
        return new UnlikelyAlliance(this);
    }
}
