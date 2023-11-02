package mage.cards.b;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public final class BoldwyrHeavyweights extends CardImpl {

    public BoldwyrHeavyweights(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.GIANT, SubType.WARRIOR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Boldwyr Heavyweights enters the battlefield, each opponent may search their library for a creature card and put it onto the battlefield. Then each player who searched their library this way shuffles it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoldwyrHeavyweightsEffect()));
    }

    private BoldwyrHeavyweights(final BoldwyrHeavyweights card) {
        super(card);
    }

    @Override
    public BoldwyrHeavyweights copy() {
        return new BoldwyrHeavyweights(this);
    }
}

class BoldwyrHeavyweightsEffect extends OneShotEffect {

    BoldwyrHeavyweightsEffect() {
        super(Outcome.Detriment);
        this.staticText = "each opponent may search their library for a creature card and put it onto the battlefield. Then each player who searched their library this way shuffles";
    }

    private BoldwyrHeavyweightsEffect(final BoldwyrHeavyweightsEffect effect) {
        super(effect);
    }

    @Override
    public BoldwyrHeavyweightsEffect copy() {
        return new BoldwyrHeavyweightsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Player> playersThatSearched = new HashSet<>(1);
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && opponent.chooseUse(Outcome.PutCreatureInPlay, "Search your library for a creature card and put it onto the battlefield?", source, game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_CREATURE);
                if (opponent.searchLibrary(target, source, game)) {
                    Card targetCard = opponent.getLibrary().getCard(target.getFirstTarget(), game);
                    if (targetCard != null) {
                        opponent.moveCards(targetCard, Zone.BATTLEFIELD, source, game);
                        playersThatSearched.add(opponent);
                    }
                }
            }
        }
        for (Player opponent : playersThatSearched) {
            opponent.shuffleLibrary(source, game);
        }
        return true;
    }
}
