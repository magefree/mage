
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class MatterReshaper extends CardImpl {

    public MatterReshaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{C}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Matter Reshaper dies, reveal the top card of your library. You may put that card onto the battlefield
        // if it's a permanent card with converted mana cost 3 or less. Otherwise, put that card into your hand.
        this.addAbility(new DiesSourceTriggeredAbility(new MatterReshaperEffect(), false));
    }

    private MatterReshaper(final MatterReshaper card) {
        super(card);
    }

    @Override
    public MatterReshaper copy() {
        return new MatterReshaper(this);
    }
}

class MatterReshaperEffect extends OneShotEffect {

    public MatterReshaperEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top card of your library. You may put that card onto the battlefield if it's a permanent card"
                + " with mana value 3 or less. Otherwise, put that card into your hand";
    }

    private MatterReshaperEffect(final MatterReshaperEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            controller.revealCards(source, new CardsImpl(card), game);
            FilterPermanentCard filter = new FilterPermanentCard("permanent card with mana value 3 or less");
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
            if (filter.match(card, game)) {
                if (controller.chooseUse(Outcome.PutCardInPlay, "Put " + card.getName() + " onto the battlefield (otherwise put in hand)?", source, game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    return true;
                }
            }
            controller.moveCards(card, Zone.HAND, source, game);
            return true;
        }
        return false;
    }

    @Override
    public MatterReshaperEffect copy() {
        return new MatterReshaperEffect(this);
    }
}
