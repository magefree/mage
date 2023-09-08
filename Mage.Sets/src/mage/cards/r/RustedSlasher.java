

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class RustedSlasher extends CardImpl {
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public RustedSlasher (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new SacrificeTargetCost(new TargetControlledPermanent(filter))));
    }

    private RustedSlasher(final RustedSlasher card) {
        super(card);
    }

    @Override
    public RustedSlasher copy() {
        return new RustedSlasher(this);
    }

}
