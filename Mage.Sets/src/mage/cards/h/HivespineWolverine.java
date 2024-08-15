package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HivespineWolverine extends CardImpl {

    public HivespineWolverine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WOLVERINE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Hivespine Wolverine enters, choose one --
        // * Put a +1/+1 counter on target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetControlledCreaturePermanent());

        // * Hivespine Wolverine fights target creature token.
        ability.addMode(new Mode(new FightTargetSourceEffect())
                .addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_TOKEN)));

        // * Destroy target artifact or enchantment.
        ability.addMode(new Mode(new DestroyTargetEffect())
                .addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT)));
        this.addAbility(ability);
    }

    private HivespineWolverine(final HivespineWolverine card) {
        super(card);
    }

    @Override
    public HivespineWolverine copy() {
        return new HivespineWolverine(this);
    }
}
