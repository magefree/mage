
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
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

/**
 *
 * @author LoneFox
 */
public final class MaliciousAdvice extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts, creatures, and/or lands");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    public MaliciousAdvice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{B}");

        // Tap X target artifacts, creatures, and/or lands. You lose X life.
        Effect effect = new TapTargetEffect();
        effect.setText("X target artifacts, creatures, and/or lands");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(new ManacostVariableValue()));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            ability.addTarget(new TargetPermanent(ability.getManaCostsToPay().getX(), filter));
        }
    }

    public MaliciousAdvice(final MaliciousAdvice card) {
        super(card);
    }

    @Override
    public MaliciousAdvice copy() {
        return new MaliciousAdvice(this);
    }
}
