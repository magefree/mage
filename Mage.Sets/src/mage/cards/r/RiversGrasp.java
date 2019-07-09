
package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.ManaSpentToCastWatcher;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class RiversGrasp extends CardImpl {

    public RiversGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U/B}");

        // If {U} was spent to cast River's Grasp, return up to one target creature to its owner's hand. If {B} was spent to cast River's Grasp, target player reveals their hand, you choose a nonland card from it, then that player discards that card.
        Target targetCreature = new TargetCreaturePermanent(0, 1);
        Target targetPlayer = new TargetPlayer();
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ReturnToHandTargetEffect(),
                new ManaWasSpentCondition(ColoredManaSymbol.U), "If {U} was spent to cast {this}, return up to one target creature to its owner's hand"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new RiversGraspEffect(),
                new ManaWasSpentCondition(ColoredManaSymbol.B), " If {B} was spent to cast {this}, target player reveals their hand, you choose a nonland card from it, then that player discards that card"));

        this.getSpellAbility().addTarget(targetCreature);
        this.getSpellAbility().addTarget(targetPlayer);

        this.getSpellAbility().addEffect(new InfoEffect("<i>(Do both if {U}{B} was spent.)</i>"));
        this.getSpellAbility().addWatcher(new ManaSpentToCastWatcher());
    }

    public RiversGrasp(final RiversGrasp card) {
        super(card);
    }

    @Override
    public RiversGrasp copy() {
        return new RiversGrasp(this);
    }
}

class RiversGraspEffect extends OneShotEffect {

    public RiversGraspEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals their hand, you choose a card from it, then that player discards that card.";
    }

    public RiversGraspEffect(final RiversGraspEffect effect) {
        super(effect);
    }

    @Override
    public RiversGraspEffect copy() {
        return new RiversGraspEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        if (player != null) {
            player.revealCards("River's Grasp", player.getHand(), game);
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                TargetCard target = new TargetCard(Zone.HAND, StaticFilters.FILTER_CARD_A_NON_LAND);
                if (controller.choose(Outcome.Benefit, player.getHand(), target, game)) {
                    Card card = player.getHand().get(target.getFirstTarget(), game);
                    return player.discard(card, source, game);

                }
            }
        }
        return false;
    }
}
