package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SanguineIndulgence extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 2);
    private static final Hint hint = new ConditionHint(condition, "You gained 3 or more life this turn");

    public SanguineIndulgence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // This spell costs {3} less to cast if you've gained 3 or more life this turn.
        this.addAbility(new SimpleStaticAbility(
                        Zone.ALL, new SpellCostReductionSourceEffect(3, condition)
                        .setText("this spell costs {3} less to cast if you've gained 3 or more life this turn")
                ).addHint(hint).setRuleAtTheTop(true),
                new PlayerGainedLifeWatcher());

        // Return up to two target creature cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD
        ));
    }

    private SanguineIndulgence(final SanguineIndulgence card) {
        super(card);
    }

    @Override
    public SanguineIndulgence copy() {
        return new SanguineIndulgence(this);
    }
}
