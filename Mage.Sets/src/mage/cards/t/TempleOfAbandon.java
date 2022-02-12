
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class TempleOfAbandon extends CardImpl {

    public TempleOfAbandon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Temple of Abandon enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Temple of Abandon enters the battlefield, scry 1.</i>
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(1, false)));
        // {T}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private TempleOfAbandon(final TempleOfAbandon card) {
        super(card);
    }

    @Override
    public TempleOfAbandon copy() {
        return new TempleOfAbandon(this);
    }
}
