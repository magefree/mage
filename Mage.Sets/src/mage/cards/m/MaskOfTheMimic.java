package mage.cards.m;

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
import mage.filter.predicate.mageobject.SharesNamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaskOfTheMimic extends CardImpl {

    public MaskOfTheMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // As an additional cost to cast Mask of the Mimic, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));

        // Search your library for a card with the same name as target nontoken creature and put that card onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new MaskOfTheMimicEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_NON_TOKEN));
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
        this.staticText = "search your library for a card with the same name as target nontoken creature,"
                + " put that card onto the battlefield, then shuffle.";
    }

    private MaskOfTheMimicEffect(final MaskOfTheMimicEffect effect) {
        super(effect);
    }

    @Override
    public MaskOfTheMimicEffect copy() {
        return new MaskOfTheMimicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creature == null) {
            return false;
        }
        FilterCard filter = new FilterCard("a card with the same name");
        filter.add(new SharesNamePredicate(creature));
        return new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)).apply(game, source);
    }
}
