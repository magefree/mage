package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.OptionalAdditionalCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.BargainAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetNonlandPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JohannsStopgap extends CardImpl {

    public JohannsStopgap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Bargain
        this.addAbility(new BargainAbility());

        // This spell costs {2} less to cast if it's bargained.
        this.getSpellAbility().addEffect(new InfoEffect("this spell costs {2} less to cast if it's bargained"));
        this.getSpellAbility().setCostAdjuster(JohannsStopgapAdjuster.instance);

        // Return target nonland permanent to its owner's hand. Draw a card.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect().concatBy("<br>"));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent().withChooseHint("to return to hand"));
    }

    private JohannsStopgap(final JohannsStopgap card) {
        super(card);
    }

    @Override
    public JohannsStopgap copy() {
        return new JohannsStopgap(this);
    }
}

enum JohannsStopgapAdjuster implements CostAdjuster {
    instance;

    private static OptionalAdditionalCost bargainCost = BargainAbility.makeBargainCost();

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (BargainedCondition.instance.apply(game, ability)
                || (game.inCheckPlayableState() && bargainCost.canPay(ability, null, ability.getControllerId(), game))) {
            CardUtil.reduceCost(ability, 2);
        }
    }
}
