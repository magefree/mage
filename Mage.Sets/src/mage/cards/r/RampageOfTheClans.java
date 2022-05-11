package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CentaurToken;
import mage.game.permanent.token.Token;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RampageOfTheClans extends CardImpl {

    public RampageOfTheClans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Destroy all artifacts and enchantments. For each permanent destroyed this way, its controller creates a 3/3 green Centaur creature token.
        this.getSpellAbility().addEffect(new RampageOfTheClansEffect());
    }

    private RampageOfTheClans(final RampageOfTheClans card) {
        super(card);
    }

    @Override
    public RampageOfTheClans copy() {
        return new RampageOfTheClans(this);
    }
}

class RampageOfTheClansEffect extends OneShotEffect {

    RampageOfTheClansEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy all artifacts and enchantments. " +
                "For each permanent destroyed this way, " +
                "its controller creates a 3/3 green Centaur creature token.";
    }

    private RampageOfTheClansEffect(final RampageOfTheClansEffect effect) {
        super(effect);
    }

    @Override
    public RampageOfTheClansEffect copy() {
        return new RampageOfTheClansEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> playersWithPermanents = new HashMap<>();
        for (Permanent p : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT,
                source.getControllerId(), source, game
        )) {
            UUID controllerId = p.getControllerId();
            if (p.destroy(source, game, false)) {
                playersWithPermanents.put(controllerId, playersWithPermanents.getOrDefault(controllerId, 0) + 1);
            }
        }
        Token token = new CentaurToken();
        for (Map.Entry<UUID, Integer> amountDestroyedByPlayer : playersWithPermanents.entrySet()) {
            token.putOntoBattlefield(amountDestroyedByPlayer.getValue(), game, source, amountDestroyedByPlayer.getKey());
        }
        return true;
    }
}
