
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class TempleOfPlenty extends CardImpl {

    public TempleOfPlenty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Temple of Plenty enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Temple of Plenty enters the battlefield, scry 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(1, false)));
        // {T}: Add {G} or {W}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private TempleOfPlenty(final TempleOfPlenty card) {
        super(card);
    }

    @Override
    public TempleOfPlenty copy() {
        return new TempleOfPlenty(this);
    }
}
