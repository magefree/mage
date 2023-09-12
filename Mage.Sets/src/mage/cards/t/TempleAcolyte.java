

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class TempleAcolyte extends CardImpl {

    public TempleAcolyte (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));
    }

    private TempleAcolyte(final TempleAcolyte card) {
        super(card);
    }

    @Override
    public TempleAcolyte copy() {
        return new TempleAcolyte(this);
    }
}
