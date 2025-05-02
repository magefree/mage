package mage.cards.k;

import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KirtarsWrath extends CardImpl {

    public KirtarsWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // Destroy all creatures. They can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, true));

        // Threshold - If seven or more cards are in your graveyard, instead destroy all creatures, then create two 1/1 white Spirit creature tokens with flying. Creatures destroyed this way can't be regenerated.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new SpiritWhiteToken(), 2),
                ThresholdCondition.instance, "<br>" + AbilityWord.THRESHOLD.formatWord() +
                "If seven or more cards are in your graveyard, instead destroy all creatures, " +
                "then create two 1/1 white Spirit creature tokens with flying. " +
                "Creatures destroyed this way can't be regenerated"
        ));
    }

    private KirtarsWrath(final KirtarsWrath card) {
        super(card);
    }

    @Override
    public KirtarsWrath copy() {
        return new KirtarsWrath(this);
    }
}
