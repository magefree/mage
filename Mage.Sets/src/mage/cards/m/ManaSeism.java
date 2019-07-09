
package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class ManaSeism extends CardImpl {

    public ManaSeism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        // Sacrifice any number of lands. Add {C} for each land sacrificed this way.
        this.getSpellAbility().addEffect(new ManaSeismEffect());
        
    }

    public ManaSeism(final ManaSeism card) {
        super(card);
    }

    @Override
    public ManaSeism copy() {
        return new ManaSeism(this);
    }
}

class ManaSeismEffect extends OneShotEffect {

    public ManaSeismEffect() {
        super(Outcome.Neutral);
        staticText  = "Sacrifice any number of lands. Add {C} for each land sacrificed this way";
    }

    public ManaSeismEffect(final ManaSeismEffect effect) {
        super(effect);
    }

    @Override
    public ManaSeismEffect copy() {
        return new ManaSeismEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null){
            return false;
        }
        int amount = 0;
        TargetControlledPermanent sacrificeLand = new TargetControlledPermanent(0, Integer.MAX_VALUE, new FilterControlledLandPermanent(), true);
        if(player.chooseTarget(Outcome.Sacrifice, sacrificeLand, source, game)){
            for(UUID uuid : sacrificeLand.getTargets()){
                Permanent land = game.getPermanent(uuid);
                if(land != null){
                    land.sacrifice(source.getSourceId(), game);
                    amount++;
                }
            }
        }
        player.getManaPool().addMana(Mana.ColorlessMana(amount), game, source);
        return true;
    }

}
