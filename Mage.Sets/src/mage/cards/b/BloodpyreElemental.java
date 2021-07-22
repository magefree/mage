
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class BloodpyreElemental extends CardImpl {

    public BloodpyreElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Sacrifice Bloodpyre Elemental: Bloodpyre Elemental deals 4 damage to target creature. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(4, "it"), new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BloodpyreElemental(final BloodpyreElemental card) {
        super(card);
    }

    @Override
    public BloodpyreElemental copy() {
        return new BloodpyreElemental(this);
    }
}
