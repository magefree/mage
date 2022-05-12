

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class StunSniper extends CardImpl {

    public StunSniper (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);


        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new TapTargetEffect().setText("tap that creature"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public StunSniper (final StunSniper card) {
        super(card);
    }

    @Override
    public StunSniper copy() {
        return new StunSniper(this);
    }

}
