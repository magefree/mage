
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author LoneFox
 */
public final class HydromorphGull extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell that targets a creature you control");

    static {
        filter.add(new TargetsPermanentPredicate(new FilterControlledCreaturePermanent()));
    }

    public HydromorphGull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {U}, Sacrifice Hydromorph Gull: Counter target spell that targets one or more creatures you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ColoredManaCost(ColoredManaSymbol.U));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private HydromorphGull(final HydromorphGull card) {
        super(card);
    }

    @Override
    public HydromorphGull copy() {
        return new HydromorphGull(this);
    }
}
