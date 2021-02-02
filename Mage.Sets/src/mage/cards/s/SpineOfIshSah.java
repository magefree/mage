
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public final class SpineOfIshSah extends CardImpl {

    public SpineOfIshSah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{7}");

        // When Spine of Ish Sah enters the battlefield, destroy target permanent.        
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
        
        // When Spine of Ish Sah is put into a graveyard from the battlefield, return Spine of Ish Sah to its owner's hand
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ReturnToHandSourceEffect()));
    }

    private SpineOfIshSah(final SpineOfIshSah card) {
        super(card);
    }

    @Override
    public SpineOfIshSah copy() {
        return new SpineOfIshSah(this);
    }
}
