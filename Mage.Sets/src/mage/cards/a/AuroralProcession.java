package mage.cards.a;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AuroralProcession extends CardImpl {

    public AuroralProcession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{U}");

        // Return target card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard());
    }

    private AuroralProcession(final AuroralProcession card) {
        super(card);
    }

    @Override
    public AuroralProcession copy() {
        return new AuroralProcession(this);
    }
}
