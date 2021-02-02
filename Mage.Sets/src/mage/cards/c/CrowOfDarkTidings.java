
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class CrowOfDarkTidings extends CardImpl {

    public CrowOfDarkTidings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Crow of Dark Tidings enters the battlefield or dies, put the top two cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(new MillCardsControllerEffect(2), false));
    }

    private CrowOfDarkTidings(final CrowOfDarkTidings card) {
        super(card);
    }

    @Override
    public CrowOfDarkTidings copy() {
        return new CrowOfDarkTidings(this);
    }
}
