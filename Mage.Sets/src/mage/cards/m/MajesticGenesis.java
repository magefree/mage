package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.GreatestCommanderManaValue;
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
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MajesticGenesis extends CardImpl {

    public MajesticGenesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{G}{G}");

        // Reveal the top X cards of your library, where X is the highest mana value of a commander you own on the battlefield or in the command zone. You may put any number of a permanent cards from among them onto the battlefield. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new MajesticGenesisEffect());
        this.getSpellAbility().addHint(GreatestCommanderManaValue.getHint());
    }

    private MajesticGenesis(final MajesticGenesis card) {
        super(card);
    }

    @Override
    public MajesticGenesis copy() {
        return new MajesticGenesis(this);
    }
}

class MajesticGenesisEffect extends OneShotEffect {

    MajesticGenesisEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top X cards of your library, where X is the greatest mana value of a commander " +
                "you own on the battlefield or in the command zone. You may put any number of a permanent cards " +
                "from among them onto the battlefield. Put the rest on the bottom of your library in a random order";
    }

    private MajesticGenesisEffect(final MajesticGenesisEffect effect) {
        super(effect);
    }

    @Override
    public MajesticGenesisEffect copy() {
        return new MajesticGenesisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int xValue = GreatestCommanderManaValue.instance.calculate(game, source, this);
        if (player == null || xValue < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, xValue));
        player.revealCards(source, cards, game);
        TargetCard target = new TargetCardInLibrary(
                0, Integer.MAX_VALUE,
                StaticFilters.FILTER_CARD_PERMANENT
        );
        player.choose(outcome, cards, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
