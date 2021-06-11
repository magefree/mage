
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class Eviscerator extends CardImpl {

    public Eviscerator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));
        // When Eviscerator enters the battlefield, you lose 5 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LoseLifeSourceControllerEffect(5)));
    }

    private Eviscerator(final Eviscerator card) {
        super(card);
    }

    @Override
    public Eviscerator copy() {
        return new Eviscerator(this);
    }
}
