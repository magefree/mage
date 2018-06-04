
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author djbrez
 */
public final class ScreechingBuzzard extends CardImpl {

    public ScreechingBuzzard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Screeching Buzzard dies, each opponent discards a card.
        this.addAbility(new DiesTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT), false));
    }

    public ScreechingBuzzard(final ScreechingBuzzard card) {
        super(card);
    }

    @Override
    public ScreechingBuzzard copy() {
        return new ScreechingBuzzard(this);
    }
}
