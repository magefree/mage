
package mage.cards.r;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class Rouse extends CardImpl {

    private static final FilterLandPermanent filterSwamp = new FilterLandPermanent("If you control a Swamp");

    static {
        filterSwamp.add(SubType.SWAMP.getPredicate());
    }

    public Rouse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // If you control a Swamp, you may pay 2 life rather than pay Rouse's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(
                new PayLifeCost(2),
                new PermanentsOnTheBattlefieldCondition(filterSwamp), null
        ));

        // Target creature gets +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Rouse(final Rouse card) {
        super(card);
    }

    @Override
    public Rouse copy() {
        return new Rouse(this);
    }
}
