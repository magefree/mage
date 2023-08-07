
package mage.cards.z;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChooseFriendsAndFoes;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class ZndrspltsJudgment extends CardImpl {

    public ZndrspltsJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // For each player, choose friend or foe. Each friend creates a token that's a copy of a creature they control. Each foe returns a creature they control to its owner's hand.
        this.getSpellAbility().addEffect(new ZndrspltsJudgmentEffect());
    }

    private ZndrspltsJudgment(final ZndrspltsJudgment card) {
        super(card);
    }

    @Override
    public ZndrspltsJudgment copy() {
        return new ZndrspltsJudgment(this);
    }
}

class ZndrspltsJudgmentEffect extends OneShotEffect {

    ZndrspltsJudgmentEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each player, choose friend or foe. Each friend creates a token that's a copy of a creature they control. Each foe returns a creature they control to its owner's hand";
    }

    ZndrspltsJudgmentEffect(final ZndrspltsJudgmentEffect effect) {
        super(effect);
    }

    @Override
    public ZndrspltsJudgmentEffect copy() {
        return new ZndrspltsJudgmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        ChooseFriendsAndFoes choice = new ChooseFriendsAndFoes();
        choice.chooseFriendOrFoe(controller, source, game);
        for (Player player : choice.getFriends()) {
            if (player == null) {
                continue;
            }
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");
            filter.add(new ControllerIdPredicate(player.getId()));
            TargetCreaturePermanent target = new TargetCreaturePermanent(1, 1, filter, true);
            if (!player.choose(Outcome.Copy, target, source, game)) {
                continue;
            }
            Effect effect = new CreateTokenCopyTargetEffect(player.getId());
            effect.setTargetPointer(new FixedTarget(target.getFirstTarget(), game));
            effect.apply(game, source);
        }
        for (Player player : choice.getFoes()) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");
            filter.add(new ControllerIdPredicate(player.getId()));
            TargetCreaturePermanent target = new TargetCreaturePermanent(1, 1, filter, true);
            if (!player.choose(Outcome.ReturnToHand, target, source, game)) {
                continue;
            }
            Effect effect = new ReturnToHandTargetEffect();
            effect.setTargetPointer(new FixedTarget(target.getFirstTarget(), game));
            effect.apply(game, source);
        }
        return true;
    }
}
