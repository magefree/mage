
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class DrownerOfSecrets extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Merfolk you control");

    static {
        filter.add(SubType.MERFOLK.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public DrownerOfSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Tap an untapped Merfolk you control: Target player puts the top card of their library into their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MillCardsTargetEffect(1), new TapTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private DrownerOfSecrets(final DrownerOfSecrets card) {
        super(card);
    }

    @Override
    public DrownerOfSecrets copy() {
        return new DrownerOfSecrets(this);
    }
}
