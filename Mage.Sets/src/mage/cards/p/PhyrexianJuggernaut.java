

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class PhyrexianJuggernaut extends CardImpl {

    public PhyrexianJuggernaut (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private PhyrexianJuggernaut(final PhyrexianJuggernaut card) {
        super(card);
    }

    @Override
    public PhyrexianJuggernaut copy() {
        return new PhyrexianJuggernaut(this);
    }

}
