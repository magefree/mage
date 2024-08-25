package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VelukanDragon extends CardImpl {

    public VelukanDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Velukan Dragon attacks or blocks, roll a six-sided die. Velukan Dragon gets +X/+0 until end of turn, where X is the result minus 1.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new VelukanDragonEffect(), false));
    }

    private VelukanDragon(final VelukanDragon card) {
        super(card);
    }

    @Override
    public VelukanDragon copy() {
        return new VelukanDragon(this);
    }
}

class VelukanDragonEffect extends OneShotEffect {

    VelukanDragonEffect() {
        super(Outcome.Benefit);
        staticText = "roll a six-sided die. {this} gets +X/+0 until end of turn, where X is the result minus 1";
    }

    private VelukanDragonEffect(final VelukanDragonEffect effect) {
        super(effect);
    }

    @Override
    public VelukanDragonEffect copy() {
        return new VelukanDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int roll = player.rollDice(Outcome.BoostCreature, source, game, 6);
        if (roll > 1) {
            game.addEffect(new BoostSourceEffect(roll - 1, 0, Duration.EndOfTurn), source);
        }
        return true;
    }
}
