
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author djbrez
 */
public final class WuSpy extends CardImpl {

    public WuSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Wu Spy enters the battlefield, look at the top two cards of target player's library. Put one of them into their graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new WuSpyEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    private WuSpy(final WuSpy card) {
        super(card);
    }

    @Override
    public WuSpy copy() {
        return new WuSpy(this);
    }
}

class WuSpyEffect extends OneShotEffect {

    WuSpyEffect() {
        super(Outcome.Exile);
        this.staticText = "look at the top two cards of target player's library. Put one of them into their graveyard";
    }

    WuSpyEffect(final WuSpyEffect effect) {
        super(effect);
    }

    @Override
    public WuSpyEffect copy() {
        return new WuSpyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller != null && opponent != null) {
            Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, 2));
            if (!cards.isEmpty()) {
                TargetCard target = new TargetCardInLibrary(new FilterCard("card to put into graveyard"));
                controller.choose(Outcome.Benefit, cards, target, source, game);
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
