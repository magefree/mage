
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class Cathodion extends CardImpl {

    public Cathodion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new DiesSourceTriggeredAbility(new BasicManaEffect(Mana.ColorlessMana(3)), false));
    }

    private Cathodion(final Cathodion card) {
        super(card);
    }

    @Override
    public Cathodion copy() {
        return new Cathodion(this);
    }
}
