
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author North
 */
public final class HamletCaptain extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Humans");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    public HamletCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Hamlet Captain attacks or blocks, other Human creatures you control get +1/+1 until end of turn.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn, filter, true), false));
    }

    private HamletCaptain(final HamletCaptain card) {
        super(card);
    }

    @Override
    public HamletCaptain copy() {
        return new HamletCaptain(this);
    }
}
