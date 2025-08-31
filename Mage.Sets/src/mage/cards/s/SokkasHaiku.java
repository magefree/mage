package mage.cards.s;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetSpell;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SokkasHaiku extends CardImpl {

    public SokkasHaiku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        this.subtype.add(SubType.LESSON);

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // Draw a card, then mill three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
        this.getSpellAbility().addEffect(new MillCardsControllerEffect(3).concatBy(", then"));

        // Untap target land.
        this.getSpellAbility().addEffect(new UntapTargetEffect("untap target land")
                .concatBy("<br>")
                .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private SokkasHaiku(final SokkasHaiku card) {
        super(card);
    }

    @Override
    public SokkasHaiku copy() {
        return new SokkasHaiku(this);
    }
}
