
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class MistCloakedHerald extends CardImpl {

    public MistCloakedHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Mist-Cloaked Herald can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private MistCloakedHerald(final MistCloakedHerald card) {
        super(card);
    }

    @Override
    public MistCloakedHerald copy() {
        return new MistCloakedHerald(this);
    }
}
