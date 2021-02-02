
package mage.cards.d;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
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

/**
 *
 * @author LevelX2
 */
public final class DrivenDespair extends SplitCard {

    public DrivenDespair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{1}{G}", "{1}{B}", SpellAbilityType.SPLIT_AFTERMATH);

        // Until end of turn, creatures you control gain trample and "Whenever this creature deals combat damage to a player, draw a card."
        getLeftHalfCard().getSpellAbility().addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES)
                .setText("Until end of turn, creatures you control gain trample"));
        TriggeredAbility ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), false);
        getLeftHalfCard().getSpellAbility().addEffect(new GainAbilityControlledEffect(ability, Duration.EndOfTurn)
                .setText("\"Whenever this creature deals combat damage to a player, draw a card.\"")
                .concatBy("and"));

        // Despair {1}{B}
        // Sorcery
        // Aftermath
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        // Until end of turn, creatures you control gain menace and "Whenever this creature deals combat damage to a player, that player discards a card."
        getRightHalfCard().getSpellAbility().addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES)
                .setText("Until end of turn, creatures you control gain menace"));
        ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DiscardTargetEffect(1), false, true);
        getRightHalfCard().getSpellAbility().addEffect(new GainAbilityControlledEffect(ability, Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES)
                .setText("\"Whenever this creature deals combat damage to a player, that player discards a card.\"")
                .concatBy("and"));

    }

    private DrivenDespair(final DrivenDespair card) {
        super(card);
    }

    @Override
    public DrivenDespair copy() {
        return new DrivenDespair(this);
    }
}
