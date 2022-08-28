package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class FallingTimber extends CardImpl {

    public FallingTimber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Kicker-Sacrifice a land.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(new TargetControlledPermanent(1, 1,
                new FilterControlledLandPermanent("a land"), true))));

        // Prevent all combat damage target creature would deal this turn. If Falling Timber was kicked,
        // prevent all combat damage another target creature would deal this turn.
        Effect effect = new PreventDamageByTargetEffect(Duration.EndOfTurn, true);
        effect.setText("Prevent all combat damage target creature would deal this turn. If this spell was kicked, " +
                "prevent all combat damage another target creature would deal this turn.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(FallingTimberAdjuster.instance);
    }

    private FallingTimber(final FallingTimber card) {
        super(card);
    }

    @Override
    public FallingTimber copy() {
        return new FallingTimber(this);
    }
}

enum FallingTimberAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(KickedCondition.ONCE.apply(game, ability) ? 2 : 1));
    }
}
