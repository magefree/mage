package mage.cards.h;

import mage.abilities.effects.common.CreateTokenControllerTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.Inkling11Token;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HarshAnnotation extends CardImpl {

    public HarshAnnotation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Destroy target creature. Its controller creates a 1/1 white and black Inkling creature token with flying.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetEffect(new Inkling11Token()));
    }

    private HarshAnnotation(final HarshAnnotation card) {
        super(card);
    }

    @Override
    public HarshAnnotation copy() {
        return new HarshAnnotation(this);
    }
}
