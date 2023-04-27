

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ReinforceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class MosquitoGuard extends CardImpl {

    public MosquitoGuard (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(new ReinforceAbility(1, new ManaCostsImpl<>("{1}{W}")));
    }

    public MosquitoGuard (final MosquitoGuard card) {
        super(card);
    }

    @Override
    public MosquitoGuard copy() {
        return new MosquitoGuard(this);
    }

}
