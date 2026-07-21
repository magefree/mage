package mage.cards.i;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class IAmIronMan extends CardImpl {

    public IAmIronMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Until end of turn, target artifact or creature becomes an artifact creature with base power and toughness 4/4 and gains flying.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(new CreatureToken(4, 4,
                "artifact creature with base power and toughness 4/4 and gains flying").withType(CardType.ARTIFACT)
                .withAbility(FlyingAbility.getInstance()), false, false, Duration.EndOfTurn
        ).withDurationRuleAtStart(true));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private IAmIronMan(final IAmIronMan card) {
        super(card);
    }

    @Override
    public IAmIronMan copy() {
        return new IAmIronMan(this);
    }
}
