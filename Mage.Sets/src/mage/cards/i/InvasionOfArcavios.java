package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.WishEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfArcavios extends CardImpl {

    public InvasionOfArcavios(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{3}{U}{U}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(7);
        this.secondSideCardClazz = mage.cards.i.InvocationOfTheFounders.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Arcavios enters the battlefield, search your library, graveyard, and/or outside the game for an instant or sorcery card you own, reveal it, and put it into your hand. If you search your library this way, shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvasionOfArcaviosEffect()));
    }

    private InvasionOfArcavios(final InvasionOfArcavios card) {
        super(card);
    }

    @Override
    public InvasionOfArcavios copy() {
        return new InvasionOfArcavios(this);
    }
}

class InvasionOfArcaviosEffect extends OneShotEffect {

    InvasionOfArcaviosEffect() {
        super(Outcome.Benefit);
        staticText = "search your library, graveyard, and/or outside the game for an instant or sorcery card you own, " +
                "reveal it, and put it into your hand. If you search your library this way, shuffle";
    }

    private InvasionOfArcaviosEffect(final InvasionOfArcaviosEffect effect) {
        super(effect);
    }

    @Override
    public InvasionOfArcaviosEffect copy() {
        return new InvasionOfArcaviosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.chooseUse(outcome, "Look outside the game?", source, game)
                && new WishEffect(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY).apply(game, source)) {
            return true;
        }
        return new SearchLibraryGraveyardPutInHandEffect(
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, false
        ).apply(game, source);
    }
}
