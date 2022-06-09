
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class StreetSpasm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public StreetSpasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{R}");


        // Street Spasm deals X damage to target creature without flying you don't control.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));

        // Overload {X}{X}{R}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        this.addAbility(new OverloadAbility(this, new DamageAllEffect(ManacostVariableValue.REGULAR, filter), new ManaCostsImpl<>("{X}{X}{R}{R}")));
    }

    private StreetSpasm(final StreetSpasm card) {
        super(card);
    }

    @Override
    public StreetSpasm copy() {
        return new StreetSpasm(this);
    }
}