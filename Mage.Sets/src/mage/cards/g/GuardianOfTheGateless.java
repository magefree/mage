package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;

/**
 *
 * @author LevelX2
 */
public final class GuardianOfTheGateless extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature it's blocking");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKED_BY);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public GuardianOfTheGateless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Guardian of the Gateless can block any number of creatures.
        this.addAbility(new SimpleStaticAbility(new CanBlockAdditionalCreatureEffect(0)));

        // Whenever Guardian of the Gateless blocks, it gets +1/+1 until end of turn for each creature it's blocking.
        this.addAbility(new BlocksSourceTriggeredAbility(
                new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn, true, "it")));
    }

    private GuardianOfTheGateless(final GuardianOfTheGateless card) {
        super(card);
    }

    @Override
    public GuardianOfTheGateless copy() {
        return new GuardianOfTheGateless(this);
    }
}
