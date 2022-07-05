
package mage.cards.v;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 * @author fireshoes
 */
public final class VoldarenPariah extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("three other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public VoldarenPariah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.a.AbolisherOfBloodlines.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sacrifice three other creatures: Transform Voldaren Pariah.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(3, 3, filter, false))));

        // Madness {B}{B}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{B}{B}{B}")));
    }

    private VoldarenPariah(final VoldarenPariah card) {
        super(card);
    }

    @Override
    public VoldarenPariah copy() {
        return new VoldarenPariah(this);
    }
}
