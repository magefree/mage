package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Atog extends CardImpl {

    public Atog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.ATOG);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN))
        ));
    }

    private Atog(final Atog card) {
        super(card);
    }

    @Override
    public Atog copy() {
        return new Atog(this);
    }
}
