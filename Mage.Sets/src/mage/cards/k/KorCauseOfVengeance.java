package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 * @author muz
 */
public final class KorCauseOfVengeance extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Klingons and Warriors");

    static {
        filter.add(Predicates.or(
            SubType.KLINGON.getPredicate(),
            SubType.WARRIOR.getPredicate()
        ));
    }

    public KorCauseOfVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you attack, you may sacrifice another creature. If you do, draw a card, then Klingons and Warriors you control get +2/+2 until end of turn.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new DoIfCostPaid(
            new DrawCardSourceControllerEffect(1),
            new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
        ).addEffect(new BoostControlledEffect(2, 2, Duration.EndOfTurn, filter).concatBy(", then")), 1));
    }

    private KorCauseOfVengeance(final KorCauseOfVengeance card) {
        super(card);
    }

    @Override
    public KorCauseOfVengeance copy() {
        return new KorCauseOfVengeance(this);
    }
}
