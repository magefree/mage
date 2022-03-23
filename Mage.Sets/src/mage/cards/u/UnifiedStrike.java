
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author TheElk801
 */
public final class UnifiedStrike extends CardImpl {

    public UnifiedStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Exile target attacking creature if its power is less than or equal to the number of Soldiers on the battlefield.
        this.getSpellAbility().addEffect(new UnifiedStrikeEffect());
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private UnifiedStrike(final UnifiedStrike card) {
        super(card);
    }

    @Override
    public UnifiedStrike copy() {
        return new UnifiedStrike(this);
    }
}

class UnifiedStrikeEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SubType.SOLDIER.getPredicate());
    }

    UnifiedStrikeEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target attacking creature if its power is less than or equal to the number of Soldiers on the battlefield";
    }

    UnifiedStrikeEffect(final UnifiedStrikeEffect effect) {
        super(effect);
    }

    @Override
    public UnifiedStrikeEffect copy() {
        return new UnifiedStrikeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (creature == null || player == null) {
            return false;
        }
        int soldierCount = game.getBattlefield()
                .getActivePermanents(
                        filter,
                        source.getControllerId(),
                        source, game
                ).size();
        boolean successful = creature.getPower().getValue() <= soldierCount;
        if (successful) {
            player.moveCards(creature, Zone.EXILED, source, game);
        }
        return successful;
    }
}