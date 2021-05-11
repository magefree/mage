
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public final class LodestoneMyr extends CardImpl {

    private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("untapped artifact you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public LodestoneMyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new TapTargetCost(new TargetControlledPermanent(filter))));
    }

    private LodestoneMyr(final LodestoneMyr card) {
        super(card);
    }

    @Override
    public LodestoneMyr copy() {
        return new LodestoneMyr(this);
    }
}
