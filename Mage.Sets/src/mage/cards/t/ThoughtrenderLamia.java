
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.ConstellationAbility;
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
public final class ThoughtrenderLamia extends CardImpl {

    public ThoughtrenderLamia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.LAMIA);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Constellation - Whenever Thoughtrender Lamia or another enchantment enters the battlefield under your control, each opponent discards a card.
        this.addAbility(new ConstellationAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT)));
    }

    private ThoughtrenderLamia(final ThoughtrenderLamia card) {
        super(card);
    }

    @Override
    public ThoughtrenderLamia copy() {
        return new ThoughtrenderLamia(this);
    }
}
