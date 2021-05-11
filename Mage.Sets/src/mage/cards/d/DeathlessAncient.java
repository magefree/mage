
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author caldover
 */
public final class DeathlessAncient extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Vampires you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public DeathlessAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Tap three untapped Vampires you control: Return Deathless Ancient from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToHandEffect(),
                new TapTargetCost(new TargetControlledPermanent(3, 3, filter, true))));

    }

    private DeathlessAncient(final DeathlessAncient card) {
        super(card);
    }

    @Override
    public DeathlessAncient copy() {
        return new DeathlessAncient(this);
    }
}
