package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StrangeNewWorldsValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.StrangeNewWorldsHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.OfficerToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class ExcalbianGamemaster extends CardImpl {

    public ExcalbianGamemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.subtype.add(SubType.EXCALBIAN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Strange new worlds -- When this creature enters, create X 1/1 red Officer creature tokens, where X is the number of differently named lands you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(
            new OfficerToken(), StrangeNewWorldsValue.instance
        ).setText("create X 1/1 red Officer creature tokens, where X is the number of differently named lands you control"))
            .setAbilityWord(AbilityWord.STRANGE_NEW_WORLDS)
            .addHint(StrangeNewWorldsHint.instance)
        );

        // Whenever this creature or another creature you control dies, put a +1/+1 counter on target creature you control.
        Ability ability = new DiesThisOrAnotherTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
            false,
            StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ExcalbianGamemaster(final ExcalbianGamemaster card) {
        super(card);
    }

    @Override
    public ExcalbianGamemaster copy() {
        return new ExcalbianGamemaster(this);
    }
}
