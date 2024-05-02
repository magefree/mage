package mage.cards.p;

import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public final class PredatorsHour extends CardImpl {

    public PredatorsHour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Until end of turn, creatures you control gain menace
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                        new MenaceAbility(false),
                        Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURES
                ).setText("Until end of turn, creatures you control gain menace")
        );

        // and “Whenever this creature deals combat damage to a player,
        // exile the top card of that player's library face down.
        // You may look at and play that card for as long as it remains exiled,
        // and you may spend mana as though it were mana of any color to cast that spell.”
        this.getSpellAbility().addEffect(
                new GainAbilityControlledEffect(
                        new DealsCombatDamageToAPlayerTriggeredAbility(
                                new ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(false, CastManaAdjustment.AS_THOUGH_ANY_MANA_COLOR)
                                        .setText("exile the top card of that player's library face down. "
                                                + "You may look at and play that card for as long as it remains exiled, "
                                                + "and you may spend mana as though it were mana of any color to cast that spell."),
                                false, true),
                        Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURES
                ).setText("\"Whenever this creature deals combat damage to a player, " +
                        "exile the top card of that player's library face down. " +
                        "You may look at and play that card for as long as it remains exiled, " +
                        "and you may spend mana as though it were mana of any color to cast that spell.\""
                ).concatBy("and")
        );
    }

    private PredatorsHour(final PredatorsHour card) {
        super(card);
    }

    @Override
    public PredatorsHour copy() {
        return new PredatorsHour(this);
    }
}