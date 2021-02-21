
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author noxx
 */
public final class DemonicTaskmaster extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a creature other than Demonic Taskmaster");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public DemonicTaskmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, sacrifice a creature other than Demonic Taskmaster.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new SacrificeEffect(filter, 1, ""), TargetController.YOU, false);
        this.addAbility(ability);
    }

    private DemonicTaskmaster(final DemonicTaskmaster card) {
        super(card);
    }

    @Override
    public DemonicTaskmaster copy() {
        return new DemonicTaskmaster(this);
    }
}
