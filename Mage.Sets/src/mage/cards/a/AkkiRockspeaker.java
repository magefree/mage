
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;

/**
 * @author Loki, North
 */
public final class AkkiRockspeaker extends CardImpl {

    public AkkiRockspeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new EntersBattlefieldTriggeredAbility(new BasicManaEffect(new Mana(ColoredManaSymbol.R))));
    }

    private AkkiRockspeaker(final AkkiRockspeaker card) {
        super(card);
    }

    @Override
    public AkkiRockspeaker copy() {
        return new AkkiRockspeaker(this);
    }
}
