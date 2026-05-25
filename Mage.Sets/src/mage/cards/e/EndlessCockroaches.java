package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class EndlessCockroaches extends CardImpl {

    public EndlessCockroaches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Endless Cockroaches dies, return it to its owner's hand.
        this.addAbility(new DiesSourceTriggeredAbility(new ReturnToHandTargetEffect()
                .setText("return it to its owner's hand"), false, SetTargetPointer.CARD));
    }

    private EndlessCockroaches(final EndlessCockroaches card) {
        super(card);
    }

    @Override
    public EndlessCockroaches copy() {
        return new EndlessCockroaches(this);
    }
}
