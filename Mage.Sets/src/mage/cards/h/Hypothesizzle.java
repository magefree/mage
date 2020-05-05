package mage.cards.h;

import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Hypothesizzle extends CardImpl {

    public Hypothesizzle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{R}");

        // Draw two cards. Then you may discard a nonland card. When you do, Hypothesizzle deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));

        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(4), false,
                "{this} deals 4 damage to target creature"
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DoWhenCostPaid(
                ability, new DiscardCardCost(StaticFilters.FILTER_CARD_A_NON_LAND),
                "Discard a nonland card?"
        ).concatBy("Then"));
    }

    private Hypothesizzle(final Hypothesizzle card) {
        super(card);
    }

    @Override
    public Hypothesizzle copy() {
        return new Hypothesizzle(this);
    }
}
