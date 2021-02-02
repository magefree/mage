
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Alvin
 */
public final class SkaabGoliath extends CardImpl {

    public SkaabGoliath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(9);

        // As an additional cost to cast Skaab Goliath, exile two creature cards from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD)));
        this.addAbility(TrampleAbility.getInstance());
    }

    private SkaabGoliath(final SkaabGoliath card) {
        super(card);
    }

    @Override
    public SkaabGoliath copy() {
        return new SkaabGoliath(this);
    }
}
