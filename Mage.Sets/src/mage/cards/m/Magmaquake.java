
package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author North
 */
public final class Magmaquake extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature without flying and each planeswalker");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                    new CardTypePredicate(CardType.CREATURE),
                    Predicates.not(new AbilityPredicate(FlyingAbility.class))),
                new CardTypePredicate(CardType.PLANESWALKER)));
    }

    public Magmaquake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{R}{R}");


        // Magmaquake deals X damage to each creature without flying and each planeswalker.
        this.getSpellAbility().addEffect(new DamageAllEffect(ManacostVariableValue.instance, filter));
    }

    public Magmaquake(final Magmaquake card) {
        super(card);
    }

    @Override
    public Magmaquake copy() {
        return new Magmaquake(this);
    }
}
