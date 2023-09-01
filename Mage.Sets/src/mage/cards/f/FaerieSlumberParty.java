package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FaerieBlockFliersToken;
import mage.game.permanent.token.Token;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class FaerieSlumberParty extends CardImpl {

    public FaerieSlumberParty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Return all creatures to their owners' hands. For each opponent who controlled a creature returned this way, you create two 1/1 blue Faerie creature tokens with flying and "This creature can block only creatures with flying."
        this.getSpellAbility().addEffect(new FaerieSlumberPartyEffect());
    }

    private FaerieSlumberParty(final FaerieSlumberParty card) {
        super(card);
    }

    @Override
    public FaerieSlumberParty copy() {
        return new FaerieSlumberParty(this);
    }
}

class FaerieSlumberPartyEffect extends OneShotEffect {

    FaerieSlumberPartyEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return all creatures to their owners' hands. For each opponent who controlled a creature "
                + "returned this way, you create two 1/1 blue Faerie creature tokens with flying and "
                + "\"This creature can block only creatures with flying.\"";
    }

    private FaerieSlumberPartyEffect(final FaerieSlumberPartyEffect effect) {
        super(effect);
    }

    @Override
    public FaerieSlumberPartyEffect copy() {
        return new FaerieSlumberPartyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        int count = game.getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)
                .stream()
                .filter(Objects::nonNull)
                .map(Permanent::getControllerId)
                .distinct()
                .filter(opponents::contains)
                .mapToInt(id -> 1)
                .sum();

        if(!new ReturnToHandFromBattlefieldAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE)
                .apply(game, source)) {
            return false;
        }

        if(count > 0) {
            Token token = new FaerieBlockFliersToken();
            token.putOntoBattlefield(2 * count, game, source, source.getControllerId());
        }

        return true;
    }

}