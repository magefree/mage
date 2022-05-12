package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class ToothAndNail extends CardImpl {

    public ToothAndNail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}{G}");

        // Choose one -
        // Search your library for up to two creature cards, reveal them, put them into your hand, then shuffle your library;
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_CREATURES), true));
        // or put up to two creature cards from your hand onto the battlefield.
        Mode mode = new Mode(new ToothAndNailPutCreatureOnBattlefieldEffect());
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {2}
        this.addAbility(new EntwineAbility("{2}"));
    }

    private ToothAndNail(final ToothAndNail card) {
        super(card);
    }

    @Override
    public ToothAndNail copy() {
        return new ToothAndNail(this);
    }
}

class ToothAndNailPutCreatureOnBattlefieldEffect extends OneShotEffect {

    public ToothAndNailPutCreatureOnBattlefieldEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put up to two creature cards from your hand onto the battlefield";
    }

    public ToothAndNailPutCreatureOnBattlefieldEffect(final ToothAndNailPutCreatureOnBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public ToothAndNailPutCreatureOnBattlefieldEffect copy() {
        return new ToothAndNailPutCreatureOnBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(0, 2, new FilterCreatureCard("creature cards"));
        if (controller.choose(Outcome.PutCreatureInPlay, target, source, game)) {
            return controller.moveCards(new CardsImpl(target.getTargets()).getCards(game),
                    Zone.BATTLEFIELD, source, game, false, false, false, null);
        }
        return false;
    }
}
