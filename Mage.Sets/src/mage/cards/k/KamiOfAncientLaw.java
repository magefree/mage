

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author Loki
 */
public final class KamiOfAncientLaw extends CardImpl {

    public KamiOfAncientLaw (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
    }

    private KamiOfAncientLaw(final KamiOfAncientLaw card) {
        super(card);
    }

    @Override
    public KamiOfAncientLaw copy() {
        return new KamiOfAncientLaw(this);
    }

}
