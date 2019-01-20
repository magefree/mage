
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class MaliciousAdvice extends CardImpl {

    public MaliciousAdvice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{B}");

        // Tap X target artifacts, creatures, and/or lands. You lose X life.
        Effect effect = new TapTargetEffect();
        effect.setText("X target artifacts, creatures, and/or lands.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(ManacostVariableValue.instance));
        this.getSpellAbility().setTargetAdjuster(MaliciousAdviceAdjuster.instance);
    }

    public MaliciousAdvice(final MaliciousAdvice card) {
        super(card);
    }

    @Override
    public MaliciousAdvice copy() {
        return new MaliciousAdvice(this);
    }
}

enum MaliciousAdviceAdjuster implements TargetAdjuster {
    instance;
    private static final FilterPermanent filter = new FilterPermanent("artifacts, creatures, and/or lands");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)
        ));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetPermanent(ability.getManaCostsToPay().getX(), filter));
    }
}