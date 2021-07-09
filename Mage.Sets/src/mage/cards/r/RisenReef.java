package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RisenReef extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELEMENTAL, "Elemental");

    public RisenReef(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Risen Reef or another Elemental enters the battlefield under your control, look at the top card of your library. If it's a land card, you may put it onto the battlefield tapped. If you don't put the card onto the battlefield, put it into your hand.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new RisenReefEffect(), filter, false, true
        ));
    }

    private RisenReef(final RisenReef card) {
        super(card);
    }

    @Override
    public RisenReef copy() {
        return new RisenReef(this);
    }
}

class RisenReefEffect extends OneShotEffect {

    RisenReefEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. " +
                "If it's a land card, you may put it onto the battlefield tapped. " +
                "If you don't put the card onto the battlefield, put it into your hand";
    }

    private RisenReefEffect(final RisenReefEffect effect) {
        super(effect);
    }

    @Override
    public RisenReefEffect copy() {
        return new RisenReefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards("", card, game);
        if (card.isLand(game) && player.chooseUse(
                outcome, "Put " + card.getName() + " onto the battlefield tapped?",
                "(otherwise put it into your hand", "To battlefield",
                "To hand", source, game)) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
        } else {
            player.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }
}
