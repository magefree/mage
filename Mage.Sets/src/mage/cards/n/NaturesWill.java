
package mage.cards.n;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ControlledCreaturesDealCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class NaturesWill extends CardImpl {

    public NaturesWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // Whenever one or more creatures you control deal combat damage to a player, tap all lands that player controls and untap all lands you control.
        this.addAbility(new ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(new NaturesWillEffect()));
    }

    public NaturesWill(final NaturesWill card) {
        super(card);
    }

    @Override
    public NaturesWill copy() {
        return new NaturesWill(this);
    }
}

class NaturesWillEffect extends OneShotEffect {

    public NaturesWillEffect() {
        super(Outcome.Benefit);
        this.staticText = "tap all lands that player controls and untap all lands you control";
    }

    public NaturesWillEffect(final NaturesWillEffect effect) {
        super(effect);
    }

    @Override
    public NaturesWillEffect copy() {
        return new NaturesWillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> damagedPlayers = (HashSet<UUID>) this.getValue("damagedPlayers");
        if (damagedPlayers != null) {
            List<Permanent> lands = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LAND, source.getControllerId(), source.getSourceId(), game);
            for (Permanent land : lands) {
                if (damagedPlayers.contains(land.getControllerId())) {
                   land.tap(game);
                } else if (land.getControllerId().equals(source.getControllerId())) {
                    land.untap(game);
                }
            }
            return true;
        }
        return false;
    }
}
