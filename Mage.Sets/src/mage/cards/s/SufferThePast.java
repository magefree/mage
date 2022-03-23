
package mage.cards.s;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class SufferThePast extends CardImpl {

    public SufferThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{B}");


        // Exile X target cards from target player's graveyard. For each card exiled this way, that player loses 1 life and you gain 1 life.
        this.getSpellAbility().addEffect(new SufferThePastEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private SufferThePast(final SufferThePast card) {
        super(card);
    }

    @Override
    public SufferThePast copy() {
        return new SufferThePast(this);
    }
}

class SufferThePastEffect extends OneShotEffect {
    
    public SufferThePastEffect() {
        super(Outcome.Neutral);
        this.staticText = "Exile X target cards from target player's graveyard. For each card exiled this way, that player loses 1 life and you gain 1 life";
    }

    public SufferThePastEffect(final SufferThePastEffect effect) {
        super(effect);
    }

    @Override
    public SufferThePastEffect copy() {
        return new SufferThePastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filter = new FilterCard("card in target player's graveyard");
        int numberExiled = 0;
        Player you = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            int numberToTarget = Math.min(targetPlayer.getGraveyard().size(), source.getManaCostsToPay().getX());
            TargetCardInOpponentsGraveyard target = new TargetCardInOpponentsGraveyard(numberToTarget, numberToTarget, filter);
            if (you != null) {
                if (target.canChoose(source.getControllerId(), source, game) && target.choose(Outcome.Neutral, source.getControllerId(), source.getSourceId(), source, game)) {
                    if (!target.getTargets().isEmpty()) {
                        List<UUID> targets = target.getTargets();
                        for (UUID targetId : targets) {
                            Card card = game.getCard(targetId);
                            if (card != null) {
                                card.moveToExile(id, "Suffer the Past", source, game);
                                numberExiled ++;
                            }
                        }
                        you.gainLife(numberExiled, game, source);
                        targetPlayer.loseLife(numberExiled, game, source, false);
                    }
                }
                return true;
            }
        }
        return false;
    }
}


