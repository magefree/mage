package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CaptainAmericaTeamLeader extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.HERO, "another Hero you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public CaptainAmericaTeamLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another Hero you control enters, it gains vigilance and haste until end of turn.
        // Put a +1/+1 counter on that Hero and a +1/+1 counter on Captain America.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
            Zone.BATTLEFIELD,
            new GainAbilityTargetEffect(VigilanceAbility.getInstance()).setText("it gains vigilance"),
            filter, false, SetTargetPointer.PERMANENT
        );
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance()).setText("and haste until end of turn"));
        ability.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
            .setText("Put a +1/+1 counter on that Hero"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
            .setText("and a +1/+1 counter on {this}"));
        this.addAbility(ability);
    }

    private CaptainAmericaTeamLeader(final CaptainAmericaTeamLeader card) {
        super(card);
    }

    @Override
    public CaptainAmericaTeamLeader copy() {
        return new CaptainAmericaTeamLeader(this);
    }
}
