package mage.cards.e;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Everdream extends CardImpl {

    public Everdream(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

        // Splice onto instant or sorcery {2}{U}
        this.addAbility(new SpliceAbility(SpliceAbility.INSTANT_OR_SORCERY, "{2}{U}"));
    }

    private Everdream(final Everdream card) {
        super(card);
    }

    @Override
    public Everdream copy() {
        return new Everdream(this);
    }
}
