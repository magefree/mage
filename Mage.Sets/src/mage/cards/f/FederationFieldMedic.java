package mage.cards.f;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class FederationFieldMedic extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public FederationFieldMedic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When this creature enters, choose one --
        // * Put a +1/+1 counter on target creature. It gains vigilance until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance()).setText("It gains vigilance until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());

        // * Draw a card if you control a permanent with a counter on it.
        ability.addMode(new Mode(new ConditionalOneShotEffect(
            new DrawCardSourceControllerEffect(1), condition,
            "draw a card if you control a permanent with a counter on it"
        )));

        this.addAbility(ability);
    }

    private FederationFieldMedic(final FederationFieldMedic card) {
        super(card);
    }

    @Override
    public FederationFieldMedic copy() {
        return new FederationFieldMedic(this);
    }
}
