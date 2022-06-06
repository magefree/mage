
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class CallOfTheWild extends CardImpl {

    public CallOfTheWild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");

        // {2}{G}{G}: Reveal the top card of your library. If it's a creature card, put it onto the battlefield. Otherwise, put it into your graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CallOfTheWildEffect(), new ManaCostsImpl<>("{2}{G}{G}")));
    }

    private CallOfTheWild(final CallOfTheWild card) {
        super(card);
    }

    @Override
    public CallOfTheWild copy() {
        return new CallOfTheWild(this);
    }
}

class CallOfTheWildEffect extends OneShotEffect {

    public CallOfTheWildEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Reveal the top card of your library. If it's a creature card, put it onto the battlefield. Otherwise, put it into your graveyard";
    }

    public CallOfTheWildEffect(final CallOfTheWildEffect effect) {
        super(effect);
    }

    @Override
    public CallOfTheWildEffect copy() {
        return new CallOfTheWildEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        if (controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl(card);
                controller.revealCards(sourceObject.getIdName(), cards, game);
                if (card.isCreature(game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                } else {
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                }
            }
        }
        return true;
    }
}
