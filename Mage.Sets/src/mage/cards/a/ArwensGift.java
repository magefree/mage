package mage.cards.a;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArwensGift extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("you control two or more legendary creatures");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1);
    private static final Hint hint
            = new ConditionHint(condition, "You control two or more legendary creatures");

    public ArwensGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // This spell costs {1} less to cast if you control two or more legendary creatures.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(1, condition)
        ).setRuleAtTheTop(true).addHint(hint));

        // Scry 2, then draw two cards.
        this.getSpellAbility().addEffect(new ScryEffect(2, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"));
    }

    private ArwensGift(final ArwensGift card) {
        super(card);
    }

    @Override
    public ArwensGift copy() {
        return new ArwensGift(this);
    }
}
