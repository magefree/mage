package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FractalMascot extends CardImpl {

    public FractalMascot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{U}");

        this.subtype.add(SubType.FRACTAL);
        this.subtype.add(SubType.ELK);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When this creature enters, tap target creature an opponent controls. Put a stun counter on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("Put a stun counter on it"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private FractalMascot(final FractalMascot card) {
        super(card);
    }

    @Override
    public FractalMascot copy() {
        return new FractalMascot(this);
    }
}
// he just like me fr
