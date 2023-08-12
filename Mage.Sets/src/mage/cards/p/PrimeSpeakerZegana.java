package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public final class PrimeSpeakerZegana extends CardImpl {

    public PrimeSpeakerZegana(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.supertype.add(SuperType.LEGENDARY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Prime Speaker Zegana enters the battlefield with X +1/+1 counters on it, where X is the greatest power among other creatures you control.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(0),
                new GreatestPowerCount(), true);
        effect.setText("with X +1/+1 counters on it, where X is the greatest power among other creatures you control.");
        this.addAbility(new EntersBattlefieldAbility(effect));
        //When Prime Speaker Zegana enters the battlefield, draw cards equal to its power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(new SourcePermanentPowerCount())));
    }

    private PrimeSpeakerZegana(final PrimeSpeakerZegana card) {
        super(card);
    }

    @Override
    public PrimeSpeakerZegana copy() {
        return new PrimeSpeakerZegana(this);
    }
}

class GreatestPowerCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int value = 0;
        for (Permanent creature : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), sourceAbility.getControllerId(), game)) {
            if (creature != null && creature.getPower().getValue() > value && !sourceAbility.getSourceId().equals(creature.getId())) {
                value = creature.getPower().getValue();
            }
        }
        return value;
    }

    @Override
    public GreatestPowerCount copy() {
        return new GreatestPowerCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "greatest power among other creatures you control";
    }
}
