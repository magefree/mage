
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author cbt33
 */
public final class LieutenantKirtar extends CardImpl {

    public LieutenantKirtar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {1}{W}, Sacrifice Lieutenant Kirtar: Exile target attacking creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetAttackingCreature());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private LieutenantKirtar(final LieutenantKirtar card) {
        super(card);
    }

    @Override
    public LieutenantKirtar copy() {
        return new LieutenantKirtar(this);
    }
}
