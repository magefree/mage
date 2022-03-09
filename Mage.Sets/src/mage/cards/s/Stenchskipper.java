package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class Stenchskipper extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledCreaturePermanent(
                    SubType.GOBLIN, "if you control no Goblins"
            ), ComparisonType.FEWER_THAN, 1
    );

    public Stenchskipper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of the end step, if you control no Goblins, sacrifice Stenchskipper.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new SacrificeSourceEffect(),
                TargetController.NEXT, condition, false
        ));
    }

    private Stenchskipper(final Stenchskipper card) {
        super(card);
    }

    @Override
    public Stenchskipper copy() {
        return new Stenchskipper(this);
    }
}
