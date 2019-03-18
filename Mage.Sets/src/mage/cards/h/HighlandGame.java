
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class HighlandGame extends CardImpl {

    public HighlandGame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Highland Game dies, you gain 2 life.
        this.addAbility(new DiesTriggeredAbility(new GainLifeEffect(2)));
    }

    public HighlandGame(final HighlandGame card) {
        super(card);
    }

    @Override
    public HighlandGame copy() {
        return new HighlandGame(this);
    }
}
