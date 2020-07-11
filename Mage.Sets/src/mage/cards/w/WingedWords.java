package mage.cards.w;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WingedWords extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("you control a creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public WingedWords(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // This spell costs {1} less to cast if you control a creature with flying.
        Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionSourceEffect(1, condition))
                .setRuleAtTheTop(true)
                .addHint(new ConditionHint(condition, "You control a creature with flying")));

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private WingedWords(final WingedWords card) {
        super(card);
    }

    @Override
    public WingedWords copy() {
        return new WingedWords(this);
    }
}
