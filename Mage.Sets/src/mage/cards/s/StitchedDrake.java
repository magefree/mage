
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author nantuko
 */
public final class StitchedDrake extends CardImpl {

    public StitchedDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());

        // As an additional cost to cast Stitched Drake, exile a creature card from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)));
    }

    private StitchedDrake(final StitchedDrake card) {
        super(card);
    }

    @Override
    public StitchedDrake copy() {
        return new StitchedDrake(this);
    }
}
