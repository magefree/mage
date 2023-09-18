
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class TempleOfMystery extends CardImpl {

    public TempleOfMystery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Temple of Mystery enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Temple of Mystery enters the battlefield, scry 1.</i>
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(1, false)));
        // {T}: Add {G} or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());

    }

    private TempleOfMystery(final TempleOfMystery card) {
        super(card);
    }

    @Override
    public TempleOfMystery copy() {
        return new TempleOfMystery(this);
    }
}
