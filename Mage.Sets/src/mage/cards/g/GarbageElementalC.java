package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BattleCryAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class GarbageElementalC extends CardImpl {

    public GarbageElementalC(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Battle cry
        this.addAbility(new BattleCryAbility());

        // When Garbage Elemental enters the battlefield, roll two six-sided dice. Create a number of 1/1 red Goblin creature tokens equal to the difference between those results.
        this.addAbility(new EntersBattlefieldAbility(new GarbageElementalCEffect(),
                null,
                "When {this} enters the battlefield, roll two six-sided dice. Create a number of 1/1 red Goblin creature tokens equal to the difference between those results",
                null));

    }

    private GarbageElementalC(final GarbageElementalC card) {
        super(card);
    }

    @Override
    public GarbageElementalC copy() {
        return new GarbageElementalC(this);
    }
}

class GarbageElementalCEffect extends OneShotEffect {

    GarbageElementalCEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "roll two six-sided dice. Create a number of 1/1 red Goblin creature tokens equal to the difference between those results";
    }

    GarbageElementalCEffect(final GarbageElementalCEffect effect) {
        super(effect);
    }

    @Override
    public GarbageElementalCEffect copy() {
        return new GarbageElementalCEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Integer> results = controller.rollDice(outcome, source, game, 6, 2, 0);
            int thisRoll = results.get(0);
            int thatRoll = results.get(1);

            Token token = new GoblinToken();
            return token.putOntoBattlefield(Math.abs(thatRoll - thisRoll), game, source, source.getControllerId());
        }
        return false;
    }
}
