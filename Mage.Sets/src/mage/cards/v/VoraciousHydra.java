package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DoubleCountersSourceEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoraciousHydra extends CardImpl {

    public VoraciousHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{G}");

        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Voracious Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())
        ));

        // When Voracious Hydra enters the battlefield, choose one —
        // • Double the number of +1/+1 counters on Voracious Hydra.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DoubleCountersSourceEffect(CounterType.P1P1), false);

        // • Voracious Hydra fights target creature you don't control.
        Mode mode = new Mode(
                new FightTargetSourceEffect()
                        .setText("{this} fights target creature you don't control")
        );
        mode.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private VoraciousHydra(final VoraciousHydra card) {
        super(card);
    }

    @Override
    public VoraciousHydra copy() {
        return new VoraciousHydra(this);
    }
}
