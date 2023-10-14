package mage.cards.g;

import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ElephantToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GenerousGift extends CardImpl {

    public GenerousGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Destroy target permanent. Its controller creates a 3/3 green Elephant creature token.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetPermanentEffect(new ElephantToken()));
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private GenerousGift(final GenerousGift card) {
        super(card);
    }

    @Override
    public GenerousGift copy() {
        return new GenerousGift(this);
    }
}