
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author loki
 */
public final class Manakin extends CardImpl {

    public Manakin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new ColorlessManaAbility());
    }

    private Manakin(final Manakin card) {
        super(card);
    }

    @Override
    public Manakin copy() {
        return new Manakin(this);
    }
}
