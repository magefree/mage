
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class DeadbridgeShaman extends CardImpl {

    public DeadbridgeShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Deadbridge Shaman dies, target opponent discards a card.
        Ability ability = new DiesSourceTriggeredAbility(new DiscardTargetEffect(1));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private DeadbridgeShaman(final DeadbridgeShaman card) {
        super(card);
    }

    @Override
    public DeadbridgeShaman copy() {
        return new DeadbridgeShaman(this);
    }
}
