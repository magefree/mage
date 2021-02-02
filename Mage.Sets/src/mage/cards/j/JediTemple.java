
package mage.cards.j;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Styxo
 */
public final class JediTemple extends CardImpl {

    public JediTemple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Jedi Temple enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Jedi Temple enters the battlefield , add {W}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddManaToManaPoolSourceControllerEffect(Mana.WhiteMana(1))));

        // {T}: Add {G} or {U} to you mana pool.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());

    }

    private JediTemple(final JediTemple card) {
        super(card);
    }

    @Override
    public JediTemple copy() {
        return new JediTemple(this);
    }
}
