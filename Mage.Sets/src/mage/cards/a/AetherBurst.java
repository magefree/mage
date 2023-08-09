
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author magenoxx_at_gmail.com
 */
public final class AetherBurst extends CardImpl {
    public AetherBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return up to X target creatures to their owners' hands, where X is one plus the number of cards named Aether Burst in all graveyards as you cast this spell.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect().setText("Return up to X target creatures to their owners' hands, where X is one plus the number of cards named Aether Burst in all graveyards as you cast this spell"));
        this.getSpellAbility().addTarget(new DynamicTargetCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(AetherBurstAdjuster.instance);
    }


    private AetherBurst(final AetherBurst card) {
        super(card);
    }

    @Override
    public AetherBurst copy() {
        return new AetherBurst(this);
    }
}

enum AetherBurstAdjuster implements TargetAdjuster {
    instance;
    private static final FilterCard filter = new FilterCard("cards named Aether Burst");

    static {
        filter.add(new NamePredicate("Aether Burst"));
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
}

class DynamicTargetCreaturePermanent extends TargetPermanent {

    public DynamicTargetCreaturePermanent() {
        super(StaticFilters.FILTER_PERMANENT_CREATURES);
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
