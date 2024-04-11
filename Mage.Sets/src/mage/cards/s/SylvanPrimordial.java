
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetadjustment.EachOpponentPermanentTargetsAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SylvanPrimordial extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }
    public SylvanPrimordial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(8);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Sylvan Primordial enters the battlefield, for each opponent, destroy target noncreature permanent that player controls. For each permanent destroyed this way, search your library for a Forest card and put that card onto the battlefield tapped. Then shuffle your library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SylvanPrimordialEffect(), false);
        ability.setTargetAdjuster(new EachOpponentPermanentTargetsAdjuster());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SylvanPrimordial(final SylvanPrimordial card) {
        super(card);
    }

    @Override
    public SylvanPrimordial copy() {
        return new SylvanPrimordial(this);
    }
}

class SylvanPrimordialEffect extends OneShotEffect {

    private static final FilterLandCard filterForest = new FilterLandCard("Forest");

    static {
        filterForest.add(SubType.FOREST.getPredicate());
    }

    public SylvanPrimordialEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "for each opponent, destroy target noncreature permanent that player controls. For each permanent destroyed this way, search your library for a Forest card and put that card onto the battlefield tapped. Then shuffle";
    }

    private SylvanPrimordialEffect(final SylvanPrimordialEffect effect) {
        super(effect);
    }

    @Override
    public SylvanPrimordialEffect copy() {
        return new SylvanPrimordialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        int destroyedCreatures = 0;
        for (Target target : source.getTargets()) {
            if (target instanceof TargetPermanent) {
                Permanent targetPermanent = game.getPermanent(target.getFirstTarget());
                if (targetPermanent != null) {
                    if (targetPermanent.destroy(source, game, false)) {
                        destroyedCreatures++;
                    }
                }
            }
        }
        if (destroyedCreatures > 0) {
            new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(destroyedCreatures, filterForest), true).apply(game, source);
        }
        return result;
    }
}
