package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
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
public final class GrapplingKraken extends CardImpl {

    public GrapplingKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Landfall -- Whenever a land you control enters, tap target creature an opponent controls and put a stun counter on it.
        Ability ability = new LandfallAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("and put a stun counter on it"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private GrapplingKraken(final GrapplingKraken card) {
        super(card);
    }

    @Override
    public GrapplingKraken copy() {
        return new GrapplingKraken(this);
    }
}
