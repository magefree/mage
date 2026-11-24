package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class BileBlight extends CardImpl {

    public BileBlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{B}");


        // Target creature and all other creatures with the same name as that creature get -3/-3 until end of turn.
        this.getSpellAbility().addEffect(new BileBlightEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BileBlight(final BileBlight card) {
        super(card);
    }

    @Override
    public BileBlight copy() {
        return new BileBlight(this);
    }
}

class BileBlightEffect extends OneShotEffect {

    BileBlightEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Target creature and all other creatures with the same name as that creature get -3/-3 until end of turn";
    }

    private BileBlightEffect(final BileBlightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (target == null) {
            return false;
        }
        FilterPermanent filter = new FilterCreaturePermanent();
        // a face down creature has no name, and nothing shares a name with it, so the target has to
        // be matched by identity rather than through the name it may not have
        filter.add(Predicates.or(
                new MageObjectReferencePredicate(target, game),
                new NamePredicate(target.getName())));
        game.addEffect(new BoostAllEffect(-3, -3, Duration.EndOfTurn, filter), source);
        return true;
    }

    @Override
    public BileBlightEffect copy() {
        return new BileBlightEffect(this);
    }
}
