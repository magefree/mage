package mage.cards.n;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DamageTargetAndTargetControllerEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NeonatesRush extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterPermanent(SubType.VAMPIRE, "you control a Vampire")
    );
    private static final Hint hint = new ConditionHint(condition, "You control a Vampire");

    public NeonatesRush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // This spell costs {1} less to cast if you control a Vampire.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(1, condition)
        ).addHint(hint).setRuleAtTheTop(true));

        // Neonate's Rush deals 1 damage to target creature and 1 damage to its controller. Draw a card.
        this.getSpellAbility().addEffect(new DamageTargetAndTargetControllerEffect(1, 1)
                .setText("{this} deals 1 damage to target creature and 1 damage to its controller"));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private NeonatesRush(final NeonatesRush card) {
        super(card);
    }

    @Override
    public NeonatesRush copy() {
        return new NeonatesRush(this);
    }
}
