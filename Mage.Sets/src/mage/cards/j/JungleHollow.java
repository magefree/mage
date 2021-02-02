
package mage.cards.j;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class JungleHollow extends CardImpl {

    public JungleHollow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Jungle Hollow enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Jungle Hollow enters the battlefield, you gain 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));
        // {T}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());
        
    }

    private JungleHollow(final JungleHollow card) {
        super(card);
    }

    @Override
    public JungleHollow copy() {
        return new JungleHollow(this);
    }
}
