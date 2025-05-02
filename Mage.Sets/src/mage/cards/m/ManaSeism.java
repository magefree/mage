
package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetSacrifice;

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

    private ManaSeism(final ManaSeism card) {
        super(card);
    }

    @Override
    public ManaSeism copy() {
        return new ManaSeism(this);
    }
}

class ManaSeismEffect extends OneShotEffect {

    ManaSeismEffect() {
        super(Outcome.Neutral);
        staticText  = "Sacrifice any number of lands, then add that much {C}";
    }

    private ManaSeismEffect(final ManaSeismEffect effect) {
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
        TargetSacrifice sacrificeLand = new TargetSacrifice(0, Integer.MAX_VALUE, StaticFilters.FILTER_LAND);
        if (player.choose(Outcome.Sacrifice, sacrificeLand, source, game)) {
            for (UUID uuid : sacrificeLand.getTargets()) {
                Permanent land = game.getPermanent(uuid);
                if (land != null) {
                    land.sacrifice(source, game);
                    amount++;
                }
            }
        }
        player.getManaPool().addMana(Mana.ColorlessMana(amount), game, source);
        return true;
    }

}
