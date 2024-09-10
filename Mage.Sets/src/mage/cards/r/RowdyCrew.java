
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class RowdyCrew extends CardImpl {

    public RowdyCrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Rowdy Crew enters the battlefield, draw three cards, then discard two cards at random. If two cards that share a card type are discarded this way, put two +1/+1 counters on Rowdy Crew.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RowdyCrewEffect()));
    }

    private RowdyCrew(final RowdyCrew card) {
        super(card);
    }

    @Override
    public RowdyCrew copy() {
        return new RowdyCrew(this);
    }
}

class RowdyCrewEffect extends OneShotEffect {

    RowdyCrewEffect() {
        super(Outcome.Benefit);
        this.staticText = "draw three cards, then discard two cards at random. If two cards that share a card type are discarded this way, put two +1/+1 counters on {this}";
    }

    private RowdyCrewEffect(final RowdyCrewEffect effect) {
        super(effect);
    }

    @Override
    public RowdyCrewEffect copy() {
        return new RowdyCrewEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(3, source, game);
            Cards cards = new CardsImpl();
            int cardsInHand = player.getHand().size();
            switch (cardsInHand) {
                case 0:
                    break;
                case 1:
                    player.discard(1, true, false, source, game);
                    break;
                default:
                    cards = player.discard(2, true, false, source, game);
            }
            if (creature != null && cardsInHand > 1) {
                for (CardType type : CardType.values()) {
                    int count = 0;
                    for (UUID cardId : cards) {
                        if (game.getCard(cardId).getCardType(game).contains(type)) {
                            count++;
                            if (count > 1) {
                                creature.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
                                return true;
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
