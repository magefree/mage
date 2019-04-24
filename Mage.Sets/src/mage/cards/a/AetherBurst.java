
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class AetherBurst extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards named Aether Burst");

    static {
        filter.add(new NamePredicate("Aether Burst"));
    }

    public AetherBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return up to X target creatures to their owners' hands, where X is one plus the number of cards named Aether Burst in all graveyards as you cast Aether Burst.
        this.getSpellAbility().addEffect(new DynamicReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new DynamicTargetCreaturePermanent());
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Target target = ability.getTargets().get(0);
        if (target instanceof DynamicTargetCreaturePermanent) {
            Player controller = game.getPlayer(ability.getControllerId());
            int amount = 0;
            if (controller != null) {
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        amount += player.getGraveyard().getCards(filter, game).size();
                    }
                }
            }
            target.setMaxNumberOfTargets(amount + 1);
        }
    }

    public AetherBurst(final AetherBurst card) {
        super(card);
    }

    @Override
    public AetherBurst copy() {
        return new AetherBurst(this);
    }
}

class DynamicTargetCreaturePermanent extends TargetPermanent {

    public DynamicTargetCreaturePermanent() {
        super(FILTER_PERMANENT_CREATURES);
    }

    public DynamicTargetCreaturePermanent(final DynamicTargetCreaturePermanent target) {
        super(target);
    }

    @Override
    public void setMaxNumberOfTargets(int maxNumberOfTargets) {
        this.maxNumberOfTargets = maxNumberOfTargets;
    }

    @Override
    public DynamicTargetCreaturePermanent copy() {
        return new DynamicTargetCreaturePermanent(this);
    }

}

/**
 * We extend ReturnToHandTargetEffect class just to override the rules.
 */
class DynamicReturnToHandTargetEffect extends ReturnToHandTargetEffect {

    public DynamicReturnToHandTargetEffect() {
        super();
    }

    public DynamicReturnToHandTargetEffect(final DynamicReturnToHandTargetEffect effect) {
        super(effect);
    }

    @Override
    public DynamicReturnToHandTargetEffect copy() {
        return new DynamicReturnToHandTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "Return up to X target creatures to their owners' hands, where X is one plus the number of cards named Aether Burst in all graveyards as you cast Aether Burst";
    }

}
