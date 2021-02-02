
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class TreeOfPerdition extends CardImpl {

    public TreeOfPerdition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.PLANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(13);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {tap}: Exchange target opponent's life total with Tree of Perdition's toughness.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TreeOfPerditionEffect(), new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TreeOfPerdition(final TreeOfPerdition card) {
        super(card);
    }

    @Override
    public TreeOfPerdition copy() {
        return new TreeOfPerdition(this);
    }
}

class TreeOfPerditionEffect extends OneShotEffect {

    public TreeOfPerditionEffect() {
        super(Outcome.Neutral);
        staticText = "Exchange target opponent's life total with {this}'s toughness";
    }

    public TreeOfPerditionEffect(final TreeOfPerditionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent != null && opponent.isLifeTotalCanChange()) {
            Permanent perm = game.getPermanent(source.getSourceId());
            if (perm != null) {
                int amount = perm.getToughness().getValue();
                int life = opponent.getLife();
                if (life == amount) {
                    return false;
                }
                if (life < amount && !opponent.isCanGainLife()) {
                    return false;
                }
                if (life > amount && !opponent.isCanLoseLife()) {
                    return false;
                }
                opponent.setLife(amount, game, source);
                perm.getToughness().modifyBaseValue(life);
                // game.addEffect(new SetPowerToughnessSourceEffect(Integer.MIN_VALUE, life, Duration.Custom, SubLayer.SetPT_7b), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public TreeOfPerditionEffect copy() {
        return new TreeOfPerditionEffect(this);
    }

}
