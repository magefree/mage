
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class ThranWarMachine extends CardImpl {

    public ThranWarMachine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        this.addAbility(new EchoAbility("{4}"));
        //Thran War Machine attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private ThranWarMachine(final ThranWarMachine card) {
        super(card);
    }

    @Override
    public ThranWarMachine copy() {
        return new ThranWarMachine(this);
    }
}