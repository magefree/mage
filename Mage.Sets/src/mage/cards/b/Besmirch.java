package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.TargetPointer;

/**
 *
 * @author TheElk801
 */
public final class Besmirch extends CardImpl {

    public Besmirch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Until end of turn, gain control of target creature and it gains haste. Untap and goad that creature.
        this.getSpellAbility().addEffect(new BesmirchEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Besmirch(final Besmirch card) {
        super(card);
    }

    @Override
    public Besmirch copy() {
        return new Besmirch(this);
    }
}

class BesmirchEffect extends OneShotEffect {

    public BesmirchEffect() {
        super(Outcome.GainControl);
        staticText = "Until end of turn, gain control of target creature and it gains haste. Untap and goad that creature";
    }

    public BesmirchEffect(final BesmirchEffect effect) {
        super(effect);
    }

    @Override
    public BesmirchEffect copy() {
        return new BesmirchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getPermanent(source.getFirstTarget()) != null) {
            TargetPointer target = new FixedTarget(source.getFirstTarget(), game);

            // gain control
            ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfTurn);
            effect.setTargetPointer(target);
            game.addEffect(effect, source);

            // haste
            effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
            effect.setTargetPointer(target);
            game.addEffect(effect, source);

            // goad
            Effect effect2 = new GoadTargetEffect();
            effect2.setTargetPointer(target);
            effect2.apply(game, source);

            // untap
            effect2 = new UntapTargetEffect();
            effect2.setTargetPointer(target);
            effect2.apply(game, source);

            return true;
        }
        return false;
    }
}
