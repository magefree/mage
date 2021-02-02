
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author LevelX2
 */
public final class FumikoTheLowblood extends CardImpl {

    public FumikoTheLowblood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Fumiko the Lowblood has bushido X, where X is the number of attacking creatures.
        this.addAbility(new BushidoAbility(new AttackingCreatureCount("the number of attacking creatures.")));

        // Creatures your opponents control attack each turn if able.
        FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures your opponents control");
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AttacksIfAbleAllEffect(filter)), new AttackedThisTurnWatcher());

    }

    private FumikoTheLowblood(final FumikoTheLowblood card) {
        super(card);
    }

    @Override
    public FumikoTheLowblood copy() {
        return new FumikoTheLowblood(this);
    }
}
