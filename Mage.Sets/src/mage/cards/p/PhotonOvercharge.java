package mage.cards.p;

import java.util.UUID;

import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author NinthWorld
 */
public final class PhotonOvercharge extends CardImpl {

    public PhotonOvercharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");
        

        // Photon Overcharge deals damage to target attacking creature equal to the number of cards in your hand.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new CardsInControllerHandCount()));
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    public PhotonOvercharge(final PhotonOvercharge card) {
        super(card);
    }

    @Override
    public PhotonOvercharge copy() {
        return new PhotonOvercharge(this);
    }
}
