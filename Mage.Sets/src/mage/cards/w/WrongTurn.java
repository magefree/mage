package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WrongTurn extends CardImpl {

    public WrongTurn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Target opponent gains control of target creature.
        this.getSpellAbility().addEffect(new WrongTurnEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WrongTurn(final WrongTurn card) {
        super(card);
    }

    @Override
    public WrongTurn copy() {
        return new WrongTurn(this);
    }
}

class WrongTurnEffect extends OneShotEffect {

    WrongTurnEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent gains control of target creature";
    }

    private WrongTurnEffect(final WrongTurnEffect effect) {
        super(effect);
    }

    @Override
    public WrongTurnEffect copy() {
        return new WrongTurnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new GainControlTargetEffect(
                Duration.Custom, true, source.getFirstTarget()
        ).setTargetPointer(new FixedTarget(source.getTargets().get(1).getFirstTarget(), game)), source);
        return true;
    }
}
