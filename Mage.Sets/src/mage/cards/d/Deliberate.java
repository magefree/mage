package mage.cards.d;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Deliberate extends CardImpl {

    public Deliberate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Scry 2, then draw a card.
        this.getSpellAbility().addEffect(new ScryEffect(2).setText("scry 2"));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
    }

    private Deliberate(final Deliberate card) {
        super(card);
    }

    @Override
    public Deliberate copy() {
        return new Deliberate(this);
    }
}
