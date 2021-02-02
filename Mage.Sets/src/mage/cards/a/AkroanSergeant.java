
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class AkroanSergeant extends CardImpl {

    public AkroanSergeant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Renown 1
        this.addAbility(new RenownAbility(1));
    }

    private AkroanSergeant(final AkroanSergeant card) {
        super(card);
    }

    @Override
    public AkroanSergeant copy() {
        return new AkroanSergeant(this);
    }
}
