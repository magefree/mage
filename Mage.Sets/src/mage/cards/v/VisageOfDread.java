package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VisageOfDread extends TransformingDoubleFacedCard {

    public VisageOfDread(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{1}{B}",
                "Dread Osseosaur",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DINOSAUR, SubType.SKELETON, SubType.HORROR}, "B"
        );

        // Visage of Dread
        // When Visage of Dread enters the battlefield, target opponent reveals their hand. You choose an artifact or creature card from it. That player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE)
        );
        ability.addTarget(new TargetOpponent());
        this.getLeftHalfCard().addAbility(ability);

        // Craft with two creatures {5}{B}
        this.getLeftHalfCard().addAbility(new CraftAbility(
                "{5}{B}", "two creatures", "other creatures you control and/or " +
                "creature cards in your graveyard", 2, 2, CardType.CREATURE.getPredicate()
        ));

        // Dread Osseosaur
        this.getRightHalfCard().setPT(5, 4);

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility(false));

        // Whenever Dread Osseosaur enters the battlefield or attacks, you may mill two cards.
        this.getRightHalfCard().addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new MillCardsControllerEffect(2), true
        ));
    }

    private VisageOfDread(final VisageOfDread card) {
        super(card);
    }

    @Override
    public VisageOfDread copy() {
        return new VisageOfDread(this);
    }
}
