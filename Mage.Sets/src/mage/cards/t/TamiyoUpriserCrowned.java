package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CombatDamageDealtToYouTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TamiyoUpriserCrowned extends CardImpl {

    public TamiyoUpriserCrowned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOONFOLK, SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Tamiyo enters, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // Whenever one or more creatures deal combat damage to you while you're the monarch, tap those creatures and put a stun counter on each of them.
        Ability ability = new CombatDamageDealtToYouTriggeredAbility(new TapTargetEffect().setText("tap those creatures"), SetTargetPointer.PERMANENT)
                .withTriggerCondition(MonarchIsSourceControllerCondition.instance)
                .setTriggerPhrase("Whenever one or more creatures deal combat damage to you while you're the monarch, ");
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("and put a stun counter on each of them"));
        this.addAbility(ability);
    }

    private TamiyoUpriserCrowned(final TamiyoUpriserCrowned card) {
        super(card);
    }

    @Override
    public TamiyoUpriserCrowned copy() {
        return new TamiyoUpriserCrowned(this);
    }
}
