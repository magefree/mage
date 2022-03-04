package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DuskmantleSeer extends CardImpl {

    public DuskmantleSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, each player reveals the top card of their library, loses life equal to that card's converted mana cost, then puts it into their hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DuskmantleSeerEffect(), TargetController.YOU, false
        ));
    }

    private DuskmantleSeer(final DuskmantleSeer card) {
        super(card);
    }

    @Override
    public DuskmantleSeer copy() {
        return new DuskmantleSeer(this);
    }
}

class DuskmantleSeerEffect extends OneShotEffect {

    public DuskmantleSeerEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player reveals the top card of their library, " +
                "loses life equal to that card's mana value, then puts it into their hand";
    }

    public DuskmantleSeerEffect(final DuskmantleSeerEffect effect) {
        super(effect);
    }

    @Override
    public DuskmantleSeerEffect copy() {
        return new DuskmantleSeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            Card card = player.getLibrary().getFromTop(game);
            if (card == null) {
                continue;
            }
            player.revealCards(source, new CardsImpl(card), game);
            player.loseLife(card.getManaValue(), game, source, false);
            player.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }
}
