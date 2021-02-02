
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInGraveyard;

/**
 * @author noxx
 */
public final class VesselOfEndlessRest extends CardImpl {

    public VesselOfEndlessRest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // When Vessel of Endless Rest enters the battlefield, put target card from a graveyard on the bottom of its owner's library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PutOnLibraryTargetEffect(false), false);
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);

        // {tap}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private VesselOfEndlessRest(final VesselOfEndlessRest card) {
        super(card);
    }

    @Override
    public VesselOfEndlessRest copy() {
        return new VesselOfEndlessRest(this);
    }
}
