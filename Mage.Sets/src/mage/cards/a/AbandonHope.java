package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.discard.LookTargetHandChooseDiscardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AbandonHope extends CardImpl {

    public AbandonHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{1}{B}");

        // As an additional cost to cast Abandon Hope, discard X cards.
        Ability ability = new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("As an additional cost to cast this spell, discard X cards")
        );
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Look at target opponent's hand and choose X cards from it. That player discards those cards.
        this.getSpellAbility().addEffect(new LookTargetHandChooseDiscardEffect(false, ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().setCostAdjuster(AbandonHopeAdjuster.instance);
    }

    private AbandonHope(final AbandonHope card) {
        super(card);
    }

    @Override
    public AbandonHope copy() {
        return new AbandonHope(this);
    }
}

enum AbandonHopeAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        if (xValue > 0) {
            ability.addCost(new DiscardTargetCost(new TargetCardInHand(xValue, xValue, StaticFilters.FILTER_CARD_CARDS)));
        }
    }
}
