
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class MoongloveExtract extends CardImpl {

    public MoongloveExtract(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Sacrifice Moonglove Extract: Moonglove Extract deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2, "it"), new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private MoongloveExtract(final MoongloveExtract card) {
        super(card);
    }

    @Override
    public MoongloveExtract copy() {
        return new MoongloveExtract(this);
    }
}
