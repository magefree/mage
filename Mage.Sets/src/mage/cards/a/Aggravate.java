
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author North
 */
public final class Aggravate extends CardImpl {

    public Aggravate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}{R}");


        // Aggravate deals 1 damage to each creature target player controls.
        this.getSpellAbility().addEffect(new DamageAllControlledTargetEffect(1, new FilterCreaturePermanent()));
        this.getSpellAbility().addTarget(new TargetPlayer());
        // Each creature dealt damage this way attacks this turn if able.
        this.getSpellAbility().addEffect(new AggravateRequirementEffect());
        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));
    }

    private Aggravate(final Aggravate card) {
        super(card);
    }

    @Override
    public Aggravate copy() {
        return new Aggravate(this);
    }
}

class AggravateRequirementEffect extends RequirementEffect {

    public AggravateRequirementEffect() {
        super(Duration.EndOfTurn);
        this.staticText = "Each creature dealt damage this way attacks this turn if able";
    }

    private AggravateRequirementEffect(final AggravateRequirementEffect effect) {
        super(effect);
    }

    @Override
    public AggravateRequirementEffect copy() {
        return new AggravateRequirementEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        DamagedByWatcher watcher = game.getState().getWatcher(DamagedByWatcher.class, source.getSourceId());
        if (watcher != null) {
            return watcher.wasDamaged(permanent, game);
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }
}
