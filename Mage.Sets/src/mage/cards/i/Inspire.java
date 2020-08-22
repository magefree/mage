package mage.cards.i;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class Inspire extends CardImpl {

    public Inspire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");
        

        // Untap target creature.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public Inspire(final Inspire card) {
        super(card);
    }

    @Override
    public Inspire copy() {
        return new Inspire(this);
    }
}
