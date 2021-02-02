
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class BorosGuildgate extends CardImpl {

    public BorosGuildgate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.GATE);

        // Boros Guildgate enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W} or {R}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new RedManaAbility());
    }

    private BorosGuildgate(final BorosGuildgate card) {
        super(card);
    }

    @Override
    public BorosGuildgate copy() {
        return new BorosGuildgate(this);
    }
}
