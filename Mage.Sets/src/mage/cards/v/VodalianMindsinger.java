package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VodalianMindsinger extends CardImpl {

    private static final DynamicValue xValue
            = new MultipliedValue(MultikickerCount.instance, 2);
    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature with power less than {this}'s power");

    static {
        filter.add(VodalianMindsingerPredicate.instance);
    }

    public VodalianMindsinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {1}{R} and/or {1}{G}
        KickerAbility kickerAbility = new KickerAbility("{1}{R}");
        kickerAbility.addKickerCost("{1}{G}");
        this.addAbility(kickerAbility);

        // Vodalian Mindsinger enters the battlefield with two +1/+1 counters on it for each time it was kicked.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(0), xValue, true
        ), "with two +1/+1 counters on it for each time it was kicked"));

        // When Vodalian Mindsinger enters the battlefield, gain control of target creature with power less than Vodalian Mindsinger's power for as long as you control Vodalian Mindsinger.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainControlTargetEffect(Duration.WhileControlled));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private VodalianMindsinger(final VodalianMindsinger card) {
        super(card);
    }

    @Override
    public VodalianMindsinger copy() {
        return new VodalianMindsinger(this);
    }
}

enum VodalianMindsingerPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent permanent = input.getSource().getSourcePermanentOrLKI(game);
        return permanent != null && permanent.getPower().getValue() > input.getObject().getPower().getValue();
    }
}
