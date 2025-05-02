package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ApothecaryStomper extends CardImpl {

    public ApothecaryStomper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When this creature enters, choose one --
        // * Put two +1/+1 counters on target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(2))
        );
        ability.addTarget(new TargetControlledCreaturePermanent());

        // * You gain 4 life.
        ability.addMode(new Mode(new GainLifeEffect(4)));
        this.addAbility(ability);
    }

    private ApothecaryStomper(final ApothecaryStomper card) {
        super(card);
    }

    @Override
    public ApothecaryStomper copy() {
        return new ApothecaryStomper(this);
    }
}
