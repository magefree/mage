
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SaguArcher extends CardImpl {

    public SaguArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // Morph {4}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{4}{G}")));
    }

    private SaguArcher(final SaguArcher card) {
        super(card);
    }

    @Override
    public SaguArcher copy() {
        return new SaguArcher(this);
    }
}
