package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BruceBanner extends ModalDoubleFacedCard {

    public BruceBanner(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SCIENTIST, SubType.HERO}, "{U}",
                "The Incredible Hulk",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GAMMA, SubType.BERSERKER, SubType.HERO}, "{2}{R}{R}{G}{G}"
        );
        this.getLeftHalfCard().setPT(1, 1);
        this.getRightHalfCard().setPT(8, 8);

        // {X}{X}, {T}: Draw X cards. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new DrawCardSourceControllerEffect(GetXValue.instance), new ManaCostsImpl<>("{X}{X}")
        );
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability);

        // {2}{R}{R}{G}{G}: Transform Bruce Banner. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{2}{R}{R}{G}{G}")
        ));

        // The Incredible Hulk
        // Reach
        this.getRightHalfCard().addAbility(ReachAbility.getInstance());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Enrage -- Whenever The Incredible Hulk is dealt damage, put a +1/+1 counter on him. If he's attacking, untap him and there is an additional combat phase after this phase.
        ability = new DealtDamageToSourceTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on him"),
                false, true
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new UntapSourceEffect(), SourceAttackingCondition.instance, "If he's attacking, " +
                "untap him and there is an additional combat phase after this phase"
        ).addEffect(new AdditionalCombatPhaseEffect()));
        this.getRightHalfCard().addAbility(ability);
    }

    private BruceBanner(final BruceBanner card) {
        super(card);
    }

    @Override
    public BruceBanner copy() {
        return new BruceBanner(this);
    }
}
