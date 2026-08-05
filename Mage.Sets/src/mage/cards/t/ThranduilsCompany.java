package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class ThranduilsCompany extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ELF);

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public ThranduilsCompany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // As long as you control another Elf, you may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield), condition,
            "as long as you control another Elf, you may play an additional land on each of your turns"
        )));

        // Landfall -- Whenever a land you control enters, put two +1/+1 counters on target creature you control. It gains vigilance until end of turn.
        Ability ability = new LandfallAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance()).setText("It gains vigilance until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ThranduilsCompany(final ThranduilsCompany card) {
        super(card);
    }

    @Override
    public ThranduilsCompany copy() {
        return new ThranduilsCompany(this);
    }
}
