
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.token.DroidToken;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public final class MarchOfTheDroids extends CardImpl {

    public MarchOfTheDroids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{U}{B}");

        // Remove all repair counters from all cards in your graveyard. Return each card with a repair counter removed this way from graveyard to the battlefield.
        this.getSpellAbility().addEffect(new MarchOfTheDroidsEffect());

        // Create 1/1 colorles Droid artifact creature token for each Droid you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new DroidToken(), new PermanentsOnBattlefieldCount(new FilterPermanent(SubType.DROID, "Droid you control"))));
    }

    private MarchOfTheDroids(final MarchOfTheDroids card) {
        super(card);
    }

    @Override
    public MarchOfTheDroids copy() {
        return new MarchOfTheDroids(this);
    }
}

class MarchOfTheDroidsEffect extends OneShotEffect {

    public MarchOfTheDroidsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Remove all repair counters from all cards in your graveyard. Return each card with a repair counter removed this way from graveyard to the battlefield";
    }

    private MarchOfTheDroidsEffect(final MarchOfTheDroidsEffect effect) {
        super(effect);
    }

    @Override
    public MarchOfTheDroidsEffect copy() {
        return new MarchOfTheDroidsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToReturn = new CardsImpl();
            for (Card card : controller.getGraveyard().getCards(game)) {
                if (card.isOwnedBy(controller.getId()) && card.getCounters(game).getCount(CounterType.REPAIR) > 0) {
                    int number = card.getCounters(game).getCount(CounterType.REPAIR);
                    if (number > 0) {
                        cardsToReturn.add(card);
                        card.removeCounters(CounterType.REPAIR.createInstance(number), source, game);
                    }
                }
            }
            if (!cardsToReturn.isEmpty()) {
                controller.moveCards(cardsToReturn, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
