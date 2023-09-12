
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class PutridCyclops extends CardImpl {

    public PutridCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CYCLOPS);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Putrid Cyclops enters the battlefield, scry 1, then reveal the top card of your library. Putrid Cyclops gets -X/-X until end of turn, where X is that card's converted mana cost.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PutridCyclopEffect()));
    }

    private PutridCyclops(final PutridCyclops card) {
        super(card);
    }

    @Override
    public PutridCyclops copy() {
        return new PutridCyclops(this);
    }
}

class PutridCyclopEffect extends OneShotEffect {

    public PutridCyclopEffect() {
        super(Outcome.Detriment);
        this.staticText = "scry 1, then reveal the top card of your library. {this} gets -X/-X until end of turn, where X is that card's mana value."
                + " <i>(To scry 1, look at the top card of your library, then you may put that card on the bottom of your library.)</i>";
    }

    private PutridCyclopEffect(final PutridCyclopEffect effect) {
        super(effect);
    }

    @Override
    public PutridCyclopEffect copy() {
        return new PutridCyclopEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            new ScryEffect(1).apply(game, source);
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
                int unboost = card.getManaValue() * -1;
                ContinuousEffect effect = new BoostSourceEffect(unboost, unboost, Duration.EndOfTurn);
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
