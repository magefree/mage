
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class YoungWolf extends CardImpl {

    public YoungWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.WOLF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Undying
        this.addAbility(new UndyingAbility());
    }

    private YoungWolf(final YoungWolf card) {
        super(card);
    }

    @Override
    public YoungWolf copy() {
        return new YoungWolf(this);
    }
}
