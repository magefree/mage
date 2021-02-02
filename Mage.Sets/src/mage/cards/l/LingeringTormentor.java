
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class LingeringTormentor extends CardImpl {

    public LingeringTormentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Fear
        this.addAbility(FearAbility.getInstance());
        // Persist
        this.addAbility(new PersistAbility());
    }

    private LingeringTormentor(final LingeringTormentor card) {
        super(card);
    }

    @Override
    public LingeringTormentor copy() {
        return new LingeringTormentor(this);
    }
}
