
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class EvraHalcyonWitness extends CardImpl {

    public EvraHalcyonWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {4}: Exchange your life total with Evra, Halcyon Witness's power.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new EvraHalcyonWitnessEffect(), new ManaCostsImpl<>("{4}")));
    }

    private EvraHalcyonWitness(final EvraHalcyonWitness card) {
        super(card);
    }

    @Override
    public EvraHalcyonWitness copy() {
        return new EvraHalcyonWitness(this);
    }
}

class EvraHalcyonWitnessEffect extends OneShotEffect {

    public EvraHalcyonWitnessEffect() {
        super(Outcome.GainLife);
        staticText = "Exchange your life total with {this}'s power";
    }

    public EvraHalcyonWitnessEffect(final EvraHalcyonWitnessEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.isLifeTotalCanChange()) {
            Permanent perm = game.getPermanent(source.getSourceId());
            if (perm != null) {
                int amount = perm.getPower().getValue();
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
                game.addEffect(new SetPowerToughnessSourceEffect(life, Integer.MIN_VALUE, Duration.Custom, SubLayer.SetPT_7b), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public EvraHalcyonWitnessEffect copy() {
        return new EvraHalcyonWitnessEffect(this);
    }

}
