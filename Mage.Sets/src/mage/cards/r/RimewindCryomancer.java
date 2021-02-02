
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetActivatedAbility;

/**
 *
 * @author fireshoes
 */
public final class RimewindCryomancer extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control four or more snow permanents");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public RimewindCryomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {1}, {tap}: Counter target activated ability. Activate this ability only if you control four or more snow permanents.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD,
                new CounterTargetEffect(),
                new GenericManaCost(1),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetActivatedAbility());
        this.addAbility(ability);
    }

    private RimewindCryomancer(final RimewindCryomancer card) {
        super(card);
    }

    @Override
    public RimewindCryomancer copy() {
        return new RimewindCryomancer(this);
    }
}
