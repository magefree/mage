
package mage.cards.m;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class MaliciousAdvice extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts, creatures, and/or lands");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public MaliciousAdvice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{B}");

        // Tap X target artifacts, creatures, and/or lands. You lose X life.
        Effect effect = new TapTargetEffect();
        effect.setText("Tap X target artifacts, creatures, and/or lands");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private MaliciousAdvice(final MaliciousAdvice card) {
        super(card);
    }

    @Override
    public MaliciousAdvice copy() {
        return new MaliciousAdvice(this);
    }
}
