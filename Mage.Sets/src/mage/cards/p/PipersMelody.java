package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class PipersMelody extends CardImpl {

    public PipersMelody(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Shuffle any number of target creature cards from your graveyard into your library.
        this.getSpellAbility().addEffect(new PipersMelodyShuffleEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
    }

    private PipersMelody(final PipersMelody card) {
        super(card);
    }

    @Override
    public PipersMelody copy() {
        return new PipersMelody(this);
    }
}

class PipersMelodyShuffleEffect extends OneShotEffect {

    PipersMelodyShuffleEffect() {
        super(Outcome.Neutral);
        this.staticText = "Shuffle any number of target creature cards from your graveyard into your library";
    }

    PipersMelodyShuffleEffect(final PipersMelodyShuffleEffect effect) {
        super(effect);
    }

    @Override
    public PipersMelodyShuffleEffect copy() {
        return new PipersMelodyShuffleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.moveCards(new CardsImpl(this.getTargetPointer().getTargets(game, source)), Zone.LIBRARY, source, game);
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
