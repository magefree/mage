
package mage.cards.o;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class OldGrowthDryads extends CardImpl {

    public OldGrowthDryads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Old-Growth Dryads enters the battlefield, each opponent may search their library for a basic land card, put it onto the battlefield tapped, then shuffle their library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OldGrowthDryadsEffect()));
    }

    private OldGrowthDryads(final OldGrowthDryads card) {
        super(card);
    }

    @Override
    public OldGrowthDryads copy() {
        return new OldGrowthDryads(this);
    }
}

class OldGrowthDryadsEffect extends OneShotEffect {

    OldGrowthDryadsEffect() {
        super(Outcome.Detriment);
        this.staticText = "each opponent may search their library for a basic land card, put it onto the battlefield tapped, then shuffle";
    }

    private OldGrowthDryadsEffect(final OldGrowthDryadsEffect effect) {
        super(effect);
    }

    @Override
    public OldGrowthDryadsEffect copy() {
        return new OldGrowthDryadsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Player> playersThatSearched = new HashSet<>(1);
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && opponent.chooseUse(Outcome.PutLandInPlay, "Search your library for a basic land card and put it onto the battlefield tapped?", source, game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(new FilterBasicLandCard());
                if (opponent.searchLibrary(target, source, game)) {
                    Card targetCard = opponent.getLibrary().getCard(target.getFirstTarget(), game);
                    if (targetCard != null) {
                        opponent.moveCards(targetCard, Zone.BATTLEFIELD, source, game, true, false, false, null);
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
