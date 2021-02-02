
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.OutlastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;

/**
 *
 * @author LevelX2
 */
public final class AinokBondKin extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CounterType.P1P1.getPredicate());
    }

    static final String rule = "Each creature you control with a +1/+1 counter on it has first strike";

    public AinokBondKin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Outlast {1}{W} <em>({1}{W}, {T}: Put a +1/+1 counter on this creature. Outlast only as a sorcery.)</em>
        this.addAbility(new OutlastAbility(new ManaCostsImpl("{1}{W}")));

        // Each creature you control with a +1/+1 counter on it has first strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter, rule)));

    }

    private AinokBondKin(final AinokBondKin card) {
        super(card);
    }

    @Override
    public AinokBondKin copy() {
        return new AinokBondKin(this);
    }
}

