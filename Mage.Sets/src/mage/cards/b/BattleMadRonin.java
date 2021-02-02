
package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author loki
 */
public final class BattleMadRonin extends CardImpl {

    public BattleMadRonin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN, SubType.SAMURAI);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Bushido 2 (When this blocks or becomes blocked, it gets +2/+2 until end of turn.)
        this.addAbility(new BushidoAbility(2));

        // Battle-Mad Ronin attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private BattleMadRonin(final BattleMadRonin card) {
        super(card);
    }

    @Override
    public BattleMadRonin copy() {
        return new BattleMadRonin(this);
    }

}
