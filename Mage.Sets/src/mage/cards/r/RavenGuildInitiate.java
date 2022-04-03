
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.constants.SubType;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class RavenGuildInitiate extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Bird");

    static {
        filter.add(SubType.BIRD.getPredicate());
    }

    public RavenGuildInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Morph-Return a Bird you control to its owner's hand.
        this.addAbility(new MorphAbility(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter))));

    }

    private RavenGuildInitiate(final RavenGuildInitiate card) {
        super(card);
    }

    @Override
    public RavenGuildInitiate copy() {
        return new RavenGuildInitiate(this);
    }
}
