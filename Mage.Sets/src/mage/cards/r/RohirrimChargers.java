package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExertCreatureControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ExertAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class RohirrimChargers extends CardImpl {

    public RohirrimChargers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // You may exert Rohirrim Chargers as it attacks.
        this.addAbility(new ExertAbility(null, false));
        // Whenever you exert a creature, reveal cards from the top of your library until you reveal an Equipment card. Put that card onto the battlefield attached to that creature, then put the rest on the bottom of your library in a random order.
        this.addAbility(new ExertCreatureControllerTriggeredAbility(new RohirrimChargersEffect(), true));
    }

    private RohirrimChargers(final RohirrimChargers card) {
        super(card);
    }

    @Override
    public RohirrimChargers copy() {
        return new RohirrimChargers(this);
    }
}

class RohirrimChargersEffect extends OneShotEffect {

    RohirrimChargersEffect() {
        super(Outcome.Benefit);
        staticText = "reveal cards from the top of your library until you reveal an Equipment card. "+
                "Put that card onto the battlefield attached to that creature, then put the rest on the bottom of your library in a random order.";
    }

    private RohirrimChargersEffect(final RohirrimChargersEffect effect) {
        super(effect);
    }

    @Override
    public RohirrimChargersEffect copy() {
        return new RohirrimChargersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Library library = player.getLibrary();
        if (!library.hasCards()) {
            return true;
        }
        Cards cards = new CardsImpl();
        Card toBattlefield = null;
        for (Card card : library.getCards(game)) {
            cards.add(card);
            if (card.hasSubtype(SubType.EQUIPMENT, game)) {
                toBattlefield = card;
                break;
            }
        }
        if (toBattlefield != null) {
            Permanent attachTarget = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (attachTarget != null) {
                game.getState().setValue("attachTo:" + toBattlefield.getId(), attachTarget);
            }
            player.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
            if (attachTarget != null) {
                attachTarget.addAttachment(toBattlefield.getId(), source, game);
            }
        }
        player.revealCards(source, cards, game);
        cards.remove(toBattlefield);
        if (!cards.isEmpty()) {
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        return true;
    }
}