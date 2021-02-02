
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class ThranForge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public ThranForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {2}: Until end of turn, target nonartifact creature gets +1/+0 and becomes an artifact in addition to its other types.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostTargetEffect(1, 0, Duration.EndOfTurn)
                        .setText("Until end of turn, target nonartifact creature gets +1/+0"),
                new GenericManaCost(2));
        ability.addEffect(
                new AddCardTypeTargetEffect(Duration.EndOfTurn, CardType.ARTIFACT)
                        .setText("and becomes an artifact in addition to its other types")
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ThranForge(final ThranForge card) {
        super(card);
    }

    @Override
    public ThranForge copy() {
        return new ThranForge(this);
    }
}
