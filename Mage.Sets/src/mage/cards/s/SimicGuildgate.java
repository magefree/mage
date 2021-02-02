
package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class SimicGuildgate extends CardImpl {

    public SimicGuildgate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.GATE);

        // Simic Guildgate enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}:Add {U} or {G}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private SimicGuildgate(final SimicGuildgate card) {
        super(card);
    }

    @Override
    public SimicGuildgate copy() {
        return new SimicGuildgate(this);
    }
}
