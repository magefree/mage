package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DraconicLore extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.DRAGON, "you control a Dragon")
    );
    private static final Hint hint = new ConditionHint(condition, "You control a Dragon");

    public DraconicLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{U}");

        // This spell costs {2} less to cast if you control a Dragon.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(2, condition));
        ability.setRuleAtTheTop(true);
        ability.addHint(hint);
        this.addAbility(ability);

        // Draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
    }

    private DraconicLore(final DraconicLore card) {
        super(card);
    }

    @Override
    public DraconicLore copy() {
        return new DraconicLore(this);
    }
}
