
package mage.cards.h;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 * @author fireshoes
 */
public final class HanweirMilitiaCaptain extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("if you control four or more creatures");

    public HanweirMilitiaCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.w.WestvaleCultLeader.class;

        // At the beginning of your upkeep, if you control four or more creatures, transform Hanweir Militia Captain.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(), TargetController.YOU, false),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 3),
                "At the beginning of your upkeep, if you control four or more creatures, transform {this}"));
    }

    private HanweirMilitiaCaptain(final HanweirMilitiaCaptain card) {
        super(card);
    }

    @Override
    public HanweirMilitiaCaptain copy() {
        return new HanweirMilitiaCaptain(this);
    }
}
