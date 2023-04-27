package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class ColossalGrowth extends CardImpl {

    public ColossalGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Kicker {R}
        this.addAbility(new KickerAbility("{R}"));

        // Target creature gets +3/+3 until end of turn. If this spell was kicked, instead that creature gets +4/+4 and gains trample and haste until end of turn.
        this.getSpellAbility().addEffect(new ColossalGrowthEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ColossalGrowth(final ColossalGrowth card) {
        super(card);
    }

    @Override
    public ColossalGrowth copy() {
        return new ColossalGrowth(this);
    }
}

class ColossalGrowthEffect extends OneShotEffect {

    public ColossalGrowthEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Target creature gets +3/+3 until end of turn. If this spell was kicked, instead that creature gets +4/+4 and gains trample and haste until end of turn.";
    }

    private ColossalGrowthEffect(final ColossalGrowthEffect effect) {
        super(effect);
    }

    @Override
    public ColossalGrowthEffect copy() {
        return new ColossalGrowthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        if (KickedCondition.ONCE.apply(game, source)) {
            game.addEffect(new BoostTargetEffect(4, 4)
                    .setTargetPointer(new FixedTarget(permanent, game)), source);
            game.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                    .setTargetPointer(new FixedTarget(permanent, game)), source);
            game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                    .setTargetPointer(new FixedTarget(permanent, game)), source);
        } else {
            game.addEffect(new BoostTargetEffect(3, 3)
                    .setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}
