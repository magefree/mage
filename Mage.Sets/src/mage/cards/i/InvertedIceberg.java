package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvertedIceberg extends TransformingDoubleFacedCard {

    public InvertedIceberg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{1}{U}",
                "Iceberg Titan",
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.GOLEM}, "U"
        );

        // Inverted Iceberg
        // When Inverted Iceberg enters the battlefield, mill a card, then draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(1));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.getLeftHalfCard().addAbility(ability);

        // Craft with artifact {4}{U}{U}
        this.getLeftHalfCard().addAbility(new CraftAbility("{4}{U}{U}"));

        // Iceberg Titan
        this.getRightHalfCard().setPT(6, 6);

        // Whenever Iceberg Titan attacks, you may tap or untap target artifact or creature.
        Ability atkAbility = new AttacksTriggeredAbility(new MayTapOrUntapTargetEffect());
        atkAbility.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getRightHalfCard().addAbility(atkAbility);
    }

    private InvertedIceberg(final InvertedIceberg card) {
        super(card);
    }

    @Override
    public InvertedIceberg copy() {
        return new InvertedIceberg(this);
    }
}
