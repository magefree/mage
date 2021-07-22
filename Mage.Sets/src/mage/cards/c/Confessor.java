
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.DiscardCardPlayerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author cbt33
 */
public final class Confessor extends CardImpl {

    public Confessor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a player discards a card, you may gain 1 life.
        this.addAbility(new DiscardCardPlayerTriggeredAbility(new GainLifeEffect(1), true));
    }

    private Confessor(final Confessor card) {
        super(card);
    }

    @Override
    public Confessor copy() {
        return new Confessor(this);
    }
}
