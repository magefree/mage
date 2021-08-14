
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class DesecratorHag extends CardImpl {

    public DesecratorHag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B/G}{B/G}");
        this.subtype.add(SubType.HAG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Desecrator Hag enters the battlefield, return to your hand the creature card in your graveyard with the greatest power. If two or more cards are tied for greatest power, you choose one of them.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DesecratorHagEffect(), false));

    }

    private DesecratorHag(final DesecratorHag card) {
        super(card);
    }

    @Override
    public DesecratorHag copy() {
        return new DesecratorHag(this);
    }
}

class DesecratorHagEffect extends OneShotEffect {

    int creatureGreatestPower = 0;
    Cards cards = new CardsImpl();
    TargetCard target = new TargetCard(Zone.GRAVEYARD, new FilterCard());

    public DesecratorHagEffect() {
        super(Outcome.DrawCard);
        this.staticText = "return to your hand the creature card in your graveyard with the greatest power. If two or more cards are tied for greatest power, you choose one of them";
    }

    public DesecratorHagEffect(final DesecratorHagEffect effect) {
        super(effect);
    }

    @Override
    public DesecratorHagEffect copy() {
        return new DesecratorHagEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            for (Card card : you.getGraveyard().getCards(game)) {
                if (card.isCreature(game)) {
                    if (card.getPower().getValue() > creatureGreatestPower) {
                        creatureGreatestPower = card.getPower().getValue();
                        cards.clear();
                        cards.add(card);
                    } else {
                        if (card.getPower().getValue() == creatureGreatestPower) {
                            cards.add(card);
                        }
                    }
                }
            }
            if (cards.isEmpty()) {
                return true;
            }
            if (cards.size() > 1
                    && you.choose(Outcome.DrawCard, cards, target, game)) {
                if (target != null) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        return you.moveCards(card, Zone.HAND, source, game);
                    }
                }
            } else {
                return you.moveCards(cards, Zone.HAND, source, game);
            }
        }
        return false;
    }
}
