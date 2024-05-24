package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class GangrenousGoliath extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.CLERIC,"untapped Clerics you control");

    public GangrenousGoliath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Tap three untapped Clerics you control: Return Gangrenous Goliath from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToHandEffect(),
                new TapTargetCost(new TargetControlledPermanent(3, 3, filter, true))));
    }

    private GangrenousGoliath(final GangrenousGoliath card) {
        super(card);
    }

    @Override
    public GangrenousGoliath copy() {
        return new GangrenousGoliath(this);
    }
}
