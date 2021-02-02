package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.JumpStartAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class ChemistersInsight extends CardImpl {

    public ChemistersInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));

        // Jump-start
        this.addAbility(new JumpStartAbility(this));
    }

    private ChemistersInsight(final ChemistersInsight card) {
        super(card);
    }

    @Override
    public ChemistersInsight copy() {
        return new ChemistersInsight(this);
    }
}
