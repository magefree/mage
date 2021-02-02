
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author fireshoes
 */
public final class ShriekingSpecter extends CardImpl {

    public ShriekingSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever Shrieking Specter attacks, defending player discards a card.
        this.addAbility(new AttacksTriggeredAbility(new DiscardTargetEffect(1), false, "Whenever {this} attacks, defending player discards a card.", SetTargetPointer.PLAYER));
    }

    private ShriekingSpecter(final ShriekingSpecter card) {
        super(card);
    }

    @Override
    public ShriekingSpecter copy() {
        return new ShriekingSpecter(this);
    }
}
