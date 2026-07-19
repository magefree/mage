package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class AtlasSizableStooge extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    private static final DynamicValue amount = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Creatures you control with power 4 or greater", amount);

    public AtlasSizableStooge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/G}{B/G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Atlas attacks or blocks, you gain 1 life for each creature you control with power 4 or greater.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(
            new GainLifeEffect(amount)
                .setText("you gain 1 life for each creature you control with power 4 or greater"),
            false
        ).addHint(hint));
    }

    private AtlasSizableStooge(final AtlasSizableStooge card) {
        super(card);
    }

    @Override
    public AtlasSizableStooge copy() {
        return new AtlasSizableStooge(this);
    }
}
