
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.keyword.UndyingAbility;
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
public final class RelentlessSkaabs extends CardImpl {

    public RelentlessSkaabs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As an additional cost to cast Relentless Skaabs, exile a creature card from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)));
        // Undying
        this.addAbility(new UndyingAbility());
    }

    private RelentlessSkaabs(final RelentlessSkaabs card) {
        super(card);
    }

    @Override
    public RelentlessSkaabs copy() {
        return new RelentlessSkaabs(this);
    }
}
