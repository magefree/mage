package mage.cards.q;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuarrelsEnd extends CardImpl {

    public QuarrelsEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // As an additional cost to cast this spell, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost());

        // Draw two cards and create a 1/1 white Human Soldier creature token.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new HumanSoldierToken()).concatBy("and"));
    }

    private QuarrelsEnd(final QuarrelsEnd card) {
        super(card);
    }

    @Override
    public QuarrelsEnd copy() {
        return new QuarrelsEnd(this);
    }
}
