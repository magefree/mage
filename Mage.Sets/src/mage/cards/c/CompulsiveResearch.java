
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
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
 * @author Plopman
 */
public final class CompulsiveResearch extends CardImpl {

    public CompulsiveResearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");


        // Target player draws three cards. Then that player discards two cards unless he or she discards a land card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(3));
        this.getSpellAbility().addEffect(new CompulsiveResearchDiscardEffect());
    }

    public CompulsiveResearch(final CompulsiveResearch card) {
        super(card);
    }

    @Override
    public CompulsiveResearch copy() {
        return new CompulsiveResearch(this);
    }
}
class CompulsiveResearchDiscardEffect extends OneShotEffect {

    public CompulsiveResearchDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "Then that player discards two cards unless he or she discards a land card";
    }

    public CompulsiveResearchDiscardEffect(final CompulsiveResearchDiscardEffect effect) {
        super(effect);
    }

    @Override
    public CompulsiveResearchDiscardEffect copy() {
        return new CompulsiveResearchDiscardEffect(this);
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
                if (!card.isLand() && !targetPlayer.getHand().isEmpty()) {
                    targetPlayer.discard(1, false, source, game);
                }
                return true;
            }            
        }
        return false;
    }
}
