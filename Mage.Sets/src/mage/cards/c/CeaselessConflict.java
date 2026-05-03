package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Spirit32Token;

/**
 *
 * @author muz
 */
public final class CeaselessConflict extends CardImpl {

    public CeaselessConflict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Destroy all creatures. Then create a 3/2 red and white Spirit creature token for each nontoken creature you controlled that was destroyed this way.
        this.getSpellAbility().addEffect(new CeaselessConflictEffect());
    }

    private CeaselessConflict(final CeaselessConflict card) {
        super(card);
    }

    @Override
    public CeaselessConflict copy() {
        return new CeaselessConflict(this);
    }
}

class CeaselessConflictEffect extends OneShotEffect {

    public CeaselessConflictEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all creatures. Then create a 3/2 red and white Spirit creature token for each nontoken creature you controlled that was destroyed this way";
    }

    private CeaselessConflictEffect(final CeaselessConflictEffect effect) {
        super(effect);
    }

    @Override
    public CeaselessConflictEffect copy() {
        return new CeaselessConflictEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
            StaticFilters.FILTER_PERMANENT_CREATURE,
            source.getControllerId(), source, game
        )) {
            if (permanent.destroy(source, game) &&
                permanent.isControlledBy(source.getControllerId()) &&
                !(permanent instanceof PermanentToken)
            ) {
                count++;
            }
        }
        if (count > 0) {
            game.processAction();
            new Spirit32Token().putOntoBattlefield(count, game, source, source.getControllerId(), true, false);
        }
        return true;
    }
}
