
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author Loki
 */
public final class HarpoonSniper extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Merfolk you control");

    static {
        filter.add(SubType.MERFOLK.getPredicate());
    }

    public HarpoonSniper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter))
                .setText("{this} deals X damage to target attacking or blocking creature, where X is the number of Merfolk you control"),
                new ColoredManaCost(ColoredManaSymbol.W));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAttackingOrBlockingCreature());
        this.addAbility(ability);
    }

    private HarpoonSniper(final HarpoonSniper card) {
        super(card);
    }

    @Override
    public HarpoonSniper copy() {
        return new HarpoonSniper(this);
    }
}
