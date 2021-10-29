package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class HammerHelper extends CardImpl {

    public HammerHelper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Gain control of target creature until end of turn. Untap that creature and roll a six-sided die. Until end of turn, it gains haste and gets +X/+0, where X is the result.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new HammerHelperEffect());
    }

    private HammerHelper(final HammerHelper card) {
        super(card);
    }

    @Override
    public HammerHelper copy() {
        return new HammerHelper(this);
    }
}

class HammerHelperEffect extends OneShotEffect {

    HammerHelperEffect() {
        super(Outcome.Benefit);
        staticText = "Gain control of target creature until end of turn. Untap that creature and roll a six-sided die. Until end of turn, it gains haste and gets +X/+0, where X is the result";
    }

    HammerHelperEffect(HammerHelperEffect effect) {
        super(effect);
    }

    @Override
    public HammerHelperEffect copy() {
        return new HammerHelperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (controller != null && targetCreature != null) {
            source.getEffects().get(0).setTargetPointer(new FixedTarget(targetCreature.getId(), game));
            game.addEffect(new GainControlTargetEffect(Duration.EndOfTurn), source);
            targetCreature.untap(game);
            int amount = controller.rollDice(outcome, source, game, 6);
            game.addEffect(new BoostTargetEffect(amount, 0, Duration.EndOfTurn), source);
            game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
