
package mage.cards.g;

import mage.MageInt;
import mage.abilities.keyword.DevourAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class GluttonousSlime extends CardImpl {

    public GluttonousSlime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.OOZE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Devour 1 (As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with that many +1/+1 counters on it.)
        this.addAbility(new DevourAbility(1));
    }

    private GluttonousSlime(final GluttonousSlime card) {
        super(card);
    }

    @Override
    public GluttonousSlime copy() {
        return new GluttonousSlime(this);
    }
}
