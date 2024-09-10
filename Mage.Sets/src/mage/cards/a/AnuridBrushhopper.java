
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.ExileReturnBattlefieldOwnerNextEndStepSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public final class AnuridBrushhopper extends CardImpl {

    public AnuridBrushhopper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{W}");
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Discard two cards: Exile Anurid Brushhopper. Return it to the battlefield under its owner's control at the beginning of the next end step.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ExileReturnBattlefieldOwnerNextEndStepSourceEffect(),
                new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS))));
    }

    private AnuridBrushhopper(final AnuridBrushhopper card) {
        super(card);
    }

    @Override
    public AnuridBrushhopper copy() {
        return new AnuridBrushhopper(this);
    }
}
