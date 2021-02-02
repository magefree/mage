
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class VeilbornGhoul extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("a Swamp");
    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    public VeilbornGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Veilborn Ghoul can't block.
        this.addAbility(new CantBlockAbility());
        
        // Whenever a Swamp enters the battlefield under your control, you may return Veilborn Ghoul from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.GRAVEYARD,  new ReturnSourceFromGraveyardToHandEffect(), filter, true));

    }

    private VeilbornGhoul(final VeilbornGhoul card) {
        super(card);
    }

    @Override
    public VeilbornGhoul copy() {
        return new VeilbornGhoul(this);
    }
}
