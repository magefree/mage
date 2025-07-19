package mage.cards.i;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasiveManeuvers extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.SPACECRAFT));

    public InvasiveManeuvers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Invasive Maneuvers deals 3 damage to target creature. It deals 5 damage instead if you control a Spacecraft.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(5), new DamageTargetEffect(3), condition,
                "{this} deals 3 damage to target creature. It deals 5 damage instead if you control a Spacecraft"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private InvasiveManeuvers(final InvasiveManeuvers card) {
        super(card);
    }

    @Override
    public InvasiveManeuvers copy() {
        return new InvasiveManeuvers(this);
    }
}
