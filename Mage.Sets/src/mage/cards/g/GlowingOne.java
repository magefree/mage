package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.MillTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GlowingOne extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard();

    public GlowingOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Glowing One deals combat damage to a player, they get four rad counters.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.RAD.createInstance(4)), false, true)
        );

        // Whenever a player mills a nonland card, you gain 1 life.
        this.addAbility(new MillTriggeredAbility(
                Zone.BATTLEFIELD, new GainLifeEffect(1),
                TargetController.ANY, filter, false
        ));
    }

    private GlowingOne(final GlowingOne card) {
        super(card);
    }

    @Override
    public GlowingOne copy() {
        return new GlowingOne(this);
    }
}
