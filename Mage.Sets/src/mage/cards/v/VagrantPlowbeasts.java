
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class VagrantPlowbeasts extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 5 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public VagrantPlowbeasts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{W}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new RegenerateTargetEffect(),
                new ManaCostsImpl<>("{1}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private VagrantPlowbeasts(final VagrantPlowbeasts card) {
        super(card);
    }

    @Override
    public VagrantPlowbeasts copy() {
        return new VagrantPlowbeasts(this);
    }
}
