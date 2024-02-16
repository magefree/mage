package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HonorWornShaku extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("untapped legendary permanent you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public HonorWornShaku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.addAbility(new ColorlessManaAbility());
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new UntapSourceEffect(),
                new TapTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private HonorWornShaku(final HonorWornShaku card) {
        super(card);
    }

    @Override
    public HonorWornShaku copy() {
        return new HonorWornShaku(this);
    }
}
