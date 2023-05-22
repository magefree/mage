
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author intimidatingant
 */
public final class ChaliceOfLife extends TransformingDoubleFacedCard {

    public ChaliceOfLife(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{3}",
                "Chalice of Death",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, ""
        );

        // {tap}: You gain 1 life. Then if you have at least 10 life more than your starting life total, transform Chalice of Life.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new ChaliceOfLifeEffect(), new TapSourceCost()));

        // Chalice of Death
        // {tap}: Target player loses 5 life.
        Ability ability = new SimpleActivatedAbility(new LoseLifeTargetEffect(5), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.getRightHalfCard().addAbility(ability);
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
        staticText = "You gain 1 life. Then if you have at least 10 life " +
                "more than your starting life total, transform Chalice of Life";
    }

    public ChaliceOfLifeEffect(final ChaliceOfLifeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.gainLife(1, game, source);
        if (player.getLife() < game.getStartingLife() + 10) {
            return true;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent == null || permanent.transform(source, game);
    }

    @Override
    public ChaliceOfLifeEffect copy() {
        return new ChaliceOfLifeEffect(this);
    }

}
