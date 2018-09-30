package mage.cards.p;

import java.util.Iterator;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class PriceOfFame extends CardImpl {

    public PriceOfFame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // This spell costs {2} less to cast if it targets a legendary creature.
        this.addAbility(new SimpleStaticAbility(Zone.STACK,
                new SpellCostReductionSourceEffect(2, PriceOfFameCondition.instance))
                .setRuleAtTheTop(true));

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Surveil 2.
        this.getSpellAbility().addEffect(new SurveilEffect(2));
    }

    public PriceOfFame(final PriceOfFame card) {
        super(card);
    }

    @Override
    public PriceOfFame copy() {
        return new PriceOfFame(this);
    }
}

enum PriceOfFameCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject sourceSpell = game.getStack().getStackObject(source.getSourceId());
        if (sourceSpell != null) {
            Iterator<Target> targets = sourceSpell.getStackAbility().getTargets().iterator();
            while (targets.hasNext()) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(targets.next().getFirstTarget());
                if (permanent != null && permanent.isCreature() && permanent.isLegendary()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "it targets a legendary creature";
    }

}
