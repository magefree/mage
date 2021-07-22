package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZombieOgre extends CardImpl {

    public ZombieOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // At the beginning of your end step, if a creature died this turn, venture into the dungeon.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new VentureIntoTheDungeonEffect(),
                TargetController.YOU, MorbidCondition.instance, false
        ).addHint(MorbidHint.instance));
    }

    private ZombieOgre(final ZombieOgre card) {
        super(card);
    }

    @Override
    public ZombieOgre copy() {
        return new ZombieOgre(this);
    }
}
