

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReinforceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class BurrentonBombardier extends CardImpl {

    public BurrentonBombardier (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.KITHKIN, SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Reinforce 2—{2}{W} ({2}{W}, Discard this card: Put two +1/+1 counters on target creature.)
        this.addAbility(new ReinforceAbility(2, new ManaCostsImpl<>("{2}{W}")));
    }

    public BurrentonBombardier (final BurrentonBombardier card) {
        super(card);
    }

    @Override
    public BurrentonBombardier copy() {
        return new BurrentonBombardier(this);
    }

}
