
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class SeaGateLoremaster extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Ally you control");

    static {
        filter.add(SubType.ALLY.getPredicate());
    }

    public SeaGateLoremaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Draw a card for each Ally you control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)),
                new TapSourceCost()));
    }

    private SeaGateLoremaster(final SeaGateLoremaster card) {
        super(card);
    }

    @Override
    public SeaGateLoremaster copy() {
        return new SeaGateLoremaster(this);
    }
}
