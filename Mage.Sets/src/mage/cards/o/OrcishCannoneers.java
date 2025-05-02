
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox

 */
public final class OrcishCannoneers extends CardImpl {

    public OrcishCannoneers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {tap}: Orcish Cannoneers deals 2 damage to any target and 3 damage to you.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new TapSourceCost());                                                                                         ability.addTarget(new TargetAnyTarget());
        Effect effect = new DamageControllerEffect(3);
        effect.setText("and 3 damage to you");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private OrcishCannoneers(final OrcishCannoneers card) {
        super(card);
    }

    @Override
    public OrcishCannoneers copy() {
        return new OrcishCannoneers(this);
    }
}
