
package mage.cards.f;

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
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
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
 * @author LevelX2
 */
public final class FeralDeceiver extends CardImpl {

    public FeralDeceiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {1}: Look at the top card of your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new LookLibraryControllerEffect(), new GenericManaCost(1)));

        // {2}: Reveal the top card of your library. If it's a land card, {this} gets +2/+2 and gains trample until end of turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new FeralDeceiverEffect(), new ManaCostsImpl<>("{2}")));
    }

    private FeralDeceiver(final FeralDeceiver card) {
        super(card);
    }

    @Override
    public FeralDeceiver copy() {
        return new FeralDeceiver(this);
    }
}

class FeralDeceiverEffect extends OneShotEffect {

    public FeralDeceiverEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Reveal the top card of your library. If it's a land card, {this} gets +2/+2 and gains trample until end of turn";
    }

    public FeralDeceiverEffect(final FeralDeceiverEffect effect) {
        super(effect);
    }

    @Override
    public FeralDeceiverEffect copy() {
        return new FeralDeceiverEffect(this);
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
                    game.addEffect(new BoostSourceEffect(2, 2, Duration.EndOfTurn), source);
                    game.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), source);
                }
            }
            return true;
        }
        return false;
    }
}
