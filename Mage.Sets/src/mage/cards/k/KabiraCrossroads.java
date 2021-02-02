

package mage.cards.k;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class KabiraCrossroads extends CardImpl {

    public KabiraCrossroads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2), false));
        this.addAbility(new WhiteManaAbility());
    }

    private KabiraCrossroads(final KabiraCrossroads card) {
        super(card);
    }

    @Override
    public KabiraCrossroads copy() {
        return new KabiraCrossroads(this);
    }

}
