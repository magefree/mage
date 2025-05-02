package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Tonberry extends CardImpl {

    public Tonberry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.SALAMANDER);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // This creature enters tapped with a stun counter on it.
        Ability ability = new EntersBattlefieldAbility(
                new TapSourceEffect(true), "tapped with a stun counter on it"
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.STUN.createInstance()));
        this.addAbility(ability);

        // Chef's Knife -- During your turn, this creature has first strike and deathtouch.
        ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()),
                MyTurnCondition.instance, "during your turn, this creature has first strike"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DeathtouchAbility.getInstance()),
                MyTurnCondition.instance, "and deathtouch"
        ));
        this.addAbility(ability.withFlavorWord("Chef's Knife"));
    }

    private Tonberry(final Tonberry card) {
        super(card);
    }

    @Override
    public Tonberry copy() {
        return new Tonberry(this);
    }
}
