package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OrneryTumblewagg extends CardImpl {

    public OrneryTumblewagg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BRUSHWAGG);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, put a +1/+1 counter on target creature.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever Ornery Tumblewagg attacks while saddled, double the number of +1/+1 counters on target creature.
        ability = new AttacksWhileSaddledTriggeredAbility(
                new DoubleCountersTargetEffect(CounterType.P1P1)
                        .setText("double the number of +1/+1 counters on target creature")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Saddle 2
        this.addAbility(new SaddleAbility(2));

    }

    private OrneryTumblewagg(final OrneryTumblewagg card) {
        super(card);
    }

    @Override
    public OrneryTumblewagg copy() {
        return new OrneryTumblewagg(this);
    }
}
