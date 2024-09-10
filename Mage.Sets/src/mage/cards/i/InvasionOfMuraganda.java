package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfMuraganda extends CardImpl {

    public InvasionOfMuraganda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{4}{G}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(6);
        this.secondSideCardClazz = mage.cards.p.PrimordialPlasm.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Muraganda enters the battlefield, put a +1/+1 counter on target creature you control. Then that creature fights up to one target creature you don't control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addEffect(new FightTargetsEffect()
                .setText("Then that creature fights up to one target creature you don't control"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);
    }

    private InvasionOfMuraganda(final InvasionOfMuraganda card) {
        super(card);
    }

    @Override
    public InvasionOfMuraganda copy() {
        return new InvasionOfMuraganda(this);
    }
}
