package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MmmenonUthrosExile extends CardImpl {

    public MmmenonUthrosExile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JELLYFISH);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever an artifact you control enters, put a +1/+1 counter on target creature.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MmmenonUthrosExile(final MmmenonUthrosExile card) {
        super(card);
    }

    @Override
    public MmmenonUthrosExile copy() {
        return new MmmenonUthrosExile(this);
    }
}
