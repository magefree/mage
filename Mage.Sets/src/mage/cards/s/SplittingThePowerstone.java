package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.PowerstoneToken;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SplittingThePowerstone extends CardImpl {

    public SplittingThePowerstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // As an additional cost to cast this spell, sacrifice an artifact.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN));

        // Create two tapped Powerstone tokens. If the sacrificed artifact was legendary, draw a card.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PowerstoneToken(), 2, true));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), SplittingThePowerstoneCondition.instance,
                "If the sacrificed artifact was legendary, draw a card"
        ));
    }

    private SplittingThePowerstone(final SplittingThePowerstone card) {
        super(card);
    }

    @Override
    public SplittingThePowerstone copy() {
        return new SplittingThePowerstone(this);
    }
}

enum SplittingThePowerstoneCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .castStream(source.getCosts().stream(), SacrificeTargetCost.class)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .anyMatch(MageObject::isLegendary);
    }
}