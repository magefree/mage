package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.BargainAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BraveTheWilds extends CardImpl {

    public BraveTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Bargain
        this.addAbility(new BargainAbility());

        // If this spell was bargained, target land you control becomes a 3/3 Elemental creature with haste that's still a land.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BecomesCreatureTargetEffect(
                        new CreatureToken(3, 3, "3/3 Elemental creature with haste")
                                .withSubType(SubType.ELEMENTAL).withAbility(HasteAbility.getInstance()),
                        false, true, Duration.Custom
                )),
                BargainedCondition.instance,
                "If this spell was bargained, target land you control becomes "
                        + "a 3/3 Elemental creature with haste that's still a land."
        ));
        this.getSpellAbility().setTargetAdjuster(BraveTheWildsTargetAdjuster.instance);

        // Search your library for a basic land card, reveal it, put it into your hand, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ).concatBy("<br>"));
    }

    private BraveTheWilds(final BraveTheWilds card) {
        super(card);
    }

    @Override
    public BraveTheWilds copy() {
        return new BraveTheWilds(this);
    }
}

enum BraveTheWildsTargetAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        if (BargainedCondition.instance.apply(game, ability)) {
            ability.addTarget(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT));
        }
    }
}
