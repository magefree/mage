package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetActivatedOrTriggeredAbility;

import java.util.UUID;

/**
 * @author muz
 */
public final class SubcommanderTPol extends CardImpl {

    public SubcommanderTPol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VULCAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Subcommander T'Pol enters, choose one --
        // Counter target activated or triggered ability.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CounterTargetEffect());
        ability.addTarget(new TargetActivatedOrTriggeredAbility());

        // Tap up to one target artifact or creature. Put a stun counter on it.
        Mode mode = new Mode(new TapTargetEffect());
        mode.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        mode.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("Put a stun counter on it"));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private SubcommanderTPol(final SubcommanderTPol card) {
        super(card);
    }

    @Override
    public SubcommanderTPol copy() {
        return new SubcommanderTPol(this);
    }
}
