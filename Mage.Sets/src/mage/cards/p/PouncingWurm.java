package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class PouncingWurm extends CardImpl {

    public PouncingWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Kicker {2}{G}
        this.addAbility(new KickerAbility("{2}{G}"));

        // If Pouncing Wurm was kicked, it enters with three +1/+1 counters on it and with haste.
        Ability ability = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), KickedCondition.ONCE,
                "If {this} was kicked, it enters with three +1/+1 counters on it and with haste.", ""
        );
        ability.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability);
    }

    private PouncingWurm(final PouncingWurm card) {
        super(card);
    }

    @Override
    public PouncingWurm copy() {
        return new PouncingWurm(this);
    }
}
