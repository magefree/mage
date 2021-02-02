
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.OutlastAbility;
import mage.abilities.keyword.ReachAbility;
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
public final class LongshotSquad extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CounterType.P1P1.getPredicate());
    }

    public LongshotSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Outlast 1G
        this.addAbility(new OutlastAbility(new ManaCostsImpl("{1}{G}")));
        // Each creature you control with a +1/+1 counter on it has reach.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(ReachAbility.getInstance(), Duration.WhileOnBattlefield, filter, 
                "Each creature you control with a +1/+1 counter on it has reach")));

    }

    private LongshotSquad(final LongshotSquad card) {
        super(card);
    }

    @Override
    public LongshotSquad copy() {
        return new LongshotSquad(this);
    }
}
