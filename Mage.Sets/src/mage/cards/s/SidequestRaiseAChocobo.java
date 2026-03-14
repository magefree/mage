package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.ChocoboToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SidequestRaiseAChocobo extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterPermanent(SubType.BIRD, "Birds");
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.BIRD, "you control four or more Birds"),
            ComparisonType.MORE_THAN, 3
    );
    private static final Hint hint = new ValueHint(
            "Birds you control", new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.BIRD))
    );

    public SidequestRaiseAChocobo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{1}{G}",
                "Black Chocobo",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.BIRD}, "G"
        );

        // Sidequest: Raise a Chocobo
        // When this enchantment enters, create a 2/2 green Bird creature token with "Whenever a land you control enters, this token gets +1/+0 until end of turn."
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ChocoboToken())));

        // At the beginning of your first main phase, if you control four or more Birds, transform this enchantment.
        this.getLeftHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(new TransformSourceEffect())
                .withInterveningIf(condition).addHint(hint));

        // Black Chocobo
        this.getRightHalfCard().setPT(2, 2);

        // When this permanent transforms into Black Chocobo, search your library for a land card, put it onto the battlefield tapped, then shuffle.
        this.getRightHalfCard().addAbility(new TransformIntoSourceTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_LAND_A), true)
        ));

        // Landfall -- Whenever a land you control enters, Birds you control get +1/+0 until end of turn.
        this.getRightHalfCard().addAbility(new LandfallAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn, filter, false
        )));
    }

    private SidequestRaiseAChocobo(final SidequestRaiseAChocobo card) {
        super(card);
    }

    @Override
    public SidequestRaiseAChocobo copy() {
        return new SidequestRaiseAChocobo(this);
    }
}
