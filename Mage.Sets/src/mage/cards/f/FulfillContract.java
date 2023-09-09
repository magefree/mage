
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class FulfillContract extends CardImpl {

    private static final FilterCreaturePermanent filterBountyCreature = new FilterCreaturePermanent("creature with a bounty counter on it");
    private static final FilterControlledCreaturePermanent filterRogueOrHunter = new FilterControlledCreaturePermanent("Rogue or Hunter you control");

    static {
        filterBountyCreature.add(CounterType.BOUNTY.getPredicate());
        filterRogueOrHunter.add(Predicates.or(SubType.ROGUE.getPredicate(), SubType.HUNTER.getPredicate()));
    }

    public FulfillContract(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B/R}{B/R}");

        // Destroy target creature with a bounty counter on it. If that creature is destroyed this way, you may put a +1/+1 counter on target Rogue or Hunter you control.
        this.getSpellAbility().addEffect(new FulfillContractEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterBountyCreature));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(filterRogueOrHunter));

    }

    private FulfillContract(final FulfillContract card) {
        super(card);
    }

    @Override
    public FulfillContract copy() {
        return new FulfillContract(this);
    }
}

class FulfillContractEffect extends OneShotEffect {

    public FulfillContractEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy target creature with a bounty counter on it. If that creature is destroyed this way, you may put a +1/+1 counter on target Rogue or Hunter you control";
    }

    private FulfillContractEffect(final FulfillContractEffect effect) {
        super(effect);
    }

    @Override
    public FulfillContractEffect copy() {
        return new FulfillContractEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanentToDestroy = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent permanentToPutCounter = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (controller != null) {
            if (permanentToDestroy != null && permanentToDestroy.destroy(source, game, false)) {
                if (permanentToPutCounter != null) {
                    permanentToPutCounter.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
