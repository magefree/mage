
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class PlagueBelcher extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another Zombie you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.ZOMBIE.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public PlagueBelcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Plague Belcher enters the battlefield, put two -1/-1 counters on target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance(2)));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever another Zombie you control dies, each opponent loses 1 life.
        this.addAbility(new DiesCreatureTriggeredAbility(new LoseLifeOpponentsEffect(1), false, filter));
    }

    private PlagueBelcher(final PlagueBelcher card) {
        super(card);
    }

    @Override
    public PlagueBelcher copy() {
        return new PlagueBelcher(this);
    }
}
