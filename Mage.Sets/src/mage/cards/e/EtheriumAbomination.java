

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class EtheriumAbomination extends CardImpl {

    public EtheriumAbomination (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}{U}{B}");
        this.subtype.add(SubType.HORROR);

        
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{1}{U}{B}")));
    }

    public EtheriumAbomination (final EtheriumAbomination card) {
        super(card);
    }

    @Override
    public EtheriumAbomination copy() {
        return new EtheriumAbomination(this);
    }

}
