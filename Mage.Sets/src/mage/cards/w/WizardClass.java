package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.BecomesClassLevelTriggeredAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WizardClass extends CardImpl {

    public WizardClass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // You have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // {2}{U}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{2}{U}"));

        // When this Class becomes level 2, draw two cards.
        this.addAbility(new BecomesClassLevelTriggeredAbility(new DrawCardSourceControllerEffect(2), 2));

        // {4}{U}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{4}{U}"));

        // Whenever you draw a card, put a +1/+1 counter on target creature you control.
        Ability ability = new DrawCardControllerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 3)));
    }

    private WizardClass(final WizardClass card) {
        super(card);
    }

    @Override
    public WizardClass copy() {
        return new WizardClass(this);
    }
}
