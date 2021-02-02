
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SigiledSkink extends CardImpl {

    public SigiledSkink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.LIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Sigiled Skink attacks, scry 1.
        this.addAbility(new AttacksTriggeredAbility(new ScryEffect(1), false));
    }

    private SigiledSkink(final SigiledSkink card) {
        super(card);
    }

    @Override
    public SigiledSkink copy() {
        return new SigiledSkink(this);
    }
}
