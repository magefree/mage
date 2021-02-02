
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class RuthlessDisposal extends CardImpl {

    public RuthlessDisposal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // As an additional cost to cast Ruthless Disposal, discard a card and sacrifice a creature.
        this.getSpellAbility().addCost(new DiscardTargetCost(new TargetCardInHand(new FilterCard("a card"))));
        Cost cost = new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        cost.setText("as an additional cost to cast this spell, sacrifice a creature");
        this.getSpellAbility().addCost(cost);

        // Two target creatures each get -13/-13 until end of turn.
        Effect effect = new BoostTargetEffect(-13, -13, Duration.EndOfTurn);
        effect.setText("Two target creatures each get -13/-13 until end of turn");
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));
        this.getSpellAbility().addEffect(effect);

    }

    private RuthlessDisposal(final RuthlessDisposal card) {
        super(card);
    }

    @Override
    public RuthlessDisposal copy() {
        return new RuthlessDisposal(this);
    }
}
