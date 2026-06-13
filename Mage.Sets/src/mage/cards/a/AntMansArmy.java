package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class AntMansArmy extends CardImpl {

    public AntMansArmy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature enters, create a Food token or a Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AntMansArmyEffect()));
    }

    private AntMansArmy(final AntMansArmy card) {
        super(card);
    }

    @Override
    public AntMansArmy copy() {
        return new AntMansArmy(this);
    }
}

class AntMansArmyEffect extends OneShotEffect {

    AntMansArmyEffect() {
        super(Outcome.Benefit);
        staticText = "create a Food token or a Treasure token";
    }

    private AntMansArmyEffect(final AntMansArmyEffect effect) {
        super(effect);
    }

    @Override
    public AntMansArmyEffect copy() {
        return new AntMansArmyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Token token = player.chooseUse(
            outcome, "Create a Food token or a Treasure token?",
            null, "Food", "Treasure", source, game
        ) ? new FoodToken() : new TreasureToken();
        return token.putOntoBattlefield(1, game, source, source.getControllerId());
    }
}
