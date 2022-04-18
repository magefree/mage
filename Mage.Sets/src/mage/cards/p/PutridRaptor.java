
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;

/**
 *
 * @author LoneFox
 */
public final class PutridRaptor extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Zombie card");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public PutridRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Morph-Discard a Zombie card.
        this.addAbility(new MorphAbility(new DiscardCardCost(filter)));
    }

    private PutridRaptor(final PutridRaptor card) {
        super(card);
    }

    @Override
    public PutridRaptor copy() {
        return new PutridRaptor(this);
    }
}
