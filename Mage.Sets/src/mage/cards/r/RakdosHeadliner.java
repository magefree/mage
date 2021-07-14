package mage.cards.r;

import mage.MageInt;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RakdosHeadliner extends CardImpl {

    public RakdosHeadliner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Echo—Discard a card.
        this.addAbility(new EchoAbility(new DiscardCardCost()));
    }

    private RakdosHeadliner(final RakdosHeadliner card) {
        super(card);
    }

    @Override
    public RakdosHeadliner copy() {
        return new RakdosHeadliner(this);
    }
}
