
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BloodthirstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class GhorClanSavage extends CardImpl {

    public GhorClanSavage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(new BloodthirstAbility(3));
    }

    private GhorClanSavage(final GhorClanSavage card) {
        super(card);
    }

    @Override
    public GhorClanSavage copy() {
        return new GhorClanSavage(this);
    }
}
