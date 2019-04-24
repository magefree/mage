
package mage.cards.g;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SubTypeSet;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Markedagain
 */
public final class GlobalRuin extends CardImpl {

    public GlobalRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Each player chooses from the lands he or she controls a land of each basic land type, then sacrifices the rest.
        this.getSpellAbility().addEffect(new GlobalRuinDestroyLandEffect());
    }

    public GlobalRuin(final GlobalRuin card) {
        super(card);
    }

    @Override
    public GlobalRuin copy() {
        return new GlobalRuin(this);
    }
}

class GlobalRuinDestroyLandEffect extends OneShotEffect {

    public GlobalRuinDestroyLandEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Each player chooses from the lands he or she controls a land of each basic land type, then sacrifices the rest";
    }

    public GlobalRuinDestroyLandEffect(final GlobalRuinDestroyLandEffect effect) {
        super(effect);
    }

    @Override
    public GlobalRuinDestroyLandEffect copy() {
        return new GlobalRuinDestroyLandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> lands = new HashSet<>();

        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            for (SubType landName : Arrays.stream(SubType.values()).filter(p -> p.getSubTypeSet() == SubTypeSet.BasicLandType).collect(Collectors.toSet())) {
                FilterControlledLandPermanent filter = new FilterControlledLandPermanent(landName + " you control");
                filter.add(new SubtypePredicate(landName));
                Target target = new TargetControlledPermanent(1, 1, filter, true);
                if (target.canChoose(player.getId(), game)) {
                    player.chooseTarget(outcome, target, source, game);
                    lands.add(target.getFirstTarget());
                }
            }
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_LAND, game)) {
            if (!lands.contains(permanent.getId())) {
                permanent.sacrifice(permanent.getId(), game);
            }
        }
        return true;
    }
}
