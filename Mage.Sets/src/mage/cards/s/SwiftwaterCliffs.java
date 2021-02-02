
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class SwiftwaterCliffs extends CardImpl {

    public SwiftwaterCliffs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Swiftwater Cliffs enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Swiftwater Cliffs enters the battlefield, you gain 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));
        // {T}: Add {U} or {R}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());        
              
    }

    private SwiftwaterCliffs(final SwiftwaterCliffs card) {
        super(card);
    }

    @Override
    public SwiftwaterCliffs copy() {
        return new SwiftwaterCliffs(this);
    }
}
