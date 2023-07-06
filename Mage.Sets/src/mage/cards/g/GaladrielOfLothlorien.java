package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ScryTriggeredAbility;
import mage.abilities.common.TheRingTemptsYouChooseAnotherTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GaladrielOfLothlorien extends CardImpl {

    public GaladrielOfLothlorien(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever the Ring tempts you, if you chose a creature other than Galadriel of Lothlorien as your Ring-bearer, scry 3.
        this.addAbility(new TheRingTemptsYouChooseAnotherTriggeredAbility(
                new ScryEffect(3, false)
        ));

        // Whenever you scry, you may reveal the top card of your library. If a land card is revealed this way, put it onto the battlefield tapped.
        this.addAbility(new ScryTriggeredAbility(new GaladrielOfLothlorienEffect(), true));
    }

    private GaladrielOfLothlorien(final GaladrielOfLothlorien card) {
        super(card);
    }

    @Override
    public GaladrielOfLothlorien copy() {
        return new GaladrielOfLothlorien(this);
    }
}

class GaladrielOfLothlorienEffect extends OneShotEffect {

    GaladrielOfLothlorienEffect() {
        super(Outcome.Benefit);
        staticText = "you may reveal the top card of your library. If a land card is revealed this way, put it onto the battlefield tapped";
    }

    private GaladrielOfLothlorienEffect(final GaladrielOfLothlorienEffect effect) {
        super(effect);
    }

    @Override
    public GaladrielOfLothlorienEffect copy() {
        return new GaladrielOfLothlorienEffect(this);
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
        player.revealCards(source, new CardsImpl(card), game);
        return card.isLand(game) && player.moveCards(
                card, Zone.BATTLEFIELD, source, game, true, false, false, null
        );
    }
}
