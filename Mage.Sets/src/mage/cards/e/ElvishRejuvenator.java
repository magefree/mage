package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author TheElk801
 */
public final class ElvishRejuvenator extends CardImpl {

    public ElvishRejuvenator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Elvish Rejuvenator enters the battlefield, look at the top five cards of your library. You may put a land card from among them onto the battlefield tapped. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ElvishRejuvenatorEffect(), false));
    }

    private ElvishRejuvenator(final ElvishRejuvenator card) {
        super(card);
    }

    @Override
    public ElvishRejuvenator copy() {
        return new ElvishRejuvenator(this);
    }
}

class ElvishRejuvenatorEffect extends OneShotEffect {

    public ElvishRejuvenatorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "look at the top five cards of your library. "
                + "You may put a land card from among them onto the battlefield tapped. "
                + "Put the rest on the bottom of your library in a random order";
    }

    public ElvishRejuvenatorEffect(final ElvishRejuvenatorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, 5));
        if (!cards.isEmpty()) {
            TargetCard target = new TargetCard(
                    0, 1, Zone.LIBRARY,
                    new FilterLandCard("land card to put on the battlefield")
            );
            if (controller.choose(Outcome.PutCardInPlay, cards, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
                }
            }
            if (!cards.isEmpty()) {
                controller.putCardsOnBottomOfLibrary(cards, game, source, false);
            }
        }
        return true;
    }

    @Override
    public ElvishRejuvenatorEffect copy() {
        return new ElvishRejuvenatorEffect(this);
    }
}
