
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class KrumarBondKin extends CardImpl {

    public KrumarBondKin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Morph {4}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{4}{B}")));
    }

    private KrumarBondKin(final KrumarBondKin card) {
        super(card);
    }

    @Override
    public KrumarBondKin copy() {
        return new KrumarBondKin(this);
    }
}
