package mage.cards.i;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Spirit32Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IlluminateHistory extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(7);

    public IlluminateHistory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        this.subtype.add(SubType.LESSON);

        // Discard any number of cards, then draw that many cards. Then if there are seven or more cards in your graveyard, create a 3/2 red and white Spirit creature token.
        this.getSpellAbility().addEffect(new DiscardAndDrawThatManyEffect(Integer.MAX_VALUE));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new Spirit32Token()), condition, "Then if there are seven or more " +
                "cards in your graveyard, create a 3/2 red and white Spirit creature token"
        ));
    }

    private IlluminateHistory(final IlluminateHistory card) {
        super(card);
    }

    @Override
    public IlluminateHistory copy() {
        return new IlluminateHistory(this);
    }
}
