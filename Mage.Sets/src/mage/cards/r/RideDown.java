package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class RideDown extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blocking creature");

    static {
        filter.add(BlockingPredicate.instance);
    }

    public RideDown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{W}");

        // Destroy target blocking creature. Creatures that were blocked by that creature this combat gain trample until end of turn.
        this.getSpellAbility().addEffect(new RideDownEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

    }

    private RideDown(final RideDown card) {
        super(card);
    }

    @Override
    public RideDown copy() {
        return new RideDown(this);
    }
}

class RideDownEffect extends OneShotEffect {

    public RideDownEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy target blocking creature. Creatures that were blocked by that creature this combat gain trample until end of turn";
    }

    public RideDownEffect(final RideDownEffect effect) {
        super(effect);
    }

    @Override
    public RideDownEffect copy() {
        return new RideDownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent blockingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (blockingCreature != null) {
                for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                    if (combatGroup.getBlockers().contains(blockingCreature.getId())) {
                        for (UUID attackerId : combatGroup.getAttackers()) {
                            ContinuousEffect effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
                            effect.setTargetPointer(new FixedTarget(attackerId, game));
                            game.addEffect(effect, source);
                        }
                        break;
                    }
                }
                blockingCreature.destroy(source, game, false);
            }
            return true;
        }
        return false;
    }
}
