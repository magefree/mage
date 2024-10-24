package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandFromBattlefieldSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */

public final class MazesEnd extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("Gate card");

    static {
        filterCard.add(SubType.GATE.getPredicate());
    }

    public MazesEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Maze's End enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add 1.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {T}, Return Maze's End to its owner's hand: Search your library for a Gate card, put it onto the battlefield, then shuffle your library. If you control ten or more Gates with different names, you win the game.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filterCard)), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new ReturnToHandFromBattlefieldSourceCost());
        ability.addEffect(new MazesEndEffect());
        this.addAbility(ability.addHint(GatesWithDifferentNamesYouControlCount.getHint()));
    }

    private MazesEnd(final MazesEnd card) {
        super(card);
    }

    @Override
    public MazesEnd copy() {
        return new MazesEnd(this);
    }
}

enum GatesWithDifferentNamesYouControlCount implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GATE);
    private static final Hint hint = new ValueHint("Gates with different names you control", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CardUtil.differentlyNamedAmongCollection(
                game.getBattlefield().getActivePermanents(
                        filter, sourceAbility.getControllerId(), sourceAbility, game
                ), game
        );
    }

    @Override
    public GatesWithDifferentNamesYouControlCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "Gates with different names you control";
    }
}


class MazesEndEffect extends OneShotEffect {

    MazesEndEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "If you control ten or more Gates with different names, you win the game";
    }

    private MazesEndEffect(final MazesEndEffect effect) {
        super(effect);
    }

    @Override
    public MazesEndEffect copy() {
        return new MazesEndEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (GatesWithDifferentNamesYouControlCount.instance.calculate(game, source, this) < 10) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.won(game);
        }
        return true;
    }
}
