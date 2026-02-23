package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EcstaticAwakener extends TransformingDoubleFacedCard {

    public EcstaticAwakener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "{B}",
                "Awoken Demon",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DEMON}, "B"
        );

        // Ecstatic Awakener
        this.getLeftHalfCard().setPT(1, 1);

        // {2}{B}, Sacrifice another creature: Draw a card, then transform Ecstatic Awakener. Activate only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{2}{B}")
        );
        ability.addEffect(new TransformSourceEffect().concatBy(", then"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.getLeftHalfCard().addAbility(ability);

        // Awoken Demon
        this.getRightHalfCard().setPT(4, 4);
    }

    private EcstaticAwakener(final EcstaticAwakener card) {
        super(card);
    }

    @Override
    public EcstaticAwakener copy() {
        return new EcstaticAwakener(this);
    }
}
