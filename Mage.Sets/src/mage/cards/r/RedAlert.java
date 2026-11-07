package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureAttackingYou;

import java.util.UUID;

/**
 * @author muz
 */
public final class RedAlert extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
        new FilterCreatureAttackingYou("a creature is attacking you"), false
    );

    public RedAlert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // This spell costs {2} less to cast if a creature is attacking you.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(2, condition))
            .setRuleAtTheTop(true));

        // Creatures you control get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));
    }

    private RedAlert(final RedAlert card) {
        super(card);
    }

    @Override
    public RedAlert copy() {
        return new RedAlert(this);
    }
}
