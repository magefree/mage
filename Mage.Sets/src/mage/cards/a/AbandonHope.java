package mage.cards.a;

import mage.abilities.costs.costadjusters.DiscardXCardsCostAdjuster;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.discard.LookTargetHandChooseDiscardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes, JayDi85
 */
public final class AbandonHope extends CardImpl {

    public AbandonHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{1}{B}");

        // As an additional cost to cast this spell, discard X cards.
        DiscardXCardsCostAdjuster.addAdjusterAndMessage(this, StaticFilters.FILTER_CARD_CARDS);

        // Look at target opponent's hand and choose X cards from it. That player discards those cards.
        this.getSpellAbility().addEffect(new LookTargetHandChooseDiscardEffect(false, GetXValue.instance, StaticFilters.FILTER_CARD_CARDS));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private AbandonHope(final AbandonHope card) {
        super(card);
    }

    @Override
    public AbandonHope copy() {
        return new AbandonHope(this);
    }
}