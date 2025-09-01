package mage.cards.d;

import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.GiftAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.GiftType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawnsTruce extends CardImpl {

    public DawnsTruce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Gift a card
        this.addAbility(new GiftAbility(this, GiftType.CARD));

        // You and permanents you control gain hexproof until end of turn. If the gift was promised, permanents you control also gain indestructible until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControllerEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn
        ).setText("you"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENTS
        ).concatBy("and"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new GainAbilityControlledEffect(
                        IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENTS
                )), GiftWasPromisedCondition.TRUE, "if the gift was promised, " +
                "permanents you control also gain indestructible until end of turn"
        ));
    }

    private DawnsTruce(final DawnsTruce card) {
        super(card);
    }

    @Override
    public DawnsTruce copy() {
        return new DawnsTruce(this);
    }
}
