
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author TaVSt
 */
public final class HydromorphGuardian extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell that targets a creature you control");

    static {
        filter.add(new TargetsPermanentPredicate(new FilterControlledCreaturePermanent()));
    }

    public HydromorphGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {U}, Sacrifice Hydromorph Guardian: Counter target spell that targets one or more creatures you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ColoredManaCost(ColoredManaSymbol.U));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private HydromorphGuardian(final HydromorphGuardian card) {
        super(card);
    }

    @Override
    public HydromorphGuardian copy() {
        return new HydromorphGuardian(this);
    }
}
