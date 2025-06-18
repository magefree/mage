package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.KithkinSoldierToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GwyllionHedgeMage extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.PLAINS, "you control two or more Plains"),
            ComparisonType.MORE_THAN, 1
    );
    private static final Condition condition2 = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.SWAMP, "you control two or more Swamps"),
            ComparisonType.MORE_THAN, 1
    );

    public GwyllionHedgeMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W/B}");
        this.subtype.add(SubType.HAG);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Gwyllion Hedge-Mage enters the battlefield, if you control two or more Plains, you may create a 1/1 white Kithkin Soldier creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KithkinSoldierToken()), true).withInterveningIf(condition));

        // When Gwyllion Hedge-Mage enters the battlefield, if you control two or more Swamps, you may put a -1/-1 counter on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance()), true).withInterveningIf(condition2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private GwyllionHedgeMage(final GwyllionHedgeMage card) {
        super(card);
    }

    @Override
    public GwyllionHedgeMage copy() {
        return new GwyllionHedgeMage(this);
    }
}
