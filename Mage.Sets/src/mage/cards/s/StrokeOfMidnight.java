package mage.cards.s;

import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.HumanToken;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StrokeOfMidnight extends CardImpl {

    public StrokeOfMidnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");


        // Destroy target nonland permanent. Its controller creates a 1/1 white Human creature token.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetPermanentEffect(new HumanToken()));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private StrokeOfMidnight(final StrokeOfMidnight card) {
        super(card);
    }

    @Override
    public StrokeOfMidnight copy() {
        return new StrokeOfMidnight(this);
    }
}