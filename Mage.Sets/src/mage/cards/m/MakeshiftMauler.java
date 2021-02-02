
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author North
 */
public final class MakeshiftMauler extends CardImpl {

    public MakeshiftMauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // As an additional cost to cast Makeshift Mauler, exile a creature card from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)));
    }

    private MakeshiftMauler(final MakeshiftMauler card) {
        super(card);
    }

    @Override
    public MakeshiftMauler copy() {
        return new MakeshiftMauler(this);
    }
}
