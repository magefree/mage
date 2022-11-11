
package mage.cards.b;

import java.util.UUID;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author FenrisulfrX
 */
public final class BreathOfDarigaaz extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public BreathOfDarigaaz(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Kicker {2}
        this.addAbility(new KickerAbility("{2}"));

        // Breath of Darigaaz deals 1 damage to each creature without flying and each player. If Breath of Darigaaz was kicked, it deals 4 damage to each creature without flying and each player instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DamageEverythingEffect(4, filter),
                new DamageEverythingEffect(1, filter), KickedCondition.ONCE,
                "{this} deals 1 damage to each creature without flying and each player. If this spell was kicked, it deals 4 damage to each creature without flying and each player instead."));
    }

    private BreathOfDarigaaz(final BreathOfDarigaaz card) {
        super(card);
    }

    @Override
    public BreathOfDarigaaz copy() {
        return new BreathOfDarigaaz(this);
    }
}
