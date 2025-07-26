package mage.cards.l;

import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.LanderToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Lithobraking extends CardImpl {

    public Lithobraking(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Create a Lander token. Then you may sacrifice an artifact. When you do, Lithobraking deals 2 damage to each creature.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new LanderToken()));
        this.getSpellAbility().addEffect(new DoWhenCostPaid(
                new ReflexiveTriggeredAbility(
                        new DamageAllEffect(2, StaticFilters.FILTER_PERMANENT_CREATURE), false
                ), new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT),
                "Sacrifice an artifact?"
        ).concatBy("Then"));
    }

    private Lithobraking(final Lithobraking card) {
        super(card);
    }

    @Override
    public Lithobraking copy() {
        return new Lithobraking(this);
    }
}
