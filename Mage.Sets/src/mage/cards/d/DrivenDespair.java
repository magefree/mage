
package mage.cards.d;

import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DrivenDespair extends SplitCard {

    public DrivenDespair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{1}{G}", "{1}{B}", SpellAbilityType.SPLIT_AFTERMATH);

        // Until end of turn, creatures you control gain trample and "Whenever this creature deals combat damage to a player, draw a card."
        this.getLeftHalfCard().getSpellAbility().addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("Until end of turn, creatures you control gain trample"));
        this.getLeftHalfCard().getSpellAbility().addEffect(new GainAbilityControlledEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), false),
                Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("\"Whenever this creature deals combat damage to a player, draw a card.\"").concatBy("and"));

        // Despair {1}{B}
        // Sorcery
        // Aftermath
        this.getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        // Until end of turn, creatures you control gain menace and "Whenever this creature deals combat damage to a player, that player discards a card."
        this.getRightHalfCard().getSpellAbility().addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("Until end of turn, creatures you control gain menace"));
        this.getRightHalfCard().getSpellAbility().addEffect(new GainAbilityControlledEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new DiscardTargetEffect(1), false, true
                ), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("\"Whenever this creature deals combat damage to a player, that player discards a card.\"").concatBy("and"));

    }

    private DrivenDespair(final DrivenDespair card) {
        super(card);
    }

    @Override
    public DrivenDespair copy() {
        return new DrivenDespair(this);
    }
}
