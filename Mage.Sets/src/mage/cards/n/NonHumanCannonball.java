package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NonHumanCannonball extends CardImpl {

    public NonHumanCannonball(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.CLOWN);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Non-Human Cannonball dies, roll a six-sided die. If the result is 4 or less, Non-Human Cannonball deals that much damage to you.
        this.addAbility(new DiesSourceTriggeredAbility(new NonHumanCannonballEffect()));
    }

    private NonHumanCannonball(final NonHumanCannonball card) {
        super(card);
    }

    @Override
    public NonHumanCannonball copy() {
        return new NonHumanCannonball(this);
    }
}

class NonHumanCannonballEffect extends OneShotEffect {

    NonHumanCannonballEffect() {
        super(Outcome.Benefit);
        staticText = "roll a six-sided die. If the result is 4 or less, {this} deals that much damage to you";
    }

    private NonHumanCannonballEffect(final NonHumanCannonballEffect effect) {
        super(effect);
    }

    @Override
    public NonHumanCannonballEffect copy() {
        return new NonHumanCannonballEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int result = player.rollDice(outcome, source, game, 6);
        if (result <= 4) {
            player.damage(result, source, game);
        }
        return true;
    }
}
