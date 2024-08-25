package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.AlienInsectToken;

import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class VrestinMenoptraLeader extends CardImpl {


    public VrestinMenoptraLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vrestin enters with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // When Vrestin enters, create X 1/1 green and white Alien Insect creature tokens with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new AlienInsectToken(), GetXValue.instance)
                .setText("create X 1/1 green and white Alien Insect creature tokens with flying.")));

        // Whenever you attack with one or more Insects, put a +1/+1 counter on each of them.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance(1))
                .setText("put a +1/+1 counter on each of them."), 1, new FilterCreaturePermanent(SubType.INSECT, "Insects"), true));


    }

    private VrestinMenoptraLeader(final VrestinMenoptraLeader card) {
        super(card);
    }

    @Override
    public VrestinMenoptraLeader copy() {
        return new VrestinMenoptraLeader(this);
    }
}