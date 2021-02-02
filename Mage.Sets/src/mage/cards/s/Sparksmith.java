
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class Sparksmith extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Goblins on the battlefield");
    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }
    
    public Sparksmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Sparksmith deals X damage to target creature and X damage to you, where X is the number of Goblins on the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter)), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new DamageControllerEffect(new PermanentsOnBattlefieldCount(filter)));
        this.addAbility(ability);
    }

    private Sparksmith(final Sparksmith card) {
        super(card);
    }

    @Override
    public Sparksmith copy() {
        return new Sparksmith(this);
    }
}
