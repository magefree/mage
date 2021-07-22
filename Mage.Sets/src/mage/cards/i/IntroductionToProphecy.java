package mage.cards.i;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntroductionToProphecy extends CardImpl {

    public IntroductionToProphecy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}");

        this.subtype.add(SubType.LESSON);

        // Scry 2, then draw a card.
        this.getSpellAbility().addEffect(new ScryEffect(2, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
    }

    private IntroductionToProphecy(final IntroductionToProphecy card) {
        super(card);
    }

    @Override
    public IntroductionToProphecy copy() {
        return new IntroductionToProphecy(this);
    }
}
