package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.SetBaseToughnessSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WintermoorCommander extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.KNIGHT, "Knights you control");
    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(filter);
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.KNIGHT, "another target Knight you control");

    static {
        filter2.add(AnotherPredicate.instance);
    }

    public WintermoorCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(0);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Wintermoor Commander's toughness is equal to the number of Knights you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBaseToughnessSourceEffect(xValue)));

        // Whenever Wintermoor Commander attacks, another target Knight you control gains indestructible until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), false);
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private WintermoorCommander(final WintermoorCommander card) {
        super(card);
    }

    @Override
    public WintermoorCommander copy() {
        return new WintermoorCommander(this);
    }
}
