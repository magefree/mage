package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SkeletalScrying extends CardImpl {

    public SkeletalScrying(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}");

        // As an additional cost to cast Skeletal Scrying, exile X cards from your graveyard.
        Ability ability = new SimpleStaticAbility(
                Zone.ALL,
                new InfoEffect(
                        "as an additional cost to cast this spell, " +
                                "exile X cards from your graveyard"
                ));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        this.getSpellAbility().setCostAdjuster(SkeletalScryingAdjuster.instance);

        // You draw X cards and you lose X life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(
                ManacostVariableValue.REGULAR
        ).setText("you draw X cards"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(
                ManacostVariableValue.REGULAR
        ).concatBy("and"));
    }

    private SkeletalScrying(final SkeletalScrying card) {
        super(card);
    }

    @Override
    public SkeletalScrying copy() {
        return new SkeletalScrying(this);
    }
}

enum SkeletalScryingAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        if (xValue > 0) {
            ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(xValue, xValue, StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD)));
        }
    }
}
