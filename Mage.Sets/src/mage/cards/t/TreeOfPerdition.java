
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

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
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm == null || opponent == null || !opponent.isLifeTotalCanChange()) {
            return false;
        }

        int amount = perm.getToughness().getValue(); // Must get total value
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

        // Any toughness-modifying effects, counters, Auras, or Equipment will apply after its toughness is set to the player’s former life total.
        // For example, say Tree of Perdition is equipped with Cultist’s Staff (which makes it 2/15) and the player’s life total is 7.
        // After the exchange, Tree of Perdition would be a 2/9 creature (its toughness became 7, which was then modified by Cultist’s Staff) and the player’s life total would be 15.
        // (2016-07-13)
        ContinuousEffect powerToughnessEffect = new SetBasePowerToughnessTargetEffect(null, StaticValue.get(life), Duration.Custom);
        powerToughnessEffect.setTargetPointer(new FixedTarget(perm.getId()));
        game.addEffect(powerToughnessEffect, source);
        return true;
    }

    @Override
    public TreeOfPerditionEffect copy() {
        return new TreeOfPerditionEffect(this);
    }

}
