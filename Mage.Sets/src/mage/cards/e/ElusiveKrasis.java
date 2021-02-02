
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ElusiveKrasis extends CardImpl {

    public ElusiveKrasis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{U}");
        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Elusive Krasis can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
        // Evolve
        this.addAbility(new EvolveAbility());
    }

    private ElusiveKrasis(final ElusiveKrasis card) {
        super(card);
    }

    @Override
    public ElusiveKrasis copy() {
        return new ElusiveKrasis(this);
    }
}
