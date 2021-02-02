
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class QuirionSentinel extends CardImpl {

    public QuirionSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Quirion Sentinel enters the battlefield, add one mana of any color.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddManaOfAnyColorEffect()));
    }

    private QuirionSentinel(final QuirionSentinel card) {
        super(card);
    }

    @Override
    public QuirionSentinel copy() {
        return new QuirionSentinel(this);
    }
}
