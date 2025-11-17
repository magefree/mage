package mage.cards.s;

import mage.abilities.condition.common.WaterbendedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ShuffleYourGraveyardIntoLibraryEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.keyword.WaterbendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiritWaterRevival extends CardImpl {

    public SpiritWaterRevival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{U}");

        // As an additional cost to cast this spell, you may waterbend {6}.
        this.addAbility(new WaterbendAbility(6));

        // Draw two cards. If this spell's additional cost was paid, instead shuffle your graveyard into your library, draw seven cards, and you have no maximum hand size for the rest of the game.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ShuffleYourGraveyardIntoLibraryEffect(), new DrawCardSourceControllerEffect(2),
                WaterbendedCondition.instance, "draw two cards. If this spell's additional cost was paid, " +
                "instead shuffle your graveyard into your library, draw seven cards, " +
                "and you have no maximum hand size for the rest of the game"
        ).addEffect(new DrawCardSourceControllerEffect(7))
                .addEffect(new AddContinuousEffectToGame(new MaximumHandSizeControllerEffect(
                        Integer.MAX_VALUE, Duration.EndOfGame, MaximumHandSizeControllerEffect.HandSizeModification.SET
                ))));

        // Exile Spirit Water Revival.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private SpiritWaterRevival(final SpiritWaterRevival card) {
        super(card);
    }

    @Override
    public SpiritWaterRevival copy() {
        return new SpiritWaterRevival(this);
    }
}
