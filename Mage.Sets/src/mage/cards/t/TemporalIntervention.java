package mage.cards.t;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.VoidCondition;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;
import mage.watchers.common.VoidWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemporalIntervention extends CardImpl {

    public TemporalIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Void -- This spell costs {2} less to cast if a nonland permanent left the battlefield this turn or a spell was warped this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, VoidCondition.instance)
                .setText("this spell costs {2} less to cast if a nonland permanent " +
                        "left the battlefield this turn or a spell was warped this turn")
        ).setRuleAtTheTop(true).setAbilityWord(AbilityWord.VOID).addHint(VoidCondition.getHint()), new VoidWatcher());

        // Target opponent reveals their hand. You choose a nonland card from it. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_LAND));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private TemporalIntervention(final TemporalIntervention card) {
        super(card);
    }

    @Override
    public TemporalIntervention copy() {
        return new TemporalIntervention(this);
    }
}
