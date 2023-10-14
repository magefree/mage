
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class MoldgrafMonstrosity extends CardImpl {

    public MoldgrafMonstrosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        this.addAbility(TrampleAbility.getInstance());
        // When Moldgraf Monstrosity dies, exile it, then return two creature cards at random from your graveyard to the battlefield.
        Effect effect = new ExileSourceEffect();
        effect.setText("");
        DiesSourceTriggeredAbility ability = new DiesSourceTriggeredAbility(effect);
        ability.addEffect(new MoldgrafMonstrosityEffect());
        this.addAbility(ability);
    }

    private MoldgrafMonstrosity(final MoldgrafMonstrosity card) {
        super(card);
    }

    @Override
    public MoldgrafMonstrosity copy() {
        return new MoldgrafMonstrosity(this);
    }
}

class MoldgrafMonstrosityEffect extends OneShotEffect {

    public MoldgrafMonstrosityEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "exile it, then return two creature cards at random from your graveyard to the battlefield";
    }

    private MoldgrafMonstrosityEffect(final MoldgrafMonstrosityEffect effect) {
        super(effect);
    }

    @Override
    public MoldgrafMonstrosityEffect copy() {
        return new MoldgrafMonstrosityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards possibleCards = new CardsImpl(controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
            // Set<Card> cards = controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game);
            Cards toBattlefield = new CardsImpl();
            for (int i = 0; i < 2; i++) {
                Card card = possibleCards.getRandom(game);
                if (card != null) {
                    toBattlefield.add(card);
                    possibleCards.remove(card);
                }
            }
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
            return true;
        }
        return false;
    }
}
