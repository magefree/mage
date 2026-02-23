package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.OpponentsTurnCondition;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
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
public final class NightmareSower extends CardImpl {

    public NightmareSower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you cast a spell during an opponent's turn, put a -1/-1 counter on up to one target creature.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.M1M1.createInstance()), false
        ).withTriggerCondition(OpponentsTurnCondition.instance);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private NightmareSower(final NightmareSower card) {
        super(card);
    }

    @Override
    public NightmareSower copy() {
        return new NightmareSower(this);
    }
}
