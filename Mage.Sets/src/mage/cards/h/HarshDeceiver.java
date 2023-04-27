
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX
 */
public final class HarshDeceiver extends CardImpl {

    public HarshDeceiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {1}: Look at the top card of your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new LookLibraryControllerEffect(), new GenericManaCost(1)));

        // {2}: Reveal the top card of your library. If it's a land card, untap {this} and it gets +1/+1 until end of turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new HarshDeceiverEffect(), new ManaCostsImpl<>("{2}")));
    }

    private HarshDeceiver(final HarshDeceiver card) {
        super(card);
    }

    @Override
    public HarshDeceiver copy() {
        return new HarshDeceiver(this);
    }
}

class HarshDeceiverEffect extends OneShotEffect {

    public HarshDeceiverEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Reveal the top card of your library. If it's a land card, untap {this} and it gets +1/+1 until end of turn";
    }

    public HarshDeceiverEffect(final HarshDeceiverEffect effect) {
        super(effect);
    }

    @Override
    public HarshDeceiverEffect copy() {
        return new HarshDeceiverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl(card);
                controller.revealCards(sourceObject.getIdName(), cards, game);
                if (card.isLand(game)) {
                    new UntapSourceEffect().apply(game, source);
                    game.addEffect(new BoostSourceEffect(1, 1, Duration.EndOfTurn), source);
                }
            }
            return true;
        }
        return false;
    }
}
