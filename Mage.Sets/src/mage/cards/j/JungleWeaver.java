

package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class JungleWeaver extends CardImpl {

    public JungleWeaver (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);
        this.addAbility(ReachAbility.getInstance());
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    public JungleWeaver (final JungleWeaver card) {
        super(card);
    }

    @Override
    public JungleWeaver copy() {
        return new JungleWeaver(this);
    }

}
