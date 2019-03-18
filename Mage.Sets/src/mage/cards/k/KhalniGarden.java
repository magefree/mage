

package mage.cards.k;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.PlantToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class KhalniGarden extends CardImpl {

    public KhalniGarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PlantToken()), false));
        this.addAbility(new GreenManaAbility());
    }

    public KhalniGarden(final KhalniGarden card) {
        super(card);
    }

    @Override
    public KhalniGarden copy() {
        return new KhalniGarden(this);
    }

}
