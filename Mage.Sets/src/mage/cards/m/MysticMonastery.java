
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class MysticMonastery extends CardImpl {

    public MysticMonastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Mystic Monastery enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {T}: Add {U}, {R}, or {W}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private MysticMonastery(final MysticMonastery card) {
        super(card);
    }

    @Override
    public MysticMonastery copy() {
        return new MysticMonastery(this);
    }
}
