
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.OutlastAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;

/**
 *
 * @author LevelX2
 */
public final class TuskguardCaptain extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CounterType.P1P1.getPredicate());
    }

    static final String rule = "Each creature you control with a +1/+1 counter on it has trample";

    public TuskguardCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Outlast G
        this.addAbility(new OutlastAbility(new ManaCostsImpl("{G}")));
        // Each creature you control with a +1/+1 counter on it has trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter, rule)));


    }

    public TuskguardCaptain(final TuskguardCaptain card) {
        super(card);
    }

    @Override
    public TuskguardCaptain copy() {
        return new TuskguardCaptain(this);
    }
}
