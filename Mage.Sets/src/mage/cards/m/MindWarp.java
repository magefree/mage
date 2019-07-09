
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class MindWarp extends CardImpl {

    public MindWarp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{3}{B}");

        // Look at target player's hand and choose X cards from it. That player discards those cards.
        this.getSpellAbility().addEffect(new MindWarpEffect(ManacostVariableValue.instance));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public MindWarp(final MindWarp card) {
        super(card);
    }

    @Override
    public MindWarp copy() {
        return new MindWarp(this);
    }
}

class MindWarpEffect extends OneShotEffect {

    private final DynamicValue xValue;

    public MindWarpEffect(DynamicValue xValue) {
        super(Outcome.Discard);
        this.xValue = xValue;
        staticText = "Look at target player's hand and choose X cards from it. That player discards those card.";
    }

    public MindWarpEffect(final MindWarpEffect effect) {
        super(effect);
        this.xValue = effect.xValue;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && you != null) {
            int amountToDiscard = Math.min(
                    xValue.calculate(game, source, this),
                    targetPlayer.getHand().size()
            );
            you.lookAtCards("Discard", targetPlayer.getHand(), game);
            TargetCard target = new TargetCard(amountToDiscard, Zone.HAND, new FilterCard());
            target.setNotTarget(true);
            if (you.choose(Outcome.Benefit, targetPlayer.getHand(), target, game)) {
                Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
                return targetPlayer.discard(card, source, game);

            }

        }
        return false;
    }

    @Override
    public MindWarpEffect copy() {
        return new MindWarpEffect(this);
    }
}
