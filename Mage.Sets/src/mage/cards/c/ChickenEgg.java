package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.GiantBirdToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author ciaccona007
 */

public final class ChickenEgg extends CardImpl {

    public ChickenEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.EGG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, roll a six-sided die. If you roll a 6, sacrifice Chicken Egg and create a 4/4 red Giant Bird creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ChickenEggEffect(), TargetController.YOU, false));
    }

    private ChickenEgg(final ChickenEgg card) {
        super(card);
    }

    @Override
    public ChickenEgg copy() {
        return new ChickenEgg(this);
    }
}

class ChickenEggEffect extends OneShotEffect {

    ChickenEggEffect() {
        super(Outcome.Benefit);
        this.staticText = "roll a six-sided die. If you roll a 6, sacrifice {this} and create a 4/4 red Giant Bird creature token";
    }

    ChickenEggEffect(final ChickenEggEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int result = controller.rollDice(outcome, source, game, 6);
            if (result == 6) {
                new SacrificeSourceEffect().apply(game, source);
                return (new CreateTokenEffect(new GiantBirdToken(), 1)).apply(game, source);
            }
        }
        return false;
    }

    @Override
    public ChickenEggEffect copy() {
        return new ChickenEggEffect(this);
    }
}
