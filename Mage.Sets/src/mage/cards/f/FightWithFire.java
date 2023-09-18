package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetAnyTargetAmount;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

/**
 *
 * @author TheElk801
 */
public final class FightWithFire extends CardImpl {

    public FightWithFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Kicker {5}{R}
        this.addAbility(new KickerAbility("{5}{R}"));

        // Fight with Fire deals 5 damage to target creature. If this spell was kicked, it deals 10 damage divided as you choose among any number of targets instead.<i> (Those targets can include players and planeswalkers.)</i>
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageMultiEffect(10),
                new DamageTargetEffect(5),
                KickedCondition.ONCE,
                "{this} deals 5 damage to target creature. If this spell was kicked, "
                + "it deals 10 damage divided as you choose among any number of targets instead."
                + "<i> (Those targets can include players and planeswalkers.)</i>"
        ));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().setTargetAdjuster(FightWithFireAdjuster.instance);
    }

    private FightWithFire(final FightWithFire card) {
        super(card);
    }

    @Override
    public FightWithFire copy() {
        return new FightWithFire(this);
    }
}

enum FightWithFireAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        if (KickedCondition.ONCE.apply(game, ability)) {
            ability.addTarget(new TargetAnyTargetAmount(10));
        } else {
            ability.addTarget(new TargetCreaturePermanent());
        }
    }
}
