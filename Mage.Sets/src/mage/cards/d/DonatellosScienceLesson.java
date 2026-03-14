package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author muz
 */
public final class DonatellosScienceLesson extends CardImpl {

    public DonatellosScienceLesson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Tap up to two target creatures. Up to two target players each draw a card.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(1)
            .setTargetPointer(new SecondTargetPointer())
            .setText("Up to two target players each draw a card"));
        this.getSpellAbility().addTarget(new TargetPlayer(0, 2, false));
    }

    private DonatellosScienceLesson(final DonatellosScienceLesson card) {
        super(card);
    }

    @Override
    public DonatellosScienceLesson copy() {
        return new DonatellosScienceLesson(this);
    }
}
