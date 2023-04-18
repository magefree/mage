
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author intimidatingant
 */
public final class ChaliceOfLife extends CardImpl {

    public ChaliceOfLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        this.secondSideCardClazz = mage.cards.c.ChaliceOfDeath.class;
        this.addAbility(new TransformAbility());


        // {tap}: You gain 1 life. Then if you have at least 10 life more than your starting life total, transform Chalice of Life.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ChaliceOfLifeEffect(), new TapSourceCost()));
    }

    private ChaliceOfLife(final ChaliceOfLife card) {
        super(card);
    }

    @Override
    public ChaliceOfLife copy() {
        return new ChaliceOfLife(this);
    }
}

class ChaliceOfLifeEffect extends OneShotEffect {

    public ChaliceOfLifeEffect() {
        super(Outcome.GainLife);
        staticText = "You gain 1 life. Then if you have at least 10 life more than your starting life total, transform Chalice of Life";
    }

    public ChaliceOfLifeEffect(final ChaliceOfLifeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Player player = game.getPlayer(source.getControllerId());
            if(player != null) {
                //gain 1 life
                player.gainLife(1, game, source);

                // if you have at least 10 life more than your starting life total, transform Chalice of Life.
                if (player.getLife() >= game.getStartingLife() + 10) {
                    permanent.transform(source, game);
                }
            }
        }
        return false;
    }

    @Override
    public ChaliceOfLifeEffect copy() {
        return new ChaliceOfLifeEffect(this);
    }

}
