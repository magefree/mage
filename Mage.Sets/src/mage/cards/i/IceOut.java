package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.OptionalAdditionalCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.BargainAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.TargetSpell;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IceOut extends CardImpl {

    public IceOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Bargain
        this.addAbility(new BargainAbility());

        // This spell costs {1} less to cast if it's bargained.
        this.getSpellAbility().addEffect(new InfoEffect("this spell costs {1} less to cast if it's bargained"));
        this.getSpellAbility().setCostAdjuster(IceOutAdjuster.instance);

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect().concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private IceOut(final IceOut card) {
        super(card);
    }

    @Override
    public IceOut copy() {
        return new IceOut(this);
    }
}

enum IceOutAdjuster implements CostAdjuster {
    instance;

    private static OptionalAdditionalCost bargainCost = BargainAbility.makeBargainCost();

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (BargainedCondition.instance.apply(game, ability)
                || (game.inCheckPlayableState() && bargainCost.canPay(ability, null, ability.getControllerId(), game))) {
            CardUtil.reduceCost(ability, 1);
        }
    }
}
