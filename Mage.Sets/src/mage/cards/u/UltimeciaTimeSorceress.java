package mage.cards.u;

import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author balazskristof
 */
public final class UltimeciaTimeSorceress extends TransformingDoubleFacedCard {

    public UltimeciaTimeSorceress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARLOCK}, "{3}{U}{B}",
                "Ultimecia, Omnipotent",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.NIGHTMARE, SubType.WARLOCK}, "UB"
        );

        // Ultimecia, Time Sorceress
        this.getLeftHalfCard().setPT(4, 5);

        // Whenever Ultimecia enters or attacks, surveil 2.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new SurveilEffect(2)));

        // At the beginning of your end step, you may pay {4}{U}{U}{B}{B} and exile eight cards from your graveyard. If you do, transform Ultimecia.
        this.getLeftHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(new DoIfCostPaid(
                new TransformSourceEffect(),
                new CompositeCost(
                        new ManaCostsImpl<>("{4}{U}{U}{B}{B}"),
                        new ExileFromGraveCost(new TargetCardInYourGraveyard(8)),
                        "{4}{U}{U}{B}{B} and exile eight cards from your graveyard"
                )
        )));

        // Ultimecia, Omnipotent
        this.getRightHalfCard().setPT(7, 7);

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility());

        // Time Compression -- When this creature transforms into Ultimecia, Omnipotent, take an extra turn after this one.
        this.getRightHalfCard().addAbility(new TransformIntoSourceTriggeredAbility(new AddExtraTurnControllerEffect()).withFlavorWord("Time Compression"));
    }

    private UltimeciaTimeSorceress(final UltimeciaTimeSorceress card) {
        super(card);
    }

    @Override
    public UltimeciaTimeSorceress copy() {
        return new UltimeciaTimeSorceress(this);
    }
}
