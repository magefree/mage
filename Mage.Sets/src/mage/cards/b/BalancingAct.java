
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman (Restore Balance), cbt33
 */
public final class BalancingAct extends CardImpl {

    public BalancingAct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}{W}");


        // Each player chooses a number of permanents he or she controls equal to the number of permanents controlled by the player who controls the fewest, then sacrifices the rest. Each player discards cards the same way.
        this.getSpellAbility().addEffect(new BalancingActEffect());
    }

    public BalancingAct(final BalancingAct card) {
        super(card);
    }

    @Override
    public BalancingAct copy() {
        return new BalancingAct(this);
    }
}

class BalancingActEffect extends OneShotEffect {

   
    public BalancingActEffect() {
        super(Outcome.Sacrifice);
        staticText = "Each player chooses a number of permanents he or she controls equal to the number of permanents controlled by the player who controls the fewest, then sacrifices the rest. Each player discards cards the same way";
    }

    public BalancingActEffect(final BalancingActEffect effect) {
        super(effect);
    }

    @Override
    public BalancingActEffect copy() {
        return new BalancingActEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int minPermanent = Integer.MAX_VALUE, minCard = Integer.MAX_VALUE;
            // count minimal permanents
            for(UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)){
                Player player = game.getPlayer(playerId);
                if(player != null){
                    int count = game.getBattlefield().getActivePermanents(new FilterControlledPermanent(), player.getId(), source.getSourceId(), game).size();
                    if(count < minPermanent){
                        minPermanent = count;
                    }
                }
            }
            // sacrifice permanents over the minimum
            for(UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)){
                Player player = game.getPlayer(playerId);
                if(player != null){
                    TargetControlledPermanent target = new TargetControlledPermanent(minPermanent, minPermanent, new FilterControlledPermanent(), true);
                    if(target.choose(Outcome.Benefit, player.getId(), source.getSourceId(), game)){
                        for(Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledPermanent(), player.getId(), source.getSourceId(), game)){
                            if(permanent != null && !target.getTargets().contains(permanent.getId())){
                                permanent.sacrifice(source.getSourceId(), game);
                            }
                        }
                    }
                }
            }

            // count minimal cards in hand
            for(UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)){
                Player player = game.getPlayer(playerId);
                if(player != null){
                    int count = player.getHand().size();
                    if(count < minCard){
                        minCard = count;
                    }
                }
            }
            
            // discard cards over the minimum
            for(UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)){
                Player player = game.getPlayer(playerId);
                if(player != null){
                    TargetCardInHand target = new TargetCardInHand(minCard, new FilterCard());
                    if(target.choose(Outcome.Benefit, player.getId(), source.getSourceId(), game)){
                        Cards cards =  player.getHand().copy();
                        for(UUID cardUUID : cards){
                            Card card = player.getHand().get(cardUUID, game);
                            if(!target.getTargets().contains(cardUUID)){
                                player.discard(card, source, game);
                            }
                        }
                    }
                }
            }

            return true;
        }
        return false;
    }

}
