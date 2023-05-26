
package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author BetaSteward
 */
public final class TreeOfRedemption extends CardImpl {

    public TreeOfRedemption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.PLANT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(13);

        this.addAbility(DefenderAbility.getInstance());

        // {T}: Exchange your life total with Tree of Redemption's toughness.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TreeOfRedemptionEffect(), new TapSourceCost()));
    }

    private TreeOfRedemption(final TreeOfRedemption card) {
        super(card);
    }

    @Override
    public TreeOfRedemption copy() {
        return new TreeOfRedemption(this);
    }
}

class TreeOfRedemptionEffect extends OneShotEffect {

    public TreeOfRedemptionEffect() {
        super(Outcome.GainLife);
        staticText = "Exchange your life total with {this}'s toughness";
    }

    public TreeOfRedemptionEffect(final TreeOfRedemptionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm == null || player == null || !player.isLifeTotalCanChange()) {
            return false;
        }


        int amount = perm.getToughness().getValue();
        int life = player.getLife();
        if (life == amount) {
            return false;
        }
        if (life < amount && !player.isCanGainLife()) {
            return false;
        }
        if (life > amount && !player.isCanLoseLife()) {
            return false;
        }
        player.setLife(amount, game, source);
        game.addEffect(new SetBasePowerToughnessSourceEffect(Integer.MIN_VALUE, life, Duration.WhileOnBattlefield, SubLayer.SetPT_7b), source);
        return true;
    }

    @Override
    public TreeOfRedemptionEffect copy() {
        return new TreeOfRedemptionEffect(this);
    }

}
