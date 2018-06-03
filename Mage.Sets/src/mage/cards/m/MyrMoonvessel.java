
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class MyrMoonvessel extends CardImpl {

    public MyrMoonvessel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new DiesTriggeredAbility(new BasicManaEffect(Mana.ColorlessMana(1))));
    }

    public MyrMoonvessel(final MyrMoonvessel card) {
        super(card);
    }

    @Override
    public MyrMoonvessel copy() {
        return new MyrMoonvessel(this);
    }

}
