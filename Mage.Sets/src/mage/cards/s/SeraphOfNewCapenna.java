package mage.cards.s;

import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeraphOfNewCapenna extends TransformingDoubleFacedCard {

    public SeraphOfNewCapenna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ANGEL, SubType.SOLDIER}, "{2}{W}",
                "Seraph of New Phyrexia",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.ANGEL}, "WB"
        );

        // Seraph of New Capenna
        this.getLeftHalfCard().setPT(2, 2);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // {4}{B/P}: Transform Seraph of New Capenna. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{4}{B/P}")));

        // Seraph of New Phyrexia
        this.getRightHalfCard().setPT(3, 3);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever Seraph of New Phyrexia attacks, you may sacrifice another creature or artifact. If you do, Seraph of New Phyrexia gets +2/+1 until end of turn.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new BoostSourceEffect(2, 1, Duration.EndOfTurn),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT)
        )));
    }

    private SeraphOfNewCapenna(final SeraphOfNewCapenna card) {
        super(card);
    }

    @Override
    public SeraphOfNewCapenna copy() {
        return new SeraphOfNewCapenna(this);
    }
}
