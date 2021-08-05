package mage.cards.t;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.Pest11GainLifeToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TendThePests extends CardImpl {

    public TendThePests(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{G}");

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        ));

        // Create X 1/1 black and green Pest creature tokens with "When this creature dies, you gain 1 life," where X is the sacrificed creature's power.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new Pest11GainLifeToken(), SacrificeCostCreaturesPower.instance
        ).setText("create X 1/1 black and green Pest creature tokens with " +
                "\"When this creature dies, you gain 1 life,\" where X is the sacrificed creature's power"));
    }

    private TendThePests(final TendThePests card) {
        super(card);
    }

    @Override
    public TendThePests copy() {
        return new TendThePests(this);
    }
}
