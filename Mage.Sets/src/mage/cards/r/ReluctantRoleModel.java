package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.SurvivalAbility;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.PutSavedPermanentCountersTargetEffect;
import mage.abilities.effects.common.counter.AddCounterChoiceSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReluctantRoleModel extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public ReluctantRoleModel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SURVIVOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Survival -- At the beginning of your second main phase, if this creature is tapped, put a flying, lifelink, or +1/+1 counter on it.
        this.addAbility(new SurvivalAbility(new AddCounterChoiceSourceEffect(
                CounterType.FLYING, CounterType.LIFELINK, CounterType.P1P1
        ).setText("put a flying, lifelink, or +1/+1 counter on it")));

        // Whenever this creature or another creature you control dies, if it had counters on it, put those counters on up to one target creature.
        Ability ability = new DiesThisOrAnotherTriggeredAbility(
                new PutSavedPermanentCountersTargetEffect("creatureDied"), false, filter
        ).setApplyFilterOnSource(true);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private ReluctantRoleModel(final ReluctantRoleModel card) {
        super(card);
    }

    @Override
    public ReluctantRoleModel copy() {
        return new ReluctantRoleModel(this);
    }
}
