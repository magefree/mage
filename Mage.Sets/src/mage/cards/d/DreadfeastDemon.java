package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreadfeastDemon extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("non-Demon creature");

    static {
        filter.add(Predicates.not(SubType.DEMON.getPredicate()));
    }

    public DreadfeastDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, sacrifice a non-Demon creature. If you do, create a token that's a copy of Dreadfeast Demon.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DoIfCostPaid(
                new CreateTokenCopySourceEffect(),
                new SacrificeTargetCost(
                        new TargetControlledPermanent(filter)
                ), null, false
        ), TargetController.YOU, false));
    }

    private DreadfeastDemon(final DreadfeastDemon card) {
        super(card);
    }

    @Override
    public DreadfeastDemon copy() {
        return new DreadfeastDemon(this);
    }
}
