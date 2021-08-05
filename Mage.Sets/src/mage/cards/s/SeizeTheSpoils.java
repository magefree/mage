package mage.cards.s;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeizeTheSpoils extends CardImpl {

    public SeizeTheSpoils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // As an additional cost to cast this spell, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost());

        // Draw two cards and create a Treasure token.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()).concatBy("and"));
    }

    private SeizeTheSpoils(final SeizeTheSpoils card) {
        super(card);
    }

    @Override
    public SeizeTheSpoils copy() {
        return new SeizeTheSpoils(this);
    }
}
