
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class Vebulid extends CardImpl {

    public Vebulid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Vebulid enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1))));

        // At the beginning of your upkeep, you may put a +1/+1 counter on Vebulid.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), TargetController.YOU, true));

        // When Vebulid attacks or blocks, destroy it at end of combat.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new DestroySourceEffect())), false));
    }

    private Vebulid(final Vebulid card) {
        super(card);
    }

    @Override
    public Vebulid copy() {
        return new Vebulid(this);
    }
}
