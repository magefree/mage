

package mage.cards.y;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.cost.CastWithoutPayingManaCostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 * @author fireshoes
 */
public final class YahennisExpertise extends CardImpl {

    public YahennisExpertise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");


        // All creatures get -3/-3 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-3, -3, Duration.EndOfTurn));

        // You may cast a card with converted mana cost 3 or less from your hand without paying its mana cost.
        this.getSpellAbility().addEffect(new CastWithoutPayingManaCostEffect(3).concatBy("<br>"));
    }

    private YahennisExpertise(final YahennisExpertise card) {
        super(card);
    }

    @Override
    public YahennisExpertise copy() {
        return new YahennisExpertise(this);
    }
}
