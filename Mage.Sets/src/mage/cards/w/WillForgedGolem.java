
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author emerald000
 */
public final class WillForgedGolem extends CardImpl {

    public WillForgedGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Convoke
        this.addAbility(new ConvokeAbility());
    }

    private WillForgedGolem(final WillForgedGolem card) {
        super(card);
    }

    @Override
    public WillForgedGolem copy() {
        return new WillForgedGolem(this);
    }
}
