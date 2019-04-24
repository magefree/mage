
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.OutlastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.CounterPredicate;

/**
 *
 * @author LevelX2
 */
public final class AbzanBattlePriest extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();
    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new CounterPredicate(CounterType.P1P1));
    }

    static final String rule = "Each creature you control with a +1/+1 counter on it has lifelink";

    public AbzanBattlePriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Outlast {W}
        this.addAbility(new OutlastAbility(new ManaCostsImpl<>("{W}")));
        
        // Each creature you control with a +1/+1 counter on it has lifelink.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter, rule)));
    }

    public AbzanBattlePriest(final AbzanBattlePriest card) {
        super(card);
    }

    @Override
    public AbzanBattlePriest copy() {
        return new AbzanBattlePriest(this);
    }
}
