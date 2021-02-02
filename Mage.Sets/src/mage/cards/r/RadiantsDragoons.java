
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class RadiantsDragoons extends CardImpl {

    public RadiantsDragoons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Echo {3}{W}
        this.addAbility(new EchoAbility("{3}{W}"));
        // When Radiant's Dragoons enters the battlefield, you gain 5 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(5)));
    }

    private RadiantsDragoons(final RadiantsDragoons card) {
        super(card);
    }

    @Override
    public RadiantsDragoons copy() {
        return new RadiantsDragoons(this);
    }
}
