
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Loki
 */
public final class HeadlessSkaab extends CardImpl {

    public HeadlessSkaab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // As an additional cost to cast Headless Skaab, exile a creature card from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)));
        // Headless Skaab enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private HeadlessSkaab(final HeadlessSkaab card) {
        super(card);
    }

    @Override
    public HeadlessSkaab copy() {
        return new HeadlessSkaab(this);
    }
}
