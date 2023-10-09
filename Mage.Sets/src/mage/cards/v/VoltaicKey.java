package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class VoltaicKey extends CardImpl {

    public VoltaicKey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new TapSourceCost());
        ability.addCost(new GenericManaCost(1));
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private VoltaicKey(final VoltaicKey card) {
        super(card);
    }

    @Override
    public VoltaicKey copy() {
        return new VoltaicKey(this);
    }

}
