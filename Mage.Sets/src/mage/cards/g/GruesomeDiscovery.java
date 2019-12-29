
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
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

import java.util.List;
import java.util.UUID;

/**
 * @author North
 */
public final class GruesomeDiscovery extends CardImpl {

    public GruesomeDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");


        // Target player discards two cards.
        // <i>Morbid</i> &mdash; If a creature died this turn, instead that player reveals their hand, you choose two cards from it, then that player discards those cards.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GruesomeDiscoveryEffect(),
                new DiscardTargetEffect(2),
                MorbidCondition.instance,
                "Target player discards two cards. <i>Morbid</i> &mdash; If a creature died this turn, instead that player reveals their hand, you choose two cards from it, then that player discards those cards"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public GruesomeDiscovery(final GruesomeDiscovery card) {
        super(card);
    }

    @Override
    public GruesomeDiscovery copy() {
        return new GruesomeDiscovery(this);
    }
}

class GruesomeDiscoveryEffect extends OneShotEffect {

    public GruesomeDiscoveryEffect() {
        super(Outcome.Discard);
        this.staticText = "target player reveals their hand, you choose two cards from it, then that player discards those cards";
    }

    public GruesomeDiscoveryEffect(final GruesomeDiscoveryEffect effect) {
        super(effect);
    }

    @Override
    public GruesomeDiscoveryEffect copy() {
        return new GruesomeDiscoveryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());

        if (player != null && targetPlayer != null) {
            targetPlayer.revealCards("Gruesome Discovery", targetPlayer.getHand(), game);

            if (targetPlayer.getHand().size() <= 2) {
                targetPlayer.discard(2, source, game);
            }

            TargetCard target = new TargetCard(2, Zone.HAND, new FilterCard());
            if (player.choose(Outcome.Benefit, targetPlayer.getHand(), target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = targetPlayer.getHand().get(targetId, game);
                    targetPlayer.discard(card, source, game);

                }
            }
            return true;
        }
        return false;
    }
}
