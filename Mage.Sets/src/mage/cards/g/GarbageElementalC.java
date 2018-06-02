
package mage.cards.g;

import java.util.UUID;
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
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
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
        this.addAbility(new EntersBattlefieldAbility(new GarbageElementalEffect(),
                null,
                "When {this} enters the battlefield, roll two six-sided dice. Create a number of 1/1 red Goblin creature tokens equal to the difference between those results",
                null));

    }

    public GarbageElementalC(final GarbageElementalC card) {
        super(card);
    }

    @Override
    public GarbageElementalC copy() {
        return new GarbageElementalC(this);
    }
}

class GarbageElementalEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("permanent with a counter");

    static {
        filter.add(new CounterPredicate(null));
    }

    GarbageElementalEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "roll two six-sided dice. Create a number of 1/1 red Goblin creature tokens equal to the difference between those results";
    }

    GarbageElementalEffect(final GarbageElementalEffect effect) {
        super(effect);
    }

    @Override
    public GarbageElementalEffect copy() {
        return new GarbageElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int thisRoll = controller.rollDice(game, 6);
            int thatRoll = controller.rollDice(game, 6);

            Token token = new GoblinToken();
            return token.putOntoBattlefield(Math.abs(thatRoll - thisRoll), game, source.getSourceId(), source.getControllerId());
        }
        return false;
    }
}
