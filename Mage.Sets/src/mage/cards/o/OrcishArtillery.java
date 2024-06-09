
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
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
 * @author Plopman
 */
public final class OrcishArtillery extends CardImpl {

    public OrcishArtillery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {tap}: Orcish Artillery deals 2 damage to any target and 3 damage to you.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        ability.addEffect(new DamageControllerEffect(3).setText("and 3 damage to you"));
        this.addAbility(ability);
    }

    private OrcishArtillery(final OrcishArtillery card) {
        super(card);
    }

    @Override
    public OrcishArtillery copy() {
        return new OrcishArtillery(this);
    }
}
