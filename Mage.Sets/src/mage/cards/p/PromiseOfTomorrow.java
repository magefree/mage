package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PromiseOfTomorrow extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledCreaturePermanent("you control no creatures"),
            ComparisonType.EQUAL_TO, 0, true
    );

    public PromiseOfTomorrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever a creature you control dies, exile it.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new ExileTargetForSourceEffect().setText("exile it"), false,
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, true
        ));

        // At the beginning of each end step, if you control no creatures, sacrifice Promise of Tomorrow and return all cards exiled with it to the battlefield under your control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new SacrificeSourceEffect(), false, condition
        );
        ability.addEffect(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD)
                .setText("and return all cards exiled with it to the battlefield under your control"));
        this.addAbility(ability);
    }

    private PromiseOfTomorrow(final PromiseOfTomorrow card) {
        super(card);
    }

    @Override
    public PromiseOfTomorrow copy() {
        return new PromiseOfTomorrow(this);
    }
}
