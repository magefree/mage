package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EutropiaTheTwiceFavored extends CardImpl {

    public EutropiaTheTwiceFavored(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Constellation—Whenever an enchantment enters the battlefield under your control, put a +1/+1 counter on target creature. That creature gains flying until end of turn.
        Ability ability = new ConstellationAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false, false
        );
        ability.addEffect(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn,
                "That creature gains flying until end of turn"
        ));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private EutropiaTheTwiceFavored(final EutropiaTheTwiceFavored card) {
        super(card);
    }

    @Override
    public EutropiaTheTwiceFavored copy() {
        return new EutropiaTheTwiceFavored(this);
    }
}
