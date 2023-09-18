
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class TempleOfEnlightenment extends CardImpl {

    public TempleOfEnlightenment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Temple of Enlightenment enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Temple of Enlightenment enters the battlefield, scry 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(1, false)));
        // {T}: Add {W} or {U}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());        
    }

    private TempleOfEnlightenment(final TempleOfEnlightenment card) {
        super(card);
    }

    @Override
    public TempleOfEnlightenment copy() {
        return new TempleOfEnlightenment(this);
    }
}
