
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jonubuu
 */
public final class MonstrousCarabid extends CardImpl {

    public MonstrousCarabid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{R}");
        this.subtype.add(SubType.INSECT);


        
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Monstrous Carabid attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
        // Cycling {BR}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{B/R}")));
    }

    private MonstrousCarabid(final MonstrousCarabid card) {
        super(card);
    }

    @Override
    public MonstrousCarabid copy() {
        return new MonstrousCarabid(this);
    }
}
