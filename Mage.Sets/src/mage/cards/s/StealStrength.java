
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class StealStrength extends CardImpl {

    public StealStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");

        // Target creature gets +1/+1 until end of turn. Another target creature gets -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new StealStrengthEffect());
        
        FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature (gets +1/+1 until end of turn)");
        TargetCreaturePermanent target1 = new TargetCreaturePermanent(filter1);
        target1.setTargetTag(1);
        this.getSpellAbility().addTarget(target1);
        
        FilterCreaturePermanent filter2 = new FilterCreaturePermanent("another creature (gets -1/-1 until end of turn)");
        filter2.add(new AnotherTargetPredicate(2));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(filter2);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
    }

    private StealStrength(final StealStrength card) {
        super(card);
    }

    @Override
    public StealStrength copy() {
        return new StealStrength(this);
    }
}

class StealStrengthEffect extends ContinuousEffectImpl {

    public StealStrengthEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.staticText = "Target creature gets +1/+1 until end of turn. Another target creature gets -1/-1 until end of turn";
    }

    private StealStrengthEffect(final StealStrengthEffect effect) {
        super(effect);
    }

    @Override
    public StealStrengthEffect copy() {
        return new StealStrengthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.addPower(1);
            permanent.addToughness(1);
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.addPower(-1);
            permanent.addToughness(-1);
        }
        return true;
    }
}