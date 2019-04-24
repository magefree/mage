
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public final class AbandonHope extends CardImpl {

    public AbandonHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{1}{B}");

        // As an additional cost to cast Abandon Hope, discard X cards.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new AbandonHopeRuleEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Look at target opponent's hand and choose X cards from it. That player discards those cards.
        ManacostVariableValue manaX = new ManacostVariableValue();
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(manaX, TargetController.ANY));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public AbandonHope(final AbandonHope card) {
        super(card);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        if (xValue > 0) {
            ability.addCost(new DiscardTargetCost(new TargetCardInHand(xValue, xValue, new FilterCard("cards"))));
        }
    }

    @Override
    public AbandonHope copy() {
        return new AbandonHope(this);
    }
}

class AbandonHopeRuleEffect extends OneShotEffect {

    public AbandonHopeRuleEffect() {
        super(Outcome.Benefit);
        this.staticText = "As an additional cost to cast this spell, discard X cards";
    }

    public AbandonHopeRuleEffect(final AbandonHopeRuleEffect effect) {
        super(effect);
    }

    @Override
    public AbandonHopeRuleEffect copy() {
        return new AbandonHopeRuleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
