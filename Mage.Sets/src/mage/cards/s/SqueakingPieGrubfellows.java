
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.KinshipAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class SqueakingPieGrubfellows extends CardImpl {

    public SqueakingPieGrubfellows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Kinship - At the beginning of your upkeep, you may look at the top card of your library. If it shares a creature type with Squeaking Pie Grubfellows, you may reveal it.
        // If you do, each opponent discards a card.
        this.addAbility(new KinshipAbility(new DiscardEachPlayerEffect(StaticValue.get(1), false, TargetController.OPPONENT)));
    }

    private SqueakingPieGrubfellows(final SqueakingPieGrubfellows card) {
        super(card);
    }

    @Override
    public SqueakingPieGrubfellows copy() {
        return new SqueakingPieGrubfellows(this);
    }
}
