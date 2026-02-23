package mage.cards.p;

import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PyreticPrankster extends TransformingDoubleFacedCard {

    public PyreticPrankster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DEVIL}, "{1}{R}",
                "Glistening Goremonger",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.DEVIL}, "BR");

        // Pyretic Prankster
        this.getLeftHalfCard().setPT(2, 1);

        // {3}{B/P}: Transform Pyretic Prankster. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{3}{B/P}")));

        // Glistening Goremonger
        this.getRightHalfCard().setPT(3, 2);

        // When Glistening Goremonger dies, each opponent sacrifices an artifact or creature.
        this.getRightHalfCard().addAbility(new DiesSourceTriggeredAbility(new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE)));
    }

    private PyreticPrankster(final PyreticPrankster card) {
        super(card);
    }

    @Override
    public PyreticPrankster copy() {
        return new PyreticPrankster(this);
    }
}
