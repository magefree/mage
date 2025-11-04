package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheBoulderReadyToRumble extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creatures you control with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Creatures you control with power 4 or greater", xValue);

    public TheBoulderReadyToRumble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.PERFORMER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever The Boulder attacks, earthbend X, where X is the number of creatures you control with power 4 or greater.
        Ability ability = new AttacksTriggeredAbility(new EarthbendTargetEffect(xValue));
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability.addHint(hint));
    }

    private TheBoulderReadyToRumble(final TheBoulderReadyToRumble card) {
        super(card);
    }

    @Override
    public TheBoulderReadyToRumble copy() {
        return new TheBoulderReadyToRumble(this);
    }
}
