package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.target.TargetPermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author muz
 */
public final class StrategicIntervention extends CardImpl {

    public static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public StrategicIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Whenever a creature you control attacks alone, it gets +1/+1 until end of turn. Tap up to one target creature defending player controls.
        Ability ability = new AttacksAloneControlledTriggeredAbility(
            new BoostTargetEffect(1, 1).setText("it gets +1/+1 until end of turn"),
            StaticFilters.FILTER_CONTROLLED_A_CREATURE, true, false
        );
        ability.addEffect(new TapTargetEffect().setTargetPointer(new SecondTargetPointer()));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private StrategicIntervention(final StrategicIntervention card) {
        super(card);
    }

    @Override
    public StrategicIntervention copy() {
        return new StrategicIntervention(this);
    }
}
