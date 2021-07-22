package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FabledPassage extends CardImpl {

    public FabledPassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}, Sacrifice Fabled Passage: Search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library. Then if you control four or more lands, untap that land.
        Ability ability = new SimpleActivatedAbility(new FabledPassageEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private FabledPassage(final FabledPassage card) {
        super(card);
    }

    @Override
    public FabledPassage copy() {
        return new FabledPassage(this);
    }
}

class FabledPassageEffect extends OneShotEffect {

    FabledPassageEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for a basic land card, put it onto the battlefield tapped, " +
                "then shuffle. Then if you control four or more lands, untap that land.";
    }

    private FabledPassageEffect(final FabledPassageEffect effect) {
        super(effect);
    }

    @Override
    public FabledPassageEffect copy() {
        return new FabledPassageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND_A);
        if (!player.searchLibrary(target, source, game)) {
            return false;
        }
        player.shuffleLibrary(source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        if (!player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null)) {
            return false;
        }
        if (game.getBattlefield().countAll(StaticFilters.FILTER_LAND, source.getControllerId(), game) < 4) {
            return true;
        }
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        return permanent.untap(game);
    }
}
