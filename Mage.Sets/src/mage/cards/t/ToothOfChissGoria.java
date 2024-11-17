
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class ToothOfChissGoria extends CardImpl {

    public ToothOfChissGoria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        this.addAbility(FlashAbility.getInstance());
        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());
        // {tap}: Target creature gets +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(1, 0, Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ToothOfChissGoria(final ToothOfChissGoria card) {
        super(card);
    }

    @Override
    public ToothOfChissGoria copy() {
        return new ToothOfChissGoria(this);
    }
}
