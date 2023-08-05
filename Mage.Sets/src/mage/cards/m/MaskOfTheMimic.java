
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class MaskOfTheMimic extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public MaskOfTheMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // As an additional cost to cast Mask of the Mimic, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));

        // Search your library for a card with the same name as target nontoken creature and put that card onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new MaskOfTheMimicEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private MaskOfTheMimic(final MaskOfTheMimic card) {
        super(card);
    }

    @Override
    public MaskOfTheMimic copy() {
        return new MaskOfTheMimic(this);
    }
}

class MaskOfTheMimicEffect extends OneShotEffect {

    MaskOfTheMimicEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for a card with the same name as target nontoken creature,"
                + " put that card onto the battlefield, then shuffle.";
    }

    MaskOfTheMimicEffect(final MaskOfTheMimicEffect effect) {
        super(effect);
    }

    @Override
    public MaskOfTheMimicEffect copy() {
        return new MaskOfTheMimicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature == null) {
            return false;
        }
        FilterCard filter = new FilterCard("a card named " + creature.getName());
        filter.add(new NamePredicate(creature.getName()));
        return new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)).apply(game, source);
    }
}
