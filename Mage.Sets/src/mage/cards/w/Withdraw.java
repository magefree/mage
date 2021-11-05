package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class Withdraw extends CardImpl {

    public Withdraw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // Return target creature to its owner's hand. Then return another target creature to its owner's hand unless its controller pays {1}.
        this.getSpellAbility().addEffect(new WithdrawEffect());
        Target target = new TargetCreaturePermanent(new FilterCreaturePermanent("creature to return unconditionally"));
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature to return unless {1} is paid");
        filter.add(new AnotherTargetPredicate(2));
        target = new TargetCreaturePermanent(filter);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target);
    }

    private Withdraw(final Withdraw card) {
        super(card);
    }

    @Override
    public Withdraw copy() {
        return new Withdraw(this);
    }
}

class WithdrawEffect extends OneShotEffect {

    WithdrawEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature to its owner's hand. Then return another target creature to its owner's hand unless its controller pays {1}";
    }

    WithdrawEffect(final WithdrawEffect effect) {
        super(effect);
    }

    @Override
    public WithdrawEffect copy() {
        return new WithdrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Effect effect = new ReturnToHandTargetEffect();
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
        effect.apply(game, source);
        Permanent secondCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (secondCreature != null) {
            Player creatureController = game.getPlayer(secondCreature.getControllerId());
            if (creatureController != null) {
                Cost cost = ManaUtil.createManaCost(1, false);
                if (creatureController.chooseUse(Outcome.Benefit, "Pay {1}? (Otherwise " + secondCreature.getName() + " will be returned to its owner's hand)", source, game)) {
                    cost.pay(source, game, source, creatureController.getId(), false);
                }
                if (!cost.isPaid()) {
                    creatureController.moveCards(secondCreature, Zone.HAND, source, game);
                }
            }
        }
        return true;
    }
}
