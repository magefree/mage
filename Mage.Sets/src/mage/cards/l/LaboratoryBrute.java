
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class LaboratoryBrute extends CardImpl {

    public LaboratoryBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Laboratory Brute enters the battlefield, put the top four cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(4)));
    }

    public LaboratoryBrute(final LaboratoryBrute card) {
        super(card);
    }

    @Override
    public LaboratoryBrute copy() {
        return new LaboratoryBrute(this);
    }
}
