package mage.cards.e;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesAllBasicsControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Energybending extends CardImpl {

    public Energybending(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}");

        this.subtype.add(SubType.LESSON);

        // Lands you control gain all basic land types until end of turn.
        this.getSpellAbility().addEffect(new BecomesAllBasicsControlledEffect(Duration.EndOfTurn)
                .setText("lands you control gain all basic land types until end of turn"));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Energybending(final Energybending card) {
        super(card);
    }

    @Override
    public Energybending copy() {
        return new Energybending(this);
    }
}
