package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class DryadGreenseeker extends CardImpl {

    public DryadGreenseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Look at the top card of your library. If it's a land card, you may reveal it and put it into your hand.
        this.addAbility(new SimpleActivatedAbility(
                new DryadGreenseekerEffect(),
                new TapSourceCost()
        ));
    }

    private DryadGreenseeker(final DryadGreenseeker card) {
        super(card);
    }

    @Override
    public DryadGreenseeker copy() {
        return new DryadGreenseeker(this);
    }
}

class DryadGreenseekerEffect extends OneShotEffect {

    public DryadGreenseekerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top card of your library. "
                + "If it's a land card, you may reveal it and put it into your hand.";
    }

    private DryadGreenseekerEffect(final DryadGreenseekerEffect effect) {
        super(effect);
    }

    @Override
    public DryadGreenseekerEffect copy() {
        return new DryadGreenseekerEffect(this);
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
        player.lookAtCards(null, card, game);
        if (!card.isLand(game)) {
            return true;
        }
        if (!player.chooseUse(outcome, "Reveal " + card.getName() + " and put it into your hand?", source, game)) {
            return true;
        }
        player.revealCards(source, new CardsImpl(card), game);
        return player.moveCards(card, Zone.HAND, source, game);
    }
}
