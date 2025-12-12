
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;

import java.util.UUID;


/**
 *
 * @author LevelX2
 */
public final class Counterflux extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public Counterflux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{U}{R}");


        // Counterflux can't be countered.
        Effect effect =  new CantBeCounteredSourceEffect();
        effect.setText("this spell can't be countered");
        Ability ability = new SimpleStaticAbility(Zone.STACK,effect);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Counter target spell you don't control.
        // Overload {1}{U}{U}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        OverloadAbility.ImplementOverloadAbility(this, new ManaCostsImpl<>("{1}{U}{U}{R}"),
                new TargetSpell(filter), new CounterTargetEffect());
    }

    private Counterflux(final Counterflux card) {
        super(card);
    }

    @Override
    public Counterflux copy() {
        return new Counterflux(this);
    }
}
