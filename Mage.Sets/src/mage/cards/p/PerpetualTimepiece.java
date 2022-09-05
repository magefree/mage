
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public final class PerpetualTimepiece extends CardImpl {

    public PerpetualTimepiece(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {T}: Put the top two cards of your library into your graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new MillCardsControllerEffect(2), new TapSourceCost()));

        // {2}, Exile Perpetual Timepiece: Shuffle any number of target cards from your graveyard into your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShuffleIntoLibraryTargetEffect(), new GenericManaCost(2));
        ability.addCost(new ExileSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, new FilterCard("cards to shuffle into your library")));
        this.addAbility(ability);
    }

    private PerpetualTimepiece(final PerpetualTimepiece card) {
        super(card);
    }

    @Override
    public PerpetualTimepiece copy() {
        return new PerpetualTimepiece(this);
    }
}

class PerpetualTimepieceShuffleEffect extends OneShotEffect {

    PerpetualTimepieceShuffleEffect() {
        super(Outcome.Neutral);
        this.staticText = "Shuffle any number of target cards from your graveyard into your library";
    }

    PerpetualTimepieceShuffleEffect(final PerpetualTimepieceShuffleEffect effect) {
        super(effect);
    }

    @Override
    public PerpetualTimepieceShuffleEffect copy() {
        return new PerpetualTimepieceShuffleEffect(this);
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
