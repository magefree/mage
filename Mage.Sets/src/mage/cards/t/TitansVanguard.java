package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TitansVanguard extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("colorless creature you control");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public TitansVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When you cast this spell and whenever Titans' Vanguard attacks, put a +1/+1 counter on each colorless creature you control.
        Ability ability = new OrTriggeredAbility(
                Zone.ALL,
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter),
                new CastSourceTriggeredAbility(null),
                new AttacksTriggeredAbility(null)
        ).setTriggerPhrase("When you cast this spell and whenever {this} attacks, ");
        this.addAbility(ability);


        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private TitansVanguard(final TitansVanguard card) {
        super(card);
    }

    @Override
    public TitansVanguard copy() {
        return new TitansVanguard(this);
    }
}
