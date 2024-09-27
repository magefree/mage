package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardWithSameNameAsPermanents;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class ClarionUltimatum extends CardImpl {

    public ClarionUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{G}{W}{W}{W}{U}{U}");

        // Choose five permanents you control. For each of those permanents, you may search your library for a card with the same name as that permanent. Put those cards onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new ClarionUltimatumEffect());
    }

    private ClarionUltimatum(final ClarionUltimatum card) {
        super(card);
    }

    @Override
    public ClarionUltimatum copy() {
        return new ClarionUltimatum(this);
    }
}

class ClarionUltimatumEffect extends OneShotEffect {

    ClarionUltimatumEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose five permanents you control. For each of those permanents, " +
                "you may search your library for a card with the same name as that permanent. " +
                "Put those cards onto the battlefield tapped, then shuffle";
    }

    private ClarionUltimatumEffect(final ClarionUltimatumEffect effect) {
        super(effect);
    }

    @Override
    public ClarionUltimatumEffect copy() {
        return new ClarionUltimatumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int permCount = game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT,
                source.getControllerId(), source, game
        );
        TargetPermanent targetPermanent = new TargetControlledPermanent(Math.max(permCount, 5));
        targetPermanent.withNotTarget(true);
        player.choose(outcome, targetPermanent, source, game);
        TargetCardInLibrary target = new TargetCardWithSameNameAsPermanents(targetPermanent.getTargets());
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        player.moveCards(
                cards.getCards(game), Zone.BATTLEFIELD, source, game,
                true, false, false, null
        );
        player.shuffleLibrary(source, game);
        return true;
    }
}
