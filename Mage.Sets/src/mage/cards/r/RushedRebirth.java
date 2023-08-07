package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RushedRebirth extends CardImpl {

    public RushedRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{G}");

        // Choose target creature. When that creature dies this turn, search your library for a
        // creature card with lesser mana value, put it onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new WhenTargetDiesDelayedTriggeredAbility(new RushedRebirthEffect(), SetTargetPointer.PERMANENT),
                true,
                "Choose target creature. "
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RushedRebirth(final RushedRebirth card) {
        super(card);
    }

    @Override
    public RushedRebirth copy() {
        return new RushedRebirth(this);
    }
}

class RushedRebirthEffect extends OneShotEffect {

    RushedRebirthEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "search your library for a creature card with lesser mana value, " +
                "put it onto the battlefield tapped, then shuffle";
    }

    private RushedRebirthEffect(final RushedRebirthEffect effect) {
        super(effect);
    }

    @Override
    public RushedRebirthEffect copy() {
        return new RushedRebirthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent == null) {
            return false;
        }
        FilterCreatureCard filter = new FilterCreatureCard("creature card with lesser mana value than " + permanent.getIdName());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, permanent.getManaValue()));
        return new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true).apply(game, source);
    }
}
