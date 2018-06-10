

package mage.cards.m;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandFromBattlefieldSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */

public final class MazesEnd extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("Gate card");
    static {
        filterCard.add(new SubtypePredicate(SubType.GATE));
    }

    public MazesEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");


        // Maze's End enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add 1.
        this.addAbility(new ColorlessManaAbility());

        // 3, {T}, Return Maze's End  to its owner's hand: Search your library for a Gate card, put it onto the battlefield, then shuffle your library. If you control ten or more Gates with different names, you win the game.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filterCard)), new GenericManaCost(3));
        ability.addEffect(new MazesEndEffect());
        ability.addCost(new TapSourceCost());
        ability.addCost(new ReturnToHandFromBattlefieldSourceCost());
        this.addAbility(ability);

    }

    public MazesEnd(final MazesEnd card) {
        super(card);
    }

    @Override
    public MazesEnd copy() {
        return new MazesEnd(this);
    }
}

class MazesEndEffect extends OneShotEffect {

    public MazesEndEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "If you control ten or more Gates with different names, you win the game";
    }

    public MazesEndEffect(final MazesEndEffect effect) {
        super(effect);
    }

    @Override
    public MazesEndEffect copy() {
        return new MazesEndEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<String> names = new ArrayList<>();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.hasSubtype(SubType.GATE, game)) {
                if (!names.contains(permanent.getName())) {
                    names.add(permanent.getName());
                }
            }
        }
        if (names.size() >= 10) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.won(game);
            }
        }
        return false;
    }

}
