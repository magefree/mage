
package mage.cards.m;

import java.util.List;
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
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class MartialCoup extends CardImpl {

    public MartialCoup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{W}{W}");

        // create X 1/1 white Soldier creature tokens. If X is 5 or more, destroy all other creatures.
        this.getSpellAbility().addEffect(new MartialCoupEffect());
    }

    private MartialCoup(final MartialCoup card) {
        super(card);
    }

    @Override
    public MartialCoup copy() {
        return new MartialCoup(this);
    }
}

class MartialCoupEffect extends OneShotEffect {

    private static SoldierToken token = new SoldierToken();

    public MartialCoupEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create X 1/1 white Soldier creature tokens. If X is 5 or more, destroy all other creatures";
    }

    private MartialCoupEffect(final MartialCoupEffect effect) {
        super(effect);
    }

    @Override
    public MartialCoupEffect copy() {
        return new MartialCoupEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getX();
        token.putOntoBattlefield(amount, game, source, source.getControllerId());
        List<UUID> tokens = token.getLastAddedTokenIds();
        if (amount > 4) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, source.getControllerId(), game)) {
                if (!tokens.contains(permanent.getId())) {
                    permanent.destroy(source, game, false);
                }
            }
        }
        return true;
    }

}
