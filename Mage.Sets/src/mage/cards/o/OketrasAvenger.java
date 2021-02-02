
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.common.PreventCombatDamageToSourceEffect;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author nickymikail
 */
public final class OketrasAvenger extends CardImpl {

    public OketrasAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // You may exert Oketra's Avenger as it attacks. When you do, prevent all combat damage that would be dealt to it this turn.
        BecomesExertSourceTriggeredAbility ability = new BecomesExertSourceTriggeredAbility(new PreventCombatDamageToSourceEffect(Duration.EndOfTurn));
        this.addAbility(new ExertAbility(ability));
    }

    private OketrasAvenger(final OketrasAvenger card) {
        super(card);
    }

    @Override
    public OketrasAvenger copy() {
        return new OketrasAvenger(this);
    }
}
