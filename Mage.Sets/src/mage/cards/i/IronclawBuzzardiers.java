
package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBlockCreaturesSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 *
 * @author dustinconrad
 */
public final class IronclawBuzzardiers extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 2 or greater");
    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 1));
    }

    public IronclawBuzzardiers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ironclaw Buzzardiers can't block creatures with power 2 or greater.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockCreaturesSourceEffect(filter)));
        // {R}: Ironclaw Buzzardiers gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));
    }

    private IronclawBuzzardiers(final IronclawBuzzardiers card) {
        super(card);
    }

    @Override
    public IronclawBuzzardiers copy() {
        return new IronclawBuzzardiers(this);
    }
}
