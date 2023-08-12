
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class HeidarRimewindMaster extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control four or more snow permanents");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public HeidarRimewindMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}, {tap}: Return target permanent to its owner's hand. Activate this ability only if you control four or more snow permanents.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD,
                new ReturnToHandTargetEffect(),
                new GenericManaCost(2),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private HeidarRimewindMaster(final HeidarRimewindMaster card) {
        super(card);
    }

    @Override
    public HeidarRimewindMaster copy() {
        return new HeidarRimewindMaster(this);
    }
}
