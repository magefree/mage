
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class MortusStrider extends CardImpl {

    public MortusStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{B}");
        this.subtype.add(SubType.SKELETON);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Mortus Strider dies, return it to its owner's hand.
        this.addAbility(new DiesSourceTriggeredAbility(new ReturnToHandSourceEffect(false)));
    }

    private MortusStrider(final MortusStrider card) {
        super(card);
    }

    @Override
    public MortusStrider copy() {
        return new MortusStrider(this);
    }
}
