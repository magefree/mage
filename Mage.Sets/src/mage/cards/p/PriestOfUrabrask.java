
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class PriestOfUrabrask extends CardImpl {

    public PriestOfUrabrask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(new EntersBattlefieldTriggeredAbility(new BasicManaEffect(Mana.RedMana(3))));
    }

    private PriestOfUrabrask(final PriestOfUrabrask card) {
        super(card);
    }

    @Override
    public PriestOfUrabrask copy() {
        return new PriestOfUrabrask(this);
    }
}
