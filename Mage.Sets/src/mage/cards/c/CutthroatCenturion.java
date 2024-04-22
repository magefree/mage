package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CutthroatCenturion extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another artifact or creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public CutthroatCenturion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sacrifice another artifact or creature: Cutthroat Centurion gets +2/+2 until end of turn. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                new SacrificeTargetCost(filter)
        ));
    }

    private CutthroatCenturion(final CutthroatCenturion card) {
        super(card);
    }

    @Override
    public CutthroatCenturion copy() {
        return new CutthroatCenturion(this);
    }
}
