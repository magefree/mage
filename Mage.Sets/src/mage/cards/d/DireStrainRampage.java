package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author weirddan455
 */
public final class DireStrainRampage extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, enchantment, or land");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public DireStrainRampage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{G}");

        // Destroy target artifact, enchantment, or land. If a land was destroyed this way,
        // its controller may search their library for up to two basic land cards, put them onto the battlefield tapped, then shuffle.
        // Otherwise, its controller may search their library for a basic land card, put it onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new DireStrainRampageEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Flashback {3}{R}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{R}{G}")));
    }

    private DireStrainRampage(final DireStrainRampage card) {
        super(card);
    }

    @Override
    public DireStrainRampage copy() {
        return new DireStrainRampage(this);
    }
}

class DireStrainRampageEffect extends OneShotEffect {

    public DireStrainRampageEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target artifact, enchantment, or land. If a land was destroyed this way, " +
                "its controller may search their library for up to two basic land cards, put them onto the battlefield tapped, then shuffle. " +
                "Otherwise, its controller may search their library for a basic land card, put it onto the battlefield tapped, then shuffle";
    }

    private DireStrainRampageEffect(final DireStrainRampageEffect effect) {
        super(effect);
    }

    @Override
    public DireStrainRampageEffect copy() {
        return new DireStrainRampageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        boolean landTargeted = permanent.isLand(game);
        boolean destroyed = permanent.destroy(source, game, false);
        game.getState().processAction(game);
        TargetCardInLibrary target = landTargeted && destroyed ?
                new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS) :
                new TargetCardInLibrary(1, 1, StaticFilters.FILTER_CARD_BASIC_LAND);
        return new SearchLibraryPutInPlayTargetControllerEffect(target, true, Outcome.PutLandInPlay, "its controller").apply(game, source);
    }
}
