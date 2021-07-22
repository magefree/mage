
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

/**
 *
 * @author North
 */
public final class Magmaquake extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature without flying and each planeswalker");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                    CardType.CREATURE.getPredicate(),
                    Predicates.not(new AbilityPredicate(FlyingAbility.class))),
                CardType.PLANESWALKER.getPredicate()));
    }

    public Magmaquake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{R}{R}");


        // Magmaquake deals X damage to each creature without flying and each planeswalker.
        this.getSpellAbility().addEffect(new DamageAllEffect(ManacostVariableValue.REGULAR, filter));
    }

    private Magmaquake(final Magmaquake card) {
        super(card);
    }

    @Override
    public Magmaquake copy() {
        return new Magmaquake(this);
    }
}
