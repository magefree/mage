package mage.cards.v;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author muz
 */
public final class VisionsOfVillainy extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
        new FilterPermanent(SubType.VILLAIN, "you control a Villain")
    );
    private static final Hint hint = new ConditionHint(condition, "You control a Villain");

    public VisionsOfVillainy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // This spell costs {1} less to cast if you control a Villain.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL, new SpellCostReductionSourceEffect(1, condition)
        ).addHint(hint).setRuleAtTheTop(true));

        // You draw two cards and lose 2 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2, true));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and"));
    }

    private VisionsOfVillainy(final VisionsOfVillainy card) {
        super(card);
    }

    @Override
    public VisionsOfVillainy copy() {
        return new VisionsOfVillainy(this);
    }
}
