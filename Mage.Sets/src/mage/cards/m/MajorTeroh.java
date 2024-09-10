
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LevelX2
 */
public final class MajorTeroh extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creatures");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public MajorTeroh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {3}{W}{W}, Sacrifice Major Teroh: Exile all black creatures.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileAllEffect(filter),new ManaCostsImpl<>("{3}{W}{W}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private MajorTeroh(final MajorTeroh card) {
        super(card);
    }

    @Override
    public MajorTeroh copy() {
        return new MajorTeroh(this);
    }
}
