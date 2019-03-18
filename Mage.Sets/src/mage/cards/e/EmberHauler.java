

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class EmberHauler extends CardImpl {

    public EmberHauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new SacrificeSourceCost());
        ability.addManaCost(new GenericManaCost(1));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public EmberHauler(final EmberHauler card) {
        super(card);
    }

    @Override
    public EmberHauler copy() {
        return new EmberHauler(this);
    }

}
