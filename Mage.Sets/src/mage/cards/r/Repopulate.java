package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class Repopulate extends CardImpl {

    public Repopulate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Shuffle all creature cards from target player's graveyard into that player's library.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new RepopulateEffect());
        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private Repopulate(final Repopulate card) {
        super(card);
    }

    @Override
    public Repopulate copy() {
        return new Repopulate(this);
    }
}

class RepopulateEffect extends OneShotEffect {

    RepopulateEffect() {
        super(Outcome.Benefit);
        staticText = "Shuffle all creature cards from target player's graveyard into that player's library";
    }

    RepopulateEffect(final RepopulateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            return player.shuffleCardsToLibrary(
                    new CardsImpl(player.getGraveyard()
                            .getCards(StaticFilters.FILTER_CARD_CREATURE, game)), game, source);
        }
        return false;
    }

    @Override
    public RepopulateEffect copy() {
        return new RepopulateEffect(this);
    }
}
