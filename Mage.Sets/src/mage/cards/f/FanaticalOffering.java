package mage.cards.f;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.MapToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FanaticalOffering extends CardImpl {

    public FanaticalOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // As an additional cost to cast this spell, sacrifice an artifact or creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));

        // Draw two cards and create a Map token.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new MapToken()).concatBy("and"));
    }

    private FanaticalOffering(final FanaticalOffering card) {
        super(card);
    }

    @Override
    public FanaticalOffering copy() {
        return new FanaticalOffering(this);
    }
}
