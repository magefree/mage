
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class GrindingStation extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an artifact");
    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public GrindingStation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {T}, Sacrifice an artifact: Target player puts the top three cards of their library into their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MillCardsTargetEffect(3), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Whenever an artifact enters the battlefield, you may untap Grinding Station.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new FilterArtifactPermanent("an artifact"), true));
        
    }

    public GrindingStation(final GrindingStation card) {
        super(card);
    }

    @Override
    public GrindingStation copy() {
        return new GrindingStation(this);
    }
}
