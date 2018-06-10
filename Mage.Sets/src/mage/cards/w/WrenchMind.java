
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetDiscard;

/**
 *
 * @author LevelX2
 */
public final class WrenchMind extends CardImpl {

    public WrenchMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}");


        // Target player discards two cards unless he or she discards an artifact card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new WrenchMindEffect());

    }

    public WrenchMind(final WrenchMind card) {
        super(card);
    }

    @Override
    public WrenchMind copy() {
        return new WrenchMind(this);
    }
}

class WrenchMindEffect extends OneShotEffect {

    public WrenchMindEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player discards two cards unless he or she discards an artifact card";
    }

    public WrenchMindEffect(final WrenchMindEffect effect) {
        super(effect);
    }

    @Override
    public WrenchMindEffect copy() {
        return new WrenchMindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetPlayer != null && !targetPlayer.getHand().isEmpty()) {
            TargetDiscard target = new TargetDiscard(targetPlayer.getId());
            targetPlayer.choose(Outcome.Discard, target, source.getSourceId(), game);
            Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                targetPlayer.discard(card, source, game);
                if (!card.isArtifact() && !targetPlayer.getHand().isEmpty()) {
                    targetPlayer.discard(1, source, game);
                }
                return true;
            }            
        }
        return false;
    }
}
