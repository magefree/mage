package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PollenbrightDruid extends CardImpl {

    public PollenbrightDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Pollenbright Druid enters the battlefield, choose one —
        // • Put a +1/+1 counter on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetCreaturePermanent());

        // • Proliferate. (Choose any number of permanents and/or players, then give each another counter of each kind already there.)
        ability.addMode(new Mode(new ProliferateEffect(true)));
        this.addAbility(ability);
    }

    private PollenbrightDruid(final PollenbrightDruid card) {
        super(card);
    }

    @Override
    public PollenbrightDruid copy() {
        return new PollenbrightDruid(this);
    }
}
